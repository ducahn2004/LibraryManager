package org.group4.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BookControllerSQL {


    // Phương thức để lấy danh sách tất cả các sách từ bảng Book
    public void getAllBooks() {
      Connection connection = DatabaseConector.getConnection(); // Lấy kết nối từ DatabaseConnector
      String query = "SELECT * FROM Book.book"; // Truy vấn SQL để lấy tất cả sách

      try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {

          String ISBN = resultSet.getString("ISBN");
          String Title = resultSet.getString("Title");
          String Subject = resultSet.getString("Subject");
          String Publisher = resultSet.getString("Publisher");
          String NumberofPages = resultSet.getString("NumberofPages");
          String Language = resultSet.getString("Language");

          String author = resultSet.getString("Author");

          // In thông tin sách ra hoặc xử lý theo nhu cầu
          System.out.println("ISBN: " + ISBN + ", Title: " + Title + ", Subject: " + Subject
              + ", Publisher: " + Publisher + ", Language: " + Language
              + ", NumberofPages: " + NumberofPages + ", Author: " + author);
        }

        resultSet.close();
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    // Phương thức để thêm mới một sách vào bảng Book
  public void addBook(String ISBN, String Title, String Subject, String Publisher, String NumberofPages, String Language, String Author) {
      Connection connection = DatabaseConector.getConnection(); // Lấy kết nối từ DatabaseConnector
      String query = "INSERT INTO Book.book (ISBN, Title, Subject, Publisher, NumberofPages, Language, Author) VALUES (?,?,?,?,?,?,?)"; // Truy vấn SQL để thêm mới sách

      try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, ISBN);
        preparedStatement.setString(2, Title);
        preparedStatement.setString(3, Subject);
        preparedStatement.setString(4, Publisher);
        preparedStatement.setString(5, NumberofPages);
        preparedStatement.setString(6, Language);
        preparedStatement.setString(7, Author);

        preparedStatement.executeUpdate(); // Thực thi truy vấn
        preparedStatement.close();

      } catch (SQLException e) {
        e.printStackTrace();
      }
      finally {
        // Closing the connection using your closeConnection method
        DatabaseConector.closeConnection(connection);
      }
  }

  // Phương thức để nhập dữ liệu từ bàn phím và gọi addBook
  public void addBookFromInput() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter ISBN: ");
    String ISBN = scanner.nextLine();

    System.out.print("Enter Title: ");
    String Title = scanner.nextLine();

    System.out.print("Enter Subject: ");
    String Subject = scanner.nextLine();

    System.out.print("Enter Publisher: ");
    String Publisher = scanner.nextLine();

    System.out.print("Enter Number of Pages: ");
    String NumberofPages = scanner.nextLine();

    System.out.print("Enter Language: ");
    String Language = scanner.nextLine();

    System.out.print("Enter Author: ");
    String Author = scanner.nextLine();

    addBook(ISBN, Title, Subject, Publisher, NumberofPages, Language, Author); // Gọi phương thức addBook để thêm vào database
  }

  // Phương thức để cập nhật thông tin của một sách trong bảng Book
  public void updateBook(String ISBN, String Title, String Subject, String Publisher, String NumberofPages, String Language, String Author) {
      Connection connection = DatabaseConector.getConnection(); // Lấy kết nối từ DatabaseConnector
      String query = "UPDATE Book.book SET Title=?, Subject=?, Publisher=?, NumberofPages=?, Language=?, Author=? WHERE ISBN=?"; // Truy vấn SQL để cập nhật thông tin sách

      try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, Title);
        preparedStatement.setString(2, Subject);
        preparedStatement.setString(3, Publisher);
        preparedStatement.setString(4, NumberofPages);
        preparedStatement.setString(5, Language);
        preparedStatement.setString(6, Author);
        preparedStatement.setString(7, ISBN);

        preparedStatement.executeUpdate(); // Thực thi truy vấn
        preparedStatement.close();

      } catch (SQLException e) {
        e.printStackTrace();
      }
      finally {
        // Closing the connection using your closeConnection method
        DatabaseConector.closeConnection(connection);
      }
  }

  // Phương thức nhập dữ liệu từ bàn phím và gọi updateBook để cập nhật sách
  public void updateBookFromInput() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter ISBN of the book to update: ");
    String ISBN = scanner.nextLine(); // Nhập ISBN từ bàn phím

    // Nhập các thông tin khác của sách
    System.out.print("Enter new Title: ");
    String Title = scanner.nextLine();

    System.out.print("Enter new Subject: ");
    String Subject = scanner.nextLine();

    System.out.print("Enter new Publisher: ");
    String Publisher = scanner.nextLine();

    System.out.print("Enter new Number of Pages: ");
    String NumberofPages = scanner.nextLine();

    System.out.print("Enter new Language: ");
    String Language = scanner.nextLine();

    System.out.print("Enter new Author: ");
    String Author = scanner.nextLine();

    // Gọi phương thức updateBook để cập nhật sách
    updateBook(ISBN, Title, Subject, Publisher, NumberofPages, Language, Author);
  }



  //Phương thức để xoa một sách trong bảng Book
  public void deleteBook(String ISBN) {
      Connection connection = DatabaseConector.getConnection(); // Lấy kết nối từ DatabaseConnector
      String query = "DELETE FROM Book.book WHERE ISBN =?"; // Truy vấn SQL để xóa sách

      try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, ISBN);

        preparedStatement.executeUpdate(); // Thực thi truy vấn
        preparedStatement.close();

      } catch (SQLException e) {
        e.printStackTrace();
      }
      finally {
        // Closing the connection using your closeConnection method
        DatabaseConector.closeConnection(connection);
      }
  }

  // Phương thức để nhập ISBN từ bàn phím và gọi deleteBook
  public void deleteBookFromInput() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter ISBN to delete: ");
    String ISBN = scanner.nextLine();

    deleteBook(ISBN); // Gọi phương thức deleteBook để xóa sách khỏi database
  }

  // Phương thức tìm kiếm sách theo ISBN hoặc Title
  public void searchBook(String searchTerm) {
    Connection connection = DatabaseConector.getConnection(); // Lấy kết nối từ DatabaseConnector

    // Câu lệnh SQL để tìm kiếm sách theo ISBN hoặc Title
    String query = "SELECT * FROM Book.book WHERE ISBN = ? OR Title LIKE ?";

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, searchTerm); // Tìm theo ISBN
      preparedStatement.setString(2, "%" + searchTerm + "%"); // Tìm theo Title (LIKE để tìm kiếm theo chuỗi con)

      ResultSet resultSet = preparedStatement.executeQuery(); // Thực thi truy vấn

      // Kiểm tra xem có kết quả trả về không
      if (!resultSet.next()) {
        System.out.println("No book found with the search term: " + searchTerm);
      } else {
        do {
          // In thông tin sách ra màn hình
          String ISBN = resultSet.getString("ISBN");
          String Title = resultSet.getString("Title");
          String Subject = resultSet.getString("Subject");
          String Publisher = resultSet.getString("Publisher");
          String NumberofPages = resultSet.getString("NumberofPages");
          String Language = resultSet.getString("Language");
          String Author = resultSet.getString("Author");

          System.out.println("ISBN: " + ISBN);
          System.out.println("Title: " + Title);
          System.out.println("Subject: " + Subject);
          System.out.println("Publisher: " + Publisher);
          System.out.println("Number of Pages: " + NumberofPages);
          System.out.println("Language: " + Language);
          System.out.println("Author: " + Author);

        } while (resultSet.next());
      }

      resultSet.close();
      preparedStatement.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      // Closing the connection using your closeConnection method
      DatabaseConector.closeConnection(connection);
    }
  }

  // Phương thức nhập dữ liệu từ bàn phím để tìm kiếm sách
  public void searchBookFromInput() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter ISBN or Title to search: ");
    String searchTerm = scanner.nextLine(); // Nhập từ khóa tìm kiếm từ bàn phím

    // Gọi phương thức searchBook để tìm kiếm sách
    searchBook(searchTerm);
  }

  }


