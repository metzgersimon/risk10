package gui;

import java.util.ArrayList;
import game.AiPlayerEasy;
import game.AiPlayerHard;
import game.AiPlayerMedium;
import game.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import network.client.Client;
import network.messages.SendChatMessageMessage;
import network.messages.game.StartGameMessage;

public class HostGameLobbyController {


  /** Text field in which a player writes his/her message to send to chatbox */
  @FXML
  TextField textField;

  /** to send a message in the chat box this button should be clicked */
  @FXML
  Button sendButton;

  /** text area in which all the players can see the message */


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
  @FXML
  TextArea chat;

  /** number of players the host want to play game with */
  public static ArrayList<CheckBox> playerNames;
  public ArrayList<Player> clients = new ArrayList<Player>();


  /** to handle the event when the button "send" is clicked */
  @FXML
  void handleSendMessage(ActionEvent event) {
    String message = textField.getText();
    SendChatMessageMessage m =
        new SendChatMessageMessage(ProfileSelectionGUIController.selectedPlayerName, message);
    Client client = NetworkController.gameFinder.getClient();
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
      // Main.g.getServer().stopServer();
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

  /**
   * @skaur
   * 
   *        enable the check boxes according to the selected number of players by the host player
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
   * @author skaur
   * 
   *         this method is called from the server class whenever a new player has joined and
   *         whenever a new bot is added to the game lobby to update the list of players in the
   *         lobby
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
    chat.setStyle("-fx-font-size:15px;");
    chat.appendText(content+ "\n");

  }

  /**
   * @skaur
   * 
   *        enable the start button, ones the all the clients have joined the lobby
   */
  public void enableStartButton() {
    if (Main.g.getPlayers().size() == HostGameGUIController.numberofPlayers) {
      startGame.setDisable(false);
    }
  }

  /**
   * @author skaur
   * @param event clicked by the host player to start the game
   * 
   *        Send message to the server that game has started with the list of players as parameter
   */
  @FXML
  public void handleStartGameButton(ActionEvent event) {
    StartGameMessage startGameMessage = new StartGameMessage(Main.g.getPlayers());
    // host player/client sends the message to the server
    NetworkController.gameFinder.getClient().sendMessage(startGameMessage);
  }

}
