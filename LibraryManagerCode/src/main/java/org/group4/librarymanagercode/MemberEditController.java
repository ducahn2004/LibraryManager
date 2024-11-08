package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.base.users.Member;

public class MemberEditController {

  public JFXButton save;
  @FXML
  private TextField memberID;
  @FXML
  private TextField memberName;
  @FXML
  private DatePicker memberBirth;
  @FXML
  private TextField memberEmail;
  @FXML
  private TextField memberPhone;

  private Member currentMember;

  // Initialize member data in the form fields
  public void setMemberData(Member member) {
    this.currentMember = member;
    memberID.setText(member.getMemberId());
    memberName.setText(member.getName());
    memberBirth.setValue(member.getDateOfBirth());
    memberEmail.setText(member.getEmail());
    memberPhone.setText(member.getPhoneNumber());
  }

  @FXML
  private void saveMember() {
    // Update member details from form fields
    currentMember.setName(memberName.getText());
    currentMember.setDateOfBirth(memberBirth.getValue());
    currentMember.setEmail(memberEmail.getText());
    currentMember.setPhoneNumber(memberPhone.getText());

    // Close the dialog after saving
    closeForm();
  }

  @FXML
  private void cancel() {
    // Close the dialog without saving
    closeForm();
  }

  private void closeForm() {
    Stage stage = (Stage) memberID.getScene().getWindow();
    stage.close();
  }
}
