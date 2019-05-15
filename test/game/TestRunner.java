package game;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

  /**
   * Tests TestProfiles and outputs the result in the console
   * 
   * @author prto
   * @param args
   */
  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(TestProfiles.class);
    
    for(Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }
    
    System.out.println(result.wasSuccessful());

  }

}
