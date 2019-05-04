package game;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import game.Game;
import gui.controller.NetworkController;
import main.Main;
import network.messages.game.AttackMessage;
import network.messages.game.FortifyMessage;
import network.messages.game.FurtherDistributeArmyMessage;
import network.messages.game.DistributeArmyMessage;
import network.messages.game.SelectInitialTerritoryMessage;

/**
 * Class defines a player which 
 * @author qiychen
 *
 */
public class Player implements Serializable {

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
  private boolean fortify;

  public int numberArmiesToDistribute;
  private int tradedCardSets;
  private int numberOfTerritories;
  private int numberOfCards;
  private int valueActuallyTradedIn;
  public int territoriesConquered;
  public int numberOfAttacks;
  public int rank;
  public int sessionWins;

 
  
  /**************************************************
   *                                                *
   *                  Constuctor                    *
   *                                                *
   *************************************************/ 
  
  
  /**
   * Constructor creates a player instance with a name, a color and a game instance.
   * It initializes all private and public attributes.
   * @param name
   * @param color
   * @param g
   */
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
    this.rank = 0;
    this.sessionWins = 0;
  }
  
  
  /**************************************************
   *                                                *
   *               Getter and Setter                *
   *                                                *
   *************************************************/

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
    numberOfTerritories = territories.size();
    return numberOfTerritories;
  }

  public void setNumberOfTerritories(int numberOfTerritories) {
    this.numberOfTerritories = numberOfTerritories;
  }

  public int getNumberOfCards() {
    numberOfCards = cards.size();
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

  public boolean getFortify() {
    return fortify;
  }

  public void setFortify(boolean fortify) {
    this.fortify = fortify;
  }
  
  public HashSet<Continent> getContinents() {
    return continents;
  }

  public void setContinents(HashSet<Continent> continents) {
    this.continents = continents;
  }

 

  public ArrayList<Card> getCards() {
    return cards;
  }

  public void addCard(Card c) {
    this.cards.add(c);
  }
  
  public void setCards(ArrayList<Card> cards) {
    for (Card c : cards) {
      this.cards.add(c);
    }
  }

  public void removeCard(Card c) {
    this.cards.remove(c);
  }

  public PlayerColor getColor() {
    return color;
  }
  
  public void setColor(PlayerColor color) {
    this.color = color;
  }

  public ArrayList<Player> getEliminatedPlayers() {
    return eliminatedPlayers;
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
  
  
  /**********************************************************
   *                                                        *
   *          Methods to add eliminated players,            *
   *          territories and continents                    *
   *          and remove territories and continents         *    
   *                                                        *
   **********************************************************/

  /**
   * @param t
   * Method adds a given territory to the players territory set and if the player now owns a continent through this territory 
   * the territory's continent is added to the continent set of the player.
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
   * Method adds given continent to the player's set of continents.
   * @param c
   */
  public void addContinents(Continent c) {
    continents.add(c);
  }
  
  /**
   * Method adds a given player to a list of players who are already out of the game.
   * @param p
   */
  public void addElimiatedPlayer(Player p) {
    eliminatedPlayers.add(p);
  }
  

  /**
   * @param t
   * Method removes a territory from the players territory set, if the player lost this territory in a battle.
   * If the player owns the whole continent of this territory that continent is removed as well.
   */
  public void lostTerritories(Territory t) {
    territories.remove(t);
    if (this.getContinents().contains(t.getContinent())) {
      this.lostContinents(t.getContinent());
    }
    if (Main.g.isShowTutorialMessages()) {
      Main.b.showMessage(TutorialMessages.lostTerritory);
    }
  }
  
  /**
   * Method removes given continent from the player's set of continents.
   * @param c
   */
  public void lostContinents(Continent c) {
    continents.remove(c);
  }


 

  /**
   * 
   * @author qiychen
   * @param c1 card1
   * @param c2 card2
   * @param c3 card3
   * @return the number of armies received after trade cards
   * 
   * Method allows player to trade in a set of three cards.
   */
  public boolean tradeCards(Card c1, Card c2, Card c3) {
    //trade-in is only possible when the player did not start the army distribution yet
    if (!this.startedDistribution) {
      int armies = 0;
      int number = this.getTradedCardSets();
      //checks if the given cards are a valid set
      if (c1.canBeTraded(c2, c3)) {
        System.out.println(this.getName() + " Karten: " + this.getCards().size());
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
        
        //if a card represents a territory the player owns he gets two additional armies on this territory
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
        
        
        this.setTradedCardSets(++number);
        this.valueActuallyTradedIn = armies;
        
        //removes cards from the players card list and add them to the card deck again
        this.removeCard(c1);
        this.removeCard(c2);
        this.removeCard(c3);
        Main.g.setCard(c1);
        Main.g.setCard(c2);
        Main.g.setCard(c3);

        
        int originalReceivedNumber = this.getTerritories().size() / 3;
        for (Continent c : this.getContinents()) {
          originalReceivedNumber += c.getValue();
        }
        Main.b.showMessage(this.getName() + " trades in cards and receives "
            + this.valueActuallyTradedIn + " number of armies");
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
   * 
   * @author pcoberge
   * 
   * @param amount of armies that shall be set at territory t
   * @param t is the territory that should receive the amount of armies
   * @return boolean in order to show whether the distribution has been successful or not
   * 
   * Method covers the second and third phase, meaning the initial- and in-game army distribution.
   *         Precondition: only own territories can be chosen, player has enough armies left
   */
  public boolean armyDistribution(int amount, Territory t) {
    setStartedDistribution(true);
    if (t.getOwner().equals(this) && this.numberArmiesToDistribute >= amount) {
      if (!Main.g.isNetworkGame()) {
        t.setNumberOfArmies(amount);
        Main.b.handleContinentGlow();
        this.numberArmiesToDistribute -= amount;
        return true;

      } else {
        if (!(Main.g.getCurrentPlayer() instanceof AiPlayer)) {
          t.setNumberOfArmies(amount);
          this.numberArmiesToDistribute -= amount;
          return true;
        } else {
          if ((NetworkController.server != null)) {
            if (Main.g.getGameState().equals(GameState.INITIALIZING_ARMY)) {
              DistributeArmyMessage armyMessage = new DistributeArmyMessage(amount, t.getId());
              armyMessage.setColor(Main.g.getCurrentPlayer().getColor().toString());
              NetworkController.gameFinder.getClient().sendMessage(armyMessage);
              System.out.println("nachricht initial army AI geschickt mit " + amount);
              return true;
            } else if (Main.g.getGameState().equals(GameState.ARMY_DISTRIBUTION)) {
              t.setNumberOfArmies(amount);
              this.numberArmiesToDistribute -= amount;
              FurtherDistributeArmyMessage armyMessage =
                  new FurtherDistributeArmyMessage(amount, t.getId());
              armyMessage.setColor(Main.g.getCurrentPlayer().getColor().toString());
              NetworkController.gameFinder.getClient().sendMessage(armyMessage);
              System.out.println("nachricht further army AI geschickt mit " + amount);
              return true;
            }
          }
          return true;
        }
      }
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
   * Method allows player to choose a territory in the first phase.
   *         Precondition: only free territories can be chosen
   */
  public boolean initialTerritoryDistribution(Territory t) {
    //if the territory is not selected yet
    if (t.getOwner() == null) {
      if (Main.g.isNetworkGame() && (Main.g.getCurrentPlayer() instanceof AiPlayer)) {
        this.initialArmyDistributionNetwork(t);
        Main.b.handleContinentGlow();
        return true;
      }
      
      //set player as owner of the territory and add it to his set of territories
      t.setOwner(this);
      this.addTerritories(t);
      
      //reduces number of armies to distribute and set the army on the territory
      this.numberArmiesToDistribute -= 1;
      t.setNumberOfArmies(1);
      return true;
    } else {
      return false;
    }

  }

  /**
   * @author skaur
   * @param t is the territory which is selected
   * 
   *        This method is for the network game. The host player sends the message to the server on
   *        the behalf of the AiPlayer The attributes of the AiPlayer will be changed after
   *        receiving the message in client class
   */
  public boolean initialArmyDistributionNetwork(Territory t) {
    if (NetworkController.server != null) {
      SelectInitialTerritoryMessage message = new SelectInitialTerritoryMessage(t.getId());
      message.setColor(Main.g.getCurrentPlayer().getColor().toString());
      NetworkController.gameFinder.getClient().sendMessage(message);
      return true;
    }
    return true;
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
    
      return result;
    }
  }

  /**
   * Method defines the attack phase of the game.
   * @author qiychen @author pcoberge @author smetzger
   * @param attacker
   * @param defender
   * @param attack
   * @param defend
   * @param numberOfAttackers
   * @return true if attacker conquers the opponents territory
   */
  public boolean attack(Vector<Integer> attacker, Vector<Integer> defender, Territory attack,
      Territory defend, int numberOfAttackers) {
    attack.getOwner().setNumberOfAttacks(attack.getOwner().getNumberOfAttacks() + 1);
    if (Main.g.isShowTutorialMessages()) {
    Main.b.showMessage(attack.getOwner().getName() + " attacks " + defend.getOwner().getName()
        + "\n-- " + attack.getName().replaceAll("_", " ") + " attacks "
        + defend.getName().replaceAll("_", " ") + " with " + numberOfAttackers + " armies --");
    }
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

    // network
    if (Main.g.isNetworkGame() && (Main.g.getCurrentPlayer() instanceof AiPlayer)) {
      this.attackNetwork(attack.getId(), defend.getId(), defend.getNumberOfArmies() == 0,
          numberOfAttackers, defend.getNumberOfArmies());
    }

    //if the defender has no more armies left on his territory
    if (defend.getNumberOfArmies() == 0) {
      System.out.println("defending territory is dead.");
      Player p = defend.getOwner();
      p.lostTerritories(defend);
      defend.setOwner(attack.getOwner());
      attack.getOwner().addTerritories(defend);
      Main.g.checkAllPlayers();
      attack.setNumberOfArmies(-numberOfAttackers);
      defend.setNumberOfArmies(numberOfAttackers);
      successfullAttack = true;
      Main.b.updateColorTerritory(defend);     
      attack.getOwner().setTerritoriesConquered(attack.getOwner().getTerritoriesConquered() + 1);
      if (Main.g.isShowTutorialMessages()) {
        Main.b.showMessage(game.TutorialMessages.conqueredTerritory);
      }
      
      if (!Main.g.getPlayers().contains(p)) {
        // set loser rank
        p.setRank(Main.g.getPlayers().size());
        attack.getOwner().addElimiatedPlayer(p);
        attack.getOwner().setCards(p.getCards());
        Main.b.showMessage(attack.getOwner().getName() + " defeated " + p.getName() + "!");
      }
      
      //if the attacker won the game
      if (Main.g.getPlayers().size() == 1) {
        Main.b.showMessage("Game Over. " + attack.getOwner().getName() + " won the game!");
        Main.g.setGameState(GameState.END_GAME);
        Main.b.endGame();
        
        //if there are no human player left in the game
      } else if (Main.g.onlyAiPlayersLeft()) {
        Main.b.showMessage("You lost the Game!");
        Main.g.setGameState(GameState.END_GAME);
        Main.b.endGame();
      }
      Main.b.handleContinentGlow();
      return true;
    } else {
      return false;
    }
  }


  /**
   * @author liwang
   * @param attackerID
   * @param defenderID
   * @param ifConquered
   * @param attackerArmies
   * @param defendArmies
   * @return true if the message is successfully sent
   * 
   *         This method is for the network game. The host player sends the message to the server on
   *         the behalf of the AiPlayer The attributes of the AiPlayer will be changed after
   *         receiving the message in client class
   */
  public boolean attackNetwork(int attackerID, int defenderID, boolean ifConquered,
      int attackerArmies, int defendArmies) {
    if (NetworkController.server != null) {
      AttackMessage message =
          new AttackMessage(attackerID, defenderID, ifConquered, attackerArmies, defendArmies);
      message.setColor(Main.g.getCurrentPlayer().getColor().toString());
      NetworkController.gameFinder.getClient().sendMessage(message);
      return true;
    }
    return true;
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
    // check if both territories belong to the current player
    if (this.equals(moveFrom.getOwner()) && (this.equals(moveTo.getOwner()))) {

      // check if both territories are neighbors
      if (moveFrom.getNeighbor().contains(moveTo)) {
        /** check if current available number of army is greater than the army to move, so that
          * there is at least one army left behind on the territory */  
        if (moveFrom.getNumberOfArmies() > armyToMove) {
          moveFrom.setReducedNumberOfArmies(armyToMove);
          moveTo.setNumberOfArmies(armyToMove);
          if (Main.g.isNetworkGame() && (Main.g.getCurrentPlayer() instanceof AiPlayer)) {
            this.fortifyNetwork(moveFrom.getId(), moveTo.getId(), armyToMove);
          }
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * @author liwang
   * @param moveFromTerritoryID
   * @param moveToTerritoryID
   * @param amount
   * @return true if the message is successfully sent
   * 
   *         This method is for the network game. The host player sends the message to the server on
   *         the behalf of the AiPlayer The attributes of the AiPlayer will be changed after
   *         receiving the message in client class
   */
  public boolean fortifyNetwork(int moveFromTerritoryID, int moveToTerritoryID, int amount) {
    if (NetworkController.server != null) {
      FortifyMessage message = new FortifyMessage(moveFromTerritoryID, moveToTerritoryID, amount);
      message.setColor(Main.g.getCurrentPlayer().getColor().toString());
      NetworkController.gameFinder.getClient().sendMessage(message);
      return true;
    }
    return true;
  }

  /**
   * 
   * @param p
   * @return true or false regarding on whether the two players are the same or not.
   */
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


