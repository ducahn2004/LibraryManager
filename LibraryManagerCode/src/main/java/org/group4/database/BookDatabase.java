package org.group4.database;

import org.group4.base.books.Book;

public class BookDatabase extends Database<Book> {
  private static final BookDatabase instance = new BookDatabase();

  private BookDatabase() {
  }

  public static BookDatabase getInstance() {
    return instance;
  }
}