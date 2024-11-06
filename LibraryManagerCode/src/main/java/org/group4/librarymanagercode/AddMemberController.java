package org.group4.librarymanagercode;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMemberController {

  @FXML
  private TextField PhoneField;

  @FXML
  private TextField FullNameField;

  @FXML
  private TextField StudentIDField;

  @FXML
  private TextField EmailField;

  @FXML
  private PasswordField PasswordField;

  @FXML
  private PasswordField repeatedPasswordField;

  @FXML
  private Button RegisterButton;

  @FXML
  public void initialize() {
    RegisterButton.setOnAction(event -> registerUser());
  }

  private void registerUser() {
    String fullName = FullNameField.getText();
    String phone = PhoneField.getText();
    String email = EmailField.getText();
    String password = PasswordField.getText();
    String repeatedPassword = repeatedPasswordField.getText();
    Integer studentID = parseStudentID(StudentIDField.getText());

    if (isInputValid(fullName, phone, email, password, repeatedPassword, studentID)) {
      showAlert(Alert.AlertType.INFORMATION, "Registration Successful",
          "You have successfully registered!");
      navigateToLogin();
    } else {
      showAlert(Alert.AlertType.ERROR, "Registration Failed",
          "Failed to register. Please try again.");
    }
  }

  private boolean isInputValid(String fullName, String phone, String email, String password,
      String repeatedPassword, Integer studentID) {
    if (fullName.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
      showAlert(Alert.AlertType.ERROR, "Input Error", "All fields are required!");
      return false;
    }

    if (!password.equals(repeatedPassword)) {
      showAlert(Alert.AlertType.ERROR, "Password Error", "Passwords do not match!");
      return false;
    }

    if (!isValidEmail(email)) {
      showAlert(Alert.AlertType.ERROR, "Email Error", "Invalid email format!");
      return false;
    }

    if (studentID == null) {
      showAlert(Alert.AlertType.ERROR, "Input Error", "Student ID must be a number!");
      return false;
    }

    return true;
  }

  private Integer parseStudentID(String studentIDText) {
    try {
      return Integer.parseInt(studentIDText);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private boolean isValidEmail(String email) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@(gmail\\.com|vnu\\.edu\\.vn)$";
    return email.matches(emailRegex);
  }

  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @FXML
  private void handleBackToLoginAction(ActionEvent event) {
    navigateToLogin();
  }

  private void navigateToLogin() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root, 700, 550);
      Stage stage = (Stage) RegisterButton.getScene().getWindow();
      stage.setScene(scene);
      stage.setTitle("Library Manager");
      stage.show();
    } catch (IOException e) {
      Logger.getLogger(AddMemberController.class.getName()).severe(e.getMessage());
      showAlert(AlertType.ERROR, "Error", "Unable to load login page.");
    }
  }
}