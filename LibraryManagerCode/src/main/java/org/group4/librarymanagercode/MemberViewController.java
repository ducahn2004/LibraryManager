package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class MemberViewController {

  public JFXButton homeButton;
  public JFXButton studentsButton;
  public JFXButton bookButton;
  public JFXButton addBookButton;
  public JFXButton returnBookButton;
  public JFXButton settingButton;
  public JFXButton closeButton;
  public ContextMenu selectStudentContext;
  public MenuItem selectMenu;
  public TableColumn check;
  public TableColumn memberTableID;
  public TableColumn memberTableName;
  public TableColumn memberTablePhone;
  public TableColumn memberTableEmail;
  public TableView memberTable;
  public JFXButton delete;
  public JFXButton update;
  public JFXButton save;
  public JFXButton cancel;
  public TextField memberPhone;
  public TextField memberEmail;
  public TextField memberName;
  public TextField memberID;

  public void deleteStudentRecord(ActionEvent actionEvent) {
  }

  public void requestMenu(ContextMenuEvent contextMenuEvent) {
  }

  public void fetchStudentWithKey(KeyEvent keyEvent) {
  }

  public void fetchStudentFeesDetails(MouseEvent mouseEvent) {
  }

  public void Close(ActionEvent actionEvent) {
  }

  public void notificationAction(ActionEvent actionEvent) {
  }

  public void SettingAction(ActionEvent actionEvent) {
  }

  public void ReturnBookAction(ActionEvent actionEvent) {
  }

  public void BookAction(ActionEvent actionEvent) {
  }

  public void MemberAction(ActionEvent actionEvent) {
  }

  public void HomeAction(ActionEvent actionEvent) {
    
  }

  public void cancel(ActionEvent actionEvent) {
  }

  public void saveStudent(ActionEvent actionEvent) {
  }

  public void updateStudent(ActionEvent actionEvent) {
  }

  public void deleteStudent(ActionEvent actionEvent) {
  }
}
