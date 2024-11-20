package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.group4.dao.FactoryDAO;
import org.group4.module.transactions.BookLending;
import org.group4.module.users.Member;

import java.time.LocalDate;
import java.util.List;

public class BookLendingController {

  @FXML
  private TableView<BookLending> tableView;

  @FXML
  private TableColumn<BookLending, String> bookBarcode, bookISBN, bookTitle, memberID, memberName, Status, creationDate, dueDate, returnDate;

  @FXML
  private JFXButton homeButton, MemberButton, bookButton, bookLendingButton, settingButton, notificationButton, closeButton;

  @FXML
  private TextField searchField;

  private final ObservableList<BookLending> bookLendingsList = FXCollections.observableArrayList();

  @FXML
  public void initialize() {
    bookBarcode.setCellValueFactory(
        data -> new SimpleStringProperty(data.getValue().getBookItem().getBarcode()));
    bookISBN.setCellValueFactory(
        data -> new SimpleStringProperty(data.getValue().getBookItem().getISBN()));
    bookTitle.setCellValueFactory(
        data -> new SimpleStringProperty(data.getValue().getBookItem().getTitle()));
    memberID.setCellValueFactory(
        data -> new SimpleStringProperty(data.getValue().getMember().getMemberId()));
    memberName.setCellValueFactory(
        data -> new SimpleStringProperty(data.getValue().getMember().getName()));
    Status.setCellValueFactory(
        data -> new SimpleStringProperty(data.getValue().getBookItem().getStatus().toString()));
    creationDate.setCellValueFactory(
        data -> new SimpleStringProperty(data.getValue().getLendingDate().toString()));
    dueDate.setCellValueFactory(
        data -> new SimpleStringProperty(data.getValue().getDueDate().toString()));
    returnDate.setCellValueFactory(data -> new SimpleStringProperty(
        data.getValue().getReturnDate().isPresent() ? data.getValue().getReturnDate().toString()
            : "Not Returned"));

    loadBookLending();

    searchField.textProperty()
        .addListener((observable, oldValue, newValue) -> filterBookLendingList(newValue));
    searchField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        filterBookLendingList(searchField.getText());
      }
    });
  }

  private void loadBookLending() {
    List<BookLending> bookLendings = FactoryDAO.getBookLendingDAO().getAll();
    if (bookLendings == null || bookLendings.isEmpty()) {
      showAlert(Alert.AlertType.WARNING, "No Data Found",
          "No book lending data found in the database.");
      return;
    }

    bookLendingsList.setAll(bookLendings);
    tableView.setItems(bookLendingsList);
  }

  private void filterBookLendingList(String searchText) {
    if (searchText == null || searchText.isEmpty()) {
      tableView.setItems(bookLendingsList);
      return;
    }

    String lowerCaseFilter = searchText.toLowerCase();
    ObservableList<BookLending> filteredList = bookLendingsList.filtered(bookLending ->
        bookLending.getBookItem().getISBN().toLowerCase().contains(lowerCaseFilter) ||
            bookLending.getBookItem().getBarcode().toLowerCase().contains(lowerCaseFilter) ||
            bookLending.getBookItem().getTitle().toLowerCase().contains(lowerCaseFilter) ||
            bookLending.getMember().getMemberId().toLowerCase().contains(lowerCaseFilter) ||
            bookLending.getMember().getName().toLowerCase().contains(lowerCaseFilter)
    );
    tableView.setItems(filteredList);
  }

  private void showAlert(Alert.AlertType type, String title, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  @FXML
  public void HomeAction(ActionEvent event) throws IOException {
    switchScene("AdminPane.fxml", "Library Manager");
  }

  @FXML
  public void MemberAction(ActionEvent event) throws IOException {
    switchScene("MemberView.fxml", "Library Manager");
  }

  @FXML
  public void BookAction(ActionEvent event) throws IOException {
    switchScene("BookView.fxml", "Library Manager");
  }

  @FXML
  public void BookLendingAction(ActionEvent event) throws IOException {
    switchScene("BookLending.fxml", "Library Manager");
  }

  @FXML
  public void notificationAction(ActionEvent event) throws IOException {
    switchScene("Notification.fxml", "Library Manager");
  }

  @FXML
  public void SettingAction(ActionEvent event) throws IOException {
    switchScene("Setting.fxml", "Library Manager");
  }

  @FXML
  public void Close(ActionEvent event) {
    Platform.exit();
  }

  private void switchScene(String fxmlFile, String title) throws IOException {
    // Chuyển đổi cảnh
    Stage stage = (Stage) homeButton.getScene().getWindow();
    SceneSwitcher.switchScene(stage, fxmlFile, title);
  }
}
