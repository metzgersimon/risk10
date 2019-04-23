package network.messages.game;

import network.messages.Message;
import network.messages.MessageType;

public class FurtherDistributeArmyMessage extends Message{
  /**
   * This message class sends and receive the amount of army and the territory to place
   * 
   * @skaur
   */
  private static final long serialVersionUID = 1L;

  private int amount;
  private int territoryID;
  private String color;

  public FurtherDistributeArmyMessage(int amount, int territoryID) {
    super(MessageType.FURTHER_DISTRIBUTE_ARMY);
    this.amount = amount;
    this.territoryID = territoryID;
  }

  public int getAmount() {
    return this.amount;
  }

  public int getTerritoryID() {
    return this.territoryID;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getColor() {
    return this.color;
  }

}


