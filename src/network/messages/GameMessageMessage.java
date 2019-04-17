package network.messages;


public class GameMessageMessage extends Message {

  private static final long serialVersionUID = 1L;

  private String username;
  private String message;

  public GameMessageMessage(String username, String message) {
    super(MessageType.INGAME);
    this.message = message;

  }

  public String getUsername() {
    return this.username;
  }

  public String getMessage() {
    return this.message;
  }

}
