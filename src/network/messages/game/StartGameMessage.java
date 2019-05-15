package network.messages.game;

import game.Card;
import game.CardDeck;
import game.Player;
import java.util.ArrayList;
import java.util.LinkedList;
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
  private LinkedList<Card> cards;

  /**
   * Constructor.
   * 
   * @param playerList of the players
   * @param card list 
   */
  public StartGameMessage(ArrayList<Player> playerList,LinkedList<Card> cards) {
    super(MessageType.START_GAME);
    this.playerList = playerList;
    this.cards = cards;
  }

  public ArrayList<Player> getPlayerList() {
    return this.playerList;
  }

  public LinkedList<Card> getCardDeck() {
    return this.cards;
  }
  

}
