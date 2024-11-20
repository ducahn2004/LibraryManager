package org.group4.librarymanagercode;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.module.manager.SessionManager;
import org.group4.module.users.Member;
import org.group4.module.users.Librarian;

public class AddMemberController {

  @FXML
  private Label memberInformation;
  @FXML
  private TextField memberName;
  @FXML
  private DatePicker memberBirth;
  @FXML
  private TextField memberEmail;
  @FXML
  private TextField memberPhone;


  private MemberViewController parentController;
  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();
  Member currentMember;
  private Stage memberFormStage;

  public void setStage(Stage stage) {
    this.memberFormStage = stage;
  }

  public void cancel(ActionEvent actionEvent) {
    closeForm();
  }

  public void saveMember(ActionEvent actionEvent) {
    try {
      // Validate inputs
      validateInputs();

      // Perform member addition logic
      addMemberToLibrary();

      // Show success message and wait for user confirmation
      showSuccessAndCloseForm();

    } catch (IllegalArgumentException e) {
      // Handle invalid input exceptions
      showAlert(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
    }
  }

  private void validateInputs() throws IllegalArgumentException {
    // Check if required fields are empty
    String regex = "([A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯỲÝỴÝĂẮẰẲẴẶÂẦẤẨẪẬÀÁÃẠẢÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ]\\p{L}*)(\\s[A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯỲÝỴÝĂẮẰẲẴẶÂẦẤẨẪẬÀÁÃẠẢÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ]\\p{L}*)*";
    if (memberName.getText().isEmpty() || memberEmail.getText().isEmpty() ||
        memberPhone.getText().isEmpty() || memberBirth.getValue() == null) {
      throw new IllegalArgumentException("Please fill in all required information.");
    }

    // Validate name format

    if (!memberName.getText().matches(regex)) {
      throw new IllegalArgumentException("Name must start with uppercase letters for each word.");
    }

    // Validate email format
    if (!memberEmail.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
      throw new IllegalArgumentException("Please enter a valid email address.");
    }

    // Validate phone format
    if (!memberPhone.getText().matches("\\d{10}")) {
      throw new IllegalArgumentException("Phone number must contain exactly 10 digits.");
    }
  }

  private void addMemberToLibrary() {
    // Check if the member already exists (custom logic in returnCheckAddMember)
    returnCheckAddMember();

    // Add the new member to the database
    if (parentController != null) {
      parentController.addMemberToList(currentMember);
    }
  }

  private void showSuccessAndCloseForm() {
    // Create a success alert
    Alert successAlert = new Alert(AlertType.INFORMATION);
    successAlert.setTitle("Add Member Successfully");
    successAlert.setHeaderText(null);
    successAlert.setContentText("The member has been added to the library.");

    // Set an action to close the form after the alert is dismissed
    successAlert.setOnHidden(event -> closeForm());

    // Show the alert and wait for the user to click "OK"
    successAlert.showAndWait();
  }


  public void setParentController(MemberViewController parentController) {
    this.parentController = parentController;
  }

  private void closeForm() {
    Stage stage = (Stage) memberInformation.getScene().getWindow();
    stage.close(); // cửa sổ hiện tại
  }

  private void returnCheckAddMember() {
    currentMember = new Member(memberName.getText(), memberBirth.getValue(),
        memberEmail.getText(),
        memberPhone.getText());
    boolean successEdit = librarian.addMember(currentMember);
    if (successEdit) {
      // TODO Uncomment after notification complete
      //SystemNotification.sendNotification(String type, String content);
      System.out.println(
          "Member with member ID: " + currentMember.getMemberId()
              + " has been edited successfully.");
    } else {

      System.out.println("Member with member ID: " + currentMember.getMemberId());
      throw new IllegalArgumentException(
          "Member with the same information already exists in the library.");
    }
  }

  /**
   * Shows an alert dialog with specified type, title, and message.
   *
   * @param alertType Type of alert
   * @param title     Title of the alert
   * @param message   Message content of the alert
   */
  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
