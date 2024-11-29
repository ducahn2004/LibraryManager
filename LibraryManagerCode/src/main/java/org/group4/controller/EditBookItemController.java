package org.group4.controller;

import com.jfoenix.controls.JFXButton;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.model.book.BookItem;
import org.group4.model.book.Rack;
import org.group4.model.enums.BookFormat;
import org.group4.model.enums.BookStatus;

/**
 * Controller class for editing a book item. Handles UI interactions for editing details such as
 * book status, format, price, and placement information.
 */
public class EditBookItemController {

  @FXML
  private Label barCodeId; // Label displaying the barcode of the book item

  @FXML
  private CheckBox referenceOnlyCheckBox; // CheckBox for marking the book as reference-only

  @FXML
  private TextField priceTextField; // TextField for entering the price of the book

  @FXML
  private ComboBox<BookStatus> statusComboBox; // ComboBox for selecting the book's status

  @FXML
  private ComboBox<BookFormat> formatComboBox; // ComboBox for selecting the book's format

  @FXML
  private DatePicker dateOfPurchasePicker; // DatePicker for the book's purchase date

  @FXML
  private DatePicker publicationDatePicker; // DatePicker for the book's publication date

  @FXML
  private TextField placeAtTextField; // TextField for entering the rack location of the book

  @FXML
  private JFXButton saveButton; // Button for saving changes to the book item

  @FXML
  private JFXButton cancelButton; // Button for canceling the operation

  private BookItem currentItem; // The book item currently being edited

  /**
   * Sets the book item to be edited and populates the form fields with its details.
   *
   * @param item The book item to be edited.
   */
  public void setBookItem(BookItem item) {
    this.currentItem = item;
    populateFields();
  }

  /**
   * Populates the form fields with the details of the current book item. This method initializes
   * the UI elements with the current data for editing.
   */
  private void populateFields() {
    barCodeId.setText(currentItem.getBarcode());

    // Set whether the book is reference-only
    referenceOnlyCheckBox.setSelected(
        Boolean.parseBoolean((currentItem.getIsReferenceOnly() ? "Yes" : "No")));

    // Initialize the status ComboBox with available values and set the default
    statusComboBox.setItems(FXCollections.observableArrayList(BookStatus.values()));
    statusComboBox.setValue(currentItem.getStatus());

    // Set the price field
    priceTextField.setText(String.valueOf(currentItem.getPrice()));

    // Initialize the format ComboBox with available values and set the default
    formatComboBox.setItems(FXCollections.observableArrayList(BookFormat.values()));
    formatComboBox.setValue(currentItem.getFormat());

    // Set the date fields
    dateOfPurchasePicker.setValue(currentItem.getDateOfPurchase());
    publicationDatePicker.setValue(currentItem.getPublicationDate());

    // Set the rack location
    placeAtTextField.setText(currentItem.getPlacedAt().getLocationIdentifier());
  }

  /**
   * Saves the changes to the book item after validating the input fields. Ensures all required
   * fields are filled and updates the book item's details.
   */
  @FXML
  private void saveChanges() {
    // Check for missing required fields
    if (statusComboBox.getValue() == null
        || priceTextField.getText().isEmpty()
        || formatComboBox.getValue() == null
        || dateOfPurchasePicker.getValue() == null
        || publicationDatePicker.getValue() == null
        || placeAtTextField.getText().isEmpty()) {

      // Display an alert for incomplete fields
      showAlert(Alert.AlertType.WARNING, "Incomplete Information",
          "Please ensure all required fields are filled before saving.");
      return; // Stop further execution
    }
    if (dateOfPurchasePicker.getValue().isAfter(LocalDate.now())) {
      showAlert(Alert.AlertType.WARNING, "Wrong Information",
          "Date of purchase cannot be a future date.");
      return;
    }
    if (publicationDatePicker.getValue().isAfter(LocalDate.now())) {
      showAlert(Alert.AlertType.WARNING, "Wrong Information",
          "Publication Date cannot be a future date.");
      return;
    }
    try {
      // Update the current book item's details
      currentItem.setReferenceOnly(referenceOnlyCheckBox.isSelected());
      currentItem.setStatus(statusComboBox.getValue());
      currentItem.setPrice(Double.parseDouble(priceTextField.getText()));
      currentItem.setFormat(formatComboBox.getValue());
      currentItem.setDateOfPurchase(dateOfPurchasePicker.getValue());
      currentItem.setPublicationDate(publicationDatePicker.getValue());
      currentItem.setPlacedAt(new Rack(1, placeAtTextField.getText()));

      // Close the form window
      ((Stage) referenceOnlyCheckBox.getScene().getWindow()).close();

    } catch (NumberFormatException e) {
      // Handle invalid input for the price field
      showAlert(Alert.AlertType.ERROR, "Invalid Input",
          "Please enter a valid number for the price.");
    }
  }

  /**
   * Displays an alert dialog with the specified type, title, and message.
   *
   * @param alertType The type of alert (e.g., WARNING, ERROR).
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
   * Cancels the editing operation and closes the form.
   *
   * @param actionEvent The event triggered by clicking the cancel button.
   */
  public void cancel(ActionEvent actionEvent) {
    ((Stage) referenceOnlyCheckBox.getScene().getWindow()).close();
  }

  /**
   * Saves the current book item by invoking the saveChanges method.
   *
   * @param actionEvent The event triggered by clicking the save button.
   */
  public void saveBookItem(ActionEvent actionEvent) {
    saveChanges();
  }
}
