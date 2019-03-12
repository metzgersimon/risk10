package game;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Dice {
  
	private String diceType;
	private int numberOfDices;
	private Image[] sidesOfDice = new Image[6];

	
	public Dice(String diceType) {
		this.diceType = diceType;
		for(int i = 0; i < sidesOfDice.length; i++) {
          sidesOfDice[i] = new Image("dice_"+(i+1)+".png");
        }
	}
	
	
	//getters
    public String getDiceType() {
      return diceType;
    }

    public int getNumberOfDices() {
      return numberOfDices;
    }
  
    public Image[] getSidesOfDice() {
      return sidesOfDice;
    }
  
    //setters
    public void setDiceType(String diceType) {
      this.diceType = diceType;
    }
  
    public void setNumberOfDices(int numberOfDices) {
      this.numberOfDices = numberOfDices;
    }
  
    public void setSidesOfDice(Image[] sidesOfDice) {
      this.sidesOfDice = sidesOfDice;
    }

    public ImageView changeColor(Image diceImage, Color color) {
      HBox hbox = new HBox();
      
      ImageView image = new ImageView(diceImage);
      hbox.getChildren().add(image);
      
      Lighting lighting = new Lighting();
      
      lighting.setSpecularConstant(0.0);
      lighting.setDiffuseConstant(5.0);
//      if(this.diceType.equalsIgnoreCase("Angreifer")){
//        lighting.setLight(new Light.Distant(100, 100, Color.RED));
//      }
//      else if(this.diceType.equalsIgnoreCase("Verteidiger")) {
//        lighting.setLight(new Light.Distant(100, 100, Color.AQUA));
//      }
//      else {
//      }
      lighting.setLight(new Light.Distant(100, 100, color));
     
      image.setEffect(lighting);
      
      return image;
    }
    
    public void drawImages() {
      HBox box;
      Image img;
      ImageView imgView;
      WritableImage imgDone;
      File file;
      
      for(int i = 1; i <= 6; i++) {
        box = new HBox();
        img = new Image("dice_"+i+".png");
        imgView = changeColor(img, Color.RED);
        imgView.setFitHeight(30);
        imgView.setFitWidth(30);
        
        box.getChildren().add(imgView);
        imgDone = box.snapshot(new SnapshotParameters(), null);
        file = new File("dice_"+i+"RED.png");
        
        try {
          ImageIO.write(SwingFXUtils.fromFXImage(imgDone, null), "png", file);
        }catch(IOException e) {
          
        }
        
      }
      
      for(int i = 1; i <= 6; i++) {
        box = new HBox();
        img = new Image("dice_"+i+".png");
        imgView = changeColor(img, Color.AQUA);
        imgView.setFitHeight(30);
        imgView.setFitWidth(30);
        
        box.getChildren().add(imgView);
        imgDone = box.snapshot(new SnapshotParameters(), null);
        file = new File("dice_"+i+"BLUE.png");
        
        try {
          ImageIO.write(SwingFXUtils.fromFXImage(imgDone, null), "png", file);
        }catch(IOException e) {
          
        }
        
      }
      
    }


    public  int rollSingleDice() {
  		return (int)((Math.random()*6+1));
  	}
	
	public int[] rollDices(int numberOfDices) {
		int[] rolledNumbers = new int[numberOfDices];
		for(int i = 0; i < rolledNumbers.length; i++) {
			rolledNumbers[i] = rollSingleDice();
		}
		return rolledNumbers;	
	}
	
	public ImageView draw() {
	  int number = rollSingleDice();
	  Image imgNumber = new Image("dice_"+number+".png");
	  ImageView img = new ImageView(imgNumber);
	  return img;
	}

	 
	
	
	
}
