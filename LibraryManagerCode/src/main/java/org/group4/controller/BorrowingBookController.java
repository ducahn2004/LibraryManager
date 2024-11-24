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
import org.group4.dao.base.FactoryDAO;
import org.group4.model.book.BookItem;
import org.group4.model.enums.BookStatus;
import org.group4.service.user.SessionManagerService;
import org.group4.model.transaction.BookLending;
import org.group4.model.user.Librarian;
import org.group4.model.user.Member;

/**
 * Controller for managing the borrowing of books. Handles interactions between the user interface
 * and the business logic for borrowing books in the library system.
 */
public class BorrowingBookController {

  /**
   * The librarian currently managing the session.
   */
  private final Librarian librarian = SessionManagerService.getInstance().getCurrentLibrarian();

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

  /**
   * The book item currently being borrowed.
   */
  private BookItem currentBookItem;

  /**
   * The current borrowing transaction.
   */
  private BookLending currentBookLending;

  /**
   * Returns the current book lending transaction.
   *
   * @return The current {@code BookLending} object.
   */
  public BookLending returnBookLending() {
    return this.currentBookLending;
  }

  /**
   * Initializes the controller. Sets up listeners and prepares the user interface.
   */
  @FXML
  private void initialize() {
    // Add a listener to the memberIdField to trigger search on input
    memberIdField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.isEmpty()) {
        findMemberById(newValue);
      }
    });
  }

  /**
   * Searches for a member by ID and updates the UI with the member's details.
   *
   * @param memberId The ID of the member to find.
   */
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

  /**
   * Retrieves a member by their ID.
   *
   * @param memberId The ID of the member.
   * @return The {@code Member} object if found, otherwise null.
   */
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

  /**
   * Sets the details of the book to be borrowed in the UI.
   *
   * @param bookItem The book item to display.
   */
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

  /**
   * Handles the borrowing process for a book.
   *
   * @param bookItem The book item to be borrowed.
   * @param member   The member borrowing the book.
   * @throws Exception If the borrowing process fails.
   */
  private void borrowingBook(BookItem bookItem, Member member) throws Exception {
    boolean isBorrowed = librarian.getLendingManager().borrowBookItem(bookItem, member);
    if (!isBorrowed) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Warning");
      alert.setHeaderText("Borrowing Failed");
      alert.setContentText(
          "The book is not available or the member has reached the maximum number of books borrowed.");
      alert.showAndWait();
    }
  }

  /**
   * Handles the submission of the borrowing form.
   *
   * @param actionEvent The event triggered by clicking the submit button.
   * @throws Exception If the submission process fails.
   */
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

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("The book has been successfully borrowed.");
        alert.showAndWait();

        loadBookDetail();
      }
    }
  }

  /**
   * Cancels the borrowing process and reloads the book details view.
   *
   * @param actionEvent The event triggered by clicking the cancel button.
   * @throws IOException  If loading the book details view fails.
   * @throws SQLException If a database error occurs.
   */
  public void handleCancel(ActionEvent actionEvent) throws IOException, SQLException {
    loadBookDetail();
  }

  /**
   * Loads the book details view and updates the UI.
   *
   * @throws IOException  If loading the view fails.
   * @throws SQLException If a database error occurs.
   */
  private void loadBookDetail() throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("BookDetails.fxml"));
    Scene bookDetailScene = new Scene(loader.load());
    Stage currentStage = (Stage) memberIdField.getScene().getWindow();
    currentStage.setScene(bookDetailScene);

    BookDetailsController bookDetailsController = loader.getController();
    bookDetailsController.setItemDetail(currentBookItem);
  }
}
