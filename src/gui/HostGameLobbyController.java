package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import game.AiPlayerEasy;
import game.AiPlayerHard;
import game.AiPlayerMedium;
import game.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;

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
  private int player = HostGameGUIController.numberofPlayers;
  MultiPlayerGUIController multi;
  private ArrayList<Player> players = main.Main.g.getPlayers(); // list of players/clients who will join the game
  public static ArrayList<CheckBox> playerNames;
  public static HashMap<Integer, String> playersJoined = new HashMap<Integer,String>();
  public static int counter = 0;


  /** to handle the event when the button "send" is clicked */
  @FXML
  void handleSendMessage(ActionEvent event) {
    try {
      String message = textField.getText() + " \n";
      chatBox.appendText(message);
      System.out.println(message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * @author skaur
   * @param event : ActionEvent This parameter represents the element that invokes this method. This
   *        action method changes the current stage back to Multiplayer GUI.
   */
  @FXML
  public void handleLeaveLobby(ActionEvent event) {
    try {
      Main.g.getServer().stopServer();
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
    // TODO
    Player p;
    if (Main.g.getPlayers().size() < 6) {
      if (botLevel.getValue() == 0.0) {
        p = new AiPlayerEasy();
      } else if (botLevel.getValue() == 1.0) {
        p = new AiPlayerMedium();
      } else {
        p = new AiPlayerHard();
      }
      Main.g.addPlayer(p);
//      addPlayerInList(p.getName());
      System.out.println("An AI player " + p.getName() + " has joined the game ");
      addPlayerName(counter,p.getName());
    }
  }

  public void addPlayerInList(String name) {
//    for (int i = 0; i < playerNames.size(); i++) {
//      if (playerNames.get(i).isDisabled()) {
//        playerNames.get(i).setDisable(false);
//        playerNames.get(i).setText(name);
//      }
//    }
  }

  public void addPlayerName(int index, String name) {
//    counter++;
    playersJoined.put(index, name);
    playerNames.get(index).setSelected(true);
    playerNames.get(index).setText(name);
//     enableStartButton();   
  }
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

  public void showMessage(String content) {
    chatBox.appendText(content);
  }
  
  public void enableStartButton(){
    if(Main.g.getPlayers().size() == HostGameGUIController.numberofPlayers ) {
      startGame.setDisable(false);
    }
  }
  
  public void test() {
    try {
     Main.m.setController(this);
    System.out.println("sdsds");
    } catch(Exception e) {
      e.printStackTrace();
    }
  }


  public void setController(MultiPlayerGUIController multiPlayerGUIController) {
   this.multi = multiPlayerGUIController;
    
  }

}
