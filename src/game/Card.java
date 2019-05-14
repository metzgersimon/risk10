package game;

import java.io.Serializable;

/**
 * Class represents a risk card which is given to each player after a successful attack.
 * 
 * @author smetzger
 *
 */
public class Card implements Serializable {

  private static final long serialVersionUID = 1L;
  private Territory territory;
  private boolean isWildcard;
  private int id;


  /**
   * Constructor to create a risk card that is covered with a territory.
   * 
   * @param territory represents the territory which is displayed on the card
   * @param isWildcard true or false whether the card is a wildcard or not
   */
  public Card(Territory territory, boolean isWildcard) {
    this.territory = territory;
    this.isWildcard = isWildcard;
    this.id = territory.getId();
  }

 
  /**
   * Constructor to create a risk card that represents a wildcard.
   * 
   * @author smetzger
   * @param id represents the id of the card
   * @param isWildcard true or false whether the card is a wildcard or not
   */
  public Card(int id, boolean isWildcard) {
    this.isWildcard = isWildcard;
    this.id = id;
  }


  
  public Territory getTerritory() {
    return territory;
  }
  
  public void setTerritory(Territory territory) {
    this.territory = territory;
  }

  public boolean getIsWildcard() {
    return isWildcard;
  }
  
  public void setIsWildcard(boolean isWildcard) {
    this.isWildcard = isWildcard;
  }

  public int getId() {
    return this.id;
  }

  
  /**
   * Methods checks if a set of three cards is a valid card set. That happens through checking if
   * those cards pass one of those combinations.
   * 
   * @author qiychen
   * @param c2 represents the second card
   * @param c3 represents the third card
   * @return boolean, whether three cards can be traded or not
   */
  public boolean canBeTraded(Card c2, Card c3) {
    CardSymbol sym1 = this.isWildcard ? CardSymbol.WILDCARD : this.getTerritory().getSym();
    CardSymbol sym2 = c2.isWildcard ? CardSymbol.WILDCARD : c2.getTerritory().getSym();
    CardSymbol sym3 = c3.isWildcard ? CardSymbol.WILDCARD : c3.getTerritory().getSym();
    // same artillery pictures without wildcards
    boolean same1 = (sym1 == sym2) && (sym2 == sym3);
    // all types of cards without wildcards
    boolean different = (sym1 != sym2) && (sym2 != sym3) && (sym1 != sym3);
    // same artillery pictures with one wildcard
    boolean same2 = ((sym1 == sym2) && (sym3 == CardSymbol.WILDCARD))
        || ((sym1 == sym3) && (sym2 == CardSymbol.WILDCARD))
        || ((sym2 == sym3) && (sym1 == CardSymbol.WILDCARD));
    // same artillery pictures with two wildcards, or all types of cards with two wildcards
    boolean same3 = ((sym1 == sym2) && (sym1 == CardSymbol.WILDCARD))
        || ((sym1 == sym3) && (sym1 == CardSymbol.WILDCARD))
        || ((sym2 == sym3) && (sym2 == CardSymbol.WILDCARD));
    // all types of cards with one wildcard
    boolean different2 =
        (sym1 == CardSymbol.WILDCARD && sym2 != sym3 && sym2 != CardSymbol.WILDCARD)
            || (sym2 == CardSymbol.WILDCARD && sym1 != sym3 && sym1 != CardSymbol.WILDCARD)
            || (sym3 == CardSymbol.WILDCARD && sym1 != sym2 && sym1 != CardSymbol.WILDCARD);
    boolean result = same1 || same2 || same3 || different || different2;
    return result;
  }

  /**
   * Method checks if two cards are equal by comparing their ids. 
   * If so, it returns true, otherwise false.
   */
  @Override
  public boolean equals(Object o) {
    Card c = (Card) o;
    return (this.getId() == c.getId());
  }
}
