package org.group4.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

import java.time.LocalDate;
import org.group4.base.books.Book;
import org.group4.base.books.BookItem;
import org.group4.base.catalog.Catalog;
import org.junit.Test;

import java.util.List;

public class CatalogTest {
  Book book = new Book("123456789", "Test Book", "Fiction", "Publisher", "English", 300, null);
  BookItem bookItem = new BookItem(book, false, 0, null, LocalDate.now(), LocalDate.now(), null);
  BookItem bookItem1 = new BookItem(book, false, 0, null, LocalDate.now(), LocalDate.now(), null);
  BookItem bookItem2 = new BookItem(book, false, 0, null, LocalDate.now(), LocalDate.now(), null);

  @Test
  public void testAddBookItem() {
    Catalog catalog = new Catalog();
    catalog.addBookItem(bookItem);
    List<BookItem> bookItems = catalog.getBookItems();
    assertEquals(1, bookItems.size());
    assertEquals(bookItem, bookItems.get(0));
  }

  @Test
  public void testRemoveBookItem() {
    Catalog catalog = new Catalog();
    catalog.addBookItem(bookItem);
    catalog.removeBookItem(bookItem);
    List<BookItem> bookItems = catalog.getBookItems();
    assertEquals(0, bookItems.size());
  }

  @Test
  public void testSearchByISBN() {
    Catalog catalog = new Catalog();
    catalog.addBookItem(bookItem);
    List<BookItem> foundItems = catalog.searchByISBN("123456789");
    assertNotNull(foundItems);
    assertEquals(1, foundItems.size());
    assertEquals(bookItem, foundItems.get(0));
  }

  @Test
  public void testSearchByTitle() {
    Catalog catalog = new Catalog();
    catalog.addBookItem(bookItem1);
    catalog.addBookItem(bookItem2);
    List<BookItem> foundItems = catalog.searchByTitle("Test Book");
    assertEquals(1, foundItems.size());
    assertEquals(bookItem1, foundItems.get(0));
  }

  @Test
  public void testSearchByAuthor() {
    Catalog catalog = new Catalog();
    catalog.addBookItem(bookItem1);
    catalog.addBookItem(bookItem2);
    List<BookItem> foundItems = catalog.searchByAuthor("Author1");
    assertEquals(1, foundItems.size());
    assertEquals(bookItem1, foundItems.get(0));
  }
}