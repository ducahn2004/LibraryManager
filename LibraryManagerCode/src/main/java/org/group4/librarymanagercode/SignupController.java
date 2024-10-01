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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import javafx.stage.Stage;
import org.group4.base.Member;
import org.group4.base.University;

public class SignupController {
  @FXML
  private TextField FirstNameField;

  @FXML
  private TextField LastNameField;

  @FXML
  private TextField StudentIDField;

  @FXML
  private TextField EmailField;

  @FXML
  private PasswordField PasswordField;

  @FXML
  private PasswordField repeatedPasswordField;

  @FXML
  private MenuButton UniversityField;

  @FXML
  private TextField GraduateYearField;

  @FXML
  private Button RegisterButton;

  @FXML
  private Button LoginButton;

  private String firstName = "";
  private String lastName  = "";
  private String email = "";
  private int studentID = 0;
  private String password = "";
  private String repeatedPassword = "";
  private String graduateYear = "";
  private University university = null;

  @FXML
  public void initialize() {
    RegisterButton.setOnAction(event -> registerUser());
    populateUniversityMenu();
  }

  // Phương thức lấy thông tin từ các trường nhập
  private void registerUser() {
    firstName = FirstNameField.getText();
    lastName = LastNameField.getText();
    email = EmailField.getText();
    password = PasswordField.getText();
    repeatedPassword = repeatedPasswordField.getText();
    graduateYear = GraduateYearField.getText();
    studentID = Integer.parseInt(StudentIDField.getText());

    if (isInputValid()) {

      String universityName = (university != null) ? university.getDisplayName() : "";

      // Tạo đối tượng Member mới
      //Member newMember = new Member(firstName, lastName, studentID, email, password, universityName, graduateYear);

      // Thêm thành viên vào cơ sở dữ liệu
      //boolean success = memberDAO.addMember(newMember);
        showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You have successfully registered!");
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
    }else {
      showAlert(Alert.AlertType.ERROR, "Registration Failed", "Failed to register. Please try again.");
    }
  }


  private boolean isInputValid() {
    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || graduateYear.isEmpty()) {
      showAlert(Alert.AlertType.ERROR, "Input Error", "All fields are required!");
      return false;
    }

    if (!password.equals(repeatedPassword)) {
      showAlert(Alert.AlertType.ERROR, "Password Error", "Passwords do not match!");
      return false;
    }
    if(isValidEmail(email)){
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

  // Phương thức hiển thị thông báo cho người dùng
  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }


  @FXML
  private void populateUniversityMenu() {
    for (University uni : University.values()) {
      MenuItem item = new MenuItem(uni.getDisplayName());
      item.setOnAction(event -> {
        UniversityField.setText(uni.getDisplayName());
        university = University.valueOf(uni.getDisplayName()); // Lưu giá trị vào biến university
      });
      UniversityField.getItems().add(item);
    }
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
