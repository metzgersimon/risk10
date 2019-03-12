package gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BoardGUI_Main extends Application {
  private Stage primaryStage;

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
      FXMLLoader loader = new FXMLLoader(BoardGUI_Main.class.getResource("BoardGUI.fxml"));
      AnchorPane pane  = loader.load();
      
     //primaryStage.setMinHeight(800.00);
     //primaryStage.setMinWidth(1000.00);
      
      BoardController boardController = loader.getController();
      //boardController.setMain(this);
      
      Scene scene = new Scene(pane);
      primaryStage.setScene(scene);
      
      primaryStage.show();
      
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  
}
