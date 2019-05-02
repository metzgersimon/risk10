package gui.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;
import org.jdom2.Text;
import game.AiPlayer;
import game.Card;
import game.CardDeck;
import game.Dice;
import game.Game;
import game.GameState;
import game.Player;
import game.Territory;
import game.TutorialMessages;
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
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import main.Parameter;
import network.client.Client;
import network.messages.GameMessageMessage;
import network.messages.SendAllianceMessage;
import network.messages.SendChatMessageMessage;
import network.messages.game.AttackMessage;
import network.messages.game.DistributeArmyMessage;
import network.messages.game.FortifyMessage;
import network.messages.game.FurtherDistributeArmyMessage;
import network.messages.game.LeaveGameMessage;
import network.messages.game.SelectInitialTerritoryMessage;

/**
 * 
 * Logik hinter Board
 *
 */
public class BoardController implements Initializable {



  private Territory selectedTerritory = null;
  private Territory selectedTerritory_attacked = null;
  private static int tradedCards = 0;
  // private CardDeck deck = new CardDeck();
  // Views

  @FXML
  private AnchorPane rootAnchor;

  @FXML
  private Pane grayPane;


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
  private TextField playername;
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
  private ImageView winnerCrown;


  /**
   * Elements that handle dicePane
   */



  /**
   * Elements that handle setArmyPane
   */

  /**
   * Elements that handle fortifyPane
   */


  /**
   * Elements that show the current game state and illustrate who is the current player
   */
  @FXML
  private static ProgressBar progress;


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
   * Elements for statistic pane
   */
  @FXML
  private TitledPane statisticPane;
  @FXML
  private TableView<Player> statistic;

  @FXML
  private TableColumn<Player, Integer> c1;

  @FXML
  private TableColumn<Player, Integer> c2;

  @FXML
  private TableColumn<Player, Integer> c3;
  
  @FXML
  private Button liveStats;

  @FXML
  private Circle openRuleBook;


  @FXML
  private Label endGame;

  /**
   * Attributes for network game
   */


  public Territory getSelectedTerritory() {
    return this.selectedTerritory;
  }

  public void setSelectedTerritory(Territory t) {
    this.selectedTerritory = t;
  }

  public Territory getSelectedTerritory_attacked() {
    return this.selectedTerritory_attacked;
  }

  public void setSelectedTerritory_attacked(Territory t) {
    this.selectedTerritory_attacked = t;
  }

  public Label getArmiesToDistributeLabel() {
    return armiesToDistribute;
  }


  // public BoardGUI_Main boardGui;
  public SinglePlayerGUIController boardGui;


  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    c1.setCellValueFactory(new PropertyValueFactory<>("name"));
    c2.setCellValueFactory(new PropertyValueFactory<>("numberOfTerritories"));
    c3.setCellValueFactory(new PropertyValueFactory<>("numberOfCards"));

