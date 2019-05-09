package network.messages;

import game.Player;

public class JoinGameResponseMessage extends Message {
  /**
   * This message class represents the response of the server after the server receives the join
   * game response message from the player/client.
   * 
   * @skaur
   */
  private static final long serialVersionUID = 1L;

  private Player player;

  public JoinGameResponseMessage(Player player) {
    super(MessageType.JOIN_REPONSE);
    this.player = player;
  }

  // send the response to the player with the player instance as the parameter
  public Player getPlayer() {
    return this.player;
  }
}
