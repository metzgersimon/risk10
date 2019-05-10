package network.messages;

import game.Player;
/**
 * This message class represents the leave lobby message of the player.
 * 
 * @skaur
 */
public class LeaveLobbyMessage extends Message {
  
  private static final long serialVersionUID = 1L;

  private Player player;

  /**
   * Constructor.
   * @param player who decides to leave the game lobby.
   */
  public LeaveLobbyMessage(Player player) {
    super(MessageType.LEAVE_LOBBY);
    this.player = player;
  }

  /**
   * The player who leaves the game lobby.
   */
  public Player getPlayer() {
    return this.player;
  }

}
