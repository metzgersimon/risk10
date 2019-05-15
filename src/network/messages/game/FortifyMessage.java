package network.messages.game;

import network.messages.Message;
import network.messages.MessageType;

/**
 * 
 * @author qiychen
 *
 */
public class FortifyMessage extends Message {
  private int moveFromTerritoryID;
  private int moveToTerritoryID;
  private int amount;
  private String color;
  private static final long serialVersionUID = 1L;

  /**
   * Constructor.
   * 
   * @author qiychen
   * @param moveFromTerritoryID
   * @param moveToTerritoryID
   * @param amount
   */
  public FortifyMessage(int moveFromTerritoryID, int moveToTerritoryID, int amount) {
    super(MessageType.FORTIFY);
    this.moveFromTerritoryID = moveFromTerritoryID;
    this.moveToTerritoryID = moveToTerritoryID;
    this.amount = amount;
  }

  public int getMoveFromTerritoryID() {
    return moveFromTerritoryID;
  }

  public int getMoveToTerritoryID() {
    return moveToTerritoryID;
  }

  public int getAmount() {
    return this.amount;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }


}
