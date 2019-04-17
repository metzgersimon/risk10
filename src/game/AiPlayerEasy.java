package game;

import java.util.Vector;
import main.Main;

/**
 * @author smetzger
 * @author pcoberge
 *
 */
public class AiPlayerEasy extends Player implements AiPlayer {

  public AiPlayerEasy() {
    super(AiPlayerNames.getRandomName(), PlayerColor.values()[Main.g.getPlayers().size()], Main.g);
    Main.g.addAiNames(this.getName());
  }

  /**
   * @author smetzger
   * @author pcoberge
   */
  public synchronized void initialTerritoryDistribution() {
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

  public synchronized void initialArmyDistribution() {
    int random = 0;
    Territory territory = null;
    do {
      random = (int) (Math.random() * this.getTerritories().size()) + 1;
      int i = 1;
      for (Territory t : this.getTerritories()) {
        if (i == random) {
          System.out.println(t.getName());
          territory = t;
          break;
        } else {
          i++;
        }
      }
    } while (!super.armyDistribution(1, territory));
    Main.b.updateLabelTerritory(territory);
    Main.g.furtherInitialArmyDistribution();

  }

  public synchronized void armyDistribution() {
    System.out.println("AI army distribution: " + Main.g.getCurrentPlayer().getName());

    // trade-in cards
    if (this.getCards().size() >= 3) {
      for (int i = 0; i < this.getCards().size(); i++) {
        for (int j = i + 1; j < this.getCards().size() - 1; j++) {
          for (int k = j + 1; k < this.getCards().size() - 2; k++) {
            this.tradeCards(this.getCards().get(i), this.getCards().get(j), this.getCards().get(k));
          }
        }
      }
    }

    int randomTerritory = 0;
    int randomNumberOfArmies = 0;
    Territory territory = null;
    // choose territory
    while (this.getNumberArmiesToDistibute() != 0) {
//      System.out.println(this.getNumberArmiesToDistibute());
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
    Main.b.handleSkipGameState();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.attack();
  }

  public synchronized void attack() {
    System.out.println(
        "AI attack: " + Main.g.getCurrentPlayer().getName() + " -- " + this.getColor().toString());
    int randomTerritoryOwn = 0;
    int randomTerritoryOpponent = 0;
    int randomNumberOfArmies = 0;
    Territory territoryOwn = null;
    Territory territoryOpponent = null;
    int numberOfAttackDices = 0;
    int numberOfDefendDices = 0;
    double attackProbability = 0;

    double random = Math.random();
   
    while (this.isCapableToAttack() && random > attackProbability) {
      // choose own territory that is able to attack
      System.out.println("Random1: "+random);
      do {
        randomTerritoryOwn = (int) (Math.random() * this.getTerritories().size());
        territoryOwn = this.getTerritoriesArrayList().get(randomTerritoryOwn);
//        int i = 1;
//        for (Territory t : this.getTerritories()) {
//          if (i == randomTerritoryOwn) {
//            territoryOwn = t;
//           
            randomNumberOfArmies = (int) (Math.random() * territoryOwn.getNumberOfArmies()-1) + 1;
//            System.out.println("Territory t: "+t + " - - RandomArmies: "+randomNumberOfArmies);
//          } else {
//            i++;
//          }
//        }
      } while (territoryOwn.getNumberOfArmies() == 1
          || territoryOwn.getHostileNeighbor().size() == 0);


      // choose hostile neighbor territory
      randomTerritoryOpponent = (int) (Math.random() * territoryOwn.getHostileNeighbor().size());
      territoryOpponent = territoryOwn.getHostileNeighbor().get(randomTerritoryOpponent);

      // calculate number of dices
      numberOfAttackDices =
          (territoryOwn.getNumberOfArmies() - 1) < 3 ? territoryOwn.getNumberOfArmies() - 1 : 3;
      numberOfDefendDices = (territoryOpponent.getNumberOfArmies()) >= 2 ? 2 : 1;

      // get dice Values
      Vector<Integer> attackerDices = Dice.rollDices(numberOfAttackDices);
      Vector<Integer> defenderDices = Dice.rollDices(numberOfDefendDices);

      // attack chosen territory and update GUI
      if (Main.g.attack(attackerDices, defenderDices, territoryOwn, territoryOpponent,
          randomNumberOfArmies)) {
        System.out.println(territoryOwn.getName() + " -- " + territoryOpponent.getName());
        Main.b.updateLabelTerritory(territoryOwn);
        Main.b.updateLabelTerritory(territoryOpponent);
        Main.b.updateColorTerritory(territoryOpponent);
        System.out.println("UPDATE");
      } else {
        Main.b.updateLabelTerritory(territoryOwn);
        Main.b.updateLabelTerritory(territoryOpponent);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        System.out.println(territoryOwn.getName() + " -- " + territoryOpponent.getName());
      }
      attackProbability = 0.66;
      random = Math.random();
      System.out.println("Random2: "+random);
    }

    Main.b.handleSkipGameState();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("next Step");
    this.fortify();

  }

  public synchronized void fortify() {
    System.out.println("AI fortify: " + Main.g.getCurrentPlayer());
    Main.b.handleSkipGameState();
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
