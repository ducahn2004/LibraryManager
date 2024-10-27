package org.group4.librarymanagercode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.group4.base.entities.Book;

public class BookDetailsController {

  @FXML
  private Label isbnLabel;

  @FXML
  private Label titleLabel;

  @FXML
  private Label authorLabel;

  @FXML
  private Label publisherLabel;

  @FXML
  private Label subjectLabel;

  @FXML
  private Label pagesLabel;

  @FXML
  private TextField isbnField;

  @FXML
  private TextField titleField;

  @FXML
  private TextField authorField;

  @FXML
  private TextField publisherField;

  @FXML
  private TextField subjectField;

  @FXML
  private TextField pagesField;

  private Book currentBook;

  // Method to populate the view with the book details
  public void setBookDetails(Book book) {
    currentBook = book;

    // Set text for labels
    isbnLabel.setText(book.getISBN());
    titleLabel.setText(book.getTitle());
    authorLabel.setText(book.getAuthors().toString());  // Assuming one author for simplicity
    publisherLabel.setText(book.getPublisher());
    subjectLabel.setText(book.getSubject());
    pagesLabel.setText(String.valueOf(book.getNumberOfPages()));

    // Set text for text fields (for editing mode)
    isbnField.setText(book.getISBN());
    titleField.setText(book.getTitle());
    authorField.setText(book.getAuthors().toString());  // Assuming one author for simplicity
    publisherField.setText(book.getPublisher());
    subjectField.setText(book.getSubject());
    pagesField.setText(String.valueOf(book.getNumberOfPages()));

    // Initially, only the labels are visible
    setEditMode(false);
  }

  // Switch between display and edit mode
  private void setEditMode(boolean editMode) {
    isbnLabel.setVisible(!editMode);
    titleLabel.setVisible(!editMode);
    authorLabel.setVisible(!editMode);
    publisherLabel.setVisible(!editMode);
    subjectLabel.setVisible(!editMode);
    pagesLabel.setVisible(!editMode);

    isbnField.setVisible(editMode);
    titleField.setVisible(editMode);
    authorField.setVisible(editMode);
    publisherField.setVisible(editMode);
    subjectField.setVisible(editMode);
    pagesField.setVisible(editMode);
  }

  // Handler for the Edit button
  @FXML
  private void onEditBook() {
    // Enable edit mode, switching to TextFields
    setEditMode(true);
  }

  // Handler for the Save button (save changes to the book)


  // Handler for the Cancel button (discard changes)
  @FXML
  private void onCancelEdit() {
    // Discard changes and reset TextFields to original values
    setBookDetails(currentBook);
    setEditMode(false);
  }

  public void onDeleteBook(ActionEvent actionEvent) {
  }

  public void onClose(ActionEvent actionEvent) {
  }

  public void onSaveChanges(ActionEvent actionEvent) {
  }
}
