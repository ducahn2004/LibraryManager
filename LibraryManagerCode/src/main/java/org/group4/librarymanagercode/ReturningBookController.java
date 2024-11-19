package org.group4.librarymanagercode;

import org.group4.module.enums.BookStatus;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.group4.dao.FactoryDAO;
import org.group4.module.books.Book;
import org.group4.module.books.BookItem;
import org.group4.module.manager.SessionManager;
import org.group4.module.transactions.BookLending;
import org.group4.module.transactions.Fine;
import org.group4.module.users.Librarian;
import org.group4.module.users.Member;


public class ReturningBookController {

  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();
  @FXML
  private Button cancelButton;
  @FXML
  private DatePicker creationDatePicker;
  @FXML
  private DatePicker dueDatePicker;
  @FXML
  private DatePicker returnDatePicker;
  @FXML
  private CheckBox statusCheckBox;
  @FXML
  private Label feeField;
  @FXML
  private Label memberIdField;
  @FXML
  private Label memberNameField;
  @FXML
  private Label dobDatePicker;
  @FXML
  private Label emailField;
  @FXML
  private Label phoneField;
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

  private String previousPage;

  public void setPreviousPage(String previousPage) {
    this.previousPage = previousPage;
  }

  public void setItemDetailReturning(BookItem bookItem) throws SQLException {
    this.currentBookItem = bookItem;

    BookLending currentBookLending = findBookLendingById(bookItem.getBarcode());

    displayBookDetails(currentBookLending);

    displayMemberDetails(currentBookLending);

    setupReturnDetails(currentBookLending);

    calculateAndDisplayFine(currentBookLending);

    System.out.println("End show information");
  }

  private BookLending findBookLendingById(String bookItemBarCode) {
    if (bookItemBarCode == null || bookItemBarCode.isEmpty()) {
      throw new IllegalArgumentException("Barcode cannot be null or empty.");
    }

    BookLending foundBookItem = null;

    for (BookLending bookLending : FactoryDAO.getBookLendingDAO().getAll()) {
      if (bookLending.getBookItem().getBarcode().equals(bookItemBarCode)) {
        foundBookItem = bookLending;
        break;
      }
    }

    if (foundBookItem == null) {
      throw new IllegalArgumentException("Book lending not found for barcode: " + bookItemBarCode);
    }

    System.out.println("Found book lending:");
    System.out.println("ISBN: " + foundBookItem.getBookItem().getISBN());
    System.out.println("MEMBER ID: " + foundBookItem.getMember().getName());
    System.out.println("BarCode: " + foundBookItem.getBookItem().getBarcode());

    return foundBookItem;
  }

  private void displayBookDetails(BookLending currentBookLending) {
    if (currentBookLending == null) {
      throw new IllegalArgumentException("Book lending cannot be null.");
    }

    BookItem bookItem = currentBookLending.getBookItem();
    if (bookItem == null) {
      throw new IllegalArgumentException("Book item in lending cannot be null.");
    }

    isbnField.setText(bookItem.getISBN());
    titleField.setText(bookItem.getTitle());
    subjectField.setText(bookItem.getSubject());
    languageField.setText(bookItem.getLanguage());
    authorField.setText(bookItem.authorsToString());
    noPageField.setText(String.valueOf(bookItem.getNumberOfPages()));
    barcodeField.setText(bookItem.getBarcode());
    placeField.setText(bookItem.getPlacedAt().getLocationIdentifier());
    referenceOnlyCheck.setText(bookItem.getIsReferenceOnly() ? "Yes" : "No");
  }

  private void displayMemberDetails(BookLending currentBookLending) {
    if (currentBookLending == null) {
      throw new IllegalArgumentException("Book lending cannot be null.");
    }

    Member member = currentBookLending.getMember();
    if (member == null) {
      throw new IllegalArgumentException("Member in lending cannot be null.");
    }

    memberIdField.setText(member.getMemberId());
    memberNameField.setText(member.getName());
    dobDatePicker.setText(
        member.getDateOfBirth() != null ? member.getDateOfBirth().toString() : "");
    emailField.setText(member.getEmail());
    phoneField.setText(member.getPhoneNumber());
  }

