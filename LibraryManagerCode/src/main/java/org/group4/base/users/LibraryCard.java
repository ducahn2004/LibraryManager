package org.group4.base.users;

import java.time.LocalDate;

/**
 * Quan ly the thu vien.
 */
public class LibraryCard {
  private String cardNumber; // Ma so the.
  private String barcode; // Ma vach.
  private LocalDate issueDate; // Ngay cap the.
  private boolean active; // Trang thai hoat dong cua the.

  /**
   * Tao the thu vien moi.
   * @param cardNumber Ma so the.
   * @param barcode Ma vach.
   * @param issueDate Ngay cap the.
   * @param active Trang thai hoat dong cua the.
   */
  public LibraryCard(String cardNumber, String barcode, LocalDate issueDate, boolean active) {
    this.cardNumber = cardNumber;
    this.barcode = barcode;
    this.issueDate = issueDate;
    this.active = active;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void deactive() {
    this.active = false;
  }

  public void active() {
    this.active = true;
  }

  public boolean isExpired() {
    return LocalDate.now().isAfter(issueDate.plusYears(1));
  }

}
