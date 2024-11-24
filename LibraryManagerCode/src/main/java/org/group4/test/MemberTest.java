package org.group4.test;

import org.group4.model.user.Member;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class MemberTest {

  private Member member;

  @Before
  public void setUp() {
    member = new Member("M001", "John Smith", LocalDate.of(1990, 5, 10),
        "john.smith@example.com", "123-456-7890", 5);
  }

  @Test
  public void testMemberConstructor() {
    assertNotNull(member);
    assertEquals("M001", member.getMemberId());
    assertEquals("John Smith", member.getName());
    assertEquals(LocalDate.of(1990, 5, 10), member.getDateOfBirth());
    assertEquals("john.smith@example.com", member.getEmail());
    assertEquals("123-456-7890", member.getPhoneNumber());
    assertEquals(5, member.getTotalBooksCheckedOut());
  }

  @Test
  public void testSetMemberId() {
    member.setMemberId("M002");
    assertEquals("M002", member.getMemberId());
  }

  @Test
  public void testSetTotalBooksCheckedOut() {
    member.setTotalBooksCheckedOut(10);
    assertEquals(10, member.getTotalBooksCheckedOut());
  }

  @Test
  public void testToString() {
    String expectedToString = "  memberId = 'M001',\n" +
        "  name = 'John Smith',\n" +
        "  dateOfBirth = 1990-05-10,\n" +
        "  email = 'john.smith@example.com',\n" +
        "  phoneNumber = '123-456-7890'\n";
    assertEquals(expectedToString, member.toString());
  }
}
