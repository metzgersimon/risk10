package game;
import org.junit.Test;
import gui.controller.ProfileManager;
import static org.junit.Assert.assertEquals;


public class TestProfiles {
  
  String testName1 = "Tester1";
  int testID1 = 1;
  
  String testName2 = "Tester2";
  int testID2 = 2;
  
  String testName3 = "Tester3";
  int testID3 = 3;
  
  @Test
  public void testWriteAndLoadProfiles() {
    ProfileManager.addNewProfile(testName1, testID1);
    ProfileManager.saveXml();
    ProfileManager.readXml();
    
    ProfileManager.setSelectedProfile(testName1);
    assertEquals(testName1, ProfileManager.getSelectedProfile());
    assertEquals(testID1, ProfileManager.getSelectedProfile());
    
    ProfileManager.setSelectedProfile(testName2);
    assertEquals(testName2, ProfileManager.getSelectedProfile());
    assertEquals(testID2, ProfileManager.getSelectedProfile());
    
    ProfileManager.setSelectedProfile(testName3);
    assertEquals(testName3, ProfileManager.getSelectedProfile());
    assertEquals(testID3, ProfileManager.getSelectedProfile());
  }
  
  

}
