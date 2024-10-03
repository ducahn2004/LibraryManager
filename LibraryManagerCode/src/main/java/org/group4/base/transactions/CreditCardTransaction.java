package org.group4.base.transactions;

public class CreditCardTransaction extends FineTransaction {
  private String nameOnCard;


  public CreditCardTransaction(Fine fine, String nameOnCard) {
    super(fine);
    this.nameOnCard = nameOnCard;
  }

  public String getNameOnCard() {
    return nameOnCard;
  }

  public void setNameOnCard(String nameOnCard) {
    this.nameOnCard = nameOnCard;
  }
  
}
