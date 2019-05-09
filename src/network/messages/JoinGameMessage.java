package network.messages;

/**
 * This message class represents the join game request of client/player.
 * 
 * @author skaur
 *
 */
public class JoinGameMessage extends Message {

  private static final long serialVersionUID = 1L;

  private String name; // name of the client/player who wants to join the game

  public JoinGameMessage(String name) {
    super(MessageType.JOIN);
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

}
