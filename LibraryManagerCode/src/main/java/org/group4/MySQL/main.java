package org.group4.MySQL;

public class main {
  public static void main(String[] args) {

//    BookControllerSQL bookDatabase = new BookControllerSQL(); // Tạo đối tượng BookDatabase
//    bookDatabase.getAllBooks(); // Gọi phương thức để in ra danh sách sách
//    //bookDatabase.deleteBookByISBN("9786041004757"); // Gọi phương thức để xóa sách theo ID 1
//
//    System.out.println("--------------------------------------------------------------------------------------------------------------------");
//
//    MemberManagerSQL memberDatabase = new MemberManagerSQL(); // Tạo đối tượng MemberDatabase
//    memberDatabase.getAllMembers(); // Gọi phương thức để in ra danh sách thành viên

    BookControllerSQL bookDatabase = new BookControllerSQL();
////    bookDatabase.addBookFromInput(); // Gọi phương thức để thêm sách từ bàn phím
//    bookDatabase.updateBookFromInput(); // Gọi phương thức để nhập dữ liệu từ bàn phím và cập nhật sách
//    deleteBookFromInput();
    bookDatabase.searchBookFromInput(); // Gọi phương thức để nhập dữ liệu từ bàn phím và tìm kiếm sách



//    MemberManagerSQL memberDatabase = new MemberManagerSQL();
//    memberDatabase.addMemberFromInput(); // Gọi phương thức để thêm thành viên từ bàn phím
//    memberDatabase.deleteMemberFromInput(); // Gọi phương thức để xóa thành viên từ bàn phím
//    memberDatabase.searchMemberFromInput(); // Gọi phương thức để tìm kiếm thành viên từ bàn phím
//    memberDatabase.updateMemberFromInput(); // Gọi phương thức để nhập dữ liệu từ bàn phím và cập nhật thành viên
    }
  }
