package org.group4.librarymanagercode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {

  @Override
  public void start(@NotNull Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BookView.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
  }


  public static void main(String[] args) {
    launch();
  }

}