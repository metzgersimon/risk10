package game;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

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
