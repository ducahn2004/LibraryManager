package org.group4.base.users;

import java.io.IOException;
import java.time.LocalDate;
import org.group4.base.books.BookItem;
import org.group4.base.manager.LendingManager;
import org.group4.base.manager.Manager;
import org.group4.base.books.Book;

/**
 * The {@code Librarian} class represents a librarian with the ability to manage books,
 * members, and lending processes within the library system.
 */
public class Librarian extends Person {
  private final Account account;
  private final Manager<BookItem> bookManager;
  private final Manager<Member> memberManager;
  private final LendingManager lendingManager;

  // Constructor
  public Librarian(String name, LocalDate dateOfBirth, String email, String phoneNumber,
      String id, String password, Manager<BookItem> bookManager,
      Manager<Member> memberManager, LendingManager lendingManager) {
    super(name, dateOfBirth, email, phoneNumber);
    this.account = new Account(id, password);
    this.bookManager = bookManager;
    this.memberManager = memberManager;
    this.lendingManager = lendingManager;
  }

  /**
   * Attempts to log in with the given credentials.
   * @param id The librarian's id
   * @param password The librarian's password
   * @return true if login is successful, false otherwise
   */
  public static boolean login(String id, String password) {
    return Account.login(id, password);
  }

  /**
   * Changes the librarian's password if the old password matches.
   * @param oldPassword The old password
   * @param newPassword The new password
   * @param reNewPassword The new password confirmation
   * @return true if password is successfully changed, false otherwise
   */
  public boolean changePassword(String oldPassword, String newPassword, String reNewPassword) {
    return account.changePassword(oldPassword, newPassword, reNewPassword);
  }

  /**
   * Logs the librarian out.
   * @return true if logout is successful, false otherwise
   */
  public boolean logout() {
    return account.logout();
  }

  /**
   * Adds a book to the library using its ISBN.
   * @param ISBN The ISBN of the book to add
   * @return true if the book is successfully added, false otherwise
   * @throws IOException if the book cannot be added
   */
  public boolean addBookByISBN(String ISBN) throws IOException {
    try {
      Book book = new Book(ISBN);
      return true;
    } catch (IOException e) {
      throw new IOException("Failed to add book with ISBN: " + ISBN, e);
    }
  }

  // Other methods for managing books, members, and lending
  public boolean addBookItem(BookItem bookItem) {
    return bookManager.add(bookItem);
  }

  public boolean removeBookItem(BookItem bookItem) {
    return bookManager.remove(bookItem);
  }

  public boolean updateBookItem(BookItem bookItem) {
    return bookManager.update(bookItem);
  }

  public boolean addMember(Member member) {
    return memberManager.add(member);
  }

  public void removeMember(Member member) {
    memberManager.remove(member);
  }

  public boolean updateMember(Member member) {
    return memberManager.update(member);
  }

  public boolean borrowBookItem(BookItem bookItem, Member member) {
    return lendingManager.borrowBook(bookItem, member);
  }

  public boolean returnBookItem(BookItem bookItem, Member member) {
    return lendingManager.returnBook(bookItem, member);
  }

  public boolean renewBookItem(BookItem bookItem, Member member) {
    return lendingManager.renewBook(bookItem, member);
  }
}
