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
 * @author liwang Controller class for MainMenuGUI
 */
public class MainMenuGUIController {

  @FXML
  private Button singlePlayer;

  @FXML
  private Button multiPlayer;

  @FXML
  private Button settings;

  /**
   * Event handle class invoked when either singlePlayer or multiPlayer Button clicked to open the
   * profileSelectionGUI
   * 
   * @param event
   */
  @FXML
  void openProfileSelection(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfileSelectionGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Profile Selection");
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load ProfileSelectionGUI.fxml");
      e.printStackTrace();
    }
  }

  /**
   * Event handle class invoked when the settings Button clicked to open the SettingGUI
   * 
   * @param event
   */
  @FXML
  void openSettings(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SettingsGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Settings");
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load SettingsGUI.fxml");
      e.printStackTrace();
    }
  }

}
