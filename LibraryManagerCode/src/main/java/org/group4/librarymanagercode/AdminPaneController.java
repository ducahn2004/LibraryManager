package org.group4.librarymanagercode;

import javafx.application.Platform;
import com.jfoenix.controls.JFXButton; // Thư viện JFoenix cho các thành phần giao diện đẹp hơn
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class AdminPaneController {
  private Stage stage; // Biến để lưu trữ Stage (cửa sổ chính của ứng dụng)

  // Các nút giao diện được ánh xạ bằng @FXML từ file FXML
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

  /**
   * Xử lý sự kiện khi nhấn nút "Home"
   */
  public void HomeAction(ActionEvent actionEvent) throws IOException {
    // Tải file FXML cho giao diện chính
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AdminPane.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Lấy stage từ nút đang hoạt động và cập nhật scene mới
    Stage stage = (Stage) homeButton.getScene().getWindow();
    stage.setTitle("Library Manager"); // Đặt tiêu đề cửa sổ
    stage.setScene(scene);
    stage.show();
    System.out.println("Home button clicked"); // Debug: In ra console khi nhấn nút
  }

  /**
   * Xử lý sự kiện khi nhấn nút "Member"
   */
  public void MemberAction(ActionEvent actionEvent) throws IOException {
    // Tải giao diện MemberView.fxml
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MemberView.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Lấy stage từ nút và chuyển scene
    Stage stage = (Stage) MemberButton.getScene().getWindow();
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Member button clicked"); // Debug
  }

  /**
   * Xử lý sự kiện khi nhấn nút "Book"
   */
  public void BookAction(ActionEvent actionEvent) throws IOException {
    // Tải giao diện BookView.fxml
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BookView.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Chuyển đổi scene
    Stage stage = (Stage) bookButton.getScene().getWindow();
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Book button clicked"); // Debug
  }

  /**
   * Xử lý sự kiện khi nhấn nút "Notification"
   */
  public void notificationAction(ActionEvent actionEvent) throws IOException {
    // Tải giao diện Notification.fxml
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Notification.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    Stage stage = (Stage) notificationButton.getScene().getWindow();
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Notification button clicked"); // Debug
  }

  /**
   * Xử lý sự kiện khi nhấn nút "Setting"
   */
  public void SettingAction(ActionEvent actionEvent) throws IOException {
    // Tải giao diện Setting.fxml
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Setting.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    Stage stage = (Stage) settingButton.getScene().getWindow();
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Setting button clicked"); // Debug
  }

  /**
   * Đóng ứng dụng khi nhấn nút "Close"
   */
  public void Close(ActionEvent actionEvent) {
    Platform.exit(); // Thoát ứng dụng
  }
}
