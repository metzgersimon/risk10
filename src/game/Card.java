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

public class Card {
    private String name;
    private ImageView territory;
    private String armyName;
    private ImageView armyType;
    private int territoryID;
    private boolean isWildcard;
    
	
	  //nur territory und wildcard
      public Card(int territoryID, boolean isWildcard) {
    
      this.territoryID = territoryID;
	  this.name = name;
	  this.territory = territory;
	  this.armyName = armyName;
	  this.armyType = armyType;
	  this.isWildcard = isWildcard;
	}
	
	public Card(boolean isWildcard) {
	  
	}

  
  
    // getters
	public int getTerritoryID() {
	  return this.territoryID;
	}
    public String getName() {
      return name;
    }
  
    public ImageView getTerritory() {
      return territory;
    }
  
    public String getArmyName() {
      return armyName;
    }
  
    public ImageView getArmyType() {
      return armyType;
    }
  
    public boolean getIsWildcard() {
      return isWildcard;
    }
  
    // setters
    public void setName(String name) {
      this.name = name;
    }
  
    public void setTerritory(ImageView territory) {
      this.territory = territory;
    }
  
    public void setArmyName(String armyName) {
      this.armyName = armyName;
    }
  
    public void setArmyType(ImageView armyType) {
      this.armyType = armyType;
    }
    
    public void setIsWildcard(boolean isWildcard) {
      this.isWildcard = isWildcard;
    }
    
    
    public ImageView drawCard(int id) {    
      VBox b = new VBox();
      VBox box = new VBox();
      box.setMinWidth(90);
     
     
      Text label = new Text(this.name);
      StackPane stPane = new StackPane();
      stPane.getChildren().add(this.territory);
      stPane.getChildren().add(label);

      
      box.getChildren().add(this.armyType);
      box.getChildren().add(stPane);
      box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderWidths.DEFAULT)));

      b.getChildren().add(box);
      WritableImage image = b.snapshot(new SnapshotParameters(), null);
      File file = new File(this.territoryID + ".png");

      try {
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
      } catch (IOException e) {
      }
      
      ImageView imgCard = new ImageView(new Image(file.toURI().toString()));
      
      return imgCard;
    }
    
   
//    }
}
