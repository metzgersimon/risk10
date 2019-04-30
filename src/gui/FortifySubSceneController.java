package gui;

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

public class FortifySubSceneController implements Initializable {
  @FXML
  private Pane fortifyPane;
  @FXML
  private Slider fortifySlider;
  @FXML
  private Button fortifyButton;
  @FXML
  private Pane grayPane;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO Auto-generated method stub
    fortifySlider.setMax(Main.b.getSelectedTerritory().getNumberOfArmies() - 1);
    fortifySlider.setMin(1.0);
    fortifySlider.setValue(1.0);
  }

  public synchronized void updateTerritoryFortify(Territory moveFrom, Territory moveTo) {
    moveFrom.getBoardRegion().getNumberOfArmy().setText(moveFrom.getNumberOfArmies() + "");
    moveTo.getBoardRegion().getNumberOfArmy().setText(moveTo.getNumberOfArmies() + "");
    fortifySlider.setValue(moveFrom.getNumberOfArmies() - 1);
  }

  public synchronized void clickBack() {
    Platform.runLater(new Runnable() {
      public void run() {
        Main.b.prepareFortify();
        Main.b.setSelectedTerritory(null);
        Main.stage.setScene(Main.boardScene);
        Main.stage.show();
      }
    });
  }

  public synchronized void confirmFortify() {
    Platform.runLater(new Runnable() {
      public void run() {
        int amount = (int) fortifySlider.getValue();
        System.out.println("Test1");
        if (Main.g.getCurrentPlayer().fortify(Main.b.getSelectedTerritory(),
            Main.b.getSelectedTerritory_attacked(), amount)) {
          System.out.println("Test2");
          Main.b.getSelectedTerritory().getBoardRegion().getNumberOfArmy()
              .setText(Main.b.getSelectedTerritory().getNumberOfArmies() + "");
          Main.b.getSelectedTerritory_attacked().getBoardRegion().getNumberOfArmy()
              .setText(Main.b.getSelectedTerritory_attacked().getNumberOfArmies() + "");
        }
        // network game
        if (Main.g.isNetworkGame()) {
          FortifyMessage message = new FortifyMessage(Main.b.getSelectedTerritory().getId(),
              Main.b.getSelectedTerritory_attacked().getId(), (int) fortifySlider.getValue());
          message.setColor(Main.g.getCurrentPlayer().getColor().toString());
          NetworkController.gameFinder.getClient().sendMessage(message);
          // System.out.println("value "+(int)fortifySlider.getValue());
        }
        // setArmyPane.toBack();
        System.out.println("Test3");
        grayPane.setVisible(false);
        Main.b.setSelectedTerritory(null);
        Main.b.setSelectedTerritory_attacked(null);
        fortifyPane.setVisible(false);
        Main.g.getCurrentPlayer().setFortify(true);
      }
    });
  }
}
