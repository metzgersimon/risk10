package gui;


import java.util.ArrayList;
import game.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Main;
import network.NetworkController;
import network.client.Client;
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
  
  @FXML private Button leaveGame;

  private Game g;  
  @FXML CheckBox clientPlayer;
  @FXML CheckBox hostBox;
  @FXML CheckBox client2;
  @FXML CheckBox client3;
  @FXML CheckBox client4;
  @FXML CheckBox client5;
  ArrayList<CheckBox> clientBoxes = new ArrayList<CheckBox>();
  
  @FXML
  void handleSendMessage(ActionEvent event) {
    String message = textField.getText();
    SendChatMessageMessage m = new SendChatMessageMessage(
    ProfileSelectionGUIController.selectedPlayerName, message);
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
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MultiplayerGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
//
//  public void setMain(Client client2, Game g) {
//    this.client = client2;
//    this.g = g;
//  }

  public void showMessage(String content) {
    button.setStyle("-fx-text-fill:black; -fx-font-size: 15px;");
    button.appendText(content + "\n");

  }

  /**
   * @skaur
   */
  public void initialize() {  
    clientBoxes = new ArrayList<CheckBox>();
    clientBoxes .add(hostBox);
    clientBoxes .add(clientPlayer);
    clientBoxes .add(client2);
    clientBoxes .add(client3);
    clientBoxes .add(client4);
    clientBoxes .add(client5);
    hostBox.setSelected(true);
    clientPlayer.setSelected(true);
  }
 
 public void viewBoardGame() {
   FXMLLoader fxmlLoader = null; 
   try {
     fxmlLoader = new FXMLLoader(getClass().getResource("BoardGUI.fxml"));
     Parent root = (Parent) fxmlLoader.load();
     Stage stage = main.Main.stage;
     Main.b = fxmlLoader.getController();
     System.out.println(Main.b);
     stage.setScene(new Scene(root));
     stage.show();
     // ((Node) event.getSource()).getScene().getWindow().hide();
   } catch (Exception e) {
     e.printStackTrace();
   }
   NetworkController.gameFinder.getClient().setBoardController(fxmlLoader.getController());
 }
}
