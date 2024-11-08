package org.group4.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

import java.util.logging.Logger;
import org.group4.base.books.Book;
import org.group4.base.books.Author;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class BookTest {

  @Test
  public void testBookCreation() {
    Set<Author> authors = new HashSet<>();
    authors.add(new Author("Author"));
    Book book = new Book("1234567891111", "Test Book", "Fiction", "Publisher", "English", 300, authors);
    assertNotNull(book);
    assertEquals("1234567891111", book.getISBN());
    assertEquals("Test Book", book.getTitle());
    assertEquals("Fiction", book.getSubject());
    assertEquals("Publisher", book.getPublisher());
    assertEquals("English", book.getLanguage());
    assertEquals(300, book.getNumberOfPages());
    assertEquals("Author,", book.authorsToString().trim());
  }

  @Test
  public void testBookCreationWithGoogleBooksAPI() {
    try {
      Book book = new Book("9781451648546");
      assertNotNull(book);
      assertEquals("9781451648546", book.getISBN());
      assertEquals("Steve Jobs", book.getTitle());
      assertEquals("Biography & Autobiography", book.getSubject());
      assertEquals("Simon and Schuster", book.getPublisher());
      assertEquals("en", book.getLanguage());
      assertEquals(656, book.getNumberOfPages());
      assertEquals("Walter Isaacson,", book.authorsToString().trim());
    } catch (Exception e) {
      Logger.getLogger(BookTest.class.getName()).warning("No book found with the given ISBN");
    }
  }
}