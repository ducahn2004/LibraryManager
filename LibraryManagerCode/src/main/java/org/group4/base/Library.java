package org.group4.base;

import java.util.ArrayList;
import java.util.List;

public class Library {
  private String name;
  private Address address;
  private List<BookItem> bookItems;

  public Library(String name, Address address) {
    this.name = name;
    this.address = address;
    this.bookItems = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public List<BookItem> getBookItems() {
    return bookItems;
  }

  public void setBookItems(List<BookItem> bookItems) {
    this.bookItems = bookItems;
  }

  public void addBookItem(BookItem bookItem) {
    bookItems.add(bookItem);
  }

  public void removeBookItem(BookItem bookItem) {
    bookItems.remove(bookItem);
  }

  public int getTotalBooks() {
    return bookItems.size();
  }

}
