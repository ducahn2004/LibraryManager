package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.module.books.Book;
import org.group4.module.books.BookItem;
import org.group4.module.books.Rack;
import org.group4.module.enums.BookFormat;
import org.group4.module.enums.BookStatus;
import org.group4.module.sessions.SessionManager;
import org.group4.module.users.Librarian;

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
  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();
  private Book currentBook;

  private BookItem bookItem;

  public void initialize() {
    // Khởi tạo formatComboBox với các giá trị từ enum BookFormat
    formatComboBox.getItems().addAll(BookFormat.values());
    formatComboBox.setValue(BookFormat.HARDCOVER);
  }

  public void cancel(ActionEvent actionEvent) {
    closeForm();
  }

  public void saveBookItem(ActionEvent actionEvent) {
    try {
      addBookItemToLibrary(); // Add the new member to the library (i.e., database)
      // After the member is successfully added to the library, update the parent controller's table.
      if (parentController != null) {
        parentController.addBookItemToList(bookItem);
      }
      // Show success message
      showAlert(Alert.AlertType.INFORMATION, "Add Book Item Successfully",
          "The book item has been added to the library.");
    } catch (IllegalArgumentException e) {
      showAlert(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
    }
    closeForm();
  }


  public void setParentController(BookDetailsController parentController) {
    this.parentController = parentController;
    this.currentBook = parentController.returnCurrentBook();
  }

  private void closeForm() {
    Stage stage = (Stage) saveButton.getScene().getWindow();
    stage.close();
  }

  private void addBookItemToLibrary() {
    bookItem = new BookItem(currentBook,
        referenceOnlyCheckBox.isSelected(), null, null,
        Double.parseDouble(priceTextField.getText()),
        formatComboBox.getValue(), BookStatus.AVAILABLE, dateOfPurchasePicker.getValue(),
        publicationDatePicker.getValue(), new Rack(1, placeAtTextField.getText()));
    boolean added = librarian.addBookItem(bookItem);
    if (!added) {
      System.out.println("ISBN Current Book is: " + currentBook.getISBN());
      throw new IllegalArgumentException("Could not add book Item to library");
    }
    //TODO: DECREASED ID MEMBER AFTER CREATE MEMBER DATA
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

  public void setCurrentBook(Book currentBook) {
    this.currentBook = currentBook;
  }
}
