package org.group4.librarymanagercode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.group4.module.manager.SessionManager;
import org.group4.module.users.Member;
import org.group4.module.users.Librarian;

public class AddMemberController {

  @FXML
  private Label memberID;
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

  public void cancel(ActionEvent actionEvent) {
    closeForm();
  }

  public void saveMember(ActionEvent actionEvent) {
    if (memberName.getText().isEmpty() || memberEmail.getText().isEmpty() ||
        memberPhone.getText().isEmpty() || memberBirth.getValue() == null) {
      showAlert(AlertType.WARNING, "Incomplete Information",
          "Please fill in all required information.");
      return; // Stop execution to allow user to correct input
    }
    returnCheckAddMember();

    try {
      // Add the new member to the library (i.e., database)
      // After the member is successfully added to the library, update the parent controller's table.
      if (parentController != null) {
        parentController.addMemberToList(currentMember);
      }
      // Show success message
      showAlert(Alert.AlertType.INFORMATION, "Add Member Successfully",
          "The member has been added to the library.");
    } catch (IllegalArgumentException e) {
      showAlert(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
    }
    closeForm();
  }


  public void setParentController(MemberViewController parentController) {
    this.parentController = parentController;
  }

  private void closeForm() {
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
          "Member with member ID: " + currentMember.getMemberId() + " has been edited successfully.");
    } else {

      System.out.println("Member with member ID: " + currentMember.getMemberId());
      throw new IllegalArgumentException("Member with the same information already exists in the library.");
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
