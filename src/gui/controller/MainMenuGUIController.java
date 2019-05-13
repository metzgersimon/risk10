package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller class for MainMenuGUI
 * 
 * @author liwang
 */
public class MainMenuGUIController {

  /**
   * singlePlayer or multiPlayer
   */
  public static String mode;

  @FXML
  private Button singlePlayer;
  @FXML
  private Button multiPlayer;
  @FXML
  private Button exitGame;

  /**
   * Event handle class invoked when the single Player or multi Player Button clicked to choose the
   * player profile
   * 
   * @author liwang
   * @param event
   */
  @FXML
  void openProfile(ActionEvent event) {
    mode = (String) ((Node) event.getSource()).getId();
    try {
      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/gui/ProfileSelectionGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Event handle class invoked when the settings Button clicked to open the SettingGUI
   * 
   * @author liwang
   * @param event
   */
  @FXML
  void openSettings(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/SettingsGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      System.out.println("Can't load SettingsGUI.fxml");
      e.printStackTrace();
    }
  }
  
  /**
   * @author prto closes the game
   */
  public void exitGame() {
    main.Main.stagePanes.close();
    main.Main.stage.close();
  }

}
