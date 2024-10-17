package org.group4.base.catalog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.group4.base.entities.Author;
import org.group4.base.books.BookItem;

/**
 * Quan ly tim kiem va to chuc sach trong thu vien.
 */
public class Catalog {
  private final LocalDate creationDate; // Ngay tao catalog
  private int totalBooks; // Tong so sach trong catalog
  private final Map<String, List<BookItem>> bookTitles; // Danh sach cac sach theo tieu de
  private final Map<String, List<BookItem>> bookAuthors; // Danh sach cac sach theo tac gia
  private final Map<String, List<BookItem>> bookSubjects; // Danh sach cac sach theo chu de
  private final Map<String, List<BookItem>> bookPublicationDates; // Danh sach cac sach theo ngay xuat ban

  /**
   * Tao mot catalog moi.
   * @param creationDate Ngay tao catalog
   */
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
