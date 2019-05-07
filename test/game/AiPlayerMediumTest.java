package game;

import static org.junit.Assert.*;
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
    Territory t1 = new Territory("Indonesia", 39, CardSymbol.CAVALRY, Continente.AUSTRALIA);
    Territory t2 = new Territory("New_Guinea", 40, CardSymbol.CAVALRY, Continente.AUSTRALIA);
    Territory t3 = new Territory("Western_Australia", 41, CardSymbol.CANNON, Continente.AUSTRALIA);
    Territory t4 = new Territory("Eastern_Australia", 42, CardSymbol.INFANTRY, Continente.AUSTRALIA);
    Territory t5 = new Territory("Siam", 33, CardSymbol.CANNON, Continente.ASIA);
    a1.addTerritories(t1);
    a1.addTerritories(t2);
    a1.addTerritories(t3);
    
    a1.initialTerritoryDistribution();
    assertTrue(a1.getTerritories().contains(t4) || a1.getTerritories().contains(t5));
   
       
  }

}
