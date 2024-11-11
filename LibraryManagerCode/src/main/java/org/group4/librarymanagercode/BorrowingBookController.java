package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.group4.base.books.BookLending;

public class BorrowingBookController {

  @FXML
  private JFXButton homeButton;
  @FXML
  private Button submit;
  @FXML
  private TextField lateFee;
  @FXML
  private ListView<String> listView;
  @FXML
  private Button renew;

  public void setBookLendingDetails(BookLending bookLending) {
    if (bookLending != null) {
      listView.getItems().addAll(
          "ISBN: " + bookLending.getBookItem().getISBN(),
          "Title: " + bookLending.getBookItem().getTitle(),
          "Barcode: " + bookLending.getBookItem().getBarcode(),
          "Creation Date: " + bookLending.getCreationDate().toString(),
          "Due Date: " + bookLending.getDueDate().toString()
      );
    }

    // Set custom cell factory for horizontal layout
    listView.setCellFactory(new Callback<>() {
      @Override
      public ListCell<String> call(ListView<String> listView) {
        return new ListCell<>() {
          @Override
          protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) {
              HBox hbox = new HBox(10); // Spacing between elements
              Label label = new Label(item);
              hbox.getChildren().add(label);
              setGraphic(hbox);
            } else {
              setGraphic(null);
            }
          }
        };
      }
    });
  }

  @FXML
  private void submitBook(ActionEvent actionEvent) {
    // Handle submit event
  }

  @FXML
  private void renewBook(ActionEvent actionEvent) {
    // Handle renew event
  }

  @FXML
  private void HomeAction(ActionEvent actionEvent) {
  }

  @FXML
  private void MemberAction(ActionEvent actionEvent) {
  }

  @FXML
  private void ReturnBookAction(ActionEvent actionEvent) {
  }

  @FXML
  private void notificationAction(ActionEvent actionEvent) {
  }

  @FXML
  private void Close(ActionEvent actionEvent) {
  }

  @FXML
  private void SettingAction(ActionEvent actionEvent) {
  }

  @FXML
  private void BookAction(ActionEvent actionEvent) {
  }
}
