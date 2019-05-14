package gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.Main;

public class NextTurnSubSceneController {

  @FXML
  private Pane grayPane;
  @FXML
  private TitledPane turnPane;
  @FXML
  private AnchorPane contentPane;

  /**
   * This method is used when a player wants to cancel the army distribution on a chosen territory.
   */
  public synchronized void clickBack() {
    Main.stagePanes.close();
  }
}
