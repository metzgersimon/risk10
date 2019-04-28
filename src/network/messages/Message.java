package network.messages;

import game.Player;
import java.io.Serializable;



public abstract class Message implements Serializable {

  private static final long serialVersionUID = 1L;

  private MessageType type;
  private String content;// content of the message
  private Object response; // the answer of the message
  private Player player;

  public Message(MessageType type) {
    this.type = type;
  }

  public MessageType getType() {
    return this.type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Object getResponce() {
    return response;
  }

  public void setResponse(Object response) {
    this.response = response;
  }

  public Player getPlayer() {
    return this.player;
  }
}
