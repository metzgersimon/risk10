package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;
import gui.BoardController;
import main.Main;
import network.Parameter;
import network.client.Client;
import network.client.GameFinder;
import network.server.Server;

public class Game {
  public final static int MAX_PLAYERS = 6;
  // public final static int NEW_GAME = 0;
  // public final static int PLACE_ARMIES = 1;
  // public final static int ATTACKING = 2;
  // public final static int FORTIFY = 3;
  // public final static int GAME_OVER = 4;

  private World w;

  private ArrayList<Player> players;
  private LinkedList<Card> cards;
  private ArrayList<Continent> continents;
  private ArrayList<Territory> territories;
  private Player currentPlayer;
  private GameState gameState;
  public static int attackWon;
  public static int defendWon;
  public HashMap<String, Integer> territoryStats;
  public HashMap<String, Integer> cardStats;
  private HashSet<String> aiNames = new HashSet<String>();



  /** variables for join and host game */
  private Server server;
  private GameFinder gameFinder;
  private Client client;
  private GameFinder gameFinderHost;

  /**
   * 
   * @author qiychen Constructor
   */
  public Game() {
    players = new ArrayList<>();
    this.w = new World();

    currentPlayer = null;
    gameState = GameState.NEW_GAME;
    // cards = new CardDeck().shuffle();



  }

  /**
   * Add a new player in the list
   * 
   * @author qiychen
   * @param name
   * @return
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
   * Reomove a new player from the list
   * 
   * @author liwang
   * @param name
   * @return
   */
  public void removePlayer() {
    if (gameState == GameState.NEW_GAME) {
      players.remove(players.size() - 1);
    }
  }



  /**
   * Set the initial order of Players
   * 
   * @author liwang
   * @param
   * @return
   */
  public Player setPlayerOrder() {

    return players.get(randomNumber());
  }

  /**
   * generate a number between 0 and number of players -1
   * 
   * @author liwang
   * @param
   * @return int the number of Player which starts first
   */
  public int randomNumber() {
    // generate a number between 0 and number of players -1
    return (int) (Math.random() * players.size());
  }


  public void startGame() {

  }

  /**
   * determine the current player
   * 
   * @author qiychen
   * @param name
   * @return
   */
  public Player getCurrentPlayer(String name) {
    for (int i = 0; i < players.size(); i++) {
      if (players.get(i).getName().equals(name)) {
        currentPlayer = players.get(i);
      }
    }
    return currentPlayer;

  }

