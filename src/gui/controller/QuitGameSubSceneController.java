package gui.controller;

import game.GameState;
import game.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import network.messages.game.LeaveGameMessage;

/**
 * Class describes a stage if a player quits the game.
 * 
 * @author pcoberge
 * @author smetzger
 *
 */
public class QuitGameSubSceneController {
  @FXML
  private Pane quitPane;
  @FXML
  private Pane grayPane;
  @FXML
  private Button noLeave;
  @FXML
  private Button yesLeave;


  /**
   * This action method changes the current stage to the statistic stage.
   * 
   * @param event : ActionEvent This parameter represents the element that invokes this method.
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
            Main.g.setGameState(GameState.END_GAME);
          }
          Main.g.setAllPlayers(Main.g.getAllPlayers());
          for (Player p : Main.g.getPlayers()) {
            Main.g.addToAllPlayers(p);
          }
          main.Main.stagePanes.close();

          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/StatisticGUI.fxml"));
          Parent root = (Parent) fxmlLoader.load();
          Stage stage = main.Main.stage;
          stage.setScene(new Scene(root));
          stage.show();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * This method cancels the exit-handling.
   */
  public synchronized void clickBack() {
    Platform.runLater(new Runnable() {
      public void run() {
        Main.stagePanes.close();
      }
    });
  }
}
