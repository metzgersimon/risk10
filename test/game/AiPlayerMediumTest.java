package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import main.Main;

public class AiPlayerMediumTest {
  Game g = new Game();
  
  @Test
  public void initialTerritoryDistributionTest() {
    Main.g = g;
    Main.g.setNetworkGame(true);
    
    AiPlayerMedium a1 = new AiPlayerMedium();
    Main.g.addPlayer(a1);
    Territory t1 = Main.g.getWorld().getTerritories().get(39);
    Territory t2 = Main.g.getWorld().getTerritories().get(40);
    Territory t3 = Main.g.getWorld().getTerritories().get(28);
    Territory t4 = Main.g.getWorld().getTerritories().get(33);
    a1.addTerritories(t1);
    a1.addTerritories(t2);
    a1.addTerritories(t3);
    a1.addTerritories(t4);
    
   
    a1.initialTerritoryDistribution();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertEquals(5, a1.getTerritories().size());
  }

  @Test
  public void initialArmyDistributionTest() {
    Main.g = new Game();
    Main.g.setNetworkGame(true);
    
    AiPlayerMedium a1 = new AiPlayerMedium();
    Player p = new Player("TestX", PlayerColor.GREEN, g);
    Main.g.addPlayer(a1);
    Main.g.addPlayer(p);
    
    Territory t1 = Main.g.getWorld().getTerritories().get(31);
    Territory t2 = Main.g.getWorld().getTerritories().get(36);
    Territory t3 = Main.g.getWorld().getTerritories().get(41);
    Territory t4 = Main.g.getWorld().getTerritories().get(42);
    Territory t5 = Main.g.getWorld().getTerritories().get(33);

    a1.setNumberArmiesToDistribute(10);
    
    a1.initialTerritoryDistribution(t1);
    a1.initialTerritoryDistribution(t2);
    a1.initialTerritoryDistribution(t3);
    p.initialTerritoryDistribution(t4);
    p.initialTerritoryDistribution(t5);


    
    a1.initialArmyDistribution();
    assertTrue(t1.getNumberOfArmies() == 2 || t2.getNumberOfArmies() == 2 || t3.getNumberOfArmies() == 2);      
  }
}
