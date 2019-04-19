package network.messages.game;

import game.Territory;
import network.messages.Message;
import network.messages.MessageType;

/**
 * send message about the selected territory by the current player and send the color of the player
 * which is a identifier of the player
 * 
 * @author skaur
 */
public class SelectInitialTerritoryMessage extends Message {

  private static final long serialVersionUID = 1L;

  private int territoryID;
  private String color;

  public SelectInitialTerritoryMessage(int territoryID) {
    super(MessageType.INITIAL_TERRITORY);
    this.territoryID = territoryID;
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
