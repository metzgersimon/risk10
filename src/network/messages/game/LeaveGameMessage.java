package network.messages.game;

import network.messages.Message;
import network.messages.MessageType;

/**
 * This message class sends the leave message from a client to every client connected to the game.
 * 
 * @author skaur
 *
 */
public class LeaveGameMessage extends Message {

  private static final long serialVersionUID = 1L;
  private String username;
  private String color;
  private boolean leaveLobby;
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
  
  public void setLeaveLobby(boolean leaveLobby) {
    this.leaveLobby = leaveLobby;
  }
  
  public boolean getLeaveLobby() {
    return this.leaveLobby;
  }
}
