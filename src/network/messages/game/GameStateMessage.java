package network.messages.game;

import game.GameState;
import network.messages.Message;
import network.messages.MessageType;

/**
 * 
 * @author qiychen
 *
 */
public class GameStateMessage extends Message {
  GameState gamestate;
  private static final long serialVersionUID = 1L;

  public GameStateMessage(GameState gamestate) {
    super(MessageType.GAMESTATE);
    this.gamestate = gamestate;
  }

  public GameState getGamestate() {
    return gamestate;
  }

  public void setGamestate(GameState gamestate) {
    this.gamestate = gamestate;
  }


}
