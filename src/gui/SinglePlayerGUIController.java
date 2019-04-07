package gui;

import game.AiPlayerEasy;
import game.AiPlayerHard;
import game.AiPlayerMedium;
import game.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import main.Main;

public class SinglePlayerGUIController {

  @FXML
  private Button startGame;

  @FXML
  private Button back;

  @FXML
  private Slider difficulty;

  @FXML
  private Button addAi;

  @FXML
  void back(ActionEvent event) {
    Main.g.removePlayer();
    
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfileSelectionGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Profile Selection");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
    Main.g.setPlayers(null);
    startGame.setDisable(true);
  }

  @FXML
  void startGame(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BoardGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      // stage.setTitle("Board");
      Main.b = fxmlLoader.getController();
      Main.b.setMain(this, Main.g);
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
    Main.g.initGame();
    System.out.println(Main.g.getGameState());
  }

  /**
   * @author smetzger
   * @author pcoberge
   */
  public void handleAddBot() {
    startGame.setDisable(false);
    if (Main.g.getPlayers().size() < 6) {
      Player p = null;
      switch ((int) difficulty.getValue()) {
        case (1):
          p = new AiPlayerEasy();
          break;
        case (2):
          p = new AiPlayerMedium();
          break;
        case (3):
          p = new AiPlayerHard();
          break;
      }
      Main.g.addPlayer(p);
      System.out.println(p.getName());
      if (Main.g.getPlayers().size() == 6) {
        addAi.setDisable(true);
      }
    }
  }
}
