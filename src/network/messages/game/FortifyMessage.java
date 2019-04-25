package network.messages.game;

import network.messages.Message;
import network.messages.MessageType;

public class FortifyMessage extends Message {
  private int moveFromTerritoryID;
  private int moveToTerritoryID;
  private static final long serialVersionUID = 1L;

  public FortifyMessage(int moveFromTerritoryID, int moveToTerritoryID) {
    super(MessageType.FORTIFY);
    this.moveFromTerritoryID = moveFromTerritoryID;
    this.moveToTerritoryID = moveToTerritoryID;
  }

  public int getMoveFromTerritoryID() {
    return moveFromTerritoryID;
  }

  public int getMoveToTerritoryID() {
    return moveToTerritoryID;
  }


}
