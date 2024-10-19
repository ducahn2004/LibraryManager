package org.group4.base.users;

import java.util.List;

import org.group4.base.database.BookDatabase;
import org.group4.base.database.BookItemDatabase;
import org.group4.base.database.AccountDatabase;

import org.group4.base.entities.Person;
import org.group4.base.enums.AccountStatus;
import org.group4.base.books.BookItem;
import org.group4.base.entities.Book;

import org.jetbrains.annotations.NotNull;

public class Librarian extends Account {

  // Constructor
  public Librarian(String id, String password, Person person) {
    super(id, password, person);
  }

  public void addBook(Book book) {
    BookDatabase.getInstance().addItem(book);
  }

  public void viewBookDetails(@NotNull Book book) {
    book.printDetails();
  }

  public void addBookItem(BookItem bookItem) {
    BookItemDatabase.getInstance().addItem(bookItem);
  }

  public void viewBookItemDetails(@NotNull BookItem bookItem) {
    bookItem.printDetails();
  }

  public void removeBookItem(BookItem bookItem) {
    BookItemDatabase.getInstance().removeItem(bookItem);
  }

  public boolean blockMember(String id) {
    List<Account> accounts = AccountDatabase.getInstance().getItems();
    for (Account account : accounts) {
      if (account.getId().equals(id) && account instanceof Member) {
        account.setStatus(AccountStatus.BLACKLISTED);
        return true;
      }
    }
    return false;
  }

  public boolean unblockMember(String id) {
    List<Account> accounts = AccountDatabase.getInstance().getItems();
    for (Account account : accounts) {
      if (account.getId().equals(id) && account instanceof Member) {
        account.setStatus(AccountStatus.ACTIVE);
        return true;
      }
    }
    return false;
  }

  public void viewMemberDetails(@NotNull Member member) {
    member.printDetails();
  }
}