
package game;

import javafx.scene.paint.Color;

/**
 * @author smetzger Enum defines color for a player during the game
 */
public enum PlayerColor {
  YELLOW, BLUE, RED, GREEN, MAGENTA, ORANGE;


  public String getRgbColor() {
  switch (this) {
    case YELLOW:
      return "#FFFF00";
    // return "rgb(255, 255, 0)";
    case BLUE:
      return "rgb(0, 0, 255)";
    case RED:
      return "#FF0000";
    // return "rgb(255, 0, 0)";
    case GREEN:
      return "rgb(0, 0, 255)";
    case MAGENTA:
      return "rgb(255, 0, 255)";
    case ORANGE:
      return "rgb(255, 165, 0)";
    default:
      return null;
  }
}

  public Color getColor() {
    switch (this) {
      case YELLOW:
        return Color.YELLOW;
      case RED:
        return Color.RED;
      case GREEN:
        return Color.LAWNGREEN;
      case MAGENTA:
        return Color.MAGENTA;
      case BLUE:
        return Color.DODGERBLUE;
      case ORANGE:
        return Color.ORANGE;
      default:
        return Color.BLACK;
    }
}
}
