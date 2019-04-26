package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import main.Main;

public class AiPlayerMedium extends Player implements AiPlayer {

  private HashMap<Integer, HashSet<Territory>> ownTerritories;
  private ArrayList<Integer> sortedValues;

  public AiPlayerMedium() {
    super(AiPlayerNames.getRandomName(), PlayerColor.values()[Main.g.getPlayers().size()], Main.g);
    Main.g.addAiNames(this.getName());
  }


  @Override
  public void initialTerritoryDistribution() {
    for (Territory t : this.getTerritories()) {
      for (Territory neighbor : t.getNeighbor()) {
        if (neighbor.getOwner() == null) {
          if (super.initialTerritoryDistribution(neighbor)) {
            System.out.println(neighbor);
            // super.initialTerritoryDistribution(neighbor);
            Main.b.updateLabelTerritory(neighbor);
            Main.b.updateColorTerritory(neighbor);
            Main.g.furtherInitialTerritoryDistribution();
            return;
          }
        }
      }
    }
    int random = 0;
    do {
      random = (random != 0 ? (random % 42) + 1 : (int) (Math.random() * 42) + 1);
      System.out.print(random + " ");
    } while (!super.initialTerritoryDistribution(Main.g.getWorld().getTerritories().get(random)));
    Main.b.updateLabelTerritory(Main.g.getWorld().getTerritories().get(random));

    Main.b.updateColorTerritory(Main.g.getWorld().getTerritories().get(random));
    System.out
        .println(Main.g.getCurrentPlayer().getName() + "--" + Main.g.getCurrentPlayer().getColor()
            + "--" + Main.g.getWorld().getTerritories().get(random));
    // System.out.println(Main.g.getCurrentPlayer().getColor() + " method");
    // System.out.println(Main.g.getWorld().getTerritories().get(random));
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Main.g.furtherInitialTerritoryDistribution();
  }


  @Override
  public void initialArmyDistribution() {

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

  /**
   * 1. sort territories depending on number of armies on it - order them by highest values
   * 
   * 2. sort territories to balance the difference of numbers of armies - order them by lowest
   * values
   * 
   */
  public void armyDistribution() {
    System.out.println("Test armyDistribution");
    int max = 0;
    ownTerritories = new HashMap<Integer, HashSet<Territory>>();
    sortedValues = new ArrayList<Integer>();

    if (this.getCards().size() >= 3) {
      for (int i = 0; i < this.getCards().size(); i++) {
        for (int j = i + 1; j < this.getCards().size() - 1; j++) {
          for (int k = j + 1; k < this.getCards().size() - 2; k++) {
            this.tradeCards(this.getCards().get(i), this.getCards().get(j), this.getCards().get(k));
          }
        }
      }
    }

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
      while (this.getNumberArmiesToDistibute() > 0) {
        for (int i = 0; i < sortedValues.size(); i++) {
          for (Territory t : ownTerritories.get(sortedValues.get(i))) {
            super.armyDistribution(1, t);
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

    // Main.b.handleSkipGameState();
    Main.g.setGameState(GameState.ATTACK);
    this.attack();
  }

  public void attack() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    int max = 0;
    int round = 1;
    Territory attacker = null;
    Territory defender = null;
    int armiesToAttack = 0;
    while (isCapableToAttack()) {
      for (Territory t : this.getTerritories()) {
        for (Territory tE : t.getHostileNeighbor()) {
          if (((t.getNumberOfArmies()) - (tE.getNumberOfArmies())) > max) {
            max = t.getNumberOfArmies() - tE.getNumberOfArmies();
            attacker = t;
            defender = tE;
            armiesToAttack = (int) Math.ceil((t.getNumberOfArmies()) / 2);
          }
        }
      }
      if (defender == null) {
        break;
      }
      Vector<Integer> attackDices = Dice.rollDices(armiesToAttack >= 3 ? 3 : armiesToAttack);
      Vector<Integer> defendDices =
          Dice.rollDices(defender.getNumberOfArmies() >= 2 ? 2 : defender.getNumberOfArmies());
      if (max > round) {
        System.out.println("Max:" + max);
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
      max = round;
      round++;
    }

    // Main.b.handleSkipGameState();
    Main.g.setGameState(GameState.FORTIFY);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.fortify();
  }

  public void fortify() {
    System.out.println("AI fortify: " + Main.g.getCurrentPlayer());

    int min = 0;
    Territory own1 = null;
    Territory own2 = null;
    int armiesToMove = 0;
    for (Territory t : this.getTerritories()) {
      for (Territory tE : t.getOwnNeighbors()) {
        for (Territory opponent : tE.getHostileNeighbor()) {
          if (((tE.getNumberOfArmies()) - (opponent.getNumberOfArmies())) < min) {
            min = tE.getNumberOfArmies() - opponent.getNumberOfArmies();
            own1 = t;
            own2 = tE;
            armiesToMove = (int) Math.ceil((t.getNumberOfArmies())
                * (((double) t.getOwnNeighbors().size()) / (double) t.getNeighbor().size()));
          }
        }
      }
    }
    if (own1 != null && own2 != null) {
      super.fortify(own1, own2, armiesToMove);
      System.out.println(
          "Territory from: " + own1 + " to: " + own2 + " with " + armiesToMove + " armies.");
      Main.b.updateLabelTerritory(own1);
      Main.b.updateLabelTerritory(own2);
    }
    // Main.b.handleSkipGameState();
    Main.g.furtherFortify();
  }

  public boolean isCapableToAttack() {
    for (Territory t : this.getTerritories()) {
      if (t.getNumberOfArmies() > 1) {
        return true;
      }
    }
    return false;
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



}

