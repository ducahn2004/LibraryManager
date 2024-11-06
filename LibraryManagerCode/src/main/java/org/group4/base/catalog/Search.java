package org.group4.base.catalog;

import java.util.List;
import org.group4.base.books.BookItem;

public interface Search {
  List<BookItem> searchByISBN(String ISBN);
  List<BookItem> searchByTitle(String title);
  List<BookItem> searchByAuthor(String author);

}
