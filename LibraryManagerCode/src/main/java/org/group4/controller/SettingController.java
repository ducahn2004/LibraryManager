package org.group4.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import org.group4.service.user.AccountService;

public class SettingController {

  AccountService accountService = new AccountService();

  @FXML
  private TextField username;
  @FXML
  private JFXButton homeButton;
  @FXML
  private JFXButton MemberButton;
  @FXML
  private JFXButton bookButton;
  @FXML
  private JFXButton bookLendingButton;
  @FXML
  private JFXButton settingButton;
  @FXML
  private JFXButton notificationButton;
  @FXML
  private JFXButton closeButton;
  @FXML
  private PasswordField currentPassword;
  @FXML
  private PasswordField newPassword;
  @FXML
  private PasswordField newPassword2;
  @FXML
  private Button save;
  @FXML
  private Button cancel;
  @FXML
  private ToggleButton showCurrentPasswordBtn;
  @FXML
  private ToggleButton showNewPasswordBtn;
  @FXML
  private ToggleButton showNewPassword2Btn;

  @FXML
  private void changePassword(ActionEvent event) {
    String userId = username.getText();
    String currentPass = currentPassword.getText();
    String newPass = newPassword.getText();
    String confirmPass = newPassword2.getText();

    if (validateFields() && validatePasswordLength() && validatePasswordsMatch()) {
      try {
        boolean isPasswordChanged = accountService.changePassword(userId, currentPass, newPass);

        if (isPasswordChanged) {
          showAlert(AlertType.INFORMATION, "Notification",
              "Password has been successfully changed.");
          clearFields(); // Xóa các trường nhập sau khi thay đổi mật khẩu thành công
        } else {
          showAlert(AlertType.ERROR, "Error",
              "The old password is incorrect or an error occurred.");
        }
      } catch (SQLException ex) {
        showAlert(AlertType.ERROR, "Error", "An error occurred while changing the password.");
        ex.printStackTrace();
      }
    }
  }

  private boolean validateFields() {
    if (currentPassword.getText().isEmpty() || newPassword.getText().isEmpty()
        || newPassword2.getText().isEmpty()) {
      showAlert(AlertType.INFORMATION, "Field Validation", "Please fill in all fields.");
      return false;
    }
    return true;
  }

  private boolean validatePasswordLength() {
    if (newPassword.getText().length() < 8 || newPassword2.getText().length() < 8) {
      showAlert(AlertType.INFORMATION, "Password Length Validation",
          "Password must be at least 8 characters long.");
      return false;
    }
    return true;
  }

  private boolean validatePasswordsMatch() {
    if (!newPassword.getText().equals(newPassword2.getText())) {
      showAlert(AlertType.INFORMATION, "Password Mismatch",
          "The new password and confirmation password do not match.");
      return false;
    }
    return true;
  }

  @FXML
  private void cancel(ActionEvent event) {
    // Khi bấm Cancel, xóa tất cả các ô nhập
    clearFields();
  }

  private void clearFields() {
    currentPassword.clear();
    newPassword.clear();
    newPassword2.clear();
  }

  private void showAlert(AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private Stage getStage() {
    return (Stage) homeButton.getScene().getWindow(); // Có thể sử dụng bất kỳ button nào
  }

  public void HomeAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "AdminPane.fxml", "Library Manager");
  }

  public void MemberAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "MemberView.fxml", "Library Manager");
  }

  public void BookAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "BookView.fxml", "Library Manager");
  }

  public void BookLendingAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "BookLending.fxml", "Library Manager");
  }

  public void notificationAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Notification.fxml", "Library Manager");
  }

  public void SettingAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Setting.fxml", "Library Manager");
  }

  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }

}
