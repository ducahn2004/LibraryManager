package org.group4.base.catalog;

import java.time.LocalDate;
import java.util.List;
import org.group4.base.books.BookItem;

public interface Search {
  List<BookItem> searchByTitle(String title);
  List<BookItem> searchByAuthor(String author);
  List<BookItem> searchBySubject(String subject);
  List<BookItem> searchByPubDate(LocalDate publicationDate);
}