  public void initGame() {
    // Compute number of armies
    initNumberOfArmies();

    setCurrentPlayer(players.get(0));// currentPlayer = players.get(0);
    System.out.println("1" + getCurrentPlayer().getName());
    setGameState(GameState.INITIALIZING_TERRITORY);
    Main.b.prepareInitTerritoryDistribution();
    Main.b.displayGameState();


    // while next Player has army left
    // change BoardGUI --> current Player should only could choose his own territories
    // highlight his own territories and disable all others
    // initArmyDistribution()
    // update Board
    // change Player

    // while Game is running
    //
    // while armies left
    // change BoardGUI --> current Player should only could choose his own territories
    // highlight his own territories and disable all others
    // distributeArmies
    //
    // Anzahl Territorien aktueller Spieler
    // while GameState == Attack
    // change BoardGUI --> current Player should only could choose his own territories
    // highlight his own territories and all attackable neighbors
    // attack
    // update Board
    // Anzahl Territorien aktueller Spieler --> vergleichen --> ggfs. Karte aus LinkedList Cards
    // nehmen
    //
    // change BoardGUI --> update only current players territories, disable all others
    // fortify
    // update BoardGUI
    // change Player
  }

  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }

  public void setCurrentPlayer(Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public World getWorld() {
    return w;
  }

  public GameState getGameState() {
    return this.gameState;
  }

  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }

  public void setPlayers(ArrayList<Player> players) {
    this.players = players;
  }

  public Player getLastPlayer() {
    return players.get(players.size() - 1);
  }

  public ArrayList<Territory> getTerritories() {
    return territories;
  }

  public void setTerritories(ArrayList<Territory> territories) {
    this.territories = territories;
  }

  public void setCard(Card c) {
    this.cards.add(c);
  }

  public LinkedList<Card> getCards() {
    return this.cards;
  }

  public HashSet<String> getAiNames() {
    return aiNames;
  }

  public void addAiNames(String aiName) {
    this.aiNames.add(aiName);
  }

  /**
   * 
   * compute the initial number of armies 2 players - 40 3 players - 35 4 players - 30 5 players -
   * 25 6 players - 20
   */
  public void initNumberOfArmies() {
    int number = 50 - (this.players.size() * 5);
    for (int i = 0; i < this.players.size(); i++) {
      this.players.get(i).setNumberArmiesToDistribute(number);
    }
  }



  /**
   * @param attacker
   * @param defender
   * @param attack
   * @param defend
   * @param numberOfAttackers
   * @return true if attacker conquers the opponent territory
   */
  public boolean attack(Vector<Integer> attacker, Vector<Integer> defender, Territory attack,
      Territory defend, int numberOfAttackers) {
    switch (defender.size()) {
      case (2):
        if (attacker.size() >= 2) {
          System.out
              .println("Case2: attacker: " + attacker.get(1) + " defender: " + defender.get(1));;
          if (attacker.get(1) > defender.get(1)) {
            defend.setNumberOfArmies(-1);
          } else {
            attack.setNumberOfArmies(-1);
          }
        }
      case (1):
        System.out
            .println("Case1: attacker: " + attacker.get(0) + " defender: " + defender.get(0));;
        if (attacker.get(0) > defender.get(0)) {
          defend.setNumberOfArmies(-1);
        } else {
          attack.setNumberOfArmies(-1);
        }
    }

    if (defend.getNumberOfArmies() == 0) {
      System.out.println("defending territory is dead.");
      Player p = defend.getOwner();
      p.getTerritories().remove(defend);
      defend.setOwner(attack.getOwner());
      attack.getOwner().addTerritories(defend);
      updateLiveStatistics();
      checkAllPlayers();
      attack.setNumberOfArmies(-numberOfAttackers);
      defend.setNumberOfArmies(numberOfAttackers);
      Main.b.updateColorTerritory(defend);
      // int randomCard = (int)((Math.random()*Main.g.getCards().size()));
      // p.setCards(Main.g.getCards().get(randomCard));
      if (!this.getPlayers().contains(p)) {
        attack.getOwner().addElimiatedPlayer(p);
        attack.getOwner().setCards(p.getCards());
      }
      if (getPlayers().size() == 1) {
        // attacker wins game
        this.gameState = GameState.END_GAME;
      }
      return true;
    } else {
      if (!(this.getCurrentPlayer() instanceof AiPlayer)) {
        Main.b.updateDiceSlider(attack);
      }
      updateLiveStatistics();
      return false;
    }
  }



  /**
   * @author prto, @author smetzger
   * @param players
   * @return returns ArrayList of players who have 0 territories left and thus lost
   */
  // checks if all players still have armies left, otherwise return losing players
  public ArrayList<Player> checkAllPlayers() {
    ArrayList<Player> lostPlayers = new ArrayList<Player>();
    for (int i = 0; i < this.players.size(); i++) {
      if (this.players.get(i).getTerritories().size() == 0) {
        lostPlayers.add(this.players.get(i));
        this.players.remove(this.players.get(i));
      }
    }
    return lostPlayers;
  }

  // get amount of territories and cards owned for each player
  public void updateLiveStatistics() {
    territoryStats = new HashMap<String, Integer>();
    for (Player x : this.players) {
      territoryStats.put(x.getName(), x.getNumberOfTerritories());
    }

    cardStats = new HashMap<String, Integer>();
    for (Player x : this.players) {
      cardStats.put(x.getName(), x.getNumberOfCards());
    }
  }



  /**
   * Shows statistics when the game is over
   * 
   * @author prto
   */
  public void endGame() {
    String name;
    int numAttacks;
    int tConquered;
    // Get statistics from each player
    for (Player x : players) {
      name = x.getName();
      numAttacks = x.numberOfAttacks;
      tConquered = x.territoriesConquered;
      // TODO add into GUI
    }
  }

  public boolean unconqueredTerritories() {
    for (Territory t : this.w.getTerritories().values()) {
      if (t.getOwner() == null) {
        return true;
      }
    }
    return false;
  }

  public Player nextPlayer() {
    for (int i = 0; i < players.size() - 1; i++) {
      if (players.get(i).equals(currentPlayer)) {
        return (this.currentPlayer = players.get(i + 1));
      }
    }
    return (this.currentPlayer = players.get(0));
  }

  public Player getNextPlayer() {
    for (int i = 0; i < players.size() - 1; i++) {
      if (players.get(i).equals(currentPlayer)) {
        return this.players.get(i + 1);
      }
    }
    return players.get(0);
  }

  public boolean getRemainingInitialArmies() {
    for (int i = 0; i < this.players.size(); i++) {
      if (this.players.get(i).getNumberArmiesToDistibute() > 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * @author smetzger
   * @author pcoberge
   */
  public synchronized void furtherInitialTerritoryDistribution() {
    this.nextPlayer();
    if (this.unconqueredTerritories()) {
      Main.b.prepareInitTerritoryDistribution();
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        p.initialTerritoryDistribution();
        // try {
        // Thread.sleep(100);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
      }
    } else {
      this.setGameState(GameState.INITIALIZING_ARMY);
      Main.b.prepareArmyDistribution();
      Main.b.displayGameState();
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        p.initialArmyDistribution();
      }
    }
  }

  /**
   * @author smetzger
   * @author pcoberge
   */
  public synchronized void furtherInitialArmyDistribution() {
    this.nextPlayer();
    Main.b.prepareArmyDistribution();
    // if (this.getLastPlayer().getNumberArmiesToDistibute() != 0) {
    if (this.getRemainingInitialArmies()) {
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        p.initialArmyDistribution();

      }
    } else {
      this.setGameState(GameState.ARMY_DISTRIBUTION);
      this.getCurrentPlayer().computeAdditionalNumberOfArmies();
      // Main.b.prepareArmyDistribution();
      // Main.b.displayArmyDistribution();
      Main.b.displayGameState();
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        p.armyDistribution();
      }
    }
  }

  public synchronized void furtherFortify() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    System.out.println(this.getCurrentPlayer().getName());
    this.nextPlayer();
    System.out.println(this.getCurrentPlayer().getName());
    this.getCurrentPlayer().computeAdditionalNumberOfArmies();
    Main.b.prepareArmyDistribution();
    this.setGameState(GameState.ARMY_DISTRIBUTION);
    Main.b.displayGameState();
    if (this.getCurrentPlayer() instanceof AiPlayer) {
      // try {
      // Thread.sleep(1000);
      // } catch (InterruptedException e) {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }
      AiPlayer p = (AiPlayer) this.getCurrentPlayer();
      p.armyDistribution();
    }
  }


  /**
   * @author skaur
   * @param noOfPlayers no of selected players by the host this method creates an instance of server
   *        and starts the server thread on a specified port, after calling this methods host should
   *        join the game lobby (game lobby UI opens)
   */
  public void hostGame(Player hostPlayer, int noOfPlayers) {
    // start the server
    this.server = new Server(Parameter.PORT, noOfPlayers);
    // create client for the host palyer
    this.gameFinderHost = new GameFinder();
    // joinHostLobby(currentPlayer): call this method to jointheHostGameLobbyGUI;
  }

  /**
   * @author skaur this methods creates an instance of gamefinder class and starts looking for
   *         broadcasting server gameFinder class then creates a client for the current player
   */
  public void joinGameonDiscovery(Player player) {
    this.gameFinder = new GameFinder();
  }

  /**
   * @author skaur this methods creates an instance of Client class and starts directly connects to
   *         the broadcasting server
   * @param ip
   * @param port
   */
  public void joinGame(String ip, int port) {
    this.client = new Client(ip, port);
    this.client.start();
  }

  public Server getServer() {
    return server;
  }

  public void setServer(Server server) {
    this.server = server;
  }

  public GameFinder getGameFinder() {
    return gameFinder;
  }

  public void setGameFinder(GameFinder gameFinder) {
    this.gameFinder = gameFinder;
  }

  public GameFinder getGameFinderHost() {
    return this.gameFinderHost;
  }

}


