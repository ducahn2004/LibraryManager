package org.group4.base.users;

import java.util.List;

import org.group4.database.BookDatabase;
import org.group4.database.BookItemDatabase;
import org.group4.database.AccountDatabase;

import org.group4.base.entities.Person;
import org.group4.base.enums.AccountStatus;
import org.group4.base.books.BookItem;
import org.group4.base.entities.Book;

public class Librarian extends Account {

  // Constructor
  public Librarian(String id, String password, Person person) {
    super(id, password, person);
  }

  public void addBook(Book book) {
    BookDatabase.getInstance().addItem(book);
  }

  public void viewBookDetails(Book book) {
    book.printDetails();
  }

  public void addBookItem(BookItem bookItem) {
    BookItemDatabase.getInstance().addItem(bookItem);
  }

  public void viewBookItemDetails(BookItem bookItem) {
    bookItem.printDetails();
  }

  public void removeBookItem(String barcode) {
    BookItemDatabase.getInstance().getItems().removeIf(bookItem -> bookItem.getBarcode().equals(barcode));
  }

  public static boolean blockMember(String id) {
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

  public void viewMemberDetails(Member member) {
    member.printDetails();
  }
}