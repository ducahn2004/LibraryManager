package org.group4.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

import org.group4.base.books.Author;
import org.junit.Test;

public class AuthorTest {

  @Test
  public void testAuthorCreation() {
    Author author = new Author("Author Name");
    assertNotNull(author);
    assertEquals("Author Name", author.getName());
  }

  @Test
  public void testSetName() {
    Author author = new Author("Initial Name");
    author.setName("Updated Name");
    assertEquals("Updated Name", author.getName());
  }
}