package network.messages.game;

import network.messages.Message;
import network.messages.MessageType;

/**
 * This message class sends message about the selected territory by the current player and send the
 * color of the player which is a identifier of the player.
 * 
 * @author skaur
 */
public class SelectInitialTerritoryMessage extends Message {

  private static final long serialVersionUID = 1L;

  private int territoryId;
  private String color;

  public SelectInitialTerritoryMessage(int territoryId) {
    super(MessageType.INITIAL_TERRITORY);
    this.territoryId = territoryId;
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
