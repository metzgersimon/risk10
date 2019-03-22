package main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import gui.ProfileManager;
import gui.PlayerProfile;


/**
 * @author liwang Main method to invoke LoginGUI
 */
public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {
    try {
      BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/gui/LoginGUI.fxml"));
      Scene scene = new Scene(root, 1000, 800);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Login");
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);

    // Testing of xml reader and writer
    // ProfileManager.readXml();
    // ProfileManager.printAllProfiles();
    // ProfileManager.addNewProfile("Susan", 8);
    // System.out.println("***************Adding Susan*****************");
    // ProfileManager.printAllProfiles();
    // System.out.println("Saving...");
    // ProfileManager.saveXml();
    // System.out.println("Reading...");
    // ProfileManager.readXml();
    // System.out.println("******Printing all profiles again*******");
    // ProfileManager.printAllProfiles();
  }
}
