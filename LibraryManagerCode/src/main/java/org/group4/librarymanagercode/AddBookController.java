package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.group4.base.books.Author;
import org.group4.base.books.Book;
import org.group4.base.users.Librarian;
import org.group4.database.LibrarianDatabase;
import org.group4.service.GoogleBooksService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Controller class for adding a new book to the library.
 * Handles interactions with GoogleBooksService and updates the UI.
 */
public class AddBookController {

  public JFXButton closeButton;
  public JFXButton settingButton;
  public JFXButton notificationButton;
  public JFXButton returnBookButton;
  public JFXButton bookButton;
  public JFXButton memberButton;
  public JFXButton homeButton;
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
  private static final Librarian librarian = LibrarianDatabase.getInstance().getItems().getFirst();

  /**
   * Searches for a book by ISBN using Google Books API and populates fields.
   */
  @FXML
  private void searchByISBN() {
    String isbn = isbnField.getText();
    if (isbn.isEmpty()) {
      showAlert(Alert.AlertType.WARNING, "Empty ISBN", "Please enter an ISBN to search.");
      return;
    }

    Optional<JSONObject> bookDetails = googleBooksService.getBookDetails(isbn);
    if (bookDetails.isEmpty()) {
      showAlert(Alert.AlertType.ERROR, "Book Not Found", "No book found with the entered ISBN.");
      return;
    }
    populateFieldsFromJson(bookDetails.get());
  }


  /**
   * Adds a new book to the library by collecting data from fields and validating inputs.
   */
  @FXML
  private void addBook() {
    try {
      Book book = createBookFromFields();
      addBookToLibrary(book);
      showAlert(Alert.AlertType.INFORMATION, "Book Added Successfully", "The book has been added to the library.");
    } catch (IllegalArgumentException e) {
      showAlert(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
    }
  }

  /**
   * Populates fields based on book details fetched from Google Books API.
   *
   * @param bookDetails JSON object containing book details
   */
  private void populateFieldsFromJson(JSONObject bookDetails) {
    titleField.setText(bookDetails.optString("title", "Unknown"));
    subjectField.setText(bookDetails.optString("categories", "Unknown"));
    publisherField.setText(bookDetails.optString("publisher", "Unknown"));
    languageField.setText(bookDetails.optString("language", "Unknown"));
    numberOfPagesField.setText(String.valueOf(bookDetails.optInt("pageCount", 0)));

    authorsField.setText(parseAuthorsFromJson(bookDetails.optJSONArray("authors")));
  }

  /**
   * Creates a Book object from the fields, validating inputs as necessary.
   *
   * @return a new Book instance
   */
  private Book createBookFromFields() {
    String isbn = isbnField.getText();
    String title = titleField.getText();
    String subject = subjectField.getText();
    String publisher = publisherField.getText();
    String language = languageField.getText();

    int numberOfPages;
    try {
      numberOfPages = Integer.parseInt(numberOfPagesField.getText());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Please enter a valid number for pages.");
    }

    Set<Author> authors = parseAuthors(authorsField.getText());
    return new Book(isbn, title, subject, publisher, language, numberOfPages, authors);
  }

  /**
   * Parses authors from a comma-separated string and returns a set of Author objects.
   *
   * @param authorsText Comma-separated string of authors
   * @return Set of Author objects
   */
  private Set<Author> parseAuthors(String authorsText) {
    if (authorsText == null || authorsText.isEmpty()) {
      throw new IllegalArgumentException("Authors cannot be empty.");
    }
    return authorsText.lines()
        .map(String::trim)
        .map(Author::new)
        .collect(Collectors.toCollection(HashSet::new));
  }

  /**
   * Parses authors from a JSON array to a formatted string.
   *
   * @param authorsArray JSON array of authors
   * @return Formatted string of authors
   */
  private String parseAuthorsFromJson(JSONArray authorsArray) {
    if (authorsArray == null) {
      return "Unknown";
    }
    return authorsArray.toList().stream()
        .map(Object::toString)
        .collect(Collectors.joining(", "));
  }

  /**
   * Adds a Book to the library system (implement your storage logic).
   *
   * @param book Book object to add
   */
  private void addBookToLibrary(Book book) {
    // Add the logic to store the book in the library's database
    boolean added = librarian.addBook(book);
    if (!added) {
      throw new IllegalArgumentException("Book with the same ISBN already exists in the library.");
    }

  }

  /**
   * Shows an alert dialog with specified type, title, and message.
   *
   * @param alertType Type of alert
   * @param title Title of the alert
   * @param message Message content of the alert
   */
  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }

  // Navigation actions (empty methods)
  public void homeAction(ActionEvent actionEvent) {}
  public void memberAction(ActionEvent actionEvent) {}
  public void bookAction(ActionEvent actionEvent) {}
  public void returnBookAction(ActionEvent actionEvent) {}
  public void notificationAction(ActionEvent actionEvent) {}
  public void settingAction(ActionEvent actionEvent) {}
  public void close(ActionEvent actionEvent) {}
  public void searchByTitle(ActionEvent actionEvent) {}
  public void addBookAction(ActionEvent actionEvent) {}
}
