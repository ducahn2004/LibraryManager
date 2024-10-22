package org.group4.librarymanagercode.Admin;

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
  @FXML
  private void onSaveChanges() {
    // Save the edited details to the currentBook object
    currentBook.setIsbn(isbnField.getText());
    currentBook.setTitle(titleField.getText());
    // Update other fields similarly...
    currentBook.getAuthors().get(0).setName(authorField.getText());
    currentBook.setPublisher(publisherField.getText());
    currentBook.setSubject(subjectField.getText());
    currentBook.setPages(Integer.parseInt(pagesField.getText()));

    // Update the labels with the new values
    setBookDetails(currentBook);

    // Disable edit mode, switching back to Labels
    setEditMode(false);
  }

  // Handler for the Cancel button (discard changes)
  @FXML
  private void onCancelEdit() {
    // Discard changes and reset TextFields to original values
    setBookDetails(currentBook);
    setEditMode(false);
  }
}
