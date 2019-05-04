package network.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import game.Player;
import gui.controller.BoardController;
import gui.controller.HostGameLobbyController;
import java.util.ArrayList;
import main.Main;
import network.messages.JoinGameMessage;
import network.messages.JoinGameResponseMessage;
import network.messages.Message;
import network.messages.SendAllianceMessage;
import network.messages.SendChatMessageMessage;
import network.messages.game.AttackMessage;
import network.messages.game.FortifyMessage;
import network.messages.game.LeaveGameMessage;
import network.messages.game.LeaveGameResponseMessage;
import network.messages.game.SelectInitialTerritoryMessage;
import network.messages.game.StartGameMessage;

/**
 * This class represents the connection between the server and the client
 */

public class ClientConnection extends Thread {
  /**
   * Variables for connection with clients
   */
  private InetAddress address; // address of the server
  private int port; // port of the server
  private Socket socket;
  private ObjectInputStream fromClient;
  private ObjectOutputStream toClient;
  private boolean active;
  private Player player; // player who represents this client connection
  private Server server;
  private HostGameLobbyController hostLobbyController;
  private ArrayList<Player> players = new ArrayList<Player>(); // list of players joined
  private BoardController boardController;
  private String playerN;

  /**
   * Constructor
   * 
   * @author qiychen
   * @param socket
   */
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
   * @param socket
   * @param server
   */
  public ClientConnection(Socket s, Server server) {
    this.server = server;
    this.socket = s;
    this.active = true;
    this.boardController = server.getBoardController();
    try {
      this.toClient = new ObjectOutputStream(s.getOutputStream());
      this.fromClient = new ObjectInputStream(s.getInputStream());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**************************************************
   *                                                *
   *                Getter and Setter               *
   *                                                *
   *************************************************/

  public ArrayList<Player> getPlayer() {
    return this.players;
  }

  public String getPlayerName() {
    return this.playerN;
  }

  public InetAddress getAdress() {
    return this.address;
  }

  public int getPort() {
    return this.port;
  }

  public Player getPlaye() {
    return this.player;
  }

  public HostGameLobbyController getHostLobbyController() {
    return this.hostLobbyController;
  }

  public BoardController getBoardController() {
    return this.boardController;
  }

  public void setHostController(HostGameLobbyController controller) {
    this.hostLobbyController = controller;
  }
 
  
  /**************************************************
   *                                                *
   *     Send messages from server                  *
   *                                                *
   *************************************************/

  /**
   * server sends messages to client
   * 
   * @author qiychen
   */
  public void sendMessage(Message m) {
    try {
      toClient.writeObject(m);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * server sends message to all clients
   * 
   * @author qiychen
   * @param message
   */
  public void sendMessagesToallClients(Message m) {
    for (int i = 0; i < server.getConnections().size(); i++) {
      ClientConnection c = server.getConnections().get(i);
      c.sendMessage(m);
    }
  }
  
  /**************************************************
   *                                                 *
   * Process the messages received from the clients  *
   *                                                 *
   *************************************************/


  /**
   * handle incoming messages from clients
   */
  public void run() {
    System.out.println("connection works");
    while (active) {
      try {
        Message message = (Message) this.fromClient.readObject();
        switch (message.getType()) {
          case BROADCAST:
            receiveSendChatMessage((SendChatMessageMessage) message);
            break;
          case START_GAME:
            this.sendMessagesToallClients((StartGameMessage) message);
            break;
          case LEAVE:
            recieveLeaveMessage((LeaveGameMessage) message);
            break;
          case LEAVE_RESPONSE :
            recieveLeaveGameResponse((LeaveGameResponseMessage) message);
            break;
          case JOIN:
            handleJoinGame((JoinGameMessage) message);
            break;
          case ALLIANCE:
            handleAllianceMessage((SendAllianceMessage) message);
            break;
          case INITIAL_TERRITORY:
            this.sendMessagesToallClients((SelectInitialTerritoryMessage) message);
            break;
          case INGAME:
            this.sendMessagesToallClients(message);
            break;
          case DISTRIBUTE_ARMY:
            this.sendMessagesToallClients(message);
            break;
          case FURTHER_DISTRIBUTE_ARMY:
            this.sendMessagesToallClients(message);
            break;
          case ATTACK:
            this.sendMessagesToallClients(message);
            break;
          case FORTIFY:
            this.sendMessagesToallClients(message);
          default:
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
   * 
   * @author skaur
   * @param message sent from client after he joins the game lobby
   * 
   *        This methods is called whenever a client is connected to the server. It creates the
   *        Player instance for each player which joins the game lobby, add it to the list and send
   *        the game instance as a response message back. This method update the list of players in
   *        the host game lobby
   */
  public void handleJoinGame(JoinGameMessage message) {
    playerN = message.getName();
    this.player = new Player(message.getName(),
        game.PlayerColor.values()[Main.g.getPlayers().size()], Main.g);
    Main.g.addPlayer(player);
    this.players.add(player);

    // update the list in the host game lobby
    if (this.server.getHostLobbyController() != null) {
      this.server.getHostLobbyController().updateList(player);
    }

    // send a response back to the client who has sent the
    // join game message with the player instance as parameter
    JoinGameResponseMessage response = new JoinGameResponseMessage(player);
    this.sendMessage(response);

  }

  /**
   * 
   * @param message
   * 
   *        send the message to all clients and server show message in host game lobby
   */
  private void receiveSendChatMessage(SendChatMessageMessage message) {
    String name = message.getUsername();
    String content = message.getMessage();
    // send message to all clients and show in join game lobby
    this.sendMessagesToallClients(message);
    // show message in host game lobby
    this.server.getHostLobbyController().showMessage(name.toUpperCase() + " : " + content);
    System.out.println("Message from client with the content " + content + " sent to all clients");
    // gui.HostGameLobbyController.showMessage(content);

  }


  /**
   * @author skaur
   * @param message
   * 
   *        send the leave message to the clients and disconnect the client who has sent the message
   * 
   */
  private void recieveLeaveMessage(LeaveGameMessage message) {
    this.server.getConnections().remove(this);
    this.sendMessagesToallClients(message);
    LeaveGameResponseMessage m = new LeaveGameResponseMessage();
    this.sendMessage(m);
    this.disconnect();
  }
  
  public void recieveLeaveGameResponse(LeaveGameResponseMessage message) {
    this.sendMessage(message);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.server.getConnections().remove(this);
    this.disconnect();
    this.server.stopServer();
  }

  /**
   * @author qiychen
   * @param message can be send only to a specific client only in this client gui will message be
   *        demostrated
   */
  public void handleAllianceMessage(SendAllianceMessage message) {
    String playername = message.getPlayerName();
    System.out.println("Clientconnection playername " + playername);
    for (int i = 0; i < server.getConnections().size(); i++) {
      ClientConnection c = server.getConnections().get(i);
      if (playername.equalsIgnoreCase(c.getPlayerName())) {
        c.sendMessage(message);
      }
    }

  }

  /**
   * @author qiychen
   * 
   *         disconnect the connection
   */
  public void disconnect() {
    try {
      this.toClient.close();
      this.fromClient.close();
      this.socket.close();
      this.active = false;
      System.out.println("Client Connection is ending....");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}

