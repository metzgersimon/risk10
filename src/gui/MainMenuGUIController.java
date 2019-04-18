package gui;

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
  private Button settings;

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
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfileSelectionGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Profile Selection");
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
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SettingsGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Settings");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      System.out.println("Can't load SettingsGUI.fxml");
      e.printStackTrace();
    }
  }

}
