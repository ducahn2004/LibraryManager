package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.group4.module.books.Book;
import org.group4.module.users.Librarian;
import org.group4.module.users.Member;


public class BookViewController {


  private Stage stage;

  private final ObservableList<Book> bookList = FXCollections.observableArrayList();

  private final Librarian librarian = LibrarianDatabase.getInstance().getItems().getFirst();
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
  private TableView<Book> tableView;

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
  private TableColumn<Book, Void> actionColumn;
  @FXML
  private TextField searchField;

  @FXML
  public void initialize() {
    // Initialize columns
    ISBN.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getISBN()));
    bookName.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getTitle()));
    bookSubject.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getSubject()));
    bookPublisher.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getPublisher()));
    bookLanguage.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getLanguage()));
    numberOfPages.setCellValueFactory(cellData ->
        new SimpleObjectProperty<>(cellData.getValue().getNumberOfPages()));
    bookAuthor.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getAuthors().iterator().next().getName()));

    actionColumn.setCellFactory(param -> new TableCell<>() {
      private final Hyperlink editLink = new Hyperlink("Edit");
      private final Hyperlink deleteLink = new Hyperlink("Delete");

      {
        editLink.setUnderline(true);
        // Xử lý sự kiện cho liên kết Edit
        editLink.setOnAction((ActionEvent event) -> {
          Book item = getTableView().getItems().get(getIndex());
          showEditForm(item);
        });

        // Xử lý sự kiện cho liên kết Delete
        deleteLink.setOnAction((ActionEvent event) -> {
          Book item = getTableView().getItems().get(getIndex());
          showDeleteConfirmation(item);
        });
      }

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setGraphic(null);
        } else {
          HBox hBox = new HBox(10, editLink, deleteLink);
          setGraphic(hBox);
        }
      }
    });

    // Add data to the table
    loadBookData();

    // Add row click event listener
    tableView.setRowFactory(tv -> {
      TableRow<Book> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && !row.isEmpty()) {
          showDetailPage(row.getItem());
        }
      });
      return row;
    });

    // Add a listener for the search field
    searchField.textProperty()
        .addListener((observable, oldValue, newValue) -> filterBookList(newValue));
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
      Logger.getLogger(BookViewController.class.getName())
          .log(Level.SEVERE, "Failed to load book details page", e);
    }
  }

  private void showDeleteConfirmation(Book book) {
    Alert alert = createAlert(AlertType.CONFIRMATION, "Delete Confirmation",
        "Are you sure you want to delete this book?",
        "ID: " + book.getISBN() + "\nName: " + book.getTitle() + "\nAuthor: " + book.getAuthors()
            .iterator().next().getName() + "\nPublisher: " + book.getPublisher() + "\nLanguage: "
            + book);
    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        bookList.remove(book);
        librarian.removeBook(book);
      }
    });
  }

  private Alert createAlert(AlertType type, String title, String header, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    return alert;
  }

  private void showEditForm(Book book) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("EditBook.fxml"));
      Stage editStage = new Stage();
      editStage.setScene(new Scene(loader.load()));

      EditBookController controller = loader.getController();
      controller.setBookData(book);
      controller.setParentController(this);

      editStage.setTitle("Edit Member");
      editStage.showAndWait();
    } catch (IOException e) {
      logAndShowError("Failed to load edit form page", e);
    }
  }

  private void logAndShowError(String message, Exception e) {
    Logger.getLogger(MemberViewController.class.getName()).log(Level.SEVERE, message, e);
    showAlert(AlertType.ERROR, "Error", message);
  }

  void showAlert(AlertType type, String title, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  private void loadBookData() {
    if (bookList.isEmpty()) {
      bookList.addAll(BookDatabase.getInstance().getItems());
      tableView.setItems(bookList);
    }
  }

  public void refreshTable() {
    tableView.refresh();
  }

  private void filterBookList(String searchText) {
    if (searchText == null || searchText.isEmpty()) {
      tableView.setItems(bookList);
    } else {
      String lowerCaseFilter = searchText.toLowerCase();
      ObservableList<Book> filteredList = bookList.filtered(book ->
          book.getISBN().toLowerCase().contains(lowerCaseFilter) ||
              book.getTitle().toLowerCase().contains(lowerCaseFilter)
      );
      tableView.setItems(filteredList);
    }
  }

  public void onSearchBook(ActionEvent actionEvent) {
    filterBookList(searchField.getText());
  }


  @FXML
  public void addBookAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddBook.fxml"));
    Parent root = fxmlLoader.load();

    // Lấy `Stage` hiện tại từ `actionEvent`
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

    // Cập nhật nội dung của `Scene` hiện tại mà không tạo ra cửa sổ mới
    stage.getScene().setRoot(root);
    stage.setTitle("Library Manager");
  }

  public void HomeAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AdminPane.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) homeButton.getScene()
        .getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Home button clicked");
  }

  public void MemberAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MemberView.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) MemberButton.getScene()
        .getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Member button clicked");
  }

  public void BookAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BookView.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) bookButton.getScene()
        .getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Book button clicked");
  }

  public void notificationAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Notification.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) notificationButton.getScene()
        .getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Notification button clicked");
  }

  public void SettingAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Setting.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) settingButton.getScene()
        .getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Setting button clicked");
  }

  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }

}
