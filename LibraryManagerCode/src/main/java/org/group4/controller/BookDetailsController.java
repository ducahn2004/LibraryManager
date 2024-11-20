package org.group4.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.group4.dao.FactoryDAO;
import org.group4.module.books.BookItem;
import org.group4.module.books.Book;
import org.group4.module.enums.BookFormat;
import org.group4.module.enums.BookStatus;
import org.group4.module.manager.SessionManager;
import org.group4.module.users.Librarian;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BookDetailsController {


  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();
  private Book currentBook;
  private final ObservableList<BookItem> bookItems = FXCollections.observableArrayList();
  private static final Logger logger = LoggerFactory.getLogger(
      BookDetailsController.class.getName());

  @FXML
  private Label isbnLabel, titleLabel, authorLabel, publisherLabel, subjectLabel, pagesLabel;


  @FXML
  private TableView<BookItem> tableView;

  @FXML
  private TableColumn<BookItem, String> barCode;
  @FXML
  private TableColumn<BookItem, String> referenceOnly;
  @FXML
  private TableColumn<BookItem, String> status;
  @FXML
  private TableColumn<BookItem, String> borrowedDate;
  @FXML
  private TableColumn<BookItem, String> dueDate;
  @FXML
  private TableColumn<BookItem, Double> price;
  @FXML
  private TableColumn<BookItem, String> format;
  @FXML
  private TableColumn<BookItem, String> dateOfPurchase;
  @FXML
  private TableColumn<BookItem, String> publicationDate;
  @FXML
  private TableColumn<BookItem, Void> actionColumn;

  @FXML
  private void initialize() {

    initializeTable();
    tableView.setEditable(true);

    tableView.setOnMouseClicked(this::handleRowClick);
  }

  public void setItemDetail(Book book) throws SQLException {
    this.currentBook = book;
    displayBookDetails();
    loadData(); // Load data based on currentBook

    // Set up a listener for row clicks in the TableView
    tableView.setOnMouseClicked(this::handleRowClick);
  }

  private void handleRowClick(javafx.scene.input.MouseEvent mouseEvent) {
    if (mouseEvent.getClickCount() == 2) { // Double-click
      BookItem selectedItem = tableView.getSelectionModel().getSelectedItem();
      if (selectedItem != null) {
        try {
          if (selectedItem.getStatus() == BookStatus.AVAILABLE) {
            openBorrowingBookPage(selectedItem);
          } else if (selectedItem.getStatus() == BookStatus.LOANED) {
            openReturningBookPage(selectedItem);
          }
        } catch (IOException e) {
          logger.error(e.getMessage(), e);
        }
      }
    }
  }

  private void displayBookDetails() {
    if (currentBook != null) {
      isbnLabel.setText(currentBook.getISBN());
      titleLabel.setText(currentBook.getTitle());
      authorLabel.setText(currentBook.authorsToString());
      publisherLabel.setText(currentBook.getPublisher());
      subjectLabel.setText(currentBook.getSubject());
      pagesLabel.setText(String.valueOf(currentBook.getNumberOfPages()));
    }
  }

  private void initializeTable() {
    // Set up each column with its cell value factory
    barCode.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getBarcode()));
    referenceOnly.setCellValueFactory(
        cellData -> new SimpleStringProperty(
            String.valueOf(cellData.getValue().getIsReferenceOnly())));
    status.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));
    borrowedDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(formatLocalDate(cellData.getValue().getBorrowed())));
    dueDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(formatLocalDate(cellData.getValue().getDueDate())));
    price.setCellValueFactory(
        cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
    format.setCellValueFactory(
        cellData -> {
          BookFormat format = cellData.getValue().getFormat();
          return new SimpleStringProperty(format != null ? format.toString() : "Unknown");
        }
    );
    dateOfPurchase.setCellValueFactory(cellData -> new SimpleStringProperty(
        formatLocalDate(cellData.getValue().getDateOfPurchase())));
    publicationDate.setCellValueFactory(cellData -> new SimpleStringProperty(
        formatLocalDate(cellData.getValue().getPublicationDate())));

    actionColumn.setCellFactory(param -> new TableCell<>() {
      private final Hyperlink editLink = new Hyperlink("Edit");
      private final Hyperlink deleteLink = new Hyperlink("Delete");

      {
        editLink.setUnderline(true);
        // Xử lý sự kiện cho liên kết Edit
        editLink.setOnAction((ActionEvent event) -> {
          BookItem item = getTableView().getItems().get(getIndex());
          try {
            openEditBookItemPage(item);
          } catch (IOException e) {
            System.out.println("Not show the edit page");
            logger.error(e.getMessage(), e);

          }
        });

        // Xử lý sự kiện cho liên kết Delete
        deleteLink.setOnAction((ActionEvent event) -> {
          BookItem item = getTableView().getItems().get(getIndex());
          deleteItemConfirmation(item);
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

    tableView.setItems(bookItems); // Bind the data list to the table
  }


  void loadData() throws SQLException {
    if (currentBook != null) {
      bookItems.clear();
      bookItems.addAll(FactoryDAO.getBookDAO().getAllBookItems(currentBook.getISBN()));
      System.out.println("Data loaded: " + bookItems.size() + " items");
    }
  }

  private void openEditBookItemPage(BookItem selectedItem) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("EditBookItem.fxml"));
    Stage editStage = new Stage();
    editStage.setScene(new Scene(loader.load()));

    EditBookItemController controller = loader.getController();
    controller.setBookItem(selectedItem);

    editStage.setTitle("Edit Book Item");
    editStage.show();

    editStage.setOnHiding(event -> {
      tableView.refresh();
    });
  }


  private void openBorrowingBookPage(BookItem bookItem) throws IOException {
    try {

      FXMLLoader loader = new FXMLLoader(getClass().getResource("BorrowingBook.fxml"));

      Scene borrowingBookScene = new Scene(loader.load());

      Stage currentStage = (Stage) tableView.getScene().getWindow();

      currentStage.setScene(borrowingBookScene);

      BorrowingBookController controller = loader.getController();
      controller.setItemDetailBorrowing(bookItem);
      currentStage.setTitle("Book Item Detail");
    } catch (Exception e) {
      logger.error("\"Failed to load book details page\"", e);
    }
  }

  private void openReturningBookPage(BookItem bookItem) throws IOException {
    try {

      FXMLLoader loader = new FXMLLoader(getClass().getResource("ReturningBook.fxml"));

      Scene returningBookScene = new Scene(loader.load());

      Stage currentStage = (Stage) tableView.getScene().getWindow();

      currentStage.setScene(returningBookScene);

      ReturningBookController controller = loader.getController();
      controller.setItemDetailReturning(bookItem);
      controller.setPreviousPage("bookDetails");

      currentStage.setTitle("Book Item Detail");
    } catch (Exception e) {
      logger.error("Failed to load book details page", e);
    }
  }


  private String formatLocalDate(LocalDate date) {
    if (date != null) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      return date.format(formatter);
    }
    return "";
  }

  private void deleteItemConfirmation(BookItem item) {
    Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
    confirmAlert.setTitle("Delete Confirmation");
    confirmAlert.setHeaderText("Are you sure you want to delete?");
    confirmAlert.setContentText("Data: " + item);

    confirmAlert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        if (librarian.deleteBookItem(item.getBarcode())) {
          bookItems.remove(item);
          System.out.println("Item deleted: " + item);
        }
      }
    });
  }

  public void addItemButton(ActionEvent actionEvent) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("AddBookItem.fxml"));
      Parent root = loader.load();
      AddBookItemController controller = loader.getController();
      controller.setParentController(this);  // Set the parent controller

      Stage stage = new Stage();
      stage.setTitle("Add Book Item");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  public void addBookItemToList(BookItem newBookItem) {
    bookItems.add(newBookItem);
    tableView.setItems(bookItems);
    tableView.refresh();
  }

  public Book returnCurrentBook() {
    return currentBook;
  }


}
