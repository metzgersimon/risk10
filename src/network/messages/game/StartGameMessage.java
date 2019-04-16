package network.messages.game;

import game.Game;
import network.messages.Message;
import network.messages.MessageType;

/** Message which says the game has started**/
public class StartGameMessage extends Message {

  private static final long serialVersionUID = 1L;
  /**
   * @skaur
   */
  private Game g;
  public StartGameMessage(Game g) {
    super(MessageType.START_GAME);
    this.g = g ;
  }

   public Game getGame() {
    return this.g;
  }

  
}
