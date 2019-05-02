package gui.controller;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class BoardRegion {
  private Region territory;
  private Label headline;
  private Label numberOfArmy;
  
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
  
  public void setNumberOfArmy(int amount) {
    int current = Integer.parseInt(this.numberOfArmy.getText());
    int erg = current + amount;
    this.numberOfArmy.setText(erg + "");
  }
  
}
