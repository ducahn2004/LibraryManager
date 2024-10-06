package org.group4.base.transactions;

/**
 * Dai dien cho tien phat khi tra sach tre.
 */
public class Fine {
  private double amount; // So tien phat.

  /**
   * Tao tien phat moi.
   * @param amount So tien phat.
   */
  public Fine(double amount) {
    this.amount = amount;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }


}
