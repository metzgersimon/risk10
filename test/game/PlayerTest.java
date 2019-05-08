package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Vector;
import org.junit.Before;
import org.junit.Test;
import main.Main;

public class PlayerTest {
  Game g = new Game();

  boolean result;


  @Before
  public void initialize() {
    PlayerTest d = new PlayerTest();
  }

  /**
   * Test to check whether a territory is added correctly to a player.
   * 
   * @author pcoberge
   * @author smetzger
   * 
   */
  @Test
  public void addTerritoriesTest() {
    Main.g = g;
    Territory t = new Territory("Brazil", 12, CardSymbol.CANNON, Continente.SOUTHAMERICA);
    Territory tu = new Territory("Venezuela", 10, CardSymbol.CANNON, Continente.SOUTHAMERICA);
    Territory tut = new Territory("Peru", 11, CardSymbol.CAVALRY, Continente.SOUTHAMERICA);
    Player p = new Player("TestX", PlayerColor.RED, g);
    p.addTerritories(t);
    p.addTerritories(tu);
    p.addTerritories(tut);
    assertEquals(3, p.getTerritories().size());
    Territory tutu = new Territory("Argentina", 13, CardSymbol.INFANTRY, Continente.SOUTHAMERICA);
    p.addTerritories(tutu);
    assertEquals(4, p.getTerritories().size());
    // assertEquals(1, p.getContinents().size());
  }

  /**
   * Test whether a territory is removed correctly from a player.
   * 
   * @author pcoberge
   * @author smetzger
   * 
   */
  @Test
  public void lostTerritoriesTest() {
    Main.g = g;
    Territory t = new Territory("Brazil", 12, CardSymbol.CANNON, Continente.SOUTHAMERICA);
    Territory tu = new Territory("Venezuela", 10, CardSymbol.CANNON, Continente.SOUTHAMERICA);
    Territory tut = new Territory("Peru", 11, CardSymbol.CAVALRY, Continente.SOUTHAMERICA);
    Territory tutu = new Territory("Argentina", 13, CardSymbol.INFANTRY, Continente.SOUTHAMERICA);
    Player p = new Player("TestX", PlayerColor.RED, g);
    p.addTerritories(t);
    p.addTerritories(tu);
    p.addTerritories(tut);
    p.addTerritories(tutu);
    // assertEquals(1, p.getContinents().size());
    p.lostTerritories(tutu);
    assertEquals(0, p.getContinents().size());
    assertEquals(3, p.getTerritories().size());

  }

  /**
   * Test, if the additional number of armies at the begin of each turn is right.
   * 
   * @author pcoberge
   * 
   */
  @Test
  public void computeAdditionalNumberOfArmiesTest() {
    PlayerTest t = new PlayerTest();
    // Player has only Territories
    // Test, if player would receive less than 3 armies, so the system would round to 3
    Player p = new Player("Test1", PlayerColor.RED, g);
    p.addTerritories(g.getWorld().getTerritories().get(10));
    p.addTerritories(g.getWorld().getTerritories().get(30));
    p.addTerritories(g.getWorld().getTerritories().get(40));
    p.addTerritories(g.getWorld().getTerritories().get(1));
    p.addTerritories(g.getWorld().getTerritories().get(20));
    p.addTerritories(g.getWorld().getTerritories().get(27));
    assertEquals(3, p.computeAdditionalNumberOfArmies());

    // Test, if player would receive 3 armies
    p.addTerritories(g.getWorld().getTerritories().get(31));
    p.addTerritories(g.getWorld().getTerritories().get(8));
    p.addTerritories(g.getWorld().getTerritories().get(11));
    assertEquals(3, p.computeAdditionalNumberOfArmies());

    // Test, if player would receive more than 3 armies
    p.addTerritories(g.getWorld().getTerritories().get(9));
    p.addTerritories(g.getWorld().getTerritories().get(12));
    p.addTerritories(g.getWorld().getTerritories().get(19));
    assertEquals(4, p.computeAdditionalNumberOfArmies());


    // Player has Territories and Continents
    // Test, if player would receive less 3 armies
    p = new Player("Test2", PlayerColor.RED, g);
    p.addTerritories(Main.g.getWorld().getTerritories().get(39));
    p.addTerritories(Main.g.getWorld().getTerritories().get(40));
    p.addTerritories(Main.g.getWorld().getTerritories().get(41));
    p.addTerritories(Main.g.getWorld().getTerritories().get(42));
    assertEquals(3, p.computeAdditionalNumberOfArmies());

    // Test, if player would receive more than 3 armies
    p.addTerritories(g.getWorld().getTerritories().get(2));
    p.addTerritories(g.getWorld().getTerritories().get(3));
    assertEquals(4, p.computeAdditionalNumberOfArmies());

    // Player has Territories and Continents and traded-in Cards
    // first CardSet
    // Method tradeCards is tested explicitly
    // Test, if player would receive correct number of Armies
    p.tradeCards(new Card(g.getWorld().getTerritories().get(41), false),
        new Card(g.getWorld().getTerritories().get(2), false), new Card(43, true));
    assertEquals(8, p.computeAdditionalNumberOfArmies());

    // Player has Territories and traded-in Cards
    // Test, if player would receive more than 3 armies
    p = new Player("Test3", PlayerColor.RED, g);
    p.addTerritories(g.getWorld().getTerritories().get(41));
    p.addTerritories(g.getWorld().getTerritories().get(2));
    p.tradeCards(new Card(g.getWorld().getTerritories().get(41), false),
        new Card(g.getWorld().getTerritories().get(2), false), new Card(44, true));
    assertFalse(p.computeAdditionalNumberOfArmies() == 3);
  }

