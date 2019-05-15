package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.messages.LeaveLobbyMessage;
import network.messages.SendChatMessageMessage;
import network.messages.game.LeaveGameResponseMessage;

/**
 * This class defines the function of game lobby after connecting to a server.
 *
 */

public class JoinGameLobbyController {

  /**
   * Text field to type the message to send.
   */
  @FXML
  private TextField textField;

  /**
   * Button pressed to send the text message.
   */
  @FXML
  private Button sendButton1;

  /**
   * Button pressed to leave the game.
   */
  @FXML
  private Button leaveGame;

  /**
   * Text area which shows the communication between the players.
   */
  @FXML
  private TextArea chat;

  @FXML
  void handleSendMessage(ActionEvent event) {
    String message = textField.getText();
    if (message.equals("")) {
      return;
    }
    SendChatMessageMessage m =
        new SendChatMessageMessage(ProfileSelectionGUIController.selectedPlayerName, message);
    NetworkController.gameFinder.getClient().sendMessage(m);
    this.textField.clear();
  }

  /**
   * This method handles leaving game of a client.
   * 
   * @author qiychen
   * @param back to host and join game page and close the thread
   */
  @FXML
  void handleLeaveGame(ActionEvent event) {
    LeaveLobbyMessage message =
        new LeaveLobbyMessage(NetworkController.gameFinder.getClient().getPlayer());
    NetworkController.gameFinder.getClient().sendMessage(message);
    try {
      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/gui/ProfileSelectionGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This method shows messages in chatbox.
   * 
   * @param content = chat message
   */
  public void showMessage(String content) {
    chat.setStyle("-fx-font-size:15px;");
    chat.appendText(content + "\n");
  }

  /**
   * This method shows the alert after the host player decides to leave the lobby. After the alert
   * it takes the client back to the multiplayer menu to host or find another game.
   * 
   * @skaur
   */
  public void showGameCancelAlert() {
    // show the alert
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Error alert");
    alert.setHeaderText("Game Cancelled");
    alert.setContentText("The host player has cancelled the game");
    alert.showAndWait();

    // this method shows the end game statistics and disconnect the client
    LeaveGameResponseMessage responseMessage = new LeaveGameResponseMessage();
    NetworkController.gameFinder.getClient().sendMessage(responseMessage);

    // take back to the multiplayer menu
    try {
      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/gui/ProfileSelectionGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
