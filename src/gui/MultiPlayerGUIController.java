package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MultiPlayerGUIController {

  @FXML
  private Button hostGame;

  @FXML
  private Button back;

  @FXML
  private Button joinGame;

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
  void hostGame(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HostGameLobby.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Host Game");
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load HostGameLobby.fxml");
      e.printStackTrace();
    }
  }

  @FXML
  void joinGame(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EnterIPGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Enter IP");
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load EnterIPGUI.fxml");
      e.printStackTrace();
    }
}

}