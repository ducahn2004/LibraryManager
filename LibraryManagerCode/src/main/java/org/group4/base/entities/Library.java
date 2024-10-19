package org.group4.base.entities;

import java.util.ArrayList;
import java.util.List;
import org.group4.base.books.BookItem;

public class Library {
  private String name;
  private Address address;
  private final List<BookItem> bookItems;

  // Constructor
  public Library(String name, Address address) {
    this.name = name;
    this.address = address;
    this.bookItems = new ArrayList<>();
  }

  // Getter
  public String getName() {
    return name;
  }

  public Address getAddress() {
    return address;
  }

  public List<BookItem> getBookItems() {
    return bookItems;
  }

  // Setter
  public void setName(String name) {
    this.name = name;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

}
