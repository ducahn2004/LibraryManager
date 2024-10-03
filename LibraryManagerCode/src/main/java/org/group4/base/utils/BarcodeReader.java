package org.group4.base.utils;

import java.time.LocalDate;
import org.group4.base.users.LibraryCard;

public class BarcodeReader {
  private String id;
  private LocalDate registereAt;
  private boolean active;

  public boolean isActive(LibraryCard card) {
    return active;
  }

}
