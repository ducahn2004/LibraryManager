package org.group4.base.database;

import org.group4.base.books.BookItem;
import org.group4.base.entities.Author;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookItemDatabase {
  private final static List<BookItem> bookItems = new ArrayList<>();

  static {
      // Add actual books to the database
      bookItems.add(new BookItem(
              "978-3-16-148410-0",
              "Effective Java",
              "Programming",
              "Addison-Wesley",
              "English",
              416,
              List.of(new Author("Joshua Bloch", "Author of Effective Java", new ArrayList<>())),
              "1234567890",
              false,
              LocalDate.now().minusDays(10),
              LocalDate.now().plusDays(20),
              45.00,
              BookFormat.HARDCOVER,
              BookStatus.AVAILABLE,
              LocalDate.now().minusYears(1),
              LocalDate.of(2018, 1, 1)
      ));

      bookItems.add(new BookItem(
              "978-0-13-468599-1",
              "Clean Code",
              "Programming",
              "Prentice Hall",
              "English",
              464,
              List.of(new Author("Robert Martin", "Author of Clean Code", new ArrayList<>())),
              "0987654321",
              false,
              LocalDate.now().minusDays(5),
              LocalDate.now().plusDays(25),
              50.00,
              BookFormat.PAPERBACK,
              BookStatus.RESERVED,
              LocalDate.now().minusYears(2),
              LocalDate.of(2008, 8, 1)
      ));
  }
  public static List<BookItem> getBookItems() {
      return bookItems;
  }

}