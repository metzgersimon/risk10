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
  private static final long serialVersionUID = 1L;

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


}

