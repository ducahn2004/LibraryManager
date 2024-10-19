package org.group4.base.database;

import java.util.List;
import java.util.ArrayList;

import org.group4.base.books.BookLending;

public class BookLendingDatabase {
  private final static List<BookLending> bookLendings = new ArrayList<>();

  public static List<BookLending> getBookLendings() {
    return bookLendings;
  }
}
