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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
  @FXML
  private Button kamschatka;

  @FXML
  private Region eastAust;
  @FXML
  private Region westAust;

  
  /**
   * Elements that handle leave option
   */
  @FXML private Shape circleLeave;
  @FXML private ImageView imageLeave;
  @FXML private Pane quitPane;
  @FXML private Pane grayPane;
  @FXML private Button yesLeave;
  @FXML private Button noLeave;
  
  /**
   * Elements that show the current game state and
   * illustrate who is the current player
   */
  @FXML private ProgressBar progress;
  
  /**
   * Elements to handle the card split-pane
   */
  @FXML private SplitPane cardPane;
  @FXML private Button upAndDown;
  

  public BoardGUI_Main boardGui;

  public void setMain(BoardGUI_Main boardGui) {
    this.boardGui = boardGui;
  }

  
  //############################################
  /**
   * @param e
   */
  public void pressLeave() {
    this.grayPane.toFront();
    this.quitPane.toFront();
  }
  
  public void handleNoLeave() {
    this.grayPane.toBack();
    this.quitPane.toBack();
  }
  
  /**
   * @param event : ActionEvent
   * This parameter represents the element that invokes this method.
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
     //((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load StatisticGUI.fxml");
    }
  }
  
  /**
   * This method is a dummy Method, to get to know, how the ProgressBAr can be handled
   */
  public void handleProgressBar() {
    this.progress.setStyle("-fx-accent: red;");
    this.progress.setProgress(0.4);
  }
  
  //#############################################
  
  
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
      double target = collapsed.get() ? 0.4: 0.1;
      KeyValue kv = new KeyValue(cardPane.getDividers().get(0).positionProperty(),target);
      Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), kv));
      timeline.play();
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

//  @FXML
//  public void clicked(MouseEvent e) {
//    Player p = null;
//    Region r = (Region) e.getSource();
//    Territory territory = game.getWorld().getTerritoriesRegion().get(r);
//    switch (game.getState()) {
//      case INITIALISING:
//        /**
//         * if a player selects a territory, he becomes the owner of this territory and places his
//         * army at this territory
//         */
//        for (Territory t : game.getWorld().getTerritories().values()) {
//          t.getRegion().setEffect(new Glow(0.0));
//        }
//        r.setEffect(new Glow(0.0));
//        territory.setOwner(p);
//        territory.setNumberOfArmies(1);
//        break;
//      case ARMY_REINFORCEMENT:
//        // Methode mit Eingabe der Anzahl der Armeen
//        break;
//      case ATTACKING:
//        /**
//         * if a player selects a territory he owns, all territories that aren't neighbors and belong
//         * to him will be disabled. if a player selects a territory his selected territory is
//         * neighbor of,
//         */
//        if (selectedTerritory == null) {
//          r.setEffect(new Glow(0.3));
//          selectedTerritory = territory;
//          for (Territory t : game.getWorld().getTerritories().values()) {
//            if (!t.equals(territory) && !(territory.getNeighbor().contains(t))) {
//              t.getRegion().setDisable(true);
//            }
//          }
//        } else {
//          /*
//           * Boolean winner = attack(selectedTerritory, territory, numberOfArmies); Pop-Up Eingabe
//           * mit Anzahl der Armeen if (winner) { r.getShape().setFill(p.color);
//           * territory.setOwner(p); //Pop-UP Eingabe mit Anzahl der Armeen
//           * territory.setNumberOfArmies(amount); selectedTerritory.setNumberOfArmies(-amount);
//           */
//        }
//        selectedTerritory = null;
//        r.setEffect(new Glow(0.0));
//        selectedTerritory.getRegion().setEffect(new Glow(0.0));
//        break;
//    }
//  }

  @FXML
  public void motion(MouseEvent e) {
    Region r = (Region) e.getSource();
    if (r.getEffect() == null) {
      r.setEffect(new Glow(0.2));
    }
  }
}