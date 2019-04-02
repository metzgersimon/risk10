package game;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.HashSet;
import org.junit.Test;
import main.Main;

public class GameTest {
  Game g = Main.g;


  @Test
  public void fortifyTest() {
    Player p1 = new Player("Test");
    g.setCurrentPlayer(p1);
    HashMap<Integer, Territory> list = g.getWorld().getTerritories();
    g.setGameState(GameState.FORTIFY);
    Territory test1 = list.get(1); // alaska
    Territory test2 = list.get(3);// alberta
    test1.setOwner(p1);
    test2.setOwner(p1);
    test1.setNumberOfArmies(8);
    assertTrue(g.fortify(test1, test2, 5));
    // when army to move is greater than available army
    assertFalse(g.fortify(test1, test2, 9));
    Territory test3 = list.get(21); // north africa
    // when territory doesnt belong to the current player
    assertFalse(g.fortify(test1, test3, 5));
    // territories belong to current palyer but are not neighbors
    test3.setOwner(p1);
    assertFalse(g.fortify(test1, test3, 5));
    // when the game is not in fortify state
    g.setGameState(GameState.ATTACK);
    assertFalse(g.fortify(test1, test3, 9));
  }


  @Test
  public void addPlayerTest() {
    Player p1 = new Player("Test");
    g.addPlayer(p1);
    assertTrue(g.getPlayers().contains(p1));
    Player p2 = new Player("Test");
    g.addPlayer(p2);
    assertTrue(g.getPlayers().contains(p2));
    assertFalse(p1.getName().equals(p2.getName()));
  }
}
