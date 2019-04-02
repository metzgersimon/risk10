
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
        return "rgb(255, 255, 0)";
      case BLUE:
        return "rgb(0, 0, 255)";
      case RED:
        return "rgb(255, 0, 0)";
      case GREEN:
        return "rgb(0, 255, 0)";
      case MAGENTA:
        return "rgb(255, 0, 255)";
      case ORANGE:
        return "rgb(255, 165, 0)";
      default:
        return null;
    }
  }
  
}
