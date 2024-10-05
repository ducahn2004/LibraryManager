package org.group4.base.transactions;

import java.time.LocalDate;

public class FineTransaction {
  private Fine fine;
  private LocalDate creationDate;
  private boolean isCompleted;


  public FineTransaction(Fine fine) {
    this.fine = fine;
    this.creationDate = creationDate;
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
