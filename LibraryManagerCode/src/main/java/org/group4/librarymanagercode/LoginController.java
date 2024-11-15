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
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.group4.dao.FactoryDAO;
import org.group4.module.services.AccountService;
import org.group4.module.sessions.SessionManager;
import org.group4.module.users.Librarian;

public class LoginController {

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Button loginButton;
  AccountService accountService = new AccountService();
  private static final Logger logger = Logger.getLogger(LoginController.class.getName());
  @FXML
  private void initialize() {
    // Set an event handler on the password field to listen for the Enter key
    passwordField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        try {
          handleLoginButton();  // Call the login method
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    });
  }
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
      SessionManager.getInstance().setCurrentLibrarian(librarian.get());
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

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}