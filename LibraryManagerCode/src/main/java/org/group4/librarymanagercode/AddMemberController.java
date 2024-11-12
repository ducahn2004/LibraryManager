package org.group4.librarymanagercode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddMemberController {

  @FXML
  private Label memberID;
  @FXML
  private TextField memberName;
  @FXML
  private DatePicker memberBirth;
  @FXML
  private TextField memberEmail;
  @FXML
  private TextField memberPhone;

  public void cancel(ActionEvent actionEvent) {
  }

  public void saveMember(ActionEvent actionEvent) {
  }
}
