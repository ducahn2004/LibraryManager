package org.group4.librarymanagercode.Admin;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
  public TableColumn stuID;
  public TableColumn stunNme;
  public TableColumn stuPhone;
  public TableColumn stuEmail;
  public TableView studentTable;

  public void deleteStudentRecord(ActionEvent actionEvent) {
  }

  public void requestMenu(ContextMenuEvent contextMenuEvent) {
  }

  public void fetchStudentWithKey(KeyEvent keyEvent) {
  }

  public void fetchStudentFeesDetails(MouseEvent mouseEvent) {
  }

}
