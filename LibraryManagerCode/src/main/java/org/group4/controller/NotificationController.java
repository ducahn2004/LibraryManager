package org.group4.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
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
import org.group4.dao.FactoryDAO;
import org.group4.module.manager.SessionManager;
import org.group4.module.notifications.EmailNotification;
import org.group4.module.notifications.Notification;
import org.group4.module.notifications.SystemNotification;
import org.group4.module.users.Librarian;

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
  @FXML
  private JFXButton MemberButton;
  @FXML
  private JFXButton bookButton;
  @FXML
  private JFXButton bookLendingButton;
  @FXML
  private JFXButton settingButton;
  @FXML
  private JFXButton notificationButton;
  @FXML
  private JFXButton closeButton;

  // ObservableLists for system and email notifications
  private final ObservableList<SystemNotification> systemNotificationObservableList = FXCollections.observableArrayList();
  private final ObservableList<EmailNotification> emailNotificationObservableList = FXCollections.observableArrayList();

  // Current librarian session
  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();

  /**
   * Initializes the controller by setting up the table columns and loading notifications.
   */
  @FXML
  public void initialize() {
    // Set up system notifications table columns
    systemDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getCreatedOn().toString()));
    systemType.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
    systemContent.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getContent()));

    // Load system notifications into the table
    loadSystemNotification();
    systemTable.setItems(systemNotificationObservableList);

    // Set up email notifications table columns
    emailDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getCreatedOn().toString()));
    emailType.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
    emailContent.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getContent()));

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
      protected ObservableList<SystemNotification> call() {
        return FXCollections.observableArrayList(FactoryDAO.getSystemNotificationDAO().getAll());
      }
    };

    // When task completes, update the observable list with the loaded data
    loadTask.setOnSucceeded(event -> systemNotificationObservableList.setAll(loadTask.getValue()));

    // If task fails, show an error alert
    loadTask.setOnFailed(event -> showAlert(AlertType.ERROR, "Error Loading System Notifications",
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
      protected ObservableList<EmailNotification> call() {
        return FXCollections.observableArrayList(FactoryDAO.getEmailNotificationDAO().getAll());
      }
    };

    // When task completes, update the observable list with the loaded data
    loadTask.setOnSucceeded(event -> emailNotificationObservableList.setAll(loadTask.getValue()));

    // If task fails, show an error alert
    loadTask.setOnFailed(event -> showAlert(AlertType.ERROR, "Error Loading Email Notifications",
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
   * Retrieves the current stage (window) from the home button's scene.
   *
   * @return The current Stage object.
   */
  private Stage getStage() {
    return (Stage) homeButton.getScene().getWindow();
  }

  /**
   * Handles the action to navigate to the home page.
   *
   * @param actionEvent The event triggered by clicking the home button.
   * @throws IOException If there is an issue loading the scene.
   */
  public void HomeAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "AdminPane.fxml", "Library Manager");
  }

  /**
   * Handles the action to navigate to the member page.
   *
   * @param actionEvent The event triggered by clicking the member button.
   * @throws IOException If there is an issue loading the scene.
   */
  public void MemberAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "MemberView.fxml", "Library Manager");
  }

  /**
   * Handles the action to navigate to the book page.
   *
   * @param actionEvent The event triggered by clicking the book button.
   * @throws IOException If there is an issue loading the scene.
   */
  public void BookAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "BookView.fxml", "Library Manager");
  }

  /**
   * Handles the action to navigate to the book lending page.
   *
   * @param actionEvent The event triggered by clicking the book lending button.
   * @throws IOException If there is an issue loading the scene.
   */
  public void BookLendingAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "BookLending.fxml", "Library Manager");
  }

  /**
   * Handles the action to navigate to the notification page.
   *
   * @param actionEvent The event triggered by clicking the notification button.
   * @throws IOException If there is an issue loading the scene.
   */
  public void notificationAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Notification.fxml", "Library Manager");
  }

  /**
   * Handles the action to navigate to the settings page.
   *
   * @param actionEvent The event triggered by clicking the setting button.
   * @throws IOException If there is an issue loading the scene.
   */
  public void SettingAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Setting.fxml", "Library Manager");
  }

  /**
   * Closes the application when the close button is clicked.
   *
   * @param actionEvent The event triggered by clicking the close button.
   */
  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }
}
