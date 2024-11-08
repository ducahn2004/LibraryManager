package org.group4.base.catalog;

public class Rack {
  private int numberRank;
  private final String locationIdentifier;

  public Rack(int numberRank, String locationIdentifier) {
    this.numberRank = numberRank;
    this.locationIdentifier = locationIdentifier;
  }

  public String getLocationIdentifier() {
    return locationIdentifier;
  }

}
