package org.group4.controller;

import com.jfoenix.controls.JFXButton;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.group4.model.transaction.BookLending;
import java.util.List;
import org.group4.model.user.Librarian;
import org.group4.service.user.SessionManagerService;

/**
 * Controller class for managing book lending functionality in the library system.
 */
public class BookLendingController {

  // Librarian instance for managing book lending operations
  private static final Librarian librarian = SessionManagerService.getInstance()
      .getCurrentLibrarian();

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
   * Loads all book lending data from the database and displays it in the TableView.
   */
  private void loadBookLending() {
    try {
        List<BookLending> bookLendings = librarian.getLendingManager().getAll();
        if (bookLendings == null || bookLendings.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Data Found",
                "No book lending data found in the database.");
            return;
        }

        bookLendingsList.setAll(bookLendings);
        tableView.setItems(bookLendingsList);
    } catch (SQLException e) {
        showAlert(Alert.AlertType.ERROR, "Database Error",
            "An error occurred while fetching book lending data: " + e.getMessage());
    }
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
