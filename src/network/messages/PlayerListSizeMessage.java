package network.messages;

public class PlayerListSizeMessage extends Message {

  private static final long serialVersionUID = -2001396726026314964L;

  private int size;
  
  public PlayerListSizeMessage() {
    super(MessageType.PLAYER_SIZE);   
  }

  public void setSize(int size) {
      this.size = size;
  }
  
  public int geSize(){
    return this.size;
  }
}
