package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * 
 * @author smetzger CardDeck represents the full risiko card set with 42 territory cards and 2
 *         wildcards
 */

public class CardDeck {
  private HashMap<Integer, Card> cards;

  public HashMap getCards() {
    return cards;
  }

  /**
   * Constructor fills the attribute cards with 42 territory cards and 2 wildcards
   * 
   * @param w is an instance of a World
   */
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

  public Card getRandomCard() {
    int random = (int) (Math.random() * 44 + 1);
    return this.cards.get(random);
  }



}
