package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.group4.module.books.BookItem;
import org.group4.module.notifications.EmailNotification;
import org.group4.module.notifications.Notification;
import org.group4.database.BookDatabase;
import org.group4.database.BookItemDatabase;
import org.group4.database.NotificationDatabase;

public class NotificationController {

  private final ObservableList<Notification> notificationObservableList = FXCollections.observableArrayList();

  @FXML
  private TableView<Notification> systemTable;
  @FXML
  private TableColumn<Notification, String> systemDate;
  @FXML
  private TableColumn<Notification, String> systemTime;
  @FXML
  private TableColumn<Notification, String> systemType;
  @FXML
  private TableColumn<Notification, String> systemContent;
  @FXML
  private TableView<EmailNotification> emailTable;
  @FXML
  private TableColumn<EmailNotification, String> emailDate;
  @FXML
  private TableColumn<EmailNotification, String> emailTime;
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

  private void initialize() {
    initializeSystemTable();
    initializeEmailTable();
  }

  public void initializeSystemTable() {
    systemDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(
            cellData.getValue().getCreatedOn().toLocalDate().toString()));
    systemTime.setCellValueFactory(
        cellData -> new SimpleStringProperty(
            cellData.getValue().getCreatedOn().toLocalTime().toString()));
    systemType.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
    systemContent.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getContent()));
    loadNotificationData();
  }

  public void initializeEmailTable() {
    emailDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(
            cellData.getValue().getCreatedOn().toLocalDate().toString()));
    emailTime.setCellValueFactory(
        cellData -> new SimpleStringProperty(
            cellData.getValue().getCreatedOn().toLocalTime().toString()));
    emailType.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
    emailContent.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getContent()));
    loadNotificationData();
  }

  private void loadNotificationData() {
    if (notificationObservableList.isEmpty()) {
      //notificationObservableList.addAll(NotificationDatabase.getInstance());
      systemTable.setItems(notificationObservableList);
    }
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

  public void NotificationAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Notification.fxml", "Library Manager");
  }

  public void SettingAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Setting.fxml", "Library Manager");
  }

  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }

  public void notificationAction(ActionEvent actionEvent) {
  }
}
