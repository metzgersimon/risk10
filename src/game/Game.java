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


  /**@author qiychen
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
   * @author qiychen
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

  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }

  public World getWorld() {
    return w;
  }

  public int getGameState() {
    return this.gameState;
  }

  /**
   * @author qiychen
   * @param c1 card1
   * @param c2 card2
   * @param c3 card3
   * @return the number of armies received after trade cards
   */
  public int tradeCards(Card c1, Card c2, Card c3) {
    int armies = 0;
    int number = currentPlayer.getTradeNumber();
    if (canbeTraded(c1, c2, c3)) {
      switch (number) {
        case 0:
          armies = 4;
          break;
        case 1:
          armies = 6;
          break;
        case 2:
          armies = 8;
          break;
        case 3:
          armies = 10;
          break;
        case 4:
          armies = 12;
          break;
        case 5:
          armies = 15;
          break;

      }

      if (number > 5) {
        armies = 15 + (number - 5) * 5;
      }
      currentPlayer.setTradeNumber(number++);
    }
    return armies;
  }

  /**
   * @author qiychen
   * @param c1 card1
   * @param c2 card2
   * @param c3 card3
   * @return whether 3 cards can be traded or not
   */
  public boolean canbeTraded(Card c1, Card c2, Card c3) {
    CardSymbol sym1 = c1.getTerritory().getSym();
    CardSymbol sym2 = c2.getTerritory().getSym();
    CardSymbol sym3 = c3.getTerritory().getSym();
    // same artillery pictures without wildcards
    boolean same = (sym1 == sym2) && (sym2 == sym3);
    // all types of cards without wildcards
    boolean different = (sym1 != sym2) && (sym2 != sym3) && (sym1 != sym3);
    // same artillery pictures with one wildcard
    boolean same2 = ((sym1 == sym2) && (sym3 == CardSymbol.WILDCARD))
        || ((sym1 == sym3) && (sym2 == CardSymbol.WILDCARD))
        || ((sym2 == sym3) && (sym1 == CardSymbol.WILDCARD));
    // same artillery pictures with two wildcards, or all types of cards with two wildcards
    boolean same3 = ((sym1 == sym2) && (sym1 == CardSymbol.WILDCARD))
        || ((sym1 == sym3) && (sym1 == CardSymbol.WILDCARD))
        || ((sym2 == sym3) && (sym2 == CardSymbol.WILDCARD));
    // all types of cards with one wildcard
    boolean different2 =
        (sym1 == CardSymbol.WILDCARD && sym2 != sym3 && sym2 != CardSymbol.WILDCARD)
            || (sym2 == CardSymbol.WILDCARD && sym1 != sym3 && sym1 != CardSymbol.WILDCARD)
            || (sym3 == CardSymbol.WILDCARD && sym1 != sym2 && sym1 != CardSymbol.WILDCARD);
    boolean result = same || same2 || same3 || different || different2;
    return result;
  }

}


