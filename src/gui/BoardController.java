package gui;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import game.Card;
import game.Game;
import game.GameState;
import game.Player;
import game.Territory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/**
 * 
 * Logik hinter Board
 *
 */
public class BoardController {
  Game g;
  Player p;
  // BoardGUI_Elements elements;

  /**
   * @author prto testing
   */
  public HashMap<Integer, Card> topList;
  public HashMap<Integer, Card> bottomList;


  // TEST PARTS
  @FXML
  private Button button;
  @FXML
  private GridPane popup;
  // TEST PARTS ENDE
  private Game game;
  private Territory selectedTerritory = null;
  private static int tradedCards = 0;
  // Views
  @FXML
  private Button chatRoomButton;
  @FXML
  private SplitPane splitter;

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
  private Pane dicePane, attackDice1, attackDice2, attackDice3, defendDice1, defendDice2;
  @FXML
  private Button throwDices;
  @FXML
  private Slider diceSlider;


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
  private ProgressBar progress;

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

  /**
   * Element which handles the function to skip a game state
   * 
   */
  @FXML
  private Pane skip;
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
  }

  /**
   * @author pcoberge
   * 
   *         Method to prepare the BoardGUI for phase ARMY_DISTRIBUTION
   */
  public void prepareArmyDistribution() {
    for (Territory t : g.getWorld().getTerritories().values()) {
      if (t.getOwner().equals(g.getCurrentPlayer())) {
        t.getBoardRegion().getRegion().setEffect(new Glow(0.3));
        t.getBoardRegion().getRegion().setDisable(false);
      } else {
        t.getBoardRegion().getRegion().setDisable(true);
      }
    }
  }

  /**
   * @author pcoberge
   * 
   *         Method to prepare the BoardGUI for phase ATTACK
   */
  public void prepareAttack() {
    for (Territory t : g.getWorld().getTerritories().values()) {
      if (t.getOwner().equals(g.getCurrentPlayer()) && t.getNumberOfArmies() > 1) {
        t.getBoardRegion().getRegion().setEffect(new Glow(0.3));
        t.getBoardRegion().getRegion().setDisable(false);
      } else {
        t.getBoardRegion().getRegion().setDisable(true);
      }
    }
  }

  /**
   * @author pcoberge
   * 
   *         Method to prepare the BoradGUI for phase FORTIFY
   */
  public void prepareFortify() {
    for (Territory t : g.getWorld().getTerritories().values()) {
      if (t.getOwner().equals(g.getCurrentPlayer()) && t.getNumberOfArmies() > 1) {
        t.getBoardRegion().getRegion().setEffect(new Glow(0.3));
        t.getBoardRegion().getRegion().setDisable(false);
      } else {
        t.getBoardRegion().getRegion().setDisable(true);
      }
    }
  }

  /**
   * @author pcoberge This method handles the exit Button. When the user presses the exit-Button, a
   *         pop-up window appears and stops the game.
   */
  public void pressLeave() {
    Platform.runLater(new Runnable() {
      public void run() {
        grayPane.toFront();
        quitPane.toFront();
      }
    });
  }

  /**
   * @author pcoberge This method cancels the exit-handling.
   */
  public void handleNoLeave() {
    Platform.runLater(new Runnable() {
      public void run() {
        grayPane.toBack();
        quitPane.toBack();
      }
    });
  }

  /**
   * @author pcoberge
   * @param event : ActionEvent This parameter represents the element that invokes this method. This
   *        action method changes the current stage to the statistic stage.
   */
  public void handleLeave(ActionEvent event) {
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
   * This method is a dummy Method, to get to know, how the ProgressBAr can be handled
   */
  public void handleProgressBar() {
    String color = p.getColor().toString().toLowerCase();
    this.progress.setStyle("-fx-accent: " + color + ";");
    this.progress.setProgress(0);
  }

  /**
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
  public void clicked(MouseEvent e) {
    Platform.runLater(new Runnable() {
      public void run() {
        Region r = (Region) e.getSource();
        Territory t = g.getWorld().getTerritoriesRegion().get(r);
        r.setEffect(new Glow(0.5));

        if (!t.equals(selectedTerritory)) {
          // switch (g.getGameState()) {

          // NUR FUER ANZEIGE
          Player p = new Player("TOM", g);
          g.setCurrentPlayer(p);
          p.addTerritories(t);
          t.setNumberOfArmies(10);
          p.setNumberArmiesToDistribute(12);
          t.setOwner(p);
          // ENDE

          switch (GameState.FORTIFY) {
            // new game
            case INITIALIZING_TERRITORY:
              if (g.getCurrentPlayer().initialTerritoryribution(selectedTerritory)) {
                t.getBoardRegion().getNumberOfArmy().setText(t.getNumberOfArmies() + "");
              }
              break;
            // place armies
            case INITIALIZING_ARMY:
              if (g.getCurrentPlayer().armyDistribution(1, t)) {
                t.getBoardRegion().getNumberOfArmy().setText(t.getNumberOfArmies() + "");
              }
              break;

            case ARMY_DISTRIBUTION:
              setArmySlider.setMax(g.getCurrentPlayer().getNumberArmiesToDistibute());
              setArmySlider.setValue(1);
              selectedTerritory = t;
              grayPane.toFront();
              setArmyPane.toFront();
              break;

            case ATTACK:
              if (selectedTerritory == null) {
                selectedTerritory = t;
                r.setEffect(new Lighting());
                for (Territory territory : t.getNeighbor()) {
                  if (!territory.getOwner().equals(t.getOwner())) {
                    territory.getBoardRegion().getRegion().setEffect(new Glow(0.5));
                  }
                }
                for (Territory territory : g.getWorld().getTerritories().values()) {
                  if (territory.getBoardRegion().getRegion().getEffect() == null) {
                    territory.getBoardRegion().getRegion().setDisable(true);
                  }
                }
              } else if (selectedTerritory != null && selectedTerritory.getNeighbor().contains(t)) {
                // ATTACK METHODE
                /*
                 * int armies=(int) setArmySlider.getValue(); g.attack(selectedTerritory, t,
                 * armies);
                 * selectedTerritory.getBoardRegion().getNumberOfArmy().setText(selectedTerritory.
                 * getNumberOfArmies()+"");
                 * t.getBoardRegion().getNumberOfArmy().setText(t.getNumberOfArmies()+"");
                 */

                // open pop-up with Dices
                grayPane.toFront();
                dicePane.toFront();
              }
              break;

            case FORTIFY:
              if (selectedTerritory == null) {
                selectedTerritory = t;
                r.setEffect(new Lighting());
                for (Territory territory : t.getNeighbor()) {
                  if (t.getOwner().equals(territory.getOwner())) {
                    territory.getBoardRegion().getRegion().setEffect(new Glow(0.5));
                  }
                }
                for (Territory territory : g.getWorld().getTerritories().values()) {
                  if (territory.getBoardRegion().getRegion().getEffect() == null) {
                    territory.getBoardRegion().getRegion().setDisable(true);
                  }
                }
              } else if (selectedTerritory != null) {
                fortifySlider.setMax(selectedTerritory.getNumberOfArmies() - 1);
                grayPane.toFront();
                fortifyPane.toFront();
              }
              break;
          }
        } else if (t.equals(selectedTerritory)) {
          r.setEffect(null);
          for (Territory territory : t.getNeighbor()) {
            territory.getBoardRegion().getRegion().setEffect(null);
          }
          selectedTerritory = null;
        }
      }
    });
  }

  public void clickBack() {
    Platform.runLater(new Runnable() {
      public void run() {
        for (Territory t : g.getWorld().getTerritories().values()) {
          t.getBoardRegion().getRegion().setEffect(null);
          t.getBoardRegion().getRegion().setDisable(false);
        }
        selectedTerritory = null;
        dicePane.toBack();
        setArmyPane.toBack();
        fortifyPane.toBack();
        quitPane.toBack();
        grayPane.toBack();
      }
    });
  }

  public void confirmArmyDistribution() {
    Platform.runLater(new Runnable() {
      public void run() {
        int amount = (int) setArmySlider.getValue();
        if (g.getCurrentPlayer().armyDistribution(amount, selectedTerritory)) {
          selectedTerritory.getBoardRegion().getNumberOfArmy()
              .setText(selectedTerritory.getNumberOfArmies() + "");
        }
        setArmyPane.toBack();
        grayPane.toBack();
        selectedTerritory.getBoardRegion().getRegion().setEffect(null);
      }
    });
  }


  /**
   * 
   */
  // @FXML
  // public void handleCardPane(MouseEvent e) {
  // if (e.getSource().equals(upAndDown)) {
  // System.out.println("Card button geklickt");
  // cardPane.setVisible(false);
  // cardPane.setPrefHeight(0);
  // upperPane.setPrefHeight(0);
  // bottomPane.setPrefHeight(0);
  // // BooleanProperty collapsed = new SimpleBooleanProperty();
  // // collapsed.bind(cardPane.getDividers().get(0).positionProperty().isEqualTo(0.1, 0.5));
  // // upAndDown.textProperty().bind(Bindings.when(collapsed).then("V").otherwise("^"));
  // // upAndDown.setOnAction(f -> {
  // // double target = collapsed.get() ? 0.4 : 0.1;
  // // KeyValue kv = new KeyValue(cardPane.getDividers().get(0).positionProperty(), target);
  // // Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), kv));
  // // timeline.play();
  // // });
  // }
  // }

  @FXML
  public void handleCardPane() {
    double j = cardPane.getDividerPositions()[0];
    if (cardPane.getDividerPositions()[0] >= 0.9) {
      for (double i = cardPane.getDividerPositions()[0]; i >= 0; i -= j / 100.0) {
        cardPane.getDividers().get(0).setPosition(i);
      }
      // cardPane.setDividerPosition(0, 0);
    } else {
      for (double i = cardPane.getDividerPositions()[0]; i <= 1; i += j / 100.0) {
        cardPane.getDividers().get(0).setPosition(i);
      }
      // cardPane.getDividers().get(0).setPosition(1);
      // cardPane.setDividerPosition(0, 1.0);
    }
  }

  // @FXML
  // public void handleDiceSlider() {
  // diceSlider.valueProperty().addListener(arg0);
  // });
  // }
  // @FXML
  // public void handleThrowDices() {
  // throwDices.setOnAction((event) -> {
  //
  // };
  // }

  @FXML
  public ImageView handleCardDragAndDrop(MouseEvent e) {
    ImageView img = (ImageView) e.getSource();
    // Integer selectedId = (int) e.getSource();
    System.out.println("Test1");
    if (left.getChildren().isEmpty()) {
      left.getChildren().add(img);
      System.out.println("TestLeft");
    } else if (center.getChildren().isEmpty()) {
      center.getChildren().add(img);
      System.out.println("TestCenter");
      // paneXY.getChildren().get(0)
    } else if (right.getChildren().isEmpty()) {
      // right.getChildren().add(img);
      right.getChildren().add(0, img);
      System.out.println("TestRight");
    }
    return img;
    // if(left == null && center == null && right == null) {
  }

  @FXML
  public void handleRemoveCard(MouseEvent e) {
    HBox b = (HBox) e.getSource();
    System.out.println("BLABLABLA");
    if (b.equals(left)) {
      ImageView img = (ImageView) b.getChildren().get(0);
      ownCards.getChildren().add(img);
      left.getChildren().clear();
    } else if (b.equals(center)) {
      ImageView img = (ImageView) b.getChildren().get(0);
      ownCards.getChildren().add(img);
      center.getChildren().clear();
      // remove(0);
    } else if (b.equals(right)) {
      ImageView img = (ImageView) b.getChildren().get(0);
      ownCards.getChildren().add(img);
      right.getChildren().clear();// getChildren().remove(0);
    }
  }

  /**
   * Arraylist<Karte> oben, unten;
   * 
   */



  @FXML
  public void handleTradeCards(ActionEvent e) {
    Thread th = new Thread() {
      public void run() {
        if (e.getSource().equals(tradeIn)) {
          // if(g.canbeTraded(topList.get(), c2, c3))
          String cards = Integer.toString(++tradedCards);
          tradedCardSets.setText(cards);

          System.out.println("Button geklickt");
        }
      }
    };
    th.start();
  }

  /**
   * 
   */
  @FXML
  public void handleSkipGameState() {
    // handleProgressBar();
    progress.setStyle("-fx-accent: magenta;");
    skip.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent e) {
        // switch(game.getGameState()) {
        // case 1:
        // gameState.setText("Place your armies!");
        // progress.setProgress(0.33);
        // break;
        // case 2:
        // gameState.setText("Attack other territories!");
        // progress.setProgress(0.66);
        // break;
        // case 3:
        // gameState.setText("Fortify your armies!");
        // progress.setProgress(1);
        // break;
        // }
        progress.setProgress(0.33);
        gameState.setText("Place your armies");

        try {
          TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        progress.setProgress(0.66);
        gameState.setText("Attack!");
        try {
          TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        progress.setProgress(0.90);
        gameState.setText("Fortify");
        try {
          TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        progress.setProgress(1);
        gameState.setText("End");
        // System.out.println("IMAGE GEKLICKT");
      }
    });
  }

  @FXML
  public void handleButton() {
    System.out.println("TEST");
  }

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

  /**
   * @author prto, @author smetzger
   * @param
   * @return
   */


  @FXML
  public void changeTerritoryColor(ActionEvent e) {

    // r.setStyle("-fx-background-color: "+p.getColor().getColorO().toString().toLowerCase()+";");
    Region r;
    String s = g.getTerritories().get(0).getName();

    // r = g.getTerritories().getName();
    // r.setStyle("-fx-background-color: yellow;");

  }



}
