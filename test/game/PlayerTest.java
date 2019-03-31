package game;

import static org.junit.Assert.*;
import org.junit.Test;

public class PlayerTest {
  private Game g = new Game();

  /**
   * @author pcoberge
   * 
   *         Test, if the additional number of armies at the begin of each turn is right
   */
  @Test
  public void computeAdditionalNumberOfArmiesTest() {

    // Player has only Territories
    // Test, if player would receive less than 3 armies, so the system would round to 3
    Player p = new Player("Test1", g);
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
    p = new Player("Test2", g);
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
        new Card(g.getWorld().getTerritories().get(2), false), new Card(true));
    assertEquals(8, p.computeAdditionalNumberOfArmies());

    // Player has Territories and traded-in Cards
    // Test, if player would receive more than 3 armies
    p = new Player("Test3", g);
    p.addTerritories(g.getWorld().getTerritories().get(41));
    p.addTerritories(g.getWorld().getTerritories().get(2));
    p.tradeCards(new Card(g.getWorld().getTerritories().get(41), false),
        new Card(g.getWorld().getTerritories().get(2), false), new Card(true));
    assertFalse(p.computeAdditionalNumberOfArmies() == 3);
  }


  /**
   * @author pcoberge
   * 
   *         Test, if all conditions are checked well
   */
  public void armyDistributionTest() {
    Player p = new Player("Test1", g);
    p.addTerritories(g.getWorld().getTerritories().get(5));
    p.setNumberArmiesToDistribute(10);
    assertTrue(p.armyDistribution(5, g.getWorld().getTerritories().get(5)));
    assertFalse(p.armyDistribution(12, g.getWorld().getTerritories().get(5)));
    assertFalse(p.armyDistribution(5, g.getWorld().getTerritories().get(12)));
    assertFalse(p.armyDistribution(12, g.getWorld().getTerritories().get(12)));
  }
}
