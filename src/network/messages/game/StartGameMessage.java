package network.messages.game;

import game.Player;
import java.util.ArrayList;
import network.messages.Message;
import network.messages.MessageType;

/**
 * Message class which says the game has started and sends player list of every client/player who
 * has joined the game.
 * 
 * @skaur
 **/

public class StartGameMessage extends Message {

  private static final long serialVersionUID = 1L;

  /**
   * List of players who have joined the game lobby.
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
