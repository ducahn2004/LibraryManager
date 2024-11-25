package org.group4.controller;

import java.sql.SQLException;
import javafx.application.Platform;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.group4.model.user.Librarian;
import org.group4.service.user.SessionManagerService;

/**
 * Controller for the Admin Pane.
 * <p>
 * This class handles the interactions and updates for the Admin Pane, including displaying
 * statistics, handling navigation, and updating charts.
 */
public class AdminPaneController {

  public Label total_members_borrowed;
  @FXML
  private Label total_Members;
  @FXML
  private Label total_books;

  @FXML
  private BarChart<String, Number> home_chart;
  @FXML
  private CategoryAxis xAxis;
  @FXML
  private NumberAxis yAxis;

  @FXML
  private JFXButton homeButton;
  @FXML
  private JFXButton MemberButton;
  @FXML
  private JFXButton bookButton;
  @FXML
  private JFXButton bookLendingButton;
  @FXML
  private JFXButton settingButton;
  @FXML
  private JFXButton notificationButton;
  @FXML
  private JFXButton closeButton;

  private Stage stage;

  Librarian librarian = SessionManagerService.getInstance().getCurrentLibrarian();

  /**
   * Updates the total number of members displayed in the UI.
   */
  public void updateTotalMembers() throws SQLException {
    int totalMembers = librarian.getMemberManager().getAll().size();
    total_Members.setText(String.valueOf(totalMembers));
  }

  /**
   * Updates the total number of books displayed in the UI.
   */
  public void updateTotalBooks() throws SQLException {
    int totalBooks = librarian.getBookManager().getAll().size();
    total_books.setText(String.valueOf(totalBooks));
  }

  public void updateMembersWhoBorrowed() throws SQLException {
    int totalMembersWhoBorrowed = librarian.getLendingManager().getAll().size();
    total_members_borrowed.setText(String.valueOf(totalMembersWhoBorrowed)); // Hiển thị lên UI
  }

  /**
   * Updates the bar chart with total members and total books data.
   */
  public void Home_chart() throws SQLException {
    home_chart.getData().clear();

    int totalBooks = librarian.getBookManager().getAll().size();
    int totalMembers = librarian.getMemberManager().getAll().size();

    // Create and populate the "Books" series
    XYChart.Series<String, Number> bookSeries = new XYChart.Series<>();
    bookSeries.setName("Books");
    bookSeries.getData().add(new XYChart.Data<>("Total Books", totalBooks));

    // Create and populate the "Members" series
    XYChart.Series<String, Number> memberSeries = new XYChart.Series<>();
    memberSeries.setName("Members");
    memberSeries.getData().add(new XYChart.Data<>("Total Members", totalMembers));

    // Add data to the bar chart
    home_chart.getData().addAll(bookSeries, memberSeries);
  }

  /**
   * Initializes the Admin Pane by updating statistics and the chart.
   */
  @FXML
  public void initialize() throws SQLException {
    updateTotalMembers();
    updateTotalBooks();
    Home_chart();
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
