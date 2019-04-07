package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;
import gui.BoardController;
import main.Main;
import network.Parameter;
import network.client.GameFinder;
import network.server.Server;

public class Game {
  public final static int MAX_PLAYERS = 6;
  // public final static int NEW_GAME = 0;
  // public final static int PLACE_ARMIES = 1;
  // public final static int ATTACKING = 2;
  // public final static int FORTIFY = 3;
  // public final static int GAME_OVER = 4;

  private World w;

  private ArrayList<Player> players;
  private LinkedList<Card> cards;
  private ArrayList<Continent> continents;
  private ArrayList<Territory> territories;
  private Player currentPlayer;
  private GameState gameState;
  public static int attackWon;
  public static int defendWon;
  public HashMap<String, Integer> territoryStats;
  public HashMap<String, Integer> cardStats;
  private HashSet<String> aiNames = new HashSet<String>();



  /** variables for join and host game */
  Server server;
  GameFinder gameFinder;

  /**
   * 
   * @author qiychen Constructor
   */
  public Game() {
    players = new ArrayList<>();
    this.w = new World();

    currentPlayer = null;
    gameState = GameState.NEW_GAME;
    // cards = new CardDeck().shuffle();



  }

  /**
   * Add a new player in the list
   * 
   * @author qiychen
   * @param name
   * @return
   */
  public void addPlayer(Player p) {
    if (gameState == GameState.NEW_GAME) {
      for (int i = 0; i < players.size(); i++) {
        if (p.getName().equals(players.get(i).getName())) {
          p.setName(p.getName() + "1");
        }
      }
      players.add(p);
    }
  }

  /**
   * Reomove a new player from the list
   * 
   * @author liwang
   * @param name
   * @return
   */
  public void removePlayer() {
    if (gameState == GameState.NEW_GAME) {
      players.remove(players.size() - 1);
    }
  }



  /**
   * Set the initial order of Players
   * 
   * @author liwang
   * @param
   * @return
   */
  public Player setPlayerOrder() {

    return players.get(randomNumber());
  }

