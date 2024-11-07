package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.group4.base.books.BookLending;

public class ReturnBookController {

  public JFXButton homeButton;
  public JFXButton MemberButton;
  public JFXButton bookButton;
  public JFXButton returnBookButton;
  public JFXButton notificationButton;
  public JFXButton settingButton;
  public JFXButton closeButton;
  public TableView<BookLending> bookLendingTable;
  public TableColumn<BookLending, String> bookItemID;
  public TableColumn<BookLending, String> bookName;
  public TableColumn<BookLending, String> memberID;
  public TableColumn<BookLending, String> memberName;
  public TableColumn<BookLending, String> creationDate;
  public TableColumn<BookLending, String> dueDate;
  public TableColumn<BookLending, String> returnDate;

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