  /**
   * Test, whether the initialTerritoryDistribution of the player delivers the desired result.
   * 
   * @author pcoberge
   * @author smetzger
   * 
   */
  @Test
  public void initialTerritoryDistributionTest() {
    Main.g = g;
    Player p = new Player("Test1", PlayerColor.GREEN, g);
    Player p1 = new Player("Test2", PlayerColor.ORANGE, g);
    assertTrue(p.initialTerritoryDistribution(Main.g.getWorld().getTerritories().get(5)));
    assertFalse(p1.initialTerritoryDistribution(Main.g.getWorld().getTerritories().get(5)));
  }

  /**
   * Test, if the number is between 0 and number of Players.
   * 
   * @author pcoberge
   * 
   */
  @Test
  public void armyDistributionTest() {
    Main.g = g;
    Player p = new Player("Test1", PlayerColor.RED, g);
    p.addTerritories(Main.g.getWorld().getTerritories().get(5));
    Main.g.getWorld().getTerritories().get(5).setOwner(p);
    Player p1 = new Player("Test2", PlayerColor.BLUE, g);
    p1.addTerritories(Main.g.getWorld().getTerritories().get(12));
    Main.g.getWorld().getTerritories().get(12).setOwner(p1);
    p.setNumberArmiesToDistribute(10);
    assertTrue(p.armyDistribution(5, Main.g.getWorld().getTerritories().get(5)));
    assertFalse(p.armyDistribution(12, Main.g.getWorld().getTerritories().get(5)));
    assertFalse(p.armyDistribution(5, Main.g.getWorld().getTerritories().get(12)));
    assertFalse(p.armyDistribution(12, Main.g.getWorld().getTerritories().get(12)));
  }

  /**
   * Test, whether the tradeCards method delivers the desired result.
   * 
   * @author pcoberge
   * @author smetzger
   */
  @Test
  public void tradeCardsTest() {
    Main.g.initCardDeck();
    Territory t = new Territory("Greenland", 6, CardSymbol.CAVALRY, Continente.NORTHAMERICA);
    Player p = new Player("Test1", PlayerColor.BLUE, g);
    Player p1 = new Player("Test2", PlayerColor.MAGENTA, g);

    Card c1 = new Card(43, true);
    Card c2 = new Card(44, true);
    Card c3 = new Card(t, false);
    p.tradeCards(c1, c2, c3);
    assertEquals(4, p.getNumberArmiesToDistibute());
  }


