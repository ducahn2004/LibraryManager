package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.base.entities.Book;
import org.group4.base.entities.Author;

public class BookViewController {

  public JFXButton homeButton;
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
  private ContextMenu contextMenu;

  @FXML
  private TextField searchField;

  private ObservableList<Book> bookList = FXCollections.observableArrayList();

  // This method is called by the FXMLLoader when initialization is complete
  @FXML
  public void initialize() {
    // Initialize columns
    ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
    bookName.setCellValueFactory(new PropertyValueFactory<>("Title"));
    bookSubject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
    bookPublisher.setCellValueFactory(new PropertyValueFactory<>("Publisher"));
    bookLanguage.setCellValueFactory(new PropertyValueFactory<>("Language"));
    numberOfPages.setCellValueFactory(new PropertyValueFactory<>("Pages"));
    bookAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));

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
    List<Author> authors1 = new ArrayList<>();
    authors1.add(new Author("Author One"));

    List<Author> authors2 = new ArrayList<>();
    authors2.add(new Author("Author Two"));

    List<Author> authors3 = new ArrayList<>();
    authors3.add(new Author("Author Three"));

    // This would normally be loaded from a database or some service
    bookList.add(new Book("510251", "Book Title 1", "Subject 1", "Publisher 1", "English", 200, authors1));
    bookList.add(new Book("496717", "Book Title 2", "Subject 2", "Publisher 2", "English", 300, authors2));
    bookList.add(new Book("111735", "Book Title 3", "Subject 3", "Publisher 3", "English", 150, authors3));

    tableView.setItems(bookList);
  }

  private void openBookDetails(Book selectedBook) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin/BookDetails.fxml"));
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
    // Implement search functionality here
  }
}
