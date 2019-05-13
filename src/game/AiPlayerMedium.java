package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import gui.controller.NetworkController;
import main.Main;
import network.messages.game.SkipgamestateMessage;

/**
 * Class defines the medium Ai including their strategy in the different game stages.
 * @author pcoberge
 * @author smetzger
 *
 */
public class AiPlayerMedium extends Player implements AiPlayer {
  private Territory territory;
  private HashMap<Integer, HashSet<Territory>> ownTerritories;
  private ArrayList<Integer> sortedValues;

  
  
  /**************************************************
   *                                                *
   *                  Constuctor                    *
   *                                                *
   *************************************************/ 
  
  
  /**
   * Constructor creates an medium Ai player by calling the player constructor to intialize
   * the name, color and the game the player will be active in.
   * After that the Ai is added into a HashSet to prevent the have same name multiple times.
   */
  public AiPlayerMedium() {
    super(AiPlayerNames.getRandomName(), PlayerColor.values()[Main.g.getPlayers().size()], Main.g);
    Main.g.addAiNames(this.getName());
  }
  
  public AiPlayerMedium(PlayerColor color) {
    super(AiPlayerNames.getRandomName(), color, Main.g);
    Main.g.addAiNames(this.getName());
  }

  /**************************************************
   *                                                *
   *        Methods for the different main          *
   *        stages of the game                      *
   *                                                *
   *************************************************/ 
  
  /**
   * Method describes the acting of the Ai in the first phase.
   * If possible a free neighbor territory is selected,
   * in case there is no such territory the Ai chooses a territory randomly.
   */
  @Override
  public void initialTerritoryDistribution() {
    Thread th = new Thread() {
      public void run() {
        //Checks if there is a free neighbor territory to select
        for (Territory t : AiPlayerMedium.this.getTerritories()) {
          for (Territory neighbor : t.getNeighbor()) {
            if (neighbor.getOwner() == null) {
              if (AiPlayerMedium.super.initialTerritoryDistribution(neighbor)) {
                if (!Main.g.isNetworkGame()) {
                  Main.b.updateLabelTerritory(neighbor);
                  Main.b.updateColorTerritory(neighbor);
                  Main.g.furtherInitialTerritoryDistribution();
                  return;
                }
                return;
              }
            }
          }
        }
        //If there was no free neighbor territory a random territory will be selected
        int random = 0;
        do {
          random = (random != 0 ? (random % 42) + 1 : (int) (Math.random() * 42) + 1);
        } while (!AiPlayerMedium.super.initialTerritoryDistribution(
            Main.g.getWorld().getTerritories().get(random)));
        if (!Main.g.isNetworkGame()) {
          Main.b.updateColorTerritory(Main.g.getWorld().getTerritories().get(random));
//          try {
//            Thread.sleep(1500);
//          } catch (InterruptedException e1) {
//            e1.printStackTrace();
//          }
          Main.g.furtherInitialTerritoryDistribution();
        }
      }
    };
    th.start();
  }


  /**
   * Method describes the strategy of the Ai in the second phase.
   * It places the army on that territory where the difference
   * of number of armies and those of the opponent is smallest.
   * If the difference is never zero or smaller the territory will be selected randomly.
   */
  @Override
  public void initialArmyDistribution() {
    int min = 0;
    territory = null;
    for (Territory t : this.getTerritories()) {
      for (Territory opponent : t.getHostileNeighbor()) {
        if (((t.getNumberOfArmies()) - (opponent.getNumberOfArmies())) <= min) {
          min = t.getNumberOfArmies() - opponent.getNumberOfArmies();
          territory = t;
        }
      }
    }
    if (territory != null) {
      super.armyDistribution(1, territory);
    } else {
      //random approach
      int random = 0;
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
    }
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
   * Method describes the third stage of the game for the medium Ai.
   * If possible a card set is traded in
   * 1. sort territories depending on number of armies on it - order them by highest values
   * 2. sort territories to balance the difference of numbers of armies - order them by lowest
   * values
   * 
   */
  public void armyDistribution() {
    int max = 0;
    ownTerritories = new HashMap<Integer, HashSet<Territory>>();
    sortedValues = new ArrayList<Integer>();

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

    for (Territory t : this.getTerritories()) {
      max = 0;
      for (Territory opponent : t.getHostileNeighbor()) {
        if (((t.getNumberOfArmies()) - (opponent.getNumberOfArmies())) > max) {
          max = t.getNumberOfArmies() - opponent.getNumberOfArmies();
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
            // if (!Main.g.isNetworkGame()) {
            Main.b.updateLabelTerritory(t);
            // }
          }
        }
      }
    } else {
      greedyArmyDistribution();
      //if the greedy army distribution wasn't successful but there are armies left
      if (this.getNumberArmiesToDistibute() > 0) {
        //call the random army distribution (of the easy Ai)
        randomArmyDistribution();
      }
    }
    Main.g.setGameState(GameState.ATTACK);
    //the Ai keeps going with the attack phase
    this.attack();
  }

