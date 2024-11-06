package org.group4.librarymanagercode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.group4.base.books.BookItem;
import org.group4.base.books.Book;
import org.group4.base.enums.BookFormat;
import org.group4.database.BookItemDatabase;

public class BookDetailsController {


  private Book currentBook;
  private final ObservableList<BookItem> bookItems = FXCollections.observableArrayList();

  @FXML
  private Label isbnLabel, titleLabel, authorLabel, publisherLabel, subjectLabel, pagesLabel;

  @FXML
  private TableView<BookItem> tableView;

  @FXML
  private TableColumn<BookItem, String> barCode;
  @FXML
  private TableColumn<BookItem, String> referenceOnly;
  @FXML
  private TableColumn<BookItem,String> status;
  @FXML
  private TableColumn<BookItem, String> borrowedDate;
  @FXML
  private TableColumn<BookItem, String> dueDate;
  @FXML
  private TableColumn<BookItem, Double> price;
  @FXML
  private TableColumn<BookItem, String> format;
  @FXML
  private TableColumn<BookItem, String> dateOfPurchase;
  @FXML
  private TableColumn<BookItem, String> publicationDate;

  @FXML
  private void initialize() {
    initializeTable();
  }

  public void setItemDetail(Book book) {
    this.currentBook = book;
    displayBookDetails();
    loadData(); // Load data based on currentBook
  }

  private void displayBookDetails() {
    if (currentBook != null) {
      isbnLabel.setText(currentBook.getISBN());
      titleLabel.setText(currentBook.getTitle());
      authorLabel.setText(currentBook.authorsToString());
      publisherLabel.setText(currentBook.getPublisher());
      subjectLabel.setText(currentBook.getSubject());
      pagesLabel.setText(String.valueOf(currentBook.getNumberOfPages()));
    }
  }

  private void initializeTable() {
    // Set up each column with its cell value factory
    barCode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBarcode()));
    referenceOnly.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getReference())));
    status.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));
    borrowedDate.setCellValueFactory(cellData -> new SimpleStringProperty(formatLocalDate(cellData.getValue().getBorrowed())));
    dueDate.setCellValueFactory(cellData -> new SimpleStringProperty(formatLocalDate(cellData.getValue().getDueDate())));
    price.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
    format.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormat().toString()));
    dateOfPurchase.setCellValueFactory(cellData -> new SimpleStringProperty(formatLocalDate(cellData.getValue().getDateOfPurchase())));
    publicationDate.setCellValueFactory(cellData -> new SimpleStringProperty(formatLocalDate(cellData.getValue().getPublicationDate())));

    tableView.setItems(bookItems); // Bind the data list to the table
  }

  private void loadData() {
    if (currentBook != null) {
      bookItems.clear();
      bookItems.add(BookItemDatabase.getInstance().getItems().getFirst());
      bookItems.add(BookItemDatabase.getInstance().getItems().get(1));
      bookItems.add(new BookItem(currentBook, false, 15.0, BookFormat.MAGAZINE, LocalDate.now(), LocalDate.now()));

      System.out.println("Data loaded: " + bookItems.size() + " items");
    }
  }

  private String formatLocalDate(LocalDate date) {
    if (date != null) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      return date.format(formatter);
    }
    return "";
  }
}
