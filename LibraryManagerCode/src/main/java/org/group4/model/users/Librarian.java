package org.group4.model.users;

import java.time.LocalDate;

import org.group4.model.books.Book;
import org.group4.model.books.BookItem;
import org.group4.model.manager.BookItemManager;
import org.group4.model.manager.BookLendingManager;
import org.group4.model.manager.BookLendingManagerImp;
import org.group4.model.manager.BookManager;
import org.group4.model.manager.GenericManager;
import org.group4.model.manager.MemberManager;


/**
 * The {@code Librarian} class represents a librarian in the library system. It provides methods to
 * manage books, book items, members, and book lending operations.
 */
public class Librarian extends Person {

  String librarianId;
  private final GenericManager<Book> bookManager;
  private final GenericManager<BookItem> bookItemManager;
  private final GenericManager<Member> memberManager;
  private final BookLendingManager bookLendingManager;


  public Librarian(String librarianId, String name, LocalDate dateOfBirth, String email,
      String phoneNumber) {
    super(name, dateOfBirth, email, phoneNumber);
    this.librarianId = librarianId;
    this.bookManager = new BookManager();
    this.bookItemManager = new BookItemManager();
    this.memberManager = new MemberManager();
    this.bookLendingManager = new BookLendingManagerImp();
  }

  public String getLibrarianId() {
    return librarianId;
  }

  public GenericManager<Book> getBookManager() {
    return bookManager;
  }

  public GenericManager<BookItem> getBookItemManager() {
    return bookItemManager;
  }

  public GenericManager<Member> getMemberManager() {
    return memberManager;
  }

  public BookLendingManager getBookLendingManager() {
    return bookLendingManager;
  }
}
