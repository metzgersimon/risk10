package gui;

import java.util.ArrayList;
import java.util.List;
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
import javafx.stage.Stage;

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
  
/**check boxes shows the status of the joined player*/
 @FXML CheckBox box1, box2, box3, box4, box5;

 private ArrayList<Player> players; //list of players/clients who will join the game
 
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
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MultiplayerGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
//      ((Node) event.getSource()).getScene().getWindow().hide();
      } catch (Exception e) {
      e.printStackTrace();
      }
  }
  @FXML
  void handleAddBot(ActionEvent event) {
    // TODO
  }

  @FXML
  void handleBotLevel(ActionEvent event) {
    // TODO
  }

  
  void enableTheBoxes() {
    //todo
   List<CheckBox> boxes= new ArrayList<CheckBox>();
   boxes.add(box1);
   boxes.add(box2);
   boxes.add(box3);
   boxes.add(box4);
   boxes.add(box5);
  
  
  }


}
