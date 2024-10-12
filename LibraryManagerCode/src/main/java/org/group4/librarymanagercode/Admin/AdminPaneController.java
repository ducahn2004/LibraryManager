package org.group4.librarymanagercode.Admin;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class AdminPaneController {
  @FXML
  private JFXButton homeButton;
  @FXML
  private JFXButton bookButton;
  @FXML
  private JFXButton studentsButton;
  @FXML
  private JFXButton IssueBookButton;
  @FXML
  private JFXButton removeBookButton;
  @FXML
  private JFXButton addBookButton;
  @FXML
  private JFXButton returnBookButton;
  @FXML
  private JFXButton settingButton;
  @FXML
  private JFXButton closeButton;

  @FXML
  public void onHomeAction () throws IOException {
    BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("/librarymanagementsystem/view/librarian.fxml"));
    BorderPane borderpane = null;
    borderpane.setCenter(borderPane);
  }
  @FXML
  public void onBookAction(){

  }
  @FXML
  public void onStudentsAction(){

  }
  @FXML
  public void onIssueBookAction(){

  }
  @FXML
  public void RemoveBook(){

  }
  @FXML
  public void AddBookAction(){

  }
  @FXML
  public void ReturnBookAction(){

  }
  @FXML
  public void SettingAction(){

  }
  @FXML
  public void closeAction(){

  }

}
