package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import main.Main;

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

  private Game g;
  private String name;
  private int armies;
  private boolean isAi = false;// human player or ai
  private PlayerColor color;
  private HashSet<Territory> territories;
  private HashSet<Continent> continents;
  private ArrayList<Card> cards;
  private ArrayList<Player> eliminatedPlayers;
  private int numberArmiesToDistribute;
  private int tradedCardSets;
  private int numberOfTerritories;
  private int numberOfCards;
  private int valueActuallyTradedIn;
  public static int territoriesConquered;
  public static int numberOfAttacks;
  public static int rank;
  public static int sessionWins;

  /**
   * create a new player
   * 
   * @param name
   * @param armies
   * @param type
   */
  public Player(String name, int armies, PlayerColor color) {
    this.name = name;
    this.armies = armies;
    this.color = color;
    territories = new HashSet<>();
    continents = new HashSet<>();
    cards = new ArrayList<>();
    this.tradedCardSets = 0;
    this.g = Main.g;
    this.numberOfTerritories = 0;
    this.numberOfCards = 0;
    territoriesConquered = 0;
    numberOfAttacks = 0;
    rank = 0;
    sessionWins = 0;
  }

  public Player(String name, PlayerColor color) {
    this.name = name;
    this.color = color;
    territories = new HashSet<>();
    continents = new HashSet<>();
    cards = new ArrayList<>();
    this.eliminatedPlayers = new ArrayList<>();
    this.g = Main.g;
    this.numberOfTerritories = 0;
    this.numberOfCards = 0;
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

  public boolean getIsAi() {
    return isAi;
  }

  public void setIsAi(boolean isAi) {
    this.isAi = isAi;
  }

  public HashSet<Territory> getTerritories() {
    return territories;
  }

  public void setTerritories(HashSet<Territory> territories) {
    this.territories = territories;
  }


  public int getTradedCardSets() {
    return tradedCardSets;
  }

  public void setTradedCardSets(int tradedCardSets) {
    this.tradedCardSets = tradedCardSets;
  }



  public int getValueActuallyTradedIn() {
    return valueActuallyTradedIn;
  }

  public void setValueActuallyTradedIn(int valueActuallyTradedIn) {
    this.valueActuallyTradedIn = valueActuallyTradedIn;
  }

  public int getNumberOfTerritories() {
    return numberOfTerritories;
  }

  public void setNumberOfTerritories(int numberOfTerritories) {
    this.numberOfTerritories = numberOfTerritories;
  }

  public int getNumberOfCards() {
    return numberOfCards;
  }

  public void setNumberOfCards(int numberOfCards) {
    this.numberOfCards = numberOfCards;
  }
  
  public int getRank() {
    return rank;
  }
  
  public int getNumberOfAttacks() {
    return numberOfAttacks;
  }
  
  public int getTerritoriesConquered() {
    return territoriesConquered;
  }
  
  public int getSessionWins() {
    return sessionWins;
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
    boolean newContinent = true;
    for (Territory territory : g.getWorld().getContinent().get(t.getContinent()).getTerritories()) {
      if (!this.getTerritories().contains(territory)) {
        newContinent = false;
      }
    }

    if (newContinent) {
      this.addContinents(g.getWorld().getContinent().get(t.getContinent()));
    }
  }

  /**
   * player losts a territory, which need to be reduced from the list
   * 
   * @param t
   */
  public void lostTerritories(Territory t) {
    territories.remove(t);
    if (this.getContinents().contains(t.getContinent())) {
      this.lostContinents(g.getWorld().getContinent().get(t.getContinent()));
    }
  }

  public HashSet<Continent> getContinents() {
    return continents;
  }

  public void setContinents(HashSet<Continent> continents) {
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

  public PlayerColor getColor() {
    return color;
  }

  public ArrayList<Player> getEliminatedPlayers() {
    return eliminatedPlayers;
  }

  public void setCards(ArrayList<Card> cards) {
    this.cards = cards;
  }

  public void setColor(PlayerColor color) {
    this.color = color;
  }

  public void setEliminatedPlayers(ArrayList<Player> eliminatedPlayers) {
    this.eliminatedPlayers = eliminatedPlayers;
  }

  public int getNumberArmiesToDistibute() {
    return this.numberArmiesToDistribute;
  }

  public void setNumberArmiesToDistribute(int amount) {
    this.numberArmiesToDistribute = amount;
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
   * @author qiychen
   * @param c1 card1
   * @param c2 card2
   * @param c3 card3
   * @return the number of armies received after trade cards
   */
  public int tradeCards(Card c1, Card c2, Card c3) {
    int armies = 0;
    int number = this.getTradedCardSets();
    if (c1.canBeTraded(c2, c3)) {
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
        default:
          armies = 15 + (number - 5) * 5;
      }

      if (this.getTerritories().contains(c1.getTerritory())) {
        c1.getTerritory().setNumberOfArmies(2);
        Main.b.updateLabelTerritory(c1.getTerritory());
      }
      if (this.getTerritories().contains(c2.getTerritory())) {
        c2.getTerritory().setNumberOfArmies(2);
        Main.b.updateLabelTerritory(c2.getTerritory());
      }
      if (this.getTerritories().contains(c3.getTerritory())) {
        c3.getTerritory().setNumberOfArmies(2);
        Main.b.updateLabelTerritory(c3.getTerritory());
      }

      this.setTradedCardSets(number++);
      this.valueActuallyTradedIn = armies;
      this.getCards().remove(c1);
      this.getCards().remove(c2);
      this.getCards().remove(c3);
    }
    return armies;
  }


  /**
   * @author pcoberge
   * 
   * @param amount of armies that shall be set at territory t
   * @param t is the territory that should receive the amount of armies
   * @return boolean in order to show whether the distribution has been successful or not
   * 
   *         Precondition: only own territories can be chosen, player has enough armies left
   */
  public boolean armyDistribution(int amount, Territory t) {
    if (t.getOwner().equals(this) && this.numberArmiesToDistribute >= amount) {
      t.setNumberOfArmies(amount);
      this.numberArmiesToDistribute -= amount;
      return true;
    } else {
      return false;
    }
  }

  /**
   * @author liwang
   * 
   * @param t is the territory which is selected
   * @return boolean in order to show whether the distribution has been successful or not
   * 
   *         Precondition: only free territories can be chosen
   */
  public boolean initialTerritoryDistribution(Territory t) {
    if (t.getOwner() == null) {
      t.setOwner(this);
      this.addTerritories(t);
      this.numberArmiesToDistribute -= 1;
      t.setNumberOfArmies(1);
      return true;
    } else {
      return false;
    }
  }


  /**
   * @author pcoberge
   * 
   * @return number of armies the player receives at the beginning of the current turn
   */
  public int computeAdditionalNumberOfArmies() {
    int result = 0;
    // player receives for three territories one army
    result += this.getTerritories().size() / 3;

    // player receives for each continent the number of additional armies
    for (Continent c : this.getContinents()) {
      result += c.getValue();
    }

    // player receives armies for each card set depending on the number of previous traded sets
    result += valueActuallyTradedIn;
    this.valueActuallyTradedIn = 0;
    if (result < 3) {
      this.numberArmiesToDistribute = 3;
      return 3;
    } else {
      this.numberArmiesToDistribute = result;
      return result;
    }
  }
}


