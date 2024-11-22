package org.group4.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.model.manager.SessionManager;
import org.group4.model.users.Librarian;
import org.group4.model.users.Member;

/**
 * Controller for editing member details. This class handles the UI interactions and business logic
 * for updating member information, such as name, email, phone number, and date of birth.
 */
public class MemberEditController {

  // UI components for saving and displaying member details
  @FXML
  private JFXButton save;
  @FXML
  private Label memberID;
  @FXML
  private TextField memberName, memberEmail, memberPhone;
  @FXML
  private DatePicker memberBirth;

  // Member being edited and the parent controller
  private Member currentMember;
  private MemberViewController parentController;

  // Librarian instance for performing member updates
  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();

  /**
   * Sets the parent controller, allowing the parent to refresh the member list after editing.
   *
   * @param controller The parent controller instance.
   */
  public void setParentController(MemberViewController controller) {
    this.parentController = controller;
  }

  /**
   * Populates the form fields with the data of the member being edited.
   *
   * @param member The member whose data will populate the fields.
   */
  public void setMemberData(Member member) {
    this.currentMember = member;
    memberName.setText(member.getName());
    memberBirth.setValue(member.getDateOfBirth());
    memberEmail.setText(member.getEmail());
    memberPhone.setText(member.getPhoneNumber());
  }

  /**
   * Saves the updated member data after validating the input fields. If validation passes, the
   * changes are saved, and the parent table is refreshed.
   *
   * @param actionEvent The event triggered by clicking the save button.
   */
  @FXML
  private void saveMember(ActionEvent actionEvent) {
    // Validate inputs before saving
    if (!validateAllInputs()) {
      return;
    }

    // Update member details
    currentMember.setName(memberName.getText());
    currentMember.setDateOfBirth(memberBirth.getValue());
    currentMember.setEmail(memberEmail.getText());
    currentMember.setPhoneNumber(memberPhone.getText());

    // Perform the update in the system
    returnCheckEditMember();

    // Refresh parent table and close the form
    if (parentController != null) {
      parentController.refreshTable();
    }
    closeForm();
  }

  /**
   * Validates all input fields for correctness.
   *
   * @return true if all fields are valid; false otherwise.
   */
  private boolean validateAllInputs() {
    // Check for empty required fields
    if (memberName.getText().isEmpty() || memberEmail.getText().isEmpty() ||
        memberPhone.getText().isEmpty() || memberBirth.getValue() == null) {
      showAlert(AlertType.WARNING, "Incomplete Information",
          "Please fill in all required information.");
      return false;
    }

    // Validate name format
    if (!isValidName(memberName.getText())) {
      showAlert(AlertType.WARNING, "Invalid Name",
          "Name must start with uppercase letters for each word.");
      return false;
    }

    // Validate email format
    if (!isValidEmail(memberEmail.getText())) {
      showAlert(AlertType.WARNING, "Invalid Email", "Please enter a valid email address.");
      return false;
    }

    // Validate phone number format
    if (!isValidPhone(memberPhone.getText())) {
      showAlert(AlertType.WARNING, "Invalid Phone Number",
          "Phone number must contain exactly 10 digits.");
      return false;
    }

    return true;
  }

  /**
   * Validates the member's name, allowing for names with special characters (e.g., Vietnamese).
   *
   * @param name The name to validate.
   * @return true if the name is valid; false otherwise.
   */
  private boolean isValidName(String name) {
    String regex = "([A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯỲÝỴÝĂẮẰẲẴẶÂẦẤẨẪẬÀÁÃẠẢÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ]\\p{L}*)(\\s[A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯỲÝỴÝĂẮẰẲẴẶÂẦẤẨẪẬÀÁÃẠẢÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ]\\p{L}*)*";
    return name.matches(regex);
  }

  /**
   * Validates the email format.
   *
   * @param email The email to validate.
   * @return true if the email is valid; false otherwise.
   */
  private boolean isValidEmail(String email) {
    return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
  }

  /**
   * Validates the phone number format.
   *
   * @param phone The phone number to validate.
   * @return true if the phone number is valid; false otherwise.
   */
  private boolean isValidPhone(String phone) {
    return phone.matches("\\d{10}");
  }

  /**
   * Checks if the member's update is successful. Throws an exception if the update fails.
   */
  private void returnCheckEditMember() {
    boolean successEdit = librarian.getMemberManager().update(currentMember);
    if (!successEdit) {
      throw new IllegalArgumentException(
          "Member with the same details already exists in the library.");
    }
  }

  /**
   * Cancels the current operation and closes the form.
   */
  @FXML
  private void cancel() {
    closeForm();
  }

  /**
   * Closes the form window.
   */
  private void closeForm() {
    Stage stage = (Stage) memberID.getScene().getWindow();
    stage.close();
  }

  /**
   * Displays an alert with the specified details.
   *
   * @param type    The type of alert.
   * @param title   The alert's title.
   * @param content The alert's message content.
   */
  private void showAlert(AlertType type, String title, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}
