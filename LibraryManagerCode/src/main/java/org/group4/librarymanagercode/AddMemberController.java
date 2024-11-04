package org.group4.librarymanagercode;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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


  private String fullName = "";
  private String phone = "";
  private String email = "";
  private int studentID = 0;
  private String password = "";
  private String repeatedPassword = "";

  @FXML
  public void initialize() {
    RegisterButton.setOnAction(event -> registerUser());
  }

  // Phương thức lấy thông tin từ các trường nhập
  private void registerUser() {
    fullName = FullNameField.getText();
    phone = PhoneField.getText();
    email = EmailField.getText();
    password = PasswordField.getText();
    repeatedPassword = repeatedPasswordField.getText();
    studentID = Integer.parseInt(StudentIDField.getText());

    if (isInputValid()) {

      // Tạo đối tượng Member mới
      //Member newMember = new Member(firstName, lastName, studentID, email, password, universityName, graduateYear);

      // Thêm thành viên vào cơ sở dữ liệu
      //boolean success = memberDAO.addMember(newMember);
      showAlert(Alert.AlertType.INFORMATION, "Registration Successful",
          "You have successfully registered!");
      // Chuyển về trang đăng nhập
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 700, 550);
        Stage stage = (Stage) ((Node) RegisterButton).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Library Manager");
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      showAlert(Alert.AlertType.ERROR, "Registration Failed",
          "Failed to register. Please try again.");
    }
  }


  private boolean isInputValid() {
    if (fullName.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
      showAlert(Alert.AlertType.ERROR, "Input Error", "All fields are required!");
      return false;
    }

    if (!password.equals(repeatedPassword)) {
      showAlert(Alert.AlertType.ERROR, "Password Error", "Passwords do not match!");
      return false;
    }
    if (isValidEmail(email)) {
      String emailRegex = "^[A-Za-z0-9+_.-]+@(gmail\\.com|vnu\\.edu\\.vn)$";
      showAlert(Alert.AlertType.ERROR, "Email Error", "Email is need to require");
      return email.matches(emailRegex);
    }

    try {
      studentID = Integer.parseInt(StudentIDField.getText());
    } catch (NumberFormatException e) {
      showAlert(Alert.AlertType.ERROR, "Input Error", "Student ID must be a number!");
      return false;
    }

    return true;
  }

  /**
   * This function is used to show alert to User.
   *
   * @param alertType is alert Type
   * @param title     is title to user
   * @param message   is the content to user
   */
  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }


  private boolean isValidEmail(String email) {
    return email.matches("^(.+)@(.+)$");
  }

  @FXML
  private void handleBackToLoginAction(ActionEvent event) throws IOException {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root, 700, 550);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.setTitle("Library Manager");
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      showAlert(AlertType.ERROR, "Error", "Unable to load login page.");
    }
  }

}
