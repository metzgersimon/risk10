package network.messages.game;

import network.messages.Message;
import network.messages.MessageType;
/**
 * send message about the selected territory by the current player
 * @author skaur
 */
public class SelectInitialTerritoryMessage extends Message {

  private static final long serialVersionUID = 1L;
  
  private int territoryID;
  
  public SelectInitialTerritoryMessage(int territoryID) {
    super(MessageType.INITIAL_TERRITORY);
  }
  
  public int getTerritoryID() {
    return this.territoryID;
  }


}
