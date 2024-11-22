package org.group4.model.users;

import java.time.LocalDate;

import org.group4.model.books.Book;
import org.group4.model.books.BookItem;
import org.group4.service.manager.BookItemManager;
import org.group4.service.manager.BookLendingManager;
import org.group4.service.manager.BookLendingManagerImp;
import org.group4.service.manager.BookManager;
import org.group4.service.manager.GenericManager;
import org.group4.service.manager.MemberManager;


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
    this.bookManager = new BookManager();
    this.bookItemManager = new BookItemManager();
    this.memberManager = new MemberManager();
    this.bookLendingManager = new BookLendingManagerImp();
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
  public GenericManager<Book> getBookManager() {
    return bookManager;
  }

  /**
   * Retrieves the book item manager.
   *
   * @return the book item manager
   */
  public GenericManager<BookItem> getBookItemManager() {
    return bookItemManager;
  }

  /**
   * Retrieves the member manager.
   *
   * @return the member manager
   */
  public GenericManager<Member> getMemberManager() {
    return memberManager;
  }

  /**
   * Retrieves the book lending manager.
   *
   * @return the book lending manager
   */
  public BookLendingManager getBookLendingManager() {
    return bookLendingManager;
  }
}
