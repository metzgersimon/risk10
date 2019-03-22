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
 * @author liwang Controller class for LoginGUI
 */
public class LoginGUIController {

  @FXML
  private Button klickToStart;

  /**
   * Event handle class invoked when the klickToStart Button clicked to open the MainMenuGUI
   * 
   * @param event
   */
  @FXML
  void handleKlickToStastButton(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfileSelectionGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Select Profile");
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
