package gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import game.AiPlayer;
import game.Game;
import game.GameState;
import game.Territory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import main.Main;
import network.messages.game.FurtherDistributeArmyMessage;

public class ArmyDistributionSubSceneController implements Initializable {
  BoardController board;

  @FXML
  private Pane setArmyPane;
  @FXML
  private Slider setArmySlider;
  @FXML
  private Button setArmyButton;
  @FXML
  private Pane grayPane;

  public void setMain(BoardController board) {
    this.board = board;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO Auto-generated method stub
    setArmySlider.setMax(Main.g.getCurrentPlayer().getNumberArmiesToDistibute());
    setArmySlider.setValue(1);

  }

  public synchronized void confirmArmyDistribution() {
    Territory t = Main.b.getSelectedTerritory();
    int amount = (int) setArmySlider.getValue();
    if (Main.g.getCurrentPlayer().armyDistribution(amount, Main.b.getSelectedTerritory())) {
      Main.b.setTerritoryText(t);
      Main.b.setCircleArmiesToDistributeLable();
      if (Main.g.isNetworkGame() && !(Main.g.getCurrentPlayer() instanceof AiPlayer)) {
        FurtherDistributeArmyMessage message =
            new FurtherDistributeArmyMessage(amount, Main.b.getSelectedTerritory().getId());
        message.setColor(Main.g.getCurrentPlayer().getColor().toString());
        NetworkController.gameFinder.getClient().sendMessage(message);
      }
    }
    if (Main.g.getCurrentPlayer().getNumberArmiesToDistibute() == 0) {
      Main.g.setGameState(GameState.ATTACK);
      Main.b.prepareAttack();
    }
//    else {
//      Main.b.prepareArmyDistribution();
//    }
    Main.stagePanes.close();
    Main.b.setSelectedTerritory(null);

  }

  public synchronized void clickBack() {
    Platform.runLater(new Runnable() {
      public void run() {
        // Main.stage.setScene(new Scene(Ma));
        //Main.b.prepareArmyDistribution();
        Main.stagePanes.close();
        Main.b.setSelectedTerritory(null);
      }
    });
  }



}
