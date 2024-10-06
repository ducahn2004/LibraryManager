package org.group4.base.catalog;

import java.util.ArrayList;
import java.util.List;
import org.group4.base.entities.Book;

/**
 * Quan ly cac ke sach trong thu vien.
 * Ke sach la noi chua cac cuon sach.
 * Co the them, xoa sach khoi ke sach.
 * Co the tim kiem sach trong ke sach.
 * Co the xem thong tin cua ke sach.
 * Khi co sach moi, sach se duoc them vao ke sach.
 * Khi co sach bi mat, sach se duoc xoa khoi ke sach.
 */
public class Rack {
  private int number; // So thu tu cua ke sach.
  private String locationIdentifier; // Ma so vi tri cua ke sach.
  private List<Book> bookItems; // Danh sach cac cuon sach trong ke sach.

  /**
   * Tao mot ke sach moi.
   * @param number So thu tu cua ke sach.
   * @param locationIdentifier Ma so vi tri cua ke sach.
   */
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
