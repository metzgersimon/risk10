package network.messages;

import game.Player;

public class JoinGameMessage extends Message {

  private static final long serialVersionUID = 1L;
  private Player player;

  public JoinGameMessage(Player player) {
    super(MessageType.JOIN);
    this.player = player;

  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

}
