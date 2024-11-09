package org.group4.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

import org.group4.base.books.Book;
import org.group4.base.books.BookItem;
import org.group4.base.books.BookLending;
import org.group4.base.users.Librarian;
import org.group4.base.users.Member;
import org.group4.database.LibrarianDatabase;
import org.junit.Test;

import java.time.LocalDate;

public class BookLendingTest {
  Member member = new Member("John Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com", "1234567890");
  Book book = new Book("123456789", "Test Book", "Fiction", "Publisher", "English", 300, null);
  BookItem bookItem = new BookItem(book, false, 29.99, null, LocalDate.now(), LocalDate.of(2023, 1, 1), null);

  @Test
  public void testBookLendingCreation() {
    BookLending bookLending = new BookLending(bookItem, member);
    assertNotNull(bookLending);
    assertEquals(bookItem, bookLending.getBookItem());
    assertEquals(member, bookLending.getMember());
    assertNotNull(bookLending.getCreationDate());
    assertNotNull(bookLending.getDueDate());
  }

  @Test
  public void testSetDueDate() {
    BookLending bookLending = new BookLending(bookItem, member);
    LocalDate newDueDate = LocalDate.now().plusDays(7);
    bookLending.setDueDate(newDueDate);
    assertEquals(newDueDate, bookLending.getDueDate());
  }

  @Test
  public void testSetReturnDate() {
    BookLending bookLending = new BookLending(bookItem, member);
    LocalDate returnDate = LocalDate.now();
    bookLending.setReturnDate(returnDate);
    assertEquals(returnDate, bookLending.getReturnDate());
  }

}