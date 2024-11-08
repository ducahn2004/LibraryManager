package org.group4.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import org.group4.base.books.Book;
import org.group4.base.books.BookItem;
import org.group4.base.books.BookLending;
import org.group4.base.users.Member;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class MemberTest {
  Member member = new Member("John Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com", "1234567890", "M123");
  Book book = new Book("123456789", "Test Book", "Fiction", "Publisher", "English", 300, null);
  BookItem bookItem = new BookItem(book, false, 29.99, null, LocalDate.now(), LocalDate.of(2023, 1, 1), null);
  BookLending bookLending = new BookLending(bookItem, member);
  @Test
  public void testMemberCreation() {
    assertNotNull(member);
    assertEquals("John Doe", member.getName());
    assertEquals(LocalDate.of(1990, 1, 1), member.getDateOfBirth());
    assertEquals("john.doe@example.com", member.getEmail());
    assertEquals("1234567890", member.getPhoneNumber());
    assertEquals("M123", member.getMemberId());
  }

  @Test
  public void testAddBookLending() {
    assertTrue(member.addBookLending(bookLending));
    List<BookLending> bookLendings = member.getBookLendings();
    assertEquals(1, bookLendings.size());
    assertEquals(bookLending, bookLendings.getFirst());
  }

  @Test
  public void testRemoveBookLending() {
    member.addBookLending(bookLending);
    assertTrue(member.removeBookLending(bookLending));
    List<BookLending> bookLendings = member.getBookLendings();
    assertEquals(0, bookLendings.size());
  }

  @Test
  public void testGetBookLending() {
    member.addBookLending(bookLending);
    BookLending retrievedLending = member.getBookLending(bookItem.getBarcode());
    assertNotNull(retrievedLending);
    assertEquals(bookLending, retrievedLending);
  }
}