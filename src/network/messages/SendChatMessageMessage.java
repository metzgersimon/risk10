package network.messages;

public class SendChatMessageMessage extends Message {
  /**
   * This class is used for sending and receiving the text message for the game lobby chat.
   * 
   * @skaur
   */

  private static final long serialVersionUID = 1L;

  private String username; // player class
  private String message;

  public SendChatMessageMessage(String username, String message) {
    super(MessageType.BROADCAST);
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
