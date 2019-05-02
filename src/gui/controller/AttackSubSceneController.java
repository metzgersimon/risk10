package gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import game.AiPlayer;
import game.Dice;
import game.GameState;
import game.Territory;
import game.TutorialMessages;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.Main;
import network.messages.game.AttackMessage;
import network.messages.game.FurtherDistributeArmyMessage;

public class AttackSubSceneController implements Initializable {
  private int numberOfDices = 1;
  Vector<Integer> attacker;
  Vector<Integer> defender;

  @FXML
  private Pane dicePane;
  @FXML
  private ImageView attackDice1, attackDice2, attackDice3, defendDice1, defendDice2;
  @FXML
  private Button throwDices;
  @FXML
  private Slider diceSlider;
  @FXML
  private Label nameAttacker, nameDefender, armiesAttacker, armiesDefender;
  @FXML
  private Pane grayPane;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    diceSlider.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        handleNumberOfDices();

        System.out.println("Slider Value Changed (newValue: " + newValue.intValue() + ")\n");

      }
    });

    diceSlider.setMax(Main.b.getSelectedTerritory().getNumberOfArmies() - 1);
    diceSlider.setMin(1.0);
    diceSlider.setValue(1.0);

    nameAttacker.setText(Main.b.getSelectedTerritory().getName().replaceAll("_", " "));
    nameDefender.setText(Main.b.getSelectedTerritory_attacked().getName().replaceAll("_", " "));
    armiesAttacker.setText(String.valueOf(Main.b.getSelectedTerritory().getNumberOfArmies()));
    armiesDefender
        .setText(String.valueOf(Main.b.getSelectedTerritory_attacked().getNumberOfArmies()));
    int numberOfDicesOpponent =
        Main.b.getSelectedTerritory_attacked().getNumberOfArmies() >= 2 ? 2 : 1;
    switch (numberOfDicesOpponent) {
      case (1):
        defendDice1.setVisible(true);
        defendDice2.setVisible(false);
        break;
      case (2):
        defendDice1.setVisible(true);
        defendDice2.setVisible(true);
        break;

    }



  }

  public synchronized void handleNumberOfDices() {
    // int numberDices = 1;
    Platform.runLater(new Runnable() {

      public void run() {
        System.out.println("Value Dice Slider: " + (int) diceSlider.getValue());
        switch ((int) diceSlider.getValue()) {
          case (1):
            attackDice1.setVisible(true);
            attackDice2.setVisible(false);
            attackDice3.setVisible(false);
            numberOfDices = 1;
            break;
          case (2):
            attackDice1.setVisible(true);
            attackDice2.setVisible(true);
            attackDice3.setVisible(false);
            numberOfDices = 2;
            break;
          case (3):
          default:
            numberOfDices = 3;
            attackDice1.setVisible(true);
            attackDice2.setVisible(true);
            attackDice3.setVisible(true);
            break;
        }
      }
    });
    // return numberDices;
  }

  public synchronized void throwDices() {
    int numberDicesOpponent =
        Main.b.getSelectedTerritory_attacked().getNumberOfArmies() > 1 ? 2 : 1;
    attacker = Dice.rollDices(numberOfDices);
    defender = Dice.rollDices(numberDicesOpponent);
    Platform.runLater(new Runnable() {
      public void run() {
        nameAttacker.setText(Main.b.getSelectedTerritory().getName().replaceAll("_", " "));
        nameDefender.setText(Main.b.getSelectedTerritory_attacked().getName().replaceAll("_", " "));

        // update opponent number of dices
        if (Main.b.getSelectedTerritory_attacked().getNumberOfArmies() == 1) {
          defendDice2.setVisible(false);
        }

        attackDice1.setImage(new Image(getClass()
            .getResource("/resources/dices/dice_" + attacker.get(0) + "_RED.png").toString(),
            true));
        if (attacker.size() >= 2) {
          attackDice2.setImage(new Image(getClass()
              .getResource("/resources/dices/dice_" + attacker.get(1) + "_RED.png").toString(),
              true));
        }
        if (attacker.size() > 2) {
          attackDice3.setImage(new Image(getClass()
              .getResource("/resources/dices/dice_" + attacker.get(2) + "_RED.png").toString(),
              true));
        }
        defendDice1.setImage(new Image(getClass()
            .getResource("/resources/dices/dice_" + defender.get(0) + "_BLUE.png").toString(),
            true));
        if (defender.size() == 2) {
          defendDice2.setImage(new Image(getClass()
              .getResource("/resources/dices/dice_" + defender.get(1) + "_BLUE.png").toString(),
              true));
        }
      }
    });
    boolean attackResult =
        Main.g.getCurrentPlayer().attack(attacker, defender, Main.b.getSelectedTerritory(),
            Main.b.getSelectedTerritory_attacked(), (int) diceSlider.getValue());
    Platform.runLater(new Runnable() {
      public void run() {
        if (attackResult || (Main.b.getSelectedTerritory().getNumberOfArmies() == 1)) {
          armiesAttacker.setText(String.valueOf(Main.b.getSelectedTerritory().getNumberOfArmies()));
          armiesDefender
              .setText(String.valueOf(Main.b.getSelectedTerritory_attacked().getNumberOfArmies()));

          // back to map
          Main.stagePanes.close();

          attackDice1.setVisible(true);
          attackDice2.setVisible(false);
          attackDice3.setVisible(false);
          defendDice1.setVisible(true);
          defendDice2.setVisible(true);



          // network game message
          if (Main.g.isNetworkGame()) {
            AttackMessage message = new AttackMessage(Main.b.getSelectedTerritory().getId(),
                Main.b.getSelectedTerritory_attacked().getId(), false,
                Main.b.getSelectedTerritory().getNumberOfArmies(),
                Main.b.getSelectedTerritory_attacked().getNumberOfArmies());
            message.setColor(Main.g.getCurrentPlayer().getColor().toString());
            if (attackResult) {
              message.setIfConquered(true);
            }
            NetworkController.gameFinder.getClient().sendMessage(message);
            // System.out.println("network message sent true");
          }

          Main.b.updateLabelTerritory(Main.b.getSelectedTerritory());
          Main.b.updateLabelTerritory(Main.b.getSelectedTerritory_attacked());

          Main.b.setSelectedTerritory(null);
          Main.b.setSelectedTerritory(null);
          Main.b.prepareAttack();

        } else {
          // Label updaten
          if (Main.g.isShowTutorialMessages()) {
            int armiesAttackerInt = Integer.parseInt(armiesAttacker.getText());
            if (armiesAttackerInt - defender.size() == Main.b.getSelectedTerritory()
                .getNumberOfArmies()) {
              Main.b.showMessage(TutorialMessages.attackFailed);
            } else {
              Main.b.showMessage(TutorialMessages.attackSuccess);
            }
          }
          if (Main.b.getSelectedTerritory_attacked().getNumberOfArmies() == 1) {
            defendDice2.setVisible(false);
          }
          armiesAttacker.setText(String.valueOf(Main.b.getSelectedTerritory().getNumberOfArmies()));
          armiesDefender
              .setText(String.valueOf(Main.b.getSelectedTerritory_attacked().getNumberOfArmies()));
          Main.b.getSelectedTerritory().getBoardRegion().getNumberOfArmy()
              .setText(Main.b.getSelectedTerritory().getNumberOfArmies() + "");
          Main.b.getSelectedTerritory_attacked().getBoardRegion().getNumberOfArmy()
              .setText(Main.b.getSelectedTerritory_attacked().getNumberOfArmies() + "");
          diceSlider.setValue(Main.b.getSelectedTerritory().getNumberOfArmies() - 1);
          // network game message
          if (Main.g.isNetworkGame()) {
            AttackMessage message = new AttackMessage(Main.b.getSelectedTerritory().getId(),
                Main.b.getSelectedTerritory_attacked().getId(), false,
                Main.b.getSelectedTerritory().getNumberOfArmies(),
                Main.b.getSelectedTerritory_attacked().getNumberOfArmies());
            message.setColor(Main.g.getCurrentPlayer().getColor().toString());
            NetworkController.gameFinder.getClient().sendMessage(message);
            System.out.println("network message sent false");
          }
        }


        // selectedTerritory_attacked = null;
        // selectedTerritory = null;
      }
    });
  }

  public void updateDiceSlider(Territory t) {
    this.diceSlider.setMax(t.getNumberOfArmies() - 1);
  }

  public synchronized void clickBack() {
    Platform.runLater(new Runnable() {
      public void run() {
        Main.b.setSelectedTerritory_attacked(null);
        // Main.b.setSelectedTerritory(null);
        attackDice1.setVisible(false);
        attackDice2.setVisible(false);
        attackDice3.setVisible(false);
        defendDice1.setVisible(false);
        defendDice2.setVisible(false);
        Main.b.prepareAttack();
        // Main.stage.setScene(new Scene(Ma));
        Main.stagePanes.close();

      }
    });
  }
}
