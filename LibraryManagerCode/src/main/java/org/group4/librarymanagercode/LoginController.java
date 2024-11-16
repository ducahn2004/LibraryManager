package org.group4.librarymanagercode;

import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.group4.base.users.Librarian;
import org.group4.database.LibrarianDatabase;

public class LoginController {

  public TextField textShowpassword;
  public ImageView iconOpen_eye;
  public ImageView iconClose_eye;
  // Các thành phần giao diện người dùng (UI) được ánh xạ từ FXML
  @FXML
  private TextField usernameField; // Trường nhập tên đăng nhập

  @FXML
  private PasswordField passwordField; // Trường nhập mật khẩu (ẩn)

  @FXML
  private Button loginButton; // Nút đăng nhập

  // Lấy thông tin của người quản trị (librarian) từ cơ sở dữ liệu
  private Librarian librarian = LibrarianDatabase.getInstance().getItems().getFirst();
  private static final Logger logger = Logger.getLogger(LoginController.class.getName());

  String password;
  // Phương thức này được gọi khi view đã được khởi tạo
  @FXML
  private void initialize() {
    textShowpassword.setVisible(false);
    iconClose_eye.setVisible(true); // Mắt nhắm hiển thị
    iconOpen_eye.setVisible(false); // Mắt mở bị ẩn


    // Thiết lập sự kiện khi nhấn phím Enter trong trường mật khẩu
    passwordField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        handleLoginButton();  // Gọi phương thức đăng nhập khi nhấn Enter
      }
    });
  }


  public void HidePasswordonAction(KeyEvent keyevent) {
    password = passwordField.getText();
    textShowpassword.setText(password);

  }

  public void ShowPasswordonAction(KeyEvent keyEvent) {
    password = textShowpassword.getText();
    passwordField.setText(password);
  }

  public void Close_clicked_OnAction(MouseEvent mouseEvent) {
    textShowpassword.setVisible(true);
    iconOpen_eye.setVisible(true);
    iconClose_eye.setVisible(false);
    passwordField.setVisible(false);

  }

  public void Open_clicked_OnAction(MouseEvent mouseEvent) {
    textShowpassword.setVisible(false);
    iconOpen_eye.setVisible(false);
    iconClose_eye.setVisible(true);
    passwordField.setVisible(true);
  }

  // Phương thức xử lý sự kiện khi người dùng nhấn nút Đăng nhập
  @FXML
  private void handleLoginButton() {
    String username = usernameField.getText().trim(); // Lấy tên đăng nhập
    String password = passwordField.getText(); // Lấy mật khẩu
    logger.info("Login attempt with username: " + username); // Ghi lại thông tin đăng nhập

    // Kiểm tra xem người dùng đã nhập đủ thông tin chưa
    if (username.isEmpty() || password.isEmpty()) {
      showAlert("Login Failed", "Please enter both username and password!"); // Hiển thị cảnh báo nếu thiếu thông tin
      return;
    }

    // Kiểm tra thông tin đăng nhập với dữ liệu người quản trị
    if (librarian.login(username, password)) {
      try {
        // Nếu đăng nhập thành công, chuyển đến màn hình AdminPane
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPane.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) loginButton.getScene().getWindow(); // Lấy cửa sổ hiện tại
        Scene scene = new Scene(root, 700, 550); // Tạo một cảnh mới cho AdminPane
        stage.setScene(scene); // Đặt cảnh mới
        stage.setTitle("Library Manager"); // Đặt tiêu đề cho cửa sổ
        stage.show(); // Hiển thị cửa sổ
      } catch (IOException e) {
        logger.severe("Failed to load admin panel: " + e.getMessage()); // Ghi lại lỗi nếu không tải được màn hình admin
        showAlert("Error", "Unable to load the admin panel."); // Hiển thị cảnh báo lỗi
      }
    } else {
      showAlert("Login Failed", "Incorrect username or password!"); // Thông báo lỗi khi đăng nhập không thành công
    }
  }

  // Phương thức này để hiển thị thông báo lỗi cho người dùng
  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR); // Tạo thông báo lỗi
    alert.setTitle(title); // Tiêu đề của thông báo
    alert.setHeaderText(null); // Không có tiêu đề phụ
    alert.setContentText(message); // Nội dung thông báo
    alert.showAndWait(); // Hiển thị thông báo và đợi phản hồi từ người dùng
  }
}