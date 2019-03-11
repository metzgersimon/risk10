package network.messages;


public class GameLobbyMessageMessage extends Message {
	
private static final long serialVersionUID = 1L;
	
	private String author;
	private String message;
	public GameLobbyMessageMessage(String username, String message) {
		super(MessageType.LOBBY);
	
		this.message = message;
		
	}
	public String getAuthor() {
		return author;
	}
	
	public String getMessage() {
		return this.message;
	}

}
