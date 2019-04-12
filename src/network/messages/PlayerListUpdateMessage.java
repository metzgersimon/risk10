package network.messages;

import game.Player;

public class PlayerListUpdateMessage extends Message {

  private static final long serialVersionUID = 1L;
  private Player player;
  public PlayerListUpdateMessage(Player p) {
    super(MessageType.PLAYER_LIST_UPDATE);
    this.player = p;
  }

  public Player getPlayer() {
    return this.player;
  }
  
}
