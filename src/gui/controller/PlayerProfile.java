package gui.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import main.Parameter;

/**
 * @author prto Represents player profile Contains Methods to increment profile statistics Profiles
 *         and their changes will be saved in local XML file
 */

public class PlayerProfile {

  public String profileName;
  public int imageId;
  private int matchesPlayed;
  private int matchesWon;
  private int matchesLost;
  private int territoriesConquered;
  private int sessionWins;

  // This constructor is used when a new player profile is created
  public PlayerProfile(String name, int imageId) {
    this.profileName = name;
    this.imageId = imageId;
    this.matchesPlayed = 0;
    this.matchesWon = 0;
    this.matchesLost = 0;
    this.territoriesConquered = 0;
    this.sessionWins = 0;
  }

  // This constructor is used when the game loads data from the XML file
  public PlayerProfile(String name, int imageId, int mp, int mw, int ml, int tc) {
    this.profileName = name;
    this.imageId = imageId;
    this.matchesPlayed = mp;
    this.matchesWon = mw;
    this.matchesLost = ml;
    this.territoriesConquered = tc;
    this.sessionWins = 0;
  }


  /**
   * Method to change the profile name of an existing profile.
   * 
   * @author prto
   * @param name
   */
  public void changePlayerNameTo(String name) {
    this.profileName = name;
  }

 
  /**
   * Changes the Image ID of an existing profile.
   * 
   * @author prto
   * @param id
   */
  public void changePlayerimageId(int id) {
    this.imageId = id;
  }

  /**
   * get methods.
   * 
   * @author prto
   */
  public String getName() {
    return this.profileName;
  }

  public String getId() {
    return "" + this.imageId;
  }

  public int getIdInt() {
    return this.imageId;
  }

  public String getMatchesPlayed() {
    return "" + this.matchesPlayed;
  }

  public String getMatchesWon() {
    return "" + this.matchesWon;
  }

  public String getMatchesLost() {
    return "" + this.matchesLost;
  }

  public String getTerritoriesConquered() {
    return "" + this.territoriesConquered;
  }

  public String getSessionWins() {
    return "" + this.sessionWins;
  }

  
  /**
   * Methods used to update profile statistics post-game.
   * 
   * @author prto
   */
  public void incrementMatchesPlayed() {
    this.matchesPlayed++;
  }

  public void incrementMatchesWon() {
    this.matchesWon++;
  }

  public void incrementMatchesLost() {
    this.matchesLost++;
  }

  public void incrementTerritoriesConquered(int n) {
    this.territoriesConquered += n;
  }

  public void incrementSessionWins() {
    this.sessionWins++;
  }
  
  public void resetSessionWins() {
    this.sessionWins = 0;
  }


  /**
   * Setter methods.
   * 
   * @author prto
   */
  public void setMatchesPlayed(int mp) {
    this.matchesPlayed = mp;
  }

  public void setMatchesLost(int ml) {
    this.matchesLost = ml;
  }

  public void setMatchesWon(int mw) {
    this.matchesWon = mw;
  }

  public void setTerritoriesConquered(int tc) {
    this.territoriesConquered = tc;
  }

  public void setSessionWins(int sw) {
    this.sessionWins = sw;
  }


 
  /**
   * Return to imageId corresponding Image.
   * 
   * @author prto
   * @return Image from current ImageId
   */
  public javafx.scene.image.Image getImage() {
    BufferedImage image = null;
    try {
      switch (this.imageId) {
        case 0:
          image = ImageIO.read(getClass().getResource("/resources/avatar/0.jpg"));
          break;
        case 1:
          image = ImageIO.read(getClass().getResource("/resources/avatar/1.jpg"));
          break;
        case 2:
          image = ImageIO.read(getClass().getResource("/resources/avatar/2.jpg"));
          break;
        case 3:
          image = ImageIO.read(getClass().getResource("/resources/avatar/3.jpg"));
          break;
        case 4:
          image = ImageIO.read(getClass().getResource("/resources/avatar/4.jpg"));
          break;
        case 5:
          image = ImageIO.read(getClass().getResource("/resources/avatar/5.jpg"));
          break;
        case 6:
          image = ImageIO.read(getClass().getResource("/resources/avatar/6.jpg"));
          break;
        case 7:
          image = ImageIO.read(getClass().getResource("/resources/avatar/7.jpg"));
          break;
        case 8:
          image = ImageIO.read(getClass().getResource("/resources/avatar/8.jpg"));
          break;
        case 9:
          image = ImageIO.read(getClass().getResource("/resources/avatar/9.jpg"));
          break;
        case 10:
          image = ImageIO.read(getClass().getResource("/resources/avatar/10.jpg"));
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
    }
    //System.out.println("loading image for " + this.profileName);//
    return SwingFXUtils.toFXImage(image, null);
  }

  
  /**
   * Prints profileData.
   * 
   * @author prto
   */
  public void printProfile() {
    System.out.println("Name:\t" + this.profileName);
    System.out.println("imageId:\t" + this.imageId);
    System.out.println("Matches played:\t" + this.matchesPlayed);
    System.out.println("Matches won:\t" + this.matchesWon);
    System.out.println("Matches lost:\t" + this.matchesLost);
    System.out.println("Territories conquered:\t" + this.territoriesConquered);
    System.out.println();
  }
}
