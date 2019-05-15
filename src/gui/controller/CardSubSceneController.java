package gui.controller;

import game.Card;
import game.GameState;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

/**
 * Class represents the stage for the risk cards a player owns.
 * 
 * @author pcoberge
 * @author smetzger
 * @author prto
 *
 */
public class CardSubSceneController {
  @FXML
  private Pane grayPane;
  @FXML
  private GridPane pane;
  @FXML
  private HBox left;
  @FXML
  private HBox center;
  @FXML
  private HBox right;
  @FXML
  private Pane transparentPane;
  @FXML
  private Pane cardPane;
  @FXML
  private AnchorPane upAndDown;
  @FXML
  private FlowPane ownCards;
  @FXML
  private Circle cardButton;
  @FXML
  private Button tradeIn;
  @FXML
  private Label tradedCardSets;
  @FXML
  private Pane cantBeTraded;
  @FXML
  private Label label;

  private HashMap<Integer, Card> topList = new HashMap<>();
  private HashMap<Integer, Card> bottomList = new HashMap<>();

  /**
   * Method sets graypane visible.
   * 
   * @param b true or false whether the pane should be visible or not.
   */
  public void handleGrayPane(boolean b) {
    grayPane.setVisible(b);
  }

  /**
   * Method allows player to drag and drop cards in its own card pane.
   * 
   * @author smetzger
   * @param e represents the MouseEvent of a player.
   */
  @FXML
  public void handleCardDragAndDrop(MouseEvent e) {
    if (left.getChildren().isEmpty() || center.getChildren().isEmpty()
        || right.getChildren().isEmpty()) {
      ImageView img = (ImageView) e.getSource();
      Card card = (Card) img.getUserData();
      img.setMouseTransparent(true);
      StackPane pane = (StackPane) img.getParent();
      if (left.getChildren().isEmpty()) {
        left.getChildren().add(pane);
        topList.put(0, card);
        bottomList.remove(card.getId());
        pane.getStylesheets().clear();
      } else if (center.getChildren().isEmpty()) {
        center.getChildren().add(pane);
        topList.put(1, card);
        bottomList.remove(card.getId());
        pane.setStyle(null);
      } else if (right.getChildren().isEmpty()) {
        right.getChildren().add(pane);
        topList.put(2, card);
        bottomList.remove(card.getId());
        pane.setStyle(null);
      }

      if (!left.getChildren().isEmpty() && !center.getChildren().isEmpty()
          && !right.getChildren().isEmpty()
          && topList.get(0).canBeTraded(topList.get(1), topList.get(2))
          && Main.g.getGameState() == GameState.ARMY_DISTRIBUTION) {
        tradeIn.setDisable(false);
      }
    }
  }


  /**
   * Method inserts a card in the card list of the player on the gui.
   * 
   * @author pcoberge
   * @param c card which should be added to the GUI of a player.
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
    pane.getChildren().add(img);
    ownCards.getChildren().add(pane);
    ownCards.getStyleClass().add("-fx-background-color: red;");
    ownCards.toFront();
    bottomList.put(c.getId(), c);
    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        handleCardDragAndDrop(e);
      }
    };
    img.setMouseTransparent(false);
    img.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    img.getStyleClass().add("-fx-translate-y: -5px;");

  }

  /**
   * Method removes a chosen card from the trade-in-pane into the bottom pane.
   * 
   * @author smetzger
   * @param e MouseEvent when clicked on a card.
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
   * Method handles the gui in case a player trades in a valid card set.
   * 
   * @author smetzger
   * @param e ActionEvent when the button is clicked.
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


  /**
   * Method hides the current stage.
   * 
   * @param e Event when clicked in the back.
   */
  @FXML
  public void clickBack(MouseEvent e) {
    Main.stagePanes.close();
  }

  /**
   * Method hides the current stage.
   */
  @FXML
  public void handleCardPane() {
    Main.b.handleCardPane();
  }

  /**
   * Method handles press on rule book button.
   * 
   * @author prto
   */
  public void handleRuleBook() {
    Main.b.handleRuleBook();
  }

  /**
   * This method handles the exit Button. When the user presses the exit-Button, a pop-up window
   * appears and stops the game.
   * 
   * @author pcoberge
   */
  public void pressLeave() {
    Main.b.pressLeave();
  }

  public void disableTradeInButton(boolean b) {
    tradeIn.setDisable(b);
  }
}
