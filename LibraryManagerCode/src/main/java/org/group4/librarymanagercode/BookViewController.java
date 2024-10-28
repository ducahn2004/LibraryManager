package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.base.entities.Book;
import org.group4.base.entities.Author;
//import org.group4.base.books.Book;

public class BookViewController {

  private ObservableList<Book> bookList = FXCollections.observableArrayList();

  public JFXButton homeButton;
  @FXML
  private TableView<Book> tableView = new TableView<>(bookList);

  @FXML
  private TableColumn<Book, String> ISBN = new TableColumn<>("ISBN");

  @FXML
  private TableColumn<Book, String> bookName = new TableColumn<>("Name");

  @FXML
  private TableColumn<Book, String> bookSubject = new TableColumn<>("Subject");

  @FXML
  private TableColumn<Book, String> bookPublisher = new TableColumn<>("Publisher");

  @FXML
  private TableColumn<Book, String> bookLanguage = new TableColumn<>("Language");

  @FXML
  private TableColumn<Book, Integer> numberOfPages = new TableColumn<>("Pages");

  @FXML
  private TableColumn<Book, String> bookAuthor = new TableColumn<>("Author");

  @FXML
  private ContextMenu contextMenu;

  @FXML
  private TextField searchField;

  //  private ObservableList<Book> bookList = FXCollections.observableArrayList();

  // This method is called by the FXMLLoader when initialization is complete
  @FXML
  public void initialize() {

    // Initialize columns
    ISBN.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getISBN()));
    bookName.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
    bookSubject.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getSubject()));
    bookPublisher.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
    bookLanguage.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getLanguage()));
    numberOfPages.setCellValueFactory(cellData ->
        new SimpleObjectProperty<>(cellData.getValue().getNumberOfPages()));
    bookAuthor.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getAuthorsAsString()));
    tableView.getColumns()
        .addAll(ISBN, bookName, bookSubject, bookPublisher, bookLanguage, numberOfPages,
            bookAuthor);
    // Add data to the table (sample data can be fetched from service or database)
    loadBookData();
    // Add row click event listener
    tableView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) { // Double Click
        Book selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
          openBookDetails(selectedBook);
        }
      }
    });
    // Add a listener for the search field
    searchField.textProperty()
        .addListener((observable, oldValue, newValue) -> filterBookList(newValue));
  }

  private void loadBookData() {
    List<Author> authors1 = new ArrayList<>();
    authors1.add(new Author("Author One"));

    List<Author> authors2 = new ArrayList<>();
    authors2.add(new Author("Author Two"));

    List<Author> authors3 = new ArrayList<>();
    authors3.add(new Author("Author Three"));

    // This would normally be loaded from a database or some service
    bookList.add(
        new Book(
            "510251", "Book Title 1", "Subject 1", "Publisher 1", "English", 200,
            authors1));
    bookList.add(
        new Book(
            "496717", "Book Title 2", "Subject 2", "Publisher 2", "English", 300,
            authors2));
    bookList.add(
        new Book("111735", "Book Title 3", "Subject 3", "Publisher 3", "English", 150,
            authors3));
    tableView.setItems(bookList);
  }

  private void openBookDetails(Book selectedBook) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("BookDetails.fxml"));
      Parent root = loader.load();

      // Get the controller and pass the selected book
      BookDetailsController controller = loader.getController();
      controller.setBookDetails(selectedBook);

      // Show the book details in a new window
      Stage stage = new Stage();
      stage.setTitle("Book Details");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void filterBookList(String searchText) {
    ObservableList<Book> filteredList = FXCollections.observableArrayList();

    if (searchText == null || searchText.isEmpty()) {
      // Show the full list if no search text is provided
      tableView.setItems(bookList);
    } else {
      // Convert search text to lowercase for case-insensitive matching
      String lowerCaseFilter = searchText.toLowerCase();

      for (Book book : bookList) {
        // Match against title or ISBN
        if (book.getISBN().toLowerCase().contains(lowerCaseFilter) ||
            book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
          filteredList.add(book);
        }
      }
      tableView.setItems(filteredList);
    }
  }

  public void onSearchBook(ActionEvent actionEvent) {
    String searchText = searchField.getText();
    filterBookList(searchText);
  }
}
