package org.group4.librarymanagercode;

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
import org.group4.module.users.Member;

public class NotificationController {

  private final ObservableList<Notification> notificationObservableList = FXCollections.observableArrayList();


  @FXML
  private TableView<SystemNotification> systemTable;
  @FXML
  private TableColumn<SystemNotification, String> systemDate;
  @FXML
  private TableColumn<SystemNotification, String> systemType;
  @FXML
  private TableColumn<SystemNotification, String> systemContent;
  @FXML
  private TableView<EmailNotification> emailTable;
  @FXML
  private TableColumn<EmailNotification, String> emailDate;
  @FXML
  private TableColumn<EmailNotification, String> emailType;
  @FXML
  private TableColumn<EmailNotification, String> emailContent;
  @FXML
  private JFXButton homeButton;
  @FXML
  private JFXButton MemberButton;
  @FXML
  private JFXButton bookButton;
  @FXML
  private JFXButton settingButton;
  @FXML
  private JFXButton notificationButton;
  @FXML
  private JFXButton closeButton;
  private final ObservableList<SystemNotification> systemNotificationObservableList = FXCollections.observableArrayList();
  private final ObservableList<EmailNotification> emailNotificationObservableList = FXCollections.observableArrayList();
  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();

  @FXML
  public void initialize() {
    systemDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getCreatedOn().toString()));
    systemType.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
    systemContent.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getContent()));
    loadSystemNotification();
    systemTable.setItems(systemNotificationObservableList);
    emailDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getCreatedOn().toString()));
    emailType.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
    emailContent.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getContent()));
    loadEmailNotification();
    emailTable.setItems(emailNotificationObservableList);

  }

  private void loadSystemNotification() {
    systemNotificationObservableList.clear();
    Task<ObservableList<SystemNotification>> loadTask = new Task<>() {
      @Override
      protected ObservableList<SystemNotification> call() {
        return FXCollections.observableArrayList(FactoryDAO.getSystemNotificationDAO().getAll());
      }
    };

    loadTask.setOnSucceeded(event -> systemNotificationObservableList.setAll(loadTask.getValue()));
    loadTask.setOnFailed(event -> showAlert(AlertType.ERROR, "Error Loading System Notifications",
        "An error occurred while loading System Notification data."));
    new Thread(loadTask).start();
  }

  private void loadEmailNotification() {
    emailNotificationObservableList.clear();
    Task<ObservableList<EmailNotification>> loadTask = new Task<>() {
      @Override
      protected ObservableList<EmailNotification> call() {
        return FXCollections.observableArrayList(FactoryDAO.getEmailNotificationDAO().getAll());
      }
    };

    loadTask.setOnSucceeded(event -> emailNotificationObservableList.setAll(loadTask.getValue()));
    loadTask.setOnFailed(event -> showAlert(AlertType.ERROR, "Error Loading Email Notifications",
        "An error occurred while loading email Notification data."));
    new Thread(loadTask).start();
  }

  void showAlert(AlertType type, String title, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  private Stage getStage() {
    return (Stage) homeButton.getScene().getWindow(); // Có thể sử dụng bất kỳ button nào
  }

  public void HomeAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "AdminPane.fxml", "Library Manager");
  }

  public void MemberAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "MemberView.fxml", "Library Manager");
  }

  public void BookAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "BookView.fxml", "Library Manager");
  }

  public void notificationAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Notification.fxml", "Library Manager");
  }

  public void SettingAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Setting.fxml", "Library Manager");
  }

  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }

}
