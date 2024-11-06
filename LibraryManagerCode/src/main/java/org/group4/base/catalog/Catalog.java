package org.group4.base.catalog;

import java.util.List;
import java.util.stream.Collectors;
import org.group4.base.books.BookItem;

public class Catalog implements Search {
  private final List<BookItem> bookItems;

  public Catalog(List<BookItem> bookItems) {
    this.bookItems = bookItems;
  }

  @Override
  public List<BookItem> searchByISBN(String ISBN) {
    return bookItems.stream()
        .filter(bookItem -> bookItem.getISBN().equals(ISBN))
        .collect(Collectors.toList());
  }

  @Override
  public List<BookItem> searchByTitle(String title) {
    return bookItems.stream()
        .filter(bookItem -> bookItem.getTitle().equals(title))
        .collect(Collectors.toList());
  }

  @Override
  public List<BookItem> searchByAuthor(String author) {
    return bookItems.stream()
        .filter(bookItem -> bookItem.getAuthors().stream()
            .anyMatch(bookAuthor -> bookAuthor.getName().equals(author)))
        .collect(Collectors.toList());
  }
}
