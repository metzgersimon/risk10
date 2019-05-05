package game;

/**
 * This enum defines symbols of risk-cards. It is used for risk-cards and as symbols of each
 * country.
 * 
 * @author pcoberge
 */
public enum CardSymbol {

  /**
   * INFANTRY describes a specific kind of card-symbol.
   */
  INFANTRY,

  /**
   * CANNON describes a specific kind of card-symbol.
   */
  CANNON,

  /**
   * CAVALARY describes a specific kind of card-symbol.
   */
  CAVALRY,

  /**
   * WILDCARD describes a specific kind of card, which has no connection to territory and can be
   * combined with each kind of cards.
   */
  WILDCARD;
}
