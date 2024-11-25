package org.group4.controller;

import com.jfoenix.controls.JFXButton;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    // Create a Task to run the search on a background thread
    Task<Optional<JSONObject>> searchTask = new Task<>() {
      @Override
      protected Optional<JSONObject> call() {
        return googleBooksService.getBookDetails(isbn);
      }
    };

    // Define the action when the task is successful
    searchTask.setOnSucceeded(event -> {
      Optional<JSONObject> bookDetails = searchTask.getValue();
      if (bookDetails.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Book Not Found", "No book found with the entered ISBN.");
      } else {
        populateFieldsFromJson(bookDetails.get());
        showAlert(Alert.AlertType.INFORMATION, "Search Successful",
            "Book details fetched successfully!");
      }
    });

    // Define the action when the task fails
    searchTask.setOnFailed(event -> {
      Throwable exception = searchTask.getException();
      showAlert(Alert.AlertType.ERROR, "Error",
          "An error occurred during the search: " + exception.getMessage());
    });

    // Run the task in a background thread
    new Thread(searchTask).start();
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
   * @return A set of Author objects, or null if the input is invalid
   */
  private Set<Author> parseAuthors(String authorsText) {
    Set<Author> authors;

    try {
      if (authorsText == null || authorsText.trim().isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Invalid Input", "Authors cannot be empty.");
        return null; // Return null if the input is invalid
      }

      authors = authorsText.lines()
          .map(String::trim)
          .map(Author::new)
          .collect(Collectors.toCollection(HashSet::new));

    } catch (Exception e) {
      showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while parsing the authors.");
      return null; // Return null if there is any error during processing
    }

    return authors;
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
   * Adds a book to the library.
   *
   * <p>Displays an alert if the addition fails due to duplicate ISBN or other issues.
   *
   * @param book The book to be added to the library.
   */
  private void addBookToLibrary(Book book) {
    try {
      boolean successAdded = librarian.getBookManager().add(book);
      if (!successAdded) {
        throw new IllegalArgumentException(
            "Book with the same ISBN already exists in the library.");
      }
      System.out.println("Book with ISBN: " + book.getISBN() + " has been added successfully.");
      showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully!");
    } catch (IllegalArgumentException e) {
      showAlert(Alert.AlertType.ERROR, "Duplicate ISBN", e.getMessage());
    } catch (SQLException e) {
      showAlert(Alert.AlertType.ERROR, "Database Error",
          "An error occurred while accessing the database: " + e.getMessage());
    } catch (Exception e) {
      showAlert(Alert.AlertType.ERROR, "Unexpected Error",
          "An unexpected error occurred. Please try again.");
    }
  }

  /**
   * Converts an ISBN-10 to its corresponding ISBN-13.
   *
   * <p>Validates the input and calculates the checksum for the ISBN-13.
   *
   * @param isbn10 The 10-digit ISBN string to convert
   * @return The 13-digit ISBN string, or null if the input is invalid
   */
  public String convertISBN10toISBN13(String isbn10) {
    try {
      if (isbn10.length() == 13) {
        return isbn10;
      } else if (isbn10.length() != 10) {
        throw new IllegalArgumentException(
            "Invalid ISBN-10! The input must be exactly 10 characters long."
        );
      }

      String isbn13WithoutChecksum = "978" + isbn10.substring(0, 9);

      int checksum = 0;
      for (int i = 0; i < isbn13WithoutChecksum.length(); i++) {
        int digit = Character.getNumericValue(isbn13WithoutChecksum.charAt(i));
        checksum += (i % 2 == 0) ? digit : digit * 3;
      }

      checksum = (10 - (checksum % 10)) % 10;
      return isbn13WithoutChecksum + checksum;

    } catch (IllegalArgumentException e) {
      showAlert(
          Alert.AlertType.ERROR,
          "Invalid Input",
          "Error: " + e.getMessage()
      );
      return null;
    } catch (Exception e) {
      showAlert(
          Alert.AlertType.ERROR,
          "Unexpected Error",
          "An unexpected error occurred. Please check the input and try again."
      );
      return null;
    }
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
   * Returns the current stage of the view.
   *
   * @return The current Stage object.
   */
  private Stage getStage() {
    return (Stage) homeButton.getScene().getWindow();
  }

// Navigation actions for switching between different views

  public void HomeAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "AdminPane.fxml", "Library Manager");
  }

  public void MemberAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "MemberView.fxml", "Library Manager");
  }

  public void BookAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "BookView.fxml", "Library Manager");
  }

  public void BookLendingAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "BookLending.fxml", "Library Manager");
  }

  public void notificationAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "Notification.fxml", "Library Manager");
  }

  public void SettingAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "Setting.fxml", "Library Manager");
  }

  /**
   * Closes the application.
   *
   * @param actionEvent The event triggered by clicking the close button.
   */
  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }
}

