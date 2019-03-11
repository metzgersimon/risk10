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

  public HashSet<Territory> getTerritories() {
    return territories;
  }

  public Continente getName() {
    return name;
  }

  public int getValue() {
    return value;
  }  
}