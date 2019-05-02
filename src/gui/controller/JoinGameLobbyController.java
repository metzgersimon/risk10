package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.messages.SendChatMessageMessage;


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
    try {
      // Main.g.getGameFinder().closeConnection();
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

  }
}