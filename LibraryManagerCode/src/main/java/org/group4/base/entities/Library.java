package org.group4.base.entities;

import java.util.ArrayList;
import java.util.List;
import org.group4.base.books.BookItem;

/**
 * Dai dien cho thu vien, quan ly danh sach cac sach.
 */
public class Library {
  private String name; // Ten cua thu vien.
  private Address address; // Dia chi cua thu vien.
  private List<BookItem> bookItems; // Danh sach cac sach trong thu vien.

  /**
   * Tao mot thu vien moi.
   * @param name Ten cua thu vien.
   * @param address Dia chi cua thu vien.
   */
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
