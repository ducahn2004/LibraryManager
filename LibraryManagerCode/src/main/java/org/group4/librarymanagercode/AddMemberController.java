package org.group4.librarymanagercode;

import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.base.books.Book;
import org.group4.base.users.Member;
import org.group4.base.users.Librarian;
import org.group4.database.LibrarianDatabase;

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
  private static final Librarian librarian = LibrarianDatabase.getInstance().getItems().getFirst();

  public void cancel(ActionEvent actionEvent) {
    closeForm();
  }

  public void saveMember(ActionEvent actionEvent) {
    try {
      addMemberToLibrary(); // Add the new member to the library (i.e., database)
      // After the member is successfully added to the library, update the parent controller's table.
      if (parentController != null) {
        parentController.addMemberToList(new Member(memberName.getText(), memberBirth.getValue(), memberEmail.getText(), memberPhone.getText()));
      }
      // Show success message
      showAlert(Alert.AlertType.INFORMATION, "Add Member Successfully", "The member has been added to the library.");
    } catch (IllegalArgumentException e) {
      showAlert(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
    }
    closeForm();
  }


  public void setParentController(MemberViewController parentController) {
    this.parentController = parentController;
  }

  private void closeForm() {
    Stage stage = (Stage) memberID.getScene().getWindow();
    stage.close();
  }
  private void addMemberToLibrary(){
    boolean added = librarian.addMember(new Member(memberName.getText(), memberBirth.getValue(), memberEmail.getText(), memberPhone.getText()));
    if(!added){
      throw new IllegalArgumentException("Could not add member to library");
    }
    //TODO: DECREASED ID MEMBER AFTER CREATE MEMBER DATA
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
