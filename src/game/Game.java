package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import javafx.application.Platform;
import main.Main;

public class Game implements Serializable {

  private World w;
  private ArrayList<Player> allPlayers;
  private ArrayList<Player> players;
  private boolean isNetworkGame;
  private boolean showTutorialMessages;
  private GameState gameState;
  private HashSet<String> aiNames = new HashSet<String>();
  private LinkedList<Card> cards;
  private Player currentPlayer;


  /**************************************************
   * * Constuctor * *
   *************************************************/

  /**
   * @author qiychen
   * 
   *         override default-constructor
   */
  public Game() {
    players = new ArrayList<>();
    allPlayers = new ArrayList<Player>();
    this.w = new World();
    currentPlayer = null;
    gameState = GameState.NEW_GAME;
  }


  /**************************************************
   * * Getter and Setter * *
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
    return w;
  }

  public boolean isShowTutorialMessages() {
    return showTutorialMessages;
  }

  public void setShowTutorialMessages(boolean showTutorialMessages) {
    this.showTutorialMessages = showTutorialMessages;
  }


  /**************************************************
   * * (more complex) Methods to handle Game-Attributes* *
   *************************************************/

  /**
   * @author qiychen
   * @param name
   * @return Add a new player in the list
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
   * @author liwang
   * @param name
   * @return Reomove a new player from the list
   */
  public void removePlayer() {
    if (gameState == GameState.NEW_GAME) {
      players.remove(players.size() - 1);
    }
  }

  /**
   * @author pcoberge
   * @author smetzger
   * @return
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
   * @author pcoberge
   * @author smetzger
   * @return Player 
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
   * @author pcoberge
   * 
   *         get to know if game should be closed, if only Ai-players are in-game
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
   * * Methods to initialize Game * *
   *************************************************/

  /**
   * @author pcoberge
   * @author smetzger
   * 
   */
  public void initGame() {
    initNumberOfArmies();
    initCardDeck();
    setCurrentPlayer(players.get(0));
    setGameState(GameState.INITIALIZING_TERRITORY);
    Main.b.prepareInitTerritoryDistribution();
  }

  /**
   * @author pcoberge
   * @author smetzger
   * 
   */
  public void initCardDeck() {
    this.cards = new CardDeck().shuffle();
  }

  /**
   * @author pcoberge
   * @author smetzger compute the initial number of armies 2 players - 40 3 players - 35 4 players -
   *         30 5 players - 25 6 players - 20
   */
  public void initNumberOfArmies() {
    int number = 50 - (this.players.size() * 5);
    for (int i = 0; i < this.players.size(); i++) {
      this.players.get(i).setNumberArmiesToDistribute(number);
    }
  }



  /**************************************************
   * * Methods to Manage Game * *
   *************************************************/

  /**
   * @author smetzger
   * @author pcoberge
   */
  public synchronized void furtherInitialTerritoryDistribution() {
    this.setNextPlayer();
    if (this.unconqueredTerritories()) {
      Main.b.prepareInitTerritoryDistribution();
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        Main.b.disableAll();
        p.initialTerritoryDistribution();
      } else {
        Main.b.enableAll();
      }
    } else

    {
      this.setGameState(GameState.INITIALIZING_ARMY);
      Main.b.prepareArmyDistribution();
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        p.initialArmyDistribution();
      } else {
        Main.b.enableAll();
      }
    }
  }

  /**
   * @author smetzger
   * @author pcoberge
   */
  public synchronized void furtherInitialArmyDistribution() {

    this.setNextPlayer();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Main.b.prepareArmyDistribution();
    if (this.getRemainingInitialArmies()) {
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        p.initialArmyDistribution();
      } else {
        Main.b.enableAll();
      }
    } else {
      this.setGameState(GameState.ARMY_DISTRIBUTION);
      this.getCurrentPlayer().computeAdditionalNumberOfArmies();
      Main.b.showMessage(Main.g.getCurrentPlayer().getName() + " receives "
          + Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + " armies.");
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        p.armyDistribution();
      } else {
        Main.b.enableAll();
      }
    }
  }

  /**
   * @author pcoberge
   * @author smetzger
   */
  public synchronized void furtherFortify() {
    if(!this.gameState.equals(GameState.END_GAME)) {
      if (this.getCurrentPlayer().getSuccessfullAttack()) {
        Card c = this.cards.getLast();
        this.cards.removeLast();
        this.getCurrentPlayer().addCard(c);
        if (!(this.getCurrentPlayer() instanceof AiPlayer)) {
          Main.cardC.insertCards(c);
        }
        this.getCurrentPlayer().setSuccessfullAttack(false);
        this.getCurrentPlayer().setFortify(false);
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
      this.getCurrentPlayer().setStartedDistribution(false);
      this.setNextPlayer();
      this.getCurrentPlayer().computeAdditionalNumberOfArmies();
      Main.b.showMessage(Main.g.getCurrentPlayer().getName() + " receives "
          + Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + " armies.");
      Main.b.prepareArmyDistribution();
      this.setGameState(GameState.ARMY_DISTRIBUTION);
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        p.armyDistribution();
      }
    }
  }

  /**
   * @author smetzger
   * @return
   */
  public boolean unconqueredTerritories() {
    for (Territory t : this.w.getTerritories().values()) {
      if (t.getOwner() == null) {
        return true;
      }
    }
    return false;
  }

  /**
   * @author pcoberge
   * @author smetzger
   * @return
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
   * @author prto
   * @author smetzger
   * @param players
   * @return returns ArrayList of players who have 0 territories left and thus lost checks if all
   *         players still have armies left, otherwise return losing players
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
   * * Methods to Manage Statistics * *
   *************************************************/

  /**
   * @author prto update amount of territories and cards owned for each player
   */
  public void updateLiveStatistics() {
    for (Player p : players) {
      p.setNumberOfTerritories(p.getNumberOfTerritories());
      p.setNumberOfCards(p.getNumberOfCards());
    }
  }

}
