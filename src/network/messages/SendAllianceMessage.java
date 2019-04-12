package network.messages;

import game.Player;

/**
 * Player can form alliance by sending message to another player
 * 
 * @author qiychen
 *
 */
public class SendAllianceMessage extends Message {
  private Player player;
  private String content;
  private static final long serialVersionUID = 1L;

  public SendAllianceMessage(Player player, String content) {
    super(MessageType.ALLIANCE);
    this.player = player;
    this.content = content;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }
}
