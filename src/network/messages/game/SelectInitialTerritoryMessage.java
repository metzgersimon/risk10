package network.messages.game;

import game.Territory;
import network.messages.Message;
import network.messages.MessageType;
/**
 * send message about the selected territory by the current player
 * @author skaur
 */
public class SelectInitialTerritoryMessage extends Message {

  private static final long serialVersionUID = 1L;
  
  private Territory territory;
  
  public SelectInitialTerritoryMessage(Territory territory) {
    super(MessageType.INITIAL_TERRITORY);
  }
  
  public Territory getTerritoryID() {
    return this.territory;
  }


}
