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
    // TODO: Implement this method
    return true;
  }

  public boolean updateTransaction() {
    // TODO: Implement this method
    return true;
  }

  public boolean deleteTransaction() {
    // TODO: Implement this method
    return true;
  }

  public boolean sendReceipt() {
    // TODO: Implement this method
    return true;
  }

  public boolean processFinePayment() {
    // TODO: Implement this method
    return true;
  }

}
