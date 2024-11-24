package org.group4.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class helps with switching between scenes in a JavaFX application.
 */
public class SceneSwitcher {

  /**
   * Switches the scene to the provided FXML file and sets the window title.
   *
   * @param stage        the current stage (window)
   * @param fxmlFileName the name of the FXML file to load
   * @param title        the title for the new scene window
   * @param width        the width of the new scene window
   * @param height       the height of the new scene window
   */
  public static void switchScene(Stage stage, String fxmlFileName, String title, double width,
      double height) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFileName));
      Scene scene = new Scene(fxmlLoader.load(), width, height);

      stage.setTitle(title);
      stage.setScene(scene);
      stage.show();
      System.out.println(title + " scene switched");
    } catch (IOException e) {
      // Log the error to the console
      System.err.println("Error loading FXML file: " + fxmlFileName);
      e.printStackTrace();

      // Show an alert dialog to the user
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Scene Load Error");
      alert.setContentText(
          "Failed to load the scene: " + fxmlFileName + "\nPlease contact support.");
      alert.showAndWait();
    }
  }

  /**
   * A convenience method for switching to a scene with a default size of 1000x700.
   *
   * @param stage        the current stage (window)
   * @param fxmlFileName the name of the FXML file to load
   * @param title        the title for the new scene window
   */
  public static void switchScene(Stage stage, String fxmlFileName, String title) {
    switchScene(stage, fxmlFileName, title, 1000, 700);
  }
}
