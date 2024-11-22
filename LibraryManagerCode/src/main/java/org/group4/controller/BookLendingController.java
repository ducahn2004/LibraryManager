package org.group4.controller;

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
import org.group4.model.transactions.BookLending;

import java.util.List;

/**
 * Controller class for managing book lending functionality in the library system.
 */
public class BookLendingController {

  // TableView and columns for displaying book lending information
  @FXML
  private TableView<BookLending> tableView;
  @FXML
  private TableColumn<BookLending, String> bookBarcode, bookISBN, bookTitle, memberID,
      memberName, Status, creationDate, dueDate, returnDate;

  // Navigation and action buttons
  @FXML
  private JFXButton homeButton, MemberButton, bookButton, bookLendingButton, settingButton,
      notificationButton, closeButton;

  // Search field for filtering book lending data
  @FXML
  private TextField searchField;

  // Observable list for managing book lending data
  private final ObservableList<BookLending> bookLendingsList = FXCollections.observableArrayList();

  /**
   * Initializes the controller after the FXML file has been loaded.
   */
  @FXML
  public void initialize() {
    // Set cell value factories for the TableView columns
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
    returnDate.setCellValueFactory(
        data -> new SimpleStringProperty(
            data.getValue().getReturnDate().isPresent()
                ? data.getValue().getReturnDate().toString()
                : "Not Returned"));

    // Load book lending data into the TableView
    loadBookLending();

    // Add listener for filtering data based on search input
    searchField.textProperty()
        .addListener((observable, oldValue, newValue) -> filterBookLendingList(newValue));
    searchField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        filterBookLendingList(searchField.getText());
      }
    });
  }

  /**
   * Loads book lending data from the database and populates the TableView.
   */
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

  /**
   * Filters the book lending list based on the provided search text.
   *
   * @param searchText the search text to filter the list
   */
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

  /**
   * Displays an alert dialog with the specified type, title, and content.
   *
   * @param type    the type of alert (e.g., INFORMATION, WARNING)
   * @param title   the title of the alert dialog
   * @param content the content of the alert dialog
   */
  private void showAlert(Alert.AlertType type, String title, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  // Navigation methods for various sections of the application

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

  /**
   * Closes the application.
   *
   * @param event the action event triggering the close
   */
  @FXML
  public void Close(ActionEvent event) {
    Platform.exit();
  }

  /**
   * Switches the scene to the specified FXML file and updates the window title.
   *
   * @param fxmlFile the name of the FXML file to load
   * @param title    the title of the window
   * @throws IOException if the FXML file cannot be loaded
   */
  private void switchScene(String fxmlFile, String title) throws IOException {
    Stage stage = (Stage) homeButton.getScene().getWindow();
    SceneSwitcher.switchScene(stage, fxmlFile, title);
  }
}
