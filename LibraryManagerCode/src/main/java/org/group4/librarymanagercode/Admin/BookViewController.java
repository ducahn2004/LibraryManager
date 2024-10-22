package org.group4.librarymanagercode.Admin;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.group4.base.books.BookItem;
import org.group4.base.entities.Book;
import org.group4.base.entities.Author;
import org.group4.base.books.BookInfor;

public class BookViewController {

  @FXML
  private TableView<BookInfor> tableView;

  @FXML
  private TableColumn<BookInfor, Boolean> Check;

  @FXML
  private TableColumn<BookInfor, String> ISBN;

  @FXML
  private TableColumn<BookInfor, String> bookName;

  @FXML
  private TableColumn<BookInfor, String> bookSubject;

  @FXML
  private TableColumn<BookInfor, String> bookPublisher;

  @FXML
  private TableColumn<BookInfor, String> bookLanguage;

  @FXML
  private TableColumn<BookInfor, Integer> numberOfPages;

  @FXML
  private TableColumn<BookInfor, String> bookAuthor;

  @FXML
  private TableColumn<BookInfor, String> bookBarcode;

  @FXML
  private TableColumn<BookInfor, Boolean> bookReferenceOnly;

  @FXML
  private TableColumn<BookInfor, Integer> bookTotal;

  @FXML
  private TableColumn<BookInfor, Integer> bookAvaiblability;

  @FXML
  private TableColumn<BookInfor, String> RackNumber;

  @FXML
  private TableColumn<BookInfor, String> bookCatalog;

  @FXML
  private ContextMenu contextMenu;

  @FXML
  private TableColumn<Book, Void> actionsColumn;

  @FXML
  private TextField searchField;


  private ObservableList<BookInfor> bookList = FXCollections.observableArrayList();

  // This method is called by the FXMLLoader when initialization is complete
  @FXML
  public void initialize() {
    // Initialize columns
    Check.setCellValueFactory(new PropertyValueFactory<>("checked"));
    ISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
    bookName.setCellValueFactory(new PropertyValueFactory<>("title"));
    bookSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
    bookPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    bookLanguage.setCellValueFactory(new PropertyValueFactory<>("language"));
    numberOfPages.setCellValueFactory(new PropertyValueFactory<>("pages"));
    bookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
    bookBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
    bookReferenceOnly.setCellValueFactory(new PropertyValueFactory<>("referenceOnly"));
    bookTotal.setCellValueFactory(new PropertyValueFactory<>("totalCopies"));
    bookAvaiblability.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
    RackNumber.setCellValueFactory(new PropertyValueFactory<>("rackNumber"));
    bookCatalog.setCellValueFactory(new PropertyValueFactory<>("catalog"));

    // Add data to the table (sample data can be fetched from service or database)
    loadBookData();
  }

  private void loadBookData() {

    List<Author> authors = new ArrayList<>();
    authors.add(new Author("Author Name","123", new ArrayList<>()));
    // This would normally be loaded from a database or some service
    bookList.add(new BookInfor("123456789", "Book Title", "Subject", "Publisher", "English", 200, authors, "1231", 1, "West"));
    tableView.setItems(bookList);
  }




  public void onSearchBook(ActionEvent actionEvent) {
    String searchText = searchField.getText().trim().toLowerCase();

    if (searchText.isEmpty()) {
      // Hiển thị tất cả sách nếu ô tìm kiếm trống
      tableView.setItems(bookList);
    } else {
      // Tìm kiếm theo ISBN hoặc tên sách
      ObservableList<BookInfor> filteredList = bookList.filtered(bookInfo ->
          bookInfo.getTitle().toLowerCase().contains(searchText) ||
              bookInfo.getISBN().toLowerCase().contains(searchText));
      tableView.setItems(filteredList);
    }
  }


}
