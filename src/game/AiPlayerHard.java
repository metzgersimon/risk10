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
  private HashSet<Territory> possibleTerritories;


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
    int maxNeighbors = 0;
    int averageArmies = 0;
    int sum = 0;
    for (Territory t : this.getTerritories()) {
      if (t.getHostileNeighbor().size() > maxNeighbors) {
        sum = 0;
        for (Territory tO : t.getHostileNeighbor()) {
          sum += tO.getNumberOfArmies();
        }
        averageArmies = (int) (sum / t.getHostileNeighbor().size());
        if (averageArmies > t.getNumberOfArmies()) {
          maxNeighbors = t.getHostileNeighbor().size();
          if(super.armyDistribution(1, t)) {
            Main.b.updateLabelTerritory(t);
            Main.g.furtherInitialArmyDistribution();
            return;
          }
        }
      }
    }
    InitialArmyDistributionMedium();
  }

  // dummy method
  public void armyDistribution() {
    HashMap<Territory,Integer> hostileNeighbors = new HashMap<Territory,Integer>();
    
    
    if (this.getCards().size() >= 3) {
      for (int i = 0; i < this.getCards().size(); i++) {
        for (int j = i + 1; j < this.getCards().size() - 1; j++) {
          for (int k = j + 1; k < this.getCards().size() - 2; k++) {
            this.tradeCards(this.getCards().get(i), this.getCards().get(j), this.getCards().get(k));
          }
        }
      }
    }
    int armiesAttack = (int) Math.round((double) this.getNumberArmiesToDistibute()*(2.0/3.0));
    do {
      int sumArmies = 0;
      for(Territory t: this.getTerritories()) {
        for(Territory n: t.getHostileNeighbor()) {
          hostileNeighbors.put(n, 0);
        }
      }
      for(Territory t: hostileNeighbors.keySet()) {
        for(Territory n: t.getOwnNeighbors()) {
          sumArmies += n.getNumberOfArmies();
        }
        hostileNeighbors.put(t, sumArmies);
        sumArmies = 0;
      }
      
      int continentSize = 0;
      int ownTerritorySize = 0;
      int minDiff = 42;
      Continent preferredContinent;
      for(Continent c: Main.g.getWorld().getContinent().values()) {
        continentSize = c.getTerritories().size();
        if(!this.getContinents().contains(c)) {
          for(Territory t: c.getTerritories()) {
            if(t.getOwner().equals(this)) {
              ownTerritorySize++;
            }
          }
          if(continentSize - ownTerritorySize < minDiff) {
            minDiff = continentSize-ownTerritorySize;
            preferredContinent = c;
          }
        }
      }
      int max = 0;
      Territory selectedTerritory;
      for(Territory t: hostileNeighbors.keySet()) {
        if(t.getContinent().equals(preferredContinent)) {
          if(hostileNeighbors.get(t) > max) {
            max = hostileNeighbors.get(t);
            selectedTerritory = t;
          }
        }
      }
     
      
      for(Territory t: selectedTerritory.getOwnNeighbors()) {
        if(armiesAttack > 0) {
          if(super.armyDistribution(1, t)) {
            armiesAttack -= 1;
          }
        }
    }
    
    }while(armiesAttack > 0);

    int armiesDefend = this.getNumberArmiesToDistibute()-armiesAttack;
   
    int max;
    for (Territory t : this.getTerritories()) {
      max = 0;
      for (Territory tE : t.getHostileNeighbor()) {
        if (((t.getNumberOfArmies()) - (tE.getNumberOfArmies())) > max) {
          max = t.getNumberOfArmies() - tE.getNumberOfArmies();
          if (ownTerritories.containsKey(max)) {
            ownTerritories.get(max).add(t);
          } else {
            HashSet<Territory> tr = new HashSet<Territory>();
            tr.add(t);
            ownTerritories.put(max, tr);
          }
        }
      }
    }
    if (ownTerritories.size() > 0) {
      sortedValues = new ArrayList<Integer>(ownTerritories.keySet());
      Collections.sort(sortedValues, Collections.reverseOrder());
      while (armiesDefend > 0) {
        for (int i = 0; i < sortedValues.size(); i++) {
          for (Territory t : ownTerritories.get(sortedValues.get(i))) {
            super.armyDistribution(1, t);
            armiesDefend -= 1;
            System.out.println("Army distribution: " + t.getName());
            Main.b.updateLabelTerritory(t);
          }
        }
      }
    } else {
      greedyArmyDistribution();
      if (this.getNumberArmiesToDistibute() > 0) {
        System.out.println("Random army distribution");
        randomArmyDistribution();
      }
    }


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
  
  public void InitialArmyDistributionMedium() {

    int min = 0;
    Territory own = null;
    for (Territory t : this.getTerritories()) {
      for (Territory opponent : t.getHostileNeighbor()) {
        if (((t.getNumberOfArmies()) - (opponent.getNumberOfArmies())) <= min) {
          min = t.getNumberOfArmies() - opponent.getNumberOfArmies();
          own = t;
        }
      }
    }
    if (own != null) {
      super.armyDistribution(1, own);
    } else {
      int random = 0;
      do {
        random = (int) (Math.random() * this.getTerritories().size()) + 1;
        int i = 1;
        for (Territory t : this.getTerritories()) {
          if (i == random) {
            System.out.println(t.getName());
            own = t;
            break;
          } else {
            i++;
          }
        }
      } while (!super.armyDistribution(1, own));
    }
    Main.b.updateLabelTerritory(own);
    Main.g.furtherInitialArmyDistribution();
  }
}
