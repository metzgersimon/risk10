package network.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import game.Player;
import game.PlayerColor;
import gui.Gui;
import gui.HostGameLobbyController;
import gui.JoinGameLobbyController;
import main.Main;
import network.messages.JoinGameMessage;
import network.messages.LeaveGameMessage;
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
  private HostGameLobbyController hostLobbyController;
  private ArrayList<Player> players = new ArrayList<Player>();
  
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
   * 
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
  
 

  public void setHostController(HostGameLobbyController controller) {
    this.hostLobbyController = controller;
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
            //send message to all clients and show in join game lobby
            this.sendMessagesToallClients(message);
            //show message in host game lobby
            this.server.getHostLobbyController().showMessage(content);
            System.out.println(
                "Message from client with the content " + content + " sent to all clients");
            // gui.HostGameLobbyController.showMessage(content);
            break;
          case SEND:
            break;

          case LEAVE: handleLeaveGameMessage((LeaveGameMessage) message);
            break;
          case JOIN: handleJoinGame((JoinGameMessage)message);
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
 /**
  * This methods is called whenever client is connected to the server
  * This method update the list of players in the gamelobby
  * @author skaur
  * @param message
  */
  public void handleJoinGame(JoinGameMessage message) {
    Player player = message.getPlayer();
    this.players.add(player);
    if (this.server.getHostLobbyController() != null) {
      this.server.getHostLobbyController().updateList(player);
    }
  }
  
  public void handleLeaveGameMessage(LeaveGameMessage message){
    //TODO
    //remove the player from the list
    //send message to all the client to leave the game lobby
    //if in the gamelobby then only remove the person from list
  }
 
  public ArrayList<Player> getPlayer(){
    return this.players;
  }

}

