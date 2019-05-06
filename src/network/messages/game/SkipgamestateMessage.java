package network.messages.game;

import game.GameState;
import network.messages.Message;
import network.messages.MessageType;
/**
 * 
 * @author qiychen
 *
 */
public class SkipgamestateMessage extends Message {
  private GameState gameState;
  private String color;
  private static final long serialVersionUID = 1L;

  public SkipgamestateMessage(GameState gameState) {
    super(MessageType.SKIP);
    this.gameState = gameState;
  }

  public GameState getGameState() {
    return gameState;
  }

  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }
  
  public String getColor() {
    return this.color;
  }
  public void setColor(String color) {
    this.color=color;
  }
}
