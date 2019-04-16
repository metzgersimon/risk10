package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import game.Player;
import game.PlayerColor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import network.client.Client;
import network.client.GameFinder;
import network.messages.JoinGameMessage;
import network.server.Server;

public class MultiPlayerGUIController {

  @FXML
  private Button hostGame;

  @FXML
  private Button back;

  @FXML
  private Button joinGame;

  @FXML
  private TextField address;

  @FXML
  private Button connect;

  /** List of players who have joined the game */
  public static List<Player> playersList = new ArrayList<Player>();
  private HostGameGUIController hostGui = null;
  private HostGameLobbyController hostLobbyController = null;
  private NetworkController networkController = new NetworkController();

  @FXML
  void back(ActionEvent event) {
  if(NetworkController.server != null) {
    Server.game.removePlayer();
  }
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
    FXMLLoader fxmlLoader = null;
    try {
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
    hostGui = fxmlLoader.getController();
    hostLobbyController = hostGui.getHostController();
  }

  public void setController(HostGameLobbyController c) {
    this.hostLobbyController = c;
  }

  @FXML
  void joinGame(ActionEvent event) {
    // create an instance of the Player, add it to the Player list and link it to profile
    String name = ProfileSelectionGUIController.selectedPlayerName;
    FXMLLoader fxmlLoader = null;
    networkController.joinGameonDiscovery();
    try {
     fxmlLoader = new FXMLLoader(getClass().getResource("JoinGameLobby.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Main.j = fxmlLoader.getController();
      Stage stage = main.Main.stage;
      stage.setTitle("Game Lobby");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load JoinGameLobbyGUI.fxml");
      e.printStackTrace();
    }
    Thread t = new Thread() {
      public void run() {
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    t.start();
    JoinGameLobbyController controller = fxmlLoader.getController();
    Client client = NetworkController.gameFinder.getClient();
    client.setController(controller);
    client.setControllerHost(hostLobbyController);
    // send join game message to the server
    client.register(name);
  }

  /**
   * join game lobby with ip address and port
   * 
   * @param event
   */
  @FXML
  void joinGameWithAddress(ActionEvent event) {
    String name = ProfileSelectionGUIController.selectedPlayerName;
    FXMLLoader fxmlLoader = null;
    try {
      String ip_port = address.getText();
      String[] tokens = ip_port.split("_");
      int port;
      try {
        port = Integer.parseInt(tokens[1]);
        networkController.joinGame(tokens[0], port);
      } catch (NumberFormatException e) {
        System.out.println(getClass() + " : Port number is not in correct format ");
        e.printStackTrace();
      }
      fxmlLoader = new FXMLLoader(getClass().getResource("JoinGameLobby.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Game Lobby");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
    JoinGameLobbyController controller = fxmlLoader.getController();
    Client client = NetworkController.gameFinder.getClient();
    client.setController(controller);
    client.setControllerHost(hostLobbyController);
    // send join game message to the server
    client.register(name);
  }
  
}
