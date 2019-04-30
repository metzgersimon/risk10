package gui;

import game.AiPlayer;
import game.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import network.messages.game.LeaveGameMessage;

public class EndGameSubSceneController {

  @FXML
  private Button endGameButton;

  /**
   * @author pcoberge
   * @param event : ActionEvent This parameter represents the element that invokes this method. This
   *        action method changes the current stage to the statistic stage.
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
          Main.g.setAllPlayers(Main.g.getAllPlayers());
          for (Player p : Main.g.getPlayers()) {
            Main.g.addToAllPlayers(p);
          }
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StatisticGUI.fxml"));
          Parent root = (Parent) fxmlLoader.load();
          Stage stage = main.Main.stage;
          stage.setScene(new Scene(root));
          stage.show();
          // ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }


}
