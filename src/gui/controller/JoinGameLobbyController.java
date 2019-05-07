package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import network.messages.LeaveLobbyMessage;
import network.messages.SendChatMessageMessage;
import network.messages.game.LeaveGameResponseMessage;


public class JoinGameLobbyController {

  @FXML
  private TextField textField;
  @FXML
  private Button sendButton1;
  @FXML
  private Button leaveGame;
  @FXML
  private TextArea chat;

  @FXML
  void handleSendMessage(ActionEvent event) {
    String message = textField.getText();
    SendChatMessageMessage m =
        new SendChatMessageMessage(ProfileSelectionGUIController.selectedPlayerName, message);
    NetworkController.gameFinder.getClient().sendMessage(m);
  }

  /**
   * @author qiychen
   * @param back to host and join game page and close the thread
   */
  @FXML
  void handleLeaveGame(ActionEvent event) {
    LeaveLobbyMessage message = new LeaveLobbyMessage(NetworkController.gameFinder.getClient().getPlayer());
    NetworkController.gameFinder.getClient().sendMessage(message);
    NetworkController.gameFinder.getClient().disconnect();
    try {
      // Main.g.getGameFinder().closeConnection();
      LeaveLobbyMessage lobbyMessage = new LeaveLobbyMessage(NetworkController.gameFinder.getClient().getPlayer());
      NetworkController.gameFinder.getClient().sendMessage(lobbyMessage);
      NetworkController.gameFinder.getClient().disconnect();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/MultiplayerGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void showMessage(String content) {
    chat.setStyle("-fx-font-size:15px;");   
    chat.appendText(content + "\n");
    textField.clear();
  }
  
  public void showGameCancelAlert() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Error alert");
    alert.setHeaderText("Game Cancelled");
    alert.setContentText("The host player has cancelled the game");
    alert.showAndWait();
      // this method shows the end game staticstics and disconnect the client
      LeaveGameResponseMessage responseMessage = new LeaveGameResponseMessage();
      NetworkController.gameFinder.getClient().sendMessage(responseMessage);
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/MultiplayerGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
