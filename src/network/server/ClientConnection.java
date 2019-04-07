package network.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import game.Player;
import gui.Gui;
import gui.JoinGameLobbyController;
import main.Main;
import network.messages.Message;
import network.messages.MessageType;
import network.messages.SendChatMessageMessage;

public class ClientConnection extends Thread {

  private InetAddress address;
  private int port;
  private Socket socket;
  private ObjectInputStream fromClient;
  private ObjectOutputStream toClient;
  private boolean active;
  private Player player;
  private Server server;

  public ClientConnection(Socket s) {
    this.socket = s;
    this.active = true;
    try {
      this.toClient = new ObjectOutputStream(s.getOutputStream());
      this.fromClient = new ObjectInputStream(s.getInputStream());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  /**
   * Constructor
   * @author qiychen
   * @param s
   * @param server
   */
  public ClientConnection(Socket s, Server server) {
    this.server = server;
    this.socket = s;
    this.active = true;
    try {
      this.toClient = new ObjectOutputStream(s.getOutputStream());
      this.fromClient = new ObjectInputStream(s.getInputStream());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void run() {

    transact();
  }

  /**
   * send messages to client
   */
  public void sendMessage(Message m) {
    try {
      toClient.writeObject(m);
      // to test
      // if(m.getType()==MessageType.BROADCAST) {
      // joinLobby.addMessage(((SendChatMessageMessage)m).getMessage());
      // joinLobby=new JoinGameLobbyController();

      // joinLobby.addMessage(((SendChatMessageMessage)m).getMessage());
      // }

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * send message to all clients
   * 
   * @author qiychen
   * @param m
   */
  public void sendMessagesToallClients(Message m) {
    for (int i = 0; i < server.getConnections().size(); i++) {
      ClientConnection c = server.getConnections().get(i);
      c.sendMessage(m);
    }
  }

  /**
   * handle incoming messages from clients
   */
  public void transact() {
    System.out.println("connection works");
    while (active) {
      try {
        Message message = (Message) this.fromClient.readObject();
        switch (message.getType()) {
          case BROADCAST:
            String name = ((SendChatMessageMessage) message).getUsername();
            String content = ((SendChatMessageMessage) message).getMessage();
            this.sendMessagesToallClients(message);
            System.out.println(
                "Message from client with the content " + content + " sent to all clients");
            gui.HostGameLobbyController.showMessage(content);
            break;
          case SEND:
            break;

          case LEAVE:
            break;
        }
      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  /**
   * disconnect the connection
   */
  public void disconnect() {
    try {
      this.toClient.close();
      this.fromClient.close();
      this.socket.close();
      this.active = false;
      System.out.println("Protocol ended");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}

