package org.group4.base.transactions;

/**
 * Giao dich bang check.
 */
public class CheckTransaction extends FineTransaction {
  private String bankName; // Ten ngan hang.
  private String checkNumber; // So cua check.

  /**
   * Tao giao dich bang check moi.
   * @param fine Phat can thanh toan.
   * @param bankName Ten ngan hang.
   * @param checkNumber So cua check.
   */
  public CheckTransaction(Fine fine, String bankName, String checkNumber) {
    super(fine);
    this.bankName = bankName;
    this.checkNumber = checkNumber;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getCheckNumber() {
    return checkNumber;
  }

  public void setCheckNumber(String checkNumber) {
    this.checkNumber = checkNumber;
  }

  
}
