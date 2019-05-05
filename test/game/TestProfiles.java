package game;
import org.junit.Test;
import gui.controller.ProfileManager;
import static org.junit.Assert.assertEquals;


public class TestProfiles {
  
  String testName1 = "Tester1";
  int testId1 = 1;
  
  String testName2 = "Tester2";
  int testId2 = 2;
  
  String testName3 = "Tester3";
  int testId3 = 3;
  
  String testName4 = "Tester4";
  int testId4 = 4;
  
  String testName5 = "Tester5";
  int testId5 = 5;
  
  String testName6 = "Tester6";
  int testId6 = 6;
  
  String testName7 = "Tester7";
  int testId7 = 7;
  
  
  @Test
  public void testWriteAndLoadProfiles() {
    
    ProfileManager.addNewProfile(testName1, testId1);
    ProfileManager.saveXml();
    ProfileManager.readXml();
    
    ProfileManager.setSelectedProfile(testName1);
    assertEquals(testName1, ProfileManager.getSelectedProfile().getName());
    assertEquals(testId1, ProfileManager.getSelectedProfile().getIdInt());
    
    ProfileManager.addNewProfile(testName2, testId2);
    ProfileManager.setSelectedProfile(testName2);
    assertEquals(testName2, ProfileManager.getSelectedProfile().getName());
    assertEquals(testId2, ProfileManager.getSelectedProfile().getIdInt());
    
    ProfileManager.addNewProfile(testName3, testId3);
    ProfileManager.setSelectedProfile(testName3);
    assertEquals(testName3, ProfileManager.getSelectedProfile().getName());
    assertEquals(testId3, ProfileManager.getSelectedProfile().getIdInt());
  
    ProfileManager.deleteProfile(testName1);
    ProfileManager.deleteProfile(testName2);
    ProfileManager.deleteProfile(testName3);
  }
  
  @Test
  public void testEditProfiles() {
    
    ProfileManager.addNewProfile(testName4, testId4);
    ProfileManager.saveXml();
    ProfileManager.readXml();

    ProfileManager.setSelectedProfile(testName4);
    ProfileManager.editProfile(testName4, testName5, testId5);
    
    ProfileManager.saveXml();
    ProfileManager.readXml();
    
    ProfileManager.setSelectedProfile(testName5);
    
    assertEquals(testName5, ProfileManager.getSelectedProfile().getName());
    assertEquals(testId5, ProfileManager.getSelectedProfile().getIdInt());
    
    ProfileManager.deleteProfile(testName5);
  }
  
  @Test
  public void testDeleteProfiles() {
   
    ProfileManager.addNewProfile(testName6, testId6);
    ProfileManager.addNewProfile(testName7, testId7);
    
    ProfileManager.saveXml();
    ProfileManager.readXml();
    
    ProfileManager.deleteProfile(testName7);
    
    ProfileManager.saveXml();
    ProfileManager.readXml();
    
    ProfileManager.setSelectedProfile(testName6);
    assertEquals(1, ProfileManager.profileList.size());
    assertEquals(testName6, ProfileManager.getSelectedProfile().getName());
  
    ProfileManager.deleteProfile(testName6);
  }
  
  

}
