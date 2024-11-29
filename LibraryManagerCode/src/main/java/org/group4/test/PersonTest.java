package org.group4.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import org.group4.model.user.Person;
import org.junit.Before;
import org.junit.Test;

public class PersonTest {

  private Person person;

  /**
   * Concrete subclass of Person for testing purposes.
   */
  private class PersonTestSubclass extends Person {

    public PersonTestSubclass(String name, LocalDate dateOfBirth, String email,
        String phoneNumber) {
      super(name, dateOfBirth, email, phoneNumber);
    }
  }

  @Before
  public void setUp() {
    // Create an instance of the Person subclass with sample data
    person = new PersonTestSubclass("John Doe", LocalDate.of(1990, 1, 1),
        "john.doe@example.com", "123-456-7890");
  }

  @Test
  public void testGetName() {
    assertEquals("John Doe", person.getName());
  }

  @Test
  public void testGetDateOfBirth() {
    assertEquals(LocalDate.of(1990, 1, 1), person.getDateOfBirth());
  }

  @Test
  public void testGetEmail() {
    assertEquals("john.doe@example.com", person.getEmail());
  }

  @Test
  public void testGetPhoneNumber() {
    assertEquals("123-456-7890", person.getPhoneNumber());
  }

  @Test
  public void testSetName() {
    person.setName("Jane Doe");
    assertEquals("Jane Doe", person.getName());
  }

  @Test
  public void testSetDateOfBirth() {
    person.setDateOfBirth(LocalDate.of(1995, 5, 15));
    assertEquals(LocalDate.of(1995, 5, 15), person.getDateOfBirth());
  }

  @Test
  public void testSetEmail() {
    person.setEmail("jane.doe@example.com");
    assertEquals("jane.doe@example.com", person.getEmail());
  }

  @Test
  public void testSetPhoneNumber() {
    person.setPhoneNumber("987-654-3210");
    assertEquals("987-654-3210", person.getPhoneNumber());
  }
}

