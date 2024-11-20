package org.group4.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

  public static void switchScene(Stage stage, String fxmlFileName, String title)
      throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFileName));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    stage.setTitle(title);
    stage.setScene(scene);
    stage.show();
    System.out.println(title + " scene switched");
  }

}
