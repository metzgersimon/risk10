package gui.controller;

import game.AiPlayer;
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
      endGame.setText("You are the winner!");
      winnerCrown.setVisible(true);
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
          if (Main.g.isNetworkGame()) {
            LeaveGameMessage leaveMessage =
                new LeaveGameMessage(Main.g.getCurrentPlayer().getName());
            leaveMessage.setColor(Main.g.getCurrentPlayer().getColor().toString());
            NetworkController.gameFinder.getClient().sendMessage(leaveMessage);
          }
          
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
