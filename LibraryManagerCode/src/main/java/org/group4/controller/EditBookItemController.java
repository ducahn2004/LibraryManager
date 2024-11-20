package org.group4.controller;

import com.jfoenix.controls.JFXButton;
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
import org.group4.module.books.BookItem;
import org.group4.module.books.Rack;
import org.group4.module.enums.BookFormat;
import org.group4.module.enums.BookStatus;

public class EditBookItemController {


  @FXML
  private Label barCodeId;
  @FXML
  private CheckBox referenceOnlyCheckBox;
  @FXML
  private TextField priceTextField;
  @FXML
  private ComboBox<BookStatus> statusComboBox;

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

  private BookItem currentItem;

  public void setBookItem(BookItem item) {
    this.currentItem = item;
    populateFields();
  }

  private void populateFields() {
    barCodeId.setText(currentItem.getBarcode());
    referenceOnlyCheckBox.setSelected(
        Boolean.parseBoolean((currentItem.getIsReferenceOnly() ? "Yes" : "No")));
    statusComboBox.setItems(FXCollections.observableArrayList(BookStatus.values()));
    statusComboBox.setValue(currentItem.getStatus()); // Đặt giá trị mặc định

    priceTextField.setText(String.valueOf(currentItem.getPrice()));
    formatComboBox.setItems(FXCollections.observableArrayList(BookFormat.values()));
    formatComboBox.setValue(currentItem.getFormat()); // Đặt giá trị mặc định

    dateOfPurchasePicker.setValue(currentItem.getDateOfPurchase());
    publicationDatePicker.setValue(currentItem.getPublicationDate());

    placeAtTextField.setText(currentItem.getPlacedAt().getLocationIdentifier());
  }

  @FXML
  private void saveChanges() {
    // Check for missing required fields
    if (statusComboBox.getValue() == null ||
        priceTextField.getText().isEmpty() ||
        formatComboBox.getValue() == null ||
        dateOfPurchasePicker.getValue() == null ||
        publicationDatePicker.getValue() == null ||
        placeAtTextField.getText().isEmpty()) {

      showAlert(Alert.AlertType.WARNING, "Incomplete Information",
          "Please ensure all required fields are filled before saving.");
      return; // Stop execution
    }

    try {
      // Save changes to currentItem
      currentItem.setReferenceOnly(referenceOnlyCheckBox.isSelected());
      currentItem.setStatus(statusComboBox.getValue());
      currentItem.setPrice(Double.parseDouble(priceTextField.getText()));
      currentItem.setFormat(formatComboBox.getValue());
      currentItem.setDateOfPurchase(dateOfPurchasePicker.getValue());
      currentItem.setPublicationDate(publicationDatePicker.getValue());
      currentItem.setPlacedAt(new Rack(1, placeAtTextField.getText()));

      // Close the form
      ((Stage) referenceOnlyCheckBox.getScene().getWindow()).close();
    } catch (NumberFormatException e) {
      // Handle invalid price input
      showAlert(Alert.AlertType.ERROR, "Invalid Input",
          "Please enter a valid number for the price.");
    }
  }

  /**
   * Shows an alert dialog with specified type, title, and message.
   *
   * @param alertType Type of alert
   * @param title     Title of the alert
   * @param message   Message content of the alert
   */
  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }


  public void cancel(ActionEvent actionEvent) {
  }

  public void saveBookItem(ActionEvent actionEvent) {
    saveChanges();
  }

}
