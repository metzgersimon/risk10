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

  public Card(boolean isWildcard) {
    this.isWildcard = isWildcard;
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
   * @author pcoberge
   * 
   * @param c1 card that invoces method
   * @param c2 card
   * @param c3 card
   * @return whether three cards are a valid set
   */
  public boolean validCardSet(Card c2, Card c3) {
    CardSymbol sym1 = (!this.getIsWildcard()) ? this.getTerritory().getSym() : CardSymbol.WILDCARD;
    CardSymbol sym2 = (!c2.getIsWildcard()) ? c2.getTerritory().getSym() : CardSymbol.WILDCARD;
    CardSymbol sym3 = (!c3.getIsWildcard()) ? c3.getTerritory().getSym() : CardSymbol.WILDCARD;
    int numberOfWildCards = 0;
    // the game has only 2 wildcards, therefore the case numberOfWildCards = 3 can't be reached
    if (this.getIsWildcard()) {
      numberOfWildCards++;
    }
    if (c2.getIsWildcard()) {
      numberOfWildCards++;
    }
    if (c3.getIsWildcard()) {
      numberOfWildCards++;
    }
    switch (numberOfWildCards) {
      case (0):
        if (sym1 == sym2 && sym2 == sym3) {
          return true;
        }
        return false;
      case (1):
        if ((this.getIsWildcard() && sym2 == sym3) || (c2.getIsWildcard() && sym1 == sym3)
            || (c3.getIsWildcard() && sym1 == sym2)) {
          return true;
        }
        return false;
      case (2):
        return true;
    }
    return false;
  }
}
