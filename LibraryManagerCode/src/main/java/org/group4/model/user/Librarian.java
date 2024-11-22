package org.group4.model.user;

import java.time.LocalDate;

import org.group4.model.book.Book;
import org.group4.model.book.BookItem;
import org.group4.service.book.BookItemManagerService;
import org.group4.service.interfaces.LendingManager;
import org.group4.service.transaction.LendingManagerService;
import org.group4.service.book.BookManagerService;
import org.group4.service.interfaces.GenericManagerService;
import org.group4.service.user.MemberManagerService;


/**
 * The {@code Librarian} class represents a librarian in the library system. It provides methods to
 * manage books, book items, members, and book lending operations.
 */
public class Librarian extends Person {

  String librarianId;
  private final GenericManagerService<Book> bookManager;
  private final GenericManagerService<BookItem> bookItemManager;
  private final GenericManagerService<Member> memberManager;
  private final LendingManager bookLendingManager;


  /**
   * Constructs a new {@code Librarian} object with the specified librarian ID, name, date of birth,
   * email, and phone number.
   *
   * @param librarianId the librarian ID
   * @param name the name
   * @param dateOfBirth the date of birth
   * @param email the email
   * @param phoneNumber the phone number
   */
  public Librarian(String librarianId, String name, LocalDate dateOfBirth, String email,
      String phoneNumber) {
    super(name, dateOfBirth, email, phoneNumber);
    this.librarianId = librarianId;
    this.bookManager = new BookManagerService();
    this.bookItemManager = new BookItemManagerService();
    this.memberManager = new MemberManagerService();
    this.bookLendingManager = new LendingManagerService();
  }

  /**
   * Retrieves the librarian ID.
   *
   * @return the librarian ID
   */
  public String getLibrarianId() {
    return librarianId;
  }

  /**
   * Retrieves the book manager.
   *
   * @return the book manager
   */
  public GenericManagerService<Book> getBookManager() {
    return bookManager;
  }

  /**
   * Retrieves the book item manager.
   *
   * @return the book item manager
   */
  public GenericManagerService<BookItem> getBookItemManager() {
    return bookItemManager;
  }

  /**
   * Retrieves the member manager.
   *
   * @return the member manager
   */
  public GenericManagerService<Member> getMemberManager() {
    return memberManager;
  }

  /**
   * Retrieves the book lending manager.
   *
   * @return the book lending manager
   */
  public LendingManager getBookLendingManager() {
    return bookLendingManager;
  }
}