  /**
   * generate a number between 0 and number of players -1
   * 
   * @author liwang
   * @param
   * @return int the number of Player which starts first
   */
  public int randomNumber() {
    // generate a number between 0 and number of players -1
    return (int) (Math.random() * players.size());
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

  public void initGame() {
    // Compute number of armies
    initNumberOfArmies();

    currentPlayer = players.get(0);
    setGameState(GameState.INITIALIZING_TERRITORY);
    Main.b.prepareInitTerritoryDistribution();
    Main.b.displayGameState();


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
    // Anzahl Territorien aktueller Spieler
    // while GameState == Attack
    // change BoardGUI --> current Player should only could choose his own territories
    // highlight his own territories and all attackable neighbors
    // attack
    // update Board
    // Anzahl Territorien aktueller Spieler --> vergleichen --> ggfs. Karte aus LinkedList Cards
    // nehmen
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

  public Player getLastPlayer() {
    return players.get(players.size() - 1);
  }

  public ArrayList<Territory> getTerritories() {
    return territories;
  }

  public void setTerritories(ArrayList<Territory> territories) {
    this.territories = territories;
  }

  public void setCard(Card c) {
    this.cards.add(c);
  }

  public LinkedList<Card> getCards() {
    return this.cards;
  }

  public HashSet<String> getAiNames() {
    return aiNames;
  }

  public void addAiNames(String aiName) {
    this.aiNames.add(aiName);
  }

  /**
   * 
   * compute the initial number of armies 2 players - 40 3 players - 35 4 players - 30 5 players -
   * 25 6 players - 20
   */
  public void initNumberOfArmies() {
    int number = 50 - (this.players.size() * 5);
    for (int i = 0; i < this.players.size(); i++) {
      this.players.get(i).setNumberArmiesToDistribute(number);
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
    System.out.println(attacker);
    System.out.println(defender);
    switch (defender.size()) {
      case (2):
        if (attacker.get(attacker.size() - 2) > defender.get(1)) {
          defend.setNumberOfArmies(-1);
        } else {
          attack.setNumberOfArmies(-1);
        }
      case (1):
        if (attacker.get(attacker.size() - 1) > defender.get(0)) {
          defend.setNumberOfArmies(-1);
        } else {
          attack.setNumberOfArmies(-1);
        }
        System.out.println();
    }

    if (defend.getNumberOfArmies() == 0) {
      Player p = defend.getOwner();
      p.getTerritories().remove(defend);
      defend.setOwner(attack.getOwner());
      attack.getOwner().addTerritories(defend);
      updateLiveStatistics();
      checkAllPlayers();
      attack.setNumberOfArmies(-numberOfAttackers);
      defend.setNumberOfArmies(numberOfAttackers);
      if (!this.getPlayers().contains(p)) {
        attack.getOwner().addElimiatedPlayer(p);
        attack.getOwner().setCards(p.getCards());
      }
      if (getPlayers().size() == 1) {
        // attacker wins game
        this.gameState = GameState.END_GAME;
      }
      return true;
    } else {
      updateLiveStatistics();
      return false;
    }
  }

  // /**
  // *
  // * @param attack territory
  // * @param defend territory
  // * @param attack with a number of armies
  // * @return Owner from defend territory
  // */
  // public Territory attack(Territory attack, Territory defend, int armies) {
  // this.attackWon = 0;
  // this.defendWon = 0;
  // Territory winner = defend;
  // if (gameState == GameState.ATTACK) {
  // HashSet<Territory> neigbors = attack.getNeighbor();
  // if (attack.getOwner() == currentPlayer && defend.getOwner() != currentPlayer
  // && neigbors.contains(defend) && attack.getNumberOfArmies() > 1) {
  //
  // int attackTotalarmies = attack.getNumberOfArmies();
  // int defendTotalarmies = defend.getNumberOfArmies();
  // // row the dice
  // int attackDiceTimes = this.rollDiceTimes(armies, "attack");
  // int defendDiceTimes = this.rollDiceTimes(defendTotalarmies, "defend");
  // // if the both sides roll same times
  // if (attackDiceTimes == defendDiceTimes) {
  // int remainingAttackArmies = armies;
  // int remainingDefendArmies = defendTotalarmies;
  // for (int i = 0; i < attackDiceTimes; i++) {
  // if (remainingAttackArmies > 3 && remainingDefendArmies > 2) {
  // this.rollDices(3, 2);
  // remainingAttackArmies -= 3;
  // remainingDefendArmies -= 2;
  // } else {
  // this.rollDices(remainingAttackArmies, remainingDefendArmies);
  // }
  // }
  // // attack side rolls more
  // } else if (attackDiceTimes > defendDiceTimes) {
  // int remainingAttackArmies = armies;
  // int remainingDefendArmies = defendTotalarmies;
  // for (int i = 0; i < attackDiceTimes; i++) {
  // if (remainingAttackArmies > 3 && remainingDefendArmies >= 2) {
  // this.rollDices(3, 2);
  // remainingAttackArmies -= 3;
  // remainingDefendArmies -= 2;
  // } else if (remainingAttackArmies > 3 && remainingDefendArmies == 1) {
  // this.rollDices(3, 1);
  // remainingAttackArmies -= 3;
  // remainingDefendArmies -= 1;
  // } else {
  // attackWon += remainingAttackArmies;
  // }
  // }
  // } else {
  // // defend side rolls more
  // for (int i = 0; i < defendDiceTimes; i++) {
  // int remainingAttackArmies = armies;
  // int remainingDefendArmies = defendTotalarmies;
  // if (remainingAttackArmies >= 3 && remainingDefendArmies >= 2) {
  // this.rollDices(3, 2);
  // remainingAttackArmies -= 3;
  // remainingDefendArmies -= 2;
  // } else if (remainingAttackArmies < 3 && remainingDefendArmies >= 2) {
  // this.rollDices(remainingAttackArmies, 2);
  // remainingAttackArmies -= remainingAttackArmies;
  // remainingDefendArmies -= 2;
  // } else {
  // defendWon += remainingDefendArmies;
  // }
  // }
  //
  //
  //
  // }
  // // change armies
  // if (attackWon > 0) {
  // if (attackWon == defendTotalarmies) {
  // winner = attack;
  // Player.territoriesConquered++;
  // defend.setOwner(currentPlayer);
  // defend.setNumberOfArmies2(attackWon); // setNumberOfArmies methode need to be changed
  // // armies moved form attack territory to defend territory
  // attack.setNumberOfArmies2(attackTotalarmies - attackWon);
  // } else {
  // defend.setNumberOfArmies2(defendTotalarmies - attackWon);
  //
  // }
  // } else if (defendWon > 0) {
  // attack.setNumberOfArmies2(attackTotalarmies - defendWon);
  // }
  //
  // } else {
  // System.out.println("Attacking is not possible");
  // }
  //
  //
  //
  // } else {
  // System.out.println("Not in attacking phase");
  // }
  // Player.numberOfAttacks++;
  // return winner;
  //
  // }

  // /**
  // * @author qiychen
  // * @param arr
  // * @return a desceding array (Dice) for example Dice: [5, 4, 2]
  // */
  // public int[] sortDesceding(int[] arr) {
  // Arrays.sort(arr);
  // int[] reverseArray = new int[arr.length];
  // for (int i = 0; i < reverseArray.length; i++) {
  // reverseArray[i] = arr[reverseArray.length - i - 1];
  // }
  // return reverseArray;
  // }

  // /**
  // * @author qiychen
  // * @param armies
  // * @param side from attack or defend
  // * @return the number of times that the dice has been rolled, attack max.3 dices at one time,
  // * defend max.2 times at one time
  // */
  // public int rollDiceTimes(int armies, String side) {
  // if (side.equals("attack")) {
  // double tmp = ((double) armies) / 3.0;
  // int times = (int) Math.ceil(tmp);
  // return times;
  // } else if (side.equals("defend")) {
  // double tmp = ((double) armies) / 2.0;
  // int times = (int) Math.ceil(tmp);
  // return times;
  // } else {
  // System.out.println("please choose a side");
  // return 0;
  // }
  // }

  // /**
  // * @author qiychen
  // * @param attack armies
  // * @param defend armies get the number of wins from attack/defend side the number of wins will
  // be
  // * saved in static variable attackWon and defendWon
  // */
  // public void rollDices(int attack, int defend) {
  // Dice dice = new Dice();
  // int[] attackDice = dice.rollDices(attack);
  // int[] defendDice = dice.rollDices(defend);
  // // sort Dice descending
  // attackDice = this.sortDesceding(attackDice);
  // defendDice = this.sortDesceding(defendDice);
  // // compare dice number
  // int length;// select min length
  // if (attackDice.length <= defendDice.length) {
  // length = attackDice.length;
  // } else {
  // length = defendDice.length;
  // }
  // for (int i = 0; i < length; i++) {
  // if (attackDice[i] > defendDice[i]) {
  // attackWon++;
  // } else {
  // defendWon++;
  // }
  // }
  //
  // }

  /**
   * @author prto, @author smetzger
   * @param players
   * @return returns ArrayList of players who have 0 territories left and thus lost
   */
  // checks if all players still have armies left, otherwise return losing players
  public ArrayList<Player> checkAllPlayers() {
    ArrayList<Player> lostPlayers = new ArrayList<Player>();
    for (int i = 0; i < this.players.size(); i++) {
      if (this.players.get(i).getTerritories().size() == 0) {
        lostPlayers.add(this.players.get(i));
        this.players.remove(this.players.get(i));
      }
    }
    return lostPlayers;
  }

  // get amount of territories and cards owned for each player
  public void updateLiveStatistics() {
    territoryStats = new HashMap<String, Integer>();
    for (Player x : this.players) {
      territoryStats.put(x.getName(), x.getNumberOfTerritories());
    }

    cardStats = new HashMap<String, Integer>();
    for (Player x : this.players) {
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
        // HashSet<Territory> neighbors = moveFrom.getNeighbor();
        // if (neighbors.contains(moveTo)) {
        // beide Zeilen in eine verpackt
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

  /**
   * Shows statistics when the game is over
   * 
   * @author prto
   */
  public void endGame() {
    String name;
    int numAttacks;
    int tConquered;
    // Get statistics from each player
    for (Player x : players) {
      name = x.getName();
      numAttacks = x.numberOfAttacks;
      tConquered = x.territoriesConquered;
      // TODO add into GUI
    }
  }

  public boolean unconqueredTerritories() {
    for (Territory t : this.w.getTerritories().values()) {
      if (t.getOwner() == null) {
        return true;
      }
    }
    return false;
  }

  public Player nextPlayer() {
    for (int i = 0; i < players.size() - 1; i++) {
      if (players.get(i).equals(currentPlayer)) {
        return (this.currentPlayer = players.get(i + 1));
      }
    }
    return (this.currentPlayer = players.get(0));
  }

  /**
   * @author smetzger
   * @author pcoberge
   */
  public void furtherInitialTerritoryDistribution() {
    this.nextPlayer();
    if (this.unconqueredTerritories()) {
      Main.b.prepareInitTerritoryDistribution();
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        p.initialTerritoryDistribution();
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    } else {
      this.setGameState(GameState.INITIALIZING_ARMY);
      Main.b.prepareArmyDistribution();
      Main.b.displayGameState();
    }
  }

  /**
   * @author smetzger
   * @author pcoberge
   */
  public void furtherInitialArmyDistribution() {
    this.nextPlayer();
    if (this.getLastPlayer().getNumberArmiesToDistibute() != 0) {
      Main.b.prepareArmyDistribution();
      if (this.getCurrentPlayer() instanceof AiPlayer) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        AiPlayer p = (AiPlayer) this.getCurrentPlayer();
        p.initialArmyDistribution();

      }
    } else {
      this.setGameState(GameState.ARMY_DISTRIBUTION);
      Main.b.prepareArmyDistribution();
      // Main.b.displayArmyDistribution();
      Main.b.displayGameState();
    }
  }

  public void furtherFortify() {
    this.nextPlayer();
    Main.b.prepareArmyDistribution();
    this.getCurrentPlayer().computeAdditionalNumberOfArmies();
    if (this.getCurrentPlayer() instanceof AiPlayer) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      AiPlayer p = (AiPlayer) this.getCurrentPlayer();
      p.armyDistribution();
    }
    this.setGameState(GameState.ARMY_DISTRIBUTION);
    Main.b.displayGameState();
  }


  /**
   * @author skaur
   * @param noOfPlayers no of selected players by the host this method creates an instance of server
   *        and starts the server thread on a specified port, after calling this methods host should
   *        join the game lobby (game lobby UI opens)
   */
  public void hostGame(Player hostPlayer, int noOfPlayers) {
    this.server = new Server(Parameter.PORT, noOfPlayers);
    this.server.start();
    // joinHostLobby(currentPlayer): call this method to jointheHostGameLobbyGUI;
  }

  /**
   * @author skaur this methods creates an instance of gamefinder class and starts looking for
   *         broadcasting server gameFinder class then creates a client for the current player
   */
  public void joinGame(Player player) {
    this.gameFinder = new GameFinder();
  }

  public Server getServer() {
    return server;
  }

  public void setServer(Server server) {
    this.server = server;
  }

  public GameFinder getGameFinder() {
    return gameFinder;
  }

  public void setGameFinder(GameFinder gameFinder) {
    this.gameFinder = gameFinder;
  }
}


