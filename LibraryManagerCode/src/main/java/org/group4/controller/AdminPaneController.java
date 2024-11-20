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
import org.group4.dao.BookDAO;
import org.group4.dao.MemberDAO;

public class AdminPaneController {

  public Label total_Members;
  public Label total_books;


  @FXML
  private BarChart<String, Number> home_chart;
  @FXML
  public CategoryAxis xAxis;
  @FXML
  public NumberAxis yAxis;

  private Stage stage;

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
  MemberDAO memberDAO = new MemberDAO();
  BookDAO bookDAO = new BookDAO();


  public void updateTotalMembers() {
    int totalMembers = memberDAO.getTotalMembers();
    total_Members.setText(String.valueOf(totalMembers));
  }


  public void updateTotalBooks() {
    int totalBooks = bookDAO.getTotalBooks();
    total_books.setText(String.valueOf(totalBooks));
  }


  public void Home_chart() {
    home_chart.getData().clear();

    int totalBooks = bookDAO.getTotalBooks();
    int totalMembers = memberDAO.getTotalMembers();

    // add series
    XYChart.Series<String, Number> bookSeries = new XYChart.Series<>();
    bookSeries.setName("Books");
    bookSeries.getData().add(new XYChart.Data<>("Total Books", totalBooks));

    // add series
    XYChart.Series<String, Number> memberSeries = new XYChart.Series<>();
    memberSeries.setName("Members");
    memberSeries.getData().add(new XYChart.Data<>("Total Members", totalMembers));

    //add data for home_chart
    home_chart.getData().addAll(bookSeries, memberSeries);
  }

  @FXML
  public void initialize() {
    // updateTotalMembers();
    updateTotalMembers();
    // updateTotalBooks();
    updateTotalBooks();
    Home_chart();
  }

  public void HomeAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AdminPane.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) homeButton.getScene()
        .getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Home button clicked");
  }

  public void MemberAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MemberView.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) MemberButton.getScene()
        .getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Member button clicked");
  }

  public void BookAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BookView.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) bookButton.getScene()
        .getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Book button clicked");
  }

  public void BookLendingAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BookLending.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) bookButton.getScene()
        .getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Book button clicked");
  }

  public void notificationAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Notification.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) notificationButton.getScene()
        .getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Notification button clicked");
  }

  public void SettingAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Setting.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) settingButton.getScene()
        .getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Setting button clicked");
  }

  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }


}
