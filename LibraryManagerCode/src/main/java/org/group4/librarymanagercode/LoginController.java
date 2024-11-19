package org.group4.librarymanagercode;

import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;
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
import org.group4.dao.FactoryDAO;
import org.group4.module.manager.SessionManager;
import org.group4.module.services.AccountService;
import org.group4.module.users.Librarian;

/**
 * Controller for the Login screen in the Library Manager application.
 * Handles user input, authentication, and navigation to the admin panel upon successful login.
 */
public class LoginController {

  @FXML private TextField textShowPassword;
  @FXML private ImageView iconOpen_eye;
  @FXML private ImageView iconClose_eye;
  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private Button loginButton;

  private final AccountService accountService = new AccountService();
  private static final Logger logger = Logger.getLogger(LoginController.class.getName());
  private String password;

  /**
   * Initializes the controller.
   * Sets default visibility for password field elements and assigns event handlers.
   */
  @FXML
  private void initialize() {
    textShowPassword.setVisible(false);
    iconClose_eye.setVisible(true);
    iconOpen_eye.setVisible(false);

    // Set an event handler on the password field to listen for the Enter key
    passwordField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        try {
          handleLoginButton();
        } catch (SQLException e) {
          throw new RuntimeException("Error during login process", e);
        }
      }
    });
  }

  /**
   * Updates the visible password field when a key is pressed.
   *
   * @param keyEvent The KeyEvent triggered when a key is pressed.
   */
  public void hidePasswordOnAction(KeyEvent keyEvent) {
    password = passwordField.getText();
    textShowPassword.setText(password);
  }

  /**
   * Updates the masked password field when a key is pressed.
   *
   * @param keyEvent The KeyEvent triggered when a key is pressed.
   */
  public void showPasswordOnAction(KeyEvent keyEvent) {
    password = textShowPassword.getText();
    passwordField.setText(password);
  }

  /**
   * Toggles visibility to show the password in plain text.
   *
   * @param mouseEvent The MouseEvent triggered when the "eye open" icon is clicked.
   */
  public void closeClickedOnAction(MouseEvent mouseEvent) {
    textShowPassword.setVisible(true);
    iconOpen_eye.setVisible(true);
    iconClose_eye.setVisible(false);
    passwordField.setVisible(false);
  }

  /**
   * Toggles visibility to mask the password.
   *
   * @param mouseEvent The MouseEvent triggered when the "eye closed" icon is clicked.
   */
  public void openClickedOnAction(MouseEvent mouseEvent) {
    textShowPassword.setVisible(false);
    iconOpen_eye.setVisible(false);
    iconClose_eye.setVisible(true);
    passwordField.setVisible(true);
  }

  /**
   * Handles the login button action.
   * Authenticates the user and navigates to the admin panel if login is successful.
   *
   * @throws SQLException If there is an error during the login process.
   */
  @FXML
  private void handleLoginButton() throws SQLException {
    String username = usernameField.getText().trim();
    String password = passwordField.getText();

    logger.info("Login attempt with username: " + username);

    if (username.isEmpty() || password.isEmpty()) {
      showAlert("Login Failed", "Please enter both username and password!");
      return;
    }

    if (accountService.login(username, password)) {
      Optional<Librarian> librarian = FactoryDAO.getLibrarianDAO().getById(username);

      if (librarian.isPresent()) {
        SessionManager.getInstance().setCurrentLibrarian(librarian.get());
      } else {
        logger.warning("Librarian not found for username: " + username);
      }

      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPane.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Library Manager");
        stage.centerOnScreen();
        stage.show();
      } catch (IOException e) {
        logger.severe("Failed to load admin panel: " + e.getMessage());
        showAlert("Error", "Unable to load the admin panel.");
      }
    } else {
      showAlert("Login Failed", "Incorrect username or password!");
    }
  }

  /**
   * Displays an alert dialog with the specified title and message.
   *
   * @param title The title of the alert dialog.
   * @param message The message to display in the alert dialog.
   */
  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }


}
