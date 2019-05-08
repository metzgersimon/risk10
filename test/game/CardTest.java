package game;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class CardTest {
  Game g = new Game();

  /**
   * Test, whether the method notices if a card set is valid or not.
   * 
   * @author pcoberge
   *        
   */
  @Test
  public void canBeTradedTest() {
    Card c1 = new Card(g.getWorld().getTerritories().get(1), false);
    Card c2 = new Card(g.getWorld().getTerritories().get(2), false);
    Card c3 = new Card(g.getWorld().getTerritories().get(3), false);
    Card c4 = new Card(g.getWorld().getTerritories().get(4), false);
    Card c5 = new Card(g.getWorld().getTerritories().get(5), false);
    Card wildCard1 = new Card(43,true);
    Card wildCard2 = new Card(44,true);
    // Test, whether one Card and two Wildcards are valid
    assertTrue(c1.canBeTraded(wildCard1, wildCard2));
    // Test, whether three Cards with different symbols are valid
    assertTrue(c1.canBeTraded(c2, c5));
    // Test, whether three Cards with same symbols are valid
    assertTrue(c1.canBeTraded(c3, c4));
    // Test, whether two Cards with different symbols and one Wildcard are valid
    assertTrue(c1.canBeTraded(c2, wildCard1));
    // Test, whether two Cards with same symbols and one Wildcard are valid
    assertTrue(c1.canBeTraded(c3, wildCard1));
    // Test, whether three cards with two different symbols are valid
    assertFalse(c1.canBeTraded(c2, c3));
    
    
  }
}
