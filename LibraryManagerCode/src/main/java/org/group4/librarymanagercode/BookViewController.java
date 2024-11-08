package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.group4.base.books.Book;
import org.group4.database.BookDatabase;

public class BookViewController {

  private final ObservableList<Book> bookList = FXCollections.observableArrayList();

  private Stage stage;

  @FXML
  private JFXButton homeButton;
  public Button addBookButton;
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
  private TextField searchField;

  @FXML
  public void initialize() {
    // Initialize columns
    ISBN.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getISBN()));
    bookName.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getTitle()));
    bookSubject.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getSubject()));
    bookPublisher.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getPublisher()));
    bookLanguage.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getLanguage()));
    numberOfPages.setCellValueFactory(cellData ->
        new SimpleObjectProperty<>(cellData.getValue().getNumberOfPages()));
    bookAuthor.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getAuthors().iterator().next().getName()));

    // Add data to the table
    loadBookData();

    // Add row click event listener
    tableView.setRowFactory(tv -> {
      TableRow<Book> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && !row.isEmpty()) {
          showDetailPage(row.getItem());
        }
      });
      return row;
    });

    // Add a listener for the search field
    searchField.textProperty()
        .addListener((observable, oldValue, newValue) -> filterBookList(newValue));
    searchField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        filterBookList(searchField.getText());
      }
    });
  }

  private void showDetailPage(Book book) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("BookDetails.fxml"));
      Stage detailStage = new Stage();
      detailStage.setScene(new Scene(loader.load()));

      BookDetailsController controller = loader.getController();
      controller.setItemDetail(book);

      detailStage.setTitle("Book Item Detail");
      detailStage.show();
    } catch (Exception e) {
      Logger.getLogger(BookViewController.class.getName())
          .log(Level.SEVERE, "Failed to load book details page", e);
    }
  }

  private void loadBookData() {
    if (bookList.isEmpty()) {
      bookList.addAll(BookDatabase.getInstance().getItems());
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

  public void onSearchBook(ActionEvent actionEvent) {
    filterBookList(searchField.getText());
  }

  @FXML
  private void HomeAction(ActionEvent event) {
    // Implement the action to be performed when the home button is clicked
    System.out.println("Home button clicked");
  }

  @FXML
  private void MemberAction(ActionEvent event) {
    // Implement the action to be performed when the member button is clicked
    System.out.println("Member button clicked");
  }

  @FXML
  private void BookAction(ActionEvent event) {
    // Implement the action to be performed when the book button is clicked
    System.out.println("Book button clicked");
  }

  @FXML
  private void ReturnBookAction(ActionEvent event) {
    // Implement the action to be performed when the return book button is clicked
    System.out.println("Return book button clicked");
  }

  @FXML
  private void notificationAction(ActionEvent event) {
    // Implement the action to be performed when the notification button is clicked
    System.out.println("Notification button clicked");
  }

  @FXML
  private void SettingAction(ActionEvent event) {
    // Implement the action to be performed when the setting button is clicked
    System.out.println("Setting button clicked");
  }

  @FXML
  private void Close(ActionEvent event) {
    // Implement the action to be performed when the close button is clicked
    System.out.println("Close button clicked");
  }

  
  @FXML
  private void addBookAction(ActionEvent event) {
    try {
      // Load the AddBook.fxml file
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddBook.fxml"));
      Parent root = fxmlLoader.load();

      // Get the current stage
      Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

      // Set the new scene on the current stage
      Scene scene = new Scene(root, 1000, 700);
      currentStage.setScene(scene);
      currentStage.setTitle("Add New Book");
      currentStage.show();
    } catch (IOException e) {
      e.printStackTrace();
      // Add better error handling or logging here
    }

    // Implement the action to be performed when the add book button is clicked
    System.out.println("Add book button clicked");
  }


}