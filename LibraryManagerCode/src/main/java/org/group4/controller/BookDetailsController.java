package org.group4.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
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
import org.group4.model.book.Book;
import org.group4.model.book.BookItem;
import org.group4.model.enums.BookFormat;
import org.group4.model.enums.BookStatus;
import org.group4.model.user.Librarian;
import org.group4.service.user.SessionManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Controller class for managing the book details view.
 */
public class BookDetailsController {

  // Current librarian obtained from the session manager.
  private final Librarian librarian = SessionManagerService.getInstance().getCurrentLibrarian();

  // The book currently being displayed in the details view.
  private Book currentBook;

  // Observable list of book items for the TableView.
  private final ObservableList<BookItem> bookItems = FXCollections.observableArrayList();

  // Logger for the class.
  private static final Logger logger = LoggerFactory.getLogger(
      BookDetailsController.class.getName());

  // Labels for displaying book details.
  @FXML
  private Label isbnLabel;
  @FXML
  private Label titleLabel;
  @FXML
  private Label authorLabel;
  @FXML
  private Label publisherLabel;
  @FXML
  private Label subjectLabel;
  @FXML
  private Label pagesLabel;

  // TableView and its columns for displaying book items.
  @FXML
  private TableView<BookItem> tableView;
  @FXML
  private TableColumn<BookItem, Void> qrColumn;
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

  /**
   * Initializes the controller and sets up the TableView.
   */
  @FXML
  private void initialize() {
    initializeTable();
    tableView.setEditable(true);
    tableView.setOnMouseClicked(this::handleRowClick);
  }

  /**
   * Sets the details of the selected book.
   *
   * @param book The selected book.
   * @throws SQLException If there is an error accessing the database.
   */
  public void setItemDetail(Book book) throws SQLException {
    this.currentBook = book;
    displayBookDetails();
    loadData();
    tableView.setOnMouseClicked(this::handleRowClick);
  }

  /**
   * Handles double-click events on TableView rows.
   */
  private void handleRowClick(javafx.scene.input.MouseEvent mouseEvent) {
    if (mouseEvent.getClickCount() == 2) {
      BookItem selectedItem = tableView.getSelectionModel().getSelectedItem();
      if (selectedItem != null) {
        if (selectedItem.getStatus() == BookStatus.AVAILABLE) {
          openBorrowingBookPage(selectedItem);
        } else if (selectedItem.getStatus() == BookStatus.LOANED) {
          openReturningBookPage(selectedItem);
        }
      }
    }
  }

  /**
   * Displays the details of the current book in the labels.
   */
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

