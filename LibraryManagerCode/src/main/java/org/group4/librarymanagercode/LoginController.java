package org.group4.librarymanagercode;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;


public class LoginController {

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Button loginButton;



  @FXML
  private void handleLoginButton() {
    String username = usernameField.getText().trim();
    String password = passwordField.getText();

    if (username.isEmpty() || password.isEmpty()) {
      showAlert("Failing Login", "Retype Account and password!");
      return;
    }

    if (authenticate(username, password)) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPane.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Library Manager");
        stage.show();
      } catch (IOException e) {
        // Replace printStackTrace() with logging or user feedback.
        showAlert("Error", "Cannot load page...");
        // Example: log the error using a logger (e.g., java.util.logging.Logger)
        // Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Page load error", e);
      }
    } else {
      showAlert("Failing Login", "Wrong Account or Password!");
    }
  }


  private boolean authenticate(String userName, String password) {
    String userName1 = "admin";
    String password1 = "password";
    return userName1.equals(userName) && password1.equals(password);
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