  /**
   * Method defines the attack stage.
   * Selects that territory where the difference between the own number of armies 
   * and those of the opponent is greatest.
   */
  public void attack() {
//    try {
//      Thread.sleep(2000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
    int max = 0;
    int round = 1;
    Territory attacker = null;
    Territory defender = null;
    int armiesToAttack = 0;
    while (isCapableToAttack() && Main.g.getGameState() != GameState.END_GAME) {
      for (Territory t : this.getTerritories()) {
        for (Territory opponent : t.getHostileNeighbor()) {
          if (((t.getNumberOfArmies()) - (opponent.getNumberOfArmies())) > max) {
            max = t.getNumberOfArmies() - opponent.getNumberOfArmies();
            attacker = t;
            defender = opponent;
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
        if (super.attack(attackDices, defendDices, attacker, defender, armiesToAttack)) {
          
          Main.b.updateLabelTerritory(attacker);
          Main.b.updateLabelTerritory(defender);
          Main.b.updateColorTerritory(defender);
        } else {
          Main.b.updateLabelTerritory(attacker);
          Main.b.updateLabelTerritory(defender);
//          try {
//            Thread.sleep(2000);
//          } catch (InterruptedException e) {
//            e.printStackTrace();
//          }
        }
      } else {
        break;
      }
      max = round;
      round++;
    }
    Main.g.setGameState(GameState.FORTIFY);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //keep going with fortify
    this.fortify();
  }

  /**
   * Method represents the fortify mode.
   * It moves the armies on that territory where the neighbor opponent territory has
   * a bigger army.
   */
  public void fortify() {

    int min = 0;
    Territory own1 = null;
    Territory own2 = null;
    int armiesToMove = 0;
    for (Territory t : this.getTerritories()) {
      for (Territory ownNeighbor : t.getOwnNeighbors()) {
        for (Territory opponent : ownNeighbor.getHostileNeighbor()) {
          if (((ownNeighbor.getNumberOfArmies()) - (opponent.getNumberOfArmies())) < min) {
            min = ownNeighbor.getNumberOfArmies() - opponent.getNumberOfArmies();
            own1 = t;
            own2 = ownNeighbor;
            armiesToMove = (int) Math.ceil((t.getNumberOfArmies())
                * (((double) t.getOwnNeighbors().size()) / (double) t.getNeighbor().size()));
          }
        }
      }
    }
    if (own1 != null && own2 != null) {
      System.out.println("fortify liefert: " + super.fortify(own1, own2, armiesToMove));
      System.out.println("methode aufgerufen");
      Main.b.updateLabelTerritory(own1);
      Main.b.updateLabelTerritory(own2);
    }

    if (NetworkController.server != null) {
      SkipgamestateMessage message = new SkipgamestateMessage(GameState.FORTIFY);
      message.setColor(Main.g.getCurrentPlayer().getColor().toString());
      NetworkController.gameFinder.getClient().sendMessage(message);
    }
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Main.g.furtherFortify();
  }

  /**
   * Method represents the army distribution of the easy Ai.
   */
  public void randomArmyDistribution() {
    int randomTerritory = 0;
    int randomNumberOfArmies = 0;
    Territory territory = null;

    // choose territory
    while (this.getNumberArmiesToDistibute() != 0) {
      do {
        randomTerritory = (int) (Math.random() * this.getTerritories().size()) + 1;
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
      
//      if (!Main.g.isNetworkGame()) {
        Main.b.updateLabelTerritory(territory);
//      }
    }
  }

  /**
   * Method defines the greedy army distribution.
   */
  public void greedyArmyDistribution() {
    int min = 0;
    ownTerritories = new HashMap<Integer, HashSet<Territory>>();
    sortedValues = new ArrayList<Integer>();

    for (Territory t : this.getTerritories()) {
      for (Territory opponent : t.getHostileNeighbor()) {
        if (t.getNumberOfArmies() - opponent.getNumberOfArmies() < min) {
          min = t.getNumberOfArmies() - opponent.getNumberOfArmies();
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
//            if (!Main.g.isNetworkGame()) {
              Main.b.updateLabelTerritory(t);
//            }
          }
        }
      }
    }
  }
  
  /**
   * Method checks if Ai is able to attack.
   * @return true in case an attack is possible, otherwise false
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

