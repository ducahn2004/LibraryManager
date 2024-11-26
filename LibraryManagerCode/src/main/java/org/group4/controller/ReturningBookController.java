package org.group4.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.group4.model.book.BookItem;
import org.group4.model.transaction.BookLending;
import org.group4.model.transaction.Fine;
import org.group4.model.user.Librarian;
import org.group4.model.user.Member;
import org.group4.model.enums.BookStatus;
import org.group4.service.transaction.FineCalculationService;
import org.group4.service.user.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class for returning a book to the library system. Handles the UI interactions for
 * returning a book, calculating fines, and updating the book status.
 */
public class ReturningBookController {

  /**
   * The current librarian using the system.
   */
  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();

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

  private static final Logger logger = LoggerFactory.getLogger(ReturningBookController.class);

  private BookItem currentBookItem;
  private String previousPage;
  private Member currentMember;

  /**
   * Sets the previous page for navigation purposes.
   *
   * @param previousPage the previous page's identifier
   */
  public void setPreviousPage(String previousPage) {
    this.previousPage = previousPage;
  }

  /**
   * Sets the book item details for returning the book.
   *
   * @param bookItem the book item to be returned
   */
  public void setItemDetailReturning(BookItem bookItem) {
    try {
      this.currentBookItem = bookItem;

      BookLending currentBookLending = findBookLendingById(bookItem.getBarcode());
      assert currentBookLending != null;
      this.currentMember = currentBookLending.getMember();
      // Display book and member details and setup return details
      displayBookDetails(currentBookLending);
      displayMemberDetails(currentBookLending);
      setupReturnDetails(currentBookLending);
      calculateAndDisplayFine(currentBookLending);

      logger.info("End show information");

    } catch (Exception e) {
      // Catch other unexpected exceptions
      logger.error("Unexpected error occurred in returning book: {}", e.getMessage(), e);

      // Show an alert for the unexpected error
      showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred in returning book.");
    }
  }

  private BookLending findBookLendingById(String bookItemBarCode) {
    try {
      if (bookItemBarCode == null || bookItemBarCode.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Invalid Barcode", "Barcode cannot be null or empty.");
        return null;
      }

      BookLending foundBookItem = null;

      // Search through all book lendings to find the corresponding one
      for (BookLending bookLending : librarian.getLendingManager().getAll()) {
        if (bookLending.getBookItem().getBarcode().equals(bookItemBarCode)) {
          foundBookItem = bookLending;
          break;
        }
      }

      if (foundBookItem == null) {
        showAlert(Alert.AlertType.ERROR, "Book Lending Not Found",
            "No lending found for the barcode: " + bookItemBarCode);
        return null;
      }

      logger.info("Found book lending: ISBN: {}, MEMBER ID: {}, BarCode: {}",
          foundBookItem.getBookItem().getISBN(),
          foundBookItem.getMember().getName(),
          foundBookItem.getBookItem().getBarcode());

      return foundBookItem;

    } catch (SQLException e) {
      logger.error("SQL error while finding book lending: {}", e.getMessage(), e);
      showAlert(Alert.AlertType.ERROR, "Database Error",
          "An error occurred while accessing the database in find book lending by Id.");
      return null;
    } catch (Exception e) {
      logger.error("Unexpected error: {}", e.getMessage(), e);
      showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred.");
      return null;
    }
  }

  /**
   * Displays the book details for the book being returned.
   *
   * @param currentBookLending the BookLending object containing the book details
   */
  private void displayBookDetails(BookLending currentBookLending) {
    if (currentBookLending == null) {
      showAlert(AlertType.ERROR, "Error", "The book lending information is missing.");
      return;
    }

    BookItem bookItem = currentBookLending.getBookItem();
    if (bookItem == null) {
      showAlert(AlertType.ERROR, "Error", "The book item is missing in the lending record.");
      return;
    }

    // Set values in the UI labels
    isbnField.setText(bookItem.getISBN() != null
        ? bookItem.getISBN() : "N/A");
    titleField.setText(bookItem.getTitle() != null
        ? bookItem.getTitle() : "Unknown Title");
    subjectField.setText(bookItem.getSubject() != null
        ? bookItem.getSubject() : "Unknown Subject");
    languageField.setText(bookItem.getLanguage() != null
        ? bookItem.getLanguage() : "Unknown");
    authorField.setText(bookItem.authorsToString() != null
        ? bookItem.authorsToString() : "Unknown Author(s)");
    noPageField.setText(bookItem.getNumberOfPages() > 0
        ? String.valueOf(bookItem.getNumberOfPages()) : "N/A");
    barcodeField.setText(bookItem.getBarcode() != null
        ? bookItem.getBarcode() : "N/A");
    placeField.setText(bookItem.getPlacedAt() != null
        ? bookItem.getPlacedAt().getLocationIdentifier() : "Unknown Location");
    referenceOnlyCheck.setText(bookItem.getIsReferenceOnly() ? "Yes" : "No");

    // Optionally log the details
    logger.info("Displaying book details: ISBN={}, Title={}", bookItem.getISBN(),
        bookItem.getTitle());
  }

  /**
   * Displays the member details for the member who borrowed the book.
   *
   * @param currentBookLending the BookLending object containing the member details
   */
  private void displayMemberDetails(BookLending currentBookLending) {
    try {
      if (currentBookLending == null) {
        // Show alert if BookLending is null
        showAlert(AlertType.ERROR, "Invalid Data", "Book lending cannot be null.");
        return;
      }

      Member member = currentBookLending.getMember();
      if (member == null) {
        // Show alert if Member is null
        showAlert(AlertType.ERROR, "Invalid Data", "Member in lending cannot be null.");
        return;
      }

      // Set the member details in the UI labels
      memberIdField.setText(member.getMemberId());
      memberNameField.setText(member.getName());
      dobDatePicker.setText(
          member.getDateOfBirth() != null ? member.getDateOfBirth().toString() : "");
      emailField.setText(member.getEmail());
      phoneField.setText(member.getPhoneNumber());
    } catch (Exception e) {
      // Catch any unexpected exceptions and show an alert
      showAlert(AlertType.ERROR, "Error",
          "An unexpected error occurred while displaying member details.");
    }
  }

  /**
   * Sets up the return date and status for the book being returned.
   *
   * @param currentBookLending the BookLending object containing the return details
   */
  private void setupReturnDetails(BookLending currentBookLending) {
    try {
      if (currentBookLending == null) {
        // Show an alert if the BookLending is null
        showAlert(AlertType.ERROR, "Invalid Data", "Book lending cannot be null.");
        return;
      }

      // Set the return details
      creationDatePicker.setValue(currentBookLending.getLendingDate());
      dueDatePicker.setValue(currentBookLending.getDueDate());
      returnDatePicker.setValue(LocalDate.now());
      currentBookLending.setReturnDate(LocalDate.now());

      BookItem bookItem = currentBookLending.getBookItem();
      if (bookItem == null) {
        // Show an alert if the BookItem is null
        showAlert(AlertType.ERROR, "Invalid Data", "Book item in lending cannot be null.");
        return;
      }

      // Set the status of the book based on the checkbox
      BookStatus status = statusCheckBox.isSelected() ? BookStatus.AVAILABLE : BookStatus.LOST;
      bookItem.setStatus(status);

    } catch (Exception e) {
      // Catch any unexpected exceptions and show an alert
      showAlert(AlertType.ERROR, "Error",
          "An unexpected error occurred while setting up return details.");
    }
  }

  /**
   * Calculates and displays any fines that may be applicable when returning the book.
   *
   * @param currentBookLending the BookLending object containing the lending details
   */
  private void calculateAndDisplayFine(BookLending currentBookLending) {
    try {
      if (currentBookLending == null) {
        // Show an alert if the BookLending is null
        showAlert(AlertType.ERROR, "Invalid Data", "Book lending cannot be null.");
        return;
      }

      Fine amountFine = new Fine(currentBookLending);
      double fineAmount = FineCalculationService.calculateFine(amountFine);
      feeField.setText(Double.toString(fineAmount));

    } catch (SQLException e) {
      // Handle SQLException if it occurs and show an alert
      showAlert(AlertType.ERROR, "Database Error",
          "An error occurred while calculating the fine in calculator fine.");
    } catch (Exception e) {
      // Handle any other unexpected exceptions
      showAlert(AlertType.ERROR, "Error",
          "An unexpected error occurred while calculating the fine.");
    }
  }

  /**
   * Handles the action of submitting the return of the book to the library system. Displays a
   * success message and navigates to the appropriate page.
   *
   * @param actionEvent the action event triggering the submission
   */
  public void handleSubmit(ActionEvent actionEvent) {
    try {
      // Attempt to return the book to the library
      returningBookToLibrary(findBookLendingById(currentBookItem.getBarcode()));

      // Show a success alert after the operation is successful
      showAlert(AlertType.INFORMATION, "Success", "Book has been returned successfully.");

      // Navigate to the previous page
      if ("memberDetails".equals(previousPage)) {
        loadMemberDetail();
      } else {
        loadBookDetail();
      }

    } catch (IllegalArgumentException e) {
      // Handle any IllegalArgumentException (e.g., invalid data)
      showAlert(AlertType.ERROR, "Input Error", "Invalid input or book lending not found.");
    } catch (Exception e) {
      // Catch any other exceptions and show a generic error alert
      showAlert(AlertType.ERROR, "Error", "An unexpected error occurred.");
    }
  }

  /**
   * Handles the action of canceling the return and navigating to the previous page.
   *
   * @param actionEvent the action event triggering the cancel action
   */
  public void handleCancel(ActionEvent actionEvent) {
    try {
      if ("memberDetails".equals(previousPage)) {
        loadMemberDetail(); // Load member details page
      } else {
        loadBookDetail(); // Load book details page
      }
    } catch (Exception e) {
      // Handle any other unexpected exceptions
      showAlert(AlertType.ERROR, "Unexpected Error",
          "An unexpected error occurred. Please try again later.");
      logger.error("Unexpected error: {}", e.getMessage(), e);
    }
  }

  /**
   * Loads the book details page for the current book item.
   */
  private void loadBookDetail() {
    Stage currentStage = (Stage) memberIdField.getScene().getWindow();
    PageLoader.openReturningBookPage(currentStage, currentBookItem, "bookDetails");
  }

  private void loadMemberDetail() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberDetails.fxml"));
      Scene memberDetailScene = new Scene(loader.load());
      Stage currentStage = (Stage) memberIdField.getScene().getWindow();
      currentStage.setScene(memberDetailScene);

      MemberDetailsController memberDetailsController = loader.getController();
      if (currentMember == null) {
        System.out.println("currentMember is null, cannot pass to MemberDetailsController");
      } else {
        memberDetailsController.setMemberDetail(currentMember);
      }

    } catch (IOException e) {
      // Handle IOException (e.g., FXML loading failure)
      showAlert(AlertType.ERROR, "Page Load Error",
          "An error occurred while loading the member details page. Please try again later.");
      logger.error("IOException while loading MemberDetails.fxml: {}", e.getMessage(), e);

    } catch (SQLException e) {
      // Handle SQLException (e.g., database interaction failure)
      showAlert(AlertType.ERROR, "Database Error",
          "An error occurred while retrieving member details from the database. Please try again later.");
      logger.error("SQLException while accessing database: {}", e.getMessage(), e);

    } catch (Exception e) {
      // Handle any other unexpected exceptions
      showAlert(AlertType.ERROR, "Unexpected Error",
          "An unexpected error occurred while loading the member details page.");
      logger.error("Unexpected error: {}", e.getMessage(), e);
    }
  }

  /**
   * Handles the action of updating the book status based on the checkbox state.
   *
   * @param actionEvent the action event triggering the status update
   */
  public void handleUserAction(ActionEvent actionEvent) {
    try {
      boolean isChecked = statusCheckBox.isSelected();

      // Update the book status based on the checkbox
      currentBookItem.setStatus(isChecked ? BookStatus.AVAILABLE : BookStatus.LOST);

      // Calculate fine based on the current lending status
      Fine amountFine = new Fine(findBookLendingById(currentBookItem.getBarcode()));
      double fineAmount = FineCalculationService.calculateFine(amountFine);
      feeField.setText(Double.toString(fineAmount));

      // Log the status change
      if (isChecked) {
        System.out.println("User has ticked the Status checkbox.");
      } else {
        System.out.println("User has not ticked the Status checkbox.");
      }

    } catch (SQLException e) {
      // Handle database-related issues (e.g., when querying lending data)
      showAlert(AlertType.ERROR, "Database Error",
          "An error occurred while accessing the database because Fine. Please try again later.");
      logger.error("SQLException in handleUserAction: {}", e.getMessage(), e);

    } catch (NullPointerException e) {
      // Handle potential null pointer exceptions (e.g., currentBookItem is null)
      showAlert(AlertType.ERROR, "Null Pointer Error",
          "A required item is missing. Please check the book details and try again.");
      logger.error("NullPointerException in handleUserAction: {}", e.getMessage(), e);

    } catch (Exception e) {
      // Catch any other unexpected exceptions
      showAlert(AlertType.ERROR, "Unexpected Error",
          "An unexpected error occurred. Please try again later.");
      logger.error("Unexpected error in handleUserAction: {}", e.getMessage(), e);
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
   */
  private void returningBookToLibrary(BookLending bookLending) {
    try {
      // Attempt to return the book item to the library system
      boolean successAdded = librarian.getLendingManager().returnBookItem(
          bookLending.getBookItem(),
          bookLending.getMember(),
          getBookStatus(statusCheckBox)
      );

      // If the return operation is not successful, log and show an error alert
      if (!successAdded) {
        System.out.println("Failed to edit book with ISBN: " + bookLending.getBookItem().getISBN());
        showAlert(AlertType.ERROR, "Error",
            "Book with ISBN " + bookLending.getBookItem().getISBN()
                + " already exists in the library.");
      } else {
        // Success, print a success message
        System.out.println(
            "Book with ISBN: " + bookLending.getBookItem().getISBN()
                + " has been returned successfully."
        );
      }
    } catch (Exception e) {
      // Catch any exceptions that occur and show an error alert
      showAlert(AlertType.ERROR, "Return Error",
          "An error occurred while returning the book.");
    }
  }

  /**
   * Displays an alert dialog with the specified type, title, and content.
   *
   * @param alertType the type of alert (e.g., INFORMATION, WARNING)
   * @param title     the title of the alert dialog
   * @param message   the message of the alert dialog
   */
  private static void showAlert(AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(title);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
