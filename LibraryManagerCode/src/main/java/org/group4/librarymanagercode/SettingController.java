package org.group4.librarymanagercode;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SettingController {




  private Stage stage;

  public void HomeAction(ActionEvent actionEvent) throws IOException {
    loadPage("AdminPane.fxml");
  }

  public void MemberAction(ActionEvent actionEvent) throws IOException {
    loadPage("MemberView.fxml");
  }

  public void BookAction(ActionEvent actionEvent) throws IOException {
    loadPage("BookView.fxml");
  }

  public void AddBookAction(ActionEvent actionEvent) throws IOException {
    loadPage("AddBook.fxml");
  }

  public void AddMemberAction(ActionEvent actionEvent) throws IOException {
    loadPage("");
  }

  public void ReturnBookAction(ActionEvent actionEvent) throws IOException {
    loadPage("ReturnBook.fxml");
  }

  public void SettingAction(ActionEvent actionEvent) throws IOException {
    loadPage("Settings.fxml");
  }

  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }

  private void loadPage(String fxmlFile) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
    Scene scene = new Scene(fxmlLoader.load(), 1000 , 700);
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
  }

  public void notificationAction(ActionEvent actionEvent) throws IOException {
    loadPage("Notification.fxml");
  }
}
