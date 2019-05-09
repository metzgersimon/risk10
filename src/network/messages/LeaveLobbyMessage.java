package network.messages;

import game.Player;

public class LeaveLobbyMessage extends Message {
  /**
   * This message class represents the leave lobby message of the player.
   * 
   * @skaur
   */
  private static final long serialVersionUID = 1L;

  private Player p;

  public LeaveLobbyMessage(Player p) {
    super(MessageType.LEAVE_LOBBY);
    // TODO Auto-generated constructor stub
    this.p = p;
  }

  /**
   * The player who leaves the game lobby.
   */
  public Player getPlayer() {
    return this.p;
  }

}
