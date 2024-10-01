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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FirstPageController {

  @FXML
  private Button signUpButton;

  @FXML
  private Button loginButton;

  @FXML
  private void LoginAction(ActionEvent event) throws IOException {
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

  @FXML
  private void SignUPAction(ActionEvent event) throws IOException {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
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

  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}