  private void setupReturnDetails(BookLending currentBookLending) {
    if (currentBookLending == null) {
      throw new IllegalArgumentException("Book lending cannot be null.");
    }

    creationDatePicker.setValue(currentBookLending.getLendingDate());
    dueDatePicker.setValue(currentBookLending.getDueDate());
    returnDatePicker.setValue(LocalDate.now());
    currentBookLending.setReturnDate(LocalDate.now());

    BookItem bookItem = currentBookLending.getBookItem();
    if (bookItem == null) {
      throw new IllegalArgumentException("Book item in lending cannot be null.");
    }

    BookStatus status = statusCheckBox.isSelected() ? BookStatus.AVAILABLE : BookStatus.LOST;
    bookItem.setStatus(status);
  }

  // Tính và hiển thị tiền phạt
  private void calculateAndDisplayFine(BookLending currentBookLending) throws SQLException {
    if (currentBookLending == null) {
      throw new IllegalArgumentException("Book lending cannot be null.");
    }

    Fine amountFine = new Fine();
    double fineAmount = amountFine.calculateFine(currentBookLending);
    feeField.setText(Double.toString(fineAmount));
  }


  public void handleSubmit(ActionEvent actionEvent) throws Exception {
    returningBookToLibrary(findBookLendingById(currentBookItem.getBarcode()));
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText(null);
    alert.setContentText("The book has been successfully borrowed.");
    alert.showAndWait();

    if ("memberDetails".equals(previousPage)) {
      loadMemberDetail();
    } else {
      loadBookDetail();
    }
  }

  public void handleCancel(ActionEvent actionEvent) throws IOException, SQLException {
    if ("memberDetails".equals(previousPage)) {
      loadMemberDetail();
    } else {
      loadBookDetail();
    }
  }

  private void loadBookDetail() throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("BookDetails.fxml"));
    Scene bookDetailScene = new Scene(loader.load());

    Stage currentStage = (Stage) memberIdField.getScene().getWindow();

    currentStage.setScene(bookDetailScene);

    BookDetailsController bookDetailsController = loader.getController();
    bookDetailsController.setItemDetail(currentBookItem);
  }

  private void loadMemberDetail() throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberDetails.fxml"));
    Scene memberDetailScene = new Scene(loader.load());

    Stage currentStage = (Stage) memberIdField.getScene().getWindow();

    currentStage.setScene(memberDetailScene);

    BookDetailsController bookDetailsController = loader.getController();
    bookDetailsController.setItemDetail(currentBookItem);
  }

  public void handleUserAction(ActionEvent actionEvent) throws SQLException {
    boolean isChecked = statusCheckBox.isSelected();

    currentBookItem.setStatus(isChecked ? BookStatus.AVAILABLE : BookStatus.LOST);
    Fine amountFine = new Fine();
    double fineAmount = amountFine.calculateFine(findBookLendingById(currentBookItem.getBarcode()));
    feeField.setText(Double.toString(fineAmount));

    if (isChecked) {
      System.out.println("User has ticked the Status checkbox.");
    } else {
      System.out.println("User has not ticked the Status checkbox.");
    }
  }

  public static BookStatus getBookStatus(CheckBox availableCheckBox) {
    if (availableCheckBox.isSelected()) {
      return BookStatus.AVAILABLE;
    }
    return BookStatus.NONE; // Default case if no CheckBox is selected
  }

  private void returningBookToLibrary(BookLending bookLending) throws Exception {
    // Add the logic to store the book in the library's database
    boolean successAdded = librarian.returnBookItem(bookLending.getBookItem(),
        bookLending.getMember(), getBookStatus(statusCheckBox));
    if (!successAdded) {
      System.out.println("Failed to edit book with ISBN: " + bookLending.getBookItem().getISBN());
      throw new IllegalArgumentException("Book with the same ISBN already exists in the library.");
    }
    // TODO Uncomment after notification complete
    //SystemNotification.sendNotification(String type, String content);
    System.out.println(
        "Book with ISBN: " + bookLending.getBookItem().getISBN()
            + " has been edited successfully.");
  }

}
