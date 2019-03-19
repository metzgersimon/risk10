package gui;

import java.io.File;
import java.io.IOException;
import org.jdom2.*;
import org.jdom2.input.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prto
 * Class to manage multiple player profiles
 */

public class ProfileManager {

  
  public void createNewProfile(String name, int imageID) {
  }
  

  //Loads all player profiles from PlayerProfiles.xml file
  public void readXML() {
    SAXBuilder builder = new SAXBuilder();
    File xml = new File("PlayerProfiles.xml");
    
    try {
      Document doc = (Document) builder.build(xml);
      Element root = doc.getRootElement();
      List profiles = root.getChildren();
      Map<String,PlayerProfile> playerList = new HashMap<String,PlayerProfile>();
      
      for(int i = 0; i < profiles.size(); i++) {
        
        Element profile = (Element) profiles.get(i);
        
        String name = profile.getChildText("name");
        int imageID = Integer.parseInt(profile.getChildText("imageID"));
        int matchesPlayed = Integer.parseInt(profile.getChildText("matchesPlayed"));
        int matchesWon = Integer.parseInt(profile.getChildText("matchesWon"));
        int matchesLost = Integer.parseInt(profile.getChildText("matchesLost"));
        int territoriesConquered = Integer.parseInt(profile.getChildText("territoriesConquered"));
        
        PlayerProfile temp = new PlayerProfile(name, imageID, matchesPlayed, matchesWon, matchesLost, territoriesConquered);
        playerList.put(name, temp);
      }
      
    } catch(IOException ioe) {
      System.out.println(ioe.getMessage());
    } catch(JDOMException jde) {
      System.out.println(jde.getMessage());
    }
  }
  
}
