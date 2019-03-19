package game;

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


  // nur territory und wildcard
  public Card(Territory territory, boolean isWildcard) {
    this.territory = territory;
    this.isWildcard = isWildcard;
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

  //setters
  public void setTerritory(Territory territory) {
    this.territory = territory;
  }
  public void setIsWildcard(boolean isWildcard) {
    this.isWildcard = isWildcard;
  }


  public void drawCard(int id) {
    VBox b = new VBox();
    VBox box = new VBox();
    box.setMinWidth(90);


    Text label = new Text(this.territory.getName());
    StackPane stPane = new StackPane();
    ImageView territoryImg = new ImageView(new Image(this.territory.getName()+".png"));
    stPane.getChildren().add(territoryImg);
    stPane.getChildren().add(label);


    ImageView armyImg = new ImageView(new Image(this.territory.getSym()+".gif"));
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

//    ImageView imgCard = new ImageView(new Image(file.toURI().toString()));
  }


  // }
}
