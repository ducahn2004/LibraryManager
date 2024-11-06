package org.group4.base.users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.group4.base.books.BookLending;
import org.group4.base.enums.Address;

public class Member extends Person {
  private final String memberId;
  private final List<BookLending> bookLendings = new ArrayList<>();

  public Member(String name, LocalDate dateOfBirth, Address address, String email, String phoneNumber, String memberId) {
    super(name, dateOfBirth, address, email, phoneNumber);
    this.memberId = memberId;
  }

  // Getters
  public String getMemberId() {
    return memberId;
  }

  public List<BookLending> getBookLendings() {
    return bookLendings;
  }

  public BookLending getBookLending(String barcode) {
    return bookLendings.stream()
        .filter(lending -> lending.getBookItem().getBarcode().equals(barcode))
        .findFirst()
        .orElse(null);
  }

  public boolean addBookLending(BookLending bookLending) {
    return bookLendings.add(bookLending);
  }

  public boolean removeBookLending(BookLending bookLending) {
    return bookLendings.remove(bookLending);
  }

}
