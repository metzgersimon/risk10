package gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HostGameLobbyGUI extends Application{

  /*to test the screen, main method is written in this class
   * /
   */
  public static void main(String[] args) {
    launch();
  }
//  /*textfield in which a player writes his/her message to send to chatbox*/
//@FXML TextField textField; 
//
///*to send a message in the chat box this button should be clicked*/
//@FXML Button sendButton;
//
///*text area in which all the players can see the message*/
//@FXML TextArea chatBox;


       public void start(Stage primaryStage) {
        try {
            // TODO Auto-generated method stub
            Parent root = FXMLLoader.load(getClass().getResource("HostGameLobby.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("My ChatLogin");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
          e.printStackTrace();
            System.out.println("Can't load Anfang.fxml");
        }

}


}       
       
  
  

