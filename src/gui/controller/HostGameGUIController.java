package gui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

/**
 * This class defines the functions before hosting the game.
 *
 */
public class HostGameGUIController {

  Stage stage = main.Main.stage;

  /**
   * Amount of player selected by the host player.
   */
  public static int numberofPlayers;

  /**
   * Button pressed to go back to the menu.
   */
  @FXML
  private Button back;

  /**
   * Choice box to select the amount of players.
   */
  @FXML
  private ChoiceBox<String> choiceBox;

  /**
   * Button pressed to start hosting the game.
   */
  @FXML
  private Button confirm;

  /**
   * Controller for the host lobby.
   */
  private HostGameLobbyController hostLobController = null;

  /**
   * Network controller.
   */
  private NetworkController networkController = new NetworkController();

  /**
   * Starts the server and client for the host game player.Host Player/ client registers with the
   * name of the player.
   * 
   * @author skaur
   * @param event to host the game with selected number of players
   */
  @FXML
  void confirm(ActionEvent event) {
    // create an instance of the Player, add it to the Player list and link it to profile
    final String name = ProfileSelectionGUIController.selectedPlayerName;
    FXMLLoader fxmlLoader = null;
    numberofPlayers = Integer.parseInt(choiceBox.getSelectionModel().getSelectedItem());
    networkController.hostGame(numberofPlayers);
    // opens the host lobby
    try {
      fxmlLoader = new FXMLLoader(getClass().getResource("/gui/HostGameLobby.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Main.h = fxmlLoader.getController();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
    // set the controllers
    hostLobController = fxmlLoader.getController();
    NetworkController.server.setController(hostLobController);
    NetworkController.gameFinder.getClient().setControllerHost(hostLobController);
    NetworkController.gameFinder.getClient().register(name);
  }

  /**
   * This method initializes the choicebox with possible number of players.
   */
  public void initialize() {
    ObservableList<String> nr = FXCollections.observableArrayList("2", "3", "4", "5", "6");
    choiceBox.setItems(nr);
    choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        if (newValue != null) {
          confirm.setDisable(false);
        } else {
          confirm.setDisable(true);
        }
      }
    });
    // initial
  }

  /**
   * Go back to the multiplayer menu.
   * 
   * @param event pressed to go back to the multiplayer menu.
   */
  @FXML
  void back(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/MultiPlayerGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
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
