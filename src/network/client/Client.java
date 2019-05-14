package network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import game.AiPlayer;
import game.Card;
import game.GameState;
import game.Player;
import game.Territory;
import gui.controller.BoardController;
import gui.controller.HostGameLobbyController;
import gui.controller.JoinGameLobbyController;
import gui.controller.NetworkController;
import java.net.UnknownHostException;
import java.util.HashSet;
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
   * player Constructor to join the game on discovery.
   * @author qiychen
   * @param address
   * @param 
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
   * @author skaur
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
   * establish the connection.
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
   * disconnect the connection.
   * @author qiychen
   * 
   *         
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
   * message send message to server.
   * 
   * @author qiychen
   * @param 
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
   * handle incoming messages from server.
   * 
   * @author qiychen 
   * @author skaur
   * @author liwang
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
            handleFurtheDistributeAmry((FurtherDistributeArmyMessage) message);
            break;
          case ATTACK:
            handleAttackMessage((AttackMessage) message);
            break;
          case FORTIFY:
            handleFortifyMessage((FortifyMessage) message);
            break;
          case SKIP:
            handleSkipgamestateMessage((SkipgamestateMessage)message);
            break;
          case LEAVE:
            handleLeaveGame((LeaveGameMessage) message);
            break;
          case LEAVE_RESPONSE:
            this.disconnect();
          case LEAVE_LOBBY:
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
    HashSet<Player> allPlayers = new HashSet<Player>();
    allPlayers.addAll(message.getPlayerList());
    Main.g.setAllPlayers(allPlayers);
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
     Main.b.setTurns();
    if ((NetworkController.server != null && Main.g.getCurrentPlayer() instanceof AiPlayer)) {
      return;
    }
    if ((NetworkController.server != null
        && !(Main.g.getCurrentPlayer().getColor().toString().equals(message.getColor())))) {
      return;
    }
    if (!(player.getColor().toString().equals(message.getColor()))) {
      Main.g.getWorld().getTerritories().get(message.getTerritoryId())
          .setNumberOfArmies(message.getAmount());
      Main.g.getCurrentPlayer().numberArmiesToDistribute -= message.getAmount();
      Main.b.updateLabelTerritory(Main.g.getWorld().getTerritories().get(message.getTerritoryId()));
    }
  }

  /**
   * After receiving attack message, update the territory in gui accordingly
   * 
   * 
   * @author qiychen
   * @author Liwang
   * @param message
   */
  private synchronized void handleAttackMessage(AttackMessage message) {
    if ((NetworkController.server != null && Main.g.getCurrentPlayer() instanceof AiPlayer)) {
      return;
    }
    if ((NetworkController.server != null
        && !(Main.g.getCurrentPlayer().getColor().toString().equals(message.getColor())))) {
      return;
    }
    //update the change for all clients
    if (!(this.player.getColor().toString().equals(message.getColor())
        && (Main.g.getCurrentPlayer().getColor().toString().equals(message.getColor())))) {
      Territory attack = Main.g.getWorld().getTerritories().get(message.getAttackerID());
      Territory defend = Main.g.getWorld().getTerritories().get(message.getDefenderID());
      Player defender = defend.getOwner();
      Player attacker = attack.getOwner();
      attacker.setNumberOfAttacks(attacker.getNumberOfAttacks() + 1);
      int attackarmies = message.getAttackerArmies();
      int defendarmies = message.getDefendArmies();
      attack.setNumberOfArmies2(attackarmies);
      defend.setNumberOfArmies2(defendarmies);
      Main.b.updateLabelTerritory(attack);
      Main.b.updateLabelTerritory(defend);
      //if the attack is successful
      if (message.getIfConquered()) {             
        defender.lostTerritories(defend);
        defend.setOwner(attacker);
        attacker.setSuccessfullAttack(true);
        Main.g.getCards().removeLast();
        Main.b.updateColorTerritory(defend);
        attacker.addTerritories(defend);
        Main.g.checkAllPlayers();
        attacker.setTerritoriesConquered(attacker.getTerritoriesConquered() + 1);
        //if the player defeats the defender
        if (!Main.g.getPlayers().contains(defender)) {
          attacker.addElimiatedPlayer(defender);
          attacker.setNumberOfEliminatedPlayers(attacker.getNumberOfEliminatedPlayers()+1);
          defender.setRank(Main.g.getPlayers().size() + 1);          
          attacker.setCards(defender.getCards());           
          Main.b.showMessage(attacker.getName() + " defeated " + defender.getName() + "!");
          if(!(defender instanceof AiPlayer)) {
            Main.b.endGame();
          }       
        }
        //if the player wins the game
        if (Main.g.getPlayers().size() == 1) {
          attacker.setRank(1);
          Main.g.setGameState(GameState.END_GAME);
          Main.b.endGame();
        } else if (Main.g.onlyAiPlayersLeft()) {
          Main.b.showMessage("You lost the Game!");
          Main.g.setGameState(GameState.END_GAME);
          Main.b.endGame();
        }
      }
    }
  }

  /**
   * After receiving the fortify message, move the armies from one territoy to another.
   * 
   * @author qiychen 
   * @author Liwang
   * @param message
   */
  private synchronized void handleFortifyMessage(FortifyMessage message) {
    if ((NetworkController.server != null && Main.g.getCurrentPlayer() instanceof AiPlayer)) {
      return;
    }
    if ((NetworkController.server != null
        && !(Main.g.getCurrentPlayer().getColor().toString().equals(message.getColor())))) {
      return;
    }

    if (!(this.player.getColor().toString().equals(message.getColor()))) {
      Territory moveFrom = Main.g.getWorld().getTerritories().get(message.getMoveFromTerritoryID());
      Territory moveTo = Main.g.getWorld().getTerritories().get(message.getMoveToTerritoryID());
      moveFrom.setNumberOfArmies(-message.getAmount());
      moveTo.setNumberOfArmies(message.getAmount());
      Main.b.updateLabelTerritory(moveFrom);
      Main.b.updateLabelTerritory(moveTo);
    }

  }
  /**
   * After receiving skip gamestate message, the turns will be changed.
   * 
   * @author qiychen
   * @param message  
   */
  private synchronized void handleSkipgamestateMessage(SkipgamestateMessage message) {
    Main.b.setTurns();
    if ((NetworkController.server != null && Main.g.getCurrentPlayer() instanceof AiPlayer)) {
      return;
    }
    if ((NetworkController.server != null
        && !(Main.g.getCurrentPlayer().getColor().toString().equals(message.getColor())))) {
      return;
    }
    if (!(this.player.getColor().toString().equals(message.getColor()))) {
      if (message.getGameState() == GameState.FORTIFY) {
        Main.b.setTurns();
        Main.g.furtherFortify();
      }
    }
  }

  
  /**
   * After receiving the send chat message, the message will be showed in the lobby.
   * 
   * @author Liwang 
   * @author qiychen
   * @param message
   *        
   * 
   */
  private void handleSendChatMessage(SendChatMessageMessage message) {
    String name = message.getUsername();
    String content = message.getMessage();
    if (!isHost) {
      controller.showMessage(name.toUpperCase() + " : " + content);
    }
  }

  /**
   * show private message in board gui.
   * @author qiychen
   * @param 
   */
  public void handleAllianceMessage(SendAllianceMessage message) {
    String privateUsername = message.getSender();
    String privateMessage = message.getContent();
    Main.b.showAllianceMessage(privateUsername.toUpperCase() + " : " + privateMessage);
  }

  /**
   * messages in boardgui will be showed.
   * @author qiychen
   * @param 
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
