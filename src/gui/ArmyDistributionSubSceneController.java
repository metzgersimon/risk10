package gui;

import java.net.URL;
import java.util.ResourceBundle;
import game.AiPlayer;
import game.Game;
import game.GameState;
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
    Platform.runLater(new Runnable() {
      public void run() {
        int amount = (int) setArmySlider.getValue();
        if (Main.g.getCurrentPlayer().armyDistribution(amount, board.getSelectedTerritory())) {
          board.getSelectedTerritory().getBoardRegion().getNumberOfArmy()
              .setText(board.getSelectedTerritory().getNumberOfArmies() + "");
          board.getArmiesToDistributeLabel()
              .setText(Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + "");
          if (Main.g.isNetworkGame() && !(Main.g.getCurrentPlayer() instanceof AiPlayer)) {
            FurtherDistributeArmyMessage message =
                new FurtherDistributeArmyMessage(amount, board.getSelectedTerritory().getId());
            message.setColor(Main.g.getCurrentPlayer().getColor().toString());
            NetworkController.gameFinder.getClient().sendMessage(message);
          }
        }
        Main.stage.setScene(Main.boardScene);
        Main.stage.show();
        board.setSelectedTerritory(null);
        // fortifyPane.toBack();
        // selectedTerritory.getBoardRegion().getRegion().setEffect(null);
        if (Main.g.getCurrentPlayer().getNumberArmiesToDistibute() == 0) {
          Main.g.setGameState(GameState.ATTACK);
          Main.b.prepareAttack();
        } else {
          Main.b.prepareArmyDistribution();
        }
      }
    });
  }

  public synchronized void clickBack() {
    Platform.runLater(new Runnable() {
      public void run() {
        // Main.stage.setScene(new Scene(Ma));
        board.prepareArmyDistribution();
        Main.stage.setScene(Main.boardScene);
        Main.stage.show();

      }
    });
  }



}
