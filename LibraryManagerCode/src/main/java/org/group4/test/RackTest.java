package org.group4.test;

import org.group4.model.book.Rack;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RackTest {

  private Rack rack;

  @Before
  public void setUp() {
    rack = new Rack(1, "A1");
  }

  @Test
  public void testGetNumberRack() {
    assertEquals(1, rack.getNumberRack());
  }

  @Test
  public void testGetLocationIdentifier() {
    assertEquals("A1", rack.getLocationIdentifier());
  }

  @Test
  public void testSetLocationIdentifier() {
    rack.setLocationIdentifier("B2");
    assertEquals("B2", rack.getLocationIdentifier());
  }

  @Test
  public void testToString() {
    String expected = "Rack{numberRack = 1, locationIdentifier = 'A1'}";
    assertEquals(expected, rack.toString());

    rack.setLocationIdentifier("B2");
    expected = "Rack{numberRack = 1, locationIdentifier = 'B2'}";
    assertEquals(expected, rack.toString());
  }

  @Test
  public void testConstructorInitialization() {
    Rack newRack = new Rack(5, "C3");
    assertEquals(5, newRack.getNumberRack());
    assertEquals("C3", newRack.getLocationIdentifier());
  }
}
