package org.group4.base;

import java.time.LocalDate;

public class Member extends Account{
  private LocalDate dateOfMembership;
  private int totalBooksCheckedOut;

  public Member() {
    super(null, null, AccountStatus.None, null);
    this.dateOfMembership = dateOfMembership;
    this.totalBooksCheckedOut = totalBooksCheckedOut;
  }

  public LocalDate getDateOfMembership() {
    return dateOfMembership;
  }

  public void setDateOfMembership(LocalDate dateOfMembership) {
    this.dateOfMembership = dateOfMembership;
  }

  public int getTotalBooksCheckedOut() {
    return totalBooksCheckedOut;
  }

  public void setTotalBooksCheckedOut(int totalBooksCheckedOut) {
    this.totalBooksCheckedOut = totalBooksCheckedOut;
  }

  public int getTotalCheckedoutBooks() {
    return totalBooksCheckedOut;
  }

}
