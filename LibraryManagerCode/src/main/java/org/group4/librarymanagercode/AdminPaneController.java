package org.group4.librarymanagercode;

import javafx.application.Platform;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.group4.dao.MemberDAO;
import org.jetbrains.annotations.NotNull;

public class AdminPaneController {

  public Label total_Members;
  private Stage stage;

  @FXML
  private JFXButton homeButton;
  @FXML
  private JFXButton MemberButton;
  @FXML
  private JFXButton bookButton;
  @FXML
  private JFXButton settingButton;
  @FXML
  private JFXButton notificationButton;
  @FXML
  private JFXButton closeButton;

  public void HomeAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AdminPane.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) homeButton.getScene().getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Home button clicked");
  }
  public void MemberAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MemberView.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) MemberButton.getScene().getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Member button clicked");
  }
  public void BookAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BookView.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) bookButton.getScene().getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Book button clicked");
  }
  public void notificationAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Notification.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) notificationButton.getScene().getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Notification button clicked");
  }
  public void SettingAction(ActionEvent actionEvent) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Setting.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Get the stage from any button that was clicked
    Stage stage = (Stage) settingButton.getScene().getWindow();  // Or use any other button, since the stage is the same
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Setting button clicked");
  }

  public void Close(ActionEvent actionEvent) {
    Platform.exit();
  }

  MemberDAO memberDAO = new MemberDAO(); // Tạo DAO để truy xuất dữ liệu

  /**
   * Phương thức để cập nhật số lượng thành viên trên giao diện.
   */
  public void updateTotalMembers() {
    int totalMembers = memberDAO.getTotalMembers(); // Gọi phương thức từ MemberDAO
    total_Members.setText(String.valueOf(totalMembers)); // Cập nhật nội dung Label
  }

  @FXML
  public void initialize() {
    // Tự động cập nhật số lượng thành viên khi khởi tạo giao diện
    updateTotalMembers();
  }

}
