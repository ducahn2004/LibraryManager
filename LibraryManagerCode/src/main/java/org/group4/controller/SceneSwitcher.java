package org.group4.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
   * @throws IOException if the FXML file cannot be loaded
   */
  public static void switchScene(Stage stage, String fxmlFileName, String title, double width,
      double height)
      throws IOException {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFileName));
      Scene scene = new Scene(fxmlLoader.load(), width, height);

      stage.setTitle(title);
      stage.setScene(scene);
      stage.show();
      System.out.println(title + " scene switched");
    } catch (IOException e) {
      System.err.println("Error loading FXML file: " + fxmlFileName);
      e.printStackTrace();
      throw e;  // rethrow the exception after logging it
    }
  }

  /**
   * A convenience method for switching to a scene with a default size of 1000x700.
   *
   * @param stage        the current stage (window)
   * @param fxmlFileName the name of the FXML file to load
   * @param title        the title for the new scene window
   * @throws IOException if the FXML file cannot be loaded
   */
  public static void switchScene(Stage stage, String fxmlFileName, String title)
      throws IOException {
    switchScene(stage, fxmlFileName, title, 1000, 700);
  }
}
