package game;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author qiychen
 *
 */
public class Player {

  // type of player
  public static int PLAYER_HUMAN = 0;
  public static int PLAYER_AI_EASY = 1;
  public static int PLAYER_AI_MITT = 2;
  public static int PLAYER_AI_HARD = 3;
  private String name;
  private int armies;
  private int type;// human player or ai
  private HashMap<String, Territory> territories;
  private HashMap<String, Continent> continents;
  private ArrayList<Card> cards;
  private ArrayList<Player> eliminatedPlayers;


  // create a new player
  // player with name and total armies
  // player with the type from human or ai
  public Player(String name, int armies, int type) {
    this.name = name;
    this.armies = armies;
    this.type = type;
    territories = new HashMap<>();
    continents = new HashMap<>();
    cards = new ArrayList<>();
  }

  public Player(String name) {
    this.name = name;
    territories = new HashMap<>();
    continents = new HashMap<>();
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

  public HashMap<String, Territory> getTerritories() {
    return territories;
  }

  public void setTerritories(HashMap<String, Territory> territories) {
    this.territories = territories;
  }

  // return the total number of territories owned by player
  public int TerritoriesOwned() {
    return territories.size();
  }

  // player conquers a new territory, which need to be added in hashmap
  public void addTerritories(Territory t) {
    territories.put(t.getName(), t);
  }

  // player losts a territory, which need to be reduced in hashmap
  public void lostTerritories(Territory t) {
    territories.remove(t.getName());
  }

  public HashMap<String, Continent> getContinents() {
    return continents;
  }

  public void setContinents(HashMap<String, Continent> continents) {
    this.continents = continents;
  }

  public void addContinents(Continent c) {
    continents.put(c.getName().toString(), c);
  }

  public void lostContinents(Continent c) {
    continents.remove(c.getName().toString());
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public void setCards(ArrayList<Card> cards) {
    this.cards = cards;
  }

  public void addElimiatedPlayer(Player p) {
    eliminatedPlayers.add(p);
  }

}

