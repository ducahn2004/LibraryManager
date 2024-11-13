package org.group4.librarymanagercode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.base.books.BookItem;
import org.group4.base.enums.BookStatus;
import org.group4.base.users.Member;
import org.group4.database.MemberDatabase;

public class BorrowingBookController {

  @FXML
  private TextField memberIdField;
  @FXML
  private TextField memberNameField;
  @FXML
  private DatePicker dobDatePicker;
  @FXML
  private TextField emailField;
  @FXML
  private TextField phoneField;
  @FXML
  private TextField barcodeField;
  @FXML
  private TextField placeField;
  @FXML
  private TextField subjectField;
  @FXML
  private TextField languageField;
  @FXML
  private TextField authorField;
  @FXML
  private TextField noPageField;
  @FXML
  private TextField isbnField;
  @FXML
  private TextField titleField;
  @FXML
  private CheckBox referenceOnlyCheck;

  private BookItem currentBookItem;

  @FXML
  private void initialize() {
    // Add a listener to the memberIdField to trigger search on input
    memberIdField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.isEmpty()) {
        findMemberById(newValue);
      }
    });
  }

  private void findMemberById(String memberId) {
    Member foundMember = null;
    for (Member member : MemberDatabase.getInstance().getItems()) {
      if (member.getMemberId().equals(memberId)) {
        foundMember = member;
        break;
      }
    }
    if (foundMember != null) {
      memberNameField.setText(foundMember.getName());
      dobDatePicker.setValue(foundMember.getDateOfBirth());
      emailField.setText(foundMember.getEmail());
      phoneField.setText(foundMember.getPhoneNumber());
    } else {
      // Clear fields if no member is found
      memberNameField.clear();
      emailField.clear();
      phoneField.clear();
    }
  }

  public void setItemDetailBorrowing(BookItem bookItem) {
    this.currentBookItem = bookItem;
    isbnField.setText(bookItem.getISBN());
    titleField.setText(bookItem.getTitle());
    subjectField.setText(bookItem.getSubject());
    languageField.setText(bookItem.getLanguage());
    authorField.setText(bookItem.getAuthors().toString());
    noPageField.setText(String.valueOf(bookItem.getNumberOfPages()));

    barcodeField.setText(bookItem.getBarcode());
    placeField.setText(bookItem.getPlacedAt().toString());
    bookItem.getReference();
    referenceOnlyCheck.setSelected(bookItem.getReference().equals(true));
  }

  public void handleSubmit(ActionEvent actionEvent) {

    if (memberNameField.getText().isEmpty() || memberIdField.getText().isEmpty() ||
        emailField.getText().isEmpty() || phoneField.getText().isEmpty()) {

      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Warning");
      alert.setHeaderText("Incomplete Member Information");
      alert.setContentText(
          "Please enter the member information to proceed with borrowing the book.");
      alert.showAndWait();
    } else {
      if (currentBookItem != null && currentBookItem.getStatus() == BookStatus.AVAILABLE) {
        currentBookItem.setStatus(BookStatus.LOANED);
        BookDetailsController bookDetailsController = new BookDetailsController();
        bookDetailsController.loadData();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("The book has been successfully borrowed.");
        alert.showAndWait();

        Stage stage = (Stage) memberIdField.getScene().getWindow();
        stage.close();
      }
    }
  }


  public void handleCancel(ActionEvent actionEvent) {
    // Đóng cửa sổ hiện tại khi nhấn "Cancel"
    Stage stage = (Stage) memberIdField.getScene().getWindow();
    stage.close();
  }
}


