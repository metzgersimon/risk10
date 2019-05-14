package main;

import game.Game;
import game.GameState;
import gui.controller.AttackSubSceneController;
import gui.controller.BoardController;
import gui.controller.CardSubSceneController;
import gui.controller.FortifySubSceneController;
import gui.controller.HostGameLobbyController;
import gui.controller.JoinGameLobbyController;
import gui.controller.MultiPlayerGUIController;
import gui.controller.NetworkController;
import gui.controller.NextTurnSubSceneController;
import gui.controller.QuitGameSubSceneController;
import gui.controller.RuleBookPopUp;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import network.messages.game.LeaveGameMessage;


/**
 * @author liwang Main method to invoke LoginGUI
 */
public class Main extends Application {

  public static Game g;
  public static BoardController b;
  public static HostGameLobbyController h;
  public static JoinGameLobbyController j;
  public static MultiPlayerGUIController m;
  public static AttackSubSceneController attack;
  public static FortifySubSceneController fortify;
  public static QuitGameSubSceneController quit;
  public static CardSubSceneController cardC;
  public static RuleBookPopUp ruleBook;
  public static NextTurnSubSceneController turn;
  public static boolean session;
  
  public static Stage stage;
  public static Scene scene;
  public static Scene cards;
  public static Stage stagePanes;
  public static AnchorPane board;
  public static AnchorPane cardPane;
  
  @Override
  public void start(Stage primaryStage) {
    Font ubuntuFontLight =
        Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Ubuntu-L.ttf"), 14);
    Font ubuntuFontRegular =
        Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Ubuntu-R.ttf"), 14);
    Font ubuntuFontMedium =
        Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Ubuntu-M.ttf"), 14);

    try {

      FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/gui/CardSubScene.fxml"));
      cardPane = (AnchorPane) fxmlLoader2.load();
      Main.cardC = fxmlLoader2.getController();
      cards = new Scene(cardPane);

      AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/gui/LoginGUI.fxml"));
      scene = new Scene(root, 1280, 720);
      stage = primaryStage;
      stage.centerOnScreen();
      primaryStage.setScene(scene);
      primaryStage.setResizable(false);
      primaryStage.sizeToScene();
      primaryStage.show();

      primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

        @Override
        public void handle(WindowEvent event) {
          if (Main.g != null) {
            if (Main.g.isNetworkGame()) {
              if (!Main.g.getGameState().equals(GameState.END_GAME)) {
                LeaveGameMessage leaveMessage = new LeaveGameMessage("");
                leaveMessage.setColor(
                    NetworkController.gameFinder.getClient().getPlayer().getColorString());
                NetworkController.gameFinder.getClient().sendMessage(leaveMessage);
              }
              Main.g.setGameState(GameState.END_GAME);
            }
          }
          stagePanes.close();
          primaryStage.close();
        }
      });
      stagePanes = new Stage(StageStyle.TRANSPARENT);
      stagePanes.initModality(Modality.WINDOW_MODAL);
      stagePanes.initOwner(stage);
      stagePanes.setOpacity(0.9);
      primaryStage.setTitle("Risk");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static void main(String[] args) {
    session = false;
    launch(args);
  }
}
