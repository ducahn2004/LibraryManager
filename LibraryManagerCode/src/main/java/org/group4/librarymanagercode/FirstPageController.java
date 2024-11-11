package org.group4.librarymanagercode;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FirstPageController {

  @FXML
  private Button signUpButton;

  @FXML
  private Button loginButton;


  private void navigateToPage(ActionEvent event, String fxmlFile, String errorMessage) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      Parent root = loader.load();
      Scene scene = new Scene(root, 700, 550);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.setTitle("Library Manager");
      stage.show();
    } catch (IOException e) {
      Logger.getLogger(FirstPageController.class.getName()).severe(e.getMessage());
      showAlert(errorMessage);
    }
  }

  private void showAlert(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public void LoginAction(ActionEvent actionEvent) {
    navigateToPage(actionEvent, "AddMember.fxml", "Unable to load sign-up page.");
  }
}