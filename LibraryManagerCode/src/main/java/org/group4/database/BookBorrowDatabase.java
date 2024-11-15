package org.group4.database;

import java.time.LocalDate;
import org.group4.base.books.BookItem;
import org.group4.base.books.BookLending;
import org.group4.base.users.Member;

public class BookBorrowDatabase extends Database<BookLending> {

  private static final BookBorrowDatabase instance = new BookBorrowDatabase();

  private BookBorrowDatabase() {
    initializeBookLendings();
  }

  public static BookBorrowDatabase getInstance() {
    return instance;
  }

  private void initializeBookLendings() {
    // Lấy dữ liệu sách và thành viên từ các cơ sở dữ liệu
    BookItemDatabase bookItemDatabase = BookItemDatabase.getInstance();
    MemberDatabase memberDatabase = MemberDatabase.getInstance();

    // Lấy danh sách các sách và thành viên
    BookItem book1 = bookItemDatabase.getItems().get(0); // Sách đầu tiên
    BookItem book2 = bookItemDatabase.getItems().get(1); // Sách thứ hai
    Member member1 = memberDatabase.getItems().get(0);   // Thành viên đầu tiên
    Member member2 = memberDatabase.getItems().get(1);   // Thành viên thứ hai

    // Tạo các bản ghi BookLending với dữ liệu cụ thể
    addItem(new BookLending(book1, member1));
    addItem(new BookLending(book2, member2));

    // Thêm nhiều bản ghi hơn nếu cần thiết
    BookItem book3 = bookItemDatabase.getItems().get(2); // Sách thứ ba
    Member member3 = memberDatabase.getItems().get(2);   // Thành viên thứ ba
    addItem(new BookLending(book3, member3));
  }
}