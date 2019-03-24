package game;

import java.util.ArrayList;

public class Game {
  public final static int MAX_PLAYERS = 6;
  public final static int NEW_GAME = 0;
  public final static int PLACE_ARMIES = 1;
  public final static int ATTACKING = 2;
  public final static int FORTIFY = 3;
  public final static int GAME_OVER = 4;
  
  private World w;

  private ArrayList<Player> players;
  private ArrayList<Card> cards;
  private ArrayList<Continent> continents;
  private ArrayList<Territory> territories;
  private Player currentPlayer;
  private int gameState;


  /**
   * Constructor
   */
  public Game() {
    players = new ArrayList<>();
    territories = new ArrayList<>(); // To do
    continents = new ArrayList<>();
    cards = new ArrayList<>();
    currentPlayer = null;
    gameState = NEW_GAME;
    this.w = new World();

  }

  /**
   * Add a new player in the list
   * 
   * @param name
   * @return
   */
  public boolean addPlayer(String name) {
    if (gameState == NEW_GAME) {
      for (int i = 0; i < players.size(); i++) {
        if (name.equals(players.get(i).getName())) {
          return false;
        }
      }
      Player newPlayer = new Player(name);
      players.add(newPlayer);
      return true;

    } else {
      return false;
    }
  }

  public void startGame() {

  }

  /**
   * determine the current player
   * 
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
  
  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }

  public World getWorld() {
    return w;
  }

  public int getGameState() {
    return this.gameState;
  }

}

