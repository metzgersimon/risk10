package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import main.Main;

public class SinglePlayerGUIController {

  @FXML
  private Button startGame;

  @FXML
  private Button back;

  @FXML
  private Slider difficulty;

  @FXML
  void back(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfileSelectionGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Profile Selection");
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void startGame(ActionEvent event) {
     try {
     FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BoardGUI.fxml"));
     Parent root = (Parent) fxmlLoader.load();
     Stage stage = new Stage();
     // stage.setTitle("Board");
     BoardController boardController = fxmlLoader.getController();
     boardController.setMain(this, Main.g);
     stage.setScene(new Scene(root));
     stage.show();
     ((Node) event.getSource()).getScene().getWindow().hide();
     } catch (Exception e) {
     e.printStackTrace();
     }
  }
}
