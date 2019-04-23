package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import main.Main;

public class AiPlayerHard extends Player implements AiPlayer {
  private HashMap<Integer, HashSet<Territory>> ownTerritories;
  private ArrayList<Integer> sortedValues;


  public AiPlayerHard() {
    super(AiPlayerNames.getRandomName(), PlayerColor.values()[Main.g.getPlayers().size()], Main.g);
    Main.g.addAiNames(this.getName());
  }

  /**
   * if no territory adjacent to another own territory is available, choose a territory with the
   * least number of neighbor territories
   */
  public void initialTerritoryDistribution() {
    int minHostile = 42;
    double maxPartOfContinent = 0.0;
    Territory selection = null;
    HashSet<Territory> minHostileTerritories = new HashSet<>();

    for (Territory t1 : this.getTerritories()) {
      if (minHostile >= t1.getHostileNeighbor().size()) {
        minHostile = t1.getHostileNeighbor().size();
        for (Territory t2 : t1.getHostileNeighbor()) {
          System.out.println(t2.getName());
          if (t2.getOwner() == null) {
            System.out.println(t2.getOwner() != null ? t2.getOwner().getName() : t2.getOwner());
            minHostileTerritories.add(t2);
          }
        }
      }

    }

    if (minHostileTerritories.size() == 0) {
      minHostile = 42;
      // territories with least number of hostile neighbors
      for (Territory t : Main.g.getWorld().getTerritories().values()) {
        if (t.getOwner() == null) {
          minHostile = minHostile > t.getHostileNeighbor().size() ? t.getHostileNeighbor().size()
              : minHostile;
        }
      }

      for (Territory t : Main.g.getWorld().getTerritories().values()) {
        if (t.getOwner() == null && t.getHostileNeighbor().size() == minHostile) {
          minHostileTerritories.add(t);
        }
      }
    }

    for (Territory t : minHostileTerritories) {
      System.out.println(t.getName());
    }

    for (Territory t : minHostileTerritories) {
      if ((1.0 / (double) Main.g.getWorld().getContinent().get(t.getContinent()).getTerritories()
          .size()) > maxPartOfContinent) {
        maxPartOfContinent = (1.0 / (double) Main.g.getWorld().getContinent().get(t.getContinent())
            .getTerritories().size());
        selection = t;
      }
    }
    System.out.println(selection);
    this.initialTerritoryDistribution(selection);

    Main.b.updateLabelTerritory(selection);

    Main.b.updateColorTerritory(selection);
    System.out.println(Main.g.getCurrentPlayer().getName() + "--"
        + Main.g.getCurrentPlayer().getColor() + "--" + selection);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Main.g.furtherInitialTerritoryDistribution();
  }

  // dummy method
  public void initialArmyDistribution() {
    for (Territory t : this.getTerritories()) {
      if (t.getHostileNeighbor().size() > 0) {
        int sum = 0;
        for (Territory tO : t.getHostileNeighbor()) {
          sum += tO.getNumberOfArmies();
        }
      }
    }

  }

  // dummy method
  public void armyDistribution() {


    Main.b.handleSkipGameState();
    this.attack();
  }

  // dummy method
  public void attack() {


    Main.b.handleSkipGameState();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.fortify();
  }

  /**
   * at first the territory that has no hostile neighbor territories and has more than one army on
   * it is chosen, if there is no such territory the territory with the highest difference between
   * territory and hostile neighbor is chosen
   */
  public void fortify() {
    Territory moveFrom = null;
    Territory moveTo = null;
    int numberToMove;

    for (Territory t : this.getTerritories()) {
      if (t.getHostileNeighbor().size() == 0 && t.getNumberOfArmies() > 1) {
        moveFrom = t;
      }
    }

    int max = 0;
    if (moveFrom != null) {
      for (Territory t : moveFrom.getOwnNeighbors()) {
        if (t.getHostileNeighbor().size() >= max) {
          max = t.getHostileNeighbor().size();
          moveTo = t;
        }
      }
    }

    if (moveTo != null) {
      if (super.fortify(moveFrom, moveTo, moveFrom.getNumberOfArmies() - 1)) {
        System.out.println("Territory from: " + moveFrom + " to: " + moveTo);
        Main.b.updateLabelTerritory(moveFrom);
        Main.b.updateLabelTerritory(moveTo);
      }
    }

    Main.b.handleSkipGameState();
  }
}
