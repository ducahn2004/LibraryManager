package org.group4.base;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Catalog {
  private LocalDate creationDate;
  private int totalBooks;
  private Map<String, List<BookItem>> bookTitles;
  private Map<String, List<BookItem>> bookAuthors;
  private Map<String, List<BookItem>> bookSubjects;
  private Map<String, List<BookItem>> bookPublicationDates;

  public Catalog(LocalDate creationDate) {
    this.creationDate = creationDate;
    this.totalBooks = 0;
    this.bookTitles = new HashMap<>();
    this.bookAuthors = new HashMap<>();
    this.bookSubjects = new HashMap<>();
    this.bookPublicationDates = new HashMap<>();
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public int getTotalBooks() {
    return totalBooks;
  }

  public boolean updateCatalog(BookItem bookItem) {
    if (bookItem == null || bookItem.getAuthors() == null) {
      return false;
    }

    try {
      bookTitles.putIfAbsent(bookItem.getTitle(), new ArrayList<>());
      bookTitles.get(bookItem.getTitle()).add(bookItem);

      for (Author author : bookItem.getAuthors()) {
        bookAuthors.putIfAbsent(author.getName(), new ArrayList<>());
        bookAuthors.get(author.getName()).add(bookItem);
      }

      bookSubjects.putIfAbsent(bookItem.getSubject(), new ArrayList<>());
      bookSubjects.get(bookItem.getSubject()).add(bookItem);

      bookPublicationDates.putIfAbsent(bookItem.getPublicationDate().toString(), new ArrayList<>());
      bookPublicationDates.get(bookItem.getPublicationDate().toString()).add(bookItem);

      totalBooks++;
      return true;
    } catch (Exception e) {
      return false;
    }
  }


}
