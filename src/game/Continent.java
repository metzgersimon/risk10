package game;

import java.util.HashSet;

/**
 * @author pcoberge
 * 
 *         Continent describes the objects that consist of a few territories. Each instance of
 *         Continent has a hashset of territories that belong to it, a unique name and a value how
 *         many armies this continent is worth
 *
 */
public class Continent {
  private HashSet<Territory> territories;
  private Continente name;
  private int value;

  /**
   * Constructor
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
   * @return all territories that belong to the instance of Continent Getter-method
   */
  public HashSet<Territory> getTerritories() {
    return territories;
  }

  /**
   * @return name of the instance of Continent Getter-method
   */
  public Continente getName() {
    return name;
  }

  /**
   * @return bonus armies a player receives if he owns all territories of this continent
   *         Getter-method
   */
  public int getValue() {
    return value;
  }
}
