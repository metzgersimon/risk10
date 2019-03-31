package game;

import static org.junit.Assert.*;
import org.junit.Test;

public class CardTest {
  Game g = new Game();

  /**
   * @author pcoberge
   * 
   *         Test, whether the method notices if a card triple is valid or not
   */
  @Test
  public void validCardSetTest() {
    Card c1 = new Card(g.getWorld().getTerritories().get(1), false);
    Card c2 = new Card(g.getWorld().getTerritories().get(2), false);
    Card c3 = new Card(g.getWorld().getTerritories().get(3), false);
    Card c4 = new Card(g.getWorld().getTerritories().get(4), false);
    Card c5 = new Card(g.getWorld().getTerritories().get(5), false);

    Card wildCard1 = new Card(true);
    Card wildCard2 = new Card(true);

    // Test, whether three Cards with different symbols are valid
    assertFalse(c1.validCardSet(c2, c5));
    // Test, whether three cards with two different symbols are valid
    assertFalse(c1.validCardSet(c2, c3));
    // Test, whether three Cards with same symbols are valid
    assertTrue(c1.validCardSet(c3, c4));
    // Test, whether one Card and two Wildcards are valid
    assertTrue(c1.validCardSet(wildCard1, wildCard2));
    // Test, whether two Cards with different symbols and one Wildcard are valid
    assertFalse(c1.validCardSet(c2, wildCard1));
    // Test, whether two Cards with same symbols and one Wildcard are valid
    assertTrue(c1.validCardSet(c3, wildCard1));
  }
}
