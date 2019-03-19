package gui;

/**
 * @author prto Represents player profile Contains Methods to increment profile statistics Profiles
 *         and their changes will be saved in local XML file
 */

public class PlayerProfile {

  public String profileName;
  public int imageID;
  private int matchesPlayed;
  private int matchesWon;
  private int matchesLost;
  private int territoriesConquered;

  // Constructor is used when a new player profile is created
  public PlayerProfile(String name, int imageID) {
    this.profileName = name;
    this.imageID = imageID;
    this.matchesPlayed = 0;
    this.matchesWon = 0;
    this.matchesLost = 0;
    this.territoriesConquered = 0;
  }
  
  //This constructor is used when the game loads data from the XML file
  public PlayerProfile(String name, int imageID, int mP, int mW, int mL, int tC) {
    this.profileName = name;
    this.imageID = imageID;
    this.matchesPlayed = mP;
    this.matchesWon = mW;
    this.matchesLost = mL;
    this.territoriesConquered = tC;
  }

  // Name change method is used when an existing player profile name is being changed
  public void changePlayerNameTo(String name) {
    this.profileName = name;
  }

  // Image ID change method is used when an existing player profile image is being changed
  public void changePlayerImageID(int id) {
    this.imageID = id;
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

  // TODO: add function to update XML file after updating profile stats

}
