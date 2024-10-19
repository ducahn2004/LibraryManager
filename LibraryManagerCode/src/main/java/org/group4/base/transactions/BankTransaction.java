package org.group4.base.transactions;

public class BankTransaction extends FineTransaction {
  private String bankName;
  private String accountNumber;

  public BankTransaction(Fine fine, String bankName, String accountNumber) {
    super(fine);
    this.bankName = bankName;
    this.accountNumber = accountNumber;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public boolean processBankPayment() {
    // Implement bank validation logic here
    return validateBank();
  }

  private boolean validateBank() {
    // Placeholder for actual bank validation logic
    return true;
  }

}