    c1.setSortType(TableColumn.SortType.ASCENDING);
  }


  /**
   * @author prto opens live statistics panel
   */
  @FXML
  public void openLiveStats() {

    Main.g.updateLiveStatistics();
    ObservableList<Player> playerList = FXCollections.observableArrayList(Main.g.getPlayers());
    statistic.setItems(playerList);
    statistic.getSortOrder().add(c1);
    /*
     * statistic.getColumns().get(0).setVisible(false);
     * statistic.getColumns().get(0).setVisible(true);
     * statistic.getColumns().get(1).setVisible(false);
     * statistic.getColumns().get(1).setVisible(true);
     * statistic.getColumns().get(2).setVisible(false);
     * statistic.getColumns().get(2).setVisible(true);
     */

    statistic.refresh();
    statisticPane.setExpanded(true);

  }

  /**
   * @author prto
   * 
   *         collapses the live statistics pane when mouse pointer exits
   */
  @FXML
  public void exitLiveStat() {
    statisticPane.setExpanded(false);
  }



  // public void setMain(BoardGUI_Main boardGUI, Game g) {
  public void setMain(SinglePlayerGUIController boardGui) {
    this.boardGui = boardGui;
    connectRegionTerritory();
    // sb.append("Game started");
    // chat.appendText("Game started!\n");
    if (Main.g.isShowTutorialMessages()) {
      showMessage(game.TutorialMessages.welcome);
      showMessage(game.TutorialMessages.intro);
    }
  }

  public void startBoard() {
    connectRegionTerritory();
  }

  public void setTerritoryText(Territory t) {
    Platform.runLater(new Runnable() {
      public void run() {
        t.getBoardRegion().getNumberOfArmy().setText(t.getNumberOfArmies() + "");

      }
    });
  }

  public void setCircleArmiesToDistributeLable() {
    Platform.runLater(new Runnable() {
      public void run() {
        armiesToDistribute.setText(Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + "");
      }
    });
  }

  /**
   * @author smetzger
   * @author pcoberge
   */
  public synchronized void prepareInitTerritoryDistribution() {
    Platform.runLater(new Runnable() {
      public void run() {
        if (Main.g.isShowTutorialMessages() && !(Main.g.getCurrentPlayer() instanceof AiPlayer)) {

        }
        showMessage("It's " + Main.g.getCurrentPlayer().getName() + "'s turn.");
        armiesToDistribute.setText(Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + "");
        circle.setFill(Main.g.getCurrentPlayer().getColor().getColor());
        if (Main.g.getCurrentPlayer() instanceof AiPlayer) {
          for (Territory t : Main.g.getWorld().getTerritories().values()) {
            t.getBoardRegion().getRegion().setDisable(true);
          }
        } else {
          for (Territory t : Main.g.getWorld().getTerritories().values()) {
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
        if (!gameState.getText().equals("Place your Armies!")) {
          gameState.setText("Place your Armies!");
          showMessage("It's " + Main.g.getCurrentPlayer().getName() + "'s turn.\n");
          showMessage(game.TutorialMessages.distributing);
          showMessage(game.TutorialMessages.distributingTip);
        }
        showMessage("It's " + Main.g.getCurrentPlayer().getName() + "'s turn.");
        circle.setFill(Main.g.getCurrentPlayer().getColor().getColor());
        armiesToDistribute.setText(Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + "");

        if (Main.g.getCurrentPlayer() instanceof AiPlayer) {
          for (Territory t : Main.g.getWorld().getTerritories().values()) {
            t.getBoardRegion().getRegion().setDisable(true);
          }
        } else {
          changeGameState.setDisable(false);
          for (Territory t : Main.g.getWorld().getTerritories().values()) {
            if (t.getOwner().equals(Main.g.getCurrentPlayer())) {
              t.getBoardRegion().getRegion().setEffect(null);
              t.getBoardRegion().getRegion().setDisable(false);
            } else {
              t.getBoardRegion().getRegion().setDisable(true);
              t.getBoardRegion().getRegion().setEffect(new Lighting());
            }
          }
          if (Main.g.getCurrentPlayer().getCards().size() >= 5) {
            try {
              FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/CardSubScene.fxml"));
              Parent root = (Parent) fxmlLoader.load();
              Main.cardC = fxmlLoader.getController();
//              Main.ca.setMain(Main.b);
              // SubScene subScene = new SubScene(root, 1024, 720);

              // subScene.setRoot(root);
              // rootAnchor.getChildren().add(subScene);
              // subScene.setOpacity(1.0);
              // subScene.setMouseTransparent(false);

              // Main.stage.setScene(new Scene(root));
              // Main.stage.show();
              Main.stagePanes.setScene(new Scene(root));
              Main.stagePanes.show();

            } catch (Exception e) {
              e.printStackTrace();
            }
            // gridCardPane.setVisible(true);
            // grayPane.setVisible(true);
            if (Main.g.isShowTutorialMessages()) {
              showMessage(TutorialMessages.forcedTrade);
            }
          }
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


  /**
   * @author smetzger
   * @author pcoberge
   * 
   *         Method to prepare the BoardGUI for phase ATTACK
   */
  public synchronized void prepareAttack() {
    Platform.runLater(new Runnable() {
      public void run() {
        if (!gameState.getText().equals("Attack!")) {
          gameState.setText("Attack!");
          if (Main.g.isShowTutorialMessages()) {
            showMessage(game.TutorialMessages.attacking1);
          }
        }

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
    });
  }

  /**
   * @author smetzger
   * @author pcoberge
   * 
   *         Method to prepare the BoradGUI for phase FORTIFY
   */
  public synchronized void prepareFortify() {
    if (!gameState.getText().equals("Move your Armies!")) {
      gameState.setText("Move your Armies!");
      if (Main.g.isShowTutorialMessages()) {
        showMessage(game.TutorialMessages.fortify);
        showMessage(game.TutorialMessages.fortifyTip);
      }
    }

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
        try {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/QuitGameSubScene.fxml"));
          Parent root = (Parent) fxmlLoader.load();
          Main.quit = fxmlLoader.getController();
          // SubScene sub = new SubScene(root, 1024, 720);
          // rootAnchor.getChildren().add(sub);

          Main.stage.setScene(new Scene(root));
          Main.stage.show();

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
        if (Main.g.isNetworkGame()) {
          if (!NetworkController.gameFinder.getClient().getPlayer()
              .equals(Main.g.getCurrentPlayer())) {
            return;
          }
        }
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
                if (Main.g.isNetworkGame() && !(Main.g.getCurrentPlayer() instanceof AiPlayer)) {
                  SelectInitialTerritoryMessage message =
                      new SelectInitialTerritoryMessage(t.getId());
                  message.setColor(Main.g.getCurrentPlayer().getColor().toString());
                  NetworkController.gameFinder.getClient().sendMessage(message);
                  return;
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
                if (Main.g.isNetworkGame() && !(Main.g.getCurrentPlayer() instanceof AiPlayer)) {
                  DistributeArmyMessage armyMessage = new DistributeArmyMessage(1, t.getId());
                  armyMessage.setColor(Main.g.getCurrentPlayer().getColor().toString());
                  NetworkController.gameFinder.getClient().sendMessage(armyMessage);
                  return;
                }
                try {
                  Thread.sleep(1000);
                } catch (InterruptedException e1) {
                  e1.printStackTrace();
                }
                r.setEffect(new Lighting());
                Main.g.furtherInitialArmyDistribution();
              }
              break;

            case ARMY_DISTRIBUTION:
              Platform.runLater(new Runnable() {
                public void run() {
                  // setArmySlider.setMax(g.getCurrentPlayer().getNumberArmiesToDistibute());
                  // setArmySlider.setValue(1);
                  selectedTerritory = t;
                  // grayPane.setVisible(true);
                  // handleGrayPane();
                  // setArmyPane.setVisible(true);
                  // Platform.runLater(new Runnable() {
                  // public void run() {
                  try {
                    FXMLLoader fxmlLoader =
                        new FXMLLoader(getClass().getResource("/gui/ArmyDistributionSubScene.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Main.army = fxmlLoader.getController();
                    Main.army.setMain(Main.b);
                    // SubScene subScene = new SubScene(root, 1024, 720);

                    // subScene.setRoot(root);
                    // rootAnchor.getChildren().add(subScene);
                    // subScene.setOpacity(1.0);
                    // subScene.setMouseTransparent(false);

                    // Main.stage.setScene(new Scene(root));
                    // Main.stage.show();
                    Main.stagePanes.setX(Main.stage.getX()+1);
                    Main.stagePanes.setY(Main.stage.getY()+23);
                    Main.stagePanes.setScene(new Scene(root));
                    Main.stagePanes.show();

                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                  // }
                  // });

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

                    for (Territory territory : Main.g.getCurrentPlayer().getTerritories()) {
                      if (!(territory.equals(t))) {
                        territory.getBoardRegion().getRegion().setDisable(true);
                        territory.getBoardRegion().getRegion().setEffect(new Lighting());
                      }
                    }
                    if (Main.g.isShowTutorialMessages()
                        && !(Main.g.getCurrentPlayer() instanceof AiPlayer)) {
                      showMessage(game.TutorialMessages.attacking2);
                      showMessage(game.TutorialMessages.attackingTip);
                    }

                  } else if (selectedTerritory != null
                      && selectedTerritory.getNeighbor().contains(t)) {
                    System.out.println("Territory ist ungleich null");
                    selectedTerritory_attacked = t;

                    // open pop-up with Dices
                    // grayPane.setVisible(true);
                    // dicePane.setVisible(true);
                    if (Main.g.isShowTutorialMessages()) {
                      showMessage(game.TutorialMessages.dicing);
                    }
                    // Platform.runLater(new Runnable() {
                    // public void run() {

                    try {
                      FXMLLoader fxmlLoader =
                          new FXMLLoader(getClass().getResource("/gui/AttackSubScene.fxml"));
                      Parent root = (Parent) fxmlLoader.load();
                      Main.attack = fxmlLoader.getController();
                      // SubScene sub = new SubScene(root, 1024, 720);
                      // rootAnchor.getChildren().add(sub);
                      Main.stagePanes.setX(Main.stage.getX()+1);
                      Main.stagePanes.setY(Main.stage.getY()+23);
                      Main.stagePanes.setScene(new Scene(root));
                      Main.stagePanes.show();
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                    // }
                    // });

                  }
                }
              });

              break;

            case FORTIFY:
              Platform.runLater(new Runnable() {
                public void run() {
                  if (!Main.g.getCurrentPlayer().getFortify()) {
                    if (selectedTerritory == null) {
                      selectedTerritory = t;
                      for (Territory territory : t.getNeighbor()) {
                        if (t.getOwner().equals(territory.getOwner())) {
                          System.out.println("Test1");
                          territory.getBoardRegion().getRegion().setEffect(null);
                          territory.getBoardRegion().getRegion().setDisable(false);
                        }
                      }
                      for (Territory territory : Main.g.getCurrentPlayer().getTerritories()) {
                        if (!territory.equals(selectedTerritory)
                            && (!selectedTerritory.getNeighbor().contains(territory))) {
                          territory.getBoardRegion().getRegion().setDisable(true);
                          territory.getBoardRegion().getRegion().setEffect(new Lighting());
                        }
                      }
                    } else if (selectedTerritory != null) {
                      selectedTerritory_attacked = t;

                      // Platform.runLater(new Runnable() {
                      // public void run() {
                      try {
                        FXMLLoader fxmlLoader =
                            new FXMLLoader(getClass().getResource("/gui/FortifySubScene.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Main.fortify = fxmlLoader.getController();
                        Main.stagePanes.setX(Main.stage.getX()+1);
                        Main.stagePanes.setY(Main.stage.getY()+23);
                        Main.stagePanes.setScene(new Scene(root));
                        Main.stagePanes.show();
                      } catch (Exception e) {
                        e.printStackTrace();
                      }
                    }
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
  
  public void handleLiveStats() {
    try {
      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/gui/StatisticsPopUp.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Main.liveStats = fxmlLoader.getController();
      Main.stagePanes.setX(Main.stage.getX()+1);
      Main.stagePanes.setY(Main.stage.getY()+23);
      Main.stagePanes.setScene(new Scene(root));
      Main.stagePanes.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void handleRuleBook() {
    try {
      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/gui/RuleBookPopUp.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Main.liveStats = fxmlLoader.getController();
      Main.stagePanes.setX(Main.stage.getX()+1);
      Main.stagePanes.setY(Main.stage.getY()+23);
      Main.stagePanes.setScene(new Scene(root));
      Main.stagePanes.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public synchronized void handleCardPane() {
    Platform.runLater(new Runnable() {
      public void run() {
        if (Main.stagePanes.isShowing()) {
          try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/CardSubScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Main.cardC = fxmlLoader.getController();
            // SubScene subScene = new SubScene(root, 1024, 720);

            // subScene.setRoot(root);
            // rootAnchor.getChildren().add(subScene);
            // subScene.setOpacity(1.0);
            // subScene.setMouseTransparent(false);

            // Main.stage.setScene(new Scene(root));
            // Main.stage.show();
            if (Main.g.getCurrentPlayer().getCards().size() >= 5) {
              Main.cardC.handleGrayPane(true);
            }
            Main.stagePanes.setX(Main.stage.getX()+1);
            Main.stagePanes.setY(Main.stage.getY()+23);
            Main.stagePanes.setScene(new Scene(root));
            Main.stagePanes.show();

          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          Main.stagePanes.close();
          if (Main.g.isShowTutorialMessages()) {
            showMessage(TutorialMessages.tradeIn);
            showMessage(TutorialMessages.tradeInTip);
          }
        }
      }
    });
  }



  // /**
  // * @author smetzger
  // * @param e
  // */
  // @FXML
  // public void exitPopup(ActionEvent e) {
  // Button b = (Button) e.getSource();
  // if (b.equals(exitPopup)) {
  // cantBeTraded.toBack();
  // }
  // }

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
        // System.out.println("Handle Skip GameState");
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
            changeGameState.setDisable(true);
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
        Main.g.getWorld().getTerritories().get(1)
            .setBoardRegion(new BoardRegion(alaska, alaskaHeadline, alaskaNoA));
        Main.g.getWorld().getTerritories().get(2).setBoardRegion(
            new BoardRegion(northwestTerritory, northwestTerritoryHeadline, northwestTerritoryNoA));
        Main.g.getWorld().getTerritories().get(3)
            .setBoardRegion(new BoardRegion(alberta, albertaHeadline, albertaNoA));
        Main.g.getWorld().getTerritories().get(4).setBoardRegion(new BoardRegion(
            westernUnitedStates, westernUnitedStatesHeadline, westernUnitedStatesNoA));
        Main.g.getWorld().getTerritories().get(5).setBoardRegion(
            new BoardRegion(centralAmerica, centralAmericaHeadline, centralAmericaNoA));
        Main.g.getWorld().getTerritories().get(6)
            .setBoardRegion(new BoardRegion(greenland, greenlandHeadline, greenlandNoA));
        Main.g.getWorld().getTerritories().get(7)
            .setBoardRegion(new BoardRegion(ontario, ontarioHeadline, ontarioNoA));
        Main.g.getWorld().getTerritories().get(8)
            .setBoardRegion(new BoardRegion(quebec, quebecHeadline, quebecNoA));
        Main.g.getWorld().getTerritories().get(9).setBoardRegion(new BoardRegion(
            easternUnitedStates, easternUnitedStatesHeadline, easternUnitedStatesNoA));
        Main.g.getWorld().getTerritories().get(10)
            .setBoardRegion(new BoardRegion(venezuela, venezuelaHeadline, venezuelaNoA));
        Main.g.getWorld().getTerritories().get(11)
            .setBoardRegion(new BoardRegion(peru, peruHeadline, peruNoA));
        Main.g.getWorld().getTerritories().get(12)
            .setBoardRegion(new BoardRegion(brazil, brazilHeadline, brazilNoA));
        Main.g.getWorld().getTerritories().get(13)
            .setBoardRegion(new BoardRegion(argentina, argentinaHeadline, argentinaNoA));
        Main.g.getWorld().getTerritories().get(14)
            .setBoardRegion(new BoardRegion(iceland, icelandHeadline, icelandNoA));
        Main.g.getWorld().getTerritories().get(15)
            .setBoardRegion(new BoardRegion(scandinavia, scandinaviaHeadline, scandinaviaNoA));
        Main.g.getWorld().getTerritories().get(16)
            .setBoardRegion(new BoardRegion(ukraine, ukraineHeadline, ukraineNoA));
        Main.g.getWorld().getTerritories().get(17)
            .setBoardRegion(new BoardRegion(greatBritain, greatBritainHeadline, greatBritainNoA));
        Main.g.getWorld().getTerritories().get(18).setBoardRegion(
            new BoardRegion(northernEurope, northernEuropeHeadline, northernEuropeNoA));
        Main.g.getWorld().getTerritories().get(19).setBoardRegion(
            new BoardRegion(westernEurope, westernEuropeHeadline, westernEuropeNoA));
        Main.g.getWorld().getTerritories().get(20).setBoardRegion(
            new BoardRegion(southernEurope, southernEuropeHeadline, southernEuropeNoA));
        Main.g.getWorld().getTerritories().get(21)
            .setBoardRegion(new BoardRegion(northAfrica, northAfricaHeadline, northAfricaNoA));
        Main.g.getWorld().getTerritories().get(22)
            .setBoardRegion(new BoardRegion(egypt, egyptHeadline, egyptNoA));
        Main.g.getWorld().getTerritories().get(23)
            .setBoardRegion(new BoardRegion(congo, congoHeadline, congoNoA));
        Main.g.getWorld().getTerritories().get(24)
            .setBoardRegion(new BoardRegion(eastAfrica, eastAfricaHeadline, eastAfricaNoA));
        Main.g.getWorld().getTerritories().get(25)
            .setBoardRegion(new BoardRegion(southAfrica, southAfricaHeadline, southAfricaNoA));
        Main.g.getWorld().getTerritories().get(26)
            .setBoardRegion(new BoardRegion(madagascar, madagascarHeadline, madagascarNoA));
        Main.g.getWorld().getTerritories().get(27)
            .setBoardRegion(new BoardRegion(siberia, siberiaHeadline, siberiaNoA));
        Main.g.getWorld().getTerritories().get(28)
            .setBoardRegion(new BoardRegion(ural, uralHeadline, uralNoA));
        Main.g.getWorld().getTerritories().get(29)
            .setBoardRegion(new BoardRegion(china, chinaHeadline, chinaNoA));
        Main.g.getWorld().getTerritories().get(30)
            .setBoardRegion(new BoardRegion(afghanistan, afghanistanHeadline, afghanistanNoA));
        Main.g.getWorld().getTerritories().get(31)
            .setBoardRegion(new BoardRegion(middleEast, middleEastHeadline, middleEastNoA));
        Main.g.getWorld().getTerritories().get(32)
            .setBoardRegion(new BoardRegion(india, indiaHeadline, indiaNoA));
        Main.g.getWorld().getTerritories().get(33)
            .setBoardRegion(new BoardRegion(siam, siamHeadline, siamNoA));
        Main.g.getWorld().getTerritories().get(34)
            .setBoardRegion(new BoardRegion(yakutsk, yakutskHeadline, yakutskNoA));
        Main.g.getWorld().getTerritories().get(35)
            .setBoardRegion(new BoardRegion(irkutsk, irkutskHeadline, irkutskNoA));
        Main.g.getWorld().getTerritories().get(36)
            .setBoardRegion(new BoardRegion(mongolia, mongoliaHeadline, mongoliaNoA));
        Main.g.getWorld().getTerritories().get(37)
            .setBoardRegion(new BoardRegion(japan, japanHeadline, japanNoA));
        Main.g.getWorld().getTerritories().get(38)
            .setBoardRegion(new BoardRegion(kamchatka, kamchatkaHeadline, kamchatkaNoA));
        Main.g.getWorld().getTerritories().get(39)
            .setBoardRegion(new BoardRegion(indonesia, indonesiaHeadline, indonesiaNoA));
        Main.g.getWorld().getTerritories().get(40)
            .setBoardRegion(new BoardRegion(newGuinea, newGuineaHeadline, newGuineaNoA));
        Main.g.getWorld().getTerritories().get(41).setBoardRegion(
            new BoardRegion(westernAustralia, westernAustraliaHeadline, westernAustraliaNoA));
        Main.g.getWorld().getTerritories().get(42).setBoardRegion(
            new BoardRegion(easternAustralia, easternAustraliaHeadline, easternAustraliaNoA));
        Main.g.getWorld().createTerritoriesBoardRegion();
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
    ObservableList<String> items;
    StringBuffer sb = new StringBuffer();
    sb.append("Player Name\t\t#Territories\t\t#Cards\n");
    for (Player x : Main.g.getPlayers()) {
      sb.append(
          x.getName() + "\t\t" + x.getNumberOfTerritories() + "\t\t" + x.getNumberOfCards() + "\n");
    }

    items = FXCollections.observableArrayList(sb.toString());
    // statistic = new ListView(items);
    // return statistic;
    return null;
  }

  /**
   * @author qiychen
   * @param event Messages regarding chat/alliance can be sent if a specific name is given, private
   *        messages will be sent, otherwise the message will be sent to all members
   */
  @FXML
  void handleSendMessage(ActionEvent event) {
    String message = messages.getText();
    String player = playername.getText();
    String author = ProfileSelectionGUIController.selectedPlayerName;
    if(Main.g.isNetworkGame()) {
      Client client = NetworkController.gameFinder.getClient();
      if (player.equals("") || player.isEmpty() || playername == null) {
        GameMessageMessage chatmessage = new GameMessageMessage(author, message);
        client.sendMessage(chatmessage);
      } else {
        SendAllianceMessage privatemessage = new SendAllianceMessage(player, message, author);
        client.sendMessage(privatemessage);
      }
    }


    // chat.appendText(message+"\n");
  }

  public void showMessage(String message) {
    chat.appendText(message + "\n_____________\n");
  }

  public void showAllianceMessage(String message) {
    chat.appendText(message + " (private) " + "\n");
  }


  /**
   * @author skaur
   * 
   *         This method is called in the client class, when a client receives the leave game by the
   *         host player message. After being called, this method shows an alert to every player
   *         connected to the game. The alert informs the players that the game is cancelled and
   *         asks them to leave the game by giving them to options.
   */
  public void gameCancelAlert() {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Game Cancelled");
    alert.setHeaderText("A player has left the game. The game is now cancelled." + "\n"
        + "Please leave the Game Board");
    alert.setContentText("Leave Game");
    Optional<ButtonType> option = alert.showAndWait();
    if (option.get() == null) {
      // do nothing
      alert.close();
    } else if (option.get() == ButtonType.OK) {
      // this method shows the end game staticstics and disconnect the client
      this.clientLeaveGame();
    } else if (option.get() == ButtonType.CANCEL) {
      // do nothing
      alert.close();
    }
  }

  /**
   * @skaur
   * 
   *        After the player choose to leave the game, this shows the game statistics to each player
   */
  public void clientLeaveGame() {
    NetworkController.gameFinder.getClient().disconnect();
    Platform.runLater(new Runnable() {
      public void run() {
        try {
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

  public void endGame() {
    if (!(Main.g.getCurrentPlayer() instanceof AiPlayer)) {
      endGame.setText("You are the winner!");
      winnerCrown.setVisible(true);

    }
    Main.g.addToAllPlayers(Main.g.getPlayers().get(0));
    Platform.runLater(new Runnable() {
      public void run() {
        try {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/EndGameSubScene.fxml"));
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

}
