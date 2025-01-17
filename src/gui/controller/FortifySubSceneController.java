package gui.controller;

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
import network.messages.game.FortifyMessage;

/**
 * This class organizes GUI aspects of fortify phase.
 * 
 * @author pcoberge
 * @author smetzger
 * 
 */
public class FortifySubSceneController implements Initializable {
  @FXML
  private Pane fortifyPane;
  @FXML
  private Pane grayPane;
  @FXML
  private Slider fortifySlider;
  @FXML
  private Button fortifyButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    int max = ((Main.b.getSelectedTerritory().getNumberOfArmies() - 1) % 10) == 0
        ? ((Main.b.getSelectedTerritory().getNumberOfArmies() - 1) / 10)
        : ((Main.b.getSelectedTerritory().getNumberOfArmies() - 1) / 10) + 1;
    fortifySlider.setMajorTickUnit(max);
    fortifySlider.setMinorTickCount(max);
    fortifySlider.setMax(Main.b.getSelectedTerritory().getNumberOfArmies() - 1);
    fortifySlider.setMin(1.0);
    fortifySlider.setValue(1.0);
  }

  /**
   * This method calls the fortify method.
   */
  public synchronized void confirmFortify() {
    System.out.println(Main.b.getSelectedTerritory().getName());
    Thread th = new Thread() {
      public void run() {
        Platform.runLater(new Runnable() {
          public void run() {
            int amount = (int) fortifySlider.getValue();
            if (Main.g.getCurrentPlayer().fortify(Main.b.getSelectedTerritory(),
                Main.b.getSelectedTerritoryAttacked(), amount)) {
              Main.b.getSelectedTerritory().getBoardRegion().getNumberOfArmy()
                  .setText(Main.b.getSelectedTerritory().getNumberOfArmies() + "");
              Main.b.getSelectedTerritoryAttacked().getBoardRegion().getNumberOfArmy()
                  .setText(Main.b.getSelectedTerritoryAttacked().getNumberOfArmies() + "");
              // network game
              if (Main.g.isNetworkGame()) {
                FortifyMessage message = new FortifyMessage(Main.b.getSelectedTerritory().getId(),
                    Main.b.getSelectedTerritoryAttacked().getId(), (int) fortifySlider.getValue());
                message.setColor(Main.g.getCurrentPlayer().getColor().toString());
                NetworkController.gameFinder.getClient().sendMessage(message);
              }
              Main.g.getCurrentPlayer().setFortify(true);
              Main.b.neutralizeGUIfortify();
              Main.stagePanes.close();
            }
          }
        });
      }
    };
    th.start();
  }

  /**
   * This method changes labels of territories after fortifying.
   * 
   * @param moveFrom territory from which the armies should be moved.
   * @param moveTo territory the armies should be moved to.
   * 
   */
  public synchronized void updateTerritoryFortify(Territory moveFrom, Territory moveTo) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        moveFrom.getBoardRegion().getNumberOfArmy().setText(moveFrom.getNumberOfArmies() + "");
        moveTo.getBoardRegion().getNumberOfArmy().setText(moveTo.getNumberOfArmies() + "");
        fortifySlider.setValue(moveFrom.getNumberOfArmies() - 1);
      }
    });
  }

  /**
   * This method organizes GUI after canceling fortify.
   */
  public synchronized void clickBack() {
    Main.b.neutralizeGUIfortify();
    Main.stagePanes.close();
  }
}
