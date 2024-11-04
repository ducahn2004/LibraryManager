package org.group4.base.users;

import org.group4.base.entities.Account;
import org.group4.base.entities.Book;
import org.group4.database.BookDatabase;
import org.group4.database.BookItemDatabase;

import org.group4.base.books.BookItem;

public class Librarian extends Account {

  // Constructor
  public Librarian() {
  }

  public Librarian(String id, String password) {
    super(id, password);
  }

  public void addBook(Book book) {
    BookDatabase.getInstance().addItem(book);
  }

  public void addBookItem(BookItem bookItem) {
    BookItemDatabase.getInstance().addItem(bookItem);
  }

  public void removeBookItem(String barcode) {
    BookItemDatabase.getInstance().getItems().removeIf(bookItem -> bookItem.getBarcode().equals(barcode));
  }

  public void addBook() {

  }

  public void addBookItem() {

  }

  public void removeBookItem() {

  }

  public void updateBookItem() {

  }

  public void findBookItem() {

  }

  public void displayBooks() {

  }

  public void borrowBookItem() {

  }

  public void returnBookItem() {

  }

}