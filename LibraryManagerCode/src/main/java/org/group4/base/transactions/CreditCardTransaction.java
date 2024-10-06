package org.group4.base.transactions;

/**
 * Giao dich bang the tin dung.
 */
public class CreditCardTransaction extends FineTransaction {
  private String nameOnCard; // Ten tren the

  /**
   * Tao giao dich bang the tin dung moi.
   * @param fine Phat can thanh toan.
   * @param nameOnCard Ten tren the.
   */
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

  public void processCreditCardPayment() {
    // TODO: Implement this method.
  }
}
