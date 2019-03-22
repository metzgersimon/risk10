package game;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author qiychen
 *
 */
public class Player {

  public static int PLAYER_HUMAN = 0;
  public static int PLAYER_AI_EASY = 1;
  public static int PLAYER_AI_MITT = 2;
  public static int PLAYER_AI_HARD = 3;
  private String name;
  private int armies;
  private int type;// human player or ai
  private ArrayList<Territory> territories;
  private ArrayList<Continent> continents;
  private ArrayList<Card> cards;
  private ArrayList<Player> eliminatedPlayers;

  /**
   * create a new player
   * 
   * @param name
   * @param armies
   * @param type
   */
  public Player(String name, int armies, int type) {
    this.name = name;
    this.armies = armies;
    this.type = type;
    territories = new ArrayList<>();
    continents = new ArrayList<>();
    cards = new ArrayList<>();
  }

  public Player(String name) {
    this.name = name;
    territories = new ArrayList<>();
    continents = new ArrayList<>();
    cards = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getArmies() {
    return armies;
  }

  public void setArmies(int armies) {
    this.armies = armies;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public ArrayList<Territory> getTerritories() {
    return territories;
  }

  public void setTerritories(ArrayList<Territory> territories) {
    this.territories = territories;
  }

  /**
   * 
   * @returnthe total number of territories owned by player
   */
  public int TerritoriesOwned() {
    return territories.size();
  }

  /**
   * player conquers a new territory, which need to be added in the list
   * 
   * @param t
   */
  public void addTerritories(Territory t) {
    territories.add(t);
  }

  /**
   * player losts a territory, which need to be reduced from the list
   * 
   * @param t
   */
  public void lostTerritories(Territory t) {
    territories.remove(t);
  }

  public ArrayList<Continent> getContinents() {
    return continents;
  }

  public void setContinents(ArrayList<Continent> continents) {
    this.continents = continents;
  }

  public void addContinents(Continent c) {
    continents.add(c);
  }

  public void lostContinents(Continent c) {
    continents.remove(c);
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public void setCards(ArrayList<Card> cards) {
    this.cards = cards;
  }

  /**
   * if player defeated another player, this player will be add to eliminatedPlayers
   * 
   * @param p
   */
  public void addElimiatedPlayer(Player p) {
    eliminatedPlayers.add(p);
  }

  /**
   * if player owns the territory, which is showed in trated cards, 2 armies will be added in this
   * territory
   * 
   * @param c1
   * @param c2
   * @param c3
   */
  public void tradeCards(Card c1, Card c2, Card c3) {
    if (territories.contains(c1.getTerritory())) {
      c1.getTerritory().setNumberOfArmies(2);
    } else if (territories.contains(c2.getTerritory())) {
      c2.getTerritory().setNumberOfArmies(2);
    } else if (territories.contains(c3.getTerritory())) {
      c3.getTerritory().setNumberOfArmies(2);
    }
    cards.remove(c1);
    cards.remove(c2);
    cards.remove(c3);
    cards.trimToSize();
  }

}

