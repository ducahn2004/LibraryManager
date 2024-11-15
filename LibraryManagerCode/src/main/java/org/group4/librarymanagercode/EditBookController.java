package org.group4.librarymanagercode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.module.books.Book;
import org.group4.module.users.Librarian;
import org.group4.database.LibrarianDatabase;

/**
 * Controller class for editing book details.
 */
public class EditBookController {

  @FXML
  private TextField bookISBN;         // Text field for book ISBN
  @FXML
  private TextField bookName;         // Text field for book title
  @FXML
  private TextField bookSubject;      // Text field for book subject
  @FXML
  private TextField bookPublisher;    // Text field for book publisher
  @FXML
  private TextField bookLanguage;     // Text field for book language
  @FXML
  private TextField numberOfPages;    // Text field for number of pages
  @FXML
  private TextField bookAuthor;       // Text field for book authors

  private Book currentBook;                    // The book currently being edited
  private BookViewController parentController; // Reference to the parent controller
  private final Librarian librarian = LibrarianDatabase.getInstance().getItems()
      .getFirst(); // Default librarian instance

  /**
   * Sets the parent controller to allow table refresh after saving book data.
   *
   * @param bookViewController the parent controller instance
   */
  public void setParentController(BookViewController bookViewController) {
    this.parentController = bookViewController;
  }

  /**
   * Populates form fields with the book's current data.
   *
   * @param book the book to be edited
   */
  public void setBookData(Book book) {
    this.currentBook = book;
    bookISBN.setText(currentBook.getISBN());
    bookName.setText(currentBook.getTitle());
    bookSubject.setText(currentBook.getSubject());
    bookPublisher.setText(currentBook.getPublisher());
    bookLanguage.setText(currentBook.getLanguage());
    numberOfPages.setText(String.valueOf(currentBook.getNumberOfPages()));
    bookAuthor.setText(currentBook.authorsToString());
  }

  /**
   * Saves the edited book data after validating all required fields. If fields are valid, updates
   * the book and refreshes the table.
   *
   * @param actionEvent the event triggered by the save button
   */
  public void saveBook(ActionEvent actionEvent) {
    // Validate required fields
    if (bookISBN.getText().isEmpty() || bookName.getText().isEmpty() ||
        bookSubject.getText().isEmpty() || bookPublisher.getText().isEmpty() ||
        bookLanguage.getText().isEmpty() || numberOfPages.getText().isEmpty()) {

      showAlert("Incomplete Information", "Please fill in all required information.");
      return; // Stop execution to allow user to correct input
    }

    // Set book details after validation
    currentBook.setISBN(bookISBN.getText());
    currentBook.setTitle(bookName.getText());
    currentBook.setSubject(bookSubject.getText());
    currentBook.setPublisher(bookPublisher.getText());
    currentBook.setLanguage(bookLanguage.getText());

    // Parse and set the number of pages, with error handling for invalid input
    try {
      currentBook.setNumberOfPages(Integer.parseInt(numberOfPages.getText()));
    } catch (NumberFormatException e) {
      showAlert("Invalid Input", "Please enter a valid number for the page count.");
      return; // Stop execution to allow user to correct input
    }

    // Refresh the table and close the form if all validations pass
    if (parentController != null) {
      parentController.refreshTable();
    }
    closeForm();
  }

  /**
   * Displays a warning alert with specified title and content.
   *
   * @param title   the title of the alert
   * @param content the content message of the alert
   */
  private void showAlert(String title, String content) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  /**
   * Cancels the edit operation and closes the form.
   *
   * @param actionEvent the event triggered by the cancel button
   */
  public void cancel(ActionEvent actionEvent) {
    closeForm();
  }

  /**
   * Closes the current form window.
   */
  private void closeForm() {
    Stage stage = (Stage) bookISBN.getScene().getWindow();
    stage.close();
  }
}
