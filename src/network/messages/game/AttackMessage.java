package network.messages.game;

import game.Territory;
import network.messages.Message;
import network.messages.MessageType;


/**
 * 
 * @author qiychen
 *
 */
public class AttackMessage extends Message {
  int lostArmiesAttacker;
  int lostArmiesDefender;
  int attackerID;
  int defenderID;
  Territory territoryAttacker;
  Territory territoryDefender;
  boolean ifConquered;
  int attackerArmies;
  int defendArmies;
  String color;
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   * 
   * @param lostArmiesAttacker
   * @param lostArmiesDefender
   * @param attackerID
   * @param defenderID
   * @param territoryAttacker
   * @param territoryDefender
   * @param ifConquered
   */
  public AttackMessage(int lostArmiesAttacker, int lostArmiesDefender, int attackerID,
      int defenderID, Territory territoryAttacker, Territory territoryDefender,
      boolean ifConquered) {
    super(MessageType.ATTACK);
    this.lostArmiesAttacker = lostArmiesAttacker;
    this.lostArmiesDefender = lostArmiesDefender;
    this.attackerID = attackerID;
    this.defenderID = defenderID;
    this.territoryAttacker = territoryAttacker;
    this.territoryDefender = territoryDefender;
    this.ifConquered = ifConquered;
  }


  /**
   * Constructor.
   * 
   * @param attackerID
   * @param defenderID
   * @param ifConquered
   * @param attackerArmies
   * @param defendArmies
   */
  public AttackMessage(int attackerID, int defenderID, boolean ifConquered, int attackerArmies,
      int defendArmies) {
    super(MessageType.ATTACK);
    this.attackerID = attackerID;
    this.defenderID = defenderID;
    this.ifConquered = ifConquered;
    this.attackerArmies = attackerArmies;
    this.defendArmies = defendArmies;
  }

  /**
   * get the attack armies
   * 
   * @return armies
   */
  public int getLostArmiesAttacker() {
    return this.lostArmiesAttacker;
  }

  public int getLostArmiesDenfender() {
    return this.lostArmiesDefender;
  }

  public int getAttackerID() {
    return attackerID;
  }

  public int getDefenderID() {
    return defenderID;
  }

  public Territory getTerritoryAttacker() {
    return this.territoryAttacker;
  }

  public Territory getTerritoryDefender() {
    return this.territoryDefender;
  }

  public boolean getIfConquered() {
    return this.ifConquered;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }



  public int getAttackerArmies() {
    return attackerArmies;
  }



  public void setAttackerArmies(int attackerArmies) {
    this.attackerArmies = attackerArmies;
  }



  public int getDefendArmies() {
    return defendArmies;
  }



  public void setDefendArmies(int defendArmies) {
    this.defendArmies = defendArmies;
  }



  public void setIfConquered(boolean ifConquered) {
    this.ifConquered = ifConquered;
  }


}

