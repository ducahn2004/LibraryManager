package org.group4.test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

import org.group4.base.users.Person;
import org.junit.Test;
import java.time.LocalDate;

public class PersonTest {

  @Test
  public void testPersonCreation() {
    Person person = new Person("John Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com", "1234567890");
    assertNotNull(person);
    assertEquals("John Doe", person.getName());
    assertEquals(LocalDate.of(1990, 1, 1), person.getDateOfBirth());
    assertEquals("john.doe@example.com", person.getEmail());
    assertEquals("1234567890", person.getPhoneNumber());
  }

  @Test
  public void testSetName() {
    Person person = new Person("John Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com", "1234567890");
    person.setName("Jane Doe");
    assertEquals("Jane Doe", person.getName());
  }

  @Test
  public void testSetDateOfBirth() {
    Person person = new Person("John Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com", "1234567890");
    person.setDateOfBirth(LocalDate.of(1991, 2, 2));
    assertEquals(LocalDate.of(1991, 2, 2), person.getDateOfBirth());
  }

  @Test
  public void testSetEmail() {
    Person person = new Person("John Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com", "1234567890");
    person.setEmail("jane.doe@example.com");
    assertEquals("jane.doe@example.com", person.getEmail());
  }

  @Test
  public void testSetPhoneNumber() {
    Person person = new Person("John Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com", "1234567890");
    person.setPhoneNumber("0987654321");
    assertEquals("0987654321", person.getPhoneNumber());
  }
}