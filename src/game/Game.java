package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import gui.controller.NetworkController;
import javafx.application.Platform;
import main.Main;

public class Game implements Serializable {

  private World world;
  private ArrayList<Player> allPlayers;
  private ArrayList<Player> players;
  private boolean isNetworkGame;
  private boolean showTutorialMessages;
  private GameState gameState;
  private HashSet<String> aiNames = new HashSet<String>();
  private LinkedList<Card> cards;
  private Player currentPlayer;


  /**************************************************
   *                                                *
   *                    Constuctor                  * 
   *                                                *
   *************************************************/

  /**
   * Override default-constructor.
   * 
   * @author qiychen         
   */
  public Game() {
    players = new ArrayList<>();
    allPlayers = new ArrayList<Player>();
    this.world = new World();
    currentPlayer = null;
    gameState = GameState.NEW_GAME;
  }


  /**************************************************
   *                                                *
   *                Getter and Setter               *
   *                                                *
   *************************************************/

  public ArrayList<Player> getAllPlayers() {
    return allPlayers;
  }

  public void setAllPlayers(ArrayList<Player> players) {
    this.allPlayers = players;
  }

  public void addToAllPlayers(Player player) {
    this.allPlayers.add(player);
  }

  public HashSet<String> getAiNames() {
    return aiNames;
  }

  public void addAiNames(String aiName) {
    this.aiNames.add(aiName);
  }

  public LinkedList<Card> getCards() {
    return this.cards;
  }

