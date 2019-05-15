package game;

import gui.controller.BoardRegion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import main.Main;

/**
 * Territory defines one territory in the risk-game. Each instance of Territory possesses a name,
 * id, number of armies, card-symbol, player, continent, neighbor territories and a board region.
 * 
 * @author pcoberge
 */

public class Territory implements Serializable{
  
  private static final long serialVersionUID = 1L;
  private String name;
  private int id;
  private int numberOfArmies;
  private CardSymbol sym;
  private Player owner;
  private Continente continent;
  private HashSet<Territory> neighbor;
  private BoardRegion region;
  
  /**************************************************
   *                                                *
   *                    Constuctor                  *
   *                                                *
   *************************************************/

  /**
   * Contructor.
   * 
   * @param name = name of this territory
   * @param id = unique number in risk-game
   * @param sym = symbol on risk cards
   * @param c = continent territory belongs to
   */
  public Territory(String name, int id, CardSymbol sym, Continente c) {
    this.name = name;
    this.id = id;
    this.sym = sym;
    this.continent = c;
    this.owner = null;
  }

  
  /**************************************************
   *                                                *
   *                Getter and Setter               *
   *                                                *
   *************************************************/

  /**
   * Getter-method.
   * 
   * @return BoardRegion of this territory
   */
  public BoardRegion getBoardRegion() {
    return this.region;
  }

  /**
   * Setter-method.
   * 
   * @param b = BoardRegion that consists of board GUI elements like region and label
   */
  public void setBoardRegion(BoardRegion b) {
    this.region = b;
  }

  /**
   * Getter-method.
   * 
   * @return continent = (enum) this territory belongs to
   */
  public Continente getContinente() {
    return continent;
  }

  /**
   * Getter-method.
   * 
   * @return continent = this territory belongs to
   */
  public Continent getContinent() {
    return Main.g.getWorld().getContinent().get(continent);
  }

  /**
   * Getter-method.
   * 
   * @return id of a territory
   */

  public int getId() {
    return id;
  }

  /**
   * Getter-method.
   * 
   * @return String = name of a territory
   */
  public String getName() {
    return name;
  }

  /**
   * Getter-method.
   * 
   * @return a HashSet of territories 
   */
  public HashSet<Territory> getNeighbor() {
    return this.neighbor;
  }

  /**
   * Setter-method.
   * 
   * @param neighbor = all territories the instance has a neighbor-relationship to
   */
  protected void setNeighbor(HashSet<Territory> neighbor) {
    this.neighbor = neighbor;
  }

  /**
   * Getter-method.
   * 
   * @return ArrayList of Territories that do not belong to the same player
   */
  public ArrayList<Territory> getHostileNeighbor() {
    ArrayList<Territory> hostileNeighbor = new ArrayList<>();
    for (Territory t : this.getNeighbor()) {
      if (this.getOwner() == null || !this.getOwner().equals(t.getOwner())) {
        hostileNeighbor.add(t);
      }
    }
    return hostileNeighbor;
  }

  /**
   * Getter-method.
   * 
   * @return ArrayList of Territories that belong to the same owner
   */
  public ArrayList<Territory> getOwnNeighbors() {
    ArrayList<Territory> hostileNeighbor = new ArrayList<>();
    for (Territory t : this.getNeighbor()) {
      if (t.getOwner() != null && this.getOwner().equals(t.getOwner())) {
        hostileNeighbor.add(t);
      }
    }
    return hostileNeighbor;
  }

  /**
   * Getter-method.
   * 
   * @return number of armies at this territory
   */
  public int getNumberOfArmies() {
    return this.numberOfArmies;
  }

  /**
   * Setter-method.
   * 
   * @param amount = number of armies that should be set to the chosen territory
   */
  public void setNumberOfArmies(int amount) {
    this.numberOfArmies += amount;
  }

  /**
   * Setter-method.
   * 
   * @param amount = number of armies in total
   */
  public void setNumberOfArmies2(int amount) {
    this.numberOfArmies = amount;
  }

  /**
   * Setter-method.
   * 
   * @param amount = number of armies to reduce from numberOfArmies
   */
  public void setReducedNumberOfArmies(int amount) {
    this.numberOfArmies -= amount;
  }

  /**
   * Getter-method.
   * 
   * @return Player = owner of a territory
   */
  public Player getOwner() {
    return this.owner;
  }

  /**
   * Setter-method.
   * 
   * @param p = owner of a territory
   */
  public void setOwner(Player p) {
    this.owner = p;
  }

  /**
   * Getter-method.
   * 
   * @return symbol this territory is connected with on the risk cards
   */
  public CardSymbol getSym() {
    return sym;
  }

  @Override
  public String toString() {
    return this.getName();
  }
}

