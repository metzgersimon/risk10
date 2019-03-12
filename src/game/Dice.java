package game;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Dice {
  
	private String diceType;
	private int numberOfDices;
	private ImageView[] sidesOfDice = new ImageView[6];

	
	public Dice(String diceType) {
		this.diceType = diceType;
	}
	
	
	//getters
    public String getDiceType() {
      return diceType;
    }

    public int getNumberOfDices() {
      return numberOfDices;
    }
  
    public ImageView[] getSidesOfDice() {
      return sidesOfDice;
    }
  
    //setters
    public void setDiceType(String diceType) {
      this.diceType = diceType;
    }
  
    public void setNumberOfDices(int numberOfDices) {
      this.numberOfDices = numberOfDices;
    }
  
    public void setSidesOfDice(ImageView[] sidesOfDice) {
      this.sidesOfDice = sidesOfDice;
    }

    public ImageView changeColor(Image diceImage) {
      HBox hbox = new HBox();
      int height = (int) diceImage.getHeight();
      int width = (int) diceImage.getWidth();
      
      ImageView image = new ImageView(diceImage);
      hbox.getChildren().add(image);
      
      Lighting lighting = new Lighting();
      
      lighting.setDiffuseConstant(1.0);
      lighting.setSpecularConstant(0.0);
      lighting.setSpecularExponent(0.0);
      lighting.setSurfaceScale(0.0);
      lighting.setLight(new Light.Distant(45, 45, Color.RED));
      
      image.setEffect(lighting);
      
      return image;
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
