package org.group4.base;

import java.time.LocalDate;

public class Member {
  private LocalDate dateOfMembership;
  private int totalBooksCheckedOut;

  public int getTotalBooksCheckedOut() {
    return totalBooksCheckedOut;
  }
}
