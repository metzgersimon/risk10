package gui;

import java.util.HashMap;
import game.Card;
import game.GameState;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
   * @author prto initialize card lists
   */
  // public void initializeCardLists() {
  // topList = new HashMap<Integer, Card>();
  // bottomList = new HashMap<Integer, Card>();
  // }

  // moves card from bottomList to topList
  public void selectCard(Card card, int i) {
    if (bottomList.containsKey(card.getId())) {
      if (topList.size() <= 3) {
        Card temp = bottomList.get(card.getId());
        bottomList.remove(card.getId());
        topList.put(i, temp);
      } else {
        System.out.println("Error: TopList is > 3");
      }
    } else {
      System.out.println("Error: Card is not in bottomList");
    }
  }

  // moves card from topList to bottomList
  public void deselectCard(Card card, int i) {
    if (topList.containsKey(i)) {
      if (bottomList.size() <= 5) {
        Card temp = topList.get(i);
        topList.remove(i);
        bottomList.put(temp.getId(), temp);
      } else {
        System.out.println("Error: bottomList is > 5");
      }
    } else {
      System.out.println("Error: Card is not in topList");
    }
  }
  
  public void handleGrayPane(boolean b) {
    Platform.runLater(new Runnable() {
      public void run() {
        grayPane.setVisible(b);
      }
    });
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
    ImageView img = (ImageView) e.getSource();
    Card card = (Card) img.getUserData();

    if (left.getChildren().isEmpty()) {
      img.setMouseTransparent(true);
      selectCard(card, 0);

      StackPane pane = (StackPane) img.getParent();
      left.getChildren().add(pane);
      topList.put(0, card);
      System.out.println(topList.get(0).getId());
      pane.getStylesheets().clear();
    } else if (center.getChildren().isEmpty()) {
      img.setMouseTransparent(true);

      selectCard(card, 1);

      StackPane pane = (StackPane) img.getParent();
      center.getChildren().add(pane);
      topList.put(1, card);
      System.out.println(topList.get(1).getId());
      pane.setStyle(null);
    } else if (right.getChildren().isEmpty()) {
      img.setMouseTransparent(true);

      selectCard(card, 2);

      StackPane pane = (StackPane) img.getParent();
      right.getChildren().add(pane);
      topList.put(2, card);
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
    //
    // }
    //
    // });
    // return img;

  }


  /**
   * @author pcoberge
   * @param c
   */
  public void insertCards(Card c) {
    Platform.runLater(new Runnable() {
      public void run() {
        String file = c.getId() + ".png";
        String css = this.getClass().getResource("BoardGUI_additional.css").toExternalForm();
        StackPane pane = new StackPane();

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
            handleCardDragAndDrop(e);
          }
        };

        ImageView img;
        img = new ImageView(
            new Image(getClass().getResource("/resources/cards/" + file).toString(), true));
        img.setUserData(c);
        pane.getChildren().add(img);
        ownCards.getChildren().add(pane);
        bottomList.put(c.getId(), c);
        System.out.println("in BottomList: " + bottomList.containsKey(c.getId()));
        img.setMouseTransparent(false);
        img.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        pane.getStylesheets().add(css);
      }
    });
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
      Card card = (Card) img.getUserData();
      this.deselectCard(card, 0);

      ownCards.getChildren().add(pane);
      bottomList.put(card.getId(), card);
      img.setMouseTransparent(false);
      left.getChildren().clear();
      pane.getStylesheets().add(css);

    } else if (b.equals(center)) {
      StackPane pane = (StackPane) b.getChildren().get(0);
      ImageView img = (ImageView) pane.getChildren().get(0);
      Card card = (Card) img.getUserData();
      this.deselectCard(card, 1);

      ownCards.getChildren().add(pane);
      bottomList.put(card.getId(), card);
      img.setMouseTransparent(false);
      center.getChildren().clear();
      pane.getStylesheets().add(css);
    } else if (b.equals(right)) {
      StackPane pane = (StackPane) b.getChildren().get(0);
      ImageView img = (ImageView) pane.getChildren().get(0);
      Card card = (Card) img.getUserData();
      this.deselectCard(card, 2);

      ownCards.getChildren().add(pane);
      bottomList.put(card.getId(), card);
      img.setMouseTransparent(false);
      right.getChildren().clear();// getChildren().remove(0);
      pane.getStylesheets().add(css);
    }
    tradeIn.setDisable(true);
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
            // Main.g.setCard(topList.get(0));
            // Main.g.setCard(topList.get(1));
            // Main.g.setCard(topList.get(2));

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
          } else {
            System.out.println("No trade");
            cantBeTraded.toFront();
          }
        }
      }
    });
  }

}
