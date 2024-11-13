package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.base.books.BookItem;
import org.group4.base.catalog.Rack;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;

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
    referenceOnlyCheckBox.setSelected(Boolean.parseBoolean(currentItem.getReference()));
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
    currentItem.setReferenceOnly(referenceOnlyCheckBox.isSelected());
    currentItem.setStatus(statusComboBox.getValue());
    currentItem.setPrice(Double.parseDouble(priceTextField.getText()));
    currentItem.setFormat(formatComboBox.getValue());
    currentItem.setDateOfPurchase(dateOfPurchasePicker.getValue());
    currentItem.setPublicationDate(publicationDatePicker.getValue());
    currentItem.setPlacedAt(new Rack(1, placeAtTextField.getText()));
    ((Stage) referenceOnlyCheckBox.getScene().getWindow()).close();
  }

  public void cancel(ActionEvent actionEvent) {
  }

  public void saveBookItem(ActionEvent actionEvent) {
    saveChanges();
  }
}
