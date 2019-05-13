package network.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import game.Player;
import game.PlayerColor;
import gui.controller.BoardController;
import gui.controller.HostGameLobbyController;
import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Platform;
import main.Main;
import network.messages.JoinGameMessage;
import network.messages.JoinGameResponseMessage;
import network.messages.LeaveLobbyMessage;
import network.messages.Message;
import network.messages.SendAllianceMessage;
import network.messages.SendChatMessageMessage;
import network.messages.game.LeaveGameMessage;
import network.messages.game.LeaveGameResponseMessage;
import network.messages.game.SelectInitialTerritoryMessage;
import network.messages.game.StartGameMessage;

/**
 * This class represents the connection between the server and the client.
 */

public class ClientConnection extends Thread {

  /************* Variable needed for the communication between the server and client. ********************/
  
  /**
   * IP Address of the server.
   */
  private InetAddress address;
  
  /**
   * Port of the server.
   */
  private int port; 
  
  /**
   * Socket of this connection
   */
  private Socket socket;
  
  /**
   * Streams to read and write to the client
   */
  private ObjectInputStream fromClient;
  private ObjectOutputStream toClient;
  
  /**
   * Server of this connection
   */
  private Server server;
  
  /************************************************ Other variables. *****************************************/
  
  private boolean active;
  private Player player; // player who represents this client connection
  private HostGameLobbyController hostLobbyController;
  private ArrayList<Player> players = new ArrayList<Player>(); // list of players joined
  private BoardController boardController;
  private String playerN;

  
  
  /**************************************************
   *                                                *
   *                   Constructor                  *
   *                                                *
   *************************************************/
  
  /**
   * 
   * @author qiychen
   * @param socket for the communication
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
   * 
   * @author qiychen
   * @param socket for the communication
   * @param server of the game
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
          case LEAVE_LOBBY : 
            recieveLeaveLobbyMessage( (LeaveLobbyMessage ) message);
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
            break;
          case SKIP:
            this.sendMessagesToallClients(message);
            break;
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
   * This methods is called whenever a client is connected to the server. It creates the Player
   * instance for each player which joins the game lobby, add it to the list and send the game
   * instance as a response message back. This method update the list of players in the host game
   * lobby.
   * 
   * @author skaur
   * @param message sent from client after he joins the game lobby
   * 
   */
  public void handleJoinGame(JoinGameMessage message) {
    playerN = message.getName();
    PlayerColor color = this.server.getAvailableColor().get(0);
    System.out.println("Color : " + color.toString());
    this.player = new Player(message.getName(),
       color, Main.g);
    Main.g.addPlayer(player);
    this.server.getAvailableColor().remove(0);
    this.server.getAvailableColor().trimToSize();
    this.players.add(player);
    int index = Main.g.getPlayers().size();
    // update the list in the host game lobby
    if (this.server.getHostLobbyController() != null) {
      this.server.getHostLobbyController().updateList(index - 1, player);
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
   * Send the leave message to the clients and disconnect the client who has sent the message.
   * 
   * @author skaur
   * @param message
   * 
   */
  private void recieveLeaveMessage(LeaveGameMessage message) {
    this.server.getConnections().remove(this);
    this.sendMessagesToallClients(message);
    LeaveGameResponseMessage m = new LeaveGameResponseMessage();
    this.sendMessage(m);
    this.disconnect();
    this.server.stopServer();
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

  
  public void recieveLeaveLobbyMessage(LeaveLobbyMessage message) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        if (server.getHostLobbyController() != null) {
         server.getHostLobbyController().refreshList(message.getPlayer());
        }     
      }
    });
    this.sendMessage(message);
    this.server.getConnections().remove(this);
    this.disconnect();
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
    this.active = false;
    this.interrupt();
    try {
      this.toClient.close();
      this.fromClient.close();
      this.socket.close();
      System.out.println("Client Connection is ending....");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}

