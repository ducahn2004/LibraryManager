package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import org.group4.base.users.Member;
import org.group4.database.MemberDatabase;

public class MemberViewController {


  public JFXButton MemberButton;
  public JFXButton notificationButton;
  @FXML
  private TextField searchField;
  private final ObservableList<Member> memberList = FXCollections.observableArrayList();
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
  private ContextMenu selectPersonContext;
  @FXML
  private MenuItem selectMenu;
  @FXML
  private TableColumn<Member, String> memberTableID;
  @FXML
  private TableColumn<Member, String> memberTableName;
  @FXML
  private TableColumn<Member, String> memberTableBirth;
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
  private DatePicker memberBirth;


  @FXML
  public void initialize() {
    // Set up table columns
    memberTableID.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getMemberId()));
    memberTableName.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    memberTableBirth.setCellValueFactory(cellData -> new SimpleStringProperty(
        cellData.getValue().getDateOfBirth().toString()));
    memberTablePhone.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
    memberTableEmail.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
    // Load member data
    loadPersons();
    memberTable.setItems(memberList);

    // Set up row selection listener
    memberTable.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          if (newValue != null) {
            displayPersonDetails((Member) newValue);
          }
        });
    searchField.textProperty()
        .addListener((observable, oldValue, newValue) -> filterPersonList(newValue));
    searchField.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ENTER) {
        filterPersonList(searchField.getText());
      }
    });
  }

  private void loadPersons() {
    // Load members from a data source (e.g., database or hardcoded list)
    // Example hardcoded members:
    memberList.clear();
    memberList.addAll(MemberDatabase.getInstance().getItems());
    System.out.println("Loaded members Done ");
  }

  private void displayPersonDetails(Member member) {
    memberID.setText(String.valueOf(member.getMemberId()));
    memberName.setText(member.getName());

    memberPhone.setText(member.getPhoneNumber());
    memberEmail.setText(member.getEmail());
  }

  private void filterPersonList(String searchText) {
    ObservableList<Member> filteredList = FXCollections.observableArrayList();

    if (searchText == null || searchText.isEmpty()) {

      memberTable.setItems(memberList);
    } else {

      String lowerCaseFilter = searchText.toLowerCase();

      for (Member member : memberList) {

        if (member.getMemberId().toLowerCase().contains(lowerCaseFilter) ||
            member.getName().toLowerCase().contains(lowerCaseFilter)) {
          filteredList.add(member);
        }
      }
      memberTable.setItems(filteredList);
    }
  }


  public void requestMenu(ContextMenuEvent contextMenuEvent) {
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

  public void HomeAction(ActionEvent actionEvent) {

  }

  public void cancel(ActionEvent actionEvent) {
    memberID.clear();
    memberName.clear();
    memberPhone.clear();
    memberEmail.clear();
    memberTable.getSelectionModel().clearSelection();
  }

  public void saveMember(ActionEvent actionEvent) {
    String name = memberName.getText();
    String phone = memberPhone.getText();
    String email = memberEmail.getText();
    LocalDate birthDate = memberBirth.getValue();

    Member selectedPerson = memberTable.getSelectionModel().getSelectedItem();
    if (selectedPerson != null) {
      selectedPerson.setName(name);
      selectedPerson.setDateOfBirth(birthDate);
      selectedPerson.setEmail(email);
      selectedPerson.setPhoneNumber(phone);

      memberTable.refresh();

      cancel(null);
    }
  }


  public void MemberAction(ActionEvent actionEvent) {
  }
}
