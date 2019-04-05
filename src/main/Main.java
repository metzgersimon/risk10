package main;

import game.Game;
import gui.BoardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * @author liwang Main method to invoke LoginGUI
 */
public class Main extends Application {
  public static Game g = new Game();
  BoardController b = new BoardController();

  public static Stage stage ;

  @Override
  public void start(Stage primaryStage) {
    try {
      AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/gui/LoginGUI.fxml"));
      Scene scene = new Scene(root, 1280, 720);
      stage = primaryStage;
      primaryStage.setScene(scene);
      primaryStage.setResizable(false);
      primaryStage.sizeToScene();
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
