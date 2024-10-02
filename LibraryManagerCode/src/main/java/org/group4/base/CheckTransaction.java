package org.group4.base;

import java.time.LocalDate;

public class CheckTransaction extends FineTransaction {
  private String bankName;
  private String checkNumber;

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
