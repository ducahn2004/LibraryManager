package org.group4.librarymanagercode;

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
import org.group4.module.sessions.SessionManager;
import org.group4.module.transactions.BookLending;
import org.group4.module.enums.BookStatus;
import org.group4.module.transactions.Fine;
import org.group4.module.users.Librarian;


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
    Fine amountFine = new Fine();
    System.out.println("Set up setItemDetailReturning");
    System.out.println("ISBN: " + currentBookLending.getBookItem().getISBN() + " BORROWED");
    System.out.println("MEMBER ID: " + currentBookLending.getMember().getName() + " BORROWED");
    System.out.println("BarCode" + currentBookLending.getBookItem().getBarcode() + " BORROWED");
    isbnField.setText(bookItem.getISBN());
    titleField.setText(bookItem.getTitle());
    subjectField.setText(bookItem.getSubject());
    languageField.setText(bookItem.getLanguage());
    authorField.setText(bookItem.authorsToString());
    noPageField.setText(String.valueOf(bookItem.getNumberOfPages()));

    barcodeField.setText(bookItem.getBarcode());
    placeField.setText(bookItem.getPlacedAt().getLocationIdentifier());
    referenceOnlyCheck.setText(bookItem.getIsReferenceOnly() ? "Yes" : "No");

    memberIdField.setText(currentBookLending.getMember().getMemberId());
    memberNameField.setText(currentBookLending.getMember().getName());
    dobDatePicker.setText(currentBookLending.getMember().getDateOfBirth().toString());
    emailField.setText(currentBookLending.getMember().getEmail());
    phoneField.setText(currentBookLending.getMember().getPhoneNumber());

    creationDatePicker.setValue(currentBookLending.getLendingDate());
    dueDatePicker.setValue(currentBookLending.getDueDate());
    returnDatePicker.setValue(LocalDate.now());
    currentBookLending.setReturnDate(LocalDate.now());
    currentBookLending.getBookItem()
        .setStatus(statusCheckBox.isSelected() ? BookStatus.AVAILABLE : BookStatus.LOST);
    double fineAmount = amountFine.calculateFine(currentBookLending);
    feeField.setText(Double.toString(fineAmount));

    System.out.println("End show information");
  }

  private BookLending findBookLendingById(String bookItemBarCode) {
    BookLending foundBookItem = null;
    for (BookLending bookLending : FactoryDAO.getBookLendingDAO().getAll()) {
      if (bookLending.getBookItem().getBarcode().equals(bookItemBarCode)) {
        foundBookItem = bookLending;
        break;
      }
    }
    System.out.println("Throw book Lending");
    assert foundBookItem != null;
    System.out.println("ISBN: " + foundBookItem.getBookItem().getISBN() + " BORROWED");
    System.out.println("MEMBER ID: " + foundBookItem.getMember().getName() + " BORROWED");
    System.out.println("BarCode" + foundBookItem.getBookItem().getBarcode() + " BORROWED");
    System.out.println("End throw book Lending");
    return foundBookItem;
  }


  public void handleSubmit(ActionEvent actionEvent) throws IOException, SQLException {
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

    // Cập nhật trạng thái và tính toán lại phí phạt
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

  private void returningBookToLibrary(BookLending bookLending) {
    // Add the logic to store the book in the library's database
    boolean successAdded = librarian.returnBookItem(bookLending);
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
