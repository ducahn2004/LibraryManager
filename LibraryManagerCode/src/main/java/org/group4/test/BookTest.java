package org.group4.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import org.group4.model.book.Author;
import org.group4.model.book.Book;
import org.junit.Before;
import org.junit.Test;

public class BookTest {

  private Book book;
  private Set<Author> authors;

  @Before
  public void setUp() {
    // Create a set of authors
    authors = new HashSet<>();
    authors.add(new Author("A123", "Author 1"));
    authors.add(new Author("A456", "Author 2"));

    // Initialize a Book object with sample values
    book = new Book(
        "ISBN12345",
        "Sample Book Title",
        "Fiction",
        "Sample Publisher",
        "English",
        300,
        authors);
  }

  @Test
  public void testConstructor() {
    // Verify the values of the attributes
    assertEquals("ISBN12345", book.getISBN());
    assertEquals("Sample Book Title", book.getTitle());
    assertEquals("Fiction", book.getSubject());
    assertEquals("Sample Publisher", book.getPublisher());
    assertEquals("English", book.getLanguage());
    assertEquals(300, book.getNumberOfPages());
    assertEquals(2, book.getAuthors().size());
  }

  @Test
  public void testSetters() {
    // Set new values for the book's attributes
    Set<Author> newAuthors = new HashSet<>();
    newAuthors.add(new Author("B789", "New Author"));

    book.setISBN("NEWISBN");
    book.setTitle("New Title");
    book.setSubject("Non-Fiction");
    book.setPublisher("New Publisher");
    book.setLanguage("Spanish");
    book.setNumberOfPages(150);
    book.setAuthors(newAuthors);

    // Verify the updated values
    assertEquals("NEWISBN", book.getISBN());
    assertEquals("New Title", book.getTitle());
    assertEquals("Non-Fiction", book.getSubject());
    assertEquals("New Publisher", book.getPublisher());
    assertEquals("Spanish", book.getLanguage());
    assertEquals(150, book.getNumberOfPages());
    assertEquals(1, book.getAuthors().size());
  }

  @Test
  public void testAuthorsToString() {
    // Verify the authorsToString() method
    String authorsString = book.authorsToString();
    assertTrue(authorsString.contains("Author 1"));
    assertTrue(authorsString.contains("Author 2"));
  }

  @Test
  public void testToString() {
    // Verify the toString() method
    String bookString = book.toString();
    assertTrue(bookString.contains("ISBN = 'ISBN12345'"));
    assertTrue(bookString.contains("title = 'Sample Book Title'"));
    assertTrue(bookString.contains("subject = 'Fiction'"));
    assertTrue(bookString.contains("publisher = 'Sample Publisher'"));
    assertTrue(bookString.contains("language = 'English'"));
    assertTrue(bookString.contains("numberOfPages = 300"));
    assertTrue(bookString.contains("authors = Author 1, Author 2"));
  }
}
