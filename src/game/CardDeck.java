package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import main.Main;


/**
 * 
 * @author smetzger CardDeck represents the full risiko card set with 42 territory cards and 2
 *         wildcards
 */

public class CardDeck {
  private HashMap<Integer, Card> cards = new HashMap<Integer, Card>();

  public HashMap getCards() {
    return cards;
  }

  /**
   * Constructor fills the attribute cards with 42 territory cards and 2 wildcards
   * 
   * @param w is an instance of a World
   */
  public CardDeck() {
    System.out.println(Main.g.getWorld().getTerritories().size());
    for (Territory t : Main.g.getWorld().getTerritories().values()) {
      this.cards.put(t.getId(), new Card(t, false));
    }
    this.cards.put(43, new Card(true));
    this.cards.put(44, new Card(true));
  }

  public LinkedList shuffle() {
//    ArrayList keys = new ArrayList(cards.keySet());
    LinkedList q = new LinkedList(cards.keySet());
    Collections.shuffle(q);
//    Collections.shuffle(keys);
    return q;
  }

  public Card getRandomCard() {
    int random = (int) (Math.random() * 44 + 1);
    return this.cards.get(random);
  }
}
