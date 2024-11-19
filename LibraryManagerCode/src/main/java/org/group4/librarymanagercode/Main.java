package org.group4.librarymanagercode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group4.module.services.AutoNotificationService;

import java.io.IOException;

public class Main extends Application {

  private AutoNotificationService notificationService;

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 700, 550);
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();

    notificationService = new AutoNotificationService();
    notificationService.start();
  }

  @Override
  public void stop() throws Exception {
    if (notificationService != null) {
      notificationService.shutdown();
    }
    super.stop();
  }

  public static void main(String[] args) {
    launch();
  }
}
