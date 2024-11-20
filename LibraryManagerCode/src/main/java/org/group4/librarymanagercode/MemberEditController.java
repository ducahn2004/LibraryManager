package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.module.manager.SessionManager;
import org.group4.module.users.Librarian;
import org.group4.module.users.Member;

/**
 * Controller class for editing member details.
 */
public class MemberEditController {

  @FXML
  private JFXButton save; // Button to save the member's information

  @FXML
  private Label memberID; // Label to display the member ID

  @FXML
  private TextField memberName, memberEmail, memberPhone; // Text fields for member information

  @FXML
  private DatePicker memberBirth; // DatePicker for member's birth date

  private Member currentMember; // The member being edited
  private MemberViewController parentController; // Reference to the parent controller
  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian(); // Default librarian instance

  /**
   * Sets the parent controller to enable refreshing the member table after updates.
   *
   * @param controller the parent controller instance
   */
  public void setParentController(MemberViewController controller) {
    this.parentController = controller;
  }

  /**
   * Initializes the form fields with the data of the member being edited.
   *
   * @param member the member whose data will populate the form fields
   */
  public void setMemberData(Member member) {
    this.currentMember = member;
    memberName.setText(member.getName());
    memberBirth.setValue(member.getDateOfBirth());
    memberEmail.setText(member.getEmail());
    memberPhone.setText(member.getPhoneNumber());
  }

  /**
   * Saves the member data after validating the input fields.
   *
   * @param actionEvent the event triggered by the save button
   */
  @FXML
  private void saveMember(ActionEvent actionEvent) {
    // Validate inputs and handle errors
    if (!validateAllInputs()) {
      return;
    }

    // Set member details after validation passes
    currentMember.setName(memberName.getText());
    currentMember.setDateOfBirth(memberBirth.getValue());
    currentMember.setEmail(memberEmail.getText());
    currentMember.setPhoneNumber(memberPhone.getText());

    // Update member in the system
    returnCheckEditMember();

    // Refresh the table and close the form
    if (parentController != null) {
      parentController.refreshTable();
    }
    closeForm();
  }

  /**
   * Validates all input fields.
   *
   * @return true if all inputs are valid, false otherwise
   */
  private boolean validateAllInputs() {
    // Check if any required field is empty
    if (memberName.getText().isEmpty() || memberEmail.getText().isEmpty() ||
        memberPhone.getText().isEmpty() || memberBirth.getValue() == null) {
      showAlert(AlertType.WARNING, "Incomplete Information", "Please fill in all required information.");
      return false;
    }

    // Check if name is valid
    if (!isValidName(memberName.getText())) {
      showAlert(AlertType.WARNING, "Invalid Name", "Name must start with uppercase letters for each word.");
      return false;
    }

    // Check if email is valid
    if (!isValidEmail(memberEmail.getText())) {
      showAlert(AlertType.WARNING, "Invalid Email", "Please enter a valid email address.");
      return false;
    }

    // Check if phone is valid
    if (!isValidPhone(memberPhone.getText())) {
      showAlert(AlertType.WARNING, "Invalid Phone Number", "Phone number must contain exactly 10 digits.");
      return false;
    }

    return true;
  }

  /**
   * Checks if the name is valid, including Vietnamese characters.
   *
   * @param name the name to validate
   * @return true if valid, false otherwise
   */
  private boolean isValidName(String name) {
    // Regex to validate names with Unicode (Vietnamese support)
    String regex = "([A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯỲÝỴÝĂẮẰẲẴẶÂẦẤẨẪẬÀÁÃẠẢÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ]\\p{L}*)(\\s[A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯỲÝỴÝĂẮẰẲẴẶÂẦẤẨẪẬÀÁÃẠẢÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ]\\p{L}*)*";
    return name.matches(regex);
  }


  /**
   * Checks if the email is valid.
   *
   * @param email the email to validate
   * @return true if valid, false otherwise
   */
  private boolean isValidEmail(String email) {
    return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
  }

  /**
   * Checks if the phone number is valid.
   *
   * @param phone the phone number to validate
   * @return true if valid, false otherwise
   */
  private boolean isValidPhone(String phone) {
    return phone.matches("\\d{10}");
  }

  private void returnCheckEditMember() {
    boolean successEdit = librarian.updateMember(currentMember);
    if (!successEdit) {
      throw new IllegalArgumentException("Member with the same details already exists in the library.");
    }
  }

  /**
   * Cancels the current edit operation and closes the form.
   */
  @FXML
  private void cancel() {
    closeForm();
  }

  /**
   * Closes the current form window.
   */
  private void closeForm() {
    Stage stage = (Stage) memberID.getScene().getWindow();
    stage.close();
  }

  /**
   * Displays an alert dialog.
   *
   * @param type    the type of alert
   * @param title   the title of the alert
   * @param content the content message of the alert
   */
  private void showAlert(AlertType type, String title, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}
