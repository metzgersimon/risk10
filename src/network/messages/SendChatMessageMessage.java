package network.messages;

/**
 * This class is used for sending and receiving the text message for the game lobby chat.
 * 
 * @skaur
 */

public class SendChatMessageMessage extends Message {

  private static final long serialVersionUID = 1L;

  private String username; // player class
  private String message;

  /**
   * Constructor.
   * 
   * @param username of the player who sends the message
   * @param message sent by the player
   */
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
