package org.group4.test;

import org.group4.base.books.BookItem;
import org.group4.base.books.Book;

public class UUIDExample {
  public static void main(String[] args) {
    Book book = new Book("978-3-16-148410-0", "The Book", "Science", "Publisher", "English", 100, null);
    BookItem bookItem = new BookItem(book, false, 10.0, null, null, null);
    System.out.println("Barcode: " + bookItem.getBarcode());
  }

}
