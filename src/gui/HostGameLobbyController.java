package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import game.AiPlayerEasy;
import game.AiPlayerHard;
import game.AiPlayerMedium;
import game.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import network.client.Client;
import network.messages.SendChatMessageMessage;
import network.messages.game.StartGameMessage;
import network.server.Server;

public class HostGameLobbyController {


  /** textfield in which a player writes his/her message to send to chatbox */
  @FXML
  TextField textField;

  /** to send a message in the chat box this button should be clicked */
  @FXML
  Button sendButton;

  /** text area in which all the players can see the message */
  @FXML
  TextArea chatBox;

  @FXML
  Button leaveGame;

  @FXML
  Button startGame;

  @FXML
  Button addBots;

  @FXML
  Slider botLevel;

  /** check boxes shows the status of the joined player */
  @FXML
  CheckBox hostBox;
  @FXML
  CheckBox box1;
  @FXML
  CheckBox box2;
  @FXML
  CheckBox box3;
  @FXML
  CheckBox box4;
  @FXML
  CheckBox box5;
  
  /** number of players the host want to play game with */
  public static ArrayList<CheckBox> playerNames;
  public ArrayList<Player> clients = new ArrayList<Player>();

  private MultiPlayerGUIController multi;
  
  /** to handle the event when the button "send" is clicked */
  @FXML
  void handleSendMessage(ActionEvent event) {
     String message = textField.getText();
     SendChatMessageMessage m = new SendChatMessageMessage("test", message);
     System.out.println(NetworkController.gameFinderHost);
     Client client = NetworkController.gameFinderHost.getClient();
     client.sendMessage(m);
  }

  /**
   * @author skaur
   * @param event : ActionEvent This parameter represents the element that invokes this method. This
   *        action method changes the current stage back to Multiplayer GUI.
   */
  @FXML
  public void handleLeaveLobby(ActionEvent event) {
    try {
//      Main.g.getServer().stopServer();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MultiplayerGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void handleAddBot(ActionEvent event) {
    Player p;
    if (Main.g.getPlayers().size() < HostGameGUIController.numberofPlayers) {
      if (botLevel.getValue() == 0.0) {
        p = new AiPlayerEasy();
      } else if (botLevel.getValue() == 1.0) {
        p = new AiPlayerMedium();
      } else {
        p = new AiPlayerHard();
      }
      Main.g.addPlayer(p);
      updateList(p);
      // addPlayerInList(p.getName());
      System.out.println("An AI player " + p.getName() + " has joined the game ");
      this.enableStartButton();
    } else {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error alert");
      alert.setHeaderText("Can not add player");
      alert.setContentText("Player Lobby already full");
      alert.showAndWait();
    }
  }

  // public void addPlayerInList(String name) {
  //// for (int i = 0; i < playerNames.size(); i++) {
  //// if (playerNames.get(i).isDisabled()) {
  //// playerNames.get(i).setDisable(false);
  //// playerNames.get(i).setText(name);
  //// }
  //// }
  // }

/**
 * enable the check boxes according to the selected number of players by the host player
 * @skaur
 */
  public void initialize() {
    playerNames = new ArrayList<CheckBox>();
    playerNames.add(hostBox);
    playerNames.add(box1);
    playerNames.add(box2);
    playerNames.add(box3);
    playerNames.add(box4);
    playerNames.add(box5);
    hostBox.setSelected(true);
    for (int i = 0; i < HostGameGUIController.numberofPlayers; i++) {
      playerNames.get(i).setDisable(false);
    }

  }

  /**
   * @author skaur this method is called from the server class whenever a new player has joined and
   *         whenever a new bot is added to the game lobby update the list of players who are
   *         joining the lobby
   */
  public void updateList(Player p) {
    this.clients.add(p);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        int size = clients.size();
        System.out.println(size);
        String name = clients.get(size - 1).getName();
        switch (size) {
          case 1:
            playerNames.get(0).setSelected(true);
            playerNames.get(0).setText(name);
            break;
          case 2:
            playerNames.get(1).setSelected(true);
            playerNames.get(1).setText(name);
            break;
          case 3:
            playerNames.get(2).setSelected(true);
            playerNames.get(2).setText(name);
            break;
          case 4:
            playerNames.get(3).setSelected(true);
            playerNames.get(3).setText(name);
            break;
          case 5:
            playerNames.get(4).setSelected(true);
            playerNames.get(4).setText(name);
            break;
          case 6:
            playerNames.get(5).setSelected(true);
            playerNames.get(5).setText(name);
            break;
          default:
            break;
        }

      }

    });
    this.enableStartButton();

  }

  /**
   * @param content: text message
   */
  public void showMessage(String content) {
    chatBox.appendText(content + "\n");
  }

  /**
   * enable the start button, ones the all the clients have joined the lobby
   * @skaur
   */
  public void enableStartButton() {
    if (Main.g.getPlayers().size() == HostGameGUIController.numberofPlayers) {  
      startGame.setDisable(false);
    }
  }


  public void setController(MultiPlayerGUIController multiPlayerGUIController) {
    this.multi = multiPlayerGUIController;

  }
  
  @FXML
  public void handleStartGameButton(ActionEvent event) {
    FXMLLoader fxmlLoader = null;
    try {
      fxmlLoader = new FXMLLoader(getClass().getResource("BoardGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Main.b = fxmlLoader.getController();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
    Server server = NetworkController.server;
    StartGameMessage startGameMessage = new StartGameMessage(Main.g);
    NetworkController.gameFinderHost.getClient().sendMessage(startGameMessage);
    server.setBoardController(fxmlLoader.getController());
    NetworkController.gameFinderHost.getClient().setBoardController(fxmlLoader.getController());
  }

}
