package game;

import static org.junit.Assert.*;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;
/**
 * 
 * @author qiychen
 *
 */
public class AttackTest {
  Game g=new Game();

  @Test
  public void testsortDescedingArray() {
    int[] arr= {2,4,6};
    int[] arr2= {6,4,2};
    assertArrayEquals(arr2, g.sortDesceding(arr));
    int[] arr3= {5,6};
    int[] arr4= {6,5};
    assertArrayEquals(arr4, g.sortDesceding(arr3));
  }
  @Test
  public void testRollDiceTimes() {
    assertEquals(4, g.rollDiceTimes(10, "attack"));
    assertEquals(3, g.rollDiceTimes(5, "defend"));
  }
  
  @Test
  public void testRollDices() {
   int attackWon=Game.attackWon;
   int defendWon=Game.defendWon;
   g.rollDices(3, 2);
   assertTrue(attackWon!=Game.attackWon || defendWon!=Game.defendWon);
  }
  @Test
  public void testAttack() {
   Player p1 = new Player("Test", g);
    Player p2 =new Player("Test2", g);
    g.setCurrentPlayer(p1);
    HashMap<Integer, Territory> list = g.getWorld().getTerritories();
    g.setGameState(GameState.ATTACK);
    Territory t1=list.get(1);
    Territory t2=list.get(3);
    t1.setOwner(p1);
    t2.setOwner(p2);
    t1.setNumberOfArmies2(5);
    t2.setNumberOfArmies2(3);
    int attakTerritoryNumber=t1.getNumberOfArmies();
    int defendTerritoryNumber=t2.getNumberOfArmies();
    g.attack(t1, t2, 4);
    assertTrue(attakTerritoryNumber!=t1.getNumberOfArmies() ||
        defendTerritoryNumber!=t2.getNumberOfArmies());
  }
}
