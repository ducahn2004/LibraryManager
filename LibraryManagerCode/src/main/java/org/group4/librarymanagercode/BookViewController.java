package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import java.sql.SQLException;
import java.util.List;
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
import org.group4.module.books.Book;
import org.group4.module.manager.SessionManager;
import org.group4.module.users.Librarian;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookViewController {

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

  private final ObservableList<Book> bookList = FXCollections.observableArrayList();
  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();

  @FXML
  public void initialize() {
    // Khởi tạo các cột của bảng
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
          HBox hBox = new HBox(10, editLink, deleteLink);
          setGraphic(hBox);
        }
      }
    });

    loadBookData();
    tableView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        Book selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
          showBookDetail(selectedBook);
        }
      }
    });
    searchField.textProperty()
        .addListener((observable, oldValue, newValue) -> filterBookList(newValue));
    searchField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        filterBookList(searchField.getText());
      }
    });
  }

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

  public void refreshTable() {
    tableView.getItems().clear();
    bookList.clear();
    bookList.addAll(FactoryDAO.getBookDAO().getAll());
    tableView.setItems(bookList);
  }

  private void showBookDetail(Book book) {
    try {
      // Tạo FXMLLoader và mở cửa sổ chi tiết sách
      FXMLLoader loader = new FXMLLoader(getClass().getResource("BookDetails.fxml"));
      Parent root = loader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root));

      BookDetailsController controller = loader.getController();
      controller.setItemDetail(book);

      stage.setTitle("Book Detail");
      stage.show();
    } catch (IOException e) {
      logAndShowError("Failed to load book detail form", e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
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

      editStage.setTitle("Edit Member");
      editStage.showAndWait();
    } catch (IOException e) {
      logAndShowError("Failed to load edit form page", e);
    }
  }

  private void showDeleteConfirmation(Book book) {
    Alert alert = createAlert(AlertType.CONFIRMATION, "Delete Confirmation",
        "Are you sure you want to delete this book?",
        "ID: " + book.getISBN() + "\nName: " + book.getTitle() + "\nSubject" + book.getSubject()
            + "\nLanguage" + book.getLanguage() + "\nNumber Of Pages" + book.getNumberOfPages()
            + "\nAuthor" + book.authorsToString());
    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        bookList.remove(book);
        librarian.deleteBook(book.getISBN());
      }
    });
  }

  private void logAndShowError(String message, Exception e) {
    Logger.getLogger(MemberViewController.class.getName()).log(Level.SEVERE, message, e);
    showAlert(AlertType.ERROR, "Error", message);
  }

  /**
   * A single method for handling various alert types.
   *
   * @param type    The type of the alert.
   * @param title   The title of the alert.
   * @param content The content message of the alert.
   */
  void showAlert(AlertType type, String title, String content) {
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

  @FXML
  public void HomeAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "AdminPane.fxml", "Library Manager");
  }

  @FXML
  public void MemberAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "MemberView.fxml", "Library Manager");
  }

  @FXML
  public void BookAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "BookView.fxml", "Library Manager");
  }

  @FXML
  public void notificationAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Notification.fxml", "Library Manager");
  }

  @FXML
  public void SettingAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Setting.fxml", "Library Manager");
  }

  @FXML
  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }

  @FXML
  public void addBookAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBook.fxml"));
    Parent root = loader.load();
    Stage stage = (Stage) addBookButton.getScene().getWindow();
    stage.getScene().setRoot(root);
  }

  private Stage getStage() {
    return (Stage) homeButton.getScene().getWindow();
  }
}
