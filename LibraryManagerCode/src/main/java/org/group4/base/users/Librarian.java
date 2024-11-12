package org.group4.base.users;

import java.time.LocalDate;
import org.group4.base.books.Book;
import org.group4.base.books.BookItem;
import org.group4.base.manager.LendingManager;
import org.group4.base.manager.Manager;

/**
 * The {@code Librarian} class represents a librarian with the ability to manage books,
 * members, and lending processes within the library system.
 */
public class Librarian extends Person {
  private final Account account;
  private final Manager<Book> bookManager;
  private final Manager<BookItem> bookItemManager;
  private final Manager<Member> memberManager;
  private final LendingManager lendingManager;

  // Constructor
  public Librarian(String name, LocalDate dateOfBirth, String email, String phoneNumber,
      String id, String password, Manager<Book> bookManager, Manager<BookItem> bookItemManager,
      Manager<Member> memberManager, LendingManager lendingManager) {
    super(name, dateOfBirth, email, phoneNumber);
    this.bookManager = bookManager;
    this.bookItemManager = bookItemManager;
    this.account = new Account(id, password);
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

  // Other methods for managing books, bookItems, members, and lending
  public boolean addBook(Book book) {
    return bookManager.add(book);
  }

  public boolean removeBook(Book book) {
    return bookManager.remove(book);
  }

  public boolean updateBook(Book book) {
    return bookManager.update(book);
  }

  public boolean addBookItem(BookItem bookItem) {
    return bookItemManager.add(bookItem);
  }

  public boolean removeBookItem(BookItem bookItem) {
    return bookItemManager.remove(bookItem);
  }

  public boolean updateBookItem(BookItem bookItem) {
    return bookItemManager.update(bookItem);
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
