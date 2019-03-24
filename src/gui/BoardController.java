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

/**
 * 
 * Logik hinter Board
 *
 */
public class BoardController {
  Player p;

  // TEST PARTS
  @FXML
  private Button button;
  @FXML
  private GridPane popup;
  // TEST PARTS ENDE
  private Game game;
  private Territory selectedTerritory = null;
  // Views
  @FXML
  private Button chatRoomButton;
  @FXML
  private SplitPane splitter;


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

  /**
   * Elements to handle trade-in button
   */
  @FXML
  private Button tradeIn;
  @FXML
  private Label tradedCards;

  /**
   * Element which handles the function to skip a game state
   * 
   */
  @FXML
  private ImageView skip;
  @FXML
  private Label gameState;

  public BoardGUI_Main boardGui;

  public void setMain(BoardGUI_Main boardGui) {
    this.boardGui = boardGui;
  }


  // ############################################
  /**
   * @author pcoberge
   * This method handles the exit Button. When the user presses the exit-Button, a pop-up window appears and stops the game.
   */
  public void pressLeave() {
    this.grayPane.toFront();
    this.quitPane.toFront();
  }

  /**
   * @author pcoberge
   * This method cancels the exit-handling.
   */
  public void handleNoLeave() {
    this.grayPane.toBack();
    this.quitPane.toBack();
  }

  /**
   * @author pcoberge
   * @param event : ActionEvent This parameter represents the element that invokes this method.
   * This action method changes the current stage to the statistic stage.
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

  // #############################################


  /**
   * 
   */
  @FXML
  public void Card(MouseEvent e) {
    BooleanProperty collapsed = new SimpleBooleanProperty();
    collapsed.bind(cardPane.getDividers().get(0).positionProperty().isEqualTo(0.1, 0.11));
    Button button = new Button();
    button.textProperty().bind(Bindings.when(collapsed).then("V").otherwise("^"));
    button.setOnAction(f -> {
      double target = collapsed.get() ? 0.4 : 0.1;
      KeyValue kv = new KeyValue(cardPane.getDividers().get(0).positionProperty(), target);
      Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), kv));
      timeline.play();
    });

  }

  /**
   * 
   */
  @FXML
  public void handleTradeCards(ActionEvent e) {
    int i = 0;
    if (e.getSource().equals(tradeIn)) {
      tradedCards = new Label("traded card sets:" + i);
      i++;

      System.out.println("Buttong geklickt");
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
   * @param e
   * This action method highlights the current region, when the mouse enters.
   */
  @FXML
  public void motionIn(MouseEvent e) {
    if (e.getSource() instanceof Label) {
      Label l = (Label) e.getSource();
      Region r = Game.getWorld().getTerritoriesName().containsKey(l)
          ? Game.getWorld().getTerritoriesName().get(l).getBoardRegion().getRegion()
          : Game.getWorld().getTerritoriesNoA().get(l).getBoardRegion().getRegion();
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

  /**
   * @author pcoberge
   * @param e
   * This action method deletes the highlight the current region is affected with, when the mouse exits. 
   */
  @FXML
  public void motionOut(MouseEvent e) {
    if (e.getSource() instanceof Label) {
      Label l = (Label) e.getSource();
      Region r = Game.getWorld().getTerritoriesName().containsKey(l)
          ? Game.getWorld().getTerritoriesName().get(l).getBoardRegion().getRegion()
          : Game.getWorld().getTerritoriesNoA().get(l).getBoardRegion().getRegion();
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
