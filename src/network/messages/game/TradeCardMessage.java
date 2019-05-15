package network.messages.game;

import network.messages.Message;
import network.messages.MessageType;

public class TradeCardMessage extends Message {
  /**
   * This message class sends and receive the amount of army and the territory to place.
   * 
   * @skaur
   */
  private static final long serialVersionUID = 1L;

  private int amount;// amount of army
  private int cardId1; // id of the territory
  private int cardId2; // id of the territory
  private int cardId3; // id of the territory
  private String color; // color of the player

  /**
   * Constructor.
   * 
   * @param amount of army to place
   * @param territoryId of the territory selected to place the army
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
