package org.group4.base.transactions;

import java.time.LocalDate;

/**
 * Quan ly cac giao dich phat.
 */
public class FineTransaction {
  private final Fine fine; // Phat.
  private final LocalDate creationDate; // Ngay tao giao dich.
  private boolean isCompleted; // Trang thai hoan thanh giao dich.

  /**
   * Tao giao dich phat moi.
   * @param fine Phat can thanh toan.
   */
  public FineTransaction(Fine fine) {
    this.fine = fine;
    this.creationDate = LocalDate.now();
    this.isCompleted = false;
  }
  
  public Fine getFine() {
    return fine;
  }


  public LocalDate getCreationDate() {
    return creationDate;
  }

  public boolean isCompleted() {
    return isCompleted;
  }

  /**
   * Hoan thanh giao dich.
   * @return true neu giao dich duoc hoan thanh, false neu giao dich da hoan thanh truoc do.
   */
  public boolean completeTransaction() {
    if (!isCompleted) {
      isCompleted = true;
      return true;
    }
    return false;
  }

  public boolean saveTransaction() {
    return true;
  }

  public boolean updateTransaction() {
    return true;
  }

  public boolean fetchTransaction(String transactionId) {
    return true;
  }

  public boolean fetchTransaction(String transactionId, String memberId) {
    return true;
  }

  public boolean fetchTransaction(String transactionId, String memberId, String bookItemBarcode) {
    return true;
  }

  public boolean fetchTransaction(String transactionId, String memberId, String bookItemBarcode, String fineId) {
    return true;
  }

  public boolean fetchTransaction(String transactionId, String memberId, String bookItemBarcode, String fineId, String fineAmount) {
    return true;
  }


  public boolean createFine(String memberId, String bookItemBarcode, long days) {
    return true;
  }

  public boolean calculateFine(String memberId, String bookItemBarcode, long days) {
    return true;
  }

  public boolean updateFine(String memberId, String bookItemBarcode, long days) {
    return true;
  }

  public boolean fetchFine(String memberId, String bookItemBarcode) {
    return true;
  }

  public boolean fetchFine(String memberId, String bookItemBarcode, String fineId) {
    return true;
  }

  public boolean fetchFine(String memberId, String bookItemBarcode, String fineId, String fineAmount) {
    return true;
  }

  public boolean fetchFine(String memberId, String bookItemBarcode, String fineId, String fineAmount, String creationDate) {
    return true;
  }

  public boolean fetchFine(String memberId, String bookItemBarcode, String fineId, String fineAmount, String creationDate, String isPaid) {
    return true;
  }

  public boolean payFine(String memberId, String fineId) {
    return true;
  }



}
