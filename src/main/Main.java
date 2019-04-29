package main;

import java.net.URL;
import game.Game;
import game.TestGame;
import gui.BoardController;
import gui.HostGameGUIController;
import gui.HostGameLobbyController;
import gui.JoinGameLobbyController;
import gui.MultiPlayerGUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


/**
 * @author liwang Main method to invoke LoginGUI
 */
public class Main extends Application {

  public static Game g = new Game();
  public static BoardController b;
  public static HostGameLobbyController h;
  public static JoinGameLobbyController j;
  public static MultiPlayerGUIController m;
  public static Stage stage;
  // public static Game g;

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

    // final URL resource = getClass().getResource("/resources/gui/Demoth.mp3");
    // final Media media = new Media(resource.toString());
    // final MediaPlayer mediaPlayer = new MediaPlayer(media);
    // mediaPlayer.play();
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
