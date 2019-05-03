package gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import game.Dice;
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

/**
 * @author pcoberge
 * @author smetzger
 * 
 *         This class organises gui aspects of attack phase
 *
 */
public class AttackSubSceneController implements Initializable {
  private int numberOfDices = 1;
  Vector<Integer> attacker;
  Vector<Integer> defender;

  @FXML
  private Pane dicePane, grayPane;
  @FXML
  private ImageView attackDice1, attackDice2, attackDice3, defendDice1, defendDice2;
  @FXML
  private Button throwDices;
  @FXML
  private Slider diceSlider;
  @FXML
  private Label nameAttacker, nameDefender, armiesAttacker, armiesDefender;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    diceSlider.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        handleNumberOfDices();
      }
    });

    Platform.runLater(new Runnable() {
      public void run() {
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
    });
  }

  /**
   * This method shows the correct number of dice images in gui
   */
  public synchronized void handleNumberOfDices() {
    Platform.runLater(new Runnable() {
      public void run() {
        switch ((int) diceSlider.getValue()) {
          case (0):
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
  }

  /**
   * This method organises the images in gui after throwing dice and calls the attack method
   */
  public synchronized void throwDices() {
    int numberDicesOpponent =
        Main.b.getSelectedTerritory_attacked().getNumberOfArmies() > 1 ? 2 : 1;
    attacker = Dice.rollDices(numberOfDices);
    defender = Dice.rollDices(numberDicesOpponent);
    Thread th = new Thread() {
      public void run() {
        Platform.runLater(new Runnable() {
          public void run() {
            nameAttacker.setText(Main.b.getSelectedTerritory().getName().replaceAll("_", " "));
            nameDefender
                .setText(Main.b.getSelectedTerritory_attacked().getName().replaceAll("_", " "));
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
      }
    };
    th.start();

    boolean attackResult =
        Main.g.getCurrentPlayer().attack(attacker, defender, Main.b.getSelectedTerritory(),
            Main.b.getSelectedTerritory_attacked(), (int) diceSlider.getValue());
    if (attackResult || (Main.b.getSelectedTerritory().getNumberOfArmies() == 1)) {
      Thread th1 = new Thread() {
        public void run() {
          Platform.runLater(new Runnable() {
            public void run() {
              armiesAttacker
                  .setText(String.valueOf(Main.b.getSelectedTerritory().getNumberOfArmies()));
              armiesDefender.setText(
                  String.valueOf(Main.b.getSelectedTerritory_attacked().getNumberOfArmies()));
              // back to map
              clickBack();
            }
          });

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
          }
          Platform.runLater(new Runnable() {
            @Override
            public void run() {
              Main.b.updateLabelTerritory(Main.b.getSelectedTerritory());
              Main.b.updateLabelTerritory(Main.b.getSelectedTerritory_attacked());
            }
          });
        }
      };
      th1.start();
    } else {
      Thread th2 = new Thread() {
        public void run() {
          Platform.runLater(new Runnable() {

            @Override
            public void run() {
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
              armiesAttacker
                  .setText(String.valueOf(Main.b.getSelectedTerritory().getNumberOfArmies()));
              armiesDefender.setText(
                  String.valueOf(Main.b.getSelectedTerritory_attacked().getNumberOfArmies()));
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
          });

        }
      };
      th2.start();
    }
  }

  /**
   * @param t = Territory the player has chosen to attack with
   * 
   *        This method updates the dice slider fitting to the number of armies in territory t
   */
  public void updateDiceSlider(Territory t) {
    this.diceSlider.setMax(t.getNumberOfArmies() - 1);
  }

  /**
   * This method cancels the attack and neutralizes the gui
   */
  public synchronized void clickBack() {
    Platform.runLater(new Runnable() {
      public void run() {
        attackDice1.setVisible(false);
        attackDice2.setVisible(false);
        attackDice3.setVisible(false);
        defendDice1.setVisible(false);
        defendDice2.setVisible(false);
        Main.b.neutralizeGUIattack();
        Main.stagePanes.close();
      }
    });
  }
}
