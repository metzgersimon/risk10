package game;

import java.util.Vector;
import main.Main;

/**
 * Class defines an easy ai player which acts completely random.
 * 
 * @author smetzger
 * @author pcoberge
 *
 */
public class AiPlayerEasy extends Player implements AiPlayer {
  private Territory territory;

  /**
   * Constructor which calls the super constructor of the player class to set the name, the color
   * and the game of the player. It also adds the created player to the player list.
   */
  public AiPlayerEasy() {
    super(AiPlayerNames.getRandomName(), PlayerColor.values()[Main.g.getPlayers().size()], Main.g);
    Main.g.addAiNames(this.getName());
  }

  /**
   * Method represents the acting of the Ai in the first phase. It computes a random number between
   * one and 42 which represents a random territory the ai places an army on
   */
  public synchronized void initialTerritoryDistribution() {
    Thread th = new Thread() {
      public void run() {
        int random = 0;
        do {
          random = (random != 0 ? (random % 42) + 1 : (int) (Math.random() * 42) + 1);
        } while (!AiPlayerEasy.super.initialTerritoryDistribution(
            Main.g.getWorld().getTerritories().get(random)));
        if (!Main.g.isNetworkGame()) {
          // Main.b.updateLabelTerritory(Main.g.getWorld().getTerritories().get(random));
          Main.b.updateColorTerritory(Main.g.getWorld().getTerritories().get(random));
//          try {
//            Thread.sleep(1500);
//          } catch (InterruptedException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//          }
          // System.out.println(Platform.isFxApplicationThread());
          // System.out.println(new Date().toString());
          // try {
          // Thread.sleep(1000);
          // } catch (InterruptedException e) {
          // e.printStackTrace();
          // }
          // System.out.println(new Date().toString());
          Main.g.furtherInitialTerritoryDistribution();
        }
      }
    };
    th.start();
  }

  /**
   * Method represents the acting of the AI in the second phase. It selects one of the territory the
   * Ai owns to distribute an army of the remaining armies on.
   */
  public synchronized void initialArmyDistribution() {
    int random = 0;
    territory = null;
    // while the armyDistribution method of the player class returns false it computes a new random
    // number to select a territory
    do {
      random = (int) (Math.random() * this.getTerritories().size()) + 1;
      int i = 1;
      for (Territory t : this.getTerritories()) {
        if (i == random) {
          territory = t;
          break;
        } else {
          i++;
        }
      }
    } while (!super.armyDistribution(1, territory));
    if (!Main.g.isNetworkGame()) {
      Thread th = new Thread() {
        public void run() {
          Main.b.highlightTerritory(territory);
          Main.b.updateLabelTerritory(territory);
        }
      };
      th.start();
      Main.g.furtherInitialArmyDistribution();
    }

  }

  /**
   * Method represents the acting of the AI in the third phase. The Ai trades in their risk cards as
   * soon as possible It places a random number of armies on a random selected territory
   */

  public synchronized void armyDistribution() {
    /** trade cards **/
    // checks if the player owns at least three risk cards
    if (this.getCards().size() >= 3) {
      // going through all possible combinations of cards
      for (int i = 0; i < this.getCards().size(); i++) {
        for (int j = i + 1; j < this.getCards().size() - 1; j++) {
          for (int k = j + 1; k < this.getCards().size() - 2; k++) {
            // if possible the three cards will be traded in
            this.tradeCards(this.getCards().get(i), this.getCards().get(j), this.getCards().get(k));
          }
        }
      }
    }
    /** select territory **/
    int randomTerritory = 0;
    int randomNumberOfArmies = 0;
    Territory territory = null;

    while (this.getNumberArmiesToDistibute() != 0) {
      do {
        // random number between one and the amount of territories he owns
        randomTerritory = (int) (Math.random() * this.getTerritories().size()) + 1;
        // random number between one and the amount of armies he can distribute
        randomNumberOfArmies = (int) (Math.random() * this.getNumberArmiesToDistibute()) + 1;
        int i = 1;
        for (Territory t : this.getTerritories()) {
          if (i == randomTerritory) {
            territory = t;
            break;
          } else {
            i++;
          }
        }
      } while (!super.armyDistribution(randomNumberOfArmies, territory));

      if (!Main.g.isNetworkGame()) {
        Main.b.updateLabelTerritory(territory);
      }
      // try {
      // Thread.sleep(2000);
      // } catch (InterruptedException e) {
      // e.printStackTrace();
      // }
    }
    // sets the game state to the next stage and calls the attack method
    Main.g.setGameState(GameState.ATTACK);
    this.attack();

  }

  /**
   * Method represents the acting of the AI in the fourth phase. It selects a random territory from
   * which the attack starts, a random territory to attack and a random number of armies to attack
   * with
   */
  public synchronized void attack() {

    int randomTerritoryOwn = 0;
    int randomTerritoryOpponent = 0;
    int randomNumberOfArmies = 0;
    Territory territoryOwn = null;
    Territory territoryOpponent = null;
    int numberOfAttackDices = 0;
    int numberOfDefendDices = 0;
    double attackProbability = 0;

    double random = Math.random();

    // checks if an attack is even possible
    while (isCapableToAttack() && Main.g.getGameState() != GameState.END_GAME
        && random > attackProbability) {
      // choose own territory that is able to attack
      do {
        // random number between zero and the amount of own territories
        randomTerritoryOwn = (int) (Math.random() * this.getTerritories().size());
        // selects a random territory to start the attack from with the computed random number above
        territoryOwn = this.getTerritoriesArrayList().get(randomTerritoryOwn);
        // random number of armies to attack with
        randomNumberOfArmies = (int) (Math.random() * territoryOwn.getNumberOfArmies() - 1) + 1;
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
      if (super.attack(attackerDices, defenderDices, territoryOwn, territoryOpponent,
          randomNumberOfArmies)) {
        if (!Main.g.isNetworkGame()) {
          Main.b.updateLabelTerritory(territoryOwn);
          Main.b.updateLabelTerritory(territoryOpponent);
          Main.b.updateColorTerritory(territoryOpponent);
        }
      } else {
        if (!Main.g.isNetworkGame()) {
          Main.b.updateLabelTerritory(territoryOwn);
          Main.b.updateLabelTerritory(territoryOpponent);
        }
//        try {
//          Thread.sleep(1000);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
      }
      attackProbability = 0.66;
      random = Math.random();
    }
    // sets the game state to the next stage
    Main.g.setGameState(GameState.FORTIFY);
//    try {
//      Thread.sleep(1000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
    // calls the fortify method of the class
    this.fortify();

  }

  /**
   * Method represents the acting of the AI in the last phase. It just skips the fortify stage and
   * passes on to the next player
   */
  public synchronized void fortify() {
    Main.g.furtherFortify();
  }

  /**
   * Method checks if the Ai player is able to attack, means it has more than one army on its chosen
   * territory.
   * 
   * @return true or false regarding to its number of armies on the selected territory
   */
  public boolean isCapableToAttack() {
    for (Territory t : this.getTerritories()) {
      if (t.getNumberOfArmies() > 1) {
        return true;
      }
    }
    return false;
  }
}
