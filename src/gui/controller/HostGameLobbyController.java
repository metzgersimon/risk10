package gui.controller;

import java.util.ArrayList;
import game.AiPlayerEasy;
import game.AiPlayerHard;
import game.AiPlayerMedium;
import game.Player;
import game.PlayerColor;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.Main;
import network.client.Client;
import network.messages.SendChatMessageMessage;
import network.messages.game.LeaveGameMessage;
import network.messages.game.StartGameMessage;

/**
 * Class for host player to handle the functions of lobby before starting the game.
 * 
 * @author skaur
 *
 */
public class HostGameLobbyController {

  /** 
   * Text field in which a player writes his/her message to send to chat box. 
   */
  @FXML TextField textField;

  /**
   *  to send a message in the chat box this button should be click.
   */
  @FXML Button sendButton;

  /**
   *  text area in which all the players can see the message. 
   */
  @FXML TextArea chat;

  /**
   * Button to leave the game.
   */
 @FXML  Button leaveGame;

 /**
  * Button to start the game.
  */
  @FXML  Button startGame;

  /**
   * Button to add AI players.
   */
  @FXML Button addBots;

  /**
   * slider to set the level of AI Player.
   */
  @FXML Slider botLevel;

  /** 
   * check boxes shows the status of the joined player. 
   */
  @FXML CheckBox hostBox;
  @FXML CheckBox box1;
  @FXML CheckBox box2;
  @FXML CheckBox box3;
  @FXML CheckBox box4;
  @FXML CheckBox box5;

  @FXML ImageView image1;

  /** 
   * Check boxes lists for players joining the game.
   */
  public static ArrayList<CheckBox> playerNames;
  
  /**
   * A list of players added to the game lobby
   */
  public ArrayList<Player> clients = new ArrayList<Player>();


  /**
   * to handle the event when the button "send" is clicked.
   */
  @FXML
  void handleSendMessage(ActionEvent event) {
    String message = textField.getText();
    SendChatMessageMessage m =
        new SendChatMessageMessage(ProfileSelectionGUIController.selectedPlayerName, message);
    Client client = NetworkController.gameFinder.getClient();
    client.sendMessage(m);
    textField.clear();
  }

  /**
   * This action method changes the current stage back to Multiplayer GUI.
   * 
   * @param event parameter represents the element that invokes method by clicking leave button
   *
   */
  @FXML
  public void handleLeaveLobby(ActionEvent event) {
     LeaveGameMessage message = new LeaveGameMessage("HOST");
     message.setLeaveLobby(true);
     NetworkController.gameFinder.getClient().sendMessage(message);    
    try {
      // Main.g.getServer().stopServer();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/MultiplayerGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
    Main.g = new game.Game();
  }

  /**
   * This methods add an AI player to the game, add the player the AI instance in the list and
   * update the list on the host lobby.
   * 
   * @param event clicked to add the AI player
   */
  @FXML
  void handleAddBot(ActionEvent event) {
    Player aiPlayer;
    PlayerColor color = NetworkController.server.getAvailableColor().get(0);
    if (Main.g.getPlayers().size() < HostGameGUIController.numberofPlayers) {
      if (botLevel.getValue() == 0.0) {
        aiPlayer = new AiPlayerEasy(color);
      } else if (botLevel.getValue() == 1.0) {
        aiPlayer = new AiPlayerMedium(color);
      } else {
        aiPlayer = new AiPlayerHard(color);
      }
      // add the player to the player list in the game class
      Main.g.addPlayer(aiPlayer);
      NetworkController.server.getAvailableColor().remove(color);
      // update the list in the game lobby for host player
      updateList(Main.g.getPlayers().size() - 1, aiPlayer);
      System.out.println("Color for AI :  " + color.getColorString());
      System.out.println("An AI player " + aiPlayer.getName() + " has joined the game ");

      // enable the start button if possible
      this.enableStartButton();

    } else {
      // show alert if the list is full
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error alert");
      alert.setHeaderText("Can not add player");
      alert.setContentText("Player Lobby already full");
      alert.showAndWait();
    }
  }

  /**
   * Enable the check boxes according to the selected number of players by the host player.
   */
  public void initialize() {
    playerNames = new ArrayList<CheckBox>();
    playerNames.add(hostBox);
    playerNames.add(box1);
    playerNames.add(box2);
    playerNames.add(box3);
    playerNames.add(box4);
    playerNames.add(box5);
    for (int i = 0; i < HostGameGUIController.numberofPlayers; i++) {
      playerNames.get(i).setDisable(false);
    }
  }

  int pos;
  /**
   * 
   * This method is called from the server class whenever a new player has joined the lobby and
   * whenever a new AI player is added to the game lobby,to update the list of players in the lobby.
   */
  public void updateList(int index , Player p) {  
    pos = index;
    for(int i = 0; i < playerNames.size(); i++) {
      if(!playerNames.get(i).isSelected()) {
        System.out.println(pos + " " +  i);
        pos = i;
        break;
      }
    }
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        String name = p.getName();
        switch ((pos+1)) {
          case 1:
            // select the box
            playerNames.get(0).setSelected(true);
            // show the name of the player who joined the game
            playerNames.get(0).setText(name.toUpperCase() + " (Host Player) ");
            break;
          case 2:
            playerNames.get(1).setSelected(true);
            playerNames.get(1).setText(name.toUpperCase());
            break;
          case 3:
            playerNames.get(2).setSelected(true);
            playerNames.get(2).setText(name.toUpperCase());
            break;
          case 4:
            playerNames.get(3).setSelected(true);
            playerNames.get(3).setText(name.toUpperCase());
            break;
          case 5:
            playerNames.get(4).setSelected(true);
            playerNames.get(4).setText(name.toUpperCase());
            break;
          case 6:
            playerNames.get(5).setSelected(true);
            playerNames.get(5).setText(name.toUpperCase());
            break;
          default:
            break;
        }

      }

    });
    //enable the  start button
    this.enableStartButton();

  }

  /**
   * @param content: text message
   */
  public void showMessage(String content) {
    chat.setStyle("-fx-font-size:15px;");
    chat.appendText(content+ "\n");
    this.textField.clear();
  }

  /**
   * Enable the start button, ones the all the clients have joined the lobby.
   */
  public void enableStartButton() {
    if (Main.g.getPlayers().size() == HostGameGUIController.numberofPlayers) {
      startGame.setDisable(false);
    }
  }

  /**
   *  Enable the start button, once the all the clients have joined the lobby.
   * @param event clicked by the host player to start the game.
   */
  @FXML
  public void handleStartGameButton(ActionEvent event) {
    StartGameMessage startGameMessage = new StartGameMessage(Main.g.getPlayers());
    // host player/client sends the message to the server
    NetworkController.gameFinder.getClient().sendMessage(startGameMessage);
  }

  public void refreshList(Player p) {
    for(CheckBox box : playerNames) {
      if(box.getText().equalsIgnoreCase(p.getName())){
        box.setText("");
        box.setSelected(false);
        break;
      }
    }
    Main.g.getPlayers().remove(p);
    NetworkController.server.getAvailableColor().add(p.getColor());

  }
}
