package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.group4.base.users.Librarian;
import org.group4.base.users.Member;
import org.group4.database.LibrarianDatabase;
import org.group4.database.MemberDatabase;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemberViewController {

  @FXML
  private TextField searchField;
  @FXML
  private TableView<Member> memberTable;
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
  private TableColumn<Member, String> memberTableAction;
  @FXML
  private JFXButton homeButton, bookButton, returnBookButton, settingButton, closeButton;
  @FXML
  private TextField memberPhone, memberEmail, memberName, memberID;
  @FXML
  private DatePicker memberBirth;

  private final ObservableList<Member> memberList = FXCollections.observableArrayList();

  private final Librarian librarian = LibrarianDatabase.getInstance().getItems().getFirst();

  @FXML
  public void initialize() {
    setUpTableColumns();
    loadMembers();
    setUpSearchListener();
    setUpRowDoubleClick();
    setUpActionColumn();
  }

  private void setUpTableColumns() {
    memberTableID.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getMemberId()));
    memberTableName.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    memberTableBirth.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth().toString()));
    memberTablePhone.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
    memberTableEmail.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
    memberTable.setItems(memberList);
  }

  private void loadMembers() {
    memberList.clear();
    Task<ObservableList<Member>> loadTask = new Task<>() {
      @Override
      protected ObservableList<Member> call() {
        return FXCollections.observableArrayList(MemberDatabase.getInstance().getItems());
      }
    };

    loadTask.setOnSucceeded(event -> memberList.setAll(loadTask.getValue()));
    loadTask.setOnFailed(event -> showAlert(AlertType.ERROR, "Error Loading Members",
        "An error occurred while loading member data."));
    new Thread(loadTask).start();
  }

  private void setUpSearchListener() {
    searchField.textProperty()
        .addListener((observable, oldValue, newValue) -> filterPersonList(newValue));
    searchField.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ENTER) {
        filterPersonList(searchField.getText());
      }
    });
  }

  private void setUpRowDoubleClick() {
    memberTable.setRowFactory(tv -> {
      TableRow<Member> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && !row.isEmpty()) {
          showDetailPage(row.getItem());
        }
      });
      return row;
    });
  }

  private void setUpActionColumn() {
    memberTableAction.setCellFactory(new Callback<>() {
      @Override
      public TableCell<Member, String> call(TableColumn<Member, String> param) {
        return new TableCell<>() {
          final Hyperlink editLink = new Hyperlink("Edit");
          final Hyperlink deleteLink = new Hyperlink("Delete");

          {
            editLink.setOnAction(event -> showEditForm(getTableView().getItems().get(getIndex())));
            deleteLink.setOnAction(
                event -> showDeleteConfirmation(getTableView().getItems().get(getIndex())));
          }

          @Override
          protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setGraphic(null);
            } else {
              HBox hBox = new HBox(editLink, deleteLink);
              hBox.setSpacing(10);
              setGraphic(hBox);
            }
          }
        };
      }
    });
  }

  private void filterPersonList(String searchText) {
    if (searchText == null || searchText.isEmpty()) {
      memberTable.setItems(memberList);
    } else {
      ObservableList<Member> filteredList = memberList.filtered(member ->
          member.getMemberId().toLowerCase().contains(searchText.toLowerCase()) ||
              member.getName().toLowerCase().contains(searchText.toLowerCase())
      );
      memberTable.setItems(filteredList);
    }
  }

  public void refreshTable() {
    memberTable.refresh();
  }

  private void showDetailPage(Member member) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberDetails.fxml"));
      Stage detailStage = new Stage();
      detailStage.setScene(new Scene(loader.load()));
      MemberDetailsController controller = loader.getController();
      controller.setItemDetail(member);
      detailStage.setTitle("Member Detail");
      detailStage.show();
    } catch (IOException e) {
      logAndShowError("Failed to load member details page", e);
    }
  }

  private void showEditForm(Member member) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberEditForm.fxml"));
      Stage editStage = new Stage();
      editStage.setScene(new Scene(loader.load()));

      // Lấy controller và truyền dữ liệu member vào
      MemberEditController controller = loader.getController();
      controller.setMemberData(member);
      controller.setParentController(this);  // Truyền controller cha như một listener

      editStage.setTitle("Edit Member");
      editStage.showAndWait();
    } catch (IOException e) {
      logAndShowError("Failed to load edit form page", e);
    }
  }


  private void showDeleteConfirmation(Member member) {
    Alert alert = createAlert(AlertType.CONFIRMATION, "Delete Confirmation",
        "Are you sure you want to delete this member?",
        "ID: " + member.getMemberId() + "\nName: " + member.getName());
    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        memberList.remove(member);
        librarian.removeMember(member);
      }
    });
  }

  private Alert createAlert(AlertType type, String title, String header, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    return alert;
  }

  private void logAndShowError(String message, Exception e) {
    Logger.getLogger(MemberViewController.class.getName()).log(Level.SEVERE, message, e);
    showAlert(AlertType.ERROR, "Error", message);
  }

  void showAlert(AlertType type, String title, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  @FXML
  public void cancel(ActionEvent actionEvent) {
    memberID.clear();
    memberName.clear();
    memberPhone.clear();
    memberEmail.clear();
    memberBirth.setValue(null);
    memberTable.getSelectionModel().clearSelection();
  }

  @FXML
  public void saveMember(ActionEvent actionEvent) {
    Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
    if (selectedMember != null) {
      selectedMember.setName(memberName.getText());
      selectedMember.setDateOfBirth(memberBirth.getValue());
      selectedMember.setEmail(memberEmail.getText());
      selectedMember.setPhoneNumber(memberPhone.getText());
      memberTable.refresh();
      cancel(null);
    }
  }

  public void addMemberAction(ActionEvent actionEvent) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("AddMember.fxml"));
      Parent root = loader.load();
      AddMemberController controller = loader.getController();
      controller.setParentController(this);  // Set the parent controller

      Stage stage = new Stage();
      stage.setTitle("Add Member");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Add a member to the list and refresh the table
  public void addMemberToList(Member newMember) {
    memberList.add(newMember);  // Adds the new member to the ObservableList
    memberTable.setItems(memberList);  // Updates the TableView to display the new member
    memberTable.refresh();  // Refresh the table view to show the newly added member
  }


  public void MemberAction(ActionEvent actionEvent) {
  }

  public void ReturnBookAction(ActionEvent actionEvent) {
  }

  public void notificationAction(ActionEvent actionEvent) {
  }

  public void requestMenu(ContextMenuEvent contextMenuEvent) {
  }

  public void SettingAction(ActionEvent actionEvent) {
  }

  public void Close(ActionEvent actionEvent) {
  }

  public void BookAction(ActionEvent actionEvent) {
  }

  public void HomeAction(ActionEvent actionEvent) {
  }

  // Additional actions methods: HomeAction, Close, etc.
}
