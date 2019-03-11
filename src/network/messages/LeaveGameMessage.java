package network.messages;

public class LeaveGameMessage extends Message {

	private static final long serialVersionUID = 1L;
	private String username;

	public LeaveGameMessage(String username) {
		super(MessageType.LEAVE);
		this.username = username;
		
	}

public String getUsername(){
	return this.username;
}
	
}
