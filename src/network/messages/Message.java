package network.messages;

import java.io.Serializable;


public class Message implements Serializable {

  private static final long serialVersionUID = 1L;

  private MessageType type;
  private String content;// content of the message
  private Object response; //  the answer of the message

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


}
