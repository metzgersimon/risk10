package gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

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

  // This constructor is used when a new player profile is created
  public PlayerProfile(String name, int imageId) {
    this.profileName = name;
    this.imageId = imageId;
    this.matchesPlayed = 0;
    this.matchesWon = 0;
    this.matchesLost = 0;
    this.territoriesConquered = 0;
  }

  // This constructor is used when the game loads data from the XML file
  public PlayerProfile(String name, int imageId, int mp, int mw, int ml, int tc) {
    this.profileName = name;
    this.imageId = imageId;
    this.matchesPlayed = mp;
    this.matchesWon = mw;
    this.matchesLost = ml;
    this.territoriesConquered = tc;
  }

  // Name change method is used when an existing player profile name is being changed
  public void changePlayerNameTo(String name) {
    this.profileName = name;
  }

  // Image ID change method is used when an existing player profile image is being changed
  public void changePlayerimageId(int id) {
    this.imageId = id;
  }


  // The following methods are used to update profile game statistics such as total number of
  // matches played etc.
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


  // get-methods
  public String getName() {
    return this.profileName;
  }

  public String getId() {
    return "" + this.imageId;
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

  // set-methods for private variables

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


  // Returns to imageId corresponding Image
  public javafx.scene.image.Image getImage(int imageId) {
    Image image = null;
    try {
      switch (imageId) {
        case 0:
          image = ImageIO.read((new File("resources\\avatars\\0.jpg")));
        case 1:
          image = ImageIO.read((new File("resources\\avatars\\1.jpg")));
        case 2:
          image = ImageIO.read((new File("resources\\avatars\\2.jpg")));
        case 3:
          image = ImageIO.read((new File("resources\\avatars\\3.jpg")));
        case 4:
          image = ImageIO.read((new File("resources\\avatars\\4.jpg")));
        case 5:
          image = ImageIO.read((new File("resources\\avatars\\5.jpg")));
        case 6:
          image = ImageIO.read((new File("resources\\avatars\\6.jpg")));
        case 7:
          image = ImageIO.read((new File("resources\\avatars\\7.jpg")));
        case 8:
          image = ImageIO.read((new File("resources\\avatars\\8.jpg")));
        case 9:
          image = ImageIO.read((new File("resources\\avatars\\9.jpg")));
        case 10:
          image = ImageIO.read((new File("resources\\avatars\\10.jpg")));
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return SwingFXUtils.toFXImage((BufferedImage) image, null);
  }


  // Test print of profile data
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
