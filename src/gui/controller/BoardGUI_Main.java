package gui.controller;

import java.io.IOException;
import game.Game;
import game.World;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class BoardGUI_Main extends Application {
  private Stage primaryStage;
  private static Game g = new Game();
  // private BoardGUI_Elements elements = new BoardGUI_Elements(g);

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    mainWindow();
  }

  public void mainWindow() {
    try {
      FXMLLoader loader = new FXMLLoader(BoardGUI_Main.class.getResource("/gui/BoardGUI.fxml"));
      AnchorPane pane = loader.load();

      BoardController boardController = loader.getController();
      // boardController.setMain(this, g, elements);
      // boardController.setMain(this, g);

      Scene scene = new Scene(pane);
      primaryStage.setScene(scene);

      // Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
      // primaryStage.setX((primaryScreenBounds.getWidth()-primaryStage.getWidth())/2);
      // primaryStage.setY((primaryScreenBounds.getHeight()-primaryStage.getHeight())/4);
      // primaryStage.setMaximized(true);
      primaryStage.setResizable(false);
      // primaryStage.setFullScreen(true);

      // for(Node n : p.getChildren()) {
      // Region r = (Region) n;
      // r.minWidthProperty().bind(scene.widthProperty());
      // r.minHeightProperty().bind(scene.heightProperty());
      // }


      primaryStage.show();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }


}
