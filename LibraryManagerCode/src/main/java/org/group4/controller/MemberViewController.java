package org.group4.controller;

import com.jfoenix.controls.JFXButton;
import java.sql.SQLException;
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
import org.group4.service.user.SessionManager;
import org.group4.model.user.Librarian;
import org.group4.model.user.Member;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for managing member views and operations in the library system.
 */
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

  /**
   * Initializes the controller by setting up the table columns, loading members, and adding
   * listeners for search and row double-click events.
   */
  @FXML
  public void initialize() {
    setUpTableColumns();
    loadMembers();
    setUpSearchListener();
    setUpRowDoubleClick();
  }

  /**
   * Configures the table columns to display member information.
   */
  private void setUpTableColumns() {
    memberTableID.setCellValueFactory(cellData
        -> new SimpleStringProperty(cellData.getValue().getMemberId()));
    memberTableName.setCellValueFactory(cellData
            -> new SimpleStringProperty(cellData.getValue().getName()));
    memberTableBirth.setCellValueFactory(cellData
            -> new SimpleStringProperty(cellData.getValue().getDateOfBirth().toString()));
    memberTablePhone.setCellValueFactory(cellData
            -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
    memberTableEmail.setCellValueFactory(cellData
            -> new SimpleStringProperty(cellData.getValue().getEmail()));

    memberTableAction.setCellFactory(param -> new TableCell<>() {
      private final Hyperlink editLink = new Hyperlink("Edit");
      private final Hyperlink deleteLink = new Hyperlink("Delete");

      {
        // Set up event handlers for edit and delete links
        editLink.setOnAction(event -> {
          Member item = getTableView().getItems().get(getIndex());
          showEditForm(item);
        });
        deleteLink.setOnAction(event -> {
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
          setGraphic(new HBox(10, editLink, deleteLink));
        }
      }
    });
    memberTable.setItems(memberList);
  }

  /**
   * Loads all members from the database and populates the member list.
   */
  private void loadMembers() {
    memberList.clear();
    Task<ObservableList<Member>> loadTask = new Task<>() {
      @Override
      protected ObservableList<Member> call() throws SQLException {
        return FXCollections.observableArrayList(librarian.getMemberManager().getAll());
      }
    };

    loadTask.setOnSucceeded(event -> memberList.setAll(loadTask.getValue()));
    loadTask.setOnFailed(event -> showAlert("Error Loading Members",
        "An error occurred while loading member data."));
    new Thread(loadTask).start();
  }

  /**
   * Sets up a listener for the search field to filter members based on input.
   */
  private void setUpSearchListener() {
    searchField.textProperty()
        .addListener((observable, oldValue, newValue)
            -> filterPersonList(newValue));
    searchField.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ENTER) {
        filterPersonList(searchField.getText());
      }
    });
  }

  /**
   * Configures double-clicking a row to open the member detail page.
   */
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

  /**
   * Filters the member list based on the search text.
   *
   * @param searchText The text to filter members by.
   */
  private void filterPersonList(String searchText) {
    if (searchText == null || searchText.isEmpty()) {
      memberTable.setItems(memberList);
    } else {
      ObservableList<Member> filteredList = memberList.filtered(member ->
          member.getMemberId().toLowerCase().contains(searchText.toLowerCase()) ||
              member.getName().toLowerCase().contains(searchText.toLowerCase()));
      memberTable.setItems(filteredList);
    }
  }

  /**
   * Refreshes the table view to ensure the latest data is displayed.
   */
  public void refreshTable() {
    memberTable.refresh();
  }

  /**
   * Opens the member detail page for the specified member.
   *
   * @param member The member whose details will be displayed.
   */
  private void showDetailPage(Member member) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberDetails.fxml"));
      Stage detailStage = new Stage();
      detailStage.setScene(new Scene(loader.load()));

      // Pass the selected member to the details controller
      MemberDetailsController controller = loader.getController();
      controller.setMemberDetail(member);

      detailStage.setTitle("Member Detail");
      detailStage.show();
    } catch (IOException e) {
      logAndShowError("Failed to load member details page", e);
    } catch (SQLException e) {
      // Handle SQL exceptions (e.g., database access issues)
      showAlert(Alert.AlertType.ERROR, "Database Error",
          "An error occurred while accessing the database. Please try again.");
    }
  }

  /**
   * Opens the edit form for the specified member.
   *
   * @param member The member to edit.
   */
  private void showEditForm(Member member) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberEditForm.fxml"));
      Stage editStage = new Stage();
      editStage.setScene(new Scene(loader.load()));

      // Pass the member and parent controller to the edit form
      MemberEditController controller = loader.getController();
      controller.setMemberData(member);
      controller.setParentController(this);

      editStage.setTitle("Edit Member");
      editStage.showAndWait();
    } catch (IOException e) {
      logAndShowError("Failed to load edit form page", e);
    }
  }

  /**
   * Shows a confirmation dialog for deleting a member.
   *
   * @param member The member to be deleted.
   */
  private void showDeleteConfirmation(Member member) {
    Alert alert = createAlert(
        "ID: " + member.getMemberId() + "\nName: " + member.getName()
    );

    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        memberList.remove(member);
        try {
          librarian.getMemberManager().delete(member.getMemberId());
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    });
  }

  /**
   * Creates a reusable alert with the specified parameters.
   *
   * @param content The content text of the alert.
   * @return A configured Alert instance.
   */
  private Alert createAlert(String content) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Delete Confirmation");
    alert.setHeaderText("Are you sure you want to delete this member?");
    alert.setContentText(content);
    return alert;
  }

  /**
   * Logs an error and shows an error alert.
   *
   * @param message The error message to log and display.
   * @param e       The exception that occurred.
   */
  private void logAndShowError(String message, Exception e) {
    Logger.getLogger(MemberViewController.class.getName()).log(Level.SEVERE, message, e);
    showAlert("Error", message);
  }

  /**
   * Displays an alert with the specified type, title, and content.
   *
   * @param title   The title of the alert.
   * @param content The content of the alert.
   */
  void showAlert(String title, String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  /**
   * Displays an alert dialog with the specified type, title, and content.
   *
   * @param alertType the type of alert (e.g., INFORMATION, WARNING)
   * @param title     the title of the alert dialog
   * @param message   the message of the alert dialog
   */
  private static void showAlert(AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(title);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Clears all input fields in the form and deselects any selected table row.
   *
   * @param actionEvent The event triggered by the cancel action.
   */
  @FXML
  public void cancel(ActionEvent actionEvent) {
    memberID.clear();
    memberName.clear();
    memberPhone.clear();
    memberEmail.clear();
    memberBirth.setValue(null);
    memberTable.getSelectionModel().clearSelection();
  }

  /**
   * Handles the action of adding a new member by opening the add member form.
   *
   * @param actionEvent The event triggered by clicking the add member button.
   */
  public void addMemberAction(ActionEvent actionEvent) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMember.fxml"));
      Parent root = loader.load();

      // Pass the parent controller to the add member form
      AddMemberController controller = loader.getController();
      controller.setParentController(this);

      Stage stage = new Stage();
      stage.setTitle("Add Member");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a new member to the list and refreshes the table.
   *
   * @param newMember The new member to add.
   */
  public void addMemberToList(Member newMember) {
    memberList.add(newMember);  // Adds the new member to the ObservableList
    memberTable.refresh();     // Refreshes the table to display the new member
  }

  /**
   * Returns the current stage of the view.
   *
   * @return The current Stage object.
   */
  private Stage getStage() {
    return (Stage) homeButton.getScene().getWindow();
  }

// Navigation actions for switching between different views

  public void HomeAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "AdminPane.fxml", "Library Manager");
  }

  public void MemberAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "MemberView.fxml", "Library Manager");
  }

  public void BookAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "BookView.fxml", "Library Manager");
  }

  public void BookLendingAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "BookLending.fxml", "Library Manager");
  }

  public void notificationAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "Notification.fxml", "Library Manager");
  }

  public void SettingAction(ActionEvent actionEvent) {
    SceneSwitcher.switchScene(getStage(), "Setting.fxml", "Library Manager");
  }

  /**
   * Closes the application.
   *
   * @param actionEvent The event triggered by clicking the close button.
   */
  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }
}

