
package game;

import javafx.scene.paint.Color;

/**
 * Enum defines color for a player during the game.
 * 
 * @author smetzger
 */
public enum PlayerColor {
  YELLOW, BLUE, RED, GREEN, MAGENTA, ORANGE;


  /**
   * Method returns the color regarding to the enum value.
   * 
   * @return Color
   */
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
  
  /**
   * @author prto get color string
   * @return name of current color
   */
  public String getColorString() {
    switch (this) {
      case YELLOW:
        return "Yellow";
      case RED:
        return "Red";
      case GREEN:
        return "Green";
      case MAGENTA:
        return "Magenta";
      case BLUE:
        return "Blue";
      case ORANGE:
        return "Orange";
      default:
        return "";
    }
  }
  
}
