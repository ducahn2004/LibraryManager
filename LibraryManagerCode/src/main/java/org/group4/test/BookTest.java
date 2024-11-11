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

}