package org.group4.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.group4.dao.FactoryDAO;
import org.group4.module.books.BookItem;
import org.group4.module.transactions.BookLending;
import org.group4.module.users.Member;

/**
 * Controller for displaying member details and their associated book lendings. Provides
 * functionality to display member information and handle interactions with the table view of
 * borrowed books.
 */
public class MemberDetailsController {

  // FXML components for the table view and its columns
  @FXML
  private TableView<BookLending> tableView;
  @FXML
  private TableColumn<BookLending, String> isbnTable;
  @FXML
  private TableColumn<BookLending, String> bookTitleTable;
  @FXML
  private TableColumn<BookLending, String> bookItemIDTable;
  @FXML
  private TableColumn<BookLending, String> creationDateTable;
  @FXML
  private TableColumn<BookLending, String> dueDateTable;
  @FXML
  private TableColumn<BookLending, String> returnDateTable;

  // FXML components for displaying member details
  @FXML
  private Label memberEmailLabel;
  @FXML
  private Label memberPhoneLabel;
  @FXML
  private Label memberBirthLabel;
  @FXML
  private Label memberNameLabel;
  @FXML
  private Label memberIDLabel;

  // Current member and their book lending data
  private Member currentMember;
  private final ObservableList<BookLending> bookLendings = FXCollections.observableArrayList();

  /**
   * Initializes the controller. Sets up the table view with appropriate cell value factories and
   * selection listeners.
   */
  @FXML
  private void initialize() {
    initializeTable();
  }

  /**
   * Sets the member whose details are to be displayed.
   *
   * @param member The member to display details for.
   */
  public void setItemDetail(Member member) {
    this.currentMember = member;
    displayMemberDetails();
    loadData();
  }

  /**
   * Configures the table view to display book lending details. Sets up cell value factories and a
   * selection listener for row clicks.
   */
  private void initializeTable() {
    // Set cell value factories for each column
    isbnTable.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getBookItem().getISBN()));
    bookTitleTable.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getBookItem().getTitle()));
    bookItemIDTable.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getBookItem().getBarcode()));
    dueDateTable.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getDueDate().toString()));
    creationDateTable.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getLendingDate().toString()));
    dueDateTable.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getDueDate().toString()));

    // Bind the observable list to the table view
    tableView.setItems(bookLendings);

    // Add a listener for row selection in the table
    tableView.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldSelection, newSelection) -> {
          if (newSelection != null) {
            try {
              openReturningBookPage(newSelection.getBookItem());
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }
        });
  }

  /**
   * Loads book lending data for the current member into the table view.
   */
  private void loadData() {
    if (currentMember != null) {
      bookLendings.clear();
      // Load all book lendings associated with the current member
      bookLendings.addAll(
          FactoryDAO.getBookLendingDAO().getByMemberId(currentMember.getMemberId()));
      System.out.println("Data loaded: " + bookLendings.size() + " items");
    }
  }

  /**
   * Displays details of the current member in the UI.
   */
  private void displayMemberDetails() {
    if (currentMember != null) {
      memberNameLabel.setText(currentMember.getName());
      memberBirthLabel.setText(currentMember.getDateOfBirth().toString());
      memberPhoneLabel.setText(currentMember.getPhoneNumber());
      memberEmailLabel.setText(currentMember.getEmail());
      memberIDLabel.setText(currentMember.getMemberId());
    }
  }

  /**
   * Opens the "Returning Book" page for the selected book item.
   *
   * @param bookItem The book item to be returned.
   * @throws IOException If the FXML file cannot be loaded.
   */
  private void openReturningBookPage(BookItem bookItem) throws IOException {
    try {
      // Load the ReturningBook.fxml file and set up the new scene
      FXMLLoader loader = new FXMLLoader(getClass().getResource("ReturningBook.fxml"));
      Scene returningBookScene = new Scene(loader.load());
      Stage currentStage = (Stage) tableView.getScene().getWindow();

      // Switch to the new scene
      currentStage.setScene(returningBookScene);

      // Pass details to the ReturningBookController
      ReturningBookController controller = loader.getController();
      controller.setItemDetailReturning(bookItem);
      controller.setPreviousPage("memberDetails");

      currentStage.setTitle("Book Item Detail");
    } catch (Exception e) {
      Logger.getLogger(MemberDetailsController.class.getName())
          .log(Level.SEVERE, "Failed to load book details page", e);
    }
  }
}
