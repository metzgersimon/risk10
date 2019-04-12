package gui;

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
  private HostGameLobbyController hostLobController=null;
  @FXML
  void confirm(ActionEvent event) {
    FXMLLoader fxmlLoader=null;
    numberofPlayers = Integer.parseInt(choiceBox.getSelectionModel().getSelectedItem());
    System.out.println(numberofPlayers);
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
    Main.g.getServer().setController(hostLobController);
    Main.g.getGameFinderHost().getClient().setControllerHost(hostLobController);
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
