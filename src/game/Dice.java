package game;


import java.util.Collections;
import java.util.Vector;



/**
 * Class defines a Dice which is used for the attacking phase, it represents both, attacking and
 * defending dices
 * 
 * @author smetzger
 *
 */
public class Dice {

  private int numberOfDices;

  /**
   * getter and setter method for the only attribute numberOfDices
   */

  public int getNumberOfDices() {
    return numberOfDices;
  }

  public void setNumberOfDices(int numberOfDices) {
    this.numberOfDices = numberOfDices;
  }


  /**
   * Method computes a random number from 1-6 to simulate a single dice roll
   * 
   * @return an int number which is calculated randomly
   */
  public static int rollSingleDice() {
    return (int) ((Math.random() * 6 + 1));
  }

  /**
   * Method generates an array of int numbers which are computed through the rollSingleDice method.
   * Each number in the array represents a number thrown by the player
   * 
   * @param numberOfDices represents the number of dices the player chooses to roll
   * @return a vector with the rolled numbers
   */

  public static Vector<Integer> rollDices(int numberOfDices) {
    System.out.println("Number of Dices: " + numberOfDices);
    Vector<Integer> rolledNumbers = new Vector<Integer>();
    for (int i = 0; i < numberOfDices; i++) {
      rolledNumbers.add(rollSingleDice());
    }
    Collections.sort(rolledNumbers, Collections.reverseOrder());
    return rolledNumbers;
  }
}
