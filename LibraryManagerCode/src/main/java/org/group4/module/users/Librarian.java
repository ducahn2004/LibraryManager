package org.group4.module.users;

import java.sql.SQLException;
import java.time.LocalDate;

import org.group4.module.books.Book;
import org.group4.module.books.BookItem;
import org.group4.module.enums.BookStatus;
import org.group4.module.manager.BookItemManager;
import org.group4.module.manager.BookLendingManager;
import org.group4.module.manager.BookManager;
import org.group4.module.manager.MemberManager;
import org.group4.module.transactions.BookLending;


/**
 * The {@code Librarian} class represents a librarian in the library system. It provides methods to
 * manage books, book items, members, and book lending operations.
 */
public class Librarian extends Person {

  /** The ID of the librarian. */
  String librarianId;

  /** The manager for book-related operations. */
  private final BookManager bookManager = new BookManager();

  /** The manager for book item-related operations. */
  private final BookItemManager bookItemManager = new BookItemManager();

  /** The manager for member-related operations. */
  private final MemberManager memberManager = new MemberManager();

  /** The manager for book lending-related operations. */
  private final BookLendingManager bookLendingDAO = new BookLendingManager();

  /**
   * Constructs a {@code Librarian} object with the specified details and account credentials.
   *
   * @param librarianId the ID of the librarian
   * @param name the name of the librarian
   * @param dateOfBirth the date of birth of the librarian
   * @param email the email address of the librarian
   * @param phoneNumber the phone number of the librarian
   */
  public Librarian(String librarianId, String name, LocalDate dateOfBirth, String email,
      String phoneNumber) {
    super(name, dateOfBirth, email, phoneNumber);
    this.librarianId = librarianId;
  }

  /**
   * Returns the ID of the librarian.
   *
   * @return the librarian ID
   */
  public String getLibrarianId() {
    return librarianId;
  }

  // Book Management Methods

  /**
   * Adds a new book to the library system.
   *
   * @param book the {@code Book} object to add
   * @return {@code true} if the book was added successfully, {@code false} otherwise
   */
  public boolean addBook(Book book) {
    return bookManager.add(book);
  }

  /**
   * Deletes a book from the library system.
   *
   * @param isbn the ISBN of the book to delete
   * @return {@code true} if the book was deleted successfully, {@code false} otherwise
   */
  public boolean deleteBook(String isbn) {
    return bookManager.delete(isbn);
  }

  /**
   * Updates a book in the library system.
   *
   * @param book the {@code Book} object to update
   * @return {@code true} if the book was updated successfully, {@code false} otherwise
   */
  public boolean updateBook(Book book) {
    return bookManager.update(book);
  }

  // BookItem Management Methods

  /**
   * Adds a new book item to the library.
   *
   * @param bookItem the {@code BookItem} object to add
   * @return {@code true} if the book item was added successfully, {@code false} otherwise
   */
  public boolean addBookItem(BookItem bookItem) {
    return bookItemManager.add(bookItem);
  }

  /**
   * Deletes a book item from the library.
   *
   * @param barcode the barcode of the book item to delete
   * @return {@code true} if the book item was deleted successfully, {@code false} otherwise
   */
  public boolean deleteBookItem(String barcode) {
    return bookItemManager.delete(barcode);
  }

  /**
   * Updates a book item in the library.
   *
   * @param bookItem the {@code BookItem} object to update
   * @return {@code true} if the book item was updated successfully, {@code false} otherwise
   */
  public boolean updateBookItem(BookItem bookItem) {
    return bookItemManager.update(bookItem);
  }

  // Member Management Methods

  /**
   * Adds a new member to the library system.
   *
   * @param member the {@code Member} object to add
   * @return {@code true} if the member was added successfully, {@code false} otherwise
   */
  public boolean addMember(Member member) {
    return memberManager.add(member);
  }

  /**
   * Deletes a member from the library system.
   *
   * @param memberId the ID of the member to delete
   * @return {@code true} if the member was deleted successfully, {@code false} otherwise
   */
  public boolean deleteMember(String memberId) {
    return memberManager.delete(memberId);
  }

  /**
   * Updates a member in the library system.
   *
   * @param member the {@code Member} object to update
   * @return {@code true} if the member was updated successfully, {@code false} otherwise
   */
  public boolean updateMember(Member member) {
    return memberManager.update(member);
  }

  // Book Lending Management Methods


  /**
   * Borrows a book item from the library system.
   *
   * @param bookItem the {@code BookItem} object to borrow
   * @param member the {@code Member} object borrowing the book item
   * @return {@code true} if the book item was borrowed successfully, {@code false} otherwise
   */
  public boolean borrowBookItem(BookItem bookItem, Member member) {
    return BookLendingManager.borrowBookItem(bookItem, member);
  }

  /**
   * Returns a book item to the library system.
   *
   * @param bookItem the {@code BookItem} object to return
   * @param member the {@code Member} object returning the book item
   * @param status the {@code BookStatus} to set for the returned book item
   * @return {@code true} if the book item was returned successfully, {@code false} otherwise
   */
  public boolean returnBookItem(BookItem bookItem, Member member, BookStatus status)
      throws SQLException {
    return BookLendingManager.returnBookItem(bookItem, member, status);
  }

}