  /**
   * Test for the attack method of the player class.
   * 
   * @author qiychen
   *
   */
  @Test
  public void attackTest() {
    Main.g = new Game();
    Player p1 = new Player("Test1", PlayerColor.BLUE, Main.g);
    Main.g.setCurrentPlayer(p1);
    // First attack
    Territory t1 = new Territory("China", 29, CardSymbol.CAVALRY, Continente.ASIA);
    t1.setOwner(p1);
    p1.addTerritories(t1);
    t1.setNumberOfArmies(3);
    int attackBeforearmies1 = t1.getNumberOfArmies();
    Player p2 = new Player("Test2", PlayerColor.GREEN, Main.g);
    Territory t2 = new Territory("Siberia", 27, CardSymbol.CANNON, Continente.ASIA);
    t2.setOwner(p2);
    p2.addTerritories(t2);
    t2.setNumberOfArmies(2);
    int attackBeforearmies2 = t2.getNumberOfArmies();
    Vector<Integer> attacker = Dice.rollDices(t1.getNumberOfArmies());
    Vector<Integer> defender = Dice.rollDices(t2.getNumberOfArmies());
    // test whether the number of territories changed after attack
    try {
      // p1 attack territory Siberia with 2 armies
      p1.attack(attacker, defender, t1, t2, 2);
      System.out.println(attackBeforearmies1 + " after attack " + t1.getNumberOfArmies());
      System.out.println(attackBeforearmies2 + " after attack " + t2.getNumberOfArmies());
      assertTrue(attackBeforearmies1 != t1.getNumberOfArmies()
          || attackBeforearmies2 != t2.getNumberOfArmies());
    } catch (NullPointerException e) {
      System.out.println("Try to reach board gui");
      assertTrue(attackBeforearmies1 != t1.getNumberOfArmies()
          || attackBeforearmies2 != t2.getNumberOfArmies());
    }
    // Second attack
    Territory t3 = new Territory("Great_Britain", 17, CardSymbol.CAVALRY, Continente.EUROPE);
    t3.setOwner(p1);
    p1.addTerritories(t3);
    t3.setNumberOfArmies(3);
    Territory t4 = new Territory("Northern_Europe", 18, CardSymbol.CAVALRY, Continente.EUROPE);
    t4.setOwner(p2);
    p2.addTerritories(t4);
    t4.setNumberOfArmies(1);
    Vector<Integer> attacker2 = Dice.rollDices(t3.getNumberOfArmies());
    Vector<Integer> defender2 = Dice.rollDices(t4.getNumberOfArmies());
    // test whether the owner of the territory changes depending on whether the attack is successful
    // or not
    try {
      // p1 attack territory Northern_Europe with 2 armies
      result = p1.attack(attacker2, defender2, t3, t4, 2);
      if (!result) {
        assertFalse(t3.getOwner() == t4.getOwner());
      }
    } catch (NullPointerException e) {
      if (p1.getSuccessfullAttack()) {
        assertTrue(t3.getOwner() == t4.getOwner());
      } else {
        assertFalse(t3.getOwner() == t4.getOwner());
      }
    }

  }

  /**
   * Test for the fortify method of the player class.
   * 
   * @author skaur
   * 
   */
  @Test
  public void fortifyTest() {
    Main.g = g;
    Player p1 = new Player("Test", PlayerColor.RED, g);
    g.setCurrentPlayer(p1);
    HashMap<Integer, Territory> list = g.getWorld().getTerritories();
    g.setGameState(GameState.FORTIFY);
    Territory test1 = list.get(1); // alaska
    Territory test2 = list.get(3);// alberta
    test1.setOwner(p1);
    test2.setOwner(p1);
    test1.setNumberOfArmies(8);
    p1.addTerritories(test1);
    p1.addTerritories(test2);
    assertTrue(p1.fortify(test1, test2, 5));
    // when army to move is greater than available army
    assertFalse(p1.fortify(test1, test2, 9));
    Territory test3 = list.get(21); // north africa
    // when territory doesnt belong to the current player
    assertFalse(p1.fortify(test1, test3, 5));
    // territories belong to current palyer but are not neighbors
    test3.setOwner(p1);
    assertFalse(p1.fortify(test1, test3, 5));
    // when the game is not in fortify state
    g.setGameState(GameState.ATTACK);
    assertFalse(p1.fortify(test1, test3, 9));
  }
}
