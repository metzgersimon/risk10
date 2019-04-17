package game;

import java.util.HashSet;
import main.Main;

public class AiPlayerHard extends Player implements AiPlayer {
  
  

  public AiPlayerHard() {
    super(AiPlayerNames.getRandomName(), PlayerColor.values()[Main.g.getPlayers().size()], Main.g);
    Main.g.addAiNames(this.getName());
  }

  public void initialTerritoryDistribution() {
    Territory selection = null;
    int minHostile = 42;

    // territories with least number of hostile neighbors
    for (Territory t : Main.g.getWorld().getTerritories().values()) {
      if (t.getOwner() == null) {
        minHostile =
            minHostile > t.getHostileNeighbor(this).size() ? t.getHostileNeighbor(this).size()
                : minHostile;
      }
    }
    HashSet<Territory> minHostileTerritories = new HashSet<>();
    for (Territory t : Main.g.getWorld().getTerritories().values()) {
      if (t.getOwner() == null && t.getHostileNeighbor(this).size() == minHostile) {
        minHostileTerritories.add(t);
      }
    }

    double maxPartOfContinent = 0.0;
    for (Territory t : minHostileTerritories) {
      if ((1.0 / (double) Main.g.getWorld().getContinent().get(t.getContinent()).getTerritories()
          .size()) > maxPartOfContinent) {
        maxPartOfContinent = (1.0 / (double) Main.g.getWorld().getContinent().get(t.getContinent())
            .getTerritories().size());
        selection = t;
      }
    }

    this.initialTerritoryDistribution(selection);

    Main.b.updateLabelTerritory(selection);

    Main.b.updateColorTerritory(selection);
    System.out.println(Main.g.getCurrentPlayer().getName() + "--"
        + Main.g.getCurrentPlayer().getColor() + "--" + selection);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Main.g.furtherInitialTerritoryDistribution();
  }

  public void initialArmyDistribution() {

  }

  public void armyDistribution() {
    

  }

  public void attack() {

  }

  public void fortify() {

  }
}
