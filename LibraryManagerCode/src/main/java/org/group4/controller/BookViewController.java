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
import org.group4.dao.FactoryDAO;
import org.group4.model.books.Book;
import org.group4.model.manager.SessionManager;
import org.group4.model.users.Librarian;

/**
 * Controller for managing book-related operations in the application.
 */
public class BookViewController {

  // FXML injected fields for UI components
  @FXML
  private JFXButton bookLendingButton;
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

  @FXML
  private TableView<Book> tableView;
  @FXML
  private TableColumn<Book, String> ISBN;
  @FXML
  private TableColumn<Book, String> bookName;
  @FXML
  private TableColumn<Book, String> bookSubject;
  @FXML
  private TableColumn<Book, String> bookPublisher;
  @FXML
  private TableColumn<Book, String> bookLanguage;
  @FXML
  private TableColumn<Book, Integer> numberOfPages;
  @FXML
  private TableColumn<Book, String> bookAuthor;
  @FXML
  private TableColumn<Book, Void> actionColumn;

  @FXML
  private TextField searchField;
  @FXML
  private Button addBookButton;

  // Observable list for storing book data
  private final ObservableList<Book> bookList = FXCollections.observableArrayList();

  // Current librarian retrieved from session manager
  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();

  /**
   * Initializes the controller and sets up the UI components and their bindings.
   */
  @FXML
  public void initialize() {
    // Configure table column bindings
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

    // Configure action column with edit and delete options
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
          HBox hBox = new HBox(10, editLink, deleteLink);
          setGraphic(hBox);
        }
      }
    });

    // Load initial book data
    loadBookData();

    // Set up search functionality
    searchField.textProperty()
        .addListener((observable, oldValue, newValue) -> filterBookList(newValue));
    searchField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        filterBookList(searchField.getText());
      }
    });
  }

  /**
   * Loads book data from the database and populates the table view.
   */
  private void loadBookData() {
    List<Book> books = FactoryDAO.getBookDAO().getAll();

    if (books == null) {
      showAlert(AlertType.WARNING, "No Data Found", "No books were found in the database.");
      return;
    }

    if (bookList.isEmpty()) {
      bookList.addAll(books);
      tableView.setItems(bookList);
    }
  }

  /**
   * Filters the book list based on the search text.
   *
   * @param searchText The text used to filter the book list.
   */
  private void filterBookList(String searchText) {
    if (searchText == null || searchText.isEmpty()) {
      tableView.setItems(bookList);
    } else {
      String lowerCaseFilter = searchText.toLowerCase();
      ObservableList<Book> filteredList = bookList.filtered(book ->
          book.getISBN().toLowerCase().contains(lowerCaseFilter) ||
              book.getTitle().toLowerCase().contains(lowerCaseFilter)
      );
      tableView.setItems(filteredList);
    }
  }

  /**
   * Refreshes the book table by reloading data from the database.
   */
  public void refreshTable() {
    tableView.getItems().clear();
    bookList.clear();
    bookList.addAll(FactoryDAO.getBookDAO().getAll());
    tableView.setItems(bookList);
  }

  /**
   * Displays the details of a selected book in a new window.
   *
   * @param book The book whose details are to be displayed.
   */
  private void showBookDetail(Book book) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("BookDetails.fxml"));
      Parent root = loader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root));

      BookDetailsController controller = loader.getController();
      controller.setItemDetail(book);

      stage.setTitle("Book Detail");
      stage.show();
    } catch (IOException | SQLException e) {
      logAndShowError("Failed to load book detail form", e);
    }
  }

  /**
   * Logs an error and displays an alert to the user.
   *
   * @param message The error message.
   * @param e       The exception that occurred.
   */
  private void logAndShowError(String message, Exception e) {
    Logger.getLogger(BookViewController.class.getName()).log(Level.SEVERE, message, e);
    showAlert(AlertType.ERROR, "Error", message);
  }

  /**
   * Displays the edit form for the specified book.
   *
   * @param book The book to be edited.
   */
  private void showEditForm(Book book) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("EditBook.fxml"));
      Stage editStage = new Stage();
      editStage.setScene(new Scene(loader.load()));

      // Pass book data and parent controller to the edit form
      EditBookController controller = loader.getController();
      controller.setBookData(book);
      controller.setParentController(this);

      editStage.setTitle("Edit Book");
      editStage.showAndWait();
    } catch (IOException e) {
      logAndShowError("Failed to load edit form page", e);
    }
  }

  /**
   * Displays a confirmation dialog for deleting the specified book.
   *
   * @param book The book to be deleted.
   */
  private void showDeleteConfirmation(Book book) {
    Alert alert = createAlert(
        AlertType.CONFIRMATION,
        "Delete Confirmation",
        "Are you sure you want to delete this book?",
        "ID: " + book.getISBN()
            + "\nName: " + book.getTitle()
            + "\nSubject: " + book.getSubject()
            + "\nLanguage: " + book.getLanguage()
            + "\nNumber Of Pages: " + book.getNumberOfPages()
            + "\nAuthor: " + book.authorsToString()
    );

    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        // Delete the book and update the table view
        if (librarian.getBookManager().delete(book.getISBN())) {
          System.out.println(
              "Book with ISBN: " + book.getISBN() + " has been deleted successfully.");
          bookList.remove(book);
        } else {
          System.out.println("Failed to delete book with ISBN: " + book.getISBN());
          showAlert(AlertType.ERROR, "Deletion Failed",
              "The book with the ISBN " + book.getISBN()
                  + " could not be deleted because it is still in use.");
        }
      }
    });
  }

  /**
   * Displays an alert with the specified type, title, and content.
   *
   * @param type    The type of alert to display.
   * @param title   The title of the alert dialog.
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
   * Creates a new alert dialog with the specified parameters.
   *
   * @param type    The type of alert.
   * @param title   The title of the alert.
   * @param header  The header text of the alert.
   * @param content The content message of the alert.
   * @return The created Alert object.
   */
  private Alert createAlert(AlertType type, String title, String header, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    return alert;
  }

  /**
   * Handles the action for adding a new book. Switches to the "AddBook.fxml" scene.
   *
   * @param actionEvent The event triggered by the "Add Book" button.
   * @throws IOException If the FXML file cannot be loaded.
   */
  @FXML
  public void addBookAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBook.fxml"));
    Parent root = loader.load();
    Stage stage = (Stage) addBookButton.getScene().getWindow();
    stage.getScene().setRoot(root);
  }

  /**
   * Handles the action for navigating to the home view.
   *
   * @param actionEvent The event triggered by the "Home" button.
   * @throws IOException If the FXML file cannot be loaded.
   */
  @FXML
  public void HomeAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "AdminPane.fxml", "Library Manager");
  }

  /**
   * Handles the action for navigating to the member view.
   *
   * @param actionEvent The event triggered by the "Member" button.
   * @throws IOException If the FXML file cannot be loaded.
   */
  @FXML
  public void MemberAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "MemberView.fxml", "Library Manager");
  }

  /**
   * Handles the action for navigating to the book view.
   *
   * @param actionEvent The event triggered by the "Book" button.
   * @throws IOException If the FXML file cannot be loaded.
   */
  @FXML
  public void BookAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "BookView.fxml", "Library Manager");
  }

  /**
   * Handles the action for navigating to the notification view.
   *
   * @param actionEvent The event triggered by the "Notification" button.
   * @throws IOException If the FXML file cannot be loaded.
   */
  @FXML
  public void notificationAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Notification.fxml", "Library Manager");
  }

  /**
   * Handles the action for navigating to the settings view.
   *
   * @param actionEvent The event triggered by the "Setting" button.
   * @throws IOException If the FXML file cannot be loaded.
   */
  @FXML
  public void SettingAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Setting.fxml", "Library Manager");
  }

  /**
   * Handles the action for navigating to the book lending view.
   *
   * @param actionEvent The event triggered by the "Book Lending" button.
   * @throws IOException If the FXML file cannot be loaded.
   */
  public void BookLendingAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "BookLending.fxml", "Library Manager");
  }

  /**
   * Exits the application.
   *
   * @param actionEvent The event triggered by the "Close" button.
   */
  @FXML
  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }

  /**
   * Retrieves the current stage.
   *
   * @return The current stage.
   */
  private Stage getStage() {
    return (Stage) homeButton.getScene().getWindow();
  }
}
