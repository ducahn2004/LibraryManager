package org.group4.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.model.book.BookItem;
import org.group4.model.enums.BookStatus;
import org.group4.service.user.SessionManager;
import org.group4.model.transaction.BookLending;
import org.group4.model.user.Librarian;
import org.group4.model.user.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for managing the borrowing of books. Handles interactions between the user interface
 * and the business logic for borrowing books in the library system.
 */
public class BorrowingBookController {

  private static final Logger logger = LoggerFactory.getLogger(BorrowingBookController.class);

  /**
   * The logger for the BorrowingBookController class.
   */
  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();
  public Button cancelButton;

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

  private void findMemberById(String memberId) {
    try {
      Optional<Member> memberOptional = librarian.getMemberManager().getById(memberId);
      if (memberOptional.isPresent()) {
        Member foundMember = memberOptional.get();
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
    } catch (SQLException e) {
      // Handle SQL exceptions if there are issues interacting with the database
      showAlert(Alert.AlertType.ERROR, "Database Error",
          "An error occurred while searching for the member. Please try again later.");
      logger.error("SQLException in findMemberById: {}", e.getMessage(), e);
    }
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
   */
  private void borrowingBook(BookItem bookItem, Member member) {
    try {
      boolean isBorrowed = librarian.getLendingManager().borrowBookItem(bookItem, member);
      if (!isBorrowed) {
        // Show an alert if the book is not successfully borrowed
        showAlert(AlertType.ERROR, "Book Not Borrowed",
            "The book could not be borrowed. Please try again later.");
      }
    } catch (SQLException e) {
      // Handle SQL exceptions if there are issues interacting with the database
      showAlert(AlertType.ERROR, "Database Error",
          "An error occurred while borrowing the book. Please try again later.");
      logger.error("SQLException in borrowingBook: {}", e.getMessage(), e);
    }
  }

  /**
   * Handles the submission of the borrowing form. Validates the input fields and borrows the book.
   *
   * @param actionEvent The event triggered by clicking the submit button.
   */
  public void handleSubmit(ActionEvent actionEvent) {
    try {
      // Check if any of the required fields are empty
      if (memberNameField.getText().isEmpty() || memberIdField.getText().isEmpty() ||
          emailField.getText().isEmpty() || phoneField.getText().isEmpty()) {

        // Show an alert if any of the required fields are empty
        showAlert(Alert.AlertType.WARNING, "Incomplete Information",
            "Please ensure all required fields are filled before submitting the form.");
        return;
      }
      // Check if the book item is available
      if (currentBookItem != null && currentBookItem.getStatus() == BookStatus.AVAILABLE) {
        // Proceed with creating the lending record and borrowing the book
        Optional<Member> memberOptional = librarian.getMemberManager()
            .getById(memberIdField.getText());
        if (memberOptional.isEmpty()) {
          showAlert(AlertType.ERROR, "Member Not Found",
              "The member with the given ID was not found.");
          return;
        }

        BookLending currentBookLending = new BookLending(currentBookItem, memberOptional.get());
        borrowingBook(currentBookLending.getBookItem(), currentBookLending.getMember());

        // Show an alert if the book is successfully borrowed
        showAlert(AlertType.INFORMATION, "Book Borrowed",
            "The book has been successfully borrowed.");

        // Load the book detail page after successful borrowing
        loadBookDetail();
      }
    } catch (SQLException e) {
      // Handle SQL exceptions if there are issues interacting with the database
      showAlert(AlertType.ERROR, "Database Error",
          "An error occurred while access borrowing book database. Please try again later.");
      logger.error("SQLException in handleSubmit: {}", e.getMessage(), e);
    } catch (Exception e) {
      // Catch any other unexpected exceptions
      showAlert(AlertType.ERROR, "Unexpected Error",
          "An unexpected error occurred in borrowing book. Please try again later.");
      logger.error("Unexpected error in handleSubmit: {}", e.getMessage(), e);
    }
  }

  /**
   * Cancels the borrowing process and reloads the book details view.
   *
   * @param actionEvent The event triggered by clicking the cancel button.
   */
  public void handleCancel(ActionEvent actionEvent) {
    loadBookDetail();
  }

  /**
   * Loads the book details view.
   */
  private void loadBookDetail() {
    Stage currentStage = (Stage) memberIdField.getScene().getWindow();
    SceneLoader.loadBookDetail(currentStage, currentBookItem);
  }

  /**
   * Shows an alert with the provided parameters.
   *
   * @param alertType the type of the alert (e.g., ERROR, INFORMATION)
   * @param title     the title of the alert
   * @param message   the content message of the alert
   */
  private void showAlert(AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(title);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
