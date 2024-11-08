package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.group4.base.users.Member;

public class MemberEditController {

  public JFXButton save;
  @FXML private Label memberID;  // Đổi thành Label
  @FXML private TextField memberName, memberEmail, memberPhone;
  @FXML private DatePicker memberBirth;

  private Member currentMember;
  private MemberViewController parentController;

  // Setter cho controller cha
  public void setParentController(MemberViewController controller) {
    this.parentController = controller;
  }

  public void setMemberData(Member member) {
    this.currentMember = member;
    memberID.setText(member.getMemberId());  // Hiển thị ID dưới dạng Label, không cho phép chỉnh sửa
    memberName.setText(member.getName());
    memberBirth.setValue(member.getDateOfBirth());
    memberEmail.setText(member.getEmail());
    memberPhone.setText(member.getPhoneNumber());
  }

  @FXML
  private void saveMember() {
    currentMember.setName(memberName.getText());
    currentMember.setDateOfBirth(memberBirth.getValue());
    currentMember.setEmail(memberEmail.getText());
    currentMember.setPhoneNumber(memberPhone.getText());

    if (parentController != null) {
      parentController.refreshTable();  // Làm mới bảng trong MemberViewController
    }

    closeForm();
  }

  @FXML
  private void cancel() {
    closeForm();
  }

  private void closeForm() {
    Stage stage = (Stage) memberID.getScene().getWindow();
    stage.close();
  }
}
