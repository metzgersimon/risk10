package game;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import main.Main;


/**
 * CardDeck represents the full risiko card set with 42 territory cards and two wildcards
 * 
 * @author smetzger
 */

public class CardDeck {
  private HashMap<Integer, Card> cards = new HashMap<Integer, Card>();

  /**
   * getter for the cards attribute
   */
  public HashMap getCards() {
    return cards;
  }

  /**
   * Constructor fills the HashMap cards with 42 territory cards and two wildcards
   */
  public CardDeck() {
    for (Territory t : Main.g.getWorld().getTerritories().values()) {
      this.cards.put(t.getId(), new Card(t, false));
    }
    this.cards.put(43, new Card(43, true));
    this.cards.put(44, new Card(44, true));
  }

  /**
   * Method shuffles the card deck
   * 
   * @return LinkedList with all risk cards in a shuffled order
   */
  public LinkedList<Card> shuffle() {
    LinkedList<Card> list = new LinkedList<Card>(cards.values());
    Collections.shuffle(list);
    return list;
  }

  /**
   * Method calculates a random number in the range of zero to 44 and selecting a card regarding to
   * this number
   * 
   * @return a card specified by the computed random number
   */
  public Card getRandomCard() {
    int random = (int) (Math.random() * 44 + 1);
    return this.cards.get(random);
  }
}
