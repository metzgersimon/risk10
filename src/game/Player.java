package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import gui.NetworkController;
import main.Main;
import network.messages.game.DistributeArmyMessage;
import network.messages.game.SelectInitialTerritoryMessage;

/**
 * 
 * @author qiychen
 *
 */
public class Player implements Serializable {

  public static int PLAYER_HUMAN = 0;
  public static int PLAYER_AI_EASY = 1;
  public static int PLAYER_AI_MITT = 2;
  public static int PLAYER_AI_HARD = 3;

  private Game g;
  private String name;
  private int armies;
  private boolean isAi = false;// human player or ai
  private PlayerColor color;
  private HashSet<Territory> territories;
  private HashSet<Continent> continents;
  private ArrayList<Card> cards;
  private ArrayList<Player> eliminatedPlayers;
  private boolean successfullAttack;
  private boolean startedDistribution;

  public int numberArmiesToDistribute;
  private int tradedCardSets;
  private int numberOfTerritories;
  private int numberOfCards;
  private int valueActuallyTradedIn;
  public int territoriesConquered;
  public int numberOfAttacks;
  public int rank;
  public int sessionWins;

  private static StringBuffer sb = new StringBuffer();

  /**
   * create a new player
   * 
   * @param name
   * @param armies
   * @param type
   */
  public Player(String name, int armies, PlayerColor color) {
    this.name = name;
    this.armies = armies;
    this.color = color;
    territories = new HashSet<>();
    continents = new HashSet<>();
    cards = new ArrayList<>();
    this.tradedCardSets = 0;
    // this.g = Main.g;
    this.numberOfTerritories = 0;
    this.numberOfCards = 0;
    this.territoriesConquered = 1;
    this.numberOfAttacks = 0;
    this.rank = 0;
    this.sessionWins = 0;
  }

