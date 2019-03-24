package gui;

import java.util.HashMap;
import game.Game;
import game.Territory;
import game.World;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class BoardGUI_Elements {
  private static Region easternAustralia;
  private static Label easternAustraliaHeadline;
  private static Label easternAustraliaNoA;
  
  private static Region westernAustralia;
  private static Label westernAustraliaHeadline;
  private static Label westernAustraliaNoA;

  public static void connectTerritoryBoardRegion() {
   HashMap<Integer, Territory> t = Game.getWorld().getTerritories();
   t.get(41).setBoardRegion(new BoardRegion(westernAustralia, westernAustraliaHeadline, westernAustraliaNoA));
   t.get(42).setBoardRegion(new BoardRegion(easternAustralia, easternAustraliaHeadline, easternAustraliaNoA));
    
  }

}
