package org.group4.base.users;

import java.time.LocalDate;

public class LibraryCard {
  private String cardNumber;
  private String barcode;
  private LocalDate issueDate;
  private boolean active;


  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getBarCodeNumber() {
    return barcode;
  }

  public void setBarCodeNumber(String barCodeNumber) {
    this.barcode = barCodeNumber;  // Sửa lại: gán barCodeNumber vào barcode
  }

  public LocalDate getIssuedAt() {
    return issueDate;
  }

  public void setIssuedAt(LocalDate issuedAt) {
    this.issueDate = issuedAt;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
