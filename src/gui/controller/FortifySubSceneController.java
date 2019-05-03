package gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import game.Territory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import main.Main;
import network.messages.game.FortifyMessage;

/**
 * @author pcoberge
 * @author smetzger
 * 
 *         This class organises gui aspects of fortify phase
 */
public class FortifySubSceneController implements Initializable {
  @FXML
  private Pane fortifyPane, grayPane;
  @FXML
  private Slider fortifySlider;
  @FXML
  private Button fortifyButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    fortifySlider.setMax(Main.b.getSelectedTerritory().getNumberOfArmies() - 1);
    fortifySlider.setMin(1.0);
    fortifySlider.setValue(1.0);
  }

  /**
   * This method calls the fortify method
   */
  public synchronized void confirmFortify() {
    Thread th = new Thread() {
      public void run() {
        int amount = (int) fortifySlider.getValue();
        if (Main.g.getCurrentPlayer().fortify(Main.b.getSelectedTerritory(),
            Main.b.getSelectedTerritory_attacked(), amount)) {
          Platform.runLater(new Runnable() {
            public void run() {
              Main.b.getSelectedTerritory().getBoardRegion().getNumberOfArmy()
                  .setText(Main.b.getSelectedTerritory().getNumberOfArmies() + "");
              Main.b.getSelectedTerritory_attacked().getBoardRegion().getNumberOfArmy()
                  .setText(Main.b.getSelectedTerritory_attacked().getNumberOfArmies() + "");
            }
          });
        }
        // network game
        if (Main.g.isNetworkGame()) {
          FortifyMessage message = new FortifyMessage(Main.b.getSelectedTerritory().getId(),
              Main.b.getSelectedTerritory_attacked().getId(), (int) fortifySlider.getValue());
          message.setColor(Main.g.getCurrentPlayer().getColor().toString());
          NetworkController.gameFinder.getClient().sendMessage(message);
        }
        Main.g.getCurrentPlayer().setFortify(true);
        Platform.runLater(new Runnable() {
          public void run() {
            Main.b.neutralizeGUIfortify();
            Main.stagePanes.close();
          }
        });
      }
    };
    th.start();
  }

  /**
   * @param moveFrom
   * @param moveTo
   * 
   *        This method changes labels of territories after fortifying
   */
  public synchronized void updateTerritoryFortify(Territory moveFrom, Territory moveTo) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        // TODO Auto-generated method stub
        moveFrom.getBoardRegion().getNumberOfArmy().setText(moveFrom.getNumberOfArmies() + "");
        moveTo.getBoardRegion().getNumberOfArmy().setText(moveTo.getNumberOfArmies() + "");
        fortifySlider.setValue(moveFrom.getNumberOfArmies() - 1);
      }
    });
  }

  /**
   * This method organises gui after canceling fortify
   */
  public synchronized void clickBack() {
    Platform.runLater(new Runnable() {
      public void run() {
        Main.b.neutralizeGUIfortify();
        Main.stagePanes.close();
      }
    });
  }

}
