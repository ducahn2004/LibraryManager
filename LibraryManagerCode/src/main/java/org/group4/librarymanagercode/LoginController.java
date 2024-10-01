package org.group4.librarymanagercode;

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

  private final String UserName = "admin";
  private final String Password = "password";

  @FXML
  private void handleLoginButton(ActionEvent event) throws IOException {
    String username = usernameField.getText();
    String password = passwordField.getText();

    if (authenticate(username, password)) {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Page2.fxml"));
      Parent root = loader.load();
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(new Scene(root));
      stage.setTitle("Page 2");
      stage.show();
    } else {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Wrong");
      alert.setHeaderText(null);
      alert.setContentText("Wrong Account or password");
      alert.showAndWait();
    }

  }

  private boolean authenticate(String userName, String password) {
    return UserName.equals(userName) && Password.equals(password);
  }
}
