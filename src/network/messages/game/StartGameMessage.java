package network.messages.game;

import java.util.ArrayList;
import game.Player;
import network.messages.Message;
import network.messages.MessageType;

/**
 * Message which says the game has started and sends player list every client
 * 
 * @skaur
 **/
public class StartGameMessage extends Message {

  private static final long serialVersionUID = 1L;
  
  /**
   * List of players who have joined the game lobby
   */
  private ArrayList<Player> playerList;

  public StartGameMessage(ArrayList<Player> playerList) {
    super(MessageType.START_GAME);
    this.playerList = playerList;
  }

  public ArrayList<Player> getPlayerList() {
    return this.playerList;
  }

}
