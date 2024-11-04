package org.group4.librarymanagercode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.group4.base.entities.Author;
import org.group4.base.entities.Book;
import org.group4.base.users.Librarian;
import org.group4.service.GoogleBooksService;
import org.group4.service.GoogleBooksServiceByISBN;
import org.json.JSONArray;
import org.json.JSONObject;
import org.group4.test.AddBookWithISBN;
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
  private final GoogleBooksServiceByISBN googleBooksServiceByISBN = new GoogleBooksServiceByISBN();
  private static final Librarian loggedInLibrarian = new Librarian();

  @FXML
  private void searchByTitle() {
//    String title = titleField.getText();
//    if (!title.isEmpty()) {
//      // Call GoogleBooksService for searching by title
//      // and populate fields with returned data
//      Book book = googleBooksService.searchByTitle(title);
//      if (book != null) {
//        populateBookFields(book);
//      } else {
//        // handle case where book is not found
//      }
//    }
  }


  private List<Author> parseAuthors(String authorsText) {
    // Parse the authors' text to create a list of Author objects
    return Arrays.stream(authorsText.split(","))
        .map(String::trim)
        .map(Author::new)  // Assuming Author has a constructor that takes a name
        .toList();
  }



  @FXML
  private void addBook() {
    // Collect data from fields
    String isbn = isbnField.getText();
    String title = titleField.getText();
    String subject = subjectField.getText();
    String publisher = publisherField.getText();
    String language = languageField.getText();
    int numberOfPages = Integer.parseInt(numberOfPagesField.getText());
    List<Author> authors = parseAuthors(authorsField.getText());

    // Create a new Book object or pass this data to add the book
    Book book = new Book(isbn, title, subject, publisher, language, numberOfPages, authors);

    // Add the book to the system (Database, etc.)
    // Assuming a method addBook exists in your system
    addBookToLibrary(book);
  }


  private void populateBookFields(Book book) {
    isbnField.setText(book.getISBN());
    titleField.setText(book.getTitle());
    subjectField.setText(book.getSubject());
    publisherField.setText(book.getPublisher());
    languageField.setText(book.getLanguage());
    numberOfPagesField.setText(String.valueOf(book.getNumberOfPages()));
    authorsField.setText(String.join(", ", book.getAuthors().stream().map(Author::getName).toList()));
  }

  private void addBookToLibrary(Book book) {
    // Implement the logic to add a book to your library
  }

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

  public void addBookAction(ActionEvent actionEvent) {
  }


  @FXML
  private void searchByISBN() {
    String isbn = isbnField.getText();
    if (!isbn.isEmpty()) {
      try {
        JSONObject bookDetails = googleBooksServiceByISBN.getBookDetails(isbn);

        String title = bookDetails.optString("title", "Unknown");
        String subject = bookDetails.optString("subject", "Unknown");
        String publisher = bookDetails.optString("publisher", "Unknown");
        String language = bookDetails.optString("language", "Unknown");
        int pageCount = bookDetails.optInt("pageCount", 0);

        List<Author> authors = new ArrayList<>();
        JSONArray authorsArray = bookDetails.optJSONArray("authors");
        if (authorsArray != null) {
          for (int i = 0; i < authorsArray.length(); i++) {
            authors.add(new Author(authorsArray.getString(i)));
          }
        }

        Book book = new Book(isbn, title, subject, publisher, language, pageCount, authors);
        populateBookFields(book);
        loggedInLibrarian.addBook(book);
        book.printDetails(); // Assuming Book has a method to print details

      } catch (IOException e) {
        e.printStackTrace();
        // Handle error, e.g., show an alert dialog
      }
    }
  }


}
