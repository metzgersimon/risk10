package gui.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import game.Card;
import game.GameState;
import game.TutorialMessages;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import main.Main;

public class CardSubSceneController {
  @FXML
  private Pane grayPane;

  /**
   * @author prto testing
   */
  public HashMap<Integer, Card> topList = new HashMap<>();
  public HashMap<Integer, Card> bottomList = new HashMap<>();


  /**
   * Elements to handle the card selection the player wants to trade in
   */
  @FXML
  private GridPane paneXY;
  @FXML
  private HBox left, center, right;
  /**
   * Elements to handle the card split-pane
   */
  @FXML
  private Pane transparentPane;
  @FXML
  private Pane cardPane;
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


  public void handleGrayPane(boolean b) {
    grayPane.setVisible(b);
  }

  /**
   * @author smetzger
   * @param e
   * @return
   */
  @FXML
  public void handleCardDragAndDrop(MouseEvent e) {
    // Platform.runLater(new Runnable() {
    // public void run() {
    if (left.getChildren().isEmpty() || center.getChildren().isEmpty()
        || right.getChildren().isEmpty()) {
      ImageView img = (ImageView) e.getSource();
      Card card = (Card) img.getUserData();
      img.setMouseTransparent(true);
      StackPane pane = (StackPane) img.getParent();
      System.out.println(card.getId());
      if (left.getChildren().isEmpty()) {
        left.getChildren().add(pane);
        topList.put(0, card);
        bottomList.remove(card.getId());
        System.out.println(topList.get(0).getId());
        pane.getStylesheets().clear();
      } else if (center.getChildren().isEmpty()) {
        center.getChildren().add(pane);
        topList.put(1, card);
        bottomList.remove(card.getId());
        System.out.println(topList.get(1).getId());
        pane.setStyle(null);
      } else if (right.getChildren().isEmpty()) {
        right.getChildren().add(pane);
        topList.put(2, card);
        bottomList.remove(card.getId());
        System.out.println(topList.get(2).getId());
        pane.setStyle(null);
      }

      if (!left.getChildren().isEmpty() && !center.getChildren().isEmpty()
          && !right.getChildren().isEmpty()
          && topList.get(0).canBeTraded(topList.get(1), topList.get(2))
          && !Main.g.getCurrentPlayer().getStartedDistribution()
          && Main.g.getGameState() == GameState.ARMY_DISTRIBUTION) {
        System.out.println(
            topList.get(0).getId() + " " + topList.get(1).getId() + " " + topList.get(2).getId());
        tradeIn.setDisable(false);
      }
    }
  }


  /**
   * @author pcoberge
   * @param c
   */
  public void insertCards(Card c) {
    String file = c.getId() + ".png";
    String css = getClass().getResource("/gui/BoardGUI_additional.css").toExternalForm();
    ImageView img = new ImageView(
        new Image(getClass().getResource("/resources/cards/" + file).toString(), true));
    img.setUserData(c);
    img.setVisible(true);
    StackPane pane = new StackPane();
    pane.setVisible(true);
    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        handleCardDragAndDrop(e);
      }
    };
    pane.getChildren().add(img);
    ownCards.getChildren().add(pane);
    ownCards.getStyleClass().add("-fx-background-color: red;");
    ownCards.toFront();
    bottomList.put(c.getId(), c);
    img.setMouseTransparent(false);
    img.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
//    pane.getStylesheets().add(css);
    img.getStyleClass().add("-fx-translate-y: -5px;");

  }

  /**
   * @author smetzger
   * @param e
   */
  @FXML
  public void handleRemoveCard(MouseEvent e) {
    String css = this.getClass().getResource("/gui/BoardGUI_additional.css").toExternalForm();
    HBox b = (HBox) e.getSource();
    if (b.getChildren().size() > 0) {
      StackPane pane = (StackPane) b.getChildren().get(0);
      ImageView img = (ImageView) pane.getChildren().get(0);
      Card card = (Card) img.getUserData();
      Platform.runLater(new Runnable() {
        public void run() {
          ownCards.getChildren().add(pane);
          bottomList.put(card.getId(), card);
          img.setMouseTransparent(false);
          if (b.equals(left)) {
            topList.remove(0);
            left.getChildren().clear();
          } else if (b.equals(center)) {
            topList.remove(1);
            center.getChildren().clear();
          } else if (b.equals(right)) {
            topList.remove(2);
            right.getChildren().clear();
          }
          pane.getStylesheets().add(css);
          tradeIn.setDisable(true);
        }
      });
    }
  }



  /**
   * @author smetzger
   * @param e
   */
  @FXML
  public void handleTradeCards(ActionEvent e) {
    Platform.runLater(new Runnable() {
      public void run() {
        if (e.getSource().equals(tradeIn) && Main.g.getGameState() == GameState.ARMY_DISTRIBUTION) {
          // re-add Cards to game carddeck
          if (Main.g.getCurrentPlayer().tradeCards(topList.get(0), topList.get(1),
              topList.get(2))) {
            String cards = Integer.toString(Main.g.getCurrentPlayer().getTradedCardSets());
            tradedCardSets.setText(cards);
            topList.remove(0);
            topList.remove(1);
            topList.remove(2);
            left.getChildren().remove(0);
            center.getChildren().remove(0);
            right.getChildren().remove(0);
            tradedCardSets.setText(Main.g.getCurrentPlayer().getTradedCardSets() + "");
            tradeIn.setDisable(true);
            Main.b.setCircleArmiesToDistributeLable();
            if (Main.g.getCurrentPlayer().getCards().size() < 5) {
              grayPane.setVisible(false);
            }
          }
        }
      }
    });
  }


  @FXML
  public void clickBack(MouseEvent e) {
    Main.stagePanes.hide();
  }

  @FXML
  public void handleCardPane() {
    Main.b.handleCardPane();
  }

  /**
   * @author prto handle press on rule book button
   */
  public void handleRuleBook() {
    Main.b.handleRuleBook();
  }

  /**
   * @author pcoberge This method handles the exit Button. When the user presses the exit-Button, a
   *         pop-up window appears and stops the game.
   */
  public void pressLeave() {
    Main.b.pressLeave();
  }
}
