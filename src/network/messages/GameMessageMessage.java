package network.messages;

/**
 * This class is used for sending and receiving the text message for the in-game chat.
 * 
 * @skaur
 */

public class GameMessageMessage extends Message {
 
  private static final long serialVersionUID = 1L;

  private String username;
  private String message;

  public GameMessageMessage(String username, String message) {
    super(MessageType.INGAME);
    this.username = username;
    this.message = message;

  }

  public String getUsername() {
    return this.username;
  }

  public String getMessage() {
    return this.message;
  }

}
