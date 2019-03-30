package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Game {
  public final static int MAX_PLAYERS = 6;
  // public final static int NEW_GAME = 0;
  // public final static int PLACE_ARMIES = 1;
  // public final static int ATTACKING = 2;
  // public final static int FORTIFY = 3;
  // public final static int GAME_OVER = 4;

  private World w;

  private ArrayList<Player> players;
  private ArrayList<Card> cards;
  private ArrayList<Continent> continents;
  private ArrayList<Territory> territories;
  private Player currentPlayer;
  private GameState gameState;
  private static int attackWon;
  private static int defendWon;
  public HashMap<String, Integer> territoryStats;
  public HashMap<String, Integer> cardStats;

  /**
   * @author qiychen Constructor
   */
  public Game() {
    players = new ArrayList<>();
    territories = new ArrayList<>(); // To do
    continents = new ArrayList<>();
    cards = new ArrayList<>();
    currentPlayer = null;
    gameState = GameState.NEW_GAME;
    this.w = new World();

  }

  /**
   * Add a new player in the list
   * 
   * @author qiychen
   * @param name
   * @return
   */
  public boolean addPlayer(String name) {
    if (gameState == GameState.NEW_GAME) {
      for (int i = 0; i < players.size(); i++) {
        if (name.equals(players.get(i).getName())) {
          return false;
        }
      }
      Player newPlayer = new Player(name, this);
      players.add(newPlayer);
      return true;

    } else {
      return false;
    }
  }

  public void startGame() {

  }

  /**
   * determine the current player
   * 
   * @author qiychen
   * @param name
   * @return
   */
  public Player getCurrentPlayer(String name) {
    for (int i = 0; i < players.size(); i++) {
      if (players.get(i).getName().equals(name)) {
        currentPlayer = players.get(i);
      }
    }
    return currentPlayer;

  }

  public void playRisk() {
    // Compute number of armies
    // define player order

    // prepare BoardGUI
    // while unconquered territories
    // initTerritoryDistribution
    // change Player

    // while next Player has army left
    // change BoardGUI --> current Player should only could choose his own territories
    // highlight his own territories and disable all others
    // initArmyDistribution()
    // update Board
    // change Player

    // while Game is running
    //
    // while armies left
    // change BoardGUI --> current Player should only could choose his own territories
    // highlight his own territories and disable all others
    // distributeArmies
    //
    // while GameState == Attack
    // change BoardGUI --> current Player should only could choose his own territories
    // highlight his own territories and all attackable neighbors
    // attack
    // update Board
    //
    // change BoardGUI --> update only current players territories, disable all others
    // fortify
    // update BoardGUI
    // change Player
  }

  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }

  public void setCurrentPlayer(Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public World getWorld() {
    return w;
  }

  public GameState getGameState() {
    return this.gameState;
  }

  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }
  
  public ArrayList<Player> getPlayers() {
    return players;
  }

  public void setPlayers(ArrayList<Player> players) {
    this.players = players;
  }

  public ArrayList<Territory> getTerritories() {
    return territories;
  }

  public void setTerritories(ArrayList<Territory> territories) {
    this.territories = territories;
  }

  /**
   * @author qiychen
   * @param c1 card1
   * @param c2 card2
   * @param c3 card3
   * @return the number of armies received after trade cards
   */
  public int tradeCards(Card c1, Card c2, Card c3) {
    int armies = 0;
    int number = currentPlayer.getTradeNumber();
    if (canbeTraded(c1, c2, c3)) {
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

      }

      if (number > 5) {
        armies = 15 + (number - 5) * 5;
      }
      currentPlayer.setTradeNumber(number++);
    }
    return armies;
  }

  /**
   * @author qiychen
   * @param c1 card1
   * @param c2 card2
   * @param c3 card3
   * @return whether 3 cards can be traded or not
   */
  public boolean canbeTraded(Card c1, Card c2, Card c3) {
    CardSymbol sym1 = c1.getTerritory().getSym();
    CardSymbol sym2 = c2.getTerritory().getSym();
    CardSymbol sym3 = c3.getTerritory().getSym();
    // same artillery pictures without wildcards
    boolean same = (sym1 == sym2) && (sym2 == sym3);
    // all types of cards without wildcards
    boolean different = (sym1 != sym2) && (sym2 != sym3) && (sym1 != sym3);
    // same artillery pictures with one wildcard
    boolean same2 = ((sym1 == sym2) && (sym3 == CardSymbol.WILDCARD))
        || ((sym1 == sym3) && (sym2 == CardSymbol.WILDCARD))
        || ((sym2 == sym3) && (sym1 == CardSymbol.WILDCARD));
    // same artillery pictures with two wildcards, or all types of cards with two wildcards
    boolean same3 = ((sym1 == sym2) && (sym1 == CardSymbol.WILDCARD))
        || ((sym1 == sym3) && (sym1 == CardSymbol.WILDCARD))
        || ((sym2 == sym3) && (sym2 == CardSymbol.WILDCARD));
    // all types of cards with one wildcard
    boolean different2 =
        (sym1 == CardSymbol.WILDCARD && sym2 != sym3 && sym2 != CardSymbol.WILDCARD)
            || (sym2 == CardSymbol.WILDCARD && sym1 != sym3 && sym1 != CardSymbol.WILDCARD)
            || (sym3 == CardSymbol.WILDCARD && sym1 != sym2 && sym1 != CardSymbol.WILDCARD);
    boolean result = same || same2 || same3 || different || different2;
    return result;
  }

  /**
   *
   * @param attack territory
   * @param defend territory
   * @param attack with a number of armies
   */
  public void attack(Territory attack, Territory defend, int armies) {
    if (gameState == GameState.ATTACK) {
      HashSet<Territory> neigbors = attack.getNeighbor();
      if (attack.getOwner() == currentPlayer && defend.getOwner() != currentPlayer
          && neigbors.contains(defend) && attack.getNumberOfArmies() > 1) {
        

        int attackTotalarmies = attack.getNumberOfArmies();
        int defendTotalarmies = defend.getNumberOfArmies();
        // row the dice
        int attackDiceTimes = this.rollDiceTimes(armies, "attack");
        int defendDiceTimes = this.rollDiceTimes(defendTotalarmies, "defend");
        // if the both sides own less than 3 armies
        if (attackDiceTimes == defendDiceTimes && attackDiceTimes == 1) {
          this.rollDices(armies, defendTotalarmies);
        } else {
          // Todo: Dices are rolled more than one times



        }
        // change armies
        if (attackWon > 0) {
          if (attackWon == defendTotalarmies) {
            defend.setOwner(currentPlayer);
            defend.setNumberOfArmies(attackWon);
            // armies moved form attack territory to defend territory
            attack.setNumberOfArmies(attackTotalarmies - attackWon);
          } else {
            defend.setNumberOfArmies(defendTotalarmies - attackWon);

          }
        } else if (defendWon > 0) {
          attack.setNumberOfArmies(attackTotalarmies - defendWon);
        }

      } else {
        System.out.println("Attacking is not possible");
      }



    } else {
      System.out.println("Not in attacking phase");
    }

  }

  /**
   * @author qiychen
   * @param arr
   * @return a desceding array (Dice) for example Dice: [5, 4, 2]
   */
  public int[] sortDesceding(int[] arr) {
    Arrays.sort(arr);
    int[] reverseArray = new int[arr.length];
    for (int i = 0; i < reverseArray.length; i++) {
      reverseArray[i] = arr[reverseArray.length - i - 1];
    }
    return reverseArray;
  }

  /**
   * @author qiychen
   * @param armies
   * @param side from attack or defend
   * @return the number of times that the dice has been rolled, attack max.3 dices at one time,
   *         defend max.2 times at one time
   */
  public int rollDiceTimes(int armies, String side) {
    if (side.equals("attack")) {
      double tmp = ((double) armies) / 3;
      int times = (int) Math.ceil(tmp);
      return times;
    } else if (side.equals("defend")) {
      double tmp = ((double) armies) / 2;
      int times = (int) Math.ceil(tmp);
      return times;
    } else {
      System.out.println("please choose a side");
      return 0;
    }
  }

  /**
   * @author qiychen
   * @param attack armies
   * @param defend armies get the number of wins from attack/defend side the number of wins will be
   *        saved in static variable attackWon and defendWon
   */
  public void rollDices(int attack, int defend) {
    attackWon = 0;
    defendWon = 0;
    Dice dice = new Dice();
    int[] attackDice = dice.rollDices(attack);
    int[] defendDice = dice.rollDices(defend);
    // sort Dice descending
    attackDice = this.sortDesceding(attackDice);
    defendDice = this.sortDesceding(defendDice);
    // compare dice number
    int length;// select min length
    if (attackDice.length <= defendDice.length) {
      length = attackDice.length;
    } else {
      length = defendDice.length;
    }
    for (int i = 0; i < length; i++) {
      if (attackDice[i] > defendDice[i]) {
        attackWon++;
      } else {
        defendWon++;
      }
    }

  }
  
  /**
   * @author prto, @author smetzger
   * @param players
   * @return returns ArrayList of players who have 0 territories left and thus lost
   */
  //checks if all players still have armies left, otherwise return losing players
  public ArrayList<Player> checkAllPlayers() {
    ArrayList<Player> lostPlayers = new ArrayList<Player>();
    for(Player p: this.players) {
      if(p.getTerritories().size() == 0) {
        lostPlayers.add(p);
        this.players.remove(p);
      }
    }
    return lostPlayers;
  }
  
  //get amount of territories and cards owned for each player
  public void updateLiveStatistics() {
    territoryStats = new HashMap<String, Integer>();
    for(Player x : this.players) {
      territoryStats.put(x.getName(), x.getNumberOfTerritories());
    }
    
    cardStats = new HashMap<String, Integer>();
    for(Player x : this.players) {
      cardStats.put(x.getName(), x.getNumberOfCards());
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
    if (gameState == GameState.FORTIFY) {
      // check if both territories belong to the current player
      if (this.currentPlayer.equals(moveFrom.getOwner())
          && (this.currentPlayer.equals(moveTo.getOwner()))) {
        HashSet<Territory> neighbors = moveFrom.getNeighbor();
        // check if both territories are neighbors
        if (neighbors.contains(moveTo)) {
          int currentNoArmies = moveFrom.getNumberOfArmies();
          // check is current available no of army is greater than the army to move, so that there
          // is at least one
          // army left behind in the territory
          if (currentNoArmies > armyToMove) {
            moveFrom.setReducedNumberOfArmies(armyToMove);
            moveTo.setNumberOfArmies(armyToMove);
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

        System.out.println(moveFrom.getName() + " or " + moveTo.getName()
            + " doesnt belong to the current player ");
        return false;
      }

    }
    System.out.println("Not in a fortify mode ");
    return false;
  }

}


