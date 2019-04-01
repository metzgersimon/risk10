package gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


/**
 * @author prto Class to manage and save player profiles
 * 
 */

public class ProfileManager {
  public static HashMap<String, PlayerProfile> profileList;
  PlayerProfile selectedProfile;

  /**
   * Adds a new profile to the list
   * 
   * @author prto
   * @param name profile name
   * @param imageID id of profile image
   */
  public static void addNewProfile(String name, int imageId) {
    readXml();
    if (profileList.containsKey(name)) {
      System.out.println("Profile name already exists. Replacing...");
    } else {
      PlayerProfile profile = new PlayerProfile(name, imageId);
      profileList.put(name, profile);
      saveXml();
    }
  }

  /**
   * Deletes a profile from list
   * 
   * @author prto
   * @param name of profile to be deleted
   */
  public static void deleteProfile(String name) {
    readXml();
    profileList.remove(name);
    saveXml();
  }

  /**
   * Updates existing profile with new name and/or image id
   * 
   * @author prto
   * @param oldName old profile name
   * @param newName new profile name
   * @param imageID new image id
   */
  public static void editProfile(String oldName, String newName, int imageId) {
    readXml();
    PlayerProfile profile = profileList.get(oldName);
    profile.profileName = newName;
    profile.imageId = imageId;
    profileList.remove(oldName);
    profileList.put(newName, profile);
    saveXml();
  }

  /**
   * Sets the current profile
   * 
   * @author prto
   * @param key name of profile to be selected
   */
  public void setSelectedProfile(String key) {
    selectedProfile = profileList.get(key);
  }

  /**
   * Returns the selected profile
   * 
   * @return currently selected player profile
   */
  public PlayerProfile getSelectedProfile() {
    return this.selectedProfile;
  }

  /**
   * Creates PlayerProfiles.xml in same directory as .jar in case it doesn't already exist
   * 
   * @author prto
   */
  public static void checkXml() {
    File tester = new File("./PlayerProfiles.xml");
    if (!tester.exists()) {
      System.out.println("PlayerProfiles.xml does not exist. Creating file...");
      Element root = new Element("risk10");
      Document doc = new Document(root);
      XMLOutputter xmlO = new XMLOutputter();
      xmlO.setFormat(Format.getPrettyFormat());
      try {
        xmlO.output(doc, new FileWriter("./PlayerProfiles.xml"));
        System.out.println("new PlayerProfiles.xml has been created");
      } catch (IOException ioe) {
        System.out.println(ioe.getMessage());
      }
    }
  }


  /**
   * Loads all player profiles from PlayerProfiles.xml file and saves them in profileList
   * 
   * @author prto
   */
  public static void readXml() {
    checkXml();
    SAXBuilder builder = new SAXBuilder();
    File xml = new File("./PlayerProfiles.xml");
    try {
      Document doc = (Document) builder.build(xml);
      Element root = doc.getRootElement();
      List<Element> profiles = root.getChildren();
      profileList = new HashMap<String, PlayerProfile>();

      for (int i = 0; i < profiles.size(); i++) {

        Element profile = (Element) profiles.get(i);

        String name = profile.getChildText("name");
        int imageId = Integer.parseInt(profile.getChildText("imageId"));
        int matchesPlayed = Integer.parseInt(profile.getChildText("matchesPlayed"));
        int matchesWon = Integer.parseInt(profile.getChildText("matchesWon"));
        int matchesLost = Integer.parseInt(profile.getChildText("matchesLost"));
        int territoriesConquered = Integer.parseInt(profile.getChildText("territoriesConquered"));

        PlayerProfile temp = new PlayerProfile(name, imageId, matchesPlayed, matchesWon,
            matchesLost, territoriesConquered);
        profileList.put(name, temp);
      }

    } catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    } catch (JDOMException jde) {
      System.out.println(jde.getMessage());
    }
  }

  /**
   * Saves all profiles from profileList into PlayerProfiles.xml
   * 
   * @author prto
   */
  public static void saveXml() {

    checkXml();
    try {

      SAXBuilder builder = new SAXBuilder();
      File xml = new File("./PlayerProfiles.xml");

      Document doc = (Document) builder.build(xml);
      Element root = doc.getRootElement();
      root.removeContent();
      Element player;


      // Write elements
      for (PlayerProfile x : profileList.values()) {
        player = new Element("Player");
        player.addContent(new Element("name").setText(x.getName()));
        player.addContent(new Element("imageId").setText(x.getId()));
        player.addContent(new Element("matchesPlayed").setText(x.getMatchesPlayed()));
        player.addContent(new Element("matchesWon").setText(x.getMatchesWon()));
        player.addContent(new Element("matchesLost").setText(x.getMatchesLost()));
        player.addContent(new Element("territoriesConquered").setText(x.getTerritoriesConquered()));
        root.addContent(player);
      }


      // Save to xml
      XMLOutputter xmlO = new XMLOutputter();
      xmlO.setFormat(Format.getPrettyFormat());
      xmlO.output(doc, new FileWriter("./PlayerProfiles.xml"));

    } catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    } catch (JDOMException jde) {
      System.out.println(jde.getMessage());
    }
  }

  /**
   * Prints all profiles from profileList into the console
   * 
   * @author prto
   */
  public static void printAllProfiles() {
    for (PlayerProfile x : profileList.values()) {
      x.printProfile();
    }
  }


}
