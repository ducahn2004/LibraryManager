package org.group4.test;

import org.group4.model.book.BookItem;
import org.group4.model.transaction.BookLending;
import org.group4.model.user.Member;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BookLendingTest {

  private BookItem mockBookItem;
  private Member mockMember;
  private BookLending bookLending;

  @Before
  public void setUp() {
    mockBookItem = mock(BookItem.class);
    mockMember = mock(Member.class);

    when(mockBookItem.getTitle()).thenReturn("Mock Book Title");
    when(mockMember.getName()).thenReturn("Mock Member Name");

    bookLending = new BookLending(mockBookItem, mockMember);
  }

  @Test
  public void testConstructorDefault() {
    assertEquals(mockBookItem, bookLending.getBookItem());
    assertEquals(mockMember, bookLending.getMember());
    assertEquals(LocalDate.now(), bookLending.getLendingDate());
    assertEquals(LocalDate.now().plusDays(14), bookLending.getDueDate());
    assertFalse(bookLending.getReturnDate().isPresent());
  }

  @Test
  public void testConstructorCustom() {
    LocalDate lendingDate = LocalDate.of(2024, 11, 10);
    LocalDate dueDate = LocalDate.of(2024, 11, 24);
    LocalDate returnDate = LocalDate.of(2024, 11, 20);

    BookLending customLending = new BookLending(mockBookItem, mockMember, lendingDate, dueDate,
        returnDate);

    assertEquals(lendingDate, customLending.getLendingDate());
    assertEquals(dueDate, customLending.getDueDate());
    assertTrue(customLending.getReturnDate().isPresent());
    assertEquals(returnDate, customLending.getReturnDate().get());
  }

  @Test
  public void testSetReturnDate() {
    LocalDate returnDate = LocalDate.of(2024, 11, 20);

    bookLending.setReturnDate(returnDate);

    assertTrue(bookLending.getReturnDate().isPresent());
    assertEquals(returnDate, bookLending.getReturnDate().get());
  }

  @Test
  public void testGetBookItem() {
    assertEquals(mockBookItem, bookLending.getBookItem());
    assertEquals("Mock Book Title", bookLending.getBookItem().getTitle());
  }

  @Test
  public void testGetMember() {
    assertEquals(mockMember, bookLending.getMember());
    assertEquals("Mock Member Name", bookLending.getMember().getName());
  }

  @Test
  public void testToString() {
    String expected = "book = Mock Book Title,\n" +
        "member = Mock Member Name,\n" +
        "lendingDate = " + LocalDate.now().toString() + ",\n" +
        "dueDate = " + LocalDate.now().plusDays(14).toString() + ",\n" +
        "returnDate = null";

    assertEquals(expected, bookLending.toString());
  }
}
