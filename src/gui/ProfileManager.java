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
 */

public class ProfileManager {
  public static HashMap<String, PlayerProfile> profileList;
  PlayerProfile selectedProfile;

  // Add new profile to profileList
  public static void addNewProfile(String name, int imageId) {
    readXml();
    PlayerProfile temp = new PlayerProfile(name, imageId);
    profileList.put(name, temp);
  }

  public void setSelectedProfile() {

  }

  public PlayerProfile getSelectedProfile() {
    return this.selectedProfile;
  }


  // Loads all player profiles from PlayerProfiles.xml file
  public static void readXml() {
    SAXBuilder builder = new SAXBuilder();
    File xml = new File("src\\gui\\PlayerProfiles.xml");

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

  // Saves profileList to xml
  public static void saveXml() {

    try {

      SAXBuilder builder = new SAXBuilder();
      File xml = new File("src\\gui\\PlayerProfiles.xml");

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
      xmlO.output(doc, new FileWriter("src\\gui\\PlayerProfiles.xml"));

    } catch (IOException ioe) {
      System.out.println(ioe.getMessage());
    } catch (JDOMException jde) {
      System.out.println(jde.getMessage());
    }
  }

  public static void printAllProfiles() {
    for (PlayerProfile x : profileList.values()) {
      x.printProfile();
    }
  }


}
