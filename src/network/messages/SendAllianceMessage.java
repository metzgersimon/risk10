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
  private String playerName;
  private String sender;
  private static final long serialVersionUID = 1L;

  public SendAllianceMessage(String playerName, String content, String sender) {
    super(MessageType.ALLIANCE);
    this.playerName = playerName;
    this.content = content;
    this.sender=sender;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }
}
