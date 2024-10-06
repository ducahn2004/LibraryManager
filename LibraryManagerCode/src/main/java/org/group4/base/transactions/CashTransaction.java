package org.group4.base.transactions;

/**
 * Giao dich bang tien mat.
 */
public class CashTransaction extends FineTransaction {
  double cashTendered; // So tien khach tra.

  /**
   * Tao giao dich bang tien mat moi.
   * @param fine Phat can thanh toan.
   * @param cashTendered So tien khach tra.
   */
  public CashTransaction(Fine fine, double cashTendered) {
    super(fine);
    this.cashTendered = cashTendered;
  }

  public double getCashTendered() {
    return cashTendered;
  }

  public void setCashTendered(double cashTendered) {
    this.cashTendered = cashTendered;
  }

}
