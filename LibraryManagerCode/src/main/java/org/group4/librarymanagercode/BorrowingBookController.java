package org.group4.librarymanagercode;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.base.books.BookItem;

import org.group4.base.enums.BookStatus;
import org.group4.base.users.Librarian;
import org.group4.base.users.Member;
import org.group4.database.LibrarianDatabase;
import org.group4.database.MemberDatabase;

public class BorrowingBookController {

  private static final Librarian librarian = LibrarianDatabase.getInstance().getItems().getFirst();
  public Button cancelButton;
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

  private Member returnMember(String memberId) {
    Member foundMember = null;
    for (Member member : MemberDatabase.getInstance().getItems()) {
      if (member.getMemberId().equals(memberId)) {
        foundMember = member;
        break;
      }
    }
    return foundMember;
  }

  public void setItemDetailBorrowing(BookItem bookItem) {
    this.currentBookItem = bookItem;
    isbnField.setText(bookItem.getISBN());
    titleField.setText(bookItem.getTitle());
    subjectField.setText(bookItem.getSubject());
    languageField.setText(bookItem.getLanguage());
    authorField.setText(bookItem.authorsToString());
    noPageField.setText(String.valueOf(bookItem.getNumberOfPages()));

    barcodeField.setText(bookItem.getBarcode());
    placeField.setText(bookItem.getPlacedAt().toString());
    referenceOnlyCheck.setSelected(bookItem.getReference().equals(true));
  }

  private void borrowingBook(BookItem bookItem, Member member) {
    boolean isBorrowed = librarian.borrowBookItem(bookItem, member);
    if (!isBorrowed) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Warning");
      alert.setHeaderText("Borrowing Failed");
      alert.setContentText(
          "The book is not available or the member has reached the maximum number of books borrowed.");
      alert.showAndWait();
    }
  }

  public void handleSubmit(ActionEvent actionEvent) throws IOException {

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
        borrowingBook(currentBookItem, returnMember(memberIdField.getText()));
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("The book has been successfully borrowed.");
        alert.showAndWait();

//        Stage stage = (Stage) memberIdField.getScene().getWindow();
//        stage.close();
        loadBookDetail();
      }
    }
  }


  public void handleCancel(ActionEvent actionEvent) throws IOException {
//    Stage stage = (Stage) memberIdField.getScene().getWindow();
//    stage.close();
    loadBookDetail();
  }

  private void loadBookDetail() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("BookDetails.fxml"));
    Scene bookDetailScene = new Scene(loader.load());

    Stage currentStage = (Stage) memberIdField.getScene().getWindow();

    currentStage.setScene(bookDetailScene);

    BookDetailsController bookDetailsController = loader.getController();
    bookDetailsController.setItemDetail(currentBookItem);
  }
}


