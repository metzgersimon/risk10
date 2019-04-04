package game;

import main.Main;

/**
 * @author smetzger
 * @author pcoberge
 *
 */
public class AiPlayerEasy extends Player implements AiPlayer {

  // public AiPlayerEasy(String name, PlayerColor c) {
  // super(name, c);
  // }

  public AiPlayerEasy() {
    super(AiPlayerNames.getFormattedName(AiPlayerNames.URSULA_THE_SNAKE), PlayerColor.RED);
    this.setIsAi(true);
  }

  public void initialTerritoryDistribution() {
    int i = 0;
    int random;
    do {
      random = ((int) (Math.random() * 42) + 1 + i) % 42;
    } while (initialTerritoryDistribution(Main.g.getWorld().getTerritories().get(random)));
  }

  public void initialArmyDistribution() {
    int random = (int) Math.random() * this.getTerritories().size() + 1;
    int i = 1;
    for (Territory t : this.getTerritories()) {
      if (i == random) {
        super.initialTerritoryDistribution(t);
        break;
      } else {
        i++;
      }
    }

  }

  public void armyDistribution() {

  }

  public void attack() {

  }

  public void fortify() {

  }

}
