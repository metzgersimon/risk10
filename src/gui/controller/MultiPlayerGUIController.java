package gui.controller;

import game.Player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import network.client.Client;

/**
 * This class represents the menu for multiplayer game.
 */
public class MultiPlayerGUIController {

  /****************************** Buttons for hosting/joining the game. **************************/

  /**
   * Button pressed to host the game.
   */
  @FXML
  private Button hostGame;
  /**
   * Button pressed switch back to the menu.
   */
  @FXML
  private Button back;

  /**
   * Button pressed to join a game.
   */
  @FXML
  private Button joinGame;

  /**
   * Text field to type the IP address of a server.
   */
  @FXML
  private TextField address;

  /**
   * Button pressed to connect to a game automatically.
   */
  @FXML
  private Button connect;

  /*************************************** Other Elements. ***************************************/
  /**
   * Alerts.
   */
  private Alert alert = null;

  /**
   * Regular expression for the IP address of a server.
   */
  private String ipAddressRegex =
      "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
          + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";


  public static List<Player> playersList = new ArrayList<Player>();

  /*************************************** Controllers. *****************************************/

  private HostGameGUIController hostGui = null;
  private HostGameLobbyController hostLobbyController = null;
  private NetworkController networkController = new NetworkController();


  /****************************************** Methods. ******************************************/

  /**
   * Switch back to the profile selection menu.
   * 
   * @param event trigger to go back to the profile selection menu.
   */
  @FXML
  void back(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/gui/ProfileSelectionGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Opens the host game UI.
   * 
   * @param event to host a game.
   */
  @FXML
  void hostGame(ActionEvent event) {
    FXMLLoader fxmlLoader = null;
    try {
      fxmlLoader = new FXMLLoader(getClass().getResource("/gui/HostGameGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
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
   * This method joins the server on discovery and if successfully connected, it opens the game
   * lobby for the client/player. After joining the lobby the client register to the server with the
   * player name
   * 
   * @author skaur
   * @param event clicked to discover the server
   */
  @FXML
  void joinGame(ActionEvent event) {

    // get the name of the player
    String name = ProfileSelectionGUIController.selectedPlayerName;
    FXMLLoader fxmlLoader = null;

    // call the method to connect to the server
    networkController.joinGameonDiscovery();
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    // if client is successfully created open the game lobby for the client
    if (NetworkController.gameFinder.getClient() != null) {
      try {
        fxmlLoader = new FXMLLoader(getClass().getResource("/gui/JoinGameLobby.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Main.j = fxmlLoader.getController();
        Stage stage = main.Main.stage;
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

      // set the controllers.
      JoinGameLobbyController controller = fxmlLoader.getController();
      Client client = NetworkController.gameFinder.getClient();
      client.setController(controller);
      client.setControllerHost(hostLobbyController);

      // send join game message to the server
      client.register(name);

    } else {
      // if lobby can't be joined show the possible errors
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error alert");
      alert.setHeaderText("Error in joining the server.");
      alert.setContentText("Possible Errors : " + "\n" + "1.Server is not available." + "\n"
          + "2.Game Lobby is full.");
      alert.showAndWait();
    }
  }

  /**
   * This method connects the client to the server with IP and port address, if successful it opens
   * the game lobby for the client/player. After joining the lobby the client register to the server
   * with the player name.
   * 
   * @author skaur
   * @param event clicked to join the server over IP and port address
   */
  @FXML
  void joinGameWithAddress(ActionEvent event) {

    // get the player bame
    String name = ProfileSelectionGUIController.selectedPlayerName;
    FXMLLoader fxmlLoader = null;

    // get the IP address given by the player to connect with the server
    String ipAddress = address.getText();

    // check if IP Address is correct, if not show the error
    if (!ipAddress.matches(ipAddressRegex)) {
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error alert");
      alert.setHeaderText("Error in IP Address format !");
      alert.setContentText(
          "Give the IP Address again in the right format followed by _ and the port number "
              + "\n");
      alert.showAndWait();
    }

    networkController.joinGame(ipAddress, main.Parameter.PORT);
    
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    
    try {

      // show the joined game lobby
      if (NetworkController.gameFinder.getClient() != null) {
        fxmlLoader = new FXMLLoader(getClass().getResource("/gui/JoinGameLobby.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = main.Main.stage;
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

    // set the controllers and register to the server with the player name.
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
