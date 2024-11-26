package org.group4.controller;

import java.sql.SQLException;
import javafx.application.Platform;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.group4.model.user.Librarian;
import org.group4.service.user.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the Admin Pane.
 * <p> This class handles the interactions and updates for the Admin Pane, including displaying
 * statistics, handling navigation, and updating charts. </p>
 */
public class AdminPaneController {

  @FXML
  public Label total_bookItem;
  @FXML
  public Label total_books_borrowed;
  @FXML
  private Label total_Members;
  @FXML
  private Label total_books;

  @FXML
  private BarChart<String, Number> home_chart;

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

  private static final Logger logger = LoggerFactory.getLogger(AdminPaneController.class);
  private static final Librarian librarian = SessionManager.getInstance().getCurrentLibrarian();

  /**
   * Updates the total number of members displayed in the UI.
   */
  public void updateTotalMembers() {
    try {
      int totalMembers = librarian.getMemberManager().getAll().size();
      total_Members.setText(String.valueOf(totalMembers));
    } catch (SQLException e) {
      logger.error("Error while updating total members");
    }
  }

  /**
   * Updates the total number of books displayed in the UI.
   */
  public void updateTotalBooks() {
    try {
      int totalBooks = librarian.getBookManager().getAll().size();
      total_books.setText(String.valueOf(totalBooks));
    } catch (SQLException e) {
      logger.error("Error while updating total books");
    }
  }

  /**
   * Updates the total number of books borrowed displayed in the UI.
   */
  public void updateBooksBorrowed() {
    try {
      int totalBooksBorrowed = librarian.getLendingManager().getAll().size();
      total_books_borrowed.setText(String.valueOf(totalBooksBorrowed));
    } catch (SQLException e) {
      logger.error("Error while updating books borrowed");
    }
  }

  /**
   * Updates the total number of book items displayed in the UI.
   */
  public void updateBookItems() {
    try {
      int totalBookItems = librarian.getBookItemManager().getAll().size();
      total_bookItem.setText(String.valueOf(totalBookItems));
    } catch (SQLException e) {
      logger.error("Error while updating book items");
    }
  }

  /**
   * Updates the bar chart with total members and total books data.
   */
  public void Home_chart() {
    try {
      // Clear existing data from the chart
      home_chart.getData().clear();

      // Retrieve data from managers
      int totalBooks = librarian.getBookManager().getAll().size();
      int totalBookItems = librarian.getBookItemManager().getAll().size();
      int totalMembers = librarian.getMemberManager().getAll().size();
      int totalBooksBorrowed = librarian.getLendingManager().getAll().size();

      // Create and populate the "Books" series
      XYChart.Series<String, Number> bookSeries = new XYChart.Series<>();
      bookSeries.setName("Books");
      bookSeries.getData().add(new XYChart.Data<>("Total Books", totalBooks));

      // Create and populate the "Total BookItems" series
      XYChart.Series<String, Number> bookItemSeries = new XYChart.Series<>();
      bookItemSeries.setName("Book Items");
      bookItemSeries.getData().add(new XYChart.Data<>("Book Items", totalBookItems));

      // Create and populate the "Books Borrowed" series
      XYChart.Series<String, Number> booksBorrowedSeries = new XYChart.Series<>();
      booksBorrowedSeries.setName("Books Borrowed");
      booksBorrowedSeries.getData().add(new XYChart.Data<>("Books Borrowed", totalBooksBorrowed));

      // Create and populate the "Members" series
      XYChart.Series<String, Number> memberSeries = new XYChart.Series<>();
      memberSeries.setName("Members");
      memberSeries.getData().add(new XYChart.Data<>("Total Members", totalMembers));

      // Add all series to the bar chart individually
      home_chart.getData().add(memberSeries);
      home_chart.getData().add(bookItemSeries);
      home_chart.getData().add(bookSeries);
      home_chart.getData().add(booksBorrowedSeries);
    } catch (SQLException e) {
      logger.error("Error while updating home chart", e);
    }
  }

  /**
   * Initializes the Admin Pane by updating statistics and the chart.
   */
  @FXML
  public void initialize() {
    updateTotalMembers();
    updateTotalBooks();
    updateBooksBorrowed();
    updateBookItems();
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
