package org.group4.controller;

import org.group4.model.enums.BookStatus;
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
import org.group4.dao.base.FactoryDAO;
import org.group4.model.book.BookItem;
import org.group4.service.transaction.FineCalculationService;
import org.group4.service.user.SessionManagerService;
import org.group4.model.transaction.BookLending;
import org.group4.model.transaction.Fine;
import org.group4.model.user.Librarian;
import org.group4.model.user.Member;

/**
 * Controller class for handling book return actions in the library system. This class allows for
 * displaying book details, member details, and fine information when a book is returned.
 */
public class ReturningBookController {

  // The current librarian session
  private final Librarian librarian = SessionManagerService.getInstance().getCurrentLibrarian();

  // FXML components for UI
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

  // Current book item being processed
  private BookItem currentBookItem;

  // Previous page to navigate to after returning the book
  private String previousPage;

  /**
   * Sets the previous page for navigation purposes.
   *
   * @param previousPage the previous page's identifier
   */
  public void setPreviousPage(String previousPage) {
    this.previousPage = previousPage;
  }

  /**
   * Sets the book details for returning based on the provided book item.
   *
   * @param bookItem the book item to be returned
   * @throws SQLException if there is an error with the database
   */
  public void setItemDetailReturning(BookItem bookItem) throws SQLException {
    this.currentBookItem = bookItem;

    BookLending currentBookLending = findBookLendingById(bookItem.getBarcode());

    // Display book and member details and setup return details
    displayBookDetails(currentBookLending);
    displayMemberDetails(currentBookLending);
    setupReturnDetails(currentBookLending);
    calculateAndDisplayFine(currentBookLending);

    System.out.println("End show information");
  }

  /**
   * Finds a book lending record by the barcode of the book item.
   *
   * @param bookItemBarCode the barcode of the book item
   * @return the found BookLending object
   * @throws IllegalArgumentException if the barcode is invalid or the book lending is not found
   */
  private BookLending findBookLendingById(String bookItemBarCode) {
    if (bookItemBarCode == null || bookItemBarCode.isEmpty()) {
      throw new IllegalArgumentException("Barcode cannot be null or empty.");
    }

    BookLending foundBookItem = null;

    // Search through all book lendings to find the corresponding one
    for (BookLending bookLending : FactoryDAO.getBookLendingDAO().getAll()) {
      if (bookLending.getBookItem().getBarcode().equals(bookItemBarCode)) {
        foundBookItem = bookLending;
        break;
      }
    }

    // If the lending is not found, throw an exception
    if (foundBookItem == null) {
      throw new IllegalArgumentException("Book lending not found for barcode: " + bookItemBarCode);
    }

    System.out.println("Found book lending:");
    System.out.println("ISBN: " + foundBookItem.getBookItem().getISBN());
    System.out.println("MEMBER ID: " + foundBookItem.getMember().getName());
    System.out.println("BarCode: " + foundBookItem.getBookItem().getBarcode());

    return foundBookItem;
  }

  /**
   * Displays the details of the book being returned.
   *
   * @param currentBookLending the BookLending object containing the book details
   * @throws IllegalArgumentException if the BookLending or BookItem is null
   */
  private void displayBookDetails(BookLending currentBookLending) {
    if (currentBookLending == null) {
      throw new IllegalArgumentException("Book lending cannot be null.");
    }

    BookItem bookItem = currentBookLending.getBookItem();
    if (bookItem == null) {
      throw new IllegalArgumentException("Book item in lending cannot be null.");
    }

    // Set the values in the UI labels
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

  /**
   * Displays the member details for the member who borrowed the book.
   *
   * @param currentBookLending the BookLending object containing the member details
   * @throws IllegalArgumentException if the BookLending or Member is null
   */
  private void displayMemberDetails(BookLending currentBookLending) {
    if (currentBookLending == null) {
      throw new IllegalArgumentException("Book lending cannot be null.");
    }

    Member member = currentBookLending.getMember();
    if (member == null) {
      throw new IllegalArgumentException("Member in lending cannot be null.");
    }

    // Set the member details in the UI labels
    memberIdField.setText(member.getMemberId());
    memberNameField.setText(member.getName());
    dobDatePicker.setText(
        member.getDateOfBirth() != null ? member.getDateOfBirth().toString() : "");
    emailField.setText(member.getEmail());
    phoneField.setText(member.getPhoneNumber());
  }

  /**
   * Sets up the return date and status for the book being returned.
   *
   * @param currentBookLending the BookLending object containing the return details
   * @throws IllegalArgumentException if the BookLending or BookItem is null
   */
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

    // Set the status of the book based on the checkbox
    BookStatus status = statusCheckBox.isSelected() ? BookStatus.AVAILABLE : BookStatus.LOST;
    bookItem.setStatus(status);
  }

