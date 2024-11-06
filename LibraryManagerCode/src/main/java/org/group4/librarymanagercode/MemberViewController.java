package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.group4.base.entities.Book;
import org.group4.base.entities.Person;
import org.group4.base.users.Member;
import org.group4.database.MemberDatabase;

public class MemberViewController {

  @FXML
  private TextField searchField;
  private ObservableList<Member> memberList = FXCollections.observableArrayList();
  @FXML
  private JFXButton homeButton;
  @FXML
  private JFXButton bookButton;
  @FXML
  private JFXButton returnBookButton;
  @FXML
  private JFXButton settingButton;
  @FXML
  private JFXButton closeButton;
  @FXML
  private ContextMenu selectMemberContext;
  @FXML
  private MenuItem selectMenu;
  @FXML
  private TableColumn<Member, String> memberTableID;
  @FXML
  private TableColumn<Member, String> memberTableName;
  @FXML
  private TableColumn<Member, String> memberTablePhone;
  @FXML
  private TableColumn<Member, String> memberTableEmail;
  @FXML
  private TableView<Member> memberTable;
  @FXML
  private JFXButton delete;
  @FXML
  private JFXButton update;
  @FXML
  private JFXButton save;
  @FXML
  private JFXButton cancel;
  @FXML
  private TextField memberPhone;
  @FXML
  private TextField memberEmail;
  @FXML
  private TextField memberName;
  @FXML
  private TextField memberID;


  @FXML
  public void initialize() {
    // Set up table columns
    memberTableID.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getId()));
    memberTableName.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getPerson().getName()));
    memberTablePhone.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getPerson().getPhoneNumber()));
    memberTableEmail.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getPerson().getEmail()));
    // Load member data
    loadMembers();
    memberTable.setItems(memberList);

    // Set up row selection listener
    memberTable.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          if (newValue != null) {
            displayMemberDetails((Member) newValue);
          }
        });
    searchField.textProperty().addListener((observable, oldValue, newValue)->filterMemberList(newValue));
    searchField.setOnKeyPressed(keyEvent -> {if(keyEvent.getCode() == KeyCode.ENTER){
      filterMemberList(searchField.getText());}
    });
  }

  private void loadMembers() {
    // Load members from a data source (e.g., database or hardcoded list)
    // Example hardcoded members:
    memberList.clear();
    memberList.addAll(MemberDatabase.getInstance().getItems());
    System.out.println("Loaded members Done ");
  }

  private void displayMemberDetails(Member member) {
    memberID.setText(String.valueOf(member.getId()));
    memberName.setText(member.getPerson().getName());
    memberPhone.setText(member.getPerson().getPhoneNumber());
    memberEmail.setText(member.getPerson().getEmail());
  }
  private void filterMemberList(String searchText){
    ObservableList<Member> filteredList = FXCollections.observableArrayList();

    if (searchText == null || searchText.isEmpty()) {

      memberTable.setItems(memberList);
    } else {

      String lowerCaseFilter = searchText.toLowerCase();

      for (Member member : memberList) {

        if (member.getId().toLowerCase().contains(lowerCaseFilter) ||
            member.getPerson().getName().toLowerCase().contains(lowerCaseFilter)) {
          filteredList.add(member);
        }
      }
      memberTable.setItems(filteredList);
    }
  }


  public void requestMenu(ContextMenuEvent contextMenuEvent) {
  }

  public void fetchMemberWithKey(KeyEvent keyEvent) {
  }

  public void fetchMemberFeesDetails(MouseEvent mouseEvent) {
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
    memberID.clear();
    memberName.clear();
    memberPhone.clear();
    memberEmail.clear();
    memberTable.getSelectionModel().clearSelection(); // Bỏ chọn trong TableView
  }

  public void saveMember(ActionEvent actionEvent) {
    // Lấy thông tin từ các TextField
    String name = memberName.getText();
    String phone = memberPhone.getText();
    String email = memberEmail.getText();

    // Kiểm tra xem có thành viên nào đang được chọn không
    Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
    if (selectedMember != null) {
      // Cập nhật thuộc tính của thành viên
      selectedMember.getPerson().setName(name);
      selectedMember.getPerson().setEmail(email);
      selectedMember.getPerson().setPhoneNumber(phone);

      // Lưu thay đổi vào database nếu cần
      //MemberDatabase.updateMember(selectedMember);

      // Làm mới TableView để hiển thị thay đổi
      memberTable.refresh();

      // Xóa nội dung các TextField sau khi cập nhật
      cancel(null);
    }
  }
}
