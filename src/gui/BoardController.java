package gui;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import game.AiPlayer;
import game.Card;
import game.CardDeck;
import game.Dice;
import game.Game;
import game.GameState;
import game.Player;
import game.Territory;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import main.Main;
import network.client.Client;
import network.messages.GameMessageMessage;
import network.messages.SendChatMessageMessage;
import network.messages.game.SelectInitialTerritoryMessage;

/**
 * 
 * Logik hinter Board
 *
 */
public class BoardController implements Initializable {
  private Game g = Main.g;

  /**
   * @author prto testing
   */
  public HashMap<Integer, Card> topList;
  public HashMap<Integer, Card> bottomList;

  private Territory selectedTerritory = null;
  private Territory selectedTerritory_attacked = null;
  private int numberOfDices = 1;
  private static int tradedCards = 0;
  private CardDeck deck = new CardDeck();
  // Views

  /**
   * Elements for the chat
   */
  @FXML
  private Button chatRoomButton;
  @FXML
  private SplitPane splitter;
  // @FXML
  // private ListView chat;
  @FXML
  private TextArea chat;
  @FXML
  private Button send;
  @FXML
  private TextField messages;
  private static StringBuffer sb = new StringBuffer();


  /**
   * Elements to connect Region, Label and Territory
   */
  @FXML
  private Region alaska, northwestTerritory, alberta, westernUnitedStates, centralAmerica,
      greenland, ontario, quebec, easternUnitedStates, venezuela, peru, brazil, argentina, iceland,
      scandinavia, ukraine, greatBritain, northernEurope, westernEurope, southernEurope,
      northAfrica, egypt, congo, eastAfrica, southAfrica, madagascar, siberia, ural, china,
      afghanistan, middleEast, india, siam, yakutsk, irkutsk, mongolia, japan, kamchatka, indonesia,
      newGuinea, westernAustralia, easternAustralia;
  @FXML
  private Label alaskaHeadline, northwestTerritoryHeadline, albertaHeadline,
      westernUnitedStatesHeadline, centralAmericaHeadline, greenlandHeadline, ontarioHeadline,
      quebecHeadline, easternUnitedStatesHeadline, venezuelaHeadline, peruHeadline, brazilHeadline,
      argentinaHeadline, icelandHeadline, scandinaviaHeadline, ukraineHeadline,
      greatBritainHeadline, northernEuropeHeadline, westernEuropeHeadline, southernEuropeHeadline,
      northAfricaHeadline, egyptHeadline, congoHeadline, eastAfricaHeadline, southAfricaHeadline,
      madagascarHeadline, siberiaHeadline, uralHeadline, chinaHeadline, afghanistanHeadline,
      middleEastHeadline, indiaHeadline, siamHeadline, yakutskHeadline, irkutskHeadline,
      mongoliaHeadline, japanHeadline, kamchatkaHeadline, indonesiaHeadline, newGuineaHeadline,
      westernAustraliaHeadline, easternAustraliaHeadline;
  @FXML
  private Label alaskaNoA, northwestTerritoryNoA, albertaNoA, westernUnitedStatesNoA,
      centralAmericaNoA, greenlandNoA, ontarioNoA, quebecNoA, easternUnitedStatesNoA, venezuelaNoA,
      peruNoA, brazilNoA, argentinaNoA, icelandNoA, scandinaviaNoA, ukraineNoA, greatBritainNoA,
      northernEuropeNoA, westernEuropeNoA, southernEuropeNoA, northAfricaNoA, egyptNoA, congoNoA,
      eastAfricaNoA, southAfricaNoA, madagascarNoA, siberiaNoA, uralNoA, chinaNoA, afghanistanNoA,
      middleEastNoA, indiaNoA, siamNoA, yakutskNoA, irkutskNoA, mongoliaNoA, japanNoA, kamchatkaNoA,
      indonesiaNoA, newGuineaNoA, westernAustraliaNoA, easternAustraliaNoA;
  @FXML
  private Label armiesToDistribute;
  @FXML
  private Circle circle;

  /**
   * Elements that handle leave option
   */
  @FXML
  private Shape circleLeave;
  @FXML
  private ImageView imageLeave;
  @FXML
  private Pane quitPane, grayPane;
  @FXML
  private Button yesLeave, noLeave;

  /**
   * Elements that handle dicePane
   */
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


  /**
   * Elements that handle setArmyPane
   */
  @FXML
  private Pane setArmyPane;
  @FXML
  private Slider setArmySlider;
  @FXML
  private Button setArmyButton;

  /**
   * Elements that handle fortifyPane
   */
  @FXML
  private Pane fortifyPane;
  @FXML
  private Slider fortifySlider;
  @FXML
  private Button fortifyButton;

  /**
   * Elements that show the current game state and illustrate who is the current player
   */
  @FXML
  private static ProgressBar progress;

  /**
   * Elements to handle the card split-pane
   */
  @FXML
  private GridPane gridCardPane;
  @FXML
  private SplitPane cardPane;
  @FXML
  private AnchorPane upAndDown, upperPane, bottomPane;
  @FXML
  private FlowPane ownCards;
  @FXML
  private Circle cardButton;

  /**
   * Elements to handle trade-in button
   */
  @FXML
  private Button tradeIn;
  @FXML
  private Label tradedCardSets;
  @FXML
  private Pane cantBeTraded;
  @FXML
  private Button exitPopup;
  @FXML
  private Label label;

