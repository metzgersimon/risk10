package game;

import static org.junit.Assert.assertEquals;
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
    Main.g = g;
    Main.g.setNetworkGame(true);
    
    AiPlayerMedium a1 = new AiPlayerMedium();
    Player p = new Player("TestX", PlayerColor.GREEN, g);
    Main.g.addPlayer(a1);
    Main.g.addPlayer(p);
    
    Territory t1 = Main.g.getWorld().getTerritories().get(39);
    Territory t2 = Main.g.getWorld().getTerritories().get(40);
    Territory t3 = Main.g.getWorld().getTerritories().get(41);
    Territory t4 = Main.g.getWorld().getTerritories().get(42);
    Territory t5 = Main.g.getWorld().getTerritories().get(33);

    a1.initialTerritoryDistribution(t1);
    a1.initialTerritoryDistribution(t2);
    a1.initialTerritoryDistribution(t3);
    p.initialTerritoryDistribution(t4);
    p.initialTerritoryDistribution(t5);


    t4.setNumberOfArmies(10);
    t5.setNumberOfArmies(20);
    
    a1.setNumberArmiesToDistribute(10);
   
    
    a1.initialArmyDistribution();
    assertEquals(2, t1.getNumberOfArmies());      
  }
  
  @Test
  public void armyDistributionTest() {
    Main.g = g;
    Main.g.setNetworkGame(true);
    
    AiPlayerMedium a1 = new AiPlayerMedium();
    Player p = new Player("TestX", PlayerColor.GREEN, g);
    Main.g.addPlayer(a1);
    Main.g.addPlayer(p);
    Main.g.setCurrentPlayer(a1);
    
    Territory t1 = Main.g.getWorld().getTerritories().get(21);
    Territory t2 = Main.g.getWorld().getTerritories().get(22);
    Territory t3 = Main.g.getWorld().getTerritories().get(23);
    Territory t4 = Main.g.getWorld().getTerritories().get(24);
    Territory t5 = Main.g.getWorld().getTerritories().get(25);
    Territory t6 = Main.g.getWorld().getTerritories().get(26);
    Territory t7 = Main.g.getWorld().getTerritories().get(27);
//    Territory t8 = new Territory("Indonesia", 39, CardSymbol.CAVALRY, Continente.AUSTRALIA);
//    Territory t9 = new Territory("New_Guinea", 40, CardSymbol.CAVALRY, Continente.AUSTRALIA);
//    Territory t10 = new Territory("Western_Australia", 41, CardSymbol.CANNON, Continente.AUSTRALIA);
    Territory t11 = Main.g.getWorld().getTerritories().get(42);
//    Territory t12 = new Territory("Siam", 33, CardSymbol.CANNON, Continente.ASIA);

    Card c1 = new Card(t1, false);
    Card c2 = new Card(t6, false);
    Card c3 = new Card(t11, false);
    a1.addTerritories(t1);
    a1.addTerritories(t2);
    a1.addTerritories(t3);
    p.addTerritories(t4);
    p.addTerritories(t5);

    t1.setNumberOfArmies(1);
    t2.setNumberOfArmies(1);
    t3.setNumberOfArmies(1);
    t4.setNumberOfArmies(10);
    t5.setNumberOfArmies(20);
   
    
//    t1.getHostileNeighbor().add(t5);
//    t2.getHostileNeighbor().add(t4);
//    t3.getHostileNeighbor().add(t4);
    Main.g.setGameState(GameState.END_GAME);
    a1.armyDistribution();
    assertEquals(2, t1.getNumberOfArmies());      
    
  }
  
  public void attack()
}
