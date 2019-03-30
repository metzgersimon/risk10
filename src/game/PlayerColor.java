
package game;

import javafx.scene.paint.Color;

/**
 * @author smetzger Enum defines color for a player during the game
 */
public enum PlayerColor {
  YELLOW, BLUE, RED, GREEN, MAGENTA, ORANGE;



  public Color getColorO() {
    switch (this) {
      case YELLOW:
        return Color.YELLOW;
      case BLUE:
        return Color.BLUE;
      case RED:
        return Color.RED;
      case GREEN:
        return Color.GREEN;
      case MAGENTA:
        return Color.MAGENTA;
      case ORANGE:
        return Color.ORANGE;
      default:
        return null;
    }
  }
}
