package org.group4.test;

import org.group4.model.book.Author;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the {@link Author} class.
 */
public class AuthorTest {

  private Author author;

  @Before
  public void setUp() {
    // Khởi tạo đối tượng Author mặc định trước mỗi test
    author = new Author("Default Name");
  }

  @Test
  public void testConstructorWithName() {
    // Arrange
    String name = "John Doe";

    // Act
    Author author = new Author(name);

    // Assert
    assertEquals("John Doe", author.getName());
    assertNull(author.getAuthorId()); // Author ID phải là null
  }

  @Test
  public void testConstructorWithIdAndName() {
    // Arrange
    String authorId = "A123";
    String name = "Jane Doe";

    // Act
    Author author = new Author(authorId, name);

    // Assert
    assertEquals("A123", author.getAuthorId());
    assertEquals("Jane Doe", author.getName());
  }

  @Test
  public void testSetAuthorId() {
    // Arrange
    String newAuthorId = "B456";

    // Act
    author.setAuthorId(newAuthorId);

    // Assert
    assertEquals("B456", author.getAuthorId());
  }

  @Test
  public void testSetName() {
    // Arrange
    String newName = "Updated Name";

    // Act
    author.setName(newName);

    // Assert
    assertEquals("Updated Name", author.getName());
  }

  @Test
  public void testGetAuthorId() {
    // Arrange
    String authorId = "C789";
    author.setAuthorId(authorId);

    // Act
    String result = author.getAuthorId();

    // Assert
    assertEquals("C789", result);
  }

  @Test
  public void testGetName() {
    // Act
    String result = author.getName();

    // Assert
    assertEquals("Default Name", result);
  }
}
