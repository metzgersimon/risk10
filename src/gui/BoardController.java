package gui;

import java.awt.Color;
import game.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Main;

/**
 * 
 * Logik hinter Board
 *
 */
public class BoardController {
  Game g;
  Player p;
  // BoardGUI_Elements elements;

  // TEST PARTS
  @FXML
  private Button button;
  @FXML
  private GridPane popup;
  // TEST PARTS ENDE
  private Game game;
  private int numberOfTerritories;
  private Territory selectedTerritory = null;
  private boolean choice = true;
  private boolean inAttack = false;
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
  private Pane quitPane;
  @FXML
  private Pane grayPane;
  @FXML
  private Button yesLeave;
  @FXML
  private Button noLeave;

  /**
   * Elements that handle dicePane
   */
  @FXML
  private Pane dicePane;
  
  /**
   * Elements that show the current game state and illustrate who is the current player
   */
  @FXML
  private ProgressBar progress;

  /**
   * Elements to handle the card split-pane
   */
  @FXML
  private SplitPane cardPane;
  @FXML
  private Button upAndDown;
  @FXML
  private AnchorPane upperPane;
  @FXML
  private AnchorPane bottomPane;

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
  private Pane left;
  @FXML
  private Pane center;
  @FXML
  private Pane right;

  public BoardGUI_Main boardGui;

  public void setMain(BoardGUI_Main boardGui, Game g) {
    this.boardGui = boardGui;
    this.g = g;
    connectRegionTerritory();
    // this.elements = elements;
  }


  // ############################################
  /**
   * @author pcoberge This method handles the exit Button. When the user presses the exit-Button, a
   *         pop-up window appears and stops the game.
   */
  public void pressLeave() {
    this.grayPane.toFront();
    this.quitPane.toFront();
  }

  /**
   * @author pcoberge This method cancels the exit-handling.
   */
  public void handleNoLeave() {
    this.grayPane.toBack();
    this.quitPane.toBack();
  }

  /**
   * @author pcoberge
   * @param event : ActionEvent This parameter represents the element that invokes this method. This
   *        action method changes the current stage to the statistic stage.
   */
  public void handleLeave(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StatisticGUI.fxml"));
      AnchorPane root = fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Statistics");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load StatisticGUI.fxml");
    }
  }

  /**
   * This method is a dummy Method, to get to know, how the ProgressBAr can be handled
   */
  public void handleProgressBar() {
    // String color = p.getColor().toString().toLowerCase();
    // this.progress.setStyle("-fx-accent: "+color+";");
    // this.progress.setProgress(0.4);
  }

  /**
   * @author pcoberge
   * @param MouseEvent The parameter contains the information which gui element triggers the
   *        actionlistener.
   * 
   */
  public void clicked(MouseEvent e) {
   
    Region r;
    if (e.getSource() instanceof Label) {
      Label l = (Label) e.getSource();
      if (l.getText().matches("(-|[0-9]+)")) {
        r = g.getWorld().getTerritoriesNoA().get(l).getBoardRegion().getRegion();
      } else {
        r = g.getWorld().getTerritoriesName().get(l).getBoardRegion().getRegion();
      }
    } else {
      r = (Region) e.getSource();
    }
    Territory t = g.getWorld().getTerritoriesRegion().get(r);
    
    if(!inAttack && !t.equals(selectedTerritory)) {
     // switch (g.getGameState()) {
      switch(2) {
        // new game
        case (0):
          // place armies
          
        case (1):
          // attack
        case (2):
          if (numberOfTerritories==0) {
            numberOfTerritories++;
            choice = false;
            selectedTerritory = t;
            r.setEffect(new Lighting());
            for (Territory territory : t.getNeighbor()) {
              territory.getBoardRegion().getRegion().setEffect(new Glow(0.5));
            }
          } else if (numberOfTerritories==1 && selectedTerritory.getNeighbor().contains(t)){
            numberOfTerritories=2;
            grayPane.toFront();
            dicePane.toFront();
            // open pop-up
            //ATTACK METHODE
            inAttack=true;
            
            

          }
      }
    } else if(t.equals(selectedTerritory)) {
      r.setEffect(null);
      for (Territory territory : t.getNeighbor()) {
        territory.getBoardRegion().getRegion().setEffect(null);
      }
      choice = true;
      selectedTerritory = null;
      
    }
  }

  public void clickBack() {
    if (numberOfTerritories==2) {
      for (Territory t : g.getWorld().getTerritories().values()) {
        t.getBoardRegion().getRegion().setEffect(null);
      }
    }
    dicePane.toBack();
    quitPane.toBack();
    grayPane.toBack();
  }
  // #############################################


  /**
   * 
   */
  @FXML
  public void handleCardPane(MouseEvent e) {
    if(e.getSource().equals(upAndDown)) {
      cardPane.setPrefHeight(0);
      upperPane.setPrefHeight(0);
      bottomPane.setPrefHeight(0);
//      BooleanProperty collapsed = new SimpleBooleanProperty();
//      collapsed.bind(cardPane.getDividers().get(0).positionProperty().isEqualTo(0.1, 0.5));
//      upAndDown.textProperty().bind(Bindings.when(collapsed).then("V").otherwise("^"));
//      upAndDown.setOnAction(f -> {
//        double target = collapsed.get() ? 0.4 : 0.1;
//        KeyValue kv = new KeyValue(cardPane.getDividers().get(0).positionProperty(), target);
//        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), kv));
//        timeline.play();
//      });
    }
  }
  
  @FXML
  public void handleCardDragAndDrop(MouseEvent e) {
    ImageView img = (ImageView)e.getSource();
    System.out.println("Test1");
    System.out.println(left.getChildren());
      if(left.getChildren().isEmpty()) {
        left.setStyle("-fx-background-color: #E94196");
        left.getChildren().add(img);
        System.out.println("TestLeft");
      }
      else if(center.getChildren().isEmpty()){
        center.getChildren().add(img);
        System.out.println("TestCenter");
//        paneXY.getChildren().get(0)
      }
      else if(right.getChildren().isEmpty()) {
//        right.getChildren().add(img);
        right.getChildren().add(0, img);
        System.out.println("TestRight");
      }
     
  //  if(left == null && center == null && right == null) {
      
    
  }

  /**
   * 
   */
  @FXML
  public void handleTradeCards(ActionEvent e) {
    if (e.getSource().equals(tradeIn)) {
      String cards = Integer.toString(++tradedCards);
      tradedCardSets.setText(cards);

      System.out.println("Button geklickt");
    }
  }

  /**
   * 
   */
  @FXML
  public void handleSkipGameState() {
    handleProgressBar();
    this.skip.setOnMouseClicked(new EventHandler<MouseEvent>() {
      double i = 0.2;

      @Override
      public void handle(MouseEvent e) {
        // progress.setProgress(0);
        // switch(game.getGameState()) {
        // case PLACE_ARMIES:
        if (progress.getProgress() < 0.8) {
          progress.setProgress(progress.getProgress() + i);
        }
        // progress.setProgress(0.3);
        // gameState = new Label("ATTACKING");
        // break;
        // case ATTACKING:
        // progress.setProgress(0.6);
        // gameState = new Label("FORTIFY");
        // break;
        // case FORTIFY:
        // progress.setProgress(1);
        // gameState = new Label("END");
        // break;
        // }
        System.out.println("IMAGE GEKLICKT");
      }

    });

  }



  @FXML
  public void handleClickCRB() {
    if (chatRoomButton.getText().equals("<")) {
      splitter.setDividerPosition(1, 0.8);
      chatRoomButton.setText("Chat Room >");
    } else {
      splitter.setDividerPosition(1, 1);
      chatRoomButton.setText("<");
    }
  }

  @FXML
  public void handleButton() {
    System.out.println("TEST");
  }

  // @FXML
  // public void clicked(MouseEvent e) {
  // Player p = null;
  // Region r = (Region) e.getSource();
  // Territory territory = game.getWorld().getTerritoriesRegion().get(r);
  // switch (game.getState()) {
  // case INITIALISING:
  // /**
  // * if a player selects a territory, he becomes the owner of this territory and places his
  // * army at this territory
  // */
  // for (Territory t : game.getWorld().getTerritories().values()) {
  // t.getRegion().setEffect(new Glow(0.0));
  // }
  // r.setEffect(new Glow(0.0));
  // territory.setOwner(p);
  // territory.setNumberOfArmies(1);
  // break;
  // case ARMY_REINFORCEMENT:
  // // Methode mit Eingabe der Anzahl der Armeen
  // break;
  // case ATTACKING:
  // /**
  // * if a player selects a territory he owns, all territories that aren't neighbors and belong
  // * to him will be disabled. if a player selects a territory his selected territory is
  // * neighbor of,
  // */
  // if (selectedTerritory == null) {
  // r.setEffect(new Glow(0.3));
  // selectedTerritory = territory;
  // for (Territory t : game.getWorld().getTerritories().values()) {
  // if (!t.equals(territory) && !(territory.getNeighbor().contains(t))) {
  // t.getRegion().setDisable(true);
  // }
  // }
  // } else {
  // /*
  // * Boolean winner = attack(selectedTerritory, territory, numberOfArmies); Pop-Up Eingabe
  // * mit Anzahl der Armeen if (winner) { r.getShape().setFill(p.color);
  // * territory.setOwner(p); //Pop-UP Eingabe mit Anzahl der Armeen
  // * territory.setNumberOfArmies(amount); selectedTerritory.setNumberOfArmies(-amount);
  // */
  // }
  // selectedTerritory = null;
  // r.setEffect(new Glow(0.0));
  // selectedTerritory.getRegion().setEffect(new Glow(0.0));
  // break;
  // }
  // }

  /**
   * @author pcoberge
   * @param e This action method highlights the current region, when the mouse enters.
   */
  @FXML
  public void motionIn(MouseEvent e) {
    if (choice) {
      if (e.getSource() instanceof Label) {
        Label l = (Label) e.getSource();
        Region r;
        if (l.getText().matches("(-|[0-9]+)")) {
          r = g.getWorld().getTerritoriesNoA().get(l).getBoardRegion().getRegion();
        } else {
          r = g.getWorld().getTerritoriesName().get(l).getBoardRegion().getRegion();
        }
        if (r.getEffect() == null) {
          r.setEffect(new Glow(0.2));
        }
      } else {
        Region r = (Region) e.getSource();
        if (r.getEffect() == null) {
          r.setEffect(new Glow(0.2));
        }
      }
    }
  }

  /**
   * @author pcoberge
   * @param e This action method deletes the highlight the current region is affected with, when the
   *        mouse exits.
   */
  @FXML
  public void motionOut(MouseEvent e) {
    if (choice) {
      if (e.getSource() instanceof Label) {
        Label l = (Label) e.getSource();
        Region r;
        if (l.getText().matches("(-|[0-9]+)")) {
          r = g.getWorld().getTerritoriesNoA().get(l).getBoardRegion().getRegion();
        } else {
          r = g.getWorld().getTerritoriesName().get(l).getBoardRegion().getRegion();
        }
        if (r.getEffect() != null) {
          r.setEffect(null);
        }
      } else {
        Region r = (Region) e.getSource();
        if (r.getEffect() != null) {
          r.setEffect(null);
        }
      }
    }
  }

  /**
   * @author pcoberge This method creates a connection between the javafx region and label elements
   *         and the equivalent territory.
   */
  public void connectRegionTerritory() {
    g.getWorld().getTerritories().get(1)
        .setBoardRegion(new BoardRegion(alaska, alaskaHeadline, alaskaNoA));
    g.getWorld().getTerritories().get(2).setBoardRegion(
        new BoardRegion(northwestTerritory, northwestTerritoryHeadline, northwestTerritoryNoA));
    g.getWorld().getTerritories().get(3)
        .setBoardRegion(new BoardRegion(alberta, albertaHeadline, albertaNoA));
    g.getWorld().getTerritories().get(4).setBoardRegion(
        new BoardRegion(westernUnitedStates, westernUnitedStatesHeadline, westernUnitedStatesNoA));
    g.getWorld().getTerritories().get(5)
        .setBoardRegion(new BoardRegion(centralAmerica, centralAmericaHeadline, centralAmericaNoA));
    g.getWorld().getTerritories().get(6)
        .setBoardRegion(new BoardRegion(greenland, greenlandHeadline, greenlandNoA));
    g.getWorld().getTerritories().get(7)
        .setBoardRegion(new BoardRegion(ontario, ontarioHeadline, ontarioNoA));
    g.getWorld().getTerritories().get(8)
        .setBoardRegion(new BoardRegion(quebec, quebecHeadline, quebecNoA));
    g.getWorld().getTerritories().get(9).setBoardRegion(
        new BoardRegion(easternUnitedStates, easternUnitedStatesHeadline, easternUnitedStatesNoA));
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
    g.getWorld().getTerritories().get(18)
        .setBoardRegion(new BoardRegion(northernEurope, northernEuropeHeadline, northernEuropeNoA));
    g.getWorld().getTerritories().get(19)
        .setBoardRegion(new BoardRegion(westernEurope, westernEuropeHeadline, westernEuropeNoA));
    g.getWorld().getTerritories().get(20)
        .setBoardRegion(new BoardRegion(southernEurope, southernEuropeHeadline, southernEuropeNoA));
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
}
