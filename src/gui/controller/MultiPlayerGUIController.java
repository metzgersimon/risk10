package gui.controller;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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

  private Alert alert = null;

  private String ipAddressRegex =
      "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
          + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";


  /** List of players who have joined the game */
  public static List<Player> playersList = new ArrayList<Player>();
  private HostGameGUIController hostGui = null;
  private HostGameLobbyController hostLobbyController = null;
  private NetworkController networkController = new NetworkController();

  @FXML
  void back(ActionEvent event) {
    if (NetworkController.server != null) {
      Main.g.removePlayer();
    }
    try {
      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/gui/ProfileSelectionGUI.fxml"));
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
      fxmlLoader = new FXMLLoader(getClass().getResource("/gui/HostGameGUI.fxml"));
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

  /**
   * @author skaur
   * @param event clicked to discover the server
   * 
   *        This method joins the server on discovery and if successfully connected, it opens the
   *        game lobby for the client/player. After joining the lobby the client register to the
   *        server with the player name
   */
  @FXML
  void joinGame(ActionEvent event) {
    // get the name of the player
    String name = ProfileSelectionGUIController.selectedPlayerName;
    FXMLLoader fxmlLoader = null;

    // call the method to connect to the server
    networkController.joinGameonDiscovery();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    // if client is succesfully created open the game lobby for the client
    if (NetworkController.gameFinder.getClient() != null) {
      try {
        fxmlLoader = new FXMLLoader(getClass().getResource("/gui/JoinGameLobby.fxml"));
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
    } else {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error alert");
      alert.setHeaderText("Error in joining the server.");
      alert.setContentText("Possible Errors : " + "\n" + "1.Server is not available." + "\n"
          + "2.Game Lobby is full.");
      alert.showAndWait();
    }
  }

  /**
   * @author skaur
   * @param event clicked to join the server over IP and port address
   * 
   *        This method joins the server with IP and port address if successfully connected, it
   *        opens the game lobby for the client/player. After joining the lobby the client register
   *        to the server with the player name
   */
  @FXML
  void joinGameWithAddress(ActionEvent event) {

    String name = ProfileSelectionGUIController.selectedPlayerName;
    FXMLLoader fxmlLoader = null;
    String ip_port = address.getText();
    String[] tokens = ip_port.split("_");

    if (!tokens[0].matches(ipAddressRegex)) {
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error alert");
      alert.setHeaderText("Error in IP Address format !");
      alert.setContentText(
          "Give the IP Address again in the right format followed by _ and the port number "
              + "\n");
      alert.showAndWait();
    }
    try {
      int port;
      try {
        port = Integer.parseInt(tokens[1]);
        networkController.joinGame(tokens[0], port);
      } catch (NumberFormatException e) {
        alert.setTitle("Error alert");
        alert.setHeaderText("Error in the port format ");
        alert.setContentText("Port number is not in correct format." + "\n");
        alert.showAndWait();
        System.out.println(getClass() + " : Port number is not in correct format ");
        e.printStackTrace();
      }
      if (NetworkController.gameFinder.getClient() != null) {
        fxmlLoader = new FXMLLoader(getClass().getResource("JoinGameLobby.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = main.Main.stage;
        stage.setTitle("Game Lobby");
        stage.setScene(new Scene(root));
        stage.show();

      } else {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error alert");
        alert.setHeaderText("You cannot join the game lobby ");
        alert.setContentText("Player Lobby is already full." + "\n");
        alert.showAndWait();
      }

    } catch (IOException e1) {
      System.out.println("Can't connect to the server ");
      e1.printStackTrace();
    } catch (Exception e2) {
      System.out.println(getClass() + ":  Can't open the JoinGameLobby.fxml");
    }

    if (NetworkController.gameFinder.getClient() != null) {
      JoinGameLobbyController controller = fxmlLoader.getController();
      Client client = NetworkController.gameFinder.getClient();
      client.setController(controller);
      client.setControllerHost(hostLobbyController);
      // send join game message to the server
      client.register(name);
    }
  }

}
