package network.messages.game;

import network.messages.Message;
import network.messages.MessageType;

public class DistributeArmyMessage extends Message {
  /**
   * This message class sends and receive the amount of army and the territory to place.
   * 
   * @skaur
   */
  private static final long serialVersionUID = 1L;

  private int amount;// amount of army
  private int territoryId; // id of the territory
  private String color; // color of the player

  // initialize the amount of the army and the territory id.
  public DistributeArmyMessage(int amount, int territoryId) {
    super(MessageType.DISTRIBUTE_ARMY);
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
