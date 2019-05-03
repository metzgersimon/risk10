package gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import game.AiPlayer;
import game.GameState;
import game.Territory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import main.Main;
import network.messages.game.FurtherDistributeArmyMessage;

/**
 * @author pcoberge
 * @author smetzger
 *
 *         This class organises gui aspects of army distribution phase
 */
public class ArmyDistributionSubSceneController implements Initializable {

  @FXML
  private Pane setArmyPane, grayPane;
  @FXML
  private Slider setArmySlider;
  @FXML
  private Button setArmyButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
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
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        if (Main.g.getCurrentPlayer().getNumberArmiesToDistibute() == 0) {
          Main.g.setGameState(GameState.ATTACK);
          Main.b.prepareAttack();
        }
      }
    };
    th.start();
    Main.stagePanes.close();
    Main.b.neutralizeGUIarmyDistribution();
  }

  /**
   * This method is used when a player wants to cancel the army distirubtion on a chosen territory
   */
  public synchronized void clickBack() {
    Main.stagePanes.close();
    Main.b.neutralizeGUIarmyDistribution();
  }
}
