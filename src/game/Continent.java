package game;

import java.util.HashSet;

public class Continent {
  private HashSet<Territory> territories;
  private Continente name;
  private int value;
  
  public Continent(Continente name, int value, HashSet<Territory> territories) {
    this.name = name;
    this.value = value;
    this.territories = territories;
  }

  /**
   * @param -
   * @return - method returns the territories that belong to the instance of Continent
   */
  public HashSet<Territory> getTerritories() {
    return territories;
  }

  /**
   * @param -
   * @return - method returns the name of the instance of Continent
   */
  public Continente getName() {
    return name;
  }

  /**
   * @param -
   * @return - method returns the bonus armies a player receives if he
   *           owns all territories of a continent
   */
  public int getValue() {
    return value;
  }  
}