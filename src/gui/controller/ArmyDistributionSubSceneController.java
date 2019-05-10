package gui.controller;

import game.AiPlayer;
import game.Territory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import main.Main;
import network.messages.game.FurtherDistributeArmyMessage;

/**
 * This class organizes gui aspects of army distribution phase.
 * 
 * @author pcoberge
 * @author smetzger
 * 
 */
public class ArmyDistributionSubSceneController implements Initializable {

  @FXML
  private Pane setArmyPane;
  @FXML
  private Pane grayPane;
  @FXML
  private Slider setArmySlider;
  @FXML
  private Button setArmyButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    int max = (Main.g.getCurrentPlayer().getNumberArmiesToDistibute() % 10) == 0
        ? (Main.g.getCurrentPlayer().getNumberArmiesToDistibute() / 10)
        : (Main.g.getCurrentPlayer().getNumberArmiesToDistibute() / 10) + 1;
    setArmySlider.setMajorTickUnit(max);
    setArmySlider.setMinorTickCount(max);
    setArmySlider.setMax(Main.g.getCurrentPlayer().getNumberArmiesToDistibute());
    setArmySlider.setValue(1);
  }

  /**
   * This method is the actionlistener-method to the button to set armies on a chosen territory. At
   * the end it turns back to the primaryStage.
   */
  public synchronized void confirmArmyDistribution() {
    Thread th = new Thread() {
      public void run() {
        Main.b.setTurns();
        Territory t = Main.b.getSelectedTerritory();
        int amount = (int) setArmySlider.getValue();
        if (Main.g.getCurrentPlayer().armyDistribution(amount, Main.b.getSelectedTerritory())) {
          Platform.runLater(new Runnable() {
            public void run() {
              Main.b.setTerritoryText(t);
              Main.b.setCircleArmiesToDistributeLable();
            }
          });
        }
        if (Main.g.isNetworkGame() && !(Main.g.getCurrentPlayer() instanceof AiPlayer)) {
          FurtherDistributeArmyMessage message =
              new FurtherDistributeArmyMessage(amount, Main.b.getSelectedTerritory().getId());
          message.setColor(Main.g.getCurrentPlayer().getColor().toString());
          NetworkController.gameFinder.getClient().sendMessage(message);
        }
      }
    };
    th.start();
    Main.b.neutralizeGUIarmyDistribution();
    Main.stagePanes.close();
  }

  /**
   * This method is used when a player wants to cancel the army distribution on a chosen territory.
   */
  public synchronized void clickBack() {
    Main.stagePanes.close();
  }
}
