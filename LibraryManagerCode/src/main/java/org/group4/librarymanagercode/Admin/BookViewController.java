package org.group4.librarymanagercode.Admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.base.entities.Book;
import org.group4.base.entities.Author;

public class BookViewController {

  @FXML
  private TableView<Book> tableView;

  @FXML
  private TableColumn<Book, Boolean> Check;

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
  private ContextMenu contextMenu;

  @FXML
  private TableColumn<Book, Void> actionsColumn;

  @FXML
  private TextField searchField;


  private ObservableList<Book> bookList = FXCollections.observableArrayList();

  // This method is called by the FXMLLoader when initialization is complete
  @FXML
  public void initialize() {
    // Initialize columns
    ISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
    bookName.setCellValueFactory(new PropertyValueFactory<>("title"));
    bookSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
    bookPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    bookLanguage.setCellValueFactory(new PropertyValueFactory<>("language"));
    numberOfPages.setCellValueFactory(new PropertyValueFactory<>("pages"));
    bookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
    // Add data to the table (sample data can be fetched from service or database)
    loadBookData();
    // Add row click event listener
    tableView.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) { // Check for double-click
        Book selectedBook = tableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
          openBookDetails(selectedBook);
        }
      }
    });
  }

  private void loadBookData() {

    List<Author> authors = new ArrayList<>();
    authors.add(new Author("Author Name","123", new ArrayList<>()));
    // This would normally be loaded from a database or some service
    bookList.add(new Book("123456789", "Book Title", "Subject", "Publisher", "English", 200, authors));
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


  public void onSearchBook(ActionEvent actionEvent) {
  }
}
