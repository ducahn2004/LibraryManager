package org.group4.librarymanagercode;

import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.group4.base.books.BookLending;
import org.group4.base.users.Member;
import org.group4.database.BookBorrowDatabase;

public class MemberDetailsController {


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

  private Member currentMember;
  private final ObservableList<BookLending> bookLendings = FXCollections.observableArrayList();

  @FXML
  private void initialize() {
    initializeTable();
  }

  public void setItemDetail(Member member) {
    this.currentMember = member;
    displayMemberDetails();
    loadData();
  }

  private void initializeTable() {
    isbnTable.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getBookItem().getISBN()));
    bookTitleTable.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getBookItem().getTitle()));
    bookItemIDTable.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getBookItem().getBarcode()));
    dueDateTable.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getDueDate().toString()));
    creationDateTable.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getCreationDate().toString()));
    dueDateTable.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getDueDate().toString()));
    returnDateTable.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getReturnDate().toString()));

    tableView.setItems(bookLendings);

    tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        openBorrowBookWindow(newSelection);
      }
    });

  }

  private void loadData() {
    if (currentMember != null) {
      bookLendings.clear();
      bookLendings.addAll(BookBorrowDatabase.getInstance().getItems().stream()
          .filter(item -> item.getMember().getMemberId().equals(currentMember.getMemberId()))
          .toList());
      System.out.println("Data loaded: " + bookLendings.size() + " items");
    }
  }

  private void displayMemberDetails() {
    if (currentMember != null) {
      memberNameLabel.setText(currentMember.getName());
      memberBirthLabel.setText(currentMember.getDateOfBirth().toString());
      memberPhoneLabel.setText(currentMember.getPhoneNumber());
      memberEmailLabel.setText(currentMember.getEmail());
      memberIDLabel.setText(currentMember.getMemberId());
    }
  }
  private void openBorrowBookWindow(BookLending selectedBookLending) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("BorrowingBook.fxml"));
      Parent root = loader.load();

      // Pass the selected BookLending to BorrowBookController
      BorrowingBookController controller = loader.getController();
      controller.setBookLendingDetails(selectedBookLending);

      // Display the new stage
      Stage stage = new Stage();
      stage.setTitle("Borrowed Book Details");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
