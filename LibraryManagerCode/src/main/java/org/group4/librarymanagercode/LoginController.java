package org.group4.librarymanagercode;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LoginController {

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Button loginButton;

  @FXML
  private Button signUpButton;

  private final String UserName = "admin";
  private final String Password = "password";

  @FXML
  private void handleLoginButton(ActionEvent event) throws IOException {
    String username = usernameField.getText().trim();
    String password = passwordField.getText();

    if (username.isEmpty() || password.isEmpty()) {
      showAlert(Alert.AlertType.ERROR, "Lỗi Đăng Nhập", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
      return;
    }

    // Xác thực người dùng từ UserStore
    if (authenticate(username,password)) {
      // Đăng nhập thành công, chuyển sang giao diện chính
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FirstPage.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) loginButton).getScene().getWindow();
        Scene scene = new Scene(root, 700, 550);
        stage.setScene(scene);
        stage.setTitle("Library Manager");
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "Không thể tải giao diện chính.");
      }
    } else {
      // Đăng nhập thất bại, hiển thị cảnh báo
      showAlert(Alert.AlertType.ERROR, "Lỗi Đăng Nhập", "Tên đăng nhập hoặc mật khẩu không chính xác!");
    }

  }

  private boolean authenticate(String userName, String password) {
    return UserName.equals(userName) && Password.equals(password);
  }

  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @FXML
  private void handleGoToSignUPAction(ActionEvent event) throws IOException {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMember.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root, 700, 550);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.setTitle("Library Manager");
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      showAlert(AlertType.ERROR, "Error", "Unable to load Sign Up page.");
    }
  }
}
