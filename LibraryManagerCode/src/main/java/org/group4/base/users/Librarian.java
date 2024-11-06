package org.group4.base.users;

import java.io.IOException;
import java.time.LocalDate;
import org.group4.base.books.BookItem;
import org.group4.base.books.BookLending;
import org.group4.base.enums.Address;
import org.group4.base.manager.LendingManager;
import org.group4.base.manager.Manager;
import org.group4.base.books.Book;

public class Librarian extends Person {
  private final Account account;
  private final Manager<BookItem> bookManager;
  private final Manager<Member> memberManager;
  private final LendingManager lendingManager;

  // Constructor

  public Librarian(String name, LocalDate dateOfBirth, Address address, String email, String phoneNumber,
      String id, String password, Manager<BookItem> bookManager, Manager<Member> memberManager,
      LendingManager lendingManager) {
    super(name, dateOfBirth, address, email, phoneNumber);
    this.account = new Account(id, password);
    this.bookManager = bookManager;
    this.memberManager = memberManager;
    this.lendingManager = lendingManager;
  }

  public static boolean login(String id, String password) {
    return Account.login(id, password);
  }

  public boolean changePassword(String oldPassword, String newPassword, String reNewPassword) {
    return account.changePassword(oldPassword, newPassword, reNewPassword);
  }

  public boolean logout() {
    return account.logout();
  }

  public boolean addBookByISBN(String ISBN) throws IOException {
    Book book = new Book(ISBN);
    return true;
  }

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

  public boolean removeMember(Member member) {
    return memberManager.remove(member);
  }

  public boolean updateMember(Member member) {
    return memberManager.update(member);
  }

  public BookLending borrowBookItem(BookItem bookItem, Member member) {
    return lendingManager.borrowBook(bookItem, member);
  }

  public BookLending returnBookItem(BookItem bookItem, Member member) {
    return lendingManager.returnBook(bookItem, member);
  }

  public BookLending renewBookItem(BookItem bookItem, Member member) {
    return lendingManager.renewBook(bookItem, member);
  }

}