package network.messages.game;

import network.messages.Message;
import network.messages.MessageType;

public class TradeCardMessage extends Message {
  /**
   * This message class sends and receive the total amount of army to distribute and three selected
   * card IDs.
   * 
   * @liwang
   */
  private static final long serialVersionUID = 1L;

  private int amount;// total amount of army to distribute
  private int cardId1; // id of the card 1
  private int cardId2; // id of the card 2
  private int cardId3; // id of the card 3
  private String color; // color of the player

  /**
   * Constructor.
   * 
   * @param amount of army to distribute
   * @param cardId1
   * @param cardId2
   * @param cardId3
   */
  public TradeCardMessage(int amount, int cardId1, int cardId2, int cardId3) {
    super(MessageType.TRADE_CARD);
    this.amount = amount;
    this.cardId1 = cardId1;
    this.cardId2 = cardId2;
    this.cardId3 = cardId3;
  }

  public int getAmount() {
    return this.amount;
  }

  public int getCardId1() {
    return this.cardId1;
  }

  public int getCardId2() {
    return this.cardId2;
  }

  public int getCardId3() {
    return this.cardId3;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getColor() {
    return this.color;
  }

}
