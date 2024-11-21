package org.group4.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group4.module.services.AutoNotificationService;

import java.io.IOException;

/**
 * Main entry point for the Library Manager application. Initializes the primary stage and starts
 * background services.
 */
public class Main extends Application {

  // Service for automatically handling notifications.
  private AutoNotificationService notificationService;

  /**
   * Entry point for the JavaFX application. Sets up the main application window (stage) and starts
   * background notification services.
   *
   * @param stage The primary stage for the application.
   * @throws IOException If the FXML file cannot be loaded.
   */
  @Override
  public void start(Stage stage) throws IOException {
    // Load the Login.fxml file to set up the login scene.
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 700, 550);

    // Configure the stage (main window) for the application.
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();

    // Initialize and start the notification service.
    notificationService = new AutoNotificationService();
    notificationService.start();
  }

  /**
   * Called when the application is stopped. Ensures that background services are shut down properly
   * before exiting.
   *
   * @throws Exception If an error occurs during shutdown.
   */
  @Override
  public void stop() throws Exception {
    if (notificationService != null) {
      notificationService.shutdown(); // Gracefully shut down the notification service.
    }
    super.stop(); // Call the superclass stop method.
  }

  /**
   * The main method, serves as the application entry point. Launches the JavaFX application.
   *
   * @param args Command-line arguments passed to the application.
   */
  public static void main(String[] args) {
    launch(); // Launch the JavaFX application.
  }
}
