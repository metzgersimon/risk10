package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;



/**
 * @author smetzger
 *
 */
public class Card {
  private Territory territory;
  private boolean isWildcard;
  private int id;
  private BufferedImage image;


  // nur territory und wildcard
  public Card(Territory territory, boolean isWildcard) {
    this.territory = territory;
    this.isWildcard = isWildcard;
    this.id = territory.getId();
  }

  public Card(int id, boolean isWildcard) {
    this.isWildcard = isWildcard;
    this.id = id;
  }



  // getters
  public Territory getTerritory() {
    return territory;
  }

  public boolean getIsWildcard() {
    return isWildcard;
  }

  public int getId() {
    return this.id;
  }

  public javafx.scene.image.Image getFxImage() {
    return SwingFXUtils.toFXImage(this.image, null);
  }

  // setters
  public void setTerritory(Territory territory) {
    this.territory = territory;
  }

  public void setIsWildcard(boolean isWildcard) {
    this.isWildcard = isWildcard;
  }


  /**
   * 
   * @param id
   */
  public void drawCard(int id) {
    VBox b = new VBox();
    VBox box = new VBox();
    box.setMinWidth(90);


    Text label = new Text(this.territory.getName());
    StackPane stPane = new StackPane();
    ImageView territoryImg = new ImageView(new Image(this.territory.getName() + ".png"));
    stPane.getChildren().add(territoryImg);
    stPane.getChildren().add(label);


    ImageView armyImg = new ImageView(new Image(this.territory.getSym() + ".gif"));
    box.getChildren().add(armyImg);
    box.getChildren().add(stPane);
    box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
        CornerRadii.EMPTY, BorderWidths.DEFAULT)));

    b.getChildren().add(box);
    WritableImage image = b.snapshot(new SnapshotParameters(), null);
    File file = new File(this.territory.getId() + ".png");

    try {
      ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    } catch (IOException e) {
    }
  }


  /**
   * @author qiychen
   * @param c1 card1
   * @param c2 card2
   * @param c3 card3
   * @return whether 3 cards can be traded or not
   */
  public boolean canBeTraded(Card c2, Card c3) {
    CardSymbol sym1 = this.isWildcard ? CardSymbol.WILDCARD : this.getTerritory().getSym();
    CardSymbol sym2 = c2.isWildcard ? CardSymbol.WILDCARD : c2.getTerritory().getSym();
    CardSymbol sym3 = c3.isWildcard ? CardSymbol.WILDCARD : c3.getTerritory().getSym();
    // same artillery pictures without wildcards
    boolean same1 = (sym1 == sym2) && (sym2 == sym3);
    // all types of cards without wildcards
    boolean different = (sym1 != sym2) && (sym2 != sym3) && (sym1 != sym3);
    // same artillery pictures with one wildcard
    boolean same2 = ((sym1 == sym2) && (sym3 == CardSymbol.WILDCARD))
        || ((sym1 == sym3) && (sym2 == CardSymbol.WILDCARD))
        || ((sym2 == sym3) && (sym1 == CardSymbol.WILDCARD));
    // same artillery pictures with two wildcards, or all types of cards with two wildcards
    boolean same3 = ((sym1 == sym2) && (sym1 == CardSymbol.WILDCARD))
        || ((sym1 == sym3) && (sym1 == CardSymbol.WILDCARD))
        || ((sym2 == sym3) && (sym2 == CardSymbol.WILDCARD));
    // all types of cards with one wildcard
    boolean different2 =
        (sym1 == CardSymbol.WILDCARD && sym2 != sym3 && sym2 != CardSymbol.WILDCARD)
            || (sym2 == CardSymbol.WILDCARD && sym1 != sym3 && sym1 != CardSymbol.WILDCARD)
            || (sym3 == CardSymbol.WILDCARD && sym1 != sym2 && sym1 != CardSymbol.WILDCARD);
    boolean result = same1 || same2 || same3 || different || different2;
    return result;
  }

  @Override
  public boolean equals(Object o) {
    Card c = (Card) o;
    return (this.getId() == c.getId());
  }
}
