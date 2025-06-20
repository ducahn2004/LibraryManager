package org.group4.model.book;

/**
 * Represents a rack where books are stored in a library.
 * <p>Each rack has a unique number and location identifier to help in locating</p>
 */
public class Rack {

  private final int numberRack;
  private String locationIdentifier;

  /**
   * Constructs a {@code Rack} instance with a rack number and location identifier.
   *
   * @param numberRack the unique number of the rack
   * @param locationIdentifier the identifier of the rack's location within the library
   */
  public Rack(int numberRack, String locationIdentifier) {
    this.numberRack = numberRack;
    this.locationIdentifier = locationIdentifier;
  }

  /**
   * Retrieves the rack number.
   *
   * @return the rack number as an integer
   */
  public int getNumberRack() {
    return numberRack;
  }

  /**
   * Retrieves the location identifier for the rack.
   *
   * @return the location identifier as a String
   */
  public String getLocationIdentifier() {
    return locationIdentifier;
  }

  /**
   * Sets the location identifier for the rack.
   *
   * @param locationIdentifier the new location identifier for the rack
   */
  public void setLocationIdentifier(String locationIdentifier) {
    this.locationIdentifier = locationIdentifier;
  }

  @Override
  public String toString() {
    return "Rack{" +
        "numberRack = " + numberRack +
        ", locationIdentifier = '" + locationIdentifier + '\'' +
        '}';
  }
}
