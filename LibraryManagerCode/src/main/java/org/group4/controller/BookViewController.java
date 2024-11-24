package org.group4.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.group4.model.book.Book;
import org.group4.service.user.SessionManagerService;
import org.group4.model.user.Librarian;

public class BookViewController {

  @FXML
  private JFXButton bookLendingButton, homeButton, MemberButton, bookButton, settingButton, notificationButton, closeButton;
  @FXML
  private TableView<Book> tableView;
  @FXML
  private TableColumn<Book, String> ISBN, bookName, bookSubject, bookPublisher, bookLanguage, bookAuthor;
  @FXML
  private TableColumn<Book, Integer> numberOfPages;
  @FXML
  private TableColumn<Book, Void> actionColumn;
  @FXML
  private TextField searchField;
  @FXML
  private Button addBookButton;

  private final ObservableList<Book> bookList = FXCollections.observableArrayList();
  private final Librarian librarian = SessionManagerService.getInstance().getCurrentLibrarian();

  @FXML
  public void initialize() {
    try {
      setupTableColumns();
      loadBookData();
      setupSearchFunctionality();
    } catch (Exception e) {
      logAndShowError("Initialization failed", e);
    }
  }

  private void setupTableColumns() {
    ISBN.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getISBN()));
    bookName.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
    bookSubject.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getSubject()));
    bookPublisher.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
    bookLanguage.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getLanguage()));
    numberOfPages.setCellValueFactory(
        cellData -> new SimpleObjectProperty<>(cellData.getValue().getNumberOfPages()));
    bookAuthor.setCellValueFactory(cellData -> new SimpleStringProperty(
        cellData.getValue().getAuthors().iterator().next().getName()));

    actionColumn.setCellFactory(param -> new TableCell<>() {
      private final Hyperlink editLink = new Hyperlink("Edit");
      private final Hyperlink deleteLink = new Hyperlink("Delete");

      {
        editLink.setOnAction(event -> showEditForm(getTableView().getItems().get(getIndex())));
        deleteLink.setOnAction(
            event -> showDeleteConfirmation(getTableView().getItems().get(getIndex())));
      }

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setGraphic(null);
        } else {
          setGraphic(new HBox(10, editLink, deleteLink));
        }
      }
    });
  }

  private void loadBookData() {
    try {
      List<Book> books = librarian.getBookManager().getAll();
      if (books == null || books.isEmpty()) {
        showAlert(AlertType.WARNING, "No Data Found", "No books were found in the database.");
      } else {
        bookList.addAll(books);
        tableView.setItems(bookList);
      }
    } catch (SQLException e) {
      logAndShowError("Failed to load book data", e);
    }
  }

  private void setupSearchFunctionality() {
    searchField.textProperty()
        .addListener((observable, oldValue, newValue) -> filterBookList(newValue));
    searchField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        filterBookList(searchField.getText());
      }
    });
  }

  private void filterBookList(String searchText) {
    if (searchText == null || searchText.isEmpty()) {
      tableView.setItems(bookList);
    } else {
      String lowerCaseFilter = searchText.toLowerCase();
      ObservableList<Book> filteredList = bookList.filtered(book ->
          book.getISBN().toLowerCase().contains(lowerCaseFilter) ||
              book.getTitle().toLowerCase().contains(lowerCaseFilter));
      tableView.setItems(filteredList);
    }
  }

  public void refreshTable() {
    try {
      tableView.getItems().clear();
      bookList.clear();
      bookList.addAll(librarian.getBookManager().getAll());
      tableView.setItems(bookList);
    } catch (SQLException e) {
      logAndShowError("Failed to refresh table", e);
    }
  }

  private void showEditForm(Book book) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("EditBook.fxml"));
      Stage editStage = new Stage();
      editStage.setScene(new Scene(loader.load()));

      EditBookController controller = loader.getController();
      controller.setBookData(book);
      controller.setParentController(this);

      editStage.setTitle("Edit Book");
      editStage.showAndWait();
    } catch (IOException e) {
      logAndShowError("Failed to load edit form page", e);
    }
  }

  private void showDeleteConfirmation(Book book) {
    Alert alert = createAlert(AlertType.CONFIRMATION, "Delete Confirmation",
        "Are you sure you want to delete this book?",
        "Book: " + book.getTitle());
    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        try {
          if (librarian.getBookManager().delete(book.getISBN())) {
            bookList.remove(book);
          } else {
            showAlert(AlertType.ERROR, "Deletion Failed", "Failed to delete the book.");
          }
        } catch (SQLException e) {
          logAndShowError("Failed to delete the book", e);
        }
      }
    });
  }

  private void logAndShowError(String message, Exception e) {
    Logger.getLogger(BookViewController.class.getName()).log(Level.SEVERE, message, e);
    showAlert(AlertType.ERROR, "Error", message);
  }

  /**
   * Handles the action for adding a new book. Switches to the "AddBook.fxml" scene.
   *
   * @param actionEvent The event triggered by the "Add Book" button.
   * @throws IOException If the FXML file cannot be loaded.
   */
  @FXML
  public void addBookAction(ActionEvent actionEvent) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBook.fxml"));
      Parent root = loader.load();
      Stage stage = (Stage) addBookButton.getScene().getWindow();
      stage.getScene().setRoot(root);
    } catch (IOException e) {
      showAlert(
          Alert.AlertType.ERROR,
          "Error",
          "Failed to load Add Book View. Please try again."
      );
    }
  }

  private void showAlert(AlertType type, String title, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  private Alert createAlert(AlertType type, String title, String header, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    return alert;
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
