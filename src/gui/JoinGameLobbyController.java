package gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import main.Main;
import network.client.Client;
import network.client.GameFinder;
import network.messages.SendChatMessageMessage;


public class JoinGameLobbyController {

  @FXML
  private Color x1;
  @FXML
  private TextField textField;

  @FXML
  private Button sendButton1;

  @FXML
  private TextArea button;
  private Client client;

  @FXML
  void handleSendMessage(ActionEvent event) {
    String message = textField.getText();
    SendChatMessageMessage m = new SendChatMessageMessage("test", message);
    client = Main.g.getGameFinder().getClient();
    client.sendMessage(m);
    button.appendText(client.getName() + "said" + message + "\n");
  }

}
