package network.messages;

import game.Player;

public class JoinGameResponseMessage extends Message {
  /**
   * 
   */
  private static final long serialVersionUID = 1L; 

  private Player player;
  public JoinGameResponseMessage(Player player) {
    super(MessageType.JOIN_REPONSE);
    this.player = player;
  }
  
  public Player getPlayer() {
    return this.player;
  }
}
