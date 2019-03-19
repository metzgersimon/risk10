package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CardDeck {
  private HashMap<Integer, Card> cards;

  public CardDeck(World w) {
    for (Territory t : w.territories.values()) {
      this.cards.put(t.getId(), new Card(t, false));
    }
    this.cards.put(43, new Card(true));
    this.cards.put(44, new Card(true));
  }

  public void shuffle() {
    List keys = new ArrayList(cards.keySet());
    Collections.shuffle(keys);
    for (Object o : keys) {
      cards.get(o);
    }
  }



}
