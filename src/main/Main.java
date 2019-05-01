package main;

import java.net.URL;
import game.Game;
import game.TestGame;
import gui.ArmyDistributionSubSceneController;
import gui.AttackSubSceneController;
import gui.BoardController;
import gui.FortifySubSceneController;
import gui.HostGameGUIController;
import gui.HostGameLobbyController;
import gui.JoinGameLobbyController;
import gui.MultiPlayerGUIController;
import gui.QuitGameSubSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import network.messages.game.AttackMessage;


/**
 * @author liwang Main method to invoke LoginGUI
 */
public class Main extends Application {

  public static Game g;// = new TestGame();
  public static BoardController b;
  public static HostGameLobbyController h;
  public static JoinGameLobbyController j;
  public static MultiPlayerGUIController m;
  public static ArmyDistributionSubSceneController army;
  public static AttackSubSceneController attack;
  public static FortifySubSceneController fortify;
  public static QuitGameSubSceneController quit;

  public static Stage stage;
  public static Scene sceneMain;
  public static Scene scenePanes;
  public static Scene boardScene;
  // public static Game g;

  @Override
  public void start(Stage primaryStage) {
    try {
      Font ubuntuFontLight = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Ubuntu-L.ttf"), 14);
      Font ubuntuFontRegular = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Ubuntu-L.ttf"), 14);
      Font ubuntuFontMedium = Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Ubuntu-L.ttf"), 14);
      AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/gui/LoginGUI.fxml"));
      scenePanes = new Scene(new Pane(), 1024, 720);
      sceneMain = new Scene(root, 1280, 720);
      stage = primaryStage;
      primaryStage.setScene(sceneMain);
      primaryStage.setResizable(false);
      primaryStage.sizeToScene();
      primaryStage.setTitle("Login");
      primaryStage.show();

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/BoardGUI.fxml"));
      AnchorPane board = (AnchorPane) fxmlLoader.load();
      this.b = fxmlLoader.getController();
      boardScene = new Scene(board);

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
