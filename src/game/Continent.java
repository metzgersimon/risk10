package game;

import java.util.HashSet;

/**
 * Continent describes the objects that consist of a few territories. Each instance of Continent has
 * a hashset of territories that belong to it, a unique name and a value how many armies this
 * continent is worth.
 * 
 * @author pcoberge
 */
public class Continent {
  private HashSet<Territory> territories;
  private Continente name;
  private int value;

  /**
   * Constructor.
   * 
   * @param name of this continent, defined by enum Continente
   * @param value of this continent, it is important in the distribution of armies phase
   * @param territories of this continent, all territories that belong to this continent
   */
  public Continent(Continente name, int value, HashSet<Territory> territories) {
    this.name = name;
    this.value = value;
    this.territories = territories;
  }

  /**
   * Getter-method.
   * 
   * @return all territories that belong to the instance of Continent
   */
  public HashSet<Territory> getTerritories() {
    return territories;
  }

  /**
   * Getter-method.
   * 
   * @return name of the instance of Continent
   */
  public Continente getName() {
    return name;
  }

  /**
   * Getter-method.
   * 
   * @return bonus armies a player receives if he owns all territories of this continent
   */
  public int getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Continent) {
      Continent c = (Continent) o;
      if (this.getName().equals(c.getName())) {
        return true;
      } else {
        return false;
      }
    }
    return false;
  }
}