  /**
   * Element which handles the function to skip a game state
   * 
   */
  @FXML
  private Pane skip;
  @FXML
  private Button changeGameState;
  @FXML
  private Label gameState;

  /**
   * Elements to handle the card selection the player wants to trade in
   */
  @FXML
  private GridPane paneXY;
  @FXML
  private HBox left, center, right;

  /**
   * Elements for statistic pane
   */
  @FXML
  private TitledPane statisticPane;
  @FXML
  private ListView statistic;


  // public BoardGUI_Main boardGui;
  public SinglePlayerGUIController boardGui;


  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    diceSlider.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        handleNumberOfDices();

        System.out.println("Slider Value Changed (newValue: " + newValue.intValue() + ")\n");
      }

    });
  }


  /**
   * @author prto initialize card lists
   */
  public void initializeCardLists() {
    topList = new HashMap<Integer, Card>();
    bottomList = new HashMap<Integer, Card>();
  }

  // moves card from bottomList to topList
  public void selectCard(Card card) {
    if (bottomList.containsKey(card.getId())) {
      if (topList.size() <= 3) {
        Card temp = bottomList.get(card.getId());
        bottomList.remove(card.getId());
        topList.put(temp.getId(), temp);
      } else {
        System.out.println("Error: TopList is > 3");
      }
    } else {
      System.out.println("Error: Card is not in bottomList");
    }
  }

  // moves card from topList to bottomList
  public void deselectCard(Card card) {
    if (topList.containsKey(card.getId())) {
      if (bottomList.size() <= 5) {
        Card temp = topList.get(card.getId());
        topList.remove(card.getId());
        bottomList.put(temp.getId(), temp);
      } else {
        System.out.println("Error: bottomList is > 5");
      }
    } else {
      System.out.println("Error: Card is not in topList");
    }
  }


  // public void setMain(BoardGUI_Main boardGUI, Game g) {
  public void setMain(SinglePlayerGUIController boardGui, Game g) {
    this.boardGui = boardGui;
    this.g = g;
    connectRegionTerritory();
    // sb.append("Game started");
    chat.appendText("Game started!\n");
  }


  /**
   * @author smetzger
   * @author pcoberge
   */
  public synchronized void prepareInitTerritoryDistribution() {
    Platform.runLater(new Runnable() {
      public void run() {
        chat.appendText("It's " + Main.g.getCurrentPlayer().getName() + "'s turn.\n");
        armiesToDistribute.setText(Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + "");
        circle.setFill(Main.g.getCurrentPlayer().getColor().getColor());
        if (Main.g.getCurrentPlayer() instanceof AiPlayer) {
          for (Territory t : Main.g.getWorld().getTerritories().values()) {
            t.getBoardRegion().getRegion().setDisable(true);
          }
        } else {
          for (Territory t : Main.g.getWorld().getTerritories().values()) {
//            System.out.println(t);
            if (t.getOwner() == null) {
              t.getBoardRegion().getRegion().setDisable(false);
            }
          }
        }
      }
    });

  }



  /**
   * @author smetzger
   * @author pcoberge
   * 
   *         Method to prepare the BoardGUI for phase ARMY_DISTRIBUTION
   */
  public synchronized void prepareArmyDistribution() {
    Platform.runLater(new Runnable() {
      public void run() {
        chat.appendText("It's " + Main.g.getCurrentPlayer().getName() + "'s turn.\n");
        armiesToDistribute.setText(Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + "");
        circle.setFill(Main.g.getCurrentPlayer().getColor().getColor());
        if (Main.g.getCurrentPlayer() instanceof AiPlayer) {
          for (Territory t : Main.g.getWorld().getTerritories().values()) {
            t.getBoardRegion().getRegion().setDisable(true);
          }
        } else {
          for (Territory t : Main.g.getWorld().getTerritories().values()) {
            if (t.getOwner().equals(Main.g.getCurrentPlayer())) {
              t.getBoardRegion().getRegion().setEffect(null);
              t.getBoardRegion().getRegion().setDisable(false);
            } else {
              t.getBoardRegion().getRegion().setDisable(true);
              t.getBoardRegion().getRegion().setEffect(new Lighting());
            }
          }
        }
      }
    });
  }

  /**
   * @author pcoberge
   * @author smetzger
   */
  public synchronized void displayGameState() {
    Platform.runLater(new Runnable() {
      public void run() {
        switch (Main.g.getGameState()) {
          case INITIALIZING_TERRITORY:
            // chat.appendText("Initial Territory Distribution phase: \n");
            gameState.setText("Choose your Territory!");
            // progress = new ProgressBar(0.05);
            break;
          case INITIALIZING_ARMY:
            // chat.appendText("Initial Army Distribution phase: \n");
            gameState.setText("Place your Armies!1");
            // progress.setProgress(0.1);
            break;
          case ARMY_DISTRIBUTION:
            // chat.appendText("Army Distribution phase: \n");
            gameState.setText("Place your Armies!2");
            // progress.setProgress(0.3);
            break;
          case ATTACK:
            // chat.appendText("Attack phase: \n");
            gameState.setText("Attack!");
            // progress.setProgress(0.6);
            break;
          case FORTIFY:
            // chat.appendText("Fortify phase: \n");
            gameState.setText("Move your Armies!");
            // progress.setProgress(0.95);
            break;
        }
      }
    });

  }


  public synchronized void updateLabelTerritory(Territory t) {
    Platform.runLater(new Runnable() {
      public void run() {
        t.getBoardRegion().getRegion().setEffect(new Glow(0.5));
        t.getBoardRegion().getNumberOfArmy().setText(t.getNumberOfArmies() + "");
        t.getBoardRegion().getRegion().setEffect(new Lighting());
      }
    });
  }

  public synchronized void updateColorTerritory(Territory t) {
    Platform.runLater(new Runnable() {
      public void run() {
        t.getBoardRegion().getRegion()
            .setBackground(new Background(new BackgroundFill(t.getOwner().getColor().getColor(),
                CornerRadii.EMPTY, Insets.EMPTY)));
      }
    });
  }

  public void updateDiceSlider(Territory t) {
    this.diceSlider.setMax(t.getNumberOfArmies() - 1);
  }

  /**
   * @author smetzger
   * @author pcoberge
   * 
   *         Method to prepare the BoardGUI for phase ATTACK
   */
  public synchronized void prepareAttack() {
    chat.appendText("It's " + Main.g.getCurrentPlayer().getName() + "'s turn.\n");
    for (Territory t : Main.g.getWorld().getTerritories().values()) {
      if (t.getOwner().equals(Main.g.getCurrentPlayer()) && t.getNumberOfArmies() > 1) {
        t.getBoardRegion().getRegion().setDisable(false);
        t.getBoardRegion().getRegion().setEffect(null);
      } else {
        t.getBoardRegion().getRegion().setDisable(true);
        t.getBoardRegion().getRegion().setEffect(new Lighting());
      }
    }
  }

  /**
   * @author smetzger
   * @author pcoberge
   * 
   *         Method to prepare the BoradGUI for phase FORTIFY
   */
  public synchronized void prepareFortify() {
    chat.appendText("It's " + Main.g.getCurrentPlayer().getName() + "'s turn.\n");
    for (Territory t : Main.g.getWorld().getTerritories().values()) {
      if (t.getOwner().equals(Main.g.getCurrentPlayer()) && t.getNumberOfArmies() > 1
          && t.getHostileNeighbor().size() != t.getNeighbor().size()) {
        t.getBoardRegion().getRegion().setEffect(null);
        t.getBoardRegion().getRegion().setDisable(false);
      } else {
        t.getBoardRegion().getRegion().setDisable(true);
        t.getBoardRegion().getRegion().setEffect(new Lighting());
      }
    }
  }

  /**
   * @author pcoberge This method handles the exit Button. When the user presses the exit-Button, a
   *         pop-up window appears and stops the game.
   */
  public synchronized void pressLeave() {
    Platform.runLater(new Runnable() {
      public void run() {
        grayPane.setVisible(true);
        quitPane.setVisible(true);
      }
    });
  }

  /**
   * @author pcoberge This method cancels the exit-handling.
   */
  public synchronized void handleNoLeave() {
    Platform.runLater(new Runnable() {
      public void run() {
        grayPane.setVisible(false);
        quitPane.setVisible(false);
      }
    });
  }

  /**
   * @author pcoberge
   * @param event : ActionEvent This parameter represents the element that invokes this method. This
   *        action method changes the current stage to the statistic stage.
   */
  public synchronized void handleLeave(ActionEvent event) {
    Platform.runLater(new Runnable() {
      public void run() {
        try {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StatisticGUI.fxml"));
          Parent root = (Parent) fxmlLoader.load();
          Stage stage = main.Main.stage;
          // stage.setTitle("Board");
          stage.setScene(new Scene(root));
          stage.show();
          // ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * @author smetzger
   * @author pcoberge
   * @param MouseEvent The parameter contains the information which gui element triggers the
   *        actionlistener.
   * 
   *        The method is divided into two parts
   * 
   *        1. Part: the previous selected Territory is not the same like the current selection.
   * 
   *        INITIALIZING_TERRITORY -
   * 
   *        INITIALIZING_ARMY - if an army could be placed on the chosen territory the label of this
   *        territory is updated
   * 
   *        ARMY_DISTRIBUTION - if a territory is selected, the player has to choose how many armies
   *        he want to place at this territory. Therefore the slider in the background is set on the
   *        amount of armies the player is allowed to set. A pane with a slider to choose appears.
   *        The label of this territory will be updated. ATTACK - if the selected territory is the
   *        first one, this territory is highlighted and all neighbors that do not belong to the
   *        same player will be highlighted as well. All other territories are disabled. if the
   *        selected territory is the second one, a pane appears in order to throw the dices.
   * 
   *        FORTIFY - if the selected territory is the first one, this territory is highlighted and
   *        all neighbors that belong to the same player will be highlighted as well. All other
   *        territories are disabled. if the selected territory is the second one, a pane appears in
   *        order to choose how many armies should be transferred.
   * 
   */
  public synchronized void clicked(MouseEvent e) {
    Thread th = new Thread() {
      public void run() {
        Region r = (Region) e.getSource();
        Territory t = Main.g.getWorld().getTerritoriesRegion().get(r);

        Platform.runLater(new Runnable() {
          public void run() {
            r.setEffect(null);
          }
        });


        if (!t.equals(selectedTerritory)) {
          switch (Main.g.getGameState()) {
            // new game
            case INITIALIZING_TERRITORY:
              if (Main.g.getCurrentPlayer().initialTerritoryDistribution(t)) {
                // Farbe aendern!!!
                Platform.runLater(new Runnable() {
                  public void run() {
                    t.getBoardRegion().getNumberOfArmy().setText(t.getNumberOfArmies() + "");
                    armiesToDistribute
                        .setText(Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + "");
                    circle.setFill(Main.g.getCurrentPlayer().getColor().getColor());
                    t.getBoardRegion().getRegion()
                        .setBackground(new Background(
                            new BackgroundFill(Main.g.getCurrentPlayer().getColor().getColor(),
                                CornerRadii.EMPTY, Insets.EMPTY)));
                  }
                });
                try {
                  Thread.sleep(1000);
                } catch (InterruptedException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
                }

                r.setEffect(new Lighting());
                Main.g.furtherInitialTerritoryDistribution();
              }
              break;
            // place armies
            case INITIALIZING_ARMY:
              if (Main.g.getCurrentPlayer().armyDistribution(1, t)) {
                Platform.runLater(new Runnable() {
                  public void run() {
                    armiesToDistribute
                        .setText(Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + "");
                    circle.setFill(Main.g.getCurrentPlayer().getColor().getColor());
                    t.getBoardRegion().getNumberOfArmy().setText(t.getNumberOfArmies() + "");
                  }
                });
                try {
                  Thread.sleep(1500);
                } catch (InterruptedException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
                }
                r.setEffect(new Lighting());
                Main.g.furtherInitialArmyDistribution();
              }
              break;

            case ARMY_DISTRIBUTION:

              System.out.println(
                  "Armies to distribute: " + g.getCurrentPlayer().getNumberArmiesToDistibute());
              Platform.runLater(new Runnable() {
                public void run() {
                  setArmySlider.setMax(g.getCurrentPlayer().getNumberArmiesToDistibute());
                  setArmySlider.setValue(1);
                  selectedTerritory = t;
                  grayPane.setVisible(true);
                  // handleGrayPane();
                  setArmyPane.setVisible(true);
                }
              });

              break;

            case ATTACK:
              Platform.runLater(new Runnable() {
                public void run() {
                  System.out.println(t);
                  // Erster Klick
                  if (selectedTerritory == null) {
                    System.out.println("Territory ist null");
                    selectedTerritory = t;
                    r.setEffect(new Lighting());
                    for (Territory territory : t.getHostileNeighbor()) {
                      territory.getBoardRegion().getRegion().setEffect(null);
                      territory.getBoardRegion().getRegion().setDisable(false);
                    }

                    for (Territory territory : g.getCurrentPlayer().getTerritories()) {
                      if (!(territory.equals(t))) {
                        territory.getBoardRegion().getRegion().setDisable(true);
                        territory.getBoardRegion().getRegion().setEffect(new Lighting());
                      }
                    }

                  } else if (selectedTerritory != null
                      && selectedTerritory.getNeighbor().contains(t)) {
                    System.out.println("Territory ist ungleich null");
                    selectedTerritory_attacked = t;
                    diceSlider.setMax(selectedTerritory.getNumberOfArmies() - 1);
                    diceSlider.setMin(1.0);
                    diceSlider.setValue(1.0);

                    nameAttacker.setText(Main.g.getCurrentPlayer().getName());
                    nameDefender.setText(selectedTerritory_attacked.getOwner().getName());
                    armiesAttacker.setText(String.valueOf(selectedTerritory.getNumberOfArmies()));
                    armiesDefender
                        .setText(String.valueOf(selectedTerritory_attacked.getNumberOfArmies()));
                    int numberOfDicesOpponent =
                        selectedTerritory_attacked.getNumberOfArmies() >= 2 ? 2 : 1;
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
                    // open pop-up with Dices
                    grayPane.setVisible(true);
                    dicePane.setVisible(true);
                  }
                }
              });

              break;

            case FORTIFY:
              Platform.runLater(new Runnable() {
                public void run() {
                  if (selectedTerritory == null) {
                    selectedTerritory = t;
                    // r.setEffect(new Lighting());
                    for (Territory territory : t.getNeighbor()) {
                      if (t.getOwner().equals(territory.getOwner())) {
                        System.out.println("Test1");
                        territory.getBoardRegion().getRegion().setEffect(null);
                        territory.getBoardRegion().getRegion().setDisable(false);
                      }
                    }
                    for (Territory territory : g.getCurrentPlayer().getTerritories()) {
                      if (!territory.equals(selectedTerritory)
                          && (!selectedTerritory.getNeighbor().contains(territory))) {
                        territory.getBoardRegion().getRegion().setDisable(true);
                        territory.getBoardRegion().getRegion().setEffect(new Lighting());
                      }
                    }
                  } else if (selectedTerritory != null) {
                    selectedTerritory_attacked = t;
                    fortifySlider.setMax(selectedTerritory.getNumberOfArmies() - 1);
                    fortifySlider.setMin(1.0);
                    fortifySlider.setValue(1.0);
                    grayPane.setVisible(true);
                    fortifyPane.setVisible(true);
                    // if(Main.g.getCurrentPlayer().fortify(t, selectedTerritory,
                    // (int)fortifySlider.getValue())){
                    //
                    // updateTerritoryFortify(t, selectedTerritory);
                    //
                    // }
                  }
                }
              });
              break;
          }
        } else if (t.equals(selectedTerritory)) {
          r.setEffect(new Lighting());
          for (Territory territory : t.getNeighbor()) {
            territory.getBoardRegion().getRegion().setEffect(new Lighting());
          }
          selectedTerritory = null;
        }
      }
    };
    th.start();
  }

  public synchronized void updateTerritoryFortify(Territory moveFrom, Territory moveTo) {
    moveFrom.getBoardRegion().getNumberOfArmy().setText(moveFrom.getNumberOfArmies() + "");
    moveTo.getBoardRegion().getNumberOfArmy().setText(moveTo.getNumberOfArmies() + "");
    fortifySlider.setValue(moveFrom.getNumberOfArmies() - 1);
  }

  public synchronized void handleGrayPane() {
    grayPane.setVisible(true);
  }

  public synchronized void clickBack() {
    Platform.runLater(new Runnable() {
      public void run() {
        switch (Main.g.getGameState()) {
          case ARMY_DISTRIBUTION:
            setArmyPane.setVisible(false);
            prepareArmyDistribution();
            break;
          case ATTACK:
            System.out.println("selected attacked null CLICKBACK");
            selectedTerritory_attacked = null;
            dicePane.setVisible(false);
            attackDice1.setVisible(false);
            attackDice2.setVisible(false);
            attackDice3.setVisible(false);
            defendDice1.setVisible(false);
            defendDice2.setVisible(false);
            prepareAttack();
            break;
          case FORTIFY:
            fortifyPane.setVisible(false);
            prepareFortify();
            break;

        }

        selectedTerritory = null;
        quitPane.setVisible(false);
        grayPane.setVisible(false);
      }
    });
  }

  public synchronized void confirmArmyDistribution() {
    Platform.runLater(new Runnable() {
      public void run() {
        int amount = (int) setArmySlider.getValue();
        if (g.getCurrentPlayer().armyDistribution(amount, selectedTerritory)) {
          selectedTerritory.getBoardRegion().getNumberOfArmy()
              .setText(selectedTerritory.getNumberOfArmies() + "");
          armiesToDistribute.setText(Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + "");
        }
        setArmyPane.setVisible(false);
        grayPane.setVisible(false);
        selectedTerritory = null;
        // fortifyPane.toBack();
        // selectedTerritory.getBoardRegion().getRegion().setEffect(null);
        if (Main.g.getCurrentPlayer().getNumberArmiesToDistibute() == 0) {
          Main.g.setGameState(GameState.ATTACK);
          prepareAttack();
          displayGameState();
        } else {
          prepareArmyDistribution();
        }
      }
    });
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
    Platform.runLater(new Runnable() {
      public void run() {
        nameAttacker.setText(Main.g.getCurrentPlayer().getName());
        nameDefender.setText(selectedTerritory_attacked.getOwner().getName());

        // update opponent number of dices
        if (selectedTerritory_attacked.getNumberOfArmies() == 1) {
          defendDice2.setVisible(false);
        }
        // int numberDicesSelf = handleNumberOfDices();
        System.out.println("number of Dices : " + numberOfDices);
        System.out.println("Selected Territory attacked: " + selectedTerritory_attacked);
        int numberDicesOpponent = selectedTerritory_attacked.getNumberOfArmies() > 1 ? 2 : 1;
        // Vector<Integer> attacker = Dice.rollDices(this.numberOfDices);
        Vector<Integer> attacker = Dice.rollDices(numberOfDices);
        Vector<Integer> defender = Dice.rollDices(numberDicesOpponent);
        System.out.println("attacker vector size: " + attacker.size());
        System.out.println("defender vector size: " + defender.size());
        attackDice1.setImage(new Image(getClass()
            .getResource("/ressources/dices/dice_" + attacker.get(0) + "_RED.png").toString(),
            true));
        if (attacker.size() >= 2) {
          attackDice2.setImage(new Image(getClass()
              .getResource("/ressources/dices/dice_" + attacker.get(1) + "_RED.png").toString(),
              true));

        }
        if (attacker.size() > 2) {
          attackDice3.setImage(new Image(getClass()
              .getResource("/ressources/dices/dice_" + attacker.get(2) + "_RED.png").toString(),
              true));

        }

        defendDice1.setImage(new Image(getClass()
            .getResource("/ressources/dices/dice_" + defender.get(0) + "_BLUE.png").toString(),
            true));
        if (defender.size() == 2) {
          defendDice2.setImage(new Image(getClass()
              .getResource("/ressources/dices/dice_" + defender.get(1) + "_BLUE.png").toString(),
              true));
        }


        if (Main.g.getCurrentPlayer().attack(attacker, defender, selectedTerritory, selectedTerritory_attacked,
            (int) diceSlider.getValue()) || (selectedTerritory.getNumberOfArmies() == 1)) {
          armiesAttacker.setText(String.valueOf(selectedTerritory.getNumberOfArmies()));
          armiesDefender.setText(String.valueOf(selectedTerritory_attacked.getNumberOfArmies()));

          // back to map

          dicePane.setVisible(false);
          attackDice1.setVisible(true);
          attackDice2.setVisible(false);
          attackDice3.setVisible(false);
          defendDice1.setVisible(true);
          defendDice2.setVisible(true);
          grayPane.setVisible(false);

          updateLabelTerritory(selectedTerritory);
          updateLabelTerritory(selectedTerritory_attacked);

          selectedTerritory_attacked = null;
          selectedTerritory = null;
          prepareAttack();

        } else {
          // Label updaten
          armiesAttacker.setText(String.valueOf(selectedTerritory.getNumberOfArmies()));
          armiesDefender.setText(String.valueOf(selectedTerritory_attacked.getNumberOfArmies()));
          selectedTerritory.getBoardRegion().getNumberOfArmy()
              .setText(selectedTerritory.getNumberOfArmies() + "");
          selectedTerritory_attacked.getBoardRegion().getNumberOfArmy()
              .setText(selectedTerritory_attacked.getNumberOfArmies() + "");
          diceSlider.setValue(selectedTerritory.getNumberOfArmies() - 1);
        }


        // selectedTerritory_attacked = null;
        // selectedTerritory = null;
      }
    });
  }


  public synchronized void confirmFortify() {
    Platform.runLater(new Runnable() {
      public void run() {
        int amount = (int) fortifySlider.getValue();
        System.out.println("Test1");
        if (g.getCurrentPlayer().fortify(selectedTerritory, selectedTerritory_attacked, amount)) {
          System.out.println("Test2");
          selectedTerritory.getBoardRegion().getNumberOfArmy()
              .setText(selectedTerritory.getNumberOfArmies() + "");
          selectedTerritory_attacked.getBoardRegion().getNumberOfArmy()
              .setText(selectedTerritory_attacked.getNumberOfArmies() + "");
        }
        // setArmyPane.toBack();
        System.out.println("Test3");
        grayPane.setVisible(false);
        selectedTerritory = null;
        selectedTerritory_attacked = null;
        fortifyPane.setVisible(false);
        // selectedTerritory.getBoardRegion().getRegion().setEffect(null);
        // prepareArmyDistribution();
        // try {
        // Thread.sleep(10000);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        // Main.g.setGameState(GameState.ATTACK);
        // prepareAttack();
        // displayGameState();
        // Main.g.furtherFortify();

      }
    });
  }



  @FXML
  public synchronized void handleCardPane() {
    Platform.runLater(new Runnable() {
      public void run() {
        if (gridCardPane.isVisible()) {
          gridCardPane.setVisible(false);
        } else {
          gridCardPane.setVisible(true);
        }
      }
    });
  }



  /**
   * @author smetzger
   * @param e
   * @return
   */
  @FXML
  public ImageView handleCardDragAndDrop(MouseEvent e) {
    // Platform.runLater(new Runnable() {
    // public void run() {
    ImageView img = (ImageView) e.getSource();
    String url = img.getImage().impl_getUrl();
    String file = url.substring(url.lastIndexOf('/') + 1, url.length());
    String[] split = file.split("\\.");
    int cardId = Integer.parseInt(split[0]);
    Card card = (Card) deck.getCards().get(cardId);
    initializeCardLists();

    if (left.getChildren().isEmpty()) {
      img.setMouseTransparent(true);
      selectCard(card);

      StackPane pane = (StackPane) img.getParent();
      left.getChildren().add(pane);
      pane.getStylesheets().clear();
    } else if (center.getChildren().isEmpty()) {
      img.setMouseTransparent(true);

      selectCard(card);

      StackPane pane = (StackPane) img.getParent();
      center.getChildren().add(pane);
      pane.setStyle(null);
    } else if (right.getChildren().isEmpty()) {
      img.setMouseTransparent(true);

      selectCard(card);

      StackPane pane = (StackPane) img.getParent();
      right.getChildren().add(pane);
      pane.setStyle(null);
    }
    if (!left.getChildren().isEmpty() && !center.getChildren().isEmpty()
        && !right.getChildren().isEmpty()
        && topList.get(0).canBeTraded(topList.get(1), topList.get(2))) {
      tradeIn.setDisable(false);
    }
    //
    // }
    //
    // });
    return img;

  }

  /**
   * @author smetzger
   * @param e
   */
  @FXML
  public void handleRemoveCard(MouseEvent e) {
    String css = this.getClass().getResource("BoardGUI_additional.css").toExternalForm();
    HBox b = (HBox) e.getSource();

    if (b.equals(left)) {
      StackPane pane = (StackPane) b.getChildren().get(0);
      ImageView img = (ImageView) pane.getChildren().get(0);
      String url = img.getImage().impl_getUrl();
      String file = url.substring(url.lastIndexOf('/') + 1, url.length());
      String[] split = file.split("\\.");
      int cardId = Integer.parseInt(split[0]);
      Card card = (Card) deck.getCards().get(cardId);
      this.deselectCard(card);

      ownCards.getChildren().add(pane);
      img.setMouseTransparent(false);
      left.getChildren().clear();
      pane.getStylesheets().add(css);

    } else if (b.equals(center)) {
      StackPane pane = (StackPane) b.getChildren().get(0);
      ImageView img = (ImageView) pane.getChildren().get(0);
      String url = img.getImage().impl_getUrl();
      String file = url.substring(url.lastIndexOf('/') + 1, url.length());
      String[] split = file.split("\\.");
      int cardId = Integer.parseInt(split[0]);
      Card card = (Card) deck.getCards().get(cardId);
      this.deselectCard(card);

      ownCards.getChildren().add(pane);
      img.setMouseTransparent(false);
      center.getChildren().clear();
      pane.getStylesheets().add(css);
    } else if (b.equals(right)) {
      StackPane pane = (StackPane) b.getChildren().get(0);
      ImageView img = (ImageView) pane.getChildren().get(0);
      String url = img.getImage().impl_getUrl();
      String file = url.substring(url.lastIndexOf('/') + 1, url.length());
      String[] split = file.split("\\.");
      int cardId = Integer.parseInt(split[0]);
      Card card = (Card) deck.getCards().get(cardId);
      this.deselectCard(card);

      ownCards.getChildren().add(pane);
      img.setMouseTransparent(false);
      right.getChildren().clear();// getChildren().remove(0);
      pane.getStylesheets().add(css);
    }
  }


  /**
   * @author smetzger
   * @param e
   */
  @FXML
  public void handleTradeCards(ActionEvent e) {
    Thread th = new Thread() {
      public void run() {
        if (e.getSource().equals(tradeIn)) {
          if (topList.get(0).canBeTraded(topList.get(1), topList.get(2))) {
            // add Cards to game carddeck
            Main.g.getCurrentPlayer().tradeCards(topList.get(0), topList.get(1), topList.get(2));
            Main.g.setCard(topList.get(0));
            Main.g.setCard(topList.get(1));
            Main.g.setCard(topList.get(2));


            String cards = Integer.toString(++tradedCards);
            tradedCardSets.setText(cards);
            topList.remove(0);
            topList.remove(1);
            topList.remove(2);

            left.getChildren().remove(0);
            center.getChildren().remove(0);
            right.getChildren().remove(0);
            tradeIn.setDisable(true);
          } else {
            System.out.println("No trade");
            cantBeTraded.toFront();
          }
        }
      }
    };
    th.start();
  }

  /**
   * @author smetzger
   * @param e
   */
  @FXML
  public void exitPopup(ActionEvent e) {
    Button b = (Button) e.getSource();
    if (b.equals(exitPopup)) {
      cantBeTraded.toBack();
    }
  }

  /**
   * @author smetzger
   * @author pcoberge
   */
  @FXML
  public void handleSkipGameState() {
    // handleProgressBar();
    // progress.setStyle("-fx-accent: magenta;");
    Platform.runLater(new Runnable() {
      public void run() {
//        System.out.println("Handle Skip GameState");
        // changeGameState.setOnAction(new EventHandler<ActionEvent>() {
        changeGameState.setEffect(new Bloom());
        // @Override public void handle(ActionEvent e) {
        switch (Main.g.getGameState()) {
          case ARMY_DISTRIBUTION:
            System.out.println("Handle Skip GameState: ARMY DISTRIBUTION");
            gameState.setText("Attack!");
            // progress.setProgress(0.66);
            prepareAttack();
            Main.g.setGameState(GameState.ATTACK);
            break;
          case ATTACK:
            System.out.println("Handle Skip GameState: ATTACK");
            gameState.setText("Move armies!");
            // progress.setProgress(0.9);
            prepareFortify();
            Main.g.setGameState(GameState.FORTIFY);
            break;
          case FORTIFY:
            System.out.println("Handle Skip GameState: ARMY FORTIFY");
            gameState.setText("End your turn!");
            // progress.setProgress(1);
            try {
              Thread.sleep(3500);
            } catch (InterruptedException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
            // Main.g.nextPlayer();
            // prepareArmyDistribution();
            Main.g.furtherFortify();
            // System.out.println(Main.g.getCurrentPlayer());
            // progress.setProgress(0);
            break;
        }
      }
    });

  }
  // });
  // }

  /**
   * @author pcoberge This method creates a connection between the javafx region and label elements
   *         and the equivalent territory.
   */
  public void connectRegionTerritory() {
    Thread th = new Thread() {
      public void run() {
        g.getWorld().getTerritories().get(1)
            .setBoardRegion(new BoardRegion(alaska, alaskaHeadline, alaskaNoA));
        g.getWorld().getTerritories().get(2).setBoardRegion(
            new BoardRegion(northwestTerritory, northwestTerritoryHeadline, northwestTerritoryNoA));
        g.getWorld().getTerritories().get(3)
            .setBoardRegion(new BoardRegion(alberta, albertaHeadline, albertaNoA));
        g.getWorld().getTerritories().get(4).setBoardRegion(new BoardRegion(westernUnitedStates,
            westernUnitedStatesHeadline, westernUnitedStatesNoA));
        g.getWorld().getTerritories().get(5).setBoardRegion(
            new BoardRegion(centralAmerica, centralAmericaHeadline, centralAmericaNoA));
        g.getWorld().getTerritories().get(6)
            .setBoardRegion(new BoardRegion(greenland, greenlandHeadline, greenlandNoA));
        g.getWorld().getTerritories().get(7)
            .setBoardRegion(new BoardRegion(ontario, ontarioHeadline, ontarioNoA));
        g.getWorld().getTerritories().get(8)
            .setBoardRegion(new BoardRegion(quebec, quebecHeadline, quebecNoA));
        g.getWorld().getTerritories().get(9).setBoardRegion(new BoardRegion(easternUnitedStates,
            easternUnitedStatesHeadline, easternUnitedStatesNoA));
        g.getWorld().getTerritories().get(10)
            .setBoardRegion(new BoardRegion(venezuela, venezuelaHeadline, venezuelaNoA));
        g.getWorld().getTerritories().get(11)
            .setBoardRegion(new BoardRegion(peru, peruHeadline, peruNoA));
        g.getWorld().getTerritories().get(12)
            .setBoardRegion(new BoardRegion(brazil, brazilHeadline, brazilNoA));
        g.getWorld().getTerritories().get(13)
            .setBoardRegion(new BoardRegion(argentina, argentinaHeadline, argentinaNoA));
        g.getWorld().getTerritories().get(14)
            .setBoardRegion(new BoardRegion(iceland, icelandHeadline, icelandNoA));
        g.getWorld().getTerritories().get(15)
            .setBoardRegion(new BoardRegion(scandinavia, scandinaviaHeadline, scandinaviaNoA));
        g.getWorld().getTerritories().get(16)
            .setBoardRegion(new BoardRegion(ukraine, ukraineHeadline, ukraineNoA));
        g.getWorld().getTerritories().get(17)
            .setBoardRegion(new BoardRegion(greatBritain, greatBritainHeadline, greatBritainNoA));
        g.getWorld().getTerritories().get(18).setBoardRegion(
            new BoardRegion(northernEurope, northernEuropeHeadline, northernEuropeNoA));
        g.getWorld().getTerritories().get(19).setBoardRegion(
            new BoardRegion(westernEurope, westernEuropeHeadline, westernEuropeNoA));
        g.getWorld().getTerritories().get(20).setBoardRegion(
            new BoardRegion(southernEurope, southernEuropeHeadline, southernEuropeNoA));
        g.getWorld().getTerritories().get(21)
            .setBoardRegion(new BoardRegion(northAfrica, northAfricaHeadline, northAfricaNoA));
        g.getWorld().getTerritories().get(22)
            .setBoardRegion(new BoardRegion(egypt, egyptHeadline, egyptNoA));
        g.getWorld().getTerritories().get(23)
            .setBoardRegion(new BoardRegion(congo, congoHeadline, congoNoA));
        g.getWorld().getTerritories().get(24)
            .setBoardRegion(new BoardRegion(eastAfrica, eastAfricaHeadline, eastAfricaNoA));
        g.getWorld().getTerritories().get(25)
            .setBoardRegion(new BoardRegion(southAfrica, southAfricaHeadline, southAfricaNoA));
        g.getWorld().getTerritories().get(26)
            .setBoardRegion(new BoardRegion(madagascar, madagascarHeadline, madagascarNoA));
        g.getWorld().getTerritories().get(27)
            .setBoardRegion(new BoardRegion(siberia, siberiaHeadline, siberiaNoA));
        g.getWorld().getTerritories().get(28)
            .setBoardRegion(new BoardRegion(ural, uralHeadline, uralNoA));
        g.getWorld().getTerritories().get(29)
            .setBoardRegion(new BoardRegion(china, chinaHeadline, chinaNoA));
        g.getWorld().getTerritories().get(30)
            .setBoardRegion(new BoardRegion(afghanistan, afghanistanHeadline, afghanistanNoA));
        g.getWorld().getTerritories().get(31)
            .setBoardRegion(new BoardRegion(middleEast, middleEastHeadline, middleEastNoA));
        g.getWorld().getTerritories().get(32)
            .setBoardRegion(new BoardRegion(india, indiaHeadline, indiaNoA));
        g.getWorld().getTerritories().get(33)
            .setBoardRegion(new BoardRegion(siam, siamHeadline, siamNoA));
        g.getWorld().getTerritories().get(34)
            .setBoardRegion(new BoardRegion(yakutsk, yakutskHeadline, yakutskNoA));
        g.getWorld().getTerritories().get(35)
            .setBoardRegion(new BoardRegion(irkutsk, irkutskHeadline, irkutskNoA));
        g.getWorld().getTerritories().get(36)
            .setBoardRegion(new BoardRegion(mongolia, mongoliaHeadline, mongoliaNoA));
        g.getWorld().getTerritories().get(37)
            .setBoardRegion(new BoardRegion(japan, japanHeadline, japanNoA));
        g.getWorld().getTerritories().get(38)
            .setBoardRegion(new BoardRegion(kamchatka, kamchatkaHeadline, kamchatkaNoA));
        g.getWorld().getTerritories().get(39)
            .setBoardRegion(new BoardRegion(indonesia, indonesiaHeadline, indonesiaNoA));
        g.getWorld().getTerritories().get(40)
            .setBoardRegion(new BoardRegion(newGuinea, newGuineaHeadline, newGuineaNoA));
        g.getWorld().getTerritories().get(41).setBoardRegion(
            new BoardRegion(westernAustralia, westernAustraliaHeadline, westernAustraliaNoA));
        g.getWorld().getTerritories().get(42).setBoardRegion(
            new BoardRegion(easternAustralia, easternAustraliaHeadline, easternAustraliaNoA));
        g.getWorld().createTerritoriesBoardRegion();
      }
    };
    th.start();
  }


  /**
   * @author prto, @author smetzger
   * @param
   * @return
   */
  @FXML
  public ListView<String> getLiveStatString() {
    g.updateLiveStatistics();
    ObservableList<String> items;
    StringBuffer sb = new StringBuffer();
    sb.append("Player Name\t\t#Territories\t\t#Cards\n");
    for (Player x : g.getPlayers()) {
      sb.append(
          x.getName() + "\t\t" + x.getNumberOfTerritories() + "\t\t" + x.getNumberOfCards() + "\n");
    }

    items = FXCollections.observableArrayList(sb.toString());
    statistic = new ListView(items);
    return statistic;
  }

  @FXML
  void handleSendMessage(ActionEvent event) {
    String message = messages.getText();
    // SendChatMessageMessage chatmessage=new SendChatMessageMessage("test", message);
    // if(Client.isHost) {
    // System.out.println("host side");
    // Client client=NetworkController.gameFinderHost.getClient();
    // client.sendMessage(chatmessage);
    // }else {
    // System.out.println("client side");
    // Client client=NetworkController.gameFinder.getClient();
    // client.sendMessage(chatmessage);
    // }
    GameMessageMessage chatmessage = new GameMessageMessage(ProfileSelectionGUIController.selectedPlayerName, message);
    Client client = NetworkController.gameFinder.getClient();
  //  System.out.println("from board gui"+client.getPlayer().getName());
    client.sendMessage(chatmessage);
    // chat.appendText(message+"\n");
  }

  public void showMessage(String message) {
    chat.appendText(message + "\n");
  }

}
