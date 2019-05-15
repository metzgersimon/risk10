package game;

import gui.controller.NetworkController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import main.Main;
import network.messages.game.SkipgamestateMessage;

/** 
 * Class defines the hard Ai including their strategy in the different game stages.
 * 
 * @author smetzger
 * @author pcoberge
 *
 */
public class AiPlayerHard extends Player implements AiPlayer {
  
  private static final long serialVersionUID = 1L;
  private Territory territory;
  private HashMap<Integer, HashSet<Territory>> ownTerritories;
  private ArrayList<Integer> sortedValues;

  /**************************************************
   *                                                *
   *                  Constuctor                    *
   *                                                *
   *************************************************/ 
  
  /**
   * Constructor creates an hard Ai player by calling the player constructor to intialize
   * the name, color and the game the player will be active in.
   * After that the Ai is added into a HashSet to prevent the have same name multiple times.
   */
  public AiPlayerHard() {
    super(AiPlayerNames.getRandomName(), PlayerColor.values()[Main.g.getPlayers().size()], Main.g);
    Main.g.addAiNames(this.getName());
  }
  
  public AiPlayerHard(PlayerColor color) {
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
   * If no territory adjacent to another own territory is available, choose a territory with the
   * least number of neighbor territories.
   */
  public void initialTerritoryDistribution() {
    Thread th = new Thread() {
      public void run() {
        int minHostile = 42;
        double maxPartOfContinent = 0.0;
        Territory selection = null;
        HashSet<Territory> minHostileTerritories = new HashSet<>();

        for (Territory t1 : AiPlayerHard.this.getTerritories()) {
          if (minHostile >= t1.getHostileNeighbor().size()) {
            minHostile = t1.getHostileNeighbor().size();
            for (Territory t2 : t1.getHostileNeighbor()) {
              if (t2.getOwner() == null) {
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
              minHostile =
                  minHostile > t.getHostileNeighbor().size() ? t.getHostileNeighbor().size()
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
          if ((1.0 / (double) Main.g.getWorld().getContinent().get(t.getContinente())
              .getTerritories().size()) > maxPartOfContinent) {
            maxPartOfContinent = (1.0 / (double) Main.g.getWorld().getContinent()
                .get(t.getContinente()).getTerritories().size());
            selection = t;
          }
        }
        AiPlayerHard.super.initialTerritoryDistribution(selection);
        if (!Main.g.isNetworkGame()) {
          Main.b.updateColorTerritory(selection);
          Main.g.furtherInitialTerritoryDistribution();
        }
      }
    };
    th.start();
  }

  /**
   * Method represents the second stage of the game.
   */
  public void initialArmyDistribution() {
    int maxNeighbors = 0;
    int averageArmies = 0;
    int sum = 0;
    territory = null;
    for (Territory t : this.getTerritories()) {
      if (t.getHostileNeighbor().size() > maxNeighbors) {
        sum = 0;
        for (Territory territoryOpponents : t.getHostileNeighbor()) {
          sum += territoryOpponents.getNumberOfArmies();
        }
        averageArmies = (int) (sum / t.getHostileNeighbor().size());
        if (averageArmies > t.getNumberOfArmies()) {
          maxNeighbors = t.getHostileNeighbor().size();
          territory = t;
          if (super.armyDistribution(1, t)) {
            if (!Main.g.isNetworkGame()) {
              Thread th = new Thread() {
                public void run() {
                  Main.b.updateLabelTerritory(territory);
                }
              };
              th.start();
              Main.g.furtherInitialArmyDistribution();
            }
            return;
          }
        }
      }
    }
    initialArmyDistributionMedium();
  }

  /**
   * Strategy: Divide number of armies to distribute in attacking and defending armies.
   * 
   * <p>Attacking armies are placed on territories with the least number of hostile neighbors. These
   * territories are a good opportunity to expand own territories.
   * 
   * <p>Defending armies are placed on territories with the highest negative difference in number of
   * armies.
   */
  public void armyDistribution() {
    HashMap<Territory, Integer> hostileNeighbors = new HashMap<Territory, Integer>();

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
        for (Territory opponent : t.getNeighbor()) {
          if (!(opponent.getOwner().equals(this))) {
            i++;
          }
        }
        hostileNeighbors.put(t, i);
      }
      Territory terrToDistribute = null;
      int min = 42;
      for (Territory t : hostileNeighbors.keySet()) {
        for (Territory ownT : t.getNeighbor()) {
          if (ownT.getOwner().equals(this) && ownT.getNumberOfArmies() >= t.getNumberOfArmies()
              && hostileNeighbors.get(t) <= min) {
            min = hostileNeighbors.get(t);
            terrToDistribute = ownT;
          }
        }
      }
      if (super.armyDistribution(1, terrToDistribute)) {
        armiesAttack--;
      } else {
        break;
      }
    } while (armiesAttack > 0 && hostileNeighbors.size() > 0);

    // armies that defend territories/ continents
    int armiesDefend = this.getNumberArmiesToDistibute() - armiesAttack;
    int max = 0;
    ownTerritories = new HashMap<Integer, HashSet<Territory>>();
    sortedValues = new ArrayList<Integer>();
    for (Territory t : this.getTerritories()) {
      max = 0;
      for (Territory terr : t.getHostileNeighbor()) {
        if (((terr.getNumberOfArmies()) - (t.getNumberOfArmies())) >= max) {
          max = t.getNumberOfArmies() - terr.getNumberOfArmies();
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
    Main.g.setGameState(GameState.ATTACK);
    this.attack();
  }


  /**
   * Strategy: At first all hostile territories are sorted by their number of hostile neighbors. So,
   * the player can evaluate whether this territory increases the number of hosts or not.
   * 
   * <p>These territories with the least number of hosts (min + 1) are sorted by their difference in
   * number of armies in compare with their neighbors.
   */
  public void attack() {
    while (isCapableToAttack() && Main.g.getGameState() != GameState.END_GAME) {
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
        for (Territory opponent : t.getNeighbor()) {
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
        for (Territory ownT : t.getNeighbor()) {
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
            for (Territory ownT : t.getNeighbor()) {
              if (ownT.getOwner().equals(this)
                  && ownT.getNumberOfArmies() - t.getNumberOfArmies() > max) {
                max = ownT.getNumberOfArmies() - t.getNumberOfArmies();
                attacker = ownT;
                defender = t;
              }
            }
          }
        }

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
        if (attackDices.size() > defendDices.size()) {
          if (super.attack(attackDices, defendDices, attacker, defender, armiesToAttack)) {
            Main.b.updateLabelTerritory(attacker);
            Main.b.updateLabelTerritory(defender);
            Main.b.updateColorTerritory(defender);
          } else {
            Main.b.updateLabelTerritory(attacker);
            Main.b.updateLabelTerritory(defender);
          }
        } else {
          break;
        }
      } else {
        break;
      }
    }
    if (Main.g.getGameState() != GameState.END_GAME) {
      Main.g.setGameState(GameState.FORTIFY);
    }
    this.fortify();
  }

  /**
   * At first the territory that has no hostile neighbor territories and has more than one army on
   * it is chosen, if there is no such territory the territory with the highest difference between
   * territory and hostile neighbor is chosen.
   */
  public void fortify() {
    Territory moveFrom = null;
    Territory moveTo = null;

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
        Main.b.updateLabelTerritory(moveFrom);
        Main.b.updateLabelTerritory(moveTo);
      }
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
   * This method should be used if the original idea of this ai-player won't work.
   */
  public void initialArmyDistributionMedium() {
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
          Main.b.updateLabelTerritory(territory);
        }
      };
      th.start();
      Main.g.furtherInitialArmyDistribution();
    }
  }

  /**
   * This method is used if neither the hard army distribution algorithm nor the medium algorithm
   * would work.
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
      Main.b.updateLabelTerritory(territory);
    }
  }

  /**
   * This method should be used if the hard army distribution algorithm won't work.
   */
  public void greedyArmyDistribution() {
    int min = 0;
    ownTerritories = new HashMap<Integer, HashSet<Territory>>();
    sortedValues = new ArrayList<Integer>();

    for (Territory t : this.getTerritories()) {
      for (Territory oppT : t.getHostileNeighbor()) {
        if (t.getNumberOfArmies() - oppT.getNumberOfArmies() < min) {
          min = t.getNumberOfArmies() - oppT.getNumberOfArmies();
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
            Main.b.updateLabelTerritory(t);
          }
        }
      }
    }
  }

  /**
   * This method describes a precondition of the attack-method.
   * 
   * @return if there are any territories this player is able to attack with.
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
