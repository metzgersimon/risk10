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
      if ((1.0 / (double) Main.g.getWorld().getContinent().get(t.getContinente()).getTerritories()
          .size()) > maxPartOfContinent) {
        maxPartOfContinent = (1.0 / (double) Main.g.getWorld().getContinent().get(t.getContinente())
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
          if (super.armyDistribution(1, t)) {
            Main.b.updateLabelTerritory(t);
            Main.g.furtherInitialArmyDistribution();
            return;
          }
        }
      }
    }
    InitialArmyDistributionMedium();
  }

  /**
   * Strategy: Divide number of armies to distribute in attacking and defending armies
   * 
   * Attacking armies are placed on territories with the least number of hostile neighbors. These
   * territories are a good opportunity to expand own territories.
   * 
   * Defending armies are placed on territories with the highest negative difference in number of
   * armies.
   * 
   */
  public void armyDistribution() {
    HashMap<Territory, Integer> hostileNeighbors = new HashMap<Territory, Integer>();


    if (this.getCards().size() >= 3) {
      for (int i = 0; i < this.getCards().size(); i++) {
        for (int j = i + 1; j < this.getCards().size() - 1; j++) {
          for (int k = j + 1; k < this.getCards().size() - 2; k++) {
            this.tradeCards(this.getCards().get(i), this.getCards().get(j), this.getCards().get(k));
          }
        }
      }
    }

    int armiesAttack = (int) ((double) this.getNumberArmiesToDistibute() * (2.0 / 3.0));
    do {
      // get all hostile neighbor territories
      for (Territory t : this.getTerritories()) {
        for (Territory opponent : t.getHostileNeighbor()) {
          hostileNeighbors.put(opponent, 42);
        }
      }
      // get number of (new) additional hostile neighbors, if you receive the defending territory
      for (Territory t : hostileNeighbors.keySet()) {
        int i = 0;
        for (Territory opponent : t.getHostileNeighbor()) {
          if (!(opponent.getOwner().equals(this))) {
            i++;
          }
        }
        hostileNeighbors.put(t, i);
      }
      Territory tToDistribute = null;
      int min = 42;
      for (Territory t : hostileNeighbors.keySet()) {
        for (Territory ownT : t.getNeighbor()) {
          if (ownT.getOwner().equals(this) && ownT.getNumberOfArmies() >= t.getNumberOfArmies()
              && hostileNeighbors.get(t) <= min) {
            min = hostileNeighbors.get(t);
            tToDistribute = ownT;
          }
        }
      }
      System.out.println("Verteilen auf " + tToDistribute.getName());
      if (super.armyDistribution(1, tToDistribute)) {
        armiesAttack--;
      }
    } while (armiesAttack > 0 && hostileNeighbors.size() > 0);



    // armies that defend territories/ continents
    int armiesDefend = this.getNumberArmiesToDistibute() - armiesAttack;
    System.out.println("Defend armies number: " + armiesDefend);
    int max = 0;
    ownTerritories = new HashMap<Integer, HashSet<Territory>>();
    sortedValues = new ArrayList<Integer>();
    for (Territory t : this.getTerritories()) {
      max = 0;
      for (Territory tE : t.getHostileNeighbor()) {
        if (((tE.getNumberOfArmies()) - (t.getNumberOfArmies())) >= max) {
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
            armiesDefend--;
            Main.b.updateLabelTerritory(t);
          }
        }
      }
    } else {
      greedyArmyDistribution();
      if (this.getNumberArmiesToDistibute() > 0) {
        randomArmyDistribution();
      }
    }

    Main.b.handleSkipGameState();
    this.attack();
  }


  /**
   * Strategy: At first all hostile territories are sorted by their number of hostile neighbors. So,
   * the player can evaluate whether this territory increases the number of hosts or not.
   * 
   * These territories with the least number of hosts (min + 1) are sorted by their difference in
   * number of armies in compare with their neighbors.
   */
  public void attack() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    while (isCapableToAttack()) {
      HashMap<Territory, Integer> hostileNeighbors = new HashMap<>();
      // get all hostile neighbor territories
      for (Territory t : this.getTerritories()) {
        for (Territory opponent : t.getHostileNeighbor()) {
          hostileNeighbors.put(opponent, 42);
        }
      }
      // get number of (new) additional hostile neighbors, if you receive the defending territory
      for (Territory t : hostileNeighbors.keySet()) {
        int i = 0;
        for (Territory opponent : t.getHostileNeighbor()) {
          if (!(opponent.getOwner().equals(this))) {
            i++;
          }
        }
        hostileNeighbors.put(t, i);
      }

      // get minimum of hostile neighbors of territory that should be attacked
      int armiesToAttack = 0;
      Territory attacker = null;
      Territory defender = null;
      int min = 42;
      for (Territory t : hostileNeighbors.keySet()) {
        for (Territory ownT : t.getHostileNeighbor()) {
          if (ownT.getOwner().equals(this) && ownT.getNumberOfArmies() > t.getNumberOfArmies()
              && hostileNeighbors.get(t) < min) {
            min = hostileNeighbors.get(t);
          }
        }
      }

      // get territories with minimum + 1 amount of hostile neighbors with least difference in
      // number of armies in compare with their neighbors.
      if (min < 42) {
        int max = 0;
        for (Territory t : hostileNeighbors.keySet()) {
          if (hostileNeighbors.get(t) <= (min + 1)) {
            for (Territory ownT : t.getHostileNeighbor()) {
              if (ownT.getNumberOfArmies() - t.getNumberOfArmies() > max) {
                max = ownT.getNumberOfArmies() - t.getNumberOfArmies();
                attacker = ownT;
                defender = t;
              }
            }
          }
        }

        System.out.println("Minimale Anzahl an neuen Gegnern durch Eroberung: " + min + " vs. "
            + hostileNeighbors.get(defender));
        int value = 0;
        if (attacker.getHostileNeighbor().size() == 1) {
          value = attacker.getNumberOfArmies() - 1;
        } else {
          if (attacker.getNumberOfArmies() >= 5) {
            value = 3;
          } else {
            value = (int) Math.ceil((attacker.getNumberOfArmies()) / 2);
          }
        }
        armiesToAttack = value > 1 ? value : 1;

        Vector<Integer> attackDices = Dice.rollDices(armiesToAttack >= 3 ? 3 : armiesToAttack);
        Vector<Integer> defendDices =
            Dice.rollDices(defender.getNumberOfArmies() >= 2 ? 2 : defender.getNumberOfArmies());
        System.out.println(attacker.getNumberOfArmies() + " vs. " + defender.getNumberOfArmies());
        // System.out.println("Max:" + max);
        if (attackDices.size() > defendDices.size()) {
          if (super.attack(attackDices, defendDices, attacker, defender, armiesToAttack)) {
            System.out.println("Defending territory owner: " + defender.getOwner().getName());
            Main.b.updateLabelTerritory(attacker);
            Main.b.updateLabelTerritory(defender);
            Main.b.updateColorTerritory(defender);
          } else {
            Main.b.updateLabelTerritory(attacker);
            Main.b.updateLabelTerritory(defender);
            try {
              Thread.sleep(2000);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          System.out.println(attacker + " -- " + defender);
        } else {
          break;
        }
      } else {
        break;
      }
      // max = round;
      // round++;
    }


    Main.b.handleSkipGameState();
    try

    {
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

  public void randomArmyDistribution() {
    int randomTerritory = 0;
    int randomNumberOfArmies = 0;
    Territory territory = null;

    // choose territory
    while (this.getNumberArmiesToDistibute() != 0) {
      // System.out.println(this.getNumberArmiesToDistibute());
      do {
        randomTerritory = (int) (Math.random() * this.getTerritories().size()) + 1;
        randomNumberOfArmies = (int) (Math.random() * this.getNumberArmiesToDistibute()) + 1;
        int i = 1;
        for (Territory t : this.getTerritories()) {
          if (i == randomTerritory) {
            territory = t;
            System.out.println(territory.getName() + " -- " + randomNumberOfArmies);
            break;
          } else {
            i++;
          }
        }
      } while (!super.armyDistribution(randomNumberOfArmies, territory));

      Main.b.updateLabelTerritory(territory);
    }
  }

  public void greedyArmyDistribution() {
    int min = 0;
    ownTerritories = new HashMap<Integer, HashSet<Territory>>();
    sortedValues = new ArrayList<Integer>();

    HashSet<Territory> list = new HashSet<>();
    for (Territory t : this.getTerritories()) {
      for (Territory oT : t.getHostileNeighbor()) {
        if (t.getNumberOfArmies() - oT.getNumberOfArmies() < min) {
          min = t.getNumberOfArmies() - oT.getNumberOfArmies();
          if (ownTerritories.containsKey(min)) {
            ownTerritories.get(min).add(t);
          } else {
            HashSet<Territory> tr = new HashSet<Territory>();
            tr.add(t);
            ownTerritories.put(min, tr);
          }
        }
      }
    }

    if (ownTerritories.size() > 0) {
      sortedValues = new ArrayList<Integer>(ownTerritories.keySet());
      Collections.sort(sortedValues);
      while (this.getNumberArmiesToDistibute() > 0) {
        for (int i = 0; i < sortedValues.size(); i++) {
          for (Territory t : ownTerritories.get(sortedValues.get(i))) {
            super.armyDistribution(1, t);
            System.out.println("Army distribution: " + t.getName());
            Main.b.updateLabelTerritory(t);
          }
        }
      }
    }
  }

  public boolean isCapableToAttack() {
    for (Territory t : this.getTerritories()) {
      if (t.getNumberOfArmies() > 1) {
        return true;
      }
    }
    return false;
  }
}
