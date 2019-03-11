package game;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CardDeck {
  private HashMap<Integer, Card> cards;
  
  public CardDeck() {
    for(int i = 1; i <= 44; i++) {
      this.cards.put(i, new Card(i,false));
      if(i > 42) {
        this.cards.put(i,new Card(i, true));
      }
    }
  }
  
  public void shuffle() {
    List keys = new ArrayList(cards.keySet());
    Collections.shuffle(keys);
    for(Object o: keys) {
      cards.get(o);
    }
  }
  
  

}
