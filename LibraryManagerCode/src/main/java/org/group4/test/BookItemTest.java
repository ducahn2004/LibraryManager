package org.group4.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import org.group4.base.books.Book;
import org.group4.base.books.BookItem;
import org.group4.base.books.Author;
import org.group4.base.catalog.Rack;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class BookItemTest {

  private Book createTestBook() {
    Set<Author> authors = new HashSet<>();
    authors.add(new Author("Author"));
    return new Book("1234567891111", "Test Book", "Fiction", "Publisher", "English", 300, authors);
  }

  private Rack createTestRack(int id) {
    return new Rack(id, "Fiction");
  }

  @Test
  public void testBookItemCreation() {
    Book book = createTestBook();
    Rack rack = createTestRack(123);
    BookItem bookItem = new BookItem(book, false, 29.99, BookFormat.HARDCOVER, LocalDate.now(), LocalDate.of(2023, 1, 1), rack);
    assertNotNull(bookItem);
    assertEquals("1234567891111", bookItem.getISBN());
    assertEquals("Test Book", bookItem.getTitle());
    assertEquals("Fiction", bookItem.getSubject());
    assertEquals("Publisher", bookItem.getPublisher());
    assertEquals("English", bookItem.getLanguage());
    assertEquals(300, bookItem.getNumberOfPages());
    assertEquals("Author,", bookItem.authorsToString().trim());
    assertEquals("1234567891111-", bookItem.getBarcode().substring(0, 14));
    assertEquals("No", bookItem.getReference());
    assertEquals(29.99, bookItem.getPrice(), 0.01);
    assertEquals(BookFormat.HARDCOVER, bookItem.getFormat());
    assertEquals(BookStatus.AVAILABLE, bookItem.getStatus());
    assertEquals(LocalDate.now(), bookItem.getDateOfPurchase());
    assertEquals(LocalDate.of(2023, 1, 1), bookItem.getPublicationDate());
    assertEquals(rack, bookItem.getPlacedAt());
  }

  @Test
  public void testSetBorrowed() {
    Book book = createTestBook();
    Rack rack = createTestRack(124);
    BookItem bookItem = new BookItem(book, false, 29.99, BookFormat.HARDCOVER, LocalDate.now(), LocalDate.of(2023, 1, 1), rack);
    LocalDate borrowedDate = LocalDate.now();
    bookItem.setBorrowed(borrowedDate);
    assertEquals(borrowedDate, bookItem.getBorrowed());
  }

  @Test
  public void testSetDueDate() {
    Book book = createTestBook();
    Rack rack = createTestRack(125);
    BookItem bookItem = new BookItem(book, false, 29.99, BookFormat.HARDCOVER, LocalDate.now(), LocalDate.of(2023, 1, 1), rack);
    LocalDate dueDate = LocalDate.now().plusDays(14);
    bookItem.setDueDate(dueDate);
    assertEquals(dueDate, bookItem.getDueDate());
  }

  @Test
  public void testSetStatus() {
    Book book = createTestBook();
    Rack rack = createTestRack(126);
    BookItem bookItem = new BookItem(book, false, 29.99, BookFormat.HARDCOVER, LocalDate.now(), LocalDate.of(2023, 1, 1), rack);
    bookItem.setStatus(BookStatus.LOANED);
    assertEquals(BookStatus.LOANED, bookItem.getStatus());
  }

  @Test
  public void testSetReferenceOnly() {
    Book book = createTestBook();
    Rack rack = createTestRack(126);
    BookItem bookItem = new BookItem(book, false, 29.99, BookFormat.HARDCOVER, LocalDate.now(), LocalDate.of(2023, 1, 1), rack);
    bookItem.setReferenceOnly(true);
    assertEquals("Yes", bookItem.getReference());
  }

  @Test
  public void testSetPrice() {
    Book book = createTestBook();
    Rack rack = createTestRack(126);
    BookItem bookItem = new BookItem(book, false, 29.99, BookFormat.HARDCOVER, LocalDate.now(), LocalDate.of(2023, 1, 1), rack);
    bookItem.setPrice(39.99);
    assertEquals(39.99, bookItem.getPrice(), 0.01);
  }

  @Test
  public void testSetFormat() {
    Book book = createTestBook();
    Rack rack = createTestRack(126);
    BookItem bookItem = new BookItem(book, false, 29.99, BookFormat.HARDCOVER, LocalDate.now(), LocalDate.of(2023, 1, 1), rack);
    bookItem.setFormat(BookFormat.PAPERBACK);
    assertEquals(BookFormat.PAPERBACK, bookItem.getFormat());
  }

  @Test
  public void testSetPublicationDate() {
    Book book = createTestBook();
    Rack rack = createTestRack(126);
    BookItem bookItem = new BookItem(book, false, 29.99, BookFormat.HARDCOVER, LocalDate.now(), LocalDate.of(2023, 1, 1), rack);
    LocalDate publicationDate = LocalDate.of(2022, 1, 1);
    bookItem.setPublicationDate(publicationDate);
    assertEquals(publicationDate, bookItem.getPublicationDate());
  }

  @Test
  public void testDateOfPurchase() {
    Book book = createTestBook();
    Rack rack = createTestRack(126);
    BookItem bookItem = new BookItem(book, false, 29.99, BookFormat.HARDCOVER, LocalDate.now(), LocalDate.of(2023, 1, 1), rack);
    LocalDate dateOfPurchase = LocalDate.of(2022, 1, 1);
    bookItem.setDateOfPurchase(dateOfPurchase);
    assertEquals(dateOfPurchase, bookItem.getDateOfPurchase());
  }

  @Test
  public void testSetPlacedAt() {
    Book book = createTestBook();
    Rack rack = createTestRack(126);
    BookItem bookItem = new BookItem(book, false, 29.99, BookFormat.HARDCOVER, LocalDate.now(), LocalDate.of(2023, 1, 1), rack);
    Rack newRack = createTestRack(127);
    bookItem.setPlacedAt(newRack);
    assertEquals(newRack, bookItem.getPlacedAt());
  }

  @Test
  public void testCheckOut() {
    Book book = createTestBook();
    Rack rack = createTestRack(126);
    BookItem bookItem = new BookItem(book, false, 29.99, BookFormat.HARDCOVER, LocalDate.now(), LocalDate.of(2023, 1, 1), rack);
    assertTrue(bookItem.checkOut());
  }
}