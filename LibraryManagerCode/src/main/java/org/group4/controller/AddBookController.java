package org.group4.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.model.book.Author;
import org.group4.model.book.Book;
import org.group4.service.user.SessionManagerService;
import org.group4.model.user.Librarian;
import org.group4.service.book.GoogleBooksService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Controller class for adding a new book to the library.
 *
 * <p>This class handles user interactions related to adding books. It uses
 * GoogleBooksService to fetch book details and allows librarians to input additional information
 * before storing the book in the library system.
 */
public class AddBookController {

  @FXML
  private JFXButton bookButton;
  @FXML
  private JFXButton MemberButton;
  @FXML
  private JFXButton homeButton;
  @FXML
  private JFXButton bookLendingButton;
  @FXML
  private JFXButton notificationButton;
  @FXML
  private JFXButton settingButton;
  @FXML
  private JFXButton closeButton;

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

  // Service for fetching book details from Google Books API.
  private final GoogleBooksService googleBooksService = new GoogleBooksService();

  // Current librarian using the system.
  private final Librarian librarian = SessionManagerService.getInstance().getCurrentLibrarian();

  // Book instance for the current operation.
  private Book book;

  /**
   * Searches for a book by its ISBN using the Google Books API.
   *
   * <p>If the book is found, the UI fields are populated with the retrieved
   * details.
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
   * Adds a new book to the library.
   *
   * <p>Validates user inputs before creating and storing a Book object.
   */
  @FXML
  private void addBookAction(ActionEvent actionEvent) {
    if (isbnField.getText().isEmpty() || titleField.getText().isEmpty()
        || subjectField.getText().isEmpty() || languageField.getText().isEmpty()
        || numberOfPagesField.getText().isEmpty() || authorsField.getText().isEmpty()) {
      showAlert(AlertType.WARNING, "Incomplete Information",
          "Please fill in all required information.");
      return;
    }

    try {
      Book book = createBookFromFields();
      addBookToLibrary(book);
      showAlert(Alert.AlertType.INFORMATION, "Book Added Successfully",
          "The book has been added to the library.");
    } catch (IllegalArgumentException e) {
      showAlert(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
    } catch (IOException | SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Populates the UI fields using the JSON book details.
   *
   * @param bookDetails JSON object containing book information
   */
  private void populateFieldsFromJson(JSONObject bookDetails) {
    isbnField.setText(convertISBN10toISBN13(isbnField.getText()));
    titleField.setText(bookDetails.optString("title", "Unknown"));
    subjectField.setText(bookDetails.optString("categories", "Unknown"));
    publisherField.setText(bookDetails.optString("publisher", "Unknown"));
    languageField.setText(bookDetails.optString("language", "Unknown"));
    numberOfPagesField.setText(String.valueOf(bookDetails.optInt("pageCount", 0)));
    authorsField.setText(parseAuthorsFromJson(bookDetails.optJSONArray("authors")));
  }

  /**
   * Creates a Book object using user input from the UI fields.
   *
   * @return A new Book instance
   * @throws IllegalArgumentException if any input is invalid
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
   * Parses a comma-separated list of authors into a set of Author objects.
   *
   * @param authorsText A string containing author names separated by commas
   * @return A set of Author objects
   * @throws IllegalArgumentException if the input is empty
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
   * Parses authors from a JSON array into a single string.
   *
   * @param authorsArray A JSON array containing author names
   * @return A comma-separated string of author names
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
   * Adds a book to the library system.
   *
   * <p>Throws an exception if the book already exists.
   *
   * @param book The book to add
   */
  private void addBookToLibrary(Book book) throws IOException, SQLException {
    boolean successAdded = librarian.getBookManager().add(book);
    if (!successAdded) {
      throw new IllegalArgumentException("Book with the same ISBN already exists in the library.");
    }
    System.out.println("Book with ISBN: " + book.getISBN() + " has been added successfully.");
  }

  /**
   * Converts an ISBN-10 to its corresponding ISBN-13.
   *
   * <p>Validates the input and calculates the checksum for the ISBN-13.
   *
   * @param isbn10 The 10-digit ISBN string to convert
   * @return The 13-digit ISBN string
   * @throws IllegalArgumentException if the input is not a valid ISBN-10
   */
  public String convertISBN10toISBN13(String isbn10) {
    if (isbn10.length() == 13) {
      return isbn10;
    } else if (isbn10.length() != 10) {
      throw new IllegalArgumentException(
          "Invalid ISBN-10! The input must be exactly 10 characters long.");
    }

    String isbn13WithoutChecksum = "978" + isbn10.substring(0, 9);

    int checksum = 0;
    for (int i = 0; i < isbn13WithoutChecksum.length(); i++) {
      int digit = Character.getNumericValue(isbn13WithoutChecksum.charAt(i));
      checksum += (i % 2 == 0) ? digit : digit * 3;
    }

    checksum = (10 - (checksum % 10)) % 10;
    return isbn13WithoutChecksum + checksum;
  }

  /**
   * Displays an alert dialog with the given type, title, and message.
   *
   * @param alertType The type of the alert (e.g., ERROR, WARNING, INFORMATION)
   * @param title     The title of the alert dialog
   * @param message   The message to display in the alert dialog
   */
  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Handles navigation to the home screen.
   *
   * @param actionEvent The action event triggered by the home button
   * @throws IOException if the FXML file cannot be loaded
   */
  public void homeAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AdminPane.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    Stage stage = (Stage) homeButton.getScene().getWindow();
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Home button clicked");
  }

  /**
   * Handles navigation to the member view.
   *
   * @param actionEvent The action event triggered by the member button
   * @throws IOException if the FXML file cannot be loaded
   */
  public void memberAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MemberView.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    Stage stage = (Stage) MemberButton.getScene().getWindow();
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Member button clicked");
  }

  /**
   * Handles navigation to the book view.
   *
   * @param actionEvent The action event triggered by the book button
   * @throws IOException if the FXML file cannot be loaded
   */
  public void bookAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BookView.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    Stage stage = (Stage) bookButton.getScene().getWindow();
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Book button clicked");
  }

  /**
   * Handles navigation to the book lending view.
   *
   * @param actionEvent The action event triggered by the book lending button
   * @throws IOException if the FXML file cannot be loaded
   */
  public void bookLendingAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BookLending.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    Stage stage = (Stage) bookButton.getScene().getWindow();
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Book lending button clicked");
  }

  /**
   * Handles navigation to the notification view.
   *
   * @param actionEvent The action event triggered by the notification button
   * @throws IOException if the FXML file cannot be loaded
   */
  public void notificationAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Notification.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    Stage stage = (Stage) notificationButton.getScene().getWindow();
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Notification button clicked");
  }

  /**
   * Handles navigation to the settings view.
   *
   * @param actionEvent The action event triggered by the settings button
   * @throws IOException if the FXML file cannot be loaded
   */
  public void settingAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Setting.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    Stage stage = (Stage) settingButton.getScene().getWindow();
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Setting button clicked");
  }

  /**
   * Closes the application.
   *
   * @param actionEvent The action event triggered by the close button
   */
  public void close(ActionEvent actionEvent) {
    Platform.exit();
  }
}

