package org.group4.base.users;

import java.time.LocalDate;
import org.group4.base.enums.AccountStatus;
import org.group4.base.books.BookItem;
import org.group4.base.enums.BookStatus;

public class Member extends Account {
  private LocalDate dateOfMembership;
  private int totalBooksCheckedOut;

  public Member() {
    super(null, null, AccountStatus.NONE, null);
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


  public void reserveBookItem(BookItem book) {
    book.setStatus(BookStatus.NONE);
  }

  public boolean checkoutBookItem(BookItem book) {
    if (this.getTotalBooksCheckedOut() >= 5) {
      return false;
    }
    return true;
  }

}
