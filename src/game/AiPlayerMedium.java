package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import main.Main;

public class AiPlayerMedium extends Player implements AiPlayer {
  
  private HashMap<Integer, HashSet<Territory>> ownTerritories;
  private ArrayList<Integer> sortedValues;

  public AiPlayerMedium() {
    super(AiPlayerNames.getRandomName(), PlayerColor.values()[Main.g.getPlayers().size()], Main.g);
    Main.g.addAiNames(this.getName());
  }


  @Override
  public void initialTerritoryDistribution() {
    // TODO Auto-generated method stub
    
  }


  @Override
  public void initialArmyDistribution() {
    // TODO Auto-generated method stub
    
  }
  
  
  public void armyDistribution() {
    System.out.println("Test armyDistribution");
    int max = 0;
    ownTerritories = new HashMap<Integer, HashSet<Territory>>();
    sortedValues = new ArrayList<Integer>();
    
    if (this.getCards().size() >= 3) {
      for (int i = 0; i < this.getCards().size(); i++) {
        for (int j = i + 1; j < this.getCards().size() - 1; j++) {
          for (int k = j + 1; k < this.getCards().size() - 2; k++) {
            this.tradeCards(this.getCards().get(i), this.getCards().get(j), this.getCards().get(k));
          }
        }
      }
    }
    
    for(Territory t: this.getTerritories()) {
      max = 0;
      for(Territory tE: t.getHostileNeighbor()) {
        if( ((t.getNumberOfArmies())-(tE.getNumberOfArmies())) > max) {
          max = t.getNumberOfArmies()-tE.getNumberOfArmies();
          if(ownTerritories.containsKey(max)) {
            ownTerritories.get(max).add(t);
          }
          else {
            HashSet<Territory> tr = new HashSet<Territory>();
            tr.add(t);
            ownTerritories.put(max, tr);
          }
        }
      }
    }
    sortedValues = new ArrayList<Integer>(ownTerritories.keySet());
    Collections.sort(sortedValues, Collections.reverseOrder());
    while(this.getNumberArmiesToDistibute() > 0) {
      for(int i = 0; i < sortedValues.size(); i++) {
        for(Territory t: ownTerritories.get(sortedValues.get(i))) {
          super.armyDistribution(1, t);
          Main.b.updateLabelTerritory(t);
        }
      }
    }
  }

  public void attack() {
    

  }

  public void fortify() {

  }


}

