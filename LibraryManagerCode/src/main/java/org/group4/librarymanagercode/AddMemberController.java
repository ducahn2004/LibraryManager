package org.group4.librarymanagercode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.base.users.Member;

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

  private Member currentMember;
  private MemberViewController parentController;

  public void setMember(Member member) {
    this.currentMember = member;
    memberID.setText(member.getMemberId());
    memberName.setText(member.getName());
    memberBirth.setValue(member.getDateOfBirth());
    memberEmail.setText(member.getEmail());
    memberPhone.setText(member.getPhoneNumber());
  }

  public void cancel(ActionEvent actionEvent) {
    closeForm();
  }

  public void saveMember(ActionEvent actionEvent) {
    if (currentMember != null) {
      currentMember.setName(memberName.getText());
      currentMember.setDateOfBirth(memberBirth.getValue());
      currentMember.setEmail(memberEmail.getText());
      currentMember.setPhoneNumber(memberPhone.getText());

      if (parentController != null) {
        parentController.addMemberToList(
            currentMember);  // Update the table in MemberViewController
      }

      closeForm();
    } else {
      // Handle the case where currentMember is null, perhaps show an error message
      parentController.showAlert(AlertType.ERROR, "Error", "Member object is not initialized.");
    }
  }

  public void setParentController(MemberViewController parentController) {
    this.parentController = parentController;
  }

  private void closeForm() {
    Stage stage = (Stage) memberID.getScene().getWindow();
    stage.close();
  }
}
