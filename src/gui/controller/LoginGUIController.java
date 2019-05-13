package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller class for LoginGUI
 * 
 * @author liwang
 */
public class LoginGUIController {

  public static boolean init = true;

  @FXML
  private Button klickToStart;


  /**
   * Event handle class invoked when the klickToStart Button clicked to open the MainMenuGUI
   * 
   * @author liwang
   * @param event
   */
  @FXML
  void handleKlickToStastButton(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/MainMenuGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
