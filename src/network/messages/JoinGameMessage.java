package network.messages;
/**
 * 
 * @author sandeepkaur
 *
 */
public class JoinGameMessage extends Message {

  private static final long serialVersionUID = 1L;
  private String name;

  public JoinGameMessage(String name) {
    super(MessageType.JOIN);
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

}
