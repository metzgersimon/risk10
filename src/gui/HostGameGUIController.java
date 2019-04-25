package gui;

import game.Player;
import game.PlayerColor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import main.Main;
import network.NetworkController;
import network.Parameter;
import network.client.GameFinder;
import network.server.ClientConnection;
import network.server.Server;

public class HostGameGUIController {

  Stage stage = main.Main.stage;
  public static int numberofPlayers; // number of Players selected

  @FXML
  private Button back;

  @FXML
  private ChoiceBox<String> choiceBox;

  @FXML
  private Button confirm;
  private HostGameLobbyController hostLobController = null;
  private NetworkController networkController = new NetworkController();
  
  @FXML
  void confirm(ActionEvent event) {
    // create an instance of the Player, add it to the Player list and link it to profile
    String name = ProfileSelectionGUIController.selectedPlayerName;
    FXMLLoader fxmlLoader = null;
     numberofPlayers = Integer.parseInt(choiceBox.getSelectionModel().getSelectedItem());
     networkController.hostGame(numberofPlayers);
    
    try {
      fxmlLoader = new FXMLLoader(getClass().getResource("HostGameLobby.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Main.h = fxmlLoader.getController();
      Stage stage = main.Main.stage;
      stage.setTitle("Host Game Lobby");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
    hostLobController = fxmlLoader.getController();
    NetworkController.server.setController(hostLobController);
    NetworkController.gameFinder.getClient().setControllerHost(hostLobController);
    NetworkController.gameFinder.getClient().register(name);
  }

  public void initialize() {
    ObservableList<String> nr = FXCollections.observableArrayList("2", "3", "4", "5", "6");
    choiceBox.setItems(nr);
    // initial
  }

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

  public HostGameLobbyController getHostController() {
    return this.hostLobController;
  }

}
