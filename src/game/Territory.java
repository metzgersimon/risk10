package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import gui.BoardRegion;

/**
 * @author pcoberge
 * 
 *         Territory defines one territory in the risk-game. Each instance of Territory possesses a
 *         name, id, number of armies, card-symbol, player, continent, neighbor territories and a
 *         board region.
 */

public class Territory {
  private String name;
  private int id;
  private int numberOfArmies;
  private CardSymbol sym;
  private Player owner;
  private Continente c;
  private Continent continent;
  private HashSet<Territory> neighbor;
  private BoardRegion r;

  /**
   * Contructor
   * 
   * @param name
   * @param id
   * @param sym = symbol on risk cards
   * @param c = continent territory belongs to
   */
  public Territory(String name, int id, CardSymbol sym, Continente c) {
    this.name = name;
    this.id = id;
    this.sym = sym;
    this.c = c;
    this.owner = null;
  }

  /**
   * @param p = owner of a territory Setter-method
   */
  public void setOwner(Player p) {
    this.owner = p;
  }

  /**
   * @return Player = owner of a territory Getter-method
   */
  public Player getOwner() {
    return this.owner;
  }

  /**
   * @param neighbor = all territories the instance has a neighbor-relationship to Setter-method
   */
  protected void setNeighbor(HashSet<Territory> neighbor) {
    this.neighbor = neighbor;
  }

  /**
   * @return HashSet<Territory> = all territories the instance has a neighbor-relationship to
   *         Getter-method
   */
  public HashSet<Territory> getNeighbor() {
    return this.neighbor;
  }

  public ArrayList<Territory> getHostileNeighbor() {
    ArrayList<Territory> hostileNeighbor = new ArrayList<>();
    for (Territory t : this.getNeighbor()) {
      if (this.getOwner() == null || !this.getOwner().equals(t.getOwner())) {
        hostileNeighbor.add(t);
      }
    }
    return hostileNeighbor;
  }

  public ArrayList<Territory> getHostileNeighbor(Player p) {
    this.setOwner(p);
    ArrayList<Territory> territories = this.getHostileNeighbor();
    this.setOwner(null);
    return territories;
  }

  /**
   * @return String = name of a territory Getter-method
   */
  public String getName() {
    return name;
  }

  /**
   * @param amount = number of armies that should be set to the chosen territory Setter-method
   */
  public void setNumberOfArmies(int amount) {
    this.numberOfArmies += amount;
  }

  /**
   * 
   * @param amount = number of armies in total
   */
  public void setNumberOfArmies2(int amount) {
    this.numberOfArmies = amount;
  }

  /**
   * 
   * @param amount = number of armies to reduce from numberOfArmies
   */
  public void setReducedNumberOfArmies(int amount) {
    this.numberOfArmies -= amount;
  }

  /**
   * @return number of armies at this territory Getter-method
   */
  public int getNumberOfArmies() {
    return this.numberOfArmies;
  }

  /**
   * @return id of a territory Getter-method
   */

  public int getId() {
    return id;
  }

  /**
   * @return symbol this territory is connected with on the risk cards Getter-method
   */
  public CardSymbol getSym() {
    return sym;
  }

  /**
   * @return continent this territory belongs to Getter-method
   */
  public Continente getContinent() {
    return c;
  }

  /**
   * @param b = BoardRegion that consists of board GUI elements like region and label Setter-method
   */
  public void setBoardRegion(BoardRegion b) {
    this.r = b;
  }

  /**
   * @return BoardRegion of this territory Getter-method
   */
  public BoardRegion getBoardRegion() {
    return this.r;
  }

  @Override
  public String toString() {
    return this.getName();
  }
}

