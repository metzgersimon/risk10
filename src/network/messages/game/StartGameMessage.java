package network.messages.game;

import network.messages.Message;
import network.messages.MessageType;

/** Message which says the game has started**/
public class StartGameMessage extends Message {

  private static final long serialVersionUID = 1L;
  /**
   * @skaur
   */
  public StartGameMessage() {
    super(MessageType.START_GAME);
    
  }


  
}
