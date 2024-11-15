package org.group4.librarymanagercode;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.group4.base.books.BookItem;
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

    tableView.setItems(bookLendings);

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
  private void openReturningBookPage(BookItem bookItem) throws IOException {
    try {
//      FXMLLoader loader = new FXMLLoader(getClass().getResource("BorrowingBook.fxml"));
//      Stage detailStage = new Stage();
//      detailStage.setScene(new Scene(loader.load()));
//
//      BorrowingBookController controller = loader.getController();
//      controller.setItemDetailBorrowing(bookItem);
//
//      detailStage.setTitle("Book Item Detail");
//      detailStage.show();

      FXMLLoader loader = new FXMLLoader(getClass().getResource("ReturningBook.fxml"));

      Scene returningBookScene = new Scene(loader.load());

      Stage currentStage = (Stage) tableView.getScene().getWindow();

      currentStage.setScene(returningBookScene);

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
