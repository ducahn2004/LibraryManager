package org.group4.controller;

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
  public void updateTotalMembers() {
    int totalMembers = librarian.getMemberManager().getAll().size();
    total_Members.setText(String.valueOf(totalMembers));
  }

  /**
   * Updates the total number of books displayed in the UI.
   */
  public void updateTotalBooks() {
    int totalBooks = librarian.getBookManager().getAll().size();
    total_books.setText(String.valueOf(totalBooks));
  }

  /**
   * Updates the bar chart with total members and total books data.
   */
  public void Home_chart() {
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
  public void initialize() {
    updateTotalMembers();
    updateTotalBooks();
    Home_chart();
  }

  /**
   * Handles the action for the Home button.
   * <p>
   * Navigates to the Admin Pane view.
   *
   * @param actionEvent The event triggered by clicking the Home button.
   * @throws IOException If the FXML file cannot be loaded.
   */
  public void HomeAction(ActionEvent actionEvent) throws IOException {
    navigateToView("AdminPane.fxml", homeButton, "Library Manager");
    System.out.println("Home button clicked");
  }

  /**
   * Handles the action for the Member button.
   * <p>
   * Navigates to the Member View.
   *
   * @param actionEvent The event triggered by clicking the Member button.
   * @throws IOException If the FXML file cannot be loaded.
   */
  public void MemberAction(ActionEvent actionEvent) throws IOException {
    navigateToView("MemberView.fxml", MemberButton, "Library Manager");
    System.out.println("Member button clicked");
  }

  /**
   * Handles the action for the Book button.
   * <p>
   * Navigates to the Book View.
   *
   * @param actionEvent The event triggered by clicking the Book button.
   * @throws IOException If the FXML file cannot be loaded.
   */
  public void BookAction(ActionEvent actionEvent) throws IOException {
    navigateToView("BookView.fxml", bookButton, "Library Manager");
    System.out.println("Book button clicked");
  }

  /**
   * Handles the action for the Book Lending button.
   * <p>
   * Navigates to the Book Lending View.
   *
   * @param actionEvent The event triggered by clicking the Book Lending button.
   * @throws IOException If the FXML file cannot be loaded.
   */
  public void BookLendingAction(ActionEvent actionEvent) throws IOException {
    navigateToView("BookLending.fxml", bookLendingButton, "Library Manager");
    System.out.println("Book Lending button clicked");
  }

  /**
   * Handles the action for the Notification button.
   * <p>
   * Navigates to the Notification View.
   *
   * @param actionEvent The event triggered by clicking the Notification button.
   * @throws IOException If the FXML file cannot be loaded.
   */
  public void notificationAction(ActionEvent actionEvent) throws IOException {
    navigateToView("Notification.fxml", notificationButton, "Library Manager");
    System.out.println("Notification button clicked");
  }

  /**
   * Handles the action for the Setting button.
   * <p>
   * Navigates to the Setting View.
   *
   * @param actionEvent The event triggered by clicking the Setting button.
   * @throws IOException If the FXML file cannot be loaded.
   */
  public void SettingAction(ActionEvent actionEvent) throws IOException {
    navigateToView("Setting.fxml", settingButton, "Library Manager");
    System.out.println("Setting button clicked");
  }

  /**
   * Handles the action for the Close button.
   * <p>
   * Exits the application.
   *
   * @param actionEvent The event triggered by clicking the Close button.
   */
  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }

  /**
   * Navigates to a specified view.
   *
   * @param fxmlFile The FXML file to load.
   * @param button   The button triggering the navigation.
   * @param title    The title of the new scene.
   * @throws IOException If the FXML file cannot be loaded.
   */
  private void navigateToView(String fxmlFile, JFXButton button, String title) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from the button's scene
    Stage stage = (Stage) button.getScene().getWindow();
    stage.setTitle(title);
    stage.setScene(scene);
    stage.show();
  }
}