  public void setCard(Card c) {
    this.cards.addFirst(c);
  }

  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }

  public void setCurrentPlayer(Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public GameState getGameState() {
    return this.gameState;
  }

  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

  public Player getLastPlayer() {
    return players.get(players.size() - 1);
  }

  public boolean isNetworkGame() {
    return isNetworkGame;
  }

  public void setNetworkGame(boolean isNetworkGame) {
    this.isNetworkGame = isNetworkGame;
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }

  public void setPlayers(ArrayList<Player> players) {
    this.players = players;
  }

  public World getWorld() {
    return world;
  }

  public boolean isShowTutorialMessages() {
    return showTutorialMessages;
  }

  public void setShowTutorialMessages(boolean showTutorialMessages) {
    this.showTutorialMessages = showTutorialMessages;
  }


  /**************************************************
   *                                                *
   *            (more complex) Methods              *
   *            to handle Game-Attributes           *
   *                                                *
   *************************************************/

  /**
   * This method adds a new player to the list.
   * 
   * @author qiychen
   * @param player
   */
  public void addPlayer(Player p) {
    if (gameState == GameState.NEW_GAME) {
      for (int i = 0; i < players.size(); i++) {
        if (p.getName().equals(players.get(i).getName())) {
          p.setName(p.getName() + "1");
        }
      }
      players.add(p);
    }
  }

  /**
   * This method removes a new player from the list.
   * 
   * @author liwang
   * @param name 
   */
  public void removePlayer() {
    if (gameState == GameState.NEW_GAME) {
      players.remove(players.size() - 1);
    }
  }

  /**
   * This method represents the next player.
   * 
   * @author pcoberge
   * @author smetzger
   * @return Player which is the next player
   */
  public Player getNextPlayer() {
    for (int i = 0; i < players.size() - 1; i++) {
      if (players.get(i).equals(currentPlayer)) {
        return this.players.get(i + 1);
      }
    }
    return players.get(0);
  }

  /**
   * This method sets the next player.
   * 
   * @author pcoberge
   * @author smetzger
   * @return Player which is the new current player
   */
  public Player setNextPlayer() {
    for (int i = 0; i < players.size() - 1; i++) {
      if (players.get(i).equals(currentPlayer)) {
        return (this.currentPlayer = players.get(i + 1));
      }
    }
    return (this.currentPlayer = players.get(0));
  }

  /**
   * This method represents if game should be closed, because only Ai-players are in-game.
   * 
   * @author pcoberge
   * @return true or false
   */
  public boolean onlyAiPlayersLeft() {
    for (Player p : players) {
      if (!(p instanceof AiPlayer)) {
        return false;
      }
    }
    return true;
  }


  /**************************************************
   *                                                *
   *            Methods to initialize Game          * 
   *                                                *
   *************************************************/

  /**
   * This method initializes the initial game situation and chooses the first player.
   * 
   * @author pcoberge
   * @author smetzger
   */
  public void initGame() {
    initNumberOfArmies();
    initCardDeck();
    setCurrentPlayer(players.get(0));
    setGameState(GameState.INITIALIZING_TERRITORY);
    Main.b.prepareInitTerritoryDistribution();
  }

  /**
   * This method initializes a shuffled carddeck.
   * 
   * @author pcoberge
   * @author smetzger
   * 
   */
  public void initCardDeck() {
    this.cards = new CardDeck().shuffle();
  }

  /**
   * This method computes the initial number of armies. 
   * 2 players - 40 
   * 3 players - 35 
   * 4 players - 30
   * 5 players - 25
   * 6 players - 20
   *         
   * @author pcoberge
   * @author smetzger
   */
  public void initNumberOfArmies() {
    int number = 50 - (this.players.size() * 5);
    for (int i = 0; i < this.players.size(); i++) {
      this.players.get(i).setNumberArmiesToDistribute(number);
    }
  }

  
  /**************************************************
   *                                                *
   *            Methods to Manage Game              *
   *                                                *
   *************************************************/

  /**
   * This method represents choosing next player and preparing the gui for this player in initial territory distribution phase.
   * 
   * @author smetzger
   * @author pcoberge
   */
  public synchronized void furtherInitialTerritoryDistribution() {
     try {
     Thread.sleep(1000);
     } catch (InterruptedException e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
     }
    this.setNextPlayer();
    if (this.unconqueredTerritories()) {
      Main.b.prepareInitTerritoryDistribution();
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        Main.b.disableAll();
        p.initialTerritoryDistribution();
      }
    } else {
      this.setGameState(GameState.INITIALIZING_ARMY);
      Main.b.prepareArmyDistribution();
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        p.initialArmyDistribution();
      }
    }
  }

  /**
   * This method represents choosing next player and preparing the gui for this player in initial army distribution phase.
   * 
   * @author smetzger
   * @author pcoberge
   */
  public synchronized void furtherInitialArmyDistribution() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
}
    this.setNextPlayer();
    Main.b.prepareArmyDistribution();
    if (this.getRemainingInitialArmies()) {
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        p.initialArmyDistribution();
      }
    } else {
      this.setGameState(GameState.ARMY_DISTRIBUTION);
      this.getCurrentPlayer().computeAdditionalNumberOfArmies();
      Main.b.showMessage(Main.g.getCurrentPlayer().getName() + " receives "
          + Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + " armies.");
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        p.armyDistribution();
      }
    }
  }

  /**
   * This method represents assigning risk-card to a player when conquering a territory in his turn,
   * choosing next player and preparing the gui for this player in the army distribution phase.
   * 
   * @author pcoberge
   * @author smetzger
   */
  public synchronized void furtherFortify() {
    if (!this.gameState.equals(GameState.END_GAME)) {
      if (this.getCurrentPlayer().getSuccessfullAttack()) {
        Card c = this.cards.getLast();
        this.cards.removeLast();
        this.getCurrentPlayer().addCard(c);
        if (!(this.getCurrentPlayer() instanceof AiPlayer)) {
          Main.cardC.insertCards(c);
        }
      }
      try {
        Thread.sleep(100);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
      this.getCurrentPlayer().setSuccessfullAttack(false);
      this.getCurrentPlayer().setFortify(false);

      System.out.println(Main.g.getCurrentPlayer().getName());
      this.setNextPlayer();
      System.out.println(Main.g.getCurrentPlayer().getName());

      this.getCurrentPlayer().computeAdditionalNumberOfArmies();
      Main.b.showMessage(Main.g.getCurrentPlayer().getName() + " receives "
          + Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + " armies.");

      // client macht alles nicht weiter
      if (Main.g.isNetworkGame() && Main.g.getCurrentPlayer() instanceof AiPlayer
          && NetworkController.server == null) {
        System.out.println("client macht alles nicht weiter");
        return;
      }
      Main.b.prepareArmyDistribution();
      this.setGameState(GameState.ARMY_DISTRIBUTION);

      if (this.getCurrentPlayer() instanceof AiPlayer) {
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        System.out.println(" AI army distribution");
        p.armyDistribution();
      }
    }
  }
  
  /**
   * This method returns if there are territories left, that do not have an owner.
   * 
   * @author smetzger
   * @return true or false
   */
  public boolean unconqueredTerritories() {
    for (Territory t : this.world.getTerritories().values()) {
      if (t.getOwner() == null) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method returns whether the initial armie distribution phase is finished.
   * 
   * @author pcoberge
   * @author smetzger
   * @return true or false
   */
  public boolean getRemainingInitialArmies() {
    for (int i = 0; i < this.players.size(); i++) {
      if (this.players.get(i).getNumberArmiesToDistibute() > 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method checks if all players still have armies left.
   *
   * @author prto
   * @author smetzger
   * @param players
   * @return ArrayList of players who have 0 territories left and thus lost the game
   */
  public ArrayList<Player> checkAllPlayers() {
    ArrayList<Player> lostPlayers = new ArrayList<Player>();
    for (int i = 0; i < this.players.size(); i++) {
      if (this.players.get(i).getTerritories().size() == 0) {
        lostPlayers.add(this.players.get(i));
        allPlayers.add(this.players.get(i));
        this.players.remove(this.players.get(i));
      }
    }
    return lostPlayers;
  }


  /**************************************************
   *                                                *
   *          Methods to Manage Statistics          *                    
   *                                                *
   *************************************************/

  /**
   * This method updates amount of territories and cards owned for each player.
   * 
   * @author prto
   */
  public void updateLiveStatistics() {
    for (Player p : players) {
      p.setNumberOfTerritories(p.getNumberOfTerritories());
      p.setNumberOfCards(p.getNumberOfCards());
    }
  }
}
