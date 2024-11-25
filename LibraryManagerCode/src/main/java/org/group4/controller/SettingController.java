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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.group4.service.user.AccountService;

public class SettingController {

  AccountService accountService = new AccountService();

  @FXML
  private TextField username, textShowCurrentPassword, textShowNewPassword, textShowNewPassword2;
  ;
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
  private ImageView iconCloseEyeCurrent, iconOpenEyeCurrent, iconCloseEyeNew, iconOpenEyeNew, iconCloseEyeNew2, iconOpenEyeNew2;
  @FXML
  private Button save;
  @FXML
  private Button cancel;

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
    String password = newPassword.getText();
    if (!password.matches(".*[a-z].*")) {
      showAlert(AlertType.INFORMATION, "Password Complexity",
          "Password must contain at least one lowercase letter.");
      return false;
    }
    if (!password.matches(".*[A-Z].*")) {
      showAlert(AlertType.INFORMATION, "Password Complexity",
          "Password must contain at least one uppercase letter.");
      return false;
    }
    if (!password.matches(".*\\d.*")) {
      showAlert(AlertType.INFORMATION, "Password Complexity",
          "Password must contain at least one digit.");
      return false;
    }
    if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
      showAlert(AlertType.INFORMATION, "Password Complexity",
          "Password must contain at least one special character (e.g., !@#$%^&*).");
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

  @FXML
  private void initialize() {
    // Set the initial visibility state of the password fields and icons.
    textShowCurrentPassword.setVisible(false);
    iconCloseEyeCurrent.setVisible(true);
    iconOpenEyeCurrent.setVisible(false);

    textShowNewPassword.setVisible(false);
    iconCloseEyeNew.setVisible(true);
    iconOpenEyeNew.setVisible(false);

    textShowNewPassword2.setVisible(false);
    iconCloseEyeNew2.setVisible(true);
    iconOpenEyeNew2.setVisible(false);
  }

  private String current_Password;

  /**
   * Updates the plain text password field when a key is pressed in the masked password field.
   *
   * @param keyEvent The KeyEvent triggered when a key is pressed.
   */
  public void hideCurrentPasswordOnAction(KeyEvent keyEvent) {
    current_Password = currentPassword.getText(); // Retrieve the current password.
    textShowCurrentPassword.setText(
        current_Password); // Update the plain text field with the password.
  }

  /**
   * Updates the masked password field when a key is pressed in the plain text password field.
   *
   * @param keyEvent The KeyEvent triggered when a key is pressed.
   */
  public void showCurrentPasswordOnAction(KeyEvent keyEvent) {
    current_Password = textShowCurrentPassword.getText(); // Retrieve the password from the plain text field.
    currentPassword.setText(current_Password); // Update the masked field with the password.
  }

  /**
   * Toggles the UI to show the password in plain text.
   *
   * @param mouseEvent The MouseEvent triggered when the "eye open" icon is clicked.
   */
  public void closeClickedOnActionCurrent(MouseEvent mouseEvent) {
    textShowCurrentPassword.setVisible(true); // Show the plain text password field.
    iconOpenEyeCurrent.setVisible(true); // Display the "eye open" icon.
    iconCloseEyeCurrent.setVisible(false); // Hide the "eye closed" icon.
    currentPassword.setVisible(false); // Hide the masked password field.
  }

  /**
   * Toggles the UI to mask the password.
   *
   * @param mouseEvent The MouseEvent triggered when the "eye closed" icon is clicked.
   */
  public void openClickedOnActionCurrent(MouseEvent mouseEvent) {
    textShowCurrentPassword.setVisible(false); // Hide the plain text password field.
    iconOpenEyeCurrent.setVisible(false); // Hide the "eye open" icon.
    iconCloseEyeCurrent.setVisible(true); // Display the "eye closed" icon.
    currentPassword.setVisible(true); // Show the masked password field.
  }

  private String new_Password;

  /**
   * Updates the plain text password field when a key is pressed in the masked password field.
   *
   * @param keyEvent The KeyEvent triggered when a key is pressed.
   */
  public void hidePasswordOnActionNew(KeyEvent keyEvent) {
    new_Password = newPassword.getText(); // Retrieve the current password.
    textShowNewPassword.setText(new_Password); // Update the plain text field with the password.
  }

  /**
   * Updates the masked password field when a key is pressed in the plain text password field.
   *
   * @param keyEvent The KeyEvent triggered when a key is pressed.
   */
  public void showPasswordOnActionNew(KeyEvent keyEvent) {
    new_Password = textShowNewPassword.getText(); // Retrieve the password from the plain text field.
    newPassword.setText(new_Password); // Update the masked field with the password.
  }

  /**
   * Toggles the UI to show the password in plain text.
   *
   * @param mouseEvent The MouseEvent triggered when the "eye open" icon is clicked.
   */
  public void closeClickedOnActionNew(MouseEvent mouseEvent) {
    textShowNewPassword.setVisible(true); // Show the plain text password field.
    iconOpenEyeNew.setVisible(true); // Display the "eye open" icon.
    iconCloseEyeNew.setVisible(false); // Hide the "eye closed" icon.
    newPassword.setVisible(false); // Hide the masked password field.
  }

  /**
   * Toggles the UI to mask the password.
   *
   * @param mouseEvent The MouseEvent triggered when the "eye closed" icon is clicked.
   */
  public void openClickedOnActionNew(MouseEvent mouseEvent) {
    textShowNewPassword.setVisible(false); // Hide the plain text password field.
    iconOpenEyeNew.setVisible(false); // Hide the "eye open" icon.
    iconCloseEyeNew.setVisible(true); // Display the "eye closed" icon.
    newPassword.setVisible(true); // Show the masked password field.
  }


  private String password_again;

  /**
   * Updates the plain text password field when a key is pressed in the masked password field.
   *
   * @param keyEvent The KeyEvent triggered when a key is pressed.
   */
  public void hidePasswordOnActionNew2(KeyEvent keyEvent) {
    password_again = newPassword2.getText(); // Retrieve the current password.
    textShowNewPassword2.setText(password_again); // Update the plain text field with the password.
  }

  /**
   * Updates the masked password field when a key is pressed in the plain text password field.
   *
   * @param keyEvent The KeyEvent triggered when a key is pressed.
   */
  public void showPasswordOnActionNew2(KeyEvent keyEvent) {
    password_again = textShowNewPassword2.getText(); // Retrieve the password from the plain text field.
    newPassword2.setText(password_again); // Update the masked field with the password.
  }

  /**
   * Toggles the UI to show the password in plain text.
   *
   * @param mouseEvent The MouseEvent triggered when the "eye open" icon is clicked.
   */
  public void closeClickedOnActionNew2(MouseEvent mouseEvent) {
    textShowNewPassword2.setVisible(true); // Show the plain text password field.
    iconOpenEyeNew2.setVisible(true); // Display the "eye open" icon.
    iconCloseEyeNew2.setVisible(false); // Hide the "eye closed" icon.
    newPassword2.setVisible(false); // Hide the masked password field.
  }

  /**
   * Toggles the UI to mask the password.
   *
   * @param mouseEvent The MouseEvent triggered when the "eye closed" icon is clicked.
   */
  public void openClickedOnActionNew2(MouseEvent mouseEvent) {
    textShowNewPassword2.setVisible(false); // Hide the plain text password field.
    iconOpenEyeNew2.setVisible(false); // Hide the "eye open" icon.
    iconCloseEyeNew2.setVisible(true); // Display the "eye closed" icon.
    newPassword2.setVisible(true); // Show the masked password field.
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

