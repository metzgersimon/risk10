package game;
import java.util.HashSet;
import gui.BoardRegion;
import javafx.scene.layout.Region;

/**
 * @author pcoberge
 * Country defines one territory in the risk-game.
 * Each instance of Country possesses a name, id and card-symbol.  
 */

public class Territory {
  private String name;
  private int id;
  private int numberOfArmies;
  private CardSymbol sym;
  private Player owner;
  private Continente c;
  private HashSet<Territory> neighbor;
  private BoardRegion r;
  
  /**
   * Contructor
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
   * Setter-method
   * @param p = owner of a territory
   */
  public void setOwner(Player p) {
    this.owner = p;
  }
  
  /**
   * Getter-method
   * @return - Player = owner of a territory
   */
  public Player getOwner() {
    return this.owner;
  }
  
  /**
   * Setter-method
   * @param neighbor = all territories the instance has a neighbor-relationship to
   */
  protected void setNeighbor(HashSet<Territory> neighbor) {
    this.neighbor = neighbor;
  }
  
  /**
   * Getter-method
   * @return HashSet<Territory> = all territories the instance has a neighbor-relationship to
   */
  public HashSet<Territory> getNeighbor(){
    return this.neighbor;
  }

  /**
   * Getter-method
   * @return String = name of a territory
   */
  public String getName() {
    return name;
  }

  public void setNumberOfArmies(int amount) {
    this.numberOfArmies += amount;
  }
  
  public int getNumberOfArmies() {
    return this.numberOfArmies;
  }
 
  /**
   * Getter-method
   * @return int = id of a territory
   */
  public int getId() {
    return id;
  }

  /**
   * Getter-method
   * @return CardSymbol = symbol this territory is connected with on the risk cards
   */
  public CardSymbol getSym() {
    return sym;
  }
  
  /**
   * Getter-method
   * @return Continente = continent the territory belongs to
   */
  public Continente getContinent() {
    return c;
  }
  
  public void setBoardRegion(BoardRegion b) {
    this.r = b;
  }
  
  public BoardRegion getBoardRegion() {
    return this.r;
  }
  
  @Override
  public String toString() {
    return this.getName();
  }
}

