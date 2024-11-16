package org.group4.librarymanagercode;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.group4.base.books.Book;
import org.group4.base.users.Librarian;
import org.group4.base.users.Member;
import org.group4.database.BookDatabase;
import org.group4.database.LibrarianDatabase;

public class BookViewController {


  private Stage stage;

  private final ObservableList<Book> bookList = FXCollections.observableArrayList();

  private final Librarian librarian = LibrarianDatabase.getInstance().getItems().getFirst();
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

  @FXML
  private TableView<Book> tableView;

  @FXML
  private TableColumn<Book, String> ISBN;

  @FXML
  private TableColumn<Book, String> bookName;

  @FXML
  private TableColumn<Book, String> bookSubject;

  @FXML
  private TableColumn<Book, String> bookPublisher;

  @FXML
  private TableColumn<Book, String> bookLanguage;

  @FXML
  private TableColumn<Book, Integer> numberOfPages;

  @FXML
  private TableColumn<Book, String> bookAuthor;
  @FXML
  private TableColumn<Book, Void> actionColumn;
  @FXML
  private TextField searchField;

  @FXML
  public void initialize() {
    // Gán giá trị cho các cột từ thuộc tính của đối tượng Book
    ISBN.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getISBN()));
    bookName.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
    bookSubject.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getSubject()));
    bookPublisher.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getPublisher()));
    bookLanguage.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getLanguage()));
    numberOfPages.setCellValueFactory(
        cellData -> new SimpleObjectProperty<>(cellData.getValue().getNumberOfPages()));
    bookAuthor.setCellValueFactory(cellData -> new SimpleStringProperty(
        cellData.getValue().getAuthors().iterator().next().getName()));

    // Tạo cột hành động (chứa liên kết Edit/Delete)
    actionColumn.setCellFactory(param -> new TableCell<>() {
      private final Hyperlink editLink = new Hyperlink("Edit");
      private final Hyperlink deleteLink = new Hyperlink("Delete");

      {
        // Xử lý sự kiện khi nhấn vào liên kết "Edit"
        editLink.setOnAction((ActionEvent event) -> {
          Book item = getTableView().getItems().get(getIndex());
          showEditForm(item); // Hiển thị form chỉnh sửa
        });

        // Xử lý sự kiện khi nhấn vào liên kết "Delete"
        deleteLink.setOnAction((ActionEvent event) -> {
          Book item = getTableView().getItems().get(getIndex());
          showDeleteConfirmation(item); // Hiển thị hộp thoại xác nhận xóa
        });
      }

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setGraphic(null); // Không hiển thị gì nếu ô trống
        } else {
          HBox hBox = new HBox(10, editLink, deleteLink); // Hiển thị hai liên kết trong một dòng
          setGraphic(hBox);
        }
      }
    });

    // Tải dữ liệu sách từ cơ sở dữ liệu
    loadBookData();

    // Thêm sự kiện khi nhấn đúp chuột vào một hàng trong bảng
    tableView.setRowFactory(tv -> {
      TableRow<Book> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && !row.isEmpty()) {
          showDetailPage(row.getItem()); // Hiển thị trang chi tiết sách
        }
      });
      return row;
    });

    // Thêm sự kiện khi nhập liệu trong ô tìm kiếm
    searchField.textProperty()
        .addListener((observable, oldValue, newValue) -> filterBookList(newValue));
    searchField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        filterBookList(searchField.getText()); // Lọc danh sách khi nhấn Enter
      }
    });
  }

  // Hiển thị trang chi tiết của một cuốn sách
  private void showDetailPage(Book book) {
    try {
      // Tải giao diện chi tiết sách từ file FXML
      FXMLLoader loader = new FXMLLoader(getClass().getResource("BookDetails.fxml"));
      Stage detailStage = new Stage(); // Tạo cửa sổ mới
      detailStage.setScene(new Scene(loader.load()));

      // Lấy controller của giao diện và truyền thông tin sách cần hiển thị
      BookDetailsController controller = loader.getController();
      controller.setItemDetail(book);

      detailStage.setTitle("Book Item Detail"); // Đặt tiêu đề cho cửa sổ
      detailStage.show(); // Hiển thị cửa sổ
    } catch (Exception e) {
      // Ghi log và hiển thị lỗi nếu không tải được giao diện
      Logger.getLogger(BookViewController.class.getName())
          .log(Level.SEVERE, "Failed to load book details page", e);
    }
  }

  // Hiển thị hộp thoại xác nhận xóa sách
  private void showDeleteConfirmation(Book book) {
    Alert alert = createAlert(AlertType.CONFIRMATION, "Delete Confirmation",
        "Are you sure you want to delete this book?", // Tiêu đề và nội dung cảnh báo
        "ID: " + book.getISBN() + "\nName: " + book.getTitle() + "\nAuthor: "
            + book.getAuthors().iterator().next().getName() + "\nPublisher: " + book.getPublisher()
            + "\nLanguage: " + book);

    // Xử lý khi người dùng nhấn nút OK
    alert.showAndWait().ifPresent(response -> {
      if (response == ButtonType.OK) {
        bookList.remove(book); // Xóa sách khỏi danh sách hiển thị
        librarian.removeBook(book); // Xóa sách khỏi dữ liệu của thủ thư
      }
    });
  }

  // Tạo một hộp thoại thông báo
  private Alert createAlert(AlertType type, String title, String header, String content) {
    Alert alert = new Alert(type); // Tạo alert với loại (CONFIRMATION, ERROR,...)
    alert.setTitle(title); // Đặt tiêu đề
    alert.setHeaderText(header); // Đặt tiêu đề phụ (có thể để null)
    alert.setContentText(content); // Đặt nội dung
    return alert;
  }

  // Hiển thị form chỉnh sửa thông tin sách
  private void showEditForm(Book book) {
    try {
      // Tải giao diện chỉnh sửa sách từ file FXML
      FXMLLoader loader = new FXMLLoader(getClass().getResource("EditBook.fxml"));
      Stage editStage = new Stage(); // Tạo cửa sổ mới
      editStage.setScene(new Scene(loader.load()));

      // Lấy controller và truyền thông tin sách cần chỉnh sửa
      EditBookController controller = loader.getController();
      controller.setBookData(book);
      controller.setParentController(this); // Truyền controller cha để cập nhật dữ liệu

      editStage.setTitle("Edit Book"); // Đặt tiêu đề cửa sổ
      editStage.showAndWait(); // Hiển thị cửa sổ và chờ người dùng đóng
    } catch (IOException e) {
      // Ghi log và hiển thị lỗi nếu không tải được giao diện
      logAndShowError("Failed to load edit form page", e);
    }
  }

  // Ghi log và hiển thị lỗi
  private void logAndShowError(String message, Exception e) {
    Logger.getLogger(MemberViewController.class.getName()).log(Level.SEVERE, message, e);
    showAlert(AlertType.ERROR, "Error", message); // Hiển thị thông báo lỗi
  }

  // Hiển thị thông báo chung
  void showAlert(AlertType type, String title, String content) {
    Alert alert = new Alert(type); // Tạo alert
    alert.setTitle(title); // Đặt tiêu đề
    alert.setHeaderText(null); // Không có tiêu đề phụ
    alert.setContentText(content); // Đặt nội dung
    alert.showAndWait(); // Hiển thị alert
  }

  // Tải dữ liệu sách từ cơ sở dữ liệu vào bảng
  private void loadBookData() {
    if (bookList.isEmpty()) { // Kiểm tra nếu danh sách rỗng
      bookList.addAll(BookDatabase.getInstance().getItems()); // Lấy dữ liệu từ cơ sở dữ liệu
      tableView.setItems(bookList); // Gán dữ liệu cho bảng
    }
  }

  // Làm mới bảng hiển thị
  public void refreshTable() {
    tableView.refresh(); // Làm mới giao diện của bảng
  }

  // Lọc danh sách sách theo từ khóa tìm kiếm
  private void filterBookList(String searchText) {
    if (searchText == null || searchText.isEmpty()) {
      tableView.setItems(bookList); // Nếu không có từ khóa, hiển thị toàn bộ danh sách
    } else {
      String lowerCaseFilter = searchText.toLowerCase(); // Chuyển từ khóa sang chữ thường
      ObservableList<Book> filteredList = bookList.filtered(book ->
          book.getISBN().toLowerCase().contains(lowerCaseFilter) || // Tìm kiếm theo ISBN
              book.getTitle().toLowerCase().contains(lowerCaseFilter)   // Tìm kiếm theo tên sách
      );
      tableView.setItems(filteredList); // Cập nhật danh sách hiển thị
    }
  }


  // Xử lý sự kiện tìm kiếm sách
  public void onSearchBook(ActionEvent actionEvent) {
    // Lọc danh sách sách dựa trên nội dung người dùng nhập trong ô tìm kiếm
    filterBookList(searchField.getText());
  }

  // Xử lý sự kiện thêm sách mới
  @FXML
  public void addBookAction(ActionEvent actionEvent) throws IOException {
    // Tải giao diện thêm sách từ file FXML
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddBook.fxml"));
    Parent root = fxmlLoader.load();

    // Lấy Stage hiện tại từ sự kiện (nút được nhấn)
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

    // Thay đổi nội dung của Scene hiện tại mà không mở cửa sổ mới
    stage.getScene().setRoot(root);
    stage.setTitle("Library Manager"); // Cập nhật tiêu đề
  }

  // Xử lý sự kiện khi nhấn nút "Home"
  public void HomeAction(ActionEvent actionEvent) throws IOException {
    // Tải giao diện trang chủ từ file FXML
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AdminPane.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Lấy Stage hiện tại và thay đổi Scene
    Stage stage = (Stage) homeButton.getScene().getWindow();
    stage.setTitle("Library Manager"); // Đặt tiêu đề cửa sổ
    stage.setScene(scene);
    stage.show();
    System.out.println("Home button clicked"); // In log xác nhận
  }

  // Xử lý sự kiện khi nhấn nút "Member"
  public void MemberAction(ActionEvent actionEvent) throws IOException {
    // Tải giao diện quản lý thành viên từ file FXML
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MemberView.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Lấy Stage hiện tại và thay đổi Scene
    Stage stage = (Stage) MemberButton.getScene().getWindow();
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Member button clicked"); // In log xác nhận
  }

  // Xử lý sự kiện khi nhấn nút "Book"
  public void BookAction(ActionEvent actionEvent) throws IOException {
    // Tải giao diện quản lý sách từ file FXML
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BookView.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Lấy Stage hiện tại và thay đổi Scene
    Stage stage = (Stage) bookButton.getScene().getWindow();
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Book button clicked"); // In log xác nhận
  }

  // Xử lý sự kiện khi nhấn nút "Notification"
  public void notificationAction(ActionEvent actionEvent) throws IOException {
    // Tải giao diện thông báo từ file FXML
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Notification.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Lấy Stage hiện tại và thay đổi Scene
    Stage stage = (Stage) notificationButton.getScene().getWindow();
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Notification button clicked"); // In log xác nhận
  }

  // Xử lý sự kiện khi nhấn nút "Setting"
  public void SettingAction(ActionEvent actionEvent) throws IOException {
    // Tải giao diện cài đặt từ file FXML
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Setting.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

    // Lấy Stage hiện tại và thay đổi Scene
    Stage stage = (Stage) settingButton.getScene().getWindow();
    stage.setTitle("Library Manager");
    stage.setScene(scene);
    stage.show();
    System.out.println("Setting button clicked"); // In log xác nhận
  }

  // Xử lý sự kiện đóng ứng dụng
  public void Close(ActionEvent actionEvent) {
    Platform.exit(); // Thoát ứng dụng
  }
}

