package network.messages.game;

import network.messages.Message;
import network.messages.MessageType;

public class FurtherDistributeArmyMessage extends Message {
  /**
   * This message class sends and receives the amount of army to place at the selected territory.
   * 
   * @skaur
   */
  private static final long serialVersionUID = 1L;

  private int amount; // amount of army
  private int territoryId; // territoryId where army should be placed
  private String color;// color the player

  public FurtherDistributeArmyMessage(int amount, int territoryId) {
    super(MessageType.FURTHER_DISTRIBUTE_ARMY);
    this.amount = amount;
    this.territoryId = territoryId;
  }

  public int getAmount() {
    return this.amount;
  }

  public int getTerritoryId() {
    return this.territoryId;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getColor() {
    return this.color;
  }

}


