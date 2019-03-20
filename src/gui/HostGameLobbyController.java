package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HostGameLobbyController {

  
///*textfield in which a player writes his/her message to send to chatbox*/
@FXML TextField textField; 
//
///*to send a message in the chat box this button should be clicked*/
@FXML Button sendButton;
//
///*text area in which all the players can see the message*/
@FXML TextArea chatBox;

/*to handle the event when the button "send" is clicked*/
@FXML void handleSendMessage(ActionEvent event){
try {

    String message = textField.getText() + " \n";
    chatBox.appendText(message);
    System.out.println(message);
} catch (Exception e) {
    e.printStackTrace();
}
}

}
