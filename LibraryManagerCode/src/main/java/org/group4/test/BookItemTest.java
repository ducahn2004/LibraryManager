package org.group4.test;

import org.group4.model.book.Author;
import org.group4.model.book.Book;
import org.group4.model.book.BookItem;
import org.group4.model.book.Rack;
import org.group4.model.enums.BookFormat;
import org.group4.model.enums.BookStatus;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Unit tests for the {@link BookItem} class.
 */
public class BookItemTest {

  private Book book;
  private BookItem bookItem;

  @Before
  public void setUp() {
    book = new Book(
        "1234567890",
        "Test Title",
        "Test Subject",
        "Test Publisher",
        "English",
        300,
        new HashSet<>(Collections.singletonList(new Author("John Doe")))
    );

    bookItem = new BookItem(
        book,
        false, // isReferenceOnly
        25.50, // price
        BookFormat.HARDCOVER,
        LocalDate.now(), // dateOfPurchase
        LocalDate.of(2020, 5, 20), // publicationDate
        new Rack(1, "A1")  // Pass both an integer and a string

    );
  }

  @Test
  public void testBookItemProperties() {
    assertEquals("1234567890", bookItem.getISBN());
    assertEquals("Test Title", bookItem.getTitle());
    assertEquals("Test Subject", bookItem.getSubject());
    assertEquals("Test Publisher", bookItem.getPublisher());
    assertEquals("English", bookItem.getLanguage());
    assertEquals(300, bookItem.getNumberOfPages());
    assertEquals(25.50, bookItem.getPrice(), 0.01);
    assertEquals(BookFormat.HARDCOVER, bookItem.getFormat());
    assertEquals(BookStatus.AVAILABLE, bookItem.getStatus());
    assertFalse(bookItem.getIsReferenceOnly());
    assertEquals(LocalDate.of(2020, 5, 20), bookItem.getPublicationDate());
  }

  @Test
  public void testCheckOutAvailable() {
    bookItem.setStatus(BookStatus.AVAILABLE);
    assertTrue(bookItem.checkOut());
  }

  @Test
  public void testCheckOutReferenceOnly() {
    bookItem.setReferenceOnly(true);
    bookItem.setStatus(BookStatus.AVAILABLE);
    assertFalse(bookItem.checkOut());
  }

  @Test
  public void testCheckOutLoaned() {
    bookItem.setStatus(BookStatus.LOANED);
    assertFalse(bookItem.checkOut());
  }

  @Test
  public void testCheckOutLost() {
    bookItem.setStatus(BookStatus.LOST);
    assertFalse(bookItem.checkOut());
  }

  @Test
  public void testSetAndGetBorrowed() {
    LocalDate borrowedDate = LocalDate.of(2024, 11, 1);
    bookItem.setBorrowed(borrowedDate);
    assertEquals(borrowedDate, bookItem.getBorrowed());
  }

  @Test
  public void testSetAndGetDueDate() {
    LocalDate dueDate = LocalDate.of(2024, 12, 1);
    bookItem.setDueDate(dueDate);
    assertEquals(dueDate, bookItem.getDueDate());
  }

  @Test
  public void testToString() {
    String expected = "ISBN = '1234567890',\n"
        + "Title = 'Test Title',\n"
        + "Subject = 'Test Subject',\n"
        + "Publisher = 'Test Publisher',\n"
        + "Language = 'English',\n"
        + "Authors = John Doe,\n"
        + "Barcode = 'null',\n"
        + "Reference Only = false,\n"
        + "Borrowed = null,\n"
        + "Due Date = null,\n"
        + "Price = 25.5,\n"
        + "Format = HARDCOVER,\n"
        + "Status = AVAILABLE,\n"
        + "Date of Purchase = " + LocalDate.now() + ",\n"
        + "Publication Date = 2020-05-20,\n"
        + "Placed At = A1";

    assertEquals(expected, bookItem.toString());
  }
}
