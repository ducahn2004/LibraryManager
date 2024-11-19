package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.group4.dao.FactoryDAO;
import org.group4.module.manager.SessionManager;
import org.group4.module.users.Librarian;
import org.group4.module.users.Member;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemberViewController {

  @FXML
  private JFXButton closeButton;
  @FXML
  private JFXButton bookLendingButton;
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
  private TableColumn<Member, Void> memberTableAction;
  @FXML
  private JFXButton homeButton, bookButton, settingButton, notificationButton, MemberButton;
  @FXML
  private TextField memberPhone, memberEmail, memberName, memberID;
  @FXML
  private DatePicker memberBirth;

  private final ObservableList<Member> memberList = FXCollections.observableArrayList();

  private final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();

  @FXML
  public void initialize() {
    setUpTableColumns();
    loadMembers();
    setUpSearchListener();
    setUpRowDoubleClick();
    //setUpActionColumn();
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
    memberTableAction.setCellFactory(param -> new TableCell<>() {
      private final Hyperlink editLink = new Hyperlink("Edit");
      private final Hyperlink deleteLink = new Hyperlink("Delete");

      {
        editLink.setUnderline(true);
        // Xử lý sự kiện cho liên kết Edit
        editLink.setOnAction((ActionEvent event) -> {
          Member item = getTableView().getItems().get(getIndex());
          showEditForm(item);
        });

        // Xử lý sự kiện cho liên kết Delete
        deleteLink.setOnAction((ActionEvent event) -> {
          Member item = getTableView().getItems().get(getIndex());
          showDeleteConfirmation(item);
        });
      }

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setGraphic(null);
        } else {
          HBox hBox = new HBox(10, editLink, deleteLink);
          setGraphic(hBox);
        }
      }
    });
    memberTable.setItems(memberList);
  }

  private void loadMembers() {
    memberList.clear();
    Task<ObservableList<Member>> loadTask = new Task<>() {
      @Override
      protected ObservableList<Member> call() {
        return FXCollections.observableArrayList(FactoryDAO.getMemberDAO().getAll());
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
        librarian.deleteMember(member.getMemberId());
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


  private Stage getStage() {
    return (Stage) homeButton.getScene().getWindow(); // Có thể sử dụng bất kỳ button nào
  }

  public void HomeAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "AdminPane.fxml", "Library Manager");
  }

  public void MemberAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "MemberView.fxml", "Library Manager");
  }

  public void BookAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "BookView.fxml", "Library Manager");
  }

  public void BookLendingAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "BookLending.fxml", "Library Manager");
  }

  public void notificationAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Notification.fxml", "Library Manager");
  }

  public void SettingAction(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.switchScene(getStage(), "Setting.fxml", "Library Manager");
  }

  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }

}
