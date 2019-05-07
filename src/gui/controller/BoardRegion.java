package gui.controller;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Class connects regions and territories.
 * 
 * @author pcoberge
 *
 */
public class BoardRegion {
  private Region territory;
  private Label headline;
  private Label numberOfArmy;

  /**
   * Constructor.
   * 
   * @param t = region on gui board
   * @param h = label with territory name on gui board
   * @param n = label with number of armies on this territory on gui board
   */
  public BoardRegion(Region t, Label h, Label n) {
    this.territory = t;
    this.headline = h;
    this.numberOfArmy = n;
  }

  public Region getRegion() {
    return territory;
  }

  public Label getHeadline() {
    return this.headline;
  }

  public Label getNumberOfArmy() {
    return this.numberOfArmy;
  }
}
