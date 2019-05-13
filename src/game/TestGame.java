/**
 * 
 */
package game;

import java.io.Serializable;
import main.Main;

/**
 * @author pcoberge
 * @author smetzger
 *
 */
public class TestGame extends Game implements Serializable {


  public void distributeTerritories() {

    for (Territory t : this.getWorld().getTerritories().values()) {
      Main.b.updateColorTerritory(t);
      this.getCurrentPlayer().initialTerritoryDistribution(t);
      super.setNextPlayer();
    }
  }

  public void distributeArmies() {
    for (Player p : this.getPlayers()) {
      while (p.getNumberArmiesToDistibute() > 0) {
        for (Territory t : p.getTerritories()) {
          p.armyDistribution(1, t);
          Main.b.updateLabelTerritory(t);
        }
      }

    }
  }
  public void prepareEndGameWinner() {
    int i=1;
    for (Player p : Main.g.getPlayers()) {
        Main.b.updateColorTerritory(Main.g.getWorld().getTerritories().get(i));
        this.getCurrentPlayer().initialTerritoryDistribution(Main.g.getWorld().getTerritories().get(i));
        i++;
        super.setNextPlayer();
    }

    for (Territory t : this.getWorld().getTerritories().values()) {
      if (t.getOwner() == null) {
        Main.b.updateColorTerritory(t);
        this.getCurrentPlayer().initialTerritoryDistribution(t);
      }
    }
  }

 

  public void prepareEndGameLoser() {
    int i=1;
    for (Player p : Main.g.getPlayers()) {
        Main.b.updateColorTerritory(Main.g.getWorld().getTerritories().get(i));
        this.getCurrentPlayer().initialTerritoryDistribution(Main.g.getWorld().getTerritories().get(i));
        i++;
        super.setNextPlayer();
    }

    Main.g.setCurrentPlayer(Main.g.getPlayers().get(1));
    for (Territory t : this.getWorld().getTerritories().values()) {
      if (t.getOwner() == null) {
        Main.b.updateColorTerritory(t);
        this.getCurrentPlayer().initialTerritoryDistribution(t);
      }
    }
  }


  public void initGame() {
    // Compute number of armies
    super.initGame();
//    initNumberOfArmies();
//    super.initCardDeck();
//    System.out.println(super.getCards().size());
//    System.out.println(super.getCards().getLast());
//    this.setCurrentPlayer(this.getPlayers().get(0));
//    System.out.println(this.getCurrentPlayer().getName());
    setGameState(GameState.INITIALIZING_TERRITORY);
     //Main.b.prepareInitTerritoryDistribution();
    //distributeTerritories();
    prepareEndGameWinner();
    //distributeArmies();
    setGameState(GameState.ARMY_DISTRIBUTION);
//    Card c = super.getCards().getLast();
//    super.getCurrentPlayer().addCard(c);
//    super.getCards().removeLast();
//    Main.cardC.insertCards(c);
//    c = super.getCards().getLast();
//    super.getCurrentPlayer().addCard(c);
//    super.getCards().removeLast();
//    Main.cardC.insertCards(c);
//    c = super.getCards().getLast();
//    super.getCurrentPlayer().addCard(c);
//    super.getCards().removeLast();
//    Main.cardC.insertCards(c);
//    c = super.getCards().getLast();
//    super.getCurrentPlayer().addCard(c);
//    super.getCards().removeLast();
//    Main.cardC.insertCards(c);
//    c = super.getCards().getLast();
//    super.getCurrentPlayer().addCard(c);
//    super.getCards().removeLast();
//    Main.cardC.insertCards(c);
    // Main.b.displayGameState();
    this.setCurrentPlayer(this.getPlayers().get(0));
    Main.b.prepareArmyDistribution();
    this.getCurrentPlayer().computeAdditionalNumberOfArmies();
  }
}
