package org.group4.base.books;

import java.time.LocalDate;
import java.util.List;
import org.group4.base.entities.Author;
import org.group4.base.entities.Book;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;

public class BookInfor extends Book {
  private int number;
  private String locationIdentifier;
  public BookInfor(String ISBN, String title, String subject, String publisher, String language, int numberOfPages,
      List<Author> authors, String barcode, int number, String locationIdentifier) {
    super(ISBN, title, subject, publisher, language, numberOfPages, authors);
    this.number = number;
    this.locationIdentifier = locationIdentifier;
  }
}
