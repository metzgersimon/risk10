package messages;


public class SendChatMessageMessage extends Message {

	
private static final long serialVersionUID = 1L;
    
	private String username; //player class
    private String message;
	public SendChatMessageMessage(String username, String message) {
		super(MessageType.BROADCAST);
		this.username = username;
		this.message = message;
	}

public String getUsername(){
	return this.username;
}

public String getMessage(){
	return this.message;
}
}
