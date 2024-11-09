package org.group4.base.catalog;

public class Rack {
  private int numberRack;
  private final String locationIdentifier;

  public Rack(int numberRack, String locationIdentifier) {
    this.numberRack = numberRack;
    this.locationIdentifier = locationIdentifier;
  }

  public String getLocationIdentifier() {
    return locationIdentifier;
  }

}
