package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import java.util.logging.Level;
import java.util.logging.Logger;
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
  private final Librarian librarian = SessionManager.getInstance()
      .getCurrentLibrarian(); // Default librarian instance

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
   * Saves the member data after validating the input fields. If all fields are filled, updates the
   * member and refreshes the table in the parent controller.
   *
   * @param actionEvent the event triggered by the save button
   */
  @FXML
  private void saveMember(ActionEvent actionEvent) {
    // Check if any required field is empty
    if (memberName.getText().isEmpty() || memberEmail.getText().isEmpty() ||
        memberPhone.getText().isEmpty() || memberBirth.getValue() == null) {
      showAlert("Incomplete Information", "Please fill in all required information.");
      return; // Stop execution to allow user to correct input
    }

    // Set member details after validating that fields are not empty
    currentMember.setName(memberName.getText());
    currentMember.setDateOfBirth(memberBirth.getValue());
    currentMember.setEmail(memberEmail.getText());
    currentMember.setPhoneNumber(memberPhone.getText());

    returnCheckEditMember();
    // Refresh the table and close the form if all validations pass
    if (parentController != null) {
      parentController.refreshTable();
    }
    closeForm();
  }

  private void returnCheckEditMember() {
    boolean successEdit = librarian.updateMember(currentMember);
    if (successEdit) {
      // TODO Uncomment after notification complete
      //SystemNotification.sendNotification(String type, String content);
      System.out.println(
          "Book with ISBN: " + currentMember.getMemberId() + " has been edited successfully.");
    } else {

      System.out.println("Failed to edit book with ISBN: " + currentMember.getMemberId());
      throw new IllegalArgumentException("Book with the same ISBN already exists in the library.");
    }
  }

  /**
   * Creates an alert dialog with specified parameters.
   *
   * @param type    the type of alert
   * @param title   the title of the alert
   * @param header  the header text of the alert
   * @param content the content message of the alert
   * @return the constructed Alert object
   */
  private Alert createAlert(AlertType type, String title, String header, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    return alert;
  }

  /**
   * Displays a warning alert with a specified title and content message.
   *
   * @param title   the title of the alert
   * @param content the content message of the alert
   */
  private void showAlert(String title, String content) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
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
}
