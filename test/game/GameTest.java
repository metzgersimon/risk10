package game;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.HashSet;
import org.junit.Test;
import main.Main;

public class GameTest {
  Game g = new Game();
  
  
  
  @Test
  public void addPlayerTest() {
    Player p1 = new Player("Test", PlayerColor.RED, g);
    g.addPlayer(p1);
    assertTrue(g.getPlayers().contains(p1));
    Player p2 = new Player("Test", PlayerColor.RED, g);
    g.addPlayer(p2);
    assertTrue(g.getPlayers().contains(p2));
    assertFalse(p1.getName().equals(p2.getName()));
  }
  
  @Test
  public void checkAllPlayersTest() {
    Main.g = g;
    Player p1 = new Player("Test", PlayerColor.MAGENTA, g);
    Player p2 = new Player("TestL", PlayerColor.GREEN, g);
    Player p3 = new Player("TestLL", PlayerColor.BLUE, g);
    p1.addTerritories( Main.g.getWorld().getTerritories().get(10));
    p1.addTerritories( Main.g.getWorld().getTerritories().get(30));
    p2.addTerritories( Main.g.getWorld().getTerritories().get(40));
    p2.addTerritories( Main.g.getWorld().getTerritories().get(1));
    p2.addTerritories( Main.g.getWorld().getTerritories().get(20));
    p2.addTerritories( Main.g.getWorld().getTerritories().get(27));
    Main.g.addPlayer(p1);
    Main.g.addPlayer(p2);
    Main.g.addPlayer(p3);
    
    Main.g.checkAllPlayers();
    assertEquals(2, Main.g.getPlayers().size());
    
  }
  
  @Test
  public void setNextPlayerTest() {
    Main.g = g;
    Player p1 = new Player("Test", PlayerColor.MAGENTA, g);
    Player p2 = new Player("TestL", PlayerColor.GREEN, g);
    Player p3 = new Player("TestLL", PlayerColor.BLUE, g);
    Player p4 = new Player("TestLLL", PlayerColor.YELLOW, g);
    Player p5 = new Player("TestLLLL", PlayerColor.RED, g);
    Player p6 = new Player("TestLLLLL", PlayerColor.ORANGE, g);
    
    p1.addTerritories( Main.g.getWorld().getTerritories().get(10));
    p2.addTerritories( Main.g.getWorld().getTerritories().get(30));
    p3.addTerritories( Main.g.getWorld().getTerritories().get(40));
    p4.addTerritories( Main.g.getWorld().getTerritories().get(1));
    p5.addTerritories( Main.g.getWorld().getTerritories().get(20));
    p6.addTerritories( Main.g.getWorld().getTerritories().get(27));
    
    Main.g.addPlayer(p1);
    Main.g.addPlayer(p2);
    Main.g.addPlayer(p3);
    Main.g.addPlayer(p4);
    Main.g.addPlayer(p5);
    Main.g.addPlayer(p6);
    
    Main.g.setCurrentPlayer(p1);
    Main.g.setNextPlayer();
    assertTrue(Main.g.getCurrentPlayer().equals(p2));
    
    p3.lostTerritories(p3.getTerritoriesArrayList().get(0));
    Main.g.checkAllPlayers();
    Main.g.setNextPlayer();
    assertFalse(Main.g.getCurrentPlayer().equals(p3));
    assertTrue(Main.g.getCurrentPlayer().equals(p4));  
  }
  
  @Test
  public void initNumberOfArmiesTest() {
    Main.g = g;
    Player p1 = new Player("Test", PlayerColor.MAGENTA, g);
    Player p2 = new Player("TestL", PlayerColor.GREEN, g);
    Player p3 = new Player("TestLL", PlayerColor.BLUE, g);
    
    Main.g.addPlayer(p1);
    Main.g.addPlayer(p2);
    Main.g.addPlayer(p3);
    
    Main.g.initNumberOfArmies();
    assertEquals(35, p1.getNumberArmiesToDistibute());
    Main.g.removePlayer();
    Main.g.initNumberOfArmies();
    assertEquals(40, p2.getNumberArmiesToDistibute());
    
  }
}