  /**
   * Initializes the TableView and sets up column bindings.
   */
  private void initializeTable() {
    qrColumn.setCellFactory(param -> new TableCell<>() {
      private final Hyperlink qrLink = new Hyperlink("Generate QR");

      {
        qrLink.setOnAction(event -> {
          BookItem bookItem = getTableView().getItems().get(getIndex());
          if (bookItem != null) {
            showQRCode(bookItem);
          }
        });
      }

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setGraphic(null);
        } else {
          setGraphic(qrLink);
        }
      }
    });
    barCode.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getBarcode()));
    referenceOnly.setCellValueFactory(cellData ->
        new SimpleStringProperty(String.valueOf(cellData.getValue().getIsReferenceOnly())));
    status.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getStatus().toString()));
    borrowedDate.setCellValueFactory(cellData ->
        new SimpleStringProperty(formatLocalDate(cellData.getValue().getBorrowed())));
    dueDate.setCellValueFactory(cellData ->
        new SimpleStringProperty(formatLocalDate(cellData.getValue().getDueDate())));
    price.setCellValueFactory(cellData ->
        new SimpleObjectProperty<>(cellData.getValue().getPrice()));
    format.setCellValueFactory(cellData -> {
      BookFormat bookFormat = cellData.getValue().getFormat();
      return new SimpleStringProperty(bookFormat != null ? bookFormat.toString() : "Unknown");
    });
    dateOfPurchase.setCellValueFactory(cellData ->
        new SimpleStringProperty(formatLocalDate(cellData.getValue().getDateOfPurchase())));
    publicationDate.setCellValueFactory(cellData ->
        new SimpleStringProperty(formatLocalDate(cellData.getValue().getPublicationDate())));
    actionColumn.setCellFactory(param -> new ActionCellFactory());
    tableView.setItems(bookItems);
  }

  /**
   * Loads data for the current book and populates the TableView.
   * <p> Clears the existing list of book items and adds all items for the current book. </p>
   */
  void loadData() {
    if (currentBook != null) {
      bookItems.clear();
      bookItems.addAll(librarian.getBookManager().getAllBookItems(currentBook.getISBN()));
      System.out.println("Data loaded: " + bookItems.size() + " items");
    }
  }

  /**
   * Opens the Edit Book Item page for the selected item.
   *
   * @param selectedItem The book item to edit.
   * @throws IOException If there is an error loading the FXML file.
   */
  private void openEditBookItemPage(BookItem selectedItem) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("EditBookItem.fxml"));
    Stage editStage = new Stage();
    editStage.setScene(new Scene(loader.load()));

    EditBookItemController controller = loader.getController();
    controller.setBookItem(selectedItem);

    editStage.setTitle("Edit Book Item");
    editStage.show();

    editStage.setOnHiding(event -> tableView.refresh());
  }

  /**
   * Opens the Borrowing Book page for the selected book item.
   *
   * @param bookItem The book item to borrow.
   */
  private void openBorrowingBookPage(BookItem bookItem) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("BorrowingBook.fxml"));
      Scene borrowingBookScene = new Scene(loader.load());
      Stage currentStage = (Stage) tableView.getScene().getWindow();

      currentStage.setScene(borrowingBookScene);

      BorrowingBookController controller = loader.getController();
      controller.setItemDetailBorrowing(bookItem);
      currentStage.setTitle("Book Item Detail");
    } catch (IOException e) {
      // Handle the specific IOException if loading the FXML fails
      logger.error("Failed to load borrowing book page", e);
      showAlert(
          "Failed to load Borrowing Book page. Please try again.");
    } catch (Exception e) {
      // Handle other unforeseen errors
      logger.error("Unexpected error", e);
      showAlert("An unexpected error occurred. Please try again.");
    }
  }

  /**
   * Opens the Returning Book page for the selected book item.
   *
   * @param bookItem The book item to return.
   */
  private void openReturningBookPage(BookItem bookItem) {
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
      logger.error("Failed to load returning book page", e);
      showAlert("Fail to open Returning Book.");
    }
  }

  /**
   * Formats a LocalDate to a string using the pattern "yyyy-MM-dd".
   *
   * @param date The date to format.
   * @return A formatted string, or an empty string if the date is null.
   */
  private String formatLocalDate(LocalDate date) {
    if (date != null) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      return date.format(formatter);
    }
    return "";
  }

  /**
   * Displays the QR Code of a given BookItem in a new window.
   *
   * @param bookItem The BookItem whose QR Code needs to be displayed.
   */
  private void showQRCode(BookItem bookItem) {
    try {
      // Retrieve the QR Code file path for the given BookItem from the BookItemManager.
      Optional<String> qrFilePath = librarian.getBookItemManager().getQRCode(bookItem.getBarcode());

      // Check if the QR Code file path is present.
      if (qrFilePath.isPresent()) {
        // Load the QR Code display window.
        Stage qrStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QRCodeDisplay.fxml"));
        Parent root = loader.load();

        // Set the QR Code image in the QRCodeDisplayController.
        QRCodeDisplayController controller = loader.getController();
        controller.setQRCodeImage(qrFilePath.get());

        // Configure and show the new stage for QR Code display.
        qrStage.setTitle("QR Code");
        qrStage.setScene(new Scene(root));
        qrStage.show();
      } else {
        // Display an alert if the QR Code file path is not found.
        showAlert("QR Code not found for the selected book item.");
      }
    } catch (IOException e) {
      // Handle IOException and display an alert.
      showAlert(
          "Failed to load QR Code display window: " + e.getMessage());
    } catch (SQLException e) {
      // Handle SQLException and display an alert.
      showAlert("Database error occurred: " + e.getMessage());
    } catch (Exception e) {
      // Handle unexpected exceptions and display an alert.
      showAlert("An unexpected error occurred: " + e.getMessage());
    }
  }

  /**
   * Displays a confirmation alert and deletes the book item if confirmed.
   *
   * @param item The book item to delete.
   */
  private void deleteItemConfirmation(BookItem item) {
    Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
    confirmAlert.setTitle("Delete Confirmation");
    confirmAlert.setHeaderText("Are you sure you want to delete?");
    confirmAlert.setContentText("Data: " + item);

    confirmAlert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        try {
          if (librarian.getBookItemManager().delete(item.getBarcode())) {
            bookItems.remove(item);
            System.out.println("Item deleted: " + item);
          }
        } catch (SQLException e) {
          logger.error("Failed to delete item: {}", item, e);
          showAlert("Failed to delete item. Please try again.");
        }
      }
    });
  }

  /**
   * Opens the Add Book Item page.
   *
   * @param actionEvent The event triggered by clicking the Add button.
   */
  public void addItemButton(ActionEvent actionEvent) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBookItem.fxml"));
      Parent root = loader.load();

      AddBookItemController controller = loader.getController();
      controller.setParentController(this);

      Stage stage = new Stage();
      stage.setTitle("Add Book Item");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      showAlert("Failed to load Add Book Item page. Please try again.");
    }
  }

  /**
   * Adds a new book item to the list and refreshes the TableView.
   *
   * @param newBookItem The new book item to add.
   */
  public void addBookItemToList(BookItem newBookItem) {
    bookItems.add(newBookItem);
    tableView.setItems(bookItems);
    tableView.refresh();
  }

  /**
   * Returns the current book being displayed.
   *
   * @return The current book.
   */
  public Book returnCurrentBook() {
    return currentBook;
  }

  /**
   * Custom cell factory for the action column in the TableView.
   */
  private class ActionCellFactory extends TableCell<BookItem, Void> {

    private final Hyperlink editLink = new Hyperlink("Edit");
    private final Hyperlink deleteLink = new Hyperlink("Delete");

    /**
     * Constructor to set up actions for the edit and delete links.
     */
    private ActionCellFactory() {
      editLink.setUnderline(true);

      editLink.setOnAction((ActionEvent event) -> {
        BookItem item = getTableView().getItems().get(getIndex());
        try {
          openEditBookItemPage(item);
        } catch (IOException e) {
          logger.error("Failed to open edit page", e);
        }
      });

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
  }

  /**
   * Shows a simple alert dialog with the specified type, title, and content.
   *
   * @param content The content message of the alert.
   */
  private void showAlert(String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}

