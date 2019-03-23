package gui;

import java.awt.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class JoinGameLobbyController {

  @FXML private Button leaveButton;
   
  @FXML private TextField textField;
  
  @FXML private TextArea textArea;
  
  @FXML private Button button;
  
  @FXML void sendMessage(ActionEvent event){
   String message = textField.getText();
   textArea.appendText(message + "\n");
  }
  
}
