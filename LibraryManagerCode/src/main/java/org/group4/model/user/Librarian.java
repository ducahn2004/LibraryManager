package org.group4.model.user;

import java.time.LocalDate;

import org.group4.service.book.BookItemManagerServiceImpl;
import org.group4.service.book.BookManagerServiceImpl;
import org.group4.service.interfaces.BookItemManagerService;
import org.group4.service.interfaces.BookManagerService;
import org.group4.service.interfaces.LendingManagerService;
import org.group4.service.interfaces.MemberManagerService;
import org.group4.service.transaction.LendingManagerServiceImpl;
import org.group4.service.user.MemberManagerServiceImpl;


/**
 * The {@code Librarian} class represents a librarian in the library system. It provides methods to
 * manage books, book items, members, and book lending operations.
 */
public class Librarian extends Person {

  String librarianId;
  private final BookManagerService bookManager;
  private final BookItemManagerService bookItemManager;
  private final MemberManagerService memberManager;
  private final LendingManagerService bookLendingManager;


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
    this.bookManager = new BookManagerServiceImpl();
    this.bookItemManager = new BookItemManagerServiceImpl();
    this.memberManager = new MemberManagerServiceImpl();
    this.bookLendingManager = new LendingManagerServiceImpl();
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
  public BookManagerService getBookManager() {
    return bookManager;
  }

  /**
   * Retrieves the book item manager.
   *
   * @return the book item manager
   */
  public BookItemManagerService getBookItemManager() {
    return bookItemManager;
  }

  /**
   * Retrieves the member manager.
   *
   * @return the member manager
   */
  public MemberManagerService getMemberManager() {
    return memberManager;
  }

  /**
   * Retrieves the book lending manager.
   *
   * @return the book lending manager
   */
  public LendingManagerService getLendingManager() {
    return bookLendingManager;
  }
}
