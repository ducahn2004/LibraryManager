package org.group4.librarymanagercode;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.group4.base.books.BookItem;
import org.group4.base.books.Book;
import org.group4.base.enums.BookStatus;
import org.group4.base.users.Librarian;
import org.group4.database.BookItemDatabase;
import org.group4.database.LibrarianDatabase;

public class BookDetailsController {

  Librarian librarian = LibrarianDatabase.getInstance().getItems().getFirst();
  private Book currentBook;
  private final ObservableList<BookItem> bookItems = FXCollections.observableArrayList();

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
  }

  public void setItemDetail(Book book) {
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
            openBorrowingBookPage();
          } else if (selectedItem.getStatus() == BookStatus.LOANED) {
            openBookLendingPage();
          }
        } catch (IOException e) {
          e.printStackTrace();
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
        cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getReference())));
    //status.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));
    status.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));
    borrowedDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(formatLocalDate(cellData.getValue().getBorrowed())));
    dueDate.setCellValueFactory(
        cellData -> new SimpleStringProperty(formatLocalDate(cellData.getValue().getDueDate())));
    price.setCellValueFactory(
        cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
    format.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getFormat().toString()));
    dateOfPurchase.setCellValueFactory(cellData -> new SimpleStringProperty(
        formatLocalDate(cellData.getValue().getDateOfPurchase())));
    publicationDate.setCellValueFactory(cellData -> new SimpleStringProperty(
        formatLocalDate(cellData.getValue().getPublicationDate())));

    // Set the cell factory to use a ComboBox for editing the status
    status.setCellFactory(column -> new TableCell<>() {
      private final ComboBox<BookStatus> comboBox = new ComboBox<>();

      {
        comboBox.setItems(
            FXCollections.observableArrayList(BookStatus.values())); // Populate with enum values
        comboBox.setOnAction(event -> {
          BookItem bookItem = getTableView().getItems().get(getIndex());
          bookItem.setStatus(comboBox.getValue()); // Update the status of the book item
        });
      }

      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
          setGraphic(null);
        } else {
          BookItem bookItem = getTableView().getItems().get(getIndex());
          comboBox.setValue(bookItem.getStatus()); // Set the current status
          setGraphic(comboBox);
        }
      }
    });

    actionColumn.setCellFactory(param -> new TableCell<>() {
      private final Hyperlink editLink = new Hyperlink("Edit");
      private final Hyperlink deleteLink = new Hyperlink("Delete");

      {
        editLink.setUnderline(true);
        // Xử lý sự kiện cho liên kết Edit
        editLink.setOnAction((ActionEvent event) -> {
//          BookItem item = getTableView().getItems().get(getIndex());
//          showItemDetails(item);
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
          HBox hBox = new HBox(10, editLink,
              deleteLink); // Đặt khoảng cách giữa các liên kết là 10 (có thể điều chỉnh)
          setGraphic(hBox);
        }
      }
    });

    tableView.setItems(bookItems); // Bind the data list to the table
  }


  private void loadData() {
    if (currentBook != null) {
      bookItems.clear();
      bookItems.addAll(BookItemDatabase.getInstance().getItems().stream()
          .filter(item -> item.getISBN().equals(currentBook.getISBN()))
          .toList());
      System.out.println("Data loaded: " + bookItems.size() + " items");
    }
  }


  private void openBorrowingBookPage() throws IOException {
    Stage stage = new Stage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/BorrowingBook.fxml"));
    stage.setScene(new Scene(loader.load()));
    stage.setTitle("Borrowing Book");
    stage.show();
  }

  private void openBookLendingPage() throws IOException {
    Stage stage = new Stage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/BookLending.fxml"));
    stage.setScene(new Scene(loader.load()));
    stage.setTitle("Book Lending");
    stage.show();
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
        if (librarian.removeBookItem(item)) {
          bookItems.remove(item);
          System.out.println("Item deleted: " + item);
        }
      }
    });
  }
//  public void showItemDetails(BookItem item) {
//    // Tạo một cửa sổ mới
//    Stage detailStage = new Stage();
//    detailStage.setTitle("Edit Book Item");
//
//    // Tạo GridPane để sắp xếp các thành phần
//    GridPane gridPane = new GridPane();
//    gridPane.setHgap(10);
//    gridPane.setVgap(10);
//
//    TextField priceField = new TextField(String.valueOf(item.getPrice()));
//    ComboBox<BookFormat> formatComboBox = new ComboBox<>(FXCollections.observableArrayList(BookFormat.values()));
//    formatComboBox.setValue(item.getFormat());
//
//    DatePicker dateOfPurchasePicker = new DatePicker(item.getDateOfPurchase());
//    DatePicker publicationDatePicker = new DatePicker(item.getPublicationDate());
//
//    // Thêm các trường vào GridPane
//    gridPane.add(new Label("Barcode:"), 0, 0);
//    gridPane.add(barcodeField, 1, 0);
//    gridPane.add(new Label("Reference Only:"), 0, 1);
//    Node referenceOnlyComboBox = null;
//    gridPane.add(referenceOnlyComboBox, 1, 1);
//    gridPane.add(new Label("Price:"), 0, 2);
//    gridPane.add(priceField, 1, 2);
//    gridPane.add(new Label("Format:"), 0, 3);
//    gridPane.add(formatComboBox, 1, 3);
//    gridPane.add(new Label("Date of Purchase:"), 0, 4);
//    gridPane.add(dateOfPurchasePicker, 1, 4);
//    gridPane.add(new Label("Publication Date:"), 0, 5);
//    gridPane.add(publicationDatePicker, 1, 5);
//
//    // Nút Save để lưu thay đổi
//    Button saveButton = new Button("Save");
//    saveButton.setOnAction(e -> {
//      // Lưu các giá trị cập nhật vào BookItem
//      item.setDateOfPurchase(dateOfPurchasePicker.getValue());
//      item.setPublicationDate(publicationDatePicker.getValue());
//
//      // Đóng cửa sổ chi tiết
//      detailStage.close();
//      tableView.refresh(); // Cập nhật bảng TableView
//    });
//
//    // Nút Cancel để hủy bỏ
//    Button cancelButton = new Button("Cancel");
//    cancelButton.setOnAction(e -> detailStage.close());
//
//    // Thêm nút vào GridPane
//    gridPane.add(saveButton, 0, 6);
//    gridPane.add(cancelButton, 1, 6);
//
//    // Cấu hình Scene và hiển thị cửa sổ
//    Scene scene = new Scene(gridPane, 400, 300);
//    detailStage.setScene(scene);
//    detailStage.initModality(Modality.APPLICATION_MODAL);
//    detailStage.showAndWait();
//  }


}
