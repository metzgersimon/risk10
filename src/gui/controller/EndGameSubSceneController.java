package gui.controller;

import game.AiPlayer;
import game.GameState;
import game.Player;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.Main;
import network.messages.game.LeaveGameMessage;
import network.messages.game.LeaveGameResponseMessage;

/**
 * Class represents the end game subscene.
 * 
 * @author pcoberge
 * @author smetzger
 */
public class EndGameSubSceneController implements Initializable {

  @FXML
  private Button endGameButton;
  @FXML
  private ImageView winnerCrown;
  @FXML
  private Label endGame;

  /**
   * This action method changes the current stage to the statistic stage.
   * 
   * @param arg0 = default parameter
   * @param arg1 = default parameter
   */
  public void initialize(URL arg0, ResourceBundle arg1) {
    if (!(Main.g.getCurrentPlayer() instanceof AiPlayer)) {
      if (Main.g.isNetworkGame()) {
        Player p = NetworkController.gameFinder.getClient().getPlayer();
        if (Main.g.getPlayers().contains(p) && Main.g.getPlayers().size() == 1) {
          System.out.println("EndGame Winner" + p.getName() + "Rank" + p.getRank());
          endGame.setText("You are the winner!");
          winnerCrown.setVisible(true);
          //to end the connection with the server
          Main.g.setGameState(GameState.END_GAME);
          LeaveGameResponseMessage message = new LeaveGameResponseMessage();
          NetworkController.gameFinder.getClient().sendMessage(message);
        } else {
          System.out.println("EndGame Loser" + p.getName() + "Rank" + p.getRank());
          endGame.setText("You lost!");
          winnerCrown.setVisible(false);
          Main.g.setGameState(GameState.END_GAME);
          LeaveGameResponseMessage message = new LeaveGameResponseMessage();
          NetworkController.gameFinder.getClient().sendMessage(message);
        }
      } else {
        endGame.setText("You are the winner!");
        winnerCrown.setVisible(true);
      }

    }
  }

  /**
   * Method switches to the statistic stage.
   * 
   * @param event Event when the leave button is clicked.
   */
  public synchronized void handleLeave(ActionEvent event) {
    Platform.runLater(new Runnable() {
      public void run() {
        try {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/StatisticGUI.fxml"));
          Parent root = (Parent) fxmlLoader.load();
          Main.stage.setScene(new Scene(root));
          Main.stage.show();
          Main.stagePanes.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
}
