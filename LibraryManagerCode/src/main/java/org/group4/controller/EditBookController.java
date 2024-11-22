package org.group4.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.model.book.Book;
import org.group4.service.user.SessionManagerService;
import org.group4.model.user.Librarian;

/**
 * Controller class for editing book details. Handles the logic for updating book information and
 * interacting with the UI components in the edit book form.
 */
public class EditBookController {

  @FXML
  private TextField bookISBN;         // Text field for the book ISBN
  @FXML
  private TextField bookName;         // Text field for the book title
  @FXML
  private TextField bookSubject;      // Text field for the book subject
  @FXML
  private TextField bookPublisher;    // Text field for the book publisher
  @FXML
  private TextField bookLanguage;     // Text field for the book language
  @FXML
  private TextField numberOfPages;    // Text field for the number of pages
  @FXML
  private TextField bookAuthor;       // Text field for the book authors

  private Book currentBook;                    // The book currently being edited
  private BookViewController parentController; // Reference to the parent controller

  /**
   * Librarian instance managing the session.
   */
  private final Librarian librarian = SessionManagerService.getInstance().getCurrentLibrarian();

  /**
   * Sets the parent controller for enabling table refresh after saving book data.
   *
   * @param bookViewController The parent controller instance.
   */
  public void setParentController(BookViewController bookViewController) {
    this.parentController = bookViewController;
  }

  /**
   * Populates the form fields with the current book's data for editing.
   *
   * @param book The book to be edited.
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
   * Saves the updated book details after validating all required fields. If successful, updates the
   * book, refreshes the table, and closes the form.
   *
   * @param actionEvent The event triggered by the save button.
   */
  public void saveBook(ActionEvent actionEvent) {
    // Validate required fields
    if (bookISBN.getText().isEmpty() || bookName.getText().isEmpty() ||
        bookSubject.getText().isEmpty() || bookPublisher.getText().isEmpty() ||
        bookLanguage.getText().isEmpty() || numberOfPages.getText().isEmpty()) {

      showAlert("Incomplete Information", "Please fill in all required information.");
      return; // Exit the method to allow user to correct input
    }

    // Set book details after validation
    currentBook.setISBN(bookISBN.getText());
    currentBook.setTitle(bookName.getText());
    currentBook.setSubject(bookSubject.getText());
    currentBook.setPublisher(bookPublisher.getText());
    currentBook.setLanguage(bookLanguage.getText());

    // Parse and validate the number of pages
    try {
      currentBook.setNumberOfPages(Integer.parseInt(numberOfPages.getText()));
    } catch (NumberFormatException e) {
      showAlert("Invalid Input", "Please enter a valid number for the page count.");
      return; // Exit the method to allow user to correct input
    }

    // Attempt to save the book
    returnCheckEditBook();

    // Refresh the parent table view if the controller is available
    if (parentController != null) {
      parentController.refreshTable();
    }

    // Close the form after a successful save
    closeForm();
  }

  /**
   * Updates the book in the system. Throws an exception if the update fails due to a duplicate
   * ISBN.
   */
  private void returnCheckEditBook() {
    boolean successEdit = librarian.getBookManager().update(currentBook);

    if (successEdit) {
      // Log the successful update (TODO: Add notifications if implemented)
      System.out.println(
          "Book with ISBN: " + currentBook.getISBN() + " has been edited successfully.");
    } else {
      // Log the failure and throw an exception
      System.out.println("Failed to edit book with ISBN: " + currentBook.getISBN());
      throw new IllegalArgumentException("Book with the same ISBN already exists in the library.");
    }
  }

  /**
   * Displays a warning alert with a specified title and message.
   *
   * @param title   The title of the alert dialog.
   * @param content The message content of the alert dialog.
   */
  private void showAlert(String title, String content) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  /**
   * Cancels the edit operation and closes the form window.
   *
   * @param actionEvent The event triggered by the cancel button.
   */
  public void cancel(ActionEvent actionEvent) {
    closeForm();
  }

  /**
   * Closes the form window.
   */
  private void closeForm() {
    Stage stage = (Stage) bookISBN.getScene().getWindow();
    stage.close();
  }
}
