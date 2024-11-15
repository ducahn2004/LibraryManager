package org.group4.module.users;

import java.time.LocalDate;
import org.group4.module.books.Book;
import org.group4.module.books.BookItem;
import org.group4.module.transactions.BookLending;
import org.group4.dao.BookDAO;
import org.group4.dao.BookItemDAO;
import org.group4.dao.BookLendingDAO;
import org.group4.dao.MemberDAO;
import org.group4.dao.FactoryDAO;

/**
 * The {@code Librarian} class represents a librarian with the ability to manage books,
 * members, and lending processes within the library system.
 */
public class Librarian extends Person {

  /** The account credentials for the librarian. */
  private final Account account;

  /** The data access object for books. */
  private final BookDAO bookDAO = FactoryDAO.getBookDAO();

  /** The data access object for book items. */
  private final BookItemDAO bookItemDAO = FactoryDAO.getBookItemDAO();

  /** The data access object for members. */
  private final MemberDAO memberDAO = FactoryDAO.getMemberDAO();

  /** The data access object for book lendings. */
  private final BookLendingDAO bookLendingDAO = FactoryDAO.getBookLendingDAO();

  /**
   * Constructs a {@code Librarian} object with the specified details and account credentials.
   *
   * @param name the name of the librarian
   * @param dateOfBirth the date of birth of the librarian
   * @param email the email address of the librarian
   * @param phoneNumber the phone number of the librarian
   * @param id the unique identifier for the librarian's account
   * @param password the password for the librarian's account
   */
  public Librarian(String name, LocalDate dateOfBirth, String email, String phoneNumber,
      String id, String password) {
    super(name, dateOfBirth, email, phoneNumber);
    this.account = new Account(id, password);
  }

  /**
   * Returns the librarian's ID.
   *
   * @return the librarian's account ID
   */
  public String getLibrarianId() {
    return account.getId();
  }

  /**
   * Attempts to log in with the given credentials.
   *
   * @param id the librarian's ID
   * @param password the librarian's password
   * @return {@code true} if login is successful, {@code false} otherwise
   */
  public boolean login(String id, String password) {
    return account.login(id, password);
  }

  /**
   * Changes the librarian's password if the old password matches.
   *
   * @param oldPassword the old password
   * @param newPassword the new password
   * @return {@code true} if password is successfully changed, {@code false} otherwise
   */
  public boolean changePassword(String oldPassword, String newPassword) {
    return account.changePassword(oldPassword, newPassword);
  }

  /**
   * Logs the librarian out of the system.
   *
   * @return {@code true} if logout is successful, {@code false} otherwise
   */
  public boolean logout() {
    return account.logout();
  }

  // Book Management Methods

  /**
   * Adds a new book to the library system.
   *
   * @param book the {@code Book} object to add
   * @return {@code true} if the book was added successfully, {@code false} otherwise
   */
  public boolean addBook(Book book) {
    return bookDAO.add(book);
  }

  /**
   * Deletes a book from the library system.
   *
   * @param book the {@code Book} object to delete
   * @return {@code true} if the book was deleted successfully, {@code false} otherwise
   */
  public boolean deleteBook(Book book) {
    return bookDAO.delete(book);
  }

  /**
   * Updates a book in the library system.
   *
   * @param book the {@code Book} object to update
   * @return {@code true} if the book was updated successfully, {@code false} otherwise
   */
  public boolean updateBook(Book book) {
    return bookDAO.update(book);
  }

  // BookItem Management Methods

  /**
   * Adds a new book item to the library.
   *
   * @param bookItem the {@code BookItem} object to add
   * @return {@code true} if the book item was added successfully, {@code false} otherwise
   */
  public boolean addBookItem(BookItem bookItem) {
    return bookItemDAO.add(bookItem);
  }

  /**
   * Deletes a book item from the library.
   *
   * @param bookItem the {@code BookItem} object to delete
   * @return {@code true} if the book item was deleted successfully, {@code false} otherwise
   */
  public boolean deleteBookItem(BookItem bookItem) {
    return bookItemDAO.delete(bookItem);
  }

  /**
   * Updates a book item in the library.
   *
   * @param bookItem the {@code BookItem} object to update
   * @return {@code true} if the book item was updated successfully, {@code false} otherwise
   */
  public boolean updateBookItem(BookItem bookItem) {
    return bookItemDAO.update(bookItem);
  }

  // Member Management Methods

  /**
   * Adds a new member to the library system.
   *
   * @param member the {@code Member} object to add
   * @return {@code true} if the member was added successfully, {@code false} otherwise
   */
  public boolean addMember(Member member) {
    return memberDAO.add(member);
  }

  /**
   * Deletes a member from the library system.
   *
   * @param member the {@code Member} object to delete
   * @return {@code true} if the member was deleted successfully, {@code false} otherwise
   */
  public boolean deleteMember(Member member) {
    return memberDAO.delete(member);
  }

  /**
   * Updates a member in the library system.
   *
   * @param member the {@code Member} object to update
   * @return {@code true} if the member was updated successfully, {@code false} otherwise
   */
  public boolean updateMember(Member member) {
    return memberDAO.update(member);
  }

  // Book Lending Management Methods

  /**
   * Records the borrowing of a book item by a member.
   *
   * @param bookLending the {@code BookLending} object representing the lending transaction
   * @return {@code true} if the book item was borrowed successfully, {@code false} otherwise
   */
  public boolean borrowBookItem(BookLending bookLending) {
    return bookLendingDAO.add(bookLending);
  }

  /**
   * Records the return of a borrowed book item by a member.
   *
   * @param bookLending the {@code BookLending} object representing the lending transaction
   * @return {@code true} if the book item was returned successfully, {@code false} otherwise
   */
  public boolean returnBookItem(BookLending bookLending) {
    return bookLendingDAO.update(bookLending);
  }

  /**
   * Deletes a book lending record from the library system.
   *
   * @param bookLending the {@code BookLending} object representing the lending transaction
   * @return {@code true} if the lending record was deleted successfully, {@code false} otherwise
   */
  public boolean deleteBookLending(BookLending bookLending) {
    return bookLendingDAO.delete(bookLending);
  }
}
