package org.group4.librarymanagercode.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.group4.base.entities.Book;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class BookDetailController {

  @FXML
  private Label isbnLabel;
  @FXML
  private Label titleLabel;
  @FXML
  private Label authorLabel;

  @FXML
  private ComboBox<String> formatComboBox;
  @FXML
  private ComboBox<String> statusComboBox;

  private Book currentBook;

  public void setBookDetails(Book book) {
    this.currentBook = book;
    isbnLabel.setText(book.getISBN());
    titleLabel.setText(book.getTitle());


    // Set values for ComboBox (book format and status)
    formatComboBox.getItems().addAll("E-book", "Paperback", "Hardcover");
    statusComboBox.getItems().addAll("Available", "Checked out");

    // Set default values (if applicable)
//    formatComboBox.setValue(book.getFormat());
//    statusComboBox.setValue(book.getStatus());
  }

//  @FXML
//  private void onEditBook() {
//    // Khi nhấn nút "Edit", bạn có thể cho phép chỉnh sửa thông tin sách
//    currentBook.setFormat(formatComboBox.getValue());
//    currentBook.setStatus(statusComboBox.getValue());
//    System.out.println("Book updated: " + currentBook);
//  }

  @FXML
  private void onRemoveBook() {
    // Hiển thị bảng cảnh báo trước khi xóa sách
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirm Remove");
    alert.setHeaderText("Are you sure you want to remove this book?");
    alert.setContentText("Book: " + currentBook.getTitle());

    // Nếu chọn "OK", xóa sách
    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        // Xóa sách (thực hiện hành động xóa trong database hoặc danh sách sách)
        System.out.println("Book removed: " + currentBook.getTitle());
      }
    });
  }
}
