package org.group4.base.catalog;

import java.util.ArrayList;
import java.util.List;
import org.group4.base.entities.Book;

public class Rack {
  private int number;
  private String locationIdentifier;
  private List<Book> bookItems;

  public Rack(int number, String locationIdentifier) {
    this.number = number;
    this.locationIdentifier = locationIdentifier;
    this.bookItems = new ArrayList<>();
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getLocationIdentifier() {
    return locationIdentifier;
  }

  public void setLocationIdentifier(String locationIdentifier) {
    this.locationIdentifier = locationIdentifier;
  }

  public List<Book> getBookItems() {
    return bookItems;
  }

  public void setBookItems(List<Book> bookItems) {
    this.bookItems = bookItems;
  }

}
