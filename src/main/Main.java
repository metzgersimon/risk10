package main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * @author liwang Main method to invoke LoginGUI
 */
public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {
    try {
      BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/gui/LoginGUI.fxml"));
      Scene scene = new Scene(root, 1000, 800);
      // scene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setTitle("Login");
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
