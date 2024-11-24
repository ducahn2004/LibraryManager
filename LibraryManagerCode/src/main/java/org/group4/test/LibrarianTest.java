package org.group4.test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.time.LocalDate;

import org.group4.model.user.Librarian;
import org.group4.service.interfaces.BookItemManagerService;
import org.group4.service.interfaces.BookManagerService;
import org.group4.service.interfaces.LendingManagerService;
import org.group4.service.interfaces.MemberManagerService;
import org.junit.Before;
import org.junit.Test;

public class LibrarianTest {

  private Librarian librarian;
  private BookManagerService bookManagerMock;
  private BookItemManagerService bookItemManagerMock;
  private MemberManagerService memberManagerMock;
  private LendingManagerService lendingManagerMock;

  @Before
  public void setUp() {
    // Mock the dependencies
    bookManagerMock = mock(BookManagerService.class);
    bookItemManagerMock = mock(BookItemManagerService.class);
    memberManagerMock = mock(MemberManagerService.class);
    lendingManagerMock = mock(LendingManagerService.class);

    // Create the librarian with mocked services
    librarian = new Librarian("L123", "John Doe", LocalDate.of(1985, 5, 15),
        "john.doe@example.com", "123-456-7890");
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
