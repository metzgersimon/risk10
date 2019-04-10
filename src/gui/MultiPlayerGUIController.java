package gui;

import java.util.ArrayList;
import java.util.List;
import game.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Main;
import network.client.Client;

public class MultiPlayerGUIController {

  @FXML
  private Button hostGame;

  @FXML
  private Button back;

  @FXML
  private Button joinGame;

  /** List of players who have joined the game */
  public static List<Player> playersList = new ArrayList<Player>();
  private HostGameGUIController hostGui=null;
  private HostGameLobbyController hostLobbyController=null;
  @FXML
  void back(ActionEvent event) {
    Main.g.removePlayer();
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfileSelectionGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Profile Selection");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void hostGame(ActionEvent event) {
    FXMLLoader fxmlLoader=null;
    try {
      // only to test connection
      Main.g.hostGame(ProfileSelectionGUIController.player, HostGameGUIController.numberofPlayers);
      MultiPlayerGUIController.playersList.add(ProfileSelectionGUIController.player);
      fxmlLoader = new FXMLLoader(getClass().getResource("HostGameGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Host Game");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
    hostGui=fxmlLoader.getController();
    hostLobbyController=hostGui.getHostController();
  }
  
 public void setController(HostGameLobbyController c) {
    System.out.println("dsds");
    this.hostLobbyController = c;
    System.out.println("dsds");
  }
  @FXML
  void joinGame(ActionEvent event) {
    FXMLLoader fxmlLoader = null;
    try {
      // only to test connection
      Main.g.joinGame(ProfileSelectionGUIController.player);
//      Main.g.addPlayer(ProfileSelectionGUIController.player);
      fxmlLoader = new FXMLLoader(getClass().getResource("JoinGameLobby.fxml"));
      Parent root = (Parent) fxmlLoader.load();    
      Stage stage = main.Main.stage;
      stage.setTitle("Game Lobby");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load JoinGameLobbyGUI.fxml");
      e.printStackTrace();
    }
    JoinGameLobbyController controller = fxmlLoader.getController();
    Client client = Main.g.getGameFinder().getClient();
    client.setController(controller);
    client.setControllerHost(hostLobbyController);
//    this.hostLobbyController.addPlayerName(2,"sandep");
 
  }
  
}
