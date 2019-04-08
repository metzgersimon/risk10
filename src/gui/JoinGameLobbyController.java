package gui;


import game.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
  private Client client = Main.g.getGameFinder().getClient();;

  private Game g;


  @FXML
  void handleSendMessage(ActionEvent event) {
    String message = textField.getText();
    SendChatMessageMessage m = new SendChatMessageMessage("test", message);
    client = Main.g.getGameFinder().getClient();
    client.sendMessage(m);
    button.appendText(client.getName() + "said" + message + "\n");
  }

  /**
   * @author qiychen
   * @param back to host and join game page and close the thread
   */
  @FXML
  void handleLeaveGame(ActionEvent event) {
    try {
      // Main.g.getGameFinder().closeConnection();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MultiplayerGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setMain(Client client2, Game g) {
    this.client = client2;
    this.g = g;
  }

  public void showMessage(String content) {
    button.appendText(content);

  }

}
