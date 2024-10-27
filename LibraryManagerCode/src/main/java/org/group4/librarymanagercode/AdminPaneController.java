package org.group4.librarymanagercode;

import javafx.application.Platform;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class AdminPaneController {

  @FXML
  public JFXButton homeButton;
  @FXML
  public JFXButton MemberButton;
  @FXML
  public JFXButton addMemberButton;
  @FXML
  public JFXButton bookButton;
  @FXML
  public JFXButton addBookButton;
  @FXML
  public JFXButton returnBookButton;
  @FXML
  public JFXButton settingButton;
  @FXML
  public JFXButton closeButton;

  private Stage stage;
  private BorderPane rootLayout;

  public void setStageAndSetupListeners(Stage stage, BorderPane rootLayout) {
    this.stage = stage;
    this.rootLayout = rootLayout;
  }

  public void HomeAction(ActionEvent actionEvent) {
    loadPage("AdminPane.fxml");
  }

  public void MemberAction(ActionEvent actionEvent) {
    loadPage("MemberView.fxml");
  }

  public void BookAction(ActionEvent actionEvent) {
    loadPage("BookView.fxml");
  }

  public void AddBookAction(ActionEvent actionEvent) {
    loadPage("AddBook.fxml");
  }

  public void AddMemberAction(ActionEvent actionEvent) {
    loadPage("AddMember.fxml");
  }

  public void ReturnBookAction(ActionEvent actionEvent) {
    loadPage("ReturnBook.fxml");
  }

  public void SettingAction(ActionEvent actionEvent) {
    loadPage("Settings.fxml");
  }

  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }

  private void loadPage(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      BorderPane pane = loader.load();
      rootLayout.setCenter(pane);
    } catch (IOException e) {
      e.printStackTrace();
      // Optionally, show an error alert dialog to the user here
    }
  }
}
