package game;

import gui.BoardController;
import javafx.application.Platform;
import main.Main;

/**
 * @author smetzger
 * @author pcoberge
 *
 */
public class AiPlayerEasy extends Player implements AiPlayer {

  public AiPlayerEasy() {
    super(AiPlayerNames.getRandomName(), PlayerColor.values()[Main.g.getPlayers().size()]);
    Main.g.addAiNames(this.getName());
  }

  /**
   * @author smetzger
   * @author pcoberge
   */
  public void initialTerritoryDistribution() {
    int random = 0;
    do {
      random = (random != 0 ? (random % 42) + 1 : (int) (Math.random() * 42) + 1);
      System.out.print(random + " ");
    } while (!super.initialTerritoryDistribution(Main.g.getWorld().getTerritories().get(random)));
    Main.b.updateLabelTerritory(Main.g.getWorld().getTerritories().get(random));
    
    Main.b.updateColorTerritory(Main.g.getWorld().getTerritories().get(random));
    System.out.println(Main.g.getCurrentPlayer().getName() + "--" + Main.g.getCurrentPlayer().getColor()+"--"+Main.g.getWorld().getTerritories().get(random));
//    System.out.println(Main.g.getCurrentPlayer().getColor() + " method");
//    System.out.println(Main.g.getWorld().getTerritories().get(random));
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Main.g.furtherInitialTerritoryDistribution();
  }

  public void initialArmyDistribution() {
    int random = 0;
    Territory territory = null;
    do {
      random = (int) (Math.random() * this.getTerritories().size()) + 1;
      int i = 1;
      for (Territory t : this.getTerritories()) {
        if (i == random) {
          System.out.println(t.getName());
          territory = t;
          break;
        } else {
          System.out.println("ELSE" + i);
          i++;
        }
      }
    } while (!super.armyDistribution(1, territory));
    Main.b.updateLabelTerritory(territory);
    Main.g.furtherInitialArmyDistribution();

  }

  public void armyDistribution() {

  }

  public void attack() {

  }

  public void fortify() {

  }

}
