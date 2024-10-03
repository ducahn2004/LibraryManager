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


}