  public Player(String name, PlayerColor color, Game g) {
    this.name = name;
    this.color = color;
    territories = new HashSet<>();
    continents = new HashSet<>();
    cards = new ArrayList<>();
    this.eliminatedPlayers = new ArrayList<>();
    this.g = g;
    this.numberOfTerritories = 0;
    this.numberOfCards = 0;
    this.territoriesConquered = 0;
    this.numberOfAttacks = 0;
    this.rank = (int) (Math.random() * 6);
    this.sessionWins = 0;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getArmies() {
    return armies;
  }

  public void setArmies(int armies) {
    this.armies = armies;
  }

  public boolean getIsAi() {
    return isAi;
  }

  public void setIsAi(boolean isAi) {
    this.isAi = isAi;
  }

  public HashSet<Territory> getTerritories() {
    return territories;
  }

  public ArrayList<Territory> getTerritoriesArrayList() {
    ArrayList<Territory> list = new ArrayList<Territory>(this.territories);
    return list;
  }

  public void setTerritories(HashSet<Territory> territories) {
    this.territories = territories;
  }


  public int getTradedCardSets() {
    return tradedCardSets;
  }

  public void setTradedCardSets(int tradedCardSets) {
    this.tradedCardSets = tradedCardSets;
  }



  public int getValueActuallyTradedIn() {
    return valueActuallyTradedIn;
  }

  public void setValueActuallyTradedIn(int valueActuallyTradedIn) {
    this.valueActuallyTradedIn = valueActuallyTradedIn;
  }

  public int getNumberOfTerritories() {
    return numberOfTerritories;
  }

  public void setNumberOfTerritories(int numberOfTerritories) {
    this.numberOfTerritories = numberOfTerritories;
  }

  public int getNumberOfCards() {
    return numberOfCards;
  }

  public void setNumberOfCards(int numberOfCards) {
    this.numberOfCards = numberOfCards;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public int getNumberOfAttacks() {
    return numberOfAttacks;
  }

  public void setNumberOfAttacks(int attacks) {
    this.numberOfAttacks = attacks;
  }

  public int getTerritoriesConquered() {
    return territoriesConquered;
  }

  public void setTerritoriesConquered(int tc) {
    this.territoriesConquered = tc;
  }

  public int getSessionWins() {
    return sessionWins;
  }

  public void setSessionWins(int sw) {
    this.sessionWins = sw;
  }

  /**
   * 
   * @returnthe total number of territories owned by player
   */
  public int TerritoriesOwned() {
    return territories.size();
  }

  /**
   * player conquers a new territory, which need to be added in the list
   * 
   * @param t
   */
  public void addTerritories(Territory t) {
    territories.add(t);
    boolean newContinent = true;
    for (Territory territory : t.getContinent().getTerritories()) {
      if (!this.getTerritories().contains(territory)) {
        newContinent = false;
      }
    }

    if (newContinent) {
      this.addContinents(t.getContinent());
    }
  }

  /**
   * player losts a territory, which need to be reduced from the list
   * 
   * @param t
   */
  public void lostTerritories(Territory t) {
    territories.remove(t);
    if (this.getContinents().contains(t.getContinent())) {
      this.lostContinents(t.getContinent());
    }
  }

  public HashSet<Continent> getContinents() {
    return continents;
  }

  public void setContinents(HashSet<Continent> continents) {
    this.continents = continents;
  }

  public void addContinents(Continent c) {
    continents.add(c);
  }

  public void lostContinents(Continent c) {
    continents.remove(c);
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public void addCard(Card c) {
    this.cards.add(c);
  }

  public PlayerColor getColor() {
    return color;
  }

  public ArrayList<Player> getEliminatedPlayers() {
    return eliminatedPlayers;
  }

  public void setCards(ArrayList<Card> cards) {
    for (Card c : cards) {
      this.cards.add(c);
    }
  }

  public void setColor(PlayerColor color) {
    this.color = color;
  }

  public void setEliminatedPlayers(ArrayList<Player> eliminatedPlayers) {
    this.eliminatedPlayers = eliminatedPlayers;
  }

  public int getNumberArmiesToDistibute() {
    return this.numberArmiesToDistribute;
  }

  public void setNumberArmiesToDistribute(int amount) {
    this.numberArmiesToDistribute = amount;
  }

  /**
   * if player defeated another player, this player will be add to eliminatedPlayers
   * 
   * @param p
   */
  public void addElimiatedPlayer(Player p) {
    eliminatedPlayers.add(p);
  }

  public boolean getSuccessfullAttack() {
    return successfullAttack;
  }

  public void setSuccessfullAttack(boolean b) {
    this.successfullAttack = b;
  }

  public boolean getStartedDistribution() {
    return startedDistribution;
  }

  public void setStartedDistribution(boolean startedDistribution) {
    this.startedDistribution = startedDistribution;
  }

  /**
   * @author qiychen
   * @param c1 card1
   * @param c2 card2
   * @param c3 card3
   * @return the number of armies received after trade cards
   */
  public boolean tradeCards(Card c1, Card c2, Card c3) {
    if (!this.startedDistribution) {
      int armies = 0;
      int number = this.getTradedCardSets();
      if (c1.canBeTraded(c2, c3)) {
        switch (number) {
          case 0:
            armies = 4;
            break;
          case 1:
            armies = 6;
            break;
          case 2:
            armies = 8;
            break;
          case 3:
            armies = 10;
            break;
          case 4:
            armies = 12;
            break;
          case 5:
            armies = 15;
            break;
          default:
            armies = 15 + (number - 5) * 5;
        }

        if (this.getTerritories().contains(c1.getTerritory())) {
          c1.getTerritory().setNumberOfArmies(2);
          Main.b.updateLabelTerritory(c1.getTerritory());
        }
        if (this.getTerritories().contains(c2.getTerritory())) {
          c2.getTerritory().setNumberOfArmies(2);
          Main.b.updateLabelTerritory(c2.getTerritory());
        }
        if (this.getTerritories().contains(c3.getTerritory())) {
          c3.getTerritory().setNumberOfArmies(2);
          Main.b.updateLabelTerritory(c3.getTerritory());
        }

        this.setTradedCardSets(number++);
        this.valueActuallyTradedIn = armies;
        this.getCards().remove(c1);
        this.getCards().remove(c2);
        this.getCards().remove(c3);
        sb.append(this.getName() + " traded in one set and got " + armies + " armies.");



        int originalReceivedNumber = this.getTerritories().size() / 3;
        for (Continent c : this.getContinents()) {
          originalReceivedNumber += c.getValue();
        }
        this.setNumberArmiesToDistribute(originalReceivedNumber + this.valueActuallyTradedIn);
        this.valueActuallyTradedIn = 0;
        return true;
      } else {
        return false;
      }
    }
    return false;
  }


  /**
   * @author pcoberge
   * 
   * @param amount of armies that shall be set at territory t
   * @param t is the territory that should receive the amount of armies
   * @return boolean in order to show whether the distribution has been successful or not
   * 
   *         Precondition: only own territories can be chosen, player has enough armies left
   */
  public boolean armyDistribution(int amount, Territory t) {
    setStartedDistribution(true);
    // this.numberArmiesToDistribute = computeAdditionalNumberOfArmies();
    if (t.getOwner().equals(this) && this.numberArmiesToDistribute >= amount) {
      t.setNumberOfArmies(amount);
      this.numberArmiesToDistribute -= amount;
      if (Main.g.isNetworkGame()) {
        if (NetworkController.server != null && (Main.g.getCurrentPlayer() instanceof AiPlayer)) {
          DistributeArmyMessage armyMessage = new DistributeArmyMessage(1, t.getId());
          armyMessage.setColor(Main.g.getCurrentPlayer().getColor().toString());
          NetworkController.gameFinder.getClient().sendMessage(armyMessage);
          return true;
        }
        return true;
      }
      return true;
    } else {
      return false;
    }
  }



  /**
   * @author liwang
   * 
   * @param t is the territory which is selected
   * @return boolean in order to show whether the distribution has been successful or not
   * 
   *         Precondition: only free territories can be chosen
   */
  public boolean initialTerritoryDistribution(Territory t) {
    if (!Main.g.isNetworkGame()) {
      if (t.getOwner() == null) {
        t.setOwner(this);
        this.addTerritories(t);
        this.numberArmiesToDistribute -= 1;
        t.setNumberOfArmies(1);
        return true;
      } else {
        return false;
      }
    } else {
      // This part of the method is for the network game
      // Change the attributes if the current player is human player
      // The attributes of the AiPlayer will be changed after receiving the message in client class
      if (t.getOwner() == null) {
        if (!(Main.g.getCurrentPlayer() instanceof AiPlayer)) {
          t.setOwner(this);
          this.addTerritories(t);
          this.numberArmiesToDistribute -= 1;
          t.setNumberOfArmies(1);
          return true;
        } else {
          // If the current player is AI player, the host player sends the message to the server
            SelectInitialTerritoryMessage message = new SelectInitialTerritoryMessage(t.getId());
            message.setColor(this.color.toString());
            gui.NetworkController.gameFinder.getClient().sendMessage(message);
            return true;
        }
      } else {
        return false;
      }
    }
  }



  /**
   * @author pcoberge
   * 
   * @return number of armies the player receives at the beginning of the current turn
   */
  public int computeAdditionalNumberOfArmies() {
    int result = 0;
    // player receives for three territories one army
    result += this.getTerritories().size() / 3;

    // player receives for each continent the number of additional armies
    for (Continent c : this.getContinents()) {
      result += c.getValue();
    }

    if (result < 3) {
      this.numberArmiesToDistribute = 3;
      return 3;
    } else {
      this.numberArmiesToDistribute = result;
      sb.append(this.getName() + " receives " + result + " armies to distribute.");
      return result;
    }
  }

  /**
   * @param attacker
   * @param defender
   * @param attack
   * @param defend
   * @param numberOfAttackers
   * @return true if attacker conquers the opponent territory
   */
  public boolean attack(Vector<Integer> attacker, Vector<Integer> defender, Territory attack,
      Territory defend, int numberOfAttackers) {
    switch (defender.size()) {
      case (2):
        if (attacker.size() >= 2) {
          System.out
              .println("Case2: attacker: " + attacker.get(1) + " defender: " + defender.get(1));;
          if (attacker.get(1) > defender.get(1)) {
            defend.setNumberOfArmies(-1);
          } else {
            attack.setNumberOfArmies(-1);
          }
        }
      case (1):
        System.out
            .println("Case1: attacker: " + attacker.get(0) + " defender: " + defender.get(0));;
        if (attacker.get(0) > defender.get(0)) {
          defend.setNumberOfArmies(-1);
        } else {
          attack.setNumberOfArmies(-1);
        }
    }

    if (defend.getNumberOfArmies() == 0) {
      System.out.println("defending territory is dead.");
      Player p = defend.getOwner();
      p.getTerritories().remove(defend);
      defend.setOwner(attack.getOwner());
      attack.getOwner().addTerritories(defend);
      Main.g.updateLiveStatistics();
      Main.g.checkAllPlayers();
      attack.setNumberOfArmies(-numberOfAttackers);
      defend.setNumberOfArmies(numberOfAttackers);
      Main.b.updateColorTerritory(defend);
      successfullAttack = true;
      // int randomCard = (int)((Math.random()*Main.g.getCards().size()));
      // p.setCards(Main.g.getCards().get(randomCard));
      if (!Main.g.getPlayers().contains(p)) {
        attack.getOwner().addElimiatedPlayer(p);
        attack.getOwner().setCards(p.getCards());
      }
      if (Main.g.getPlayers().size() == 1) {
        // attacker wins game
        Main.g.setGameState(GameState.END_GAME);
      }
      return true;
    } else {
      if (!(Main.g.getCurrentPlayer() instanceof AiPlayer)) {
        Main.b.updateDiceSlider(attack);
      }
      Main.g.updateLiveStatistics();
      return false;
    }
  }

  /**
   * @author skaur
   * @param moveFrom : territory selected from where the army is going to be moved
   * @param moveTo : territory where the army is going to moved
   * @param armyToMove: no. of armies selected to be move
   * @return: false if invalid parameter are selected; should be called in a while loop until the
   *          player selects valid paramater or skip the fortify gamestate
   */
  public boolean fortify(Territory moveFrom, Territory moveTo, int armyToMove) {
    // if (Main.g.getGameState() == GameState.FORTIFY) {
    // check if both territories belong to the current player
    if (this.equals(moveFrom.getOwner()) && (this.equals(moveTo.getOwner()))) {

      // check if both territories are neighbors
      if (moveFrom.getNeighbor().contains(moveTo)) {
        // int currentNoArmies = moveFrom.getNumberOfArmies();
        // check is current available number of army is greater than the army to move, so that
        // there
        // is at least one
        // army left behind in the territory
        if (moveFrom.getNumberOfArmies() > armyToMove) {
          moveFrom.setReducedNumberOfArmies(armyToMove);
          moveTo.setNumberOfArmies(armyToMove);
          sb.append(this.getName() + " moved " + armyToMove + " from " + moveFrom.getName() + " to "
              + moveTo.getName() + ".");
          return true;
        } else {
          // error messages which may be implemented in the boardgui later
          System.out.println(
              "The number of army to move is greater than the available army in the territory "
                  + moveFrom.getName());
          return false;
        }
      } else {

        System.out.println("Armies can't be moved because " + moveTo.getName() + " and "
            + moveFrom.getName() + " are not neighbors");
        return false;
      }
    } else {

      System.out.println(
          moveFrom.getName() + " or " + moveTo.getName() + " doesnt belong to the current player ");
      return false;
    }

    // }
    // System.out.println("Not in a fortify mode ");
    // return false;
  }

  public boolean equals(Player p) {
    if (this != null && p != null) {
      if (this.getColor().toString().equals(p.getColor().toString())) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
}


