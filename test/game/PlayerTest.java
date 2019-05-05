package game;

import static org.junit.Assert.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;
import org.junit.Test;
import gui.controller.BoardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.Main;

public class PlayerTest {
  Game g = new Game();

  public PlayerTest() {
    Main.g = g;
    FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/gui/BoardGUI.fxml"));
    Main.b = fxmlLoader1.getController();
  }

  /**
   * @author pcoberge
   * 
   *         Test, if the additional number of armies at the begin of each turn is right
   */
  @Test
  public void computeAdditionalNumberOfArmiesTest() {

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
    p.addTerritories(g.getWorld().getTerritories().get(39));
    p.addTerritories(g.getWorld().getTerritories().get(40));
    p.addTerritories(g.getWorld().getTerritories().get(41));
    p.addTerritories(g.getWorld().getTerritories().get(42));
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
   * @author pcoberge
   * 
   *         Test, if the number is between 0 and number of Players
   */
  public void armyDistributionTest() {
    Player p = new Player("Test1", PlayerColor.RED, g);
    p.addTerritories(g.getWorld().getTerritories().get(5));
    p.setNumberArmiesToDistribute(10);
    assertTrue(p.armyDistribution(5, g.getWorld().getTerritories().get(5)));
    assertFalse(p.armyDistribution(12, g.getWorld().getTerritories().get(5)));
    assertFalse(p.armyDistribution(5, g.getWorld().getTerritories().get(12)));
    assertFalse(p.armyDistribution(12, g.getWorld().getTerritories().get(12)));
  }

  /**
   * @author liwang
   * 
   *         Test, if all conditions are checked well
   */
  public void randomNumber() {
    assertTrue(g.randomNumber() >= 0 && g.randomNumber() < g.getPlayers().size());
  }

  /**
   * 
   * @author qiychen
   * 
   *         attacker has 1 dice and defender has 1 dice
   */
  @Test
  public void attackTest1() {
    Game g = new Game();

    Player p1 = new Player("Test", PlayerColor.RED, g);
    Territory tAttacker = g.getWorld().getTerritories().get(1);
    p1.addTerritories(tAttacker);
    tAttacker.setOwner(p1);
    tAttacker.setNumberOfArmies(2);

    Player p2 = new Player("Test2", PlayerColor.RED, g);
    Territory tDefender1 = g.getWorld().getTerritories().get(2);
    p2.addTerritories(tDefender1);
    tDefender1.setOwner(p2);
    tDefender1.setNumberOfArmies(1);
    g.addPlayer(p1);
    g.addPlayer(p2);

    Vector<Integer> attacker = new Vector<>();
    attacker.add(6);
    Vector<Integer> defender = new Vector<>();
    defender.add(4);

    // attacker wins attack and wins game
    assertTrue(p1.attack(attacker, defender, tAttacker, tDefender1, 1));
    assertTrue(p1.getTerritories().contains(tDefender1));
    assertEquals(1, tDefender1.getNumberOfArmies());
    assertTrue(p2.getTerritories().size() == 0);


    Player p3 = new Player("Test3", PlayerColor.RED, g);
    Territory tDefender2 = g.getWorld().getTerritories().get(3);
    p3.addTerritories(tDefender2);
    tDefender2.setOwner(p3);
    tDefender2.setNumberOfArmies(1);
    g.addPlayer(p3);

    Vector<Integer> attacker1 = new Vector<>();
    attacker.add(2);
    Vector<Integer> defender1 = new Vector<>();
    defender.add(4);

    // attacker loses attack
    assertFalse(p1.attack(attacker1, defender1, tAttacker, tDefender2, 1));
    assertFalse(p1.getTerritories().contains(tDefender2));
    assertEquals(1, tDefender2.getNumberOfArmies());
    assertEquals(1, tAttacker.getNumberOfArmies());
    assertTrue(p3.getTerritories().contains(tDefender2));


    Territory tAttacker1 = g.getWorld().getTerritories().get(21);
    p1.addTerritories(tAttacker1);
    tAttacker1.setNumberOfArmies(2);
    tAttacker1.setOwner(p1);
    Player p4 = new Player("Test4", PlayerColor.RED, g);
    Territory tDefender3 = g.getWorld().getTerritories().get(22);
    tDefender3.setNumberOfArmies(1);
    p4.addTerritories(tDefender3);
    tDefender3.setOwner(p4);
    g.addPlayer(p4);

    Vector<Integer> attacker2 = new Vector<>();
    attacker.add(4);
    Vector<Integer> defender2 = new Vector<>();
    defender.add(4);

    // attacker loses attack by same dices
    assertFalse(p4.attack(attacker2, defender2, tAttacker1, tDefender3, 1));
    assertFalse(p1.getTerritories().contains(tDefender3));
    assertEquals(1, tDefender2.getNumberOfArmies());
    assertEquals(1, tAttacker.getNumberOfArmies());
    assertTrue(p4.getTerritories().contains(tDefender3));
  }

  // attacker has 3 dices and defender has 1 dice
  @Test
  public void attackTest2() {
    Game g = new Game();

    Player p1 = new Player("Test", PlayerColor.RED, g);
    Territory tAttacker = g.getWorld().getTerritories().get(1);
    p1.addTerritories(tAttacker);
    tAttacker.setOwner(p1);
    tAttacker.setNumberOfArmies(4);

    Player p2 = new Player("Test2", PlayerColor.RED, g);
    Territory tDefender1 = g.getWorld().getTerritories().get(2);
    p2.addTerritories(tDefender1);
    tDefender1.setOwner(p2);
    tDefender1.setNumberOfArmies(2);
    g.addPlayer(p1);
    g.addPlayer(p2);

    Vector<Integer> attacker = new Vector<>();
    attacker.add(2);
    attacker.add(3);
    attacker.add(6);
    Collections.sort(attacker);
    Vector<Integer> defender = new Vector<>();
    defender.add(4);

    // attacker wins attack but doesnt conquers the territory
    assertFalse(p1.attack(attacker, defender, tAttacker, tDefender1, 3));
    assertFalse(p1.getTerritories().contains(tDefender1));
    assertEquals(1, tDefender1.getNumberOfArmies());
    assertEquals(4, tAttacker.getNumberOfArmies());
    assertTrue(p2.getTerritories().contains(tDefender1));
  }

  // attacker has 3 dices and defender has 2 dices
  @Test
  public void attackTest3() {
    Game g = new Game();

    Player p1 = new Player("Test", PlayerColor.RED, g);
    Territory tAttacker = g.getWorld().getTerritories().get(1);
    p1.addTerritories(tAttacker);
    tAttacker.setOwner(p1);
    tAttacker.setNumberOfArmies(10);

    Player p2 = new Player("Test2", PlayerColor.RED, g);
    Territory tDefender1 = g.getWorld().getTerritories().get(2);
    p2.addTerritories(tDefender1);
    tDefender1.setOwner(p2);
    tDefender1.setNumberOfArmies(2);
    g.addPlayer(p1);
    g.addPlayer(p2);

    Vector<Integer> attacker = new Vector<>();
    attacker.add(2);
    attacker.add(3);
    attacker.add(3);
    Collections.sort(attacker);
    Vector<Integer> defender = new Vector<>();
    defender.add(3);
    defender.add(5);

    // attacker loses attack
    assertFalse(p1.attack(attacker, defender, tAttacker, tDefender1, 6));
    assertFalse(p1.getTerritories().contains(tDefender1));
    assertEquals(2, tDefender1.getNumberOfArmies());
    assertEquals(8, tAttacker.getNumberOfArmies());
    assertTrue(p2.getTerritories().contains(tDefender1));
  }

  // attacker has 3 dices and defender has 1 dice
  @Test
  public void attackTest4() {
    Game g = new Game();

    Player p1 = new Player("Test", PlayerColor.RED, g);
    Territory tAttacker = g.getWorld().getTerritories().get(1);
    p1.addTerritories(tAttacker);
    tAttacker.setOwner(p1);
    tAttacker.setNumberOfArmies(10);

    Player p2 = new Player("Test2", PlayerColor.RED, g);
    Territory tDefender1 = g.getWorld().getTerritories().get(2);
    p2.addTerritories(tDefender1);
    tDefender1.setOwner(p2);
    tDefender1.setNumberOfArmies(1);
    g.addPlayer(p1);
    g.addPlayer(p2);

    Vector<Integer> attacker = new Vector<>();
    attacker.add(2);
    attacker.add(3);
    attacker.add(3);
    Collections.sort(attacker);
    Vector<Integer> defender = new Vector<>();
    defender.add(1);

    // attacker loses attack
    assertTrue(p1.attack(attacker, defender, tAttacker, tDefender1, 6));
    assertTrue(p1.getTerritories().contains(tDefender1));
    assertEquals(6, tDefender1.getNumberOfArmies());
    assertEquals(4, tAttacker.getNumberOfArmies());
    assertFalse(p2.getTerritories().contains(tDefender1));
    assertFalse(g.getPlayers().contains(p2));
  }
}
