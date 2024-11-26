package org.group4.controller;

import com.jfoenix.controls.JFXButton;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.group4.dao.base.FactoryDAO;
import org.group4.service.user.SessionManager;
import org.group4.model.notification.EmailNotification;
import org.group4.model.notification.Notification;
import org.group4.model.notification.SystemNotification;
import org.group4.model.user.Librarian;

public class NotificationController {

  // ObservableLists to store notifications for system and email notifications
  private final ObservableList<Notification> notificationObservableList = FXCollections.observableArrayList();

  // FXML elements for system notifications
  @FXML
  private TableView<SystemNotification> systemTable;

  @FXML
  private TableColumn<SystemNotification, String> systemDate;

  @FXML
  private TableColumn<SystemNotification, String> systemType;

  @FXML
  private TableColumn<SystemNotification, String> systemContent;

  // FXML elements for email notifications
  @FXML
  private TableView<EmailNotification> emailTable;

  @FXML
  private TableColumn<EmailNotification, String> emailDate;

  @FXML
  private TableColumn<EmailNotification, String> emailType;

  @FXML
  private TableColumn<EmailNotification, String> emailContent;

  // Navigation buttons
  @FXML
  private JFXButton homeButton;

  // ObservableLists for system and email notifications
  private final ObservableList<SystemNotification> systemNotificationObservableList
      = FXCollections.observableArrayList();
  private final ObservableList<EmailNotification> emailNotificationObservableList
      = FXCollections.observableArrayList();

  // Current librarian session
  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();

  /**
   * Initializes the controller by setting up the table columns and loading notifications.
   */
  @FXML
  public void initialize() {
    // Set up system notifications table columns
    systemDate.setCellValueFactory(cellData
        -> new SimpleStringProperty(cellData.getValue().getCreatedOn().toString()));
    systemType.setCellValueFactory(cellData
            -> new SimpleStringProperty(cellData.getValue().getType().toString()));
    systemContent.setCellValueFactory(cellData
            -> new SimpleStringProperty(cellData.getValue().getContent()));

    // Load system notifications into the table
    loadSystemNotification();
    systemTable.setItems(systemNotificationObservableList);

    // Set up email notifications table columns
    emailDate.setCellValueFactory(cellData
        -> new SimpleStringProperty(cellData.getValue().getCreatedOn().toString()));
    emailType.setCellValueFactory(cellData
            -> new SimpleStringProperty(cellData.getValue().getType().toString()));
    emailContent.setCellValueFactory(cellData
        -> new SimpleStringProperty(cellData.getValue().getContent()));

    // Load email notifications into the table
    loadEmailNotification();
    emailTable.setItems(emailNotificationObservableList);
  }

  /**
   * Loads system notifications from the database asynchronously.
   */
  private void loadSystemNotification() {
    systemNotificationObservableList.clear();
    Task<ObservableList<SystemNotification>> loadTask = new Task<>() {
      @Override
      protected ObservableList<SystemNotification> call() throws SQLException {
        return FXCollections.observableArrayList(librarian.getNotificationManager()
            .getAllSystemNotifications());
      }
    };

    // When task completes, update the observable list with the loaded data
    loadTask.setOnSucceeded(event
        -> systemNotificationObservableList.setAll(loadTask.getValue()));

    // If task fails, show an error alert
    loadTask.setOnFailed(event
        -> showAlert(AlertType.ERROR, "Error Loading System Notifications",
        "An error occurred while loading System Notification data."));

    // Start the task in a new thread
    new Thread(loadTask).start();
  }

  /**
   * Loads email notifications from the database asynchronously.
   */
  private void loadEmailNotification() {
    emailNotificationObservableList.clear();
    Task<ObservableList<EmailNotification>> loadTask = new Task<>() {
      @Override
      protected ObservableList<EmailNotification> call() throws SQLException {
        return FXCollections.observableArrayList(librarian.getNotificationManager()
            .getAllEmailNotifications());
      }
    };

    // When task completes, update the observable list with the loaded data
    loadTask.setOnSucceeded(event
        -> emailNotificationObservableList.setAll(loadTask.getValue()));

    // If task fails, show an error alert
    loadTask.setOnFailed(event
        -> showAlert(AlertType.ERROR, "Error Loading Email Notifications",
        "An error occurred while loading email Notification data."));

    // Start the task in a new thread
    new Thread(loadTask).start();
  }

  /**
   * Displays an alert with the specified type, title, and content.
   *
   * @param type    The type of alert (e.g., ERROR, INFORMATION).
   * @param title   The title of the alert window.
   * @param content The content message of the alert.
   */
  void showAlert(AlertType type, String title, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  /**
   * Returns the current stage of the view.
   *
   * @return The current Stage object.
   */
  private Stage getStage() {
    return (Stage) homeButton.getScene().getWindow();
  }

// Navigation actions for switching between different views

  public void HomeAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "AdminPane.fxml", "Library Manager");
  }

  public void MemberAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "MemberView.fxml", "Library Manager");
  }

  public void BookAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "BookView.fxml", "Library Manager");
  }

  public void BookLendingAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "BookLending.fxml", "Library Manager");
  }

  public void notificationAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "Notification.fxml", "Library Manager");
  }

  public void SettingAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "Setting.fxml", "Library Manager");
  }

  /**
   * Closes the application.
   *
   * @param actionEvent The event triggered by clicking the close button.
   */
  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }
}
