package org.group4.base.transactions;

public class CashTransaction extends FineTransaction {
  double cashTendered;

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