  /**
   * Calculates and displays any fines that may be applicable when returning the book.
   *
   * @param currentBookLending the BookLending object containing the lending details
   * @throws SQLException if there is an error calculating the fine
   */
  private void calculateAndDisplayFine(BookLending currentBookLending) throws SQLException {
    if (currentBookLending == null) {
      throw new IllegalArgumentException("Book lending cannot be null.");
    }

    Fine amountFine = new Fine(currentBookLending);
    double fineAmount = FineCalculationService.calculateFine(amountFine);
    feeField.setText(Double.toString(fineAmount));
  }

  /**
   * Handles the action of submitting the return of the book to the library system. Displays a
   * success message and navigates to the appropriate page.
   *
   * @param actionEvent the action event triggering the submission
   * @throws Exception if there is an error during the submission process
   */
  public void handleSubmit(ActionEvent actionEvent) throws Exception {
    returningBookToLibrary(findBookLendingById(currentBookItem.getBarcode()));
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText(null);
    alert.setContentText("The book has been successfully borrowed.");
    alert.showAndWait();

    // Navigate to the previous page
    if ("memberDetails".equals(previousPage)) {
      loadMemberDetail();
    } else {
      loadBookDetail();
    }
  }

  /**
   * Handles the action of canceling the return and navigating to the previous page.
   *
   * @param actionEvent the action event triggering the cancel action
   * @throws IOException  if there is an error loading the previous page
   * @throws SQLException if there is an error with the database
   */
  public void handleCancel(ActionEvent actionEvent) throws IOException, SQLException {
    if ("memberDetails".equals(previousPage)) {
      loadMemberDetail();
    } else {
      loadBookDetail();
    }
  }

  /**
   * Loads the book detail page for further actions.
   *
   * @throws IOException  if there is an error loading the page
   * @throws SQLException if there is an error with the database
   */
  private void loadBookDetail() throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("BookDetails.fxml"));
    Scene bookDetailScene = new Scene(loader.load());

    Stage currentStage = (Stage) memberIdField.getScene().getWindow();
    currentStage.setScene(bookDetailScene);

    BookDetailsController bookDetailsController = loader.getController();
    bookDetailsController.setItemDetail(currentBookItem);
  }

  /**
   * Loads the member detail page for further actions.
   *
   * @throws IOException  if there is an error loading the page
   * @throws SQLException if there is an error with the database
   */
  private void loadMemberDetail() throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberDetails.fxml"));
    Scene memberDetailScene = new Scene(loader.load());

    Stage currentStage = (Stage) memberIdField.getScene().getWindow();
    currentStage.setScene(memberDetailScene);

    BookDetailsController bookDetailsController = loader.getController();
    bookDetailsController.setItemDetail(currentBookItem);
  }

  /**
   * Handles user actions for updating the status of the book (Available or Lost).
   *
   * @param actionEvent the action event triggering the status change
   * @throws SQLException if there is an error with the database
   */
  public void handleUserAction(ActionEvent actionEvent) throws SQLException {
    boolean isChecked = statusCheckBox.isSelected();

    // Update the book status based on the checkbox
    currentBookItem.setStatus(isChecked ? BookStatus.AVAILABLE : BookStatus.LOST);
    Fine amountFine = new Fine(findBookLendingById(currentBookItem.getBarcode()));
    double fineAmount = FineCalculationService.calculateFine(amountFine);
    feeField.setText(Double.toString(fineAmount));

    if (isChecked) {
      System.out.println("User has ticked the Status checkbox.");
    } else {
      System.out.println("User has not ticked the Status checkbox.");
    }
  }

  /**
   * Retrieves the book status based on the state of the checkbox.
   *
   * @param availableCheckBox the checkbox indicating whether the book is available
   * @return the BookStatus of the book
   */
  public static BookStatus getBookStatus(CheckBox availableCheckBox) {
    if (availableCheckBox.isSelected()) {
      return BookStatus.AVAILABLE;
    }
    return BookStatus.LOST; // Default case if no CheckBox is selected
  }

  /**
   * Handles the logic for returning the book to the library system.
   *
   * @param bookLending the BookLending object representing the lending details
   * @throws Exception if there is an error during the return process
   */
  private void returningBookToLibrary(BookLending bookLending) throws Exception {
    // Return the book item to the library system
    boolean successAdded = librarian.getBookLendingManager().returnBookItem(bookLending.getBookItem(),
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
