package network.messages.game;

import network.messages.Message;
import network.messages.MessageType;
/**
 * 
 * @author qiychen
 *
 */
public class AttackMessage extends Message {
  int armies;
  int attackerID;
  int defenderID;
  private static final long serialVersionUID = 1L;

  public AttackMessage(int armies, int attackerID, int defenderID) {
    super(MessageType.ATTACK);
    this.armies = armies;
    this.attackerID = attackerID;
    this.defenderID = defenderID;
  }

  /**
   * get the attack armies
   * 
   * @return armies
   */
  public int getArmies() {
    return armies;
  }

  public int getAttackerID() {
    return attackerID;
  }

  public int getDefenderID() {
    return defenderID;
  }



}

