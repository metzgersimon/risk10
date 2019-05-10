package network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import game.AiPlayer;
import game.GameState;
import game.Player;
import game.Territory;
import gui.controller.BoardController;
import gui.controller.HostGameLobbyController;
import gui.controller.JoinGameLobbyController;
import gui.controller.NetworkController;
import java.net.UnknownHostException;
import javafx.application.Platform;
import main.Main;
import network.messages.GameMessageMessage;
import network.messages.JoinGameMessage;
import network.messages.JoinGameResponseMessage;
import network.messages.Message;
import network.messages.SendAllianceMessage;
import network.messages.SendChatMessageMessage;
import network.messages.game.AttackMessage;
import network.messages.game.DistributeArmyMessage;
import network.messages.game.FortifyMessage;
import network.messages.game.FurtherDistributeArmyMessage;
import network.messages.game.LeaveGameMessage;
import network.messages.game.SelectInitialTerritoryMessage;
import network.messages.game.SkipgamestateMessage;
import network.messages.game.StartGameMessage;


public class Client extends Thread implements Serializable {
  /**
   * This class defines the client connected to the server.
   */
  private static final long serialVersionUID = 1L;

  /*************** Elements needed for the communication with the server. **********************/
  
  /**
   * InetAddress of the connected server.
   */
  private InetAddress address;
  
  /**
   * The socket of the client.
   */
  private Socket s;
  
  /**
   * Streams to read and write to the server.
   */
  private ObjectInputStream fromServer;
  private ObjectOutputStream toServer;
  private int port;

  /*************************************** Controllers. *****************************************/
  
  private JoinGameLobbyController controller = null;
  private HostGameLobbyController hostcontroller = null;
  private BoardController boardController;
  private NetworkController networkController = new NetworkController();

  /**
   * other elements
   */
  
  
  /*************************************** Other Elements.***************************************/
  /**
   * Represents if the client is host.
   */
  public static boolean isHost;
  
  /**
   * Represents if the client is active.
   */
  private boolean active;
  
  /**
   * The player which is represents this client.
   */
  private Player player; 

  
  /**************************************************
   *                                                *
   *                Constructors                    *
   *                                                *
   *************************************************/

  public Client(InetAddress address, int port) {
    this.address = address;
    this.port = port;
    connect();
    Main.g.setNetworkGame(true);
  }

  /**
   * 
   * @author qiychen
   * @param address
   * @param player Constructor to join the game on discovery
   */
  public Client(InetAddress address, Player player, int port) {
    this.address = address;
    // this.player = player;
    this.port = port;
    connect();
    Main.g.setNetworkGame(true);
  }

  /**
   * Second Constructor to join the game by giving the IP and port address and starts the client
   * thread.
   * 
   * @skaur
   * @param ip address of the server
   * @param port listening to the server
   *
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
  
  /**************************************************
   *                                                *
   *   Connection and disconnection of clients      *
   *                                                *
   *************************************************/
  
