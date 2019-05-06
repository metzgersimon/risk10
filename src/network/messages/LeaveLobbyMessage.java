package network.messages;

import game.Player;

public class LeaveLobbyMessage extends Message {
  /**
   * @skaur
   */
  private static final long serialVersionUID = 1L;

  private Player p;

  public LeaveLobbyMessage(Player p) {
    super(MessageType.LEAVE_LOBBY);
    // TODO Auto-generated constructor stub
    this.p = p;
  }

  public Player getPlayer() {
    return this.p;
  }

}
