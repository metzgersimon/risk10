package network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import game.AiPlayer;
import game.Game;
import game.GameState;
import game.Player;
import gui.BoardController;
import gui.HostGameGUIController;
import gui.HostGameLobbyController;
import gui.JoinGameLobbyController;
import gui.NetworkController;
import javafx.application.Platform;
import main.Main;
import network.Parameter;
import network.messages.GameMessageMessage;
import network.messages.JoinGameMessage;
import network.messages.JoinGameResponseMessage;
import network.messages.Message;
import network.messages.SendAllianceMessage;
import network.messages.SendChatMessageMessage;
import network.messages.game.SelectInitialTerritoryMessage;
import network.messages.game.StartGameMessage;


public class Client extends Thread implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private InetAddress address;
  private Socket s;
  private Player player;
  private ObjectInputStream fromServer;
  private ObjectOutputStream toServer;
  private boolean active;
  private int port;
  private JoinGameLobbyController controller = null;
  private HostGameLobbyController hostcontroller = null;
  private BoardController boardController;
  public static boolean isHost;
  private NetworkController networkController = new NetworkController();
  // private HostGameLobbyController hostUi;

  public Client(InetAddress address, int port) {
    this.address = address;
    this.port = port;
    connect();
    Main.g.setNetworkGame(true);
  }

  /**
   * Constructor to join the game on discovery
   * 
   * @author qiychen
   * @param address
   * @param player
   */
  public Client(InetAddress address, Player player, int port) {
    this.address = address;
    // this.player = player;
    this.port = port;
    connect();
    Main.g.setNetworkGame(true);
  }

  /**
   * Second Constructor to join the game by giving the ip and port address and starts the client
   * thread
   * 
   * @skaur
   * @param ip
   * @param port
   */
  public Client(String ip, int port) {
    try {
      this.address = InetAddress.getByName(ip);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    this.port = port;
    connect();
    Main.g.setNetworkGame(true);
  }

  /**
   * @author qiychen
   * @return whether the connection is established
   */
  public boolean connect() {
    try {
      this.s = new Socket(address, Parameter.PORT);
      fromServer = new ObjectInputStream(s.getInputStream());
      toServer = new ObjectOutputStream(s.getOutputStream());
      this.active = true;
      System.out.println("Socket opened successfully");
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public void run() {
    transact();
  }

  /**
   * send message to server
   * 
   * @author qiychen
   * @param message
   */
  public void sendMessage(Message m) {
    System.out.println("test send message" + m.getContent());
    try {
      toServer.writeObject(m);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void register(String name) {
    JoinGameMessage join = new JoinGameMessage(name);
    this.sendMessage(join);
    // To get response
  }

  public void setController(JoinGameLobbyController controller) {
    this.controller = controller;
  }

  public void setControllerHost(HostGameLobbyController hostcontroller) {
    this.hostcontroller = hostcontroller;
  }


  /**
   * handle incoming messages from server
   */
  public void transact() {
    // clientUi = new JoinGameLobbyController();
    // hostUi = new HostGameLobbyController();

    // FXMLLoader loader = new FXMLLoader(getClass().getResource("JoinGameLobby.fxml"));
    // Main.j = loader.getController();
    // Main.j.setMain(this, Main.g);

    while (active) {
      try {
        Message message = (Message) fromServer.readObject();
        switch (message.getType()) {
          case BROADCAST:
            String name = ((SendChatMessageMessage) message).getUsername();
            String content = ((SendChatMessageMessage) message).getMessage();
            System.out.println("from server: " + name + " " + content);
            System.out.println("client show " + content);
            System.out.println("host show " + content);
            if (!isHost) {
              controller.showMessage(name.toUpperCase() + " : " + content);
            }
            // Parent root = FXMLLoader.load(getClass().getResource("JoinGameLobby.fxml"));
            // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("JoinGameLobby.fxml"));
            // Parent root = (Parent) fxmlLoader.load();
            // TextArea chat = (TextArea) root.lookup("#lblData");
            // chat.appendText(content);
            break;
          case START_GAME:
            handleStartGameMessage((StartGameMessage) message);
          case LEAVE:
            break;
          case DISPLAY:
            break;
          case ALLIANCE:
            handleAllianceMessage((SendAllianceMessage) message);
            break;
          case JOIN_REPONSE:
            handleJoinGameResponse((JoinGameResponseMessage) message);
            break;
          case INITIAL_TERRITORY:
            handleInitialTerritory((SelectInitialTerritoryMessage) message);
            break;
          case INGAME:
            handleIngameMessage((GameMessageMessage) message);
            break;
        }
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * this method showcase the game board to the all the players
   * 
   * @author skaur
   * @param message
   */
  public void handleStartGameMessage(StartGameMessage message) {
    Main.g.setPlayers(message.getPlayerList());
    // if(!(player instanceof AiPlayer)) {
//    for (Player p : Main.g.getPlayers()) {
//      System.out.println(p.getName());
//    }
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        networkController.viewBoardGame();
        Main.b.connectRegionTerritory();
        Main.g.initGame();
      }

    });
    // }
  }

  /**
   * After receiving the Initial Territory information, every client updates the information in
   * their game instance and game board
   * 
   * @author skaur
   * @param message
   */
  public void handleInitialTerritory(SelectInitialTerritoryMessage message) {
    // change the attributes of the aiPlayer after receiving the message
    if (this.player instanceof AiPlayer) {
      Main.g.getWorld().getTerritories().get(message.getTerritoryID()).setOwner(this.player);
      this.player.addTerritories(Main.g.getWorld().getTerritories().get(message.getTerritoryID()));
      this.player.numberArmiesToDistribute -= 1;
      Main.g.furtherInitialTerritoryDistribution();
    }
    // Dont update the board if the message is sent the by the humanplayer himself
    if (!(this.player.getColor().toString().equals(message.getColor()))) {
      // Control if its AI player
      if (!(this.player instanceof AiPlayer)) {
        // update the information recieved from the message in game instance
        Main.g.getWorld().getTerritories().get(message.getTerritoryID())
            .setOwner(Main.g.getCurrentPlayer());
        Main.g.getCurrentPlayer()
            .addTerritories(Main.g.getWorld().getTerritories().get(message.getTerritoryID()));
        Main.g.getCurrentPlayer().numberArmiesToDistribute -= 1;
        Main.g.getWorld().getTerritories().get(message.getTerritoryID()).setNumberOfArmies(1);
        // update the label and the color of the territory selected by the other player
        Main.b
            .updateLabelTerritory(Main.g.getWorld().getTerritories().get(message.getTerritoryID()));
        Main.b
            .updateColorTerritory(Main.g.getWorld().getTerritories().get(message.getTerritoryID()));
        Main.g.furtherInitialTerritoryDistribution();
      } else {
        Main.g.furtherInitialTerritoryDistribution();
      }
    }
  }

  public void handleJoinGameResponse(JoinGameResponseMessage responseMessage) {
    this.player = responseMessage.getPlayer();
  }

  /**
   * @author qiychen
   * @param show private message in board gui
   */
  public void handleAllianceMessage(SendAllianceMessage message) {
    String privateUsername = message.getSender();
    String privateMessage = message.getContent();
    Main.b.showAllianceMessage(privateUsername.toUpperCase() + " : " + privateMessage);
  }

  /**
   * @author qiychen
   * @param messages in boardgui will be showed
   */
  private void handleIngameMessage(GameMessageMessage message) {
    String username = message.getUsername();
    String messageContent = message.getMessage();
    Main.b.showMessage(username.toUpperCase() + " : " + messageContent);

  }

  public Player getPlayer() {
    return this.player;
  }

  public void setBoardController(BoardController boardController) {
    this.boardController = boardController;
  }

  public int getPort() {
    return this.port;
  }

  /**
   * @author qiychen disconnect the connection
   */
  public void disconnect() {
    try {
      active = false;
      this.fromServer.close();
      this.toServer.close();
      s.close();
      System.out.println("Protocol ended");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
