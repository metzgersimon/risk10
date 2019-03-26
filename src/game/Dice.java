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
  private ImageView[] redDice = new ImageView[6];
  private ImageView[] blueDice = new ImageView[6];
  private Image[] sidesOfDice = new Image[6];


  public Dice(String diceType) {
    this.diceType = diceType;
    for (int i = 0; i < sidesOfDice.length; i++) {
      sidesOfDice[i] = new Image("dice_" + (i + 1) + ".png");
    }
  }


  // getters

  public String getDiceType() {
    return diceType;
  }

  public int getNumberOfDices() {
    return numberOfDices;
  }

  public Image[] getSidesOfDice() {
    return sidesOfDice;
  }

  public ImageView[] getRedDice() {
    return redDice;
  }

  public ImageView[] getBlueDice() {
    return blueDice;
  }


  // setters
  public void setDiceType(String diceType) {
    this.diceType = diceType;
  }

  public void setNumberOfDices(int numberOfDices) {
    this.numberOfDices = numberOfDices;
  }

  public void setSidesOfDice(Image[] sidesOfDice) {
    this.sidesOfDice = sidesOfDice;
  }


  public void setRedDice(ImageView[] redDice) {
    
    this.redDice = redDice;
  }


  public void setBlueDice(ImageView[] blueDice) {
    this.blueDice = blueDice;
  }


  /**
   * Method changes the color of a given dice image to a chosen color
   * 
   * @param diceImage an image of a face of a dice with a value between 1 and 6
   * @param color represents a color to color the white dice image in
   * @return diceImage white image colored in the color which is saved in param color
   */
  public ImageView changeColor(Image diceImage, Color color) {
    HBox hbox = new HBox();

    ImageView image = new ImageView(diceImage);
    hbox.getChildren().add(image);

    Lighting lighting = new Lighting();

    lighting.setSpecularConstant(0.0);
    lighting.setDiffuseConstant(5.0);
    lighting.setLight(new Light.Distant(100, 100, color));

    image.setEffect(lighting);

    return image;
  }

  /**
   * Method colors given white dice images in either red (for the attacking dices) or blue (for the
   * defending dices) and saves them in project
   */
  public void drawImages() {
    HBox box;
    Image img;
    ImageView imgView;
    WritableImage imgDone;
    File file;

    for (int i = 1; i <= 6; i++) {
      box = new HBox();
      img = new Image("dice_" + i + ".png");
      imgView = changeColor(img, Color.RED);
      imgView.setFitHeight(30);
      imgView.setFitWidth(30);

      box.getChildren().add(imgView);
      imgDone = box.snapshot(new SnapshotParameters(), null);
      file = new File("dice_" + i + "RED.png");

      try {
        ImageIO.write(SwingFXUtils.fromFXImage(imgDone, null), "png", file);
      } catch (IOException e) {

      }

    }

    for (int i = 1; i <= 6; i++) {
      box = new HBox();
      img = new Image("dice_" + i + ".png");
      imgView = changeColor(img, Color.AQUA);
      imgView.setFitHeight(30);
      imgView.setFitWidth(30);

      box.getChildren().add(imgView);
      imgDone = box.snapshot(new SnapshotParameters(), null);
      file = new File("dice_" + i + "BLUE.png");

      try {
        ImageIO.write(SwingFXUtils.fromFXImage(imgDone, null), "png", file);
      } catch (IOException e) {

      }

    }

  }


  /**
   * Method computes a random number from 1-6 to simulate a single dice roll
   * 
   * @return an int number which is calculated randomly
   */
  public int rollSingleDice() {
    return (int) ((Math.random() * 6 + 1));
  }

  /**
   * 
   * @param numberOfDices represents the number of dices the player chooses to roll
   * @return the rolled values from
   */

  public int[] rollDices(int numberOfDices) {
    int[] rolledNumbers = new int[numberOfDices];
    for (int i = 0; i < rolledNumbers.length; i++) {
      rolledNumbers[i] = rollSingleDice();
    }
    return rolledNumbers;
  }

  /**
   * Method calculates a random number with the method rollSingleDice and returns the dice image
   * which is defined by this random number
   * 
   * @return an ImageView which contains a random dice image
   */
  public ImageView displayThrowDice() {
    int number = rollSingleDice();
    Image imgNumber = new Image("dice_" + number + ".png");
    ImageView img = new ImageView(imgNumber);
    return img;
  }



}
