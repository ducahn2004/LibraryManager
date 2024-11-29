package org.group4.controller;

import com.jfoenix.controls.JFXButton;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import org.group4.service.user.SessionManager;
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

  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();
  private BookDetailsController parentController;
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
      if (dateOfPurchasePicker.getValue().isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Date of purchase cannot be a future date.");
      }
      if (publicationDatePicker.getValue().isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Publication Date cannot be a future date.");
      }
      if (publicationDatePicker.getValue().isBefore(dateOfPurchasePicker.getValue())) {
        throw new IllegalArgumentException("Publication Date cannot be before date of Purchase.");
      }
      addBookItemToLibrary(); // Add the new book item to the library
      if (parentController != null) {
        parentController.addBookItemToList(bookItem); // Update the parent controller's table
      }
      showAlert(Alert.AlertType.INFORMATION, "Add Book Item Successfully",
          "The book item has been added to the library.");
      closeForm();
    } catch (IllegalArgumentException e) {
      showAlert(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
    }
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
   * Creates a new {@link BookItem} instance and adds it to the library via the librarian. If the
   * addition fails, an alert is shown to the user.
   */
  private void addBookItemToLibrary() {
    try {
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
        showAlert(
            Alert.AlertType.ERROR,
            "Error",
            "Could not add the book item to the library. Please check the details and try again."
        );
      }
    } catch (NumberFormatException e) {
      showAlert(
          Alert.AlertType.ERROR,
          "Invalid Input",
          "Please enter a valid price for the book item."
      );
    } catch (SQLException e) {
      showAlert(
          Alert.AlertType.ERROR,
          "Database Error",
          "An error occurred while accessing the database. Please try again later."
      );
    } catch (Exception e) {
      showAlert(
          Alert.AlertType.ERROR,
          "Unexpected Error",
          "An unexpected error occurred. Please contact support if the issue persists."
      );
    }
  }

  /**
   * Shows an alert dialog.
   *
   * @param alertType The type of alert to display (e.g., ERROR, INFORMATION).
   * @param title     The title of the alert dialog.
   * @param message   The message content of the alert dialog.
   */
  private void showAlert(AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
