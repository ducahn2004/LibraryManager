package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.group4.dao.FactoryDAO;
import org.group4.module.books.Book;
import org.group4.module.manager.SessionManager;
import org.group4.module.transactions.BookLending;
import org.group4.module.users.Librarian;

public class BookLendingController {

  @FXML
  private TableColumn<BookLending, String> bookBarcode;
  @FXML
  private TableColumn<BookLending, String> bookISBN;
  @FXML
  private TableColumn<BookLending, String> bookTitle;
  @FXML
  private TableColumn<BookLending, String> memberID;
  @FXML
  private TableColumn<BookLending, String> memberName;
  @FXML
  private TableColumn<BookLending, String> Status;
  @FXML
  private TableColumn<BookLending, String> creationDate;
  @FXML
  private TableColumn<BookLending, String> dueDate;
  @FXML
  private TableColumn<BookLending, String> returnDate;
  @FXML
  private JFXButton bookLendingButton;

  @FXML
  private JFXButton homeButton;

  @FXML
  private JFXButton MemberButton;

  @FXML
  private JFXButton bookButton;

  @FXML
  private JFXButton settingButton;

  @FXML
  private JFXButton notificationButton;

  @FXML
  private JFXButton closeButton;

  @FXML
  private TableView<BookLending> tableView;


  @FXML
  private TextField searchField;

  private final ObservableList<BookLending> bookLendingsList = FXCollections.observableArrayList();
  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();

  public void initialize(MouseEvent mouseEvent) {
    bookBarcode.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getBookItem().getBarcode()));
    bookISBN.setCellValueFactory(
        cellData -> new SimpleStringProperty(
            cellData.getValue().getBookItem().getISBN()));
    bookTitle.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getBookItem().getTitle()));
    memberID.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getMember().getMemberId()));
    memberName.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getMember().getName()));
    Status.setCellValueFactory(
        cellData -> new SimpleStringProperty(
            cellData.getValue().getBookItem().getStatus().toString()));
    creationDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getLendingDate().toString()));
    dueDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getDueDate().toString()));
    returnDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getReturnDate().toString()));

    loadBookLending();
    searchField.textProperty()
        .addListener((observable, oldValue, newValue) -> filterBookLendingList(newValue));
    searchField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        filterBookLendingList(searchField.getText());
      }
    });

  }

  private void loadBookLending() {
    List<BookLending> bookLendings = FactoryDAO.getBookLendingDAO().getAll();

    if (bookLendings == null) {
      showAlert(AlertType.WARNING, "No Data Found", "No book lending were found in the database.");
      return;
    }

    if (bookLendingsList.isEmpty()) {
      bookLendingsList.addAll(bookLendings);
      tableView.setItems(bookLendingsList);
    }
  }

  private void filterBookLendingList(String searchText) {
    if (searchText == null || searchText.isEmpty()) {
      tableView.setItems(bookLendingsList);
    } else {
      String lowerCaseFilter = searchText.toLowerCase();
      ObservableList<BookLending> filteredList = bookLendingsList.filtered(
          bookLending -> bookLending.getBookItem().getISBN().contains(lowerCaseFilter)
              || bookLending.getBookItem().getBarcode().contains(lowerCaseFilter)
              || bookLending.getBookItem().getTitle().contains(lowerCaseFilter)
              || bookLending.getMember().getMemberId().contains(lowerCaseFilter)
              || bookLending.getMember().getName().contains(lowerCaseFilter));
      tableView.setItems(filteredList);
    }
  }

  void showAlert(AlertType type, String title, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  @FXML
  public void HomeAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "AdminPane.fxml", "Library Manager");
  }

  @FXML
  public void MemberAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "MemberView.fxml", "Library Manager");
  }

  @FXML
  public void BookAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "BookView.fxml", "Library Manager");
  }

  @FXML
  public void notificationAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Notification.fxml", "Library Manager");
  }

  @FXML
  public void SettingAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Setting.fxml", "Library Manager");
  }

  public void BookLendingAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "BookLending", "Library Manager");
  }


  @FXML
  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }

  private Stage getStage() {
    return (Stage) homeButton.getScene().getWindow();
  }


}
