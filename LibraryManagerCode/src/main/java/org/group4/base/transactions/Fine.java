package org.group4.base.transactions;

import java.time.LocalDate;

import org.group4.base.books.BookItem;
import org.jetbrains.annotations.NotNull;

public class Fine {
  private double amount;

  public Fine() {
    this.amount = 0;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

 public void calculateFine(@NotNull BookItem bookItem) {
    LocalDate dueDate = bookItem.getDueDate();
    double overdueDays = LocalDate.now().toEpochDay() - dueDate.toEpochDay();
    if (overdueDays > 0) {
        setAmount(overdueDays * 20000);
    } else {
        setAmount(0);
    }
}

  public void payFine(@NotNull FineTransaction transaction) {
    if (transaction.processFinePayment()) {
      System.out.println("Fine paid successfully.");
    } else {
      System.out.println("Fine payment failed.");
    }
  }

}
