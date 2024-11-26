package org.group4.controller;

import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.group4.service.user.SessionManager;
import org.group4.service.user.AccountService;
import org.group4.model.user.Librarian;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the Login screen in the Library Manager application. Manages user authentication,
 * input validation, and navigation to the admin panel upon successful login.
 */
public class LoginController {

  @FXML
  private TextField textShowPassword; // Text field to display the password in plain text.

  @FXML
  private ImageView iconOpen_eye; // Icon for toggling password visibility (visible state).

  @FXML
  private ImageView iconClose_eye; // Icon for toggling password visibility (hidden state).

  @FXML
  private TextField usernameField; // Input field for the username.

  @FXML
  private PasswordField passwordField; // Input field for the password (masked).

  @FXML
  private Button loginButton; // Button to initiate the login process.

  private final AccountService accountService = new AccountService(); // Handles account-related logic.
  private static final Logger logger = LoggerFactory.getLogger(LoginController.class); // Logger instance.
  private String password; // Stores the current password value.

  @FXML
  private void initialize() {
    textShowPassword.setVisible(false); // Initially hide the plain text password field.
    iconClose_eye.setVisible(true); // Display the "eye closed" icon by default.
    iconOpen_eye.setVisible(false); // Hide the "eye open" icon by default.

    // Set event handler to trigger login when the Enter key is pressed.
    passwordField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        try {
          handleLoginButton();  // Try to handle the login action
        } catch (SQLException e) {
          // If an error occurs, show an alert with the error message
          showAlert(
          );
          // Optionally, log the error
          logger.error("Error during login process: {}", e.getMessage());
        }
      }
    });
  }

  /**
   * Updates the plain text password field when a key is pressed in the masked password field.
   *
   * @param keyEvent The KeyEvent triggered when a key is pressed.
   */
  public void hidePasswordOnAction(KeyEvent keyEvent) {
    password = passwordField.getText(); // Retrieve the current password.
    textShowPassword.setText(password); // Update the plain text field with the password.
  }

  /**
   * Updates the masked password field when a key is pressed in the plain text password field.
   *
   * @param keyEvent The KeyEvent triggered when a key is pressed.
   */
  public void showPasswordOnAction(KeyEvent keyEvent) {
    password = textShowPassword.getText(); // Retrieve the password from the plain text field.
    passwordField.setText(password); // Update the masked field with the password.
  }

  /**
   * Toggles the UI to show the password in plain text.
   *
   * @param mouseEvent The MouseEvent triggered when the "eye open" icon is clicked.
   */
  public void closeClickedOnAction(MouseEvent mouseEvent) {
    textShowPassword.setVisible(true); // Show the plain text password field.
    iconOpen_eye.setVisible(true); // Display the "eye open" icon.
    iconClose_eye.setVisible(false); // Hide the "eye closed" icon.
    passwordField.setVisible(false); // Hide the masked password field.
  }

  /**
   * Toggles the UI to mask the password.
   *
   * @param mouseEvent The MouseEvent triggered when the "eye closed" icon is clicked.
   */
  public void openClickedOnAction(MouseEvent mouseEvent) {
    textShowPassword.setVisible(false); // Hide the plain text password field.
    iconOpen_eye.setVisible(false); // Hide the "eye open" icon.
    iconClose_eye.setVisible(true); // Display the "eye closed" icon.
    passwordField.setVisible(true); // Show the masked password field.
  }

  /**
   * Handles the login action, authenticates the user, and navigates to the admin panel.
   *
   * @throws SQLException If there is an error during the login process.
   */
  @FXML
  private void handleLoginButton() throws SQLException {
    String username = usernameField.getText().trim(); // Retrieve and trim the username.
    String password = passwordField.getText(); // Retrieve the password.

    logger.info("Attempting to log in with username: {}", username);

    // Validate that username and password fields are not empty.
    if (username.isEmpty() || password.isEmpty()) {
      showAlert("Login Failed", "Please enter both username and password!");
      return;
    }

    // Authenticate the user using AccountService.
    if (accountService.login(username, password)) {
      Optional<Librarian> librarian = accountService.getLibrarian(username);

      // If a librarian is found, set it in the session manager.
      if (librarian.isPresent()) {
        SessionManager.getInstance().setCurrentLibrarian(librarian.get());
      } else {
        logger.warn("Librarian not found in the database.");
        showAlert("Error", "Librarian not found in the database.");
        return;
      }

      try {
        // Load the admin panel view and navigate to it.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPane.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Library Manager");
        stage.centerOnScreen();
        stage.show();
      } catch (IOException e) {
        // Log and show an alert if the admin panel fails to load.
        logger.error("Failed to load the admin panel: {}", e.getMessage());
        showAlert("Error", "Unable to load the admin panel.");
      }
    } else {
      // Show an alert if the login credentials are incorrect.
      showAlert("Login Failed", "Incorrect username or password!");
    }
  }

  /**
   * Displays an alert dialog with the specified title and message.
   *
   * @param title   The title of the alert dialog.
   * @param message The message to display in the alert dialog.
   */
  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR); // Create an error alert.
    alert.setTitle(title); // Set the title of the alert.
    alert.setHeaderText(null); // No header text for this alert.
    alert.setContentText(message); // Set the content message.
    alert.showAndWait(); // Display the alert and wait for user response.
  }

  /**
   * Shows an alert to the user.
   */
  private void showAlert() {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Login Error");
    alert.setHeaderText(null);  // Optional: leave header empty
    alert.setContentText("An error occurred during the login process. Please try again.");
    alert.showAndWait();
  }
}
