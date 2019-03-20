package gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author prto
 * Class to manage multiple player profiles
 */

public class ProfileManager {
  public static HashMap<String,PlayerProfile> profileList;
  
  //Add new profile to profileList
  public void addNewProfile(String name, int imageID) {
    readXML();
    PlayerProfile temp = new PlayerProfile(name,imageID);
    profileList.put(name,temp);
  }
  

  //Loads all player profiles from PlayerProfiles.xml file
  public void readXML() {
    SAXBuilder builder = new SAXBuilder();
    File xml = new File("PlayerProfiles.xml");
    
    try {
      Document doc = (Document) builder.build(xml);
      Element root = doc.getRootElement();
      List<Element> profiles = root.getChildren();
      profileList = new HashMap<String,PlayerProfile>();
      
      for(int i = 0; i < profiles.size(); i++) {
        
        Element profile = (Element) profiles.get(i);
        
        String name = profile.getChildText("name");
        int imageID = Integer.parseInt(profile.getChildText("imageID"));
        int matchesPlayed = Integer.parseInt(profile.getChildText("matchesPlayed"));
        int matchesWon = Integer.parseInt(profile.getChildText("matchesWon"));
        int matchesLost = Integer.parseInt(profile.getChildText("matchesLost"));
        int territoriesConquered = Integer.parseInt(profile.getChildText("territoriesConquered"));
        
        PlayerProfile temp = new PlayerProfile(name, imageID, matchesPlayed, matchesWon, matchesLost, territoriesConquered);
        profileList.put(name, temp);
      }
      
    } catch(IOException ioe) {
      System.out.println(ioe.getMessage());
    } catch(JDOMException jde) {
      System.out.println(jde.getMessage());
    }
  }
  
  public void saveXML() {
    try {
      Element root = new Element("risk10");
      Document doc = new Document(root);
      doc.setRootElement(root);
      Element tempProfile; 
      

      for(PlayerProfile x : profileList.values()) {
        //ToDo
      }
      
      //Save to xml
      XMLOutputter xml = new XMLOutputter();
      xml.setFormat(Format.getPrettyFormat());
      xml.output(doc,new FileWriter("PlayerProfiles.xml"));
      
    } catch(IOException ioe) {
      System.out.println(ioe.getMessage());
    }
  }
  
}
