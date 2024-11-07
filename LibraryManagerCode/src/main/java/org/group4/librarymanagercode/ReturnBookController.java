package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.group4.base.books.BookLending;
import javafx.scene.control.ListView;

public class ReturnBookController {

  @FXML
  private JFXButton homeButton, MemberButton, bookButton, returnBookButton, notificationButton, settingButton, closeButton;
  @FXML
  private TableView<BookLending> bookLendingTable;
  @FXML
  private TableColumn<BookLending, String> bookItemID, bookName, memberID, memberName, creationDate, dueDate, returnDate;

  @FXML
  private TextField bookItemSearchField, memberSearchField;
  @FXML
  private ListView<String> bookItemSuggestions, memberSuggestions;

  private ObservableList<BookLending> bookLendingList = FXCollections.observableArrayList();

  public void initialize() {
    //books = FXCollections.observableArrayList(BookLendingDatabase.getInstance().getItems());

    bookLendingTable.setItems(bookLendingList);

    bookItemSearchField.textProperty().addListener((observable, oldValue, newValue) -> showBookItemSuggestions(newValue));
    memberSearchField.textProperty().addListener((observable, oldValue, newValue) -> showMemberSuggestions(newValue));

    // Hide suggestions when user clicks on an item
    bookItemSuggestions.setOnMouseClicked(event -> selectBookItemSuggestion());
    memberSuggestions.setOnMouseClicked(event -> selectMemberSuggestion());
  }

  private void showBookItemSuggestions(String query) {
    ObservableList<String> matches = FXCollections.observableArrayList();
    for (BookLending item : bookLendingList) {
      if (item.getBookItem().getBarcode().contains(query) || item.getBookItem().getISBN().contains(query)) {
        matches.add(item.getBookItem().getBarcode() + " - " + item.getBookItem().getTitle());
      }
    }
    bookItemSuggestions.setItems(matches);
    bookItemSuggestions.setVisible(!matches.isEmpty());
  }

  private void showMemberSuggestions(String query) {
    ObservableList<String> matches = FXCollections.observableArrayList();
    for (BookLending item : bookLendingList) {
      if (item.getMember().getMemberId().contains(query) || item.getMember().getName().toLowerCase().contains(query.toLowerCase())) {
        matches.add(item.getMember().getMemberId() + " - " + item.getMember().getName());
      }
    }
    memberSuggestions.setItems(matches);
    memberSuggestions.setVisible(!matches.isEmpty());
  }

  private void selectBookItemSuggestion() {
    String selectedSuggestion = bookItemSuggestions.getSelectionModel().getSelectedItem();
    if (selectedSuggestion != null) {
      bookItemSearchField.setText(selectedSuggestion.split(" - ")[0]);
      bookItemSuggestions.setVisible(false);
      updateTableWithSelectedBookItem(selectedSuggestion.split(" - ")[0]);
    }
  }

  private void selectMemberSuggestion() {
    String selectedSuggestion = memberSuggestions.getSelectionModel().getSelectedItem();
    if (selectedSuggestion != null) {
      memberSearchField.setText(selectedSuggestion.split(" - ")[0]);
      memberSuggestions.setVisible(false);
      updateTableWithSelectedMember(selectedSuggestion.split(" - ")[0]);
    }
  }

  private void updateTableWithSelectedBookItem(String bookItemID) {
    // Logic to update table with selected BookItem details
  }

  private void updateTableWithSelectedMember(String memberID) {
    // Logic to update table with selected Member details
  }

  // Action methods for buttons
  public void BookAction(ActionEvent actionEvent) {
  }

  public void ReturnBookAction(ActionEvent actionEvent) {
  }

  public void notificationAction(ActionEvent actionEvent) {
  }

  public void SettingAction(ActionEvent actionEvent) {
  }

  public void Close(ActionEvent actionEvent) {
  }

  public void HomeAction(ActionEvent actionEvent) {
  }

  public void MemberAction(ActionEvent actionEvent) {
  }
}
