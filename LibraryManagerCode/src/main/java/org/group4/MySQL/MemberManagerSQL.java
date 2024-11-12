package org.group4.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MemberManagerSQL {
  //Phuon thuc lay du lieu member từ bảng Member
  public void getAllMembers() {
    Connection connection = DatabaseConector.getConnection(); // Lấy kết nối từ DatabaseConnector
    String query = "SELECT * FROM Book.membermanager";

    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(query);

      while (resultSet.next()) {

        String MemberId = resultSet.getString("MemberId");
        String Name = resultSet.getString("Name");
        String Dateofbirth = resultSet.getString("Date Of Birth");
        String PhoneNumber = resultSet.getString("PhoneNumber");
        String EmailAddress = resultSet.getString("Email Address");

        // In thông tin member ra hoặc xử lý theo nhu cầu
        System.out.println("MemberId: " + MemberId + ", Name: " + Name + ", Date Of Birth: " + Dateofbirth
            + ", Phone Number: " + PhoneNumber + ", Email Address: " + EmailAddress);
      }

      resultSet.close();
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //Phuong thuc add member vao bang Member
  public void addMember(String MemberId, String Name, String Dateofbirth, String PhoneNumber, String EmailAddress) {
    Connection connection = DatabaseConector.getConnection(); // Lấy kết nối từ DatabaseConnector
    String query = "INSERT INTO Book.membermanager (MemberId, Name, `Date Of Birth`, PhoneNumber, `Email Address`) VALUES (?,?,?,?,?)";

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, MemberId);
      preparedStatement.setString(2, Name);
      preparedStatement.setString(3, Dateofbirth);
      preparedStatement.setString(4, PhoneNumber);
      preparedStatement.setString(5, EmailAddress);

      preparedStatement.executeUpdate();

      System.out.println("Member added successfully!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Phương thức để nhập dữ liệu từ bàn phím và gọi addMember
  public void addMemberFromInput() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter Member ID: ");
    String MemberId = scanner.nextLine();

    System.out.print("Enter Name: ");
    String Name = scanner.nextLine();

    System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
    String DateOfBirth = scanner.nextLine();

    System.out.print("Enter Phone Number: ");
    String PhoneNumber = scanner.nextLine();

    System.out.print("Enter Email Address: ");
    String EmailAddress = scanner.nextLine();

    addMember(MemberId, Name, DateOfBirth, PhoneNumber, EmailAddress); // Gọi phương thức addMember để thêm vào database
  }

  // Phương thức tìm kiếm member theo MemberId
  public void searchMemberById(String MemberId) {
    Connection connection = DatabaseConector.getConnection(); // Lấy kết nối từ DatabaseConnector
    String query = "SELECT * FROM Book.membermanager WHERE MemberId =?";

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, MemberId); // Gán giá trị cho MemberId trong câu truy vấn

      ResultSet resultSet = preparedStatement.executeQuery(); // Thực thi truy vấn và lấy kết quả

      if (resultSet.next()) { // Kiểm tra nếu có kết quả
        // Hiển thị thông tin thành viên tìm thấy
        String name = resultSet.getString("Name");
        String dateOfBirth = resultSet.getString("Date Of Birth");
        String phoneNumber = resultSet.getString("PhoneNumber");
        String emailAddress = resultSet.getString("Email Address");

        System.out.println("Member found:");
        System.out.println("MemberId: " + MemberId);
        System.out.println("Name: " + name);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email Address: " + emailAddress);
      } else {
        System.out.println("No member found with the provided MemberId.");
      }

      resultSet.close();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Phương thức nhập MemberId từ bàn phím và gọi searchMemberById
  public void searchMemberFromInput() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter MemberId to search: ");
    String MemberId = scanner.nextLine(); // Nhập MemberId từ bàn phím

    searchMemberById(MemberId); // Gọi phương thức tìm kiếm thành viên
  }


  //phuong thuc xoa member
  public void deleteMember(String MemberId) {
    Connection connection = DatabaseConector.getConnection(); // Lấy kết nối từ DatabaseConnector
    String query = "DELETE FROM Book.membermanager WHERE MemberId =?";

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, MemberId);

      preparedStatement.executeUpdate();

      System.out.println("Member deleted successfully!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Phương thức nhập MemberId từ bàn phím và gọi deleteMember
  public void deleteMemberFromInput() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter MemberId to delete: ");
    String MemberId = scanner.nextLine(); // Nhập MemberId từ bàn phím

    deleteMember(MemberId); // Gọi phương thức deleteMember để xóa thành viên
  }

  // Phương thức cập nhật thông tin của một thành viên theo MemberId
  public void updateMember(String MemberId, String Name, String DateOfBirth, String PhoneNumber, String EmailAddress) {
    Connection connection = DatabaseConector.getConnection(); // Lấy kết nối từ DatabaseConnector
    String query = "UPDATE Book.MemberManager SET Name=?, `Date Of Birth`=?, PhoneNumber=?, `Email Address`=? WHERE MemberId=?"; // Truy vấn SQL để cập nhật thông tin thành viên

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, Name);
      preparedStatement.setString(2, DateOfBirth);
      preparedStatement.setString(3, PhoneNumber);
      preparedStatement.setString(4, EmailAddress);
      preparedStatement.setString(5, MemberId); // Cập nhật theo MemberId

      preparedStatement.executeUpdate(); // Thực thi truy vấn
      preparedStatement.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Phương thức nhập dữ liệu từ bàn phím và gọi updateMember để cập nhật thành viên
  public void updateMemberFromInput() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter MemberId to update: ");
    String MemberId = scanner.nextLine(); // Nhập MemberId từ bàn phím

    // Nhập các thông tin khác của thành viên
    System.out.print("Enter new Name: ");
    String Name = scanner.nextLine();

    System.out.print("Enter new Date of Birth (YYYY-MM-DD): ");
    String DateOfBirth = scanner.nextLine();

    System.out.print("Enter new Phone Number: ");
    String PhoneNumber = scanner.nextLine();

    System.out.print("Enter new Email Address: ");
    String EmailAddress = scanner.nextLine();

    // Gọi phương thức updateMember để cập nhật thành viên
    updateMember(MemberId, Name, DateOfBirth, PhoneNumber, EmailAddress);
  }
}
