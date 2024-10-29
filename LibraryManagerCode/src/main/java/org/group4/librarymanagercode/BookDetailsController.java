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
import javafx.scene.input.MouseEvent;
import org.group4.base.books.BookItem;
import org.group4.base.entities.Book;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;

public class BookDetailsController {

  private Book currentBook;
  private ObservableList<BookItem> bookItems = FXCollections.observableArrayList();

  @FXML
  private Label isbnLabel, titleLabel, authorLabel, publisherLabel, subjectLabel, pagesLabel;

  @FXML
  private TableView<BookItem> tableView;

  @FXML
  private TableColumn<BookItem, String> barCode;
  @FXML
  private TableColumn<BookItem, String> referenceOnly;
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

  public void setItemDetail(Book book) {
    this.currentBook = book;
    displayBookDetails();
    loadData(); // Load data based on currentBook
  }

  private void displayBookDetails() {
    if (currentBook != null) {
      isbnLabel.setText(currentBook.getISBN());
      titleLabel.setText(currentBook.getTitle());
      authorLabel.setText(currentBook.getAuthorsAsString());
      publisherLabel.setText(currentBook.getPublisher());
      subjectLabel.setText(currentBook.getSubject());
      pagesLabel.setText(String.valueOf(currentBook.getNumberOfPages()));
    }
  }

  @FXML
  public void initializeTable() {
    // Set up each column with its cell value factory
    barCode.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getBarcode()));
    referenceOnly.setCellValueFactory(cellData -> new SimpleStringProperty(
        String.valueOf(cellData.getValue().getReference())));
    borrowedDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(formatLocalDate(cellData.getValue().getBorrowed())));
    dueDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(formatLocalDate(cellData.getValue().getDueDate())));
    price.setCellValueFactory(
        cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
    format.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getFormat().toString()));
    dateOfPurchase.setCellValueFactory(cellData -> new SimpleStringProperty(
        formatLocalDate(cellData.getValue().getDateOfPurchase())));
    publicationDate.setCellValueFactory(cellData -> new SimpleStringProperty(
        formatLocalDate(cellData.getValue().getPublicationDate())));

    tableView.setItems(bookItems); // Bind the data list to the table
  }

  private void loadData() {
    if (currentBook != null) {
      bookItems.clear();
      bookItems.add(
          new BookItem(currentBook, "845542", false, 12.0, BookFormat.EBOOK, LocalDate.now(),
              LocalDate.now()));
      bookItems.add(
          new BookItem(currentBook, "270423", false, 16.0, BookFormat.AUDIOBOOK, LocalDate.now(),
              LocalDate.now()));
      bookItems.add(
          new BookItem(currentBook, "318493", false, 15.0, BookFormat.MAGAZINE, LocalDate.now(),
              LocalDate.now()));
    }
  }

  // Helper method to format LocalDate to String
  private String formatLocalDate(LocalDate date) {
    if (date != null) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      return date.format(formatter);
    }
    return ""; // Return empty string if date is null
  }


}