  /**
   * @author qiychen
   * @return whether the connection is established
   */
  public boolean connect() {
    try {
      this.s = new Socket(address, main.Parameter.PORT);
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

  /**
   * @author qiychen
   * 
   *         disconnect the connection
   */
  public void disconnect() {
    this.interrupt();
    try {
      active = false;
      this.fromServer.close();
      this.toServer.close();
      s.close();
      System.out.println("Client is leaving the game ....");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**************************************************
   *                                                *
   *      Send and receive messages from client     *
   *                                                *
   *************************************************/
  
  /**
   * @author qiychen
   * @param message send message to server
   */
  public void sendMessage(Message m) {
    // System.out.println("test send message" + m.getContent());
    try {
      toServer.writeObject(m);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }


  /**
   * handle incoming messages from server
   */
  public void run() {

    while (active) {
      try {
        Message message = (Message) fromServer.readObject();
        switch (message.getType()) {
          case BROADCAST:
            handleSendChatMessage((SendChatMessageMessage) message);
            break;
          case START_GAME:
            handleStartGameMessage((StartGameMessage) message);
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
          case DISTRIBUTE_ARMY:
            handleDistributeArmy((DistributeArmyMessage) message);
            break;
          case FURTHER_DISTRIBUTE_ARMY:
            System.out.println("further army message bekommen!!!");
            handleFurtheDistributeAmry((FurtherDistributeArmyMessage) message);
            break;
          case ATTACK:
            System.out.println("attack message bekommen");
            handleAttackMessage((AttackMessage) message);
            break;
          case FORTIFY:
            System.out.println("fortify message bekommen");
            handleFortifyMessage((FortifyMessage) message);
            break;
          case SKIP:
            System.out.println("skip message bekommen");
            handleSkipgamestateMessage((SkipgamestateMessage)message);
            break;
          case LEAVE:
            handleLeaveGame((LeaveGameMessage) message);
            break;
          case LEAVE_RESPONSE:
            this.disconnect();
          default:
            break;
        }
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  
  /**************************************************
   *                                                *
   *               Getters and Setters              *
   *                                                *
   *************************************************/
  
  public Player getPlayer() {
    return this.player;
  }

  public void setBoardController(BoardController boardController) {
    this.boardController = boardController;
  }

  public int getPort() {
    return this.port;
  }

  public void setController(JoinGameLobbyController controller) {
    this.controller = controller;
  }

  public void setControllerHost(HostGameLobbyController hostcontroller) {
    this.hostcontroller = hostcontroller;
  }

  public HostGameLobbyController getHostController() {
    return this.hostcontroller;
  }

  public BoardController getBoardController() {
    return this.boardController;
  }
  
  public JoinGameLobbyController getController() {
    return this.controller;
  }

  
  /**************************************************
   *                                                *
   *  Process the messages received from the server *
   *                                                *
   *************************************************/

  /**
   * After connecting to the server, the client sends a message to the server with the player name
   * to register for the game and to get response from the server.
   * 
   * @author skaur
   * @param name of the player who has requested to join the game
   *
   */
  public void register(String name) {
    JoinGameMessage join = new JoinGameMessage(name);
    // To get response
    this.sendMessage(join);
  }

  /**
   * After registering the name of the client, server sends back a response message containing the
   * player instance. This methods initialize the player instance for the client.
   * 
   * @author skaur
   * @param responseMessage containing the player instance for the client
   */
  public void handleJoinGameResponse(JoinGameResponseMessage responseMessage) {
    if (!(responseMessage.getPlayer() instanceof AiPlayer)) {
      this.player = responseMessage.getPlayer();
    }
  }

  /**
   * After receiving the start game message from the host player, this method update the player list
   * to match up the player list of the host player. After that it opens the game board for the
   * client player.
   * 
   * @author skaur
   * @param message containing the list of players who will be playing the game
   */
  public void handleStartGameMessage(StartGameMessage message) {
    // update the player list
    Main.g.setPlayers(message.getPlayerList());

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        // open the game board
        networkController.viewBoardGame();
        Main.b.connectRegionTerritory();
        Main.g.initGame();
      }
    });
  }

  /**
   * After receiving the Initial Territory information, every client updates the information in
   * their game instance and game board.
   * 
   * @author skaur
   * @param message
   * 
   */
  public synchronized void handleInitialTerritory(SelectInitialTerritoryMessage message) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        // Dont update the board if the message is sent the by the humanplayer himself
        if (!(player.getColor().toString().equals(message.getColor()))) {
          // update the information recieved from the message in game instance
          Main.g.getWorld().getTerritories().get(message.getTerritoryId())
              .setOwner(Main.g.getCurrentPlayer());
          Main.g.getCurrentPlayer()
              .addTerritories(Main.g.getWorld().getTerritories().get(message.getTerritoryId()));
          Main.g.getCurrentPlayer().numberArmiesToDistribute -= 1;
          Main.g.getWorld().getTerritories().get(message.getTerritoryId()).setNumberOfArmies(1);

          // update the label and the color of the territory selected by the other player
          Main.b.updateLabelTerritory(
              Main.g.getWorld().getTerritories().get(message.getTerritoryId()));
          Main.b.updateColorTerritory(
              Main.g.getWorld().getTerritories().get(message.getTerritoryId()));
          // System.out.println(this.player.getName() + " " + this.player.getTerritories
        }
        try {
          Thread.sleep(100);
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
        Main.g.furtherInitialTerritoryDistribution();
      }
    });
  }

  /**
   * 
   * After receiving the army distribution information, every client updates the information in
   * their game instance and game board.
   * 
   * @author skaur
   * @param message
   */
  public synchronized void handleDistributeArmy(DistributeArmyMessage message) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        if (!(player.getColor().toString().equals(message.getColor()))) {
          Main.g.getWorld().getTerritories().get(message.getTerritoryId())
              .setNumberOfArmies(message.getAmount());
          Main.g.getCurrentPlayer().numberArmiesToDistribute -= message.getAmount();
          Main.b.updateLabelTerritory(
              Main.g.getWorld().getTerritories().get(message.getTerritoryId()));
        }
        Main.g.furtherInitialArmyDistribution();
      }
    });
  }

  /**
   * 
   * After receiving the further army distribution information, every client updates the information
   * in their game instance and game board.
   * 
   * @author skaur
   * @param message
   */
  public synchronized void handleFurtheDistributeAmry(FurtherDistributeArmyMessage message) {
    if (player.getColor().toString().equals(message.getColor())) {
      Main.b.setState("Place your Armies!");
    }

    if ((NetworkController.server != null && Main.g.getCurrentPlayer() instanceof AiPlayer)) {
      System.out.println("host soll further army message nicht bearbeiten");
      return;
    }

    if ((NetworkController.server != null
        && !(Main.g.getCurrentPlayer().getColor().toString().equals(message.getColor())))) {
      System.out.println(
          "host bekommt die nachricht verzögert und soll das further army message nicht bearbeiten");
      return;
    }

    if (!(player.getColor().toString().equals(message.getColor()))) {
      // Main.b.setState("It's "+Main.g.getCurrentPlayer().getName()+" turn.");
      Main.g.getWorld().getTerritories().get(message.getTerritoryId())
          .setNumberOfArmies(message.getAmount());
      Main.g.getCurrentPlayer().numberArmiesToDistribute -= message.getAmount();
      System.out.println(Main.g.getCurrentPlayer().getName() + " set " + message.getAmount()
          + " on " + Main.g.getWorld().getTerritories().get(message.getTerritoryId()).getName());
      System.out.println(Main.g.getCurrentPlayer().getName() + " still have "
          + Main.g.getCurrentPlayer().numberArmiesToDistribute + " to distribute");
      Main.b.updateLabelTerritory(Main.g.getWorld().getTerritories().get(message.getTerritoryId()));
    }

  }

  /**
   * After receiving attack message, update the territory in gui accordingly
   * 
   * @author qiychen
   * @param message
   */
  private synchronized void handleAttackMessage(AttackMessage message) {
    if ((NetworkController.server != null && Main.g.getCurrentPlayer() instanceof AiPlayer)) {
      System.out.println("host soll attack message nicht bearbeiten");
      return;
    }

    if ((NetworkController.server != null
        && !(Main.g.getCurrentPlayer().getColor().toString().equals(message.getColor())))) {
      System.out.println(
          "host bekommt die nachricht verzögert und soll das attack message nicht bearbeiten");
      return;
    }

    if (!(this.player.getColor().toString().equals(message.getColor())
        && (Main.g.getCurrentPlayer().getColor().toString().equals(message.getColor())))) {
      Territory attack = Main.g.getWorld().getTerritories().get(message.getAttackerID());
      Territory defend = Main.g.getWorld().getTerritories().get(message.getDefenderID());
      int attackarmies = message.getAttackerArmies();
      int defendarmies = message.getDefendArmies();

      System.out.println(Main.g.getCurrentPlayer().getName() + " attacks  from " + attack.getName()
          + " to " + defend.getName());

      attack.setNumberOfArmies2(attackarmies);
      defend.setNumberOfArmies2(defendarmies);
      System.out.println("client attackarmies " + attackarmies);
      System.out.println("client defendarmies " + defendarmies);
      Main.b.updateLabelTerritory(attack);
      Main.b.updateLabelTerritory(defend);
      if (message.getIfConquered()) {
        Player defender = defend.getOwner();
        defender.lostTerritories(defend);
        Player attacker = attack.getOwner();
        defend.setOwner(attacker);
        Main.b.updateColorTerritory(defend);
        attacker.addTerritories(defend);
        Main.g.checkAllPlayers();
        attacker.setTerritoriesConquered(attacker.getTerritoriesConquered() + 1);
        if (!Main.g.getPlayers().contains(defender)) {
          defender.setRank(Main.g.getPlayers().size());
          attacker.addElimiatedPlayer(defender);
          attacker.setCards(defender.getCards());
          Main.b.showMessage(attacker.getName() + " defeated " + defender.getName() + "!");
        }
        if (Main.g.getPlayers().size() == 1) {
          Main.g.setGameState(GameState.END_GAME);
          Main.b.endGame();
        } else if (Main.g.onlyAiPlayersLeft()) {
          Main.b.showMessage("You lost the Game!");
          Main.g.setGameState(GameState.END_GAME);
          Main.b.endGame();
        }
        System.out.println("attack owner " + attack.getOwner().getName());
        System.out.println("defend owner " + defend.getOwner().getName());
      }
    }
  }

  /**
   * After receiving the fortify message, move the armies from one territoy to another
   * 
   * @author qiychen
   * @param message
   */
  private synchronized void handleFortifyMessage(FortifyMessage message) {
    if ((NetworkController.server != null && Main.g.getCurrentPlayer() instanceof AiPlayer)) {
      System.out.println("host soll fortify message nicht bearbeiten");
      return;
    }
    if ((NetworkController.server != null
        && !(Main.g.getCurrentPlayer().getColor().toString().equals(message.getColor())))) {
      System.out.println(
          "host bekommt die nachricht verzögert und soll das fortify message nicht bearbeiten");
      return;
    }

    if (!(this.player.getColor().toString().equals(message.getColor()))) {
      // if (!(this.player instanceof AiPlayer)) {

      Territory moveFrom = Main.g.getWorld().getTerritories().get(message.getMoveFromTerritoryID());
      Territory moveTo = Main.g.getWorld().getTerritories().get(message.getMoveToTerritoryID());
      moveFrom.setNumberOfArmies(-message.getAmount());
      moveTo.setNumberOfArmies(message.getAmount());
      Main.b.updateLabelTerritory(moveFrom);
      Main.b.updateLabelTerritory(moveTo);
      System.out.println("move from " + moveFrom.getName() + " " + moveFrom.getNumberOfArmies());
      System.out.println("move to " + moveTo.getName() + " " + moveTo.getNumberOfArmies());
      System.out.println("move " + message.getAmount());

      // }
    }

    // if ((NetworkController.server == null && Main.g.getCurrentPlayer() instanceof AiPlayer)) {
    // System.out.println("skip to next for Client");
    // Main.g.furtherFortify();
    // return;
    // }

    // Main.g.furtherFortify();
    // if(this.player.getColor().toString().equals(message.getColor())) {
    // Main.b.setState("End your turn!");
    // }

  }
  /**
   * @author qiychen
   * @param message
   * 
   *  After receiving skip gamestate message, the turns will be changed
   */
  private synchronized void handleSkipgamestateMessage(SkipgamestateMessage message) {
    if ((NetworkController.server != null && Main.g.getCurrentPlayer() instanceof AiPlayer)) {
      System.out.println("host soll skip message nicht bearbeiten");
      return;
    }

    if ((NetworkController.server != null
        && !(Main.g.getCurrentPlayer().getColor().toString().equals(message.getColor())))) {
      System.out.println(
          "host bekommt die nachricht verzögert und soll das skip message nicht bearbeiten");
      return;
    }

    Main.b.setTurns();
    if (!(this.player.getColor().toString().equals(message.getColor()))) {
      if (message.getGameState() == GameState.FORTIFY) {
        System.out.println("skip fortify");
        Main.b.setState("\"Place your Armies!\"");
        Main.g.furtherFortify();
      }
    }
  }

  
  /**
   * @author Liwang @author qiychen
   * @param message
   * 
   *        After receiving the send chat message, the message will be showed in the lobby
   * 
   */
  private void handleSendChatMessage(SendChatMessageMessage message) {
    String name = message.getUsername();
    String content = message.getMessage();
    System.out.println("from server: " + name + " " + content);
    System.out.println("client show " + content);
    System.out.println("host show " + content);
    if (!isHost) {
      controller.showMessage(name.toUpperCase() + " : " + content);
    }
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

  /**
   * After receiving the leave game message, this methods shows the game cancelled alert and shows
   * the end game statistics.
   * 
   * @author skaur
   * @param message send from one of the player after quitting the game.
   */
  public void handleLeaveGame(LeaveGameMessage message) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        if (!message.getLeaveLobby()) {
          Main.b.gameCancelAlert();
        } else {
          controller.showGameCancelAlert();
        }
      }
    });
  }
}
