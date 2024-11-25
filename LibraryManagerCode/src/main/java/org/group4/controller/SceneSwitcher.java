package org.group4.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for switching scenes in the application.
 */
public class SceneSwitcher {

  private static final Logger logger = LoggerFactory.getLogger(SceneSwitcher.class);

  /**
   * Switches the scene to the provided FXML file and sets the window title, width, and height.
   *
   * @param stage        the current stage (window)
   * @param fxmlFileName the name of the FXML file to load
   * @param title        the title for the new scene window
   * @param width        the width of the new scene window
   * @param height       the height of the new scene window
   */
  public static void switchScene(Stage stage, String fxmlFileName, String title, double width,
      double height) {
    if (fxmlFileName == null || fxmlFileName.isEmpty()) {
      logger.error("FXML file name is null or empty");
      showError("Invalid FXML file name.", "The provided file name is null or empty.");
      return;
    }

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFileName));
      Scene scene = new Scene(fxmlLoader.load(), width, height);
      stage.setTitle(title);
      stage.setScene(scene);
      stage.show();
      logger.info("Switched to scene: {}", title);
    } catch (IOException e) {
      logger.error("Error loading FXML file: {}", fxmlFileName, e);
      showError("Scene Load Error",
          "Failed to load the scene: " + fxmlFileName + "\nPlease contact support.");
    }
  }

  /**
   * Switches the scene to the provided FXML file and sets the window title.
   *
   * @param stage        the current stage (window)
   * @param fxmlFileName the name of the FXML file to load
   * @param title        the title for the new scene window
   */
  public static void switchScene(Stage stage, String fxmlFileName, String title) {
    switchScene(stage, fxmlFileName, title, 1000, 700);
  }

  /**
   * Displays an error alert to the user with a custom header and message.
   *
   * @param header  the header text of the alert
   * @param message the content text of the alert
   */
  private static void showError(String header, String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(header);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
