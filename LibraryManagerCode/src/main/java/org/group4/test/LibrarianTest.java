package org.group4.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import org.group4.model.user.Librarian;
import org.group4.service.interfaces.BookItemManager;
import org.group4.service.interfaces.BookManager;
import org.group4.service.interfaces.LendingManager;
import org.group4.service.interfaces.MemberManager;
import org.junit.Before;
import org.junit.Test;

public class LibrarianTest {

  private Librarian librarian;
  private BookManager bookManagerMock;
  private BookItemManager bookItemManagerMock;
  private MemberManager memberManagerMock;
  private LendingManager lendingManagerMock;

  @Before
  public void setUp() {
    // Mock the dependencies
    bookManagerMock = mock(BookManager.class);
    bookItemManagerMock = mock(BookItemManager.class);
    memberManagerMock = mock(MemberManager.class);
    lendingManagerMock = mock(LendingManager.class);

    // Create the librarian with mocked services
    librarian = new Librarian(
        "L123", "John Doe", LocalDate.of(1985, 5, 15), "john.doe@example.com",
        "123-456-7890");
  }

  @Test
  public void testGetLibrarianId() {
    assertEquals("L123", librarian.getLibrarianId());
  }

  @Test
  public void testGetBookManager() {
    assertNotNull(librarian.getBookManager());
  }

  @Test
  public void testGetBookItemManager() {
    assertNotNull(librarian.getBookItemManager());
  }

  @Test
  public void testGetMemberManager() {
    assertNotNull(librarian.getMemberManager());
  }

  @Test
  public void testGetLendingManager() {
    assertNotNull(librarian.getLendingManager());
  }
}
