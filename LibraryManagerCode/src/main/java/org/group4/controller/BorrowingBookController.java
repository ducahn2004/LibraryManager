package org.group4.controller;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.dao.FactoryDAO;
import org.group4.module.books.BookItem;

import org.group4.module.manager.SessionManager;
import org.group4.module.transactions.BookLending;
import org.group4.module.enums.BookStatus;
import org.group4.module.users.Librarian;
import org.group4.module.users.Member;

public class BorrowingBookController {

  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();
  @FXML
  private Button cancelButton;
  @FXML
  private Label priceField;
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
  private Label barcodeField;
  @FXML
  private Label placeField;
  @FXML
  private Label subjectField;
  @FXML
  private Label languageField;
  @FXML
  private Label authorField;
  @FXML
  private Label noPageField;
  @FXML
  private Label isbnField;
  @FXML
  private Label titleField;
  @FXML
  private Label referenceOnlyCheck;

  private BookItem currentBookItem;
  private BookLending currentBookLending;

  public BookLending returnBookLending() {
    return this.currentBookLending;
  }

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
    for (Member member : FactoryDAO.getMemberDAO().getAll()) {
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
    for (Member member : FactoryDAO.getMemberDAO().getAll()) {
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
    placeField.setText(bookItem.getPlacedAt().getLocationIdentifier());
    priceField.setText(Double.toString(bookItem.getPrice()));
    referenceOnlyCheck.setText((bookItem.getIsReferenceOnly()) ? "Yes" : "No");
  }

  private void borrowingBook(BookItem bookItem, Member member) throws Exception {
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

  public void handleSubmit(ActionEvent actionEvent) throws Exception {

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
        currentBookLending = new BookLending(currentBookItem,
            returnMember(memberIdField.getText()));
        borrowingBook(currentBookLending.getBookItem(), currentBookLending.getMember());

        System.out.println(
            "MEMBER ID: " + currentBookLending.getMember().getMemberId() + " BORROWED");
        System.out.println("BarCode: " + currentBookLending.getBookItem().getBarcode() + " "
            + "BORROWED");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("The book has been successfully borrowed.");
        alert.showAndWait();

        loadBookDetail();
      }
    }
  }

  public void handleCancel(ActionEvent actionEvent) throws IOException, SQLException {
    loadBookDetail();
  }

  private void loadBookDetail() throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("BookDetails.fxml"));
    Scene bookDetailScene = new Scene(loader.load());

    Stage currentStage = (Stage) memberIdField.getScene().getWindow();

    currentStage.setScene(bookDetailScene);

    BookDetailsController bookDetailsController = loader.getController();
    bookDetailsController.setItemDetail(currentBookItem);
  }


}


