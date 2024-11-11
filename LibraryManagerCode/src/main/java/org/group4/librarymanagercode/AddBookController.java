package org.group4.librarymanagercode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.group4.base.books.Author;
import org.group4.base.books.Book;
import org.group4.base.users.Librarian;
import org.group4.database.LibrarianDatabase;
import org.group4.service.GoogleBooksService;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.stream.Collectors;

public class AddBookController {

  @FXML
  private TextField isbnField;
  @FXML
  private TextField titleField;
  @FXML
  private TextField subjectField;
  @FXML
  private TextField publisherField;
  @FXML
  private TextField languageField;
  @FXML
  private TextField numberOfPagesField;
  @FXML
  private TextArea authorsField;

  private final GoogleBooksService googleBooksService = new GoogleBooksService();
  private final Librarian  librarian= LibrarianDatabase.getInstance().getItems().getFirst();

  @FXML
  private void searchByISBN() {
    String isbn = isbnField.getText();
    if (!isbn.isEmpty()) {
      try {
        JSONObject bookDetails = googleBooksService.getBookDetails(isbn);

        if (bookDetails.isEmpty()) {
          showAlert(AlertType.ERROR, "Book Not Found", "No book found with the entered ISBN.");
          return;
        }

        // Điền thông tin sách vào các trường
        titleField.setText(bookDetails.optString("title", "Unknown"));
        subjectField.setText(bookDetails.optString("categories", "Unknown"));
        publisherField.setText(bookDetails.optString("publisher", "Unknown"));
        languageField.setText(bookDetails.optString("language", "Unknown"));
        numberOfPagesField.setText(String.valueOf(bookDetails.optInt("pageCount", 0)));

        // Xử lý danh sách tác giả
        JSONArray authorsArray = bookDetails.optJSONArray("authors");
        if (authorsArray != null) {
          List<String> authorsList = new ArrayList<>();
          for (int i = 0; i < authorsArray.length(); i++) {
            authorsList.add(authorsArray.getString(i));
          }
          authorsField.setText(String.join(", ", authorsList));
        } else {
          authorsField.setText("Unknown");
        }

      } catch (IOException e) {
        // Hiển thị thông báo lỗi nếu có lỗi khi kết nối
        showAlert(AlertType.ERROR, "Error", "An error occurred while fetching book details.");
      }
    }
  }

  @FXML
  private void addBook() {
    // Collect data
    String isbn = isbnField.getText();
    String title = titleField.getText();
    String subject = subjectField.getText();
    String publisher = publisherField.getText();
    String language = languageField.getText();
    int numberOfPages = 0;
    try {
      numberOfPages = Integer.parseInt(numberOfPagesField.getText());
    } catch (NumberFormatException e) {
      showAlert(AlertType.ERROR, "Invalid Input", "Please enter a valid number for pages.");
      return;
    }
    Set<Author> authors = parseAuthors(authorsField.getText());

    // Create new book object
    Book book = new Book(isbn, title, subject, publisher, language, numberOfPages, authors);

    // Add book to system
    addBookToLibrary(book);

    // Show successful notification
    showAlert(AlertType.INFORMATION, "Book Added Successfully",
        "The book has been added to the library.");
  }

  private void populateBookFields(Book book) {
    isbnField.setText(book.getISBN());
    titleField.setText(book.getTitle());
    subjectField.setText(book.getSubject());
    publisherField.setText(book.getPublisher());
    languageField.setText(book.getLanguage());
    numberOfPagesField.setText(String.valueOf(book.getNumberOfPages()));
    authorsField.setText(String.join(", ",
        book.getAuthors().stream().map(Author::getName).collect(Collectors.toList())));
  }

  private void addBookToLibrary(Book book) {
    // Logic to add book to the library system (e.g., save to database or list)
  }

  private void showAlert(AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private Set<Author> parseAuthors(String authorsText) {
    // Parse the authors' text to create a set of Author objects
    return Arrays.stream(authorsText.split(","))
        .map(String::trim)
        .map(Author::new)   // Consumer author's name
        .collect(Collectors.toSet());
  }

  // Các sự kiện khác cho button điều hướng:
  public void HomeAction(ActionEvent actionEvent) {
  }

  public void MemberAction(ActionEvent actionEvent) {
  }

  public void BookAction(ActionEvent actionEvent) {
  }

  public void ReturnBookAction(ActionEvent actionEvent) {
  }

  public void notificationAction(ActionEvent actionEvent) {
  }

  public void SettingAction(ActionEvent actionEvent) {
  }

  public void Close(ActionEvent actionEvent) {
  }

  public void searchByTitle(ActionEvent actionEvent) {
  }

  public void addBookAction(ActionEvent actionEvent) {
  }
}
