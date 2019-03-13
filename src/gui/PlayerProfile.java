package gui;

/**
 * @author prto
 * Represents player profile
 * Contains Methods to increment profile statistics
 * Profiles and their changes will be saved in local XML file
 */

public class PlayerProfile {

    public String profileName;
    public int profileImageID;
    private int matchesPlayed;
    private int matchesWon;
    private int matchesLost;
    private int territoriesConquered;
     
    public PlayerProfile(String name, int imageID) {
      this.profileName = name;
      this.profileImageID = imageID;
      this.matchesPlayed = 0;
      this.matchesWon = 0;
      this.matchesLost = 0;
    }
    
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
    
    //TODO: add function to update XML file after updating profile stats
    
}
