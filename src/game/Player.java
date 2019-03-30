package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
  private int type;// human player or ai
  private PlayerColor color;
  private HashSet<Territory> territories;
  private HashSet<Continent> continents;
  private ArrayList<Card> cards;
  private ArrayList<Player> eliminatedPlayers;
  private int numberOfcardsWithTerritories;
  private int numberArmiesToDistribute;
  private int tradedCardSets;//
  private int valueActuallyTradedIn;

  /**
   * create a new player
   * 
   * @param name
   * @param armies
   * @param type
   */
  public Player(String name, int armies, int type, PlayerColor color, Game g) {
    this.name = name;
    this.armies = armies;
    this.type = type;
    this.color = color;
    territories = new HashSet<>();
    continents = new HashSet<>();
    cards = new ArrayList<>();
    this.tradedCardSets = 0;
    this.g = g;
  }

  public Player(String name, Game g) {
    this.name = name;
    territories = new HashSet<>();
    continents = new HashSet<>();
    cards = new ArrayList<>();
    this.g = g;
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

  public HashSet<Territory> getTerritories() {
    return territories;
  }

  public void setTerritories(HashSet<Territory> territories) {
    this.territories = territories;
  }


  public int getTradeNumber() {
    return tradedCardSets;
  }

  public void setTradeNumber(int tradedCardSets) {
    this.tradedCardSets = tradedCardSets;
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

  public int getValueActuallyTradedIn() {
    return this.valueActuallyTradedIn;
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
   * @param c1 in GUI selected Card
   * @param c2 in GUI selected Card
   * @param c3 in GUI selected Card
   * 
   *        Precondition: Cards are a valid set --> concern in GUI
   */
  public void tradeCards(Card c1, Card c2, Card c3) {
    if (c1.validCardSet(c2, c3)) {
      if (territories.contains(c1.getTerritory())) {
        c1.getTerritory().setNumberOfArmies(2);
        this.numberOfcardsWithTerritories++;
      } else if (territories.contains(c2.getTerritory())) {
        c2.getTerritory().setNumberOfArmies(2);
        this.numberOfcardsWithTerritories++;
      } else if (territories.contains(c3.getTerritory())) {
        c3.getTerritory().setNumberOfArmies(2);
        this.numberOfcardsWithTerritories++;
      }

      this.tradedCardSets++;
      for (int i = 0; i <= this.tradedCardSets; i++) {
        if (i <= 5) {
          this.valueActuallyTradedIn += 2;
        } else if (i == 6) {
          this.valueActuallyTradedIn += 3;
        } else if (i > 6) {
          this.valueActuallyTradedIn += 5;
        }
      }
      cards.remove(c1);
      cards.remove(c2);
      cards.remove(c3);
      cards.trimToSize();
    }
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
    if (result < 3 && this.numberOfcardsWithTerritories == 0) {
      this.numberArmiesToDistribute = 3;
      return 3;
    } else {
      this.numberArmiesToDistribute = result;
      return result;
    }
  }



  public void attack(Territory own, Territory opponent) {
    /**
     * if(!this.getTerritories().contains(own)) {
     * 
     * } if(this.getTerritories().contains(own) && this.getTerritories().contains(opponent)) {
     * 
     * } if(!this.getTerritories().contains(own) && this.getTerritories().contains(opponent)) {
     * 
     * } }
     **/
    if (this.getTerritories().contains(own) && !this.getTerritories().contains(opponent)) {

    } else {
      // Error message
    }
  }
}


