package game;
import java.util.HashSet;

/**
 * @author pcoberge
 * Country defines one territory in the risk-game.
 * Each instance of Country possesses a name, id and card-symbol.  
 */

public class Territory {
  private String name;
  private int id;
  private CardSymbol sym;
  private Player owner;
  private Continente c;
  private HashSet<Territory> neighbor;
  
  public Territory(String name, int id, CardSymbol sym, Continente c) {
    this.name = name;
    this.id = id;
    this.sym = sym;
    this.c = c;
    this.owner = null;
  }
  
  /**
   * 
   * Getter and Setter methods
   */
  public void setOwner(Player p) {
    this.owner = p;
  }
  
  public Player getOwner() {
    return this.owner;
  }
  
  protected void setNeighbor(HashSet<Territory> neighbor) {
    this.neighbor = neighbor;
  }
  
  public HashSet<Territory> getNeighbor(){
    return this.neighbor;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public CardSymbol getSym() {
    return sym;
  }
  
  public Continente getContinent() {
    return c;
  }
  
  public String toString() {
    return this.getName();
  }
}

