package gui;

import game.Player;
import game.PlayerColor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;

public class EnterIPGUIController {

  @FXML
  private Button back;

  @FXML
  private Button connect;

  @FXML
  private TextField ip;

  @FXML
  private TextField port;

  @FXML
  void back(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MultiPlayerGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Multi Player");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void discover(ActionEvent event) {
    // create an instance of the Player, add it to the Player list and link it to profile
    String name = ProfileSelectionGUIController.selectedPlayerName;
    System.out.println("Player instance created with name " + name + " and color "
        + PlayerColor.values()[Main.g.getPlayers().size()]);
    Player player = new Player(name, game.PlayerColor.values()[Main.g.getPlayers().size()]);
    Main.g.addPlayer(player);
    ProfileManager.setSelectedProfile(name);
    try {
      Main.g.joinGameonDiscovery(player);
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("JoinGameLobby.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Join Game Lobby");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void connect(ActionEvent event) {
    try {
      Main.g.joinGame(ip.getText(), Integer.parseInt(ip.getText()));
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("JoinGameLobby.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Join Game Lobby");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
