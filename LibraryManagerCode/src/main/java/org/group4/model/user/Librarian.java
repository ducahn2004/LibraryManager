package org.group4.model.user;

import java.time.LocalDate;

import org.group4.service.book.BookItemManagerImpl;
import org.group4.service.book.BookManagerImpl;
import org.group4.service.interfaces.BookItemManager;
import org.group4.service.interfaces.BookManager;
import org.group4.service.interfaces.LendingManager;
import org.group4.service.interfaces.MemberManager;
import org.group4.service.interfaces.NotificationManager;
import org.group4.service.notification.NotificationManagerImpl;
import org.group4.service.transaction.LendingManagerImpl;
import org.group4.service.user.MemberManagerImpl;


/**
 * The {@code Librarian} class represents a librarian in the library system. It provides methods to
 * manage books, book items, members, and book lending operations.
 */
public class Librarian extends Person {

  String librarianId;
  private final BookManager bookManager;
  private final BookItemManager bookItemManager;
  private final MemberManager memberManager;
  private final LendingManager bookLendingManager;
  private final NotificationManager notificationManager;

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
    this.bookManager = new BookManagerImpl();
    this.bookItemManager = new BookItemManagerImpl();
    this.memberManager = new MemberManagerImpl();
    this.bookLendingManager = new LendingManagerImpl();
    this.notificationManager = new NotificationManagerImpl();
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
  public BookManager getBookManager() {
    return bookManager;
  }

  /**
   * Retrieves the book item manager.
   *
   * @return the book item manager
   */
  public BookItemManager getBookItemManager() {
    return bookItemManager;
  }

  /**
   * Retrieves the member manager.
   *
   * @return the member manager
   */
  public MemberManager getMemberManager() {
    return memberManager;
  }

  /**
   * Retrieves the book lending manager.
   *
   * @return the book lending manager
   */
  public LendingManager getLendingManager() {
    return bookLendingManager;
  }

  /**
   * Retrieves the notification manager.
   *
   * @return the notification manager
   */
  public NotificationManager getNotificationManager() {
    return notificationManager;
  }
}
