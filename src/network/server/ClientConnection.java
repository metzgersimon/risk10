package network.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import game.Player;
import gui.BoardController;
import gui.HostGameLobbyController;
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
import network.messages.game.SelectInitialTerritoryMessage;
import network.messages.game.StartGameMessage;

/**
 *This class represents the connection between the server and the client
 */

public class ClientConnection extends Thread {

  private InetAddress address; //address of the server
  private int port; //port of the server
  private Socket socket;
  private ObjectInputStream fromClient;
  private ObjectOutputStream toClient;
  private boolean active;
  private Player player; //player who represents this client connection
  private Server server;
  private HostGameLobbyController hostLobbyController;
  private ArrayList<Player> players = new ArrayList<Player>(); //list of players joined 
  private BoardController boardController;
  private String playerN;

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

  public void run() {
    transact();
  }

  /**
   * send messages to client
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
   * send message to all clients
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
            // send message to all clients and show in join game lobby
            this.sendMessagesToallClients(message);
            // show message in host game lobby
            this.server.getHostLobbyController().showMessage(name.toUpperCase() + " : " + content);
            System.out.println(
                "Message from client with the content " + content + " sent to all clients");
            // gui.HostGameLobbyController.showMessage(content);
            break;
          case SEND:
            break;
          case START_GAME:
            handleStartGameMessage((StartGameMessage) message);
            break;
          case LEAVE:
            recieveLeaveMessage((LeaveGameMessage) message);
            break;
          case JOIN:
            handleJoinGame((JoinGameMessage) message);
            break;
          case ALLIANCE:
            handleAllianceMessage((SendAllianceMessage) message);
            break;
          case INITIAL_TERRITORY:
            recieveInitialTerritory((SelectInitialTerritoryMessage) message);
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
            receiveAttackTerritory((AttackMessage) message);
            break;
          case FORTIFY:
            receiveFortifyMessage((FortifyMessage) message);
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

  public void handleStartGameMessage(StartGameMessage startMessage) {
    this.sendMessagesToallClients(startMessage);
  }

  public void recieveInitialTerritory(SelectInitialTerritoryMessage message) {
    this.sendMessagesToallClients(message);
  }

  private void receiveAttackTerritory(AttackMessage message) {
    this.sendMessagesToallClients(message);

  }

  private void receiveFortifyMessage(FortifyMessage message) {
    this.sendMessagesToallClients(message);

  }

  /**
   * @author skaur
   * @param message
   * 
   *        send the leave message to the clients and disconnect the client who has sent the message
   * 
   */
  private void recieveLeaveMessage(LeaveGameMessage message) {
    this.sendMessagesToallClients(message);
    if (this.player.getColor().toString().equals(message.getColor())) {
      this.disconnect();
    }
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
      if(playername.equalsIgnoreCase(c.getPlayerName())) {
        c.sendMessage(message);
      }
    }

  }

  public ArrayList<Player> getPlayer() {
    return this.players;
  }

  /**
   * @author qiychen
   * @return name of the player
   */
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
}

