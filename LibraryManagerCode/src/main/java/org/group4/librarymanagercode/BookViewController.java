package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.group4.base.entities.Book;
import org.group4.database.BookDatabase;

public class BookViewController {

  public Button addBookButton;
  private final ObservableList<Book> bookList = FXCollections.observableArrayList();

  public JFXButton homeButton;
  @FXML
  private TableView<Book> tableView = new TableView<>(bookList);

  @FXML
  private TableColumn<Book, String> ISBN;

  @FXML
  private TableColumn<Book, String> bookName;

  @FXML
  private TableColumn<Book, String> bookSubject;

  @FXML
  private TableColumn<Book, String> bookPublisher;

  @FXML
  private TableColumn<Book, String> bookLanguage;

  @FXML
  private TableColumn<Book, Integer> numberOfPages;

  @FXML
  private TableColumn<Book, String> bookAuthor;

  @FXML
  private ContextMenu contextMenu;

  @FXML
  private TextField searchField;

  //  private ObservableList<Book> bookList = FXCollections.observableArrayList();
  // This method is called by the FXMLLoader when initialization is complete
  @FXML
  public void initialize() {

    // Initialize columns
    ISBN.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getISBN()));
    bookName.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
    bookSubject.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getSubject()));
    bookPublisher.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
    bookLanguage.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getLanguage()));
    numberOfPages.setCellValueFactory(cellData ->
        new SimpleObjectProperty<>(cellData.getValue().getNumberOfPages()));
    bookAuthor.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getAuthorsAsString()));
    tableView.getColumns()
        .addAll(ISBN, bookName, bookSubject, bookPublisher, bookLanguage, numberOfPages,
            bookAuthor);
    // Add data to the table (sample data can be fetched from service or database)
    loadBookData();
    // Add row click event listener
    tableView.setRowFactory(tv -> {
      TableRow<Book> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (!row.isEmpty())) {
          Book selectedItem = row.getItem();
          showDetailPage(selectedItem);
        }
      });
      return row;
    });

    searchField.textProperty().addListener((observable, oldValue, newValue) -> filterBookList(newValue));


    searchField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        filterBookList(searchField.getText());
      }
    });

  }

  private void showDetailPage(Book book) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("BookDetails.fxml"));
      Stage detailStage = new Stage();
      detailStage.setScene(new Scene(loader.load()));

      BookDetailsController controller = loader.getController();
      controller.setItemDetail(book);

      detailStage.setTitle("Book Item Detail");
      detailStage.show();
    } catch (Exception e) {
      e.printStackTrace();
      }
  }

  private void loadBookData() {
    bookList.addAll(BookDatabase.getInstance().getItems());
    tableView.setItems(bookList);
  }


  private void filterBookList(String searchText) {
    ObservableList<Book> filteredList = FXCollections.observableArrayList();

    if (searchText == null || searchText.isEmpty()) {

      tableView.setItems(bookList);
    } else {

      String lowerCaseFilter = searchText.toLowerCase();

      for (Book book : bookList) {

        if (book.getISBN().toLowerCase().contains(lowerCaseFilter) ||
            book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
          filteredList.add(book);
        }
      }
      tableView.setItems(filteredList);
    }
  }

  public void HomeAction(ActionEvent actionEvent) {
  }

  public void MemberAction(ActionEvent actionEvent) {
  }

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

  public void addBookAction(ActionEvent actionEvent) throws IOException {
    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddBook.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
  }
}
