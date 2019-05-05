package game;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import main.Main;

public class TerritoryTest {

  @Test
  public void getHostileNeighborTest() {
    Main.g = new Game();
    Territory t = Main.g.getWorld().getTerritories().get(41);
    Player p1 = new Player("Test1", PlayerColor.RED, Main.g);
    p1.addTerritories(t);
    t.setOwner(p1);

    assertEquals(3, t.getHostileNeighbor().size());

    Player p2 = new Player("Test2", PlayerColor.BLUE, Main.g);
    p2.addTerritories(Main.g.getWorld().getTerritories().get(42));
    Main.g.getWorld().getTerritories().get(42).setOwner(p2);

    assertEquals(3, t.getHostileNeighbor().size());

    p1.addTerritories(Main.g.getWorld().getTerritories().get(40));
    Main.g.getWorld().getTerritories().get(40).setOwner(p1);

    assertEquals(2, t.getHostileNeighbor().size());
  }
}
