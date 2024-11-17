package org.group4.module.transactions;

import java.time.LocalDate;

public class FineTransaction {
  private final Fine fine;
  private final LocalDate creationDate;

  public FineTransaction(Fine fine) {
    this.fine = fine;
    this.creationDate = LocalDate.now();
  }

  public Fine getFine() {
    return fine;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public boolean payFine(double amount) {
    return !(fine.getAmount() > amount);
  }
}