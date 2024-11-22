package org.group4.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.model.book.Book;
import org.group4.model.book.BookItem;
import org.group4.model.book.Rack;
import org.group4.model.enums.BookFormat;
import org.group4.model.enums.BookStatus;
import org.group4.service.user.SessionManagerService;
import org.group4.model.user.Librarian;

/**
 * Controller for the "Add Book Item" view.
 * <p>
 * This class manages the UI and logic for adding a book item to the library system. It interacts
 * with the parent controller and updates the book item list upon successful addition.
 */
public class AddBookItemController {

  @FXML
  private CheckBox referenceOnlyCheckBox;
  @FXML
  private TextField priceTextField;
  @FXML
  private ComboBox<BookFormat> formatComboBox;
  @FXML
  private DatePicker dateOfPurchasePicker;
  @FXML
  private DatePicker publicationDatePicker;
  @FXML
  private TextField placeAtTextField;
  @FXML
  private JFXButton saveButton;
  @FXML
  private JFXButton cancelButton;

  private BookDetailsController parentController;
  private final Librarian librarian = SessionManagerService.getInstance().getCurrentLibrarian();
  private Book currentBook;
  private BookItem bookItem;

  /**
   * Initializes the controller.
   * <p>
   * This method is called after the FXML file has been loaded. It sets up the format combo box with
   * values from the {@link BookFormat} enumeration.
   */
  public void initialize() {
    // Populate formatComboBox with available book formats.
    formatComboBox.getItems().addAll(BookFormat.values());
    formatComboBox.setValue(BookFormat.HARDCOVER); // Default value
  }

  /**
   * Handles the cancel button action.
   * <p>
   * Closes the current form without saving any changes.
   *
   * @param actionEvent The event triggered by clicking the cancel button.
   */
  public void cancel(ActionEvent actionEvent) {
    closeForm();
  }

  /**
   * Handles the save button action.
   * <p>
   * Creates a new {@link BookItem} and adds it to the library. Updates the parent controller's
   * table if the operation is successful.
   *
   * @param actionEvent The event triggered by clicking the save button.
   */
  public void saveBookItem(ActionEvent actionEvent) {
    try {
      addBookItemToLibrary(); // Add the new book item to the library
      if (parentController != null) {
        parentController.addBookItemToList(bookItem); // Update the parent controller's table
      }
      showAlert(Alert.AlertType.INFORMATION, "Add Book Item Successfully",
          "The book item has been added to the library.");
    } catch (IllegalArgumentException | IOException e) {
      showAlert(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
    }
    closeForm();
  }

  /**
   * Sets the parent controller.
   * <p>
   * This method allows the parent controller to be set so it can be updated when changes occur.
   *
   * @param parentController The parent controller.
   */
  public void setParentController(BookDetailsController parentController) {
    this.parentController = parentController;
    this.currentBook = parentController.returnCurrentBook();
  }

  /**
   * Closes the current form.
   */
  private void closeForm() {
    Stage stage = (Stage) saveButton.getScene().getWindow();
    stage.close();
  }

  /**
   * Adds a book item to the library.
   * <p>
   * Creates a new {@link BookItem} instance and adds it to the library via the librarian. Throws an
   * exception if the addition fails.
   *
   * @throws IOException If there is an issue with accessing resources.
   */
  private void addBookItemToLibrary() throws IOException {
    bookItem = new BookItem(
        currentBook,
        referenceOnlyCheckBox.isSelected(), // Indicates if the book is reference-only
        null, null, // Placeholder for future attributes
        Double.parseDouble(priceTextField.getText()), // Parse price from input field
        formatComboBox.getValue(), // Selected book format
        BookStatus.AVAILABLE, // Default book status
        dateOfPurchasePicker.getValue(), // Date of purchase
        publicationDatePicker.getValue(), // Publication date
        new Rack(1, placeAtTextField.getText()) // Rack location
    );

    boolean added = librarian.getBookItemManager().add(bookItem);
    if (!added) {
      System.out.println("ISBN Current Book is: " + currentBook.getISBN());
      throw new IllegalArgumentException("Could not add book item to the library.");
    }
  }

  /**
   * Shows an alert dialog.
   *
   * @param alertType The type of alert to display (e.g., ERROR, INFORMATION).
   * @param title     The title of the alert dialog.
   * @param message   The message content of the alert dialog.
   */
  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Sets the current book.
   * <p>
   * This method allows the book being edited or added to be set from outside this controller.
   *
   * @param currentBook The book to be set as the current book.
   */
  public void setCurrentBook(Book currentBook) {
    this.currentBook = currentBook;
  }
}
