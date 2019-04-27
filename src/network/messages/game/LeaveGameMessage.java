package network.messages.game;

import network.messages.Message;
import network.messages.MessageType;

/**
 * 
 * @author skaur
 *
 */
public class LeaveGameMessage extends Message {

  private static final long serialVersionUID = 1L;
  private String username;
  private String color;

  public LeaveGameMessage(String username) {
    super(MessageType.LEAVE);
    this.username = username;
  }

  public String getUsername() {
    return this.username;
  }

  public String getColor() {
    return this.color;
  }

  public void setColor(String color) {
    this.color = color;
  }
}
