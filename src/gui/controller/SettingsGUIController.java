package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

/**
 * @author liwang Controller class for SettingsGUI
 */
public class SettingsGUIController {

  @FXML
  private Button back;

  @FXML
  private Button conform;

  @FXML
  private CheckBox sound;

  @FXML
  private CheckBox music;

  /**
   * Event handle class invoked when back Button clicked to go back to the MainMenuGUI
   * 
   * @param event
   */
  @FXML
  void handleBackButton(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/MainMenuGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load MainMenuGUI.fxml");
    }
  }

  /**
   * Event handle class invoked when conform Button clicked to go back to the MainMenuGUI and user
   * is informed that the change is saved (not yet implemented)
   * 
   * @param event
   */
  @FXML
  void handleConformButton(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/MainMenuGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load MainMenuGUI.fxml");
    }

  }

  /**
   * Event handle class invoked when checkBox music is changed (not yet implemented)
   * 
   * @param event
   */
  @FXML
  void handleMusicCheckBox(ActionEvent event) {

  }

  /**
   * Event handle class invoked when checkBox sound is changed (not yet implemented)
   * 
   * @param event
   */
  @FXML
  void handleSoundCheckBox(ActionEvent event) {

  }

}
