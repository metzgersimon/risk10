package game;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

  /**
   * Prints 'true' if TestProfiles.java ran successfully without errors
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
