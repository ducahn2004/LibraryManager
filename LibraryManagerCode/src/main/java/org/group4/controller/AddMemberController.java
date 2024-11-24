package org.group4.controller;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.service.user.SessionManagerService;
import org.group4.model.user.Member;
import org.group4.model.user.Librarian;

/**
 * Controller for the "Add Member" functionality.
 * <p>
 * This class manages the UI and logic for adding a new library member. It validates user input,
 * interacts with the database through a librarian, and updates the parent controller when
 * necessary.
 */
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
  private final Librarian librarian = SessionManagerService.getInstance().getCurrentLibrarian();
  private Member currentMember;
  private Stage memberFormStage;

  /**
   * Sets the stage for the member form.
   *
   * @param stage The stage to be set.
   */
  public void setStage(Stage stage) {
    this.memberFormStage = stage;
  }

  /**
   * Handles the cancel button action.
   * <p>
   * Closes the form without saving any changes.
   *
   * @param actionEvent The event triggered by clicking the cancel button.
   */
  public void cancel(ActionEvent actionEvent) {
    closeForm();
  }

  /**
   * Handles the save button action.
   * <p>
   * Validates the input, adds the member to the library, and shows a success message.
   *
   * @param actionEvent The event triggered by clicking the save button.
   */
  public void saveMember(ActionEvent actionEvent) {
    try {
      // Validate the input fields
      validateInputs();

      // Add the member to the library database
      addMemberToLibrary();

      // Show a success message and close the form
      showSuccessAndCloseForm();

    } catch (IllegalArgumentException e) {
      // Display an error message if input validation fails
      showAlert(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Validates the input fields for the member form.
   *
   * @throws IllegalArgumentException If any input is invalid.
   */
  private void validateInputs() throws IllegalArgumentException {
    String regex = "([A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯỲÝỴÝĂẮẰẲẴẶÂẦẤẨẪẬÀÁÃẠẢÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ]\\p{L}*)(\\s[A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯỲÝỴÝĂẮẰẲẴẶÂẦẤẨẪẬÀÁÃẠẢÈÉẺẼẸÊỀẾỂỄỆÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴ]\\p{L}*)*";

    // Ensure all required fields are filled
    if (memberName.getText().isEmpty() || memberEmail.getText().isEmpty() ||
        memberPhone.getText().isEmpty() || memberBirth.getValue() == null) {
      throw new IllegalArgumentException("Please fill in all required information.");
    }

    // Validate the name format
    if (!memberName.getText().matches(regex)) {
      throw new IllegalArgumentException("Name must start with uppercase letters for each word.");
    }

    // Validate the email format
    if (!memberEmail.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
      throw new IllegalArgumentException("Please enter a valid email address.");
    }

    // Validate the phone number format
    if (!memberPhone.getText().matches("\\d{10}")) {
      throw new IllegalArgumentException("Phone number must contain exactly 10 digits.");
    }
  }

  /**
   * Adds a new member to the library database.
   * <p>
   * Checks for duplicate entries before adding and updates the parent controller if successful.
   */
  private void addMemberToLibrary() throws IOException, SQLException {
    // Verify that the member does not already exist
    returnCheckAddMember();

    // Notify the parent controller of the new member
    if (parentController != null) {
      parentController.addMemberToList(currentMember);
    }
  }

  /**
   * Displays a success message and closes the form.
   */
  private void showSuccessAndCloseForm() {
    // Create an information alert
    Alert successAlert = new Alert(AlertType.INFORMATION);
    successAlert.setTitle("Add Member Successfully");
    successAlert.setHeaderText(null);
    successAlert.setContentText("The member has been added to the library.");

    // Close the form when the alert is dismissed
    successAlert.setOnHidden(event -> closeForm());
    successAlert.showAndWait();
  }

  /**
   * Sets the parent controller for communication.
   *
   * @param parentController The parent controller to be set.
   */
  public void setParentController(MemberViewController parentController) {
    this.parentController = parentController;
  }

  /**
   * Closes the form.
   */
  private void closeForm() {
    Stage stage = (Stage) memberInformation.getScene().getWindow();
    stage.close();
  }

  /**
   * Checks if the member already exists and adds it to the database if not.
   * <p>
   * Throws an exception if a duplicate is detected.
   */
  private void returnCheckAddMember() throws IOException, SQLException {
    currentMember = new Member(
        memberName.getText(),
        memberBirth.getValue(),
        memberEmail.getText(),
        memberPhone.getText()
    );

    boolean successEdit = librarian.getMemberManager().add(currentMember);
    if (successEdit) {
      // TODO Uncomment after notification is implemented
      // SystemNotification.sendNotification(String type, String content);
      System.out.println(
          "Member with member ID: " + currentMember.getMemberId()
              + " has been edited successfully.");
    } else {
      throw new IllegalArgumentException(
          "Member with the same information already exists in the library.");
    }
  }

  /**
   * Shows an alert dialog with a specified type, title, and message.
   *
   * @param alertType The type of the alert (e.g., ERROR, INFORMATION).
   * @param title     The title of the alert dialog.
   * @param message   The message content of the alert dialog.
   */
  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
