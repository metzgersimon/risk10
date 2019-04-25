package network;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Main;
import network.Parameter;
import network.client.Client;
import network.client.GameFinder;
import network.server.Server;
/**
 * this class contains methods which connects the network with gui
 * @author sandeepkaur
 *
 */
public class NetworkController {

  public static Server server;
  public static Client client;
  public static GameFinder gameFinder;
  public static GameFinder gameFinderHost;
  public static boolean ingame=false;
  public void hostGame(int noOfPlayer) {
    server = new Server(Parameter.PORT,noOfPlayer);
//    gameFinder = new GameFinder();
//    client=gameFinderHost.getClient();
//    Client.isHost=true;
    String [] token = server.getIpAddress().toString().split("/");
    joinGame(token[1], server.getPort());
    gameFinder.getClient().isHost = true;

  }
  
  public void joinGameonDiscovery(){
    gameFinder = new GameFinder();
    client = gameFinder.getClient();
//    Client.isHost=false;
  }
  
  public void joinGame(String ip, int port) {
   gameFinder = new GameFinder(ip,port);
  }
  
  public void viewBoardGame() {
    FXMLLoader fxmlLoader = null; 
    try {
      fxmlLoader = new FXMLLoader(getClass().getResource("BoardGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      Main.b = fxmlLoader.getController();
      Main.b.startBoard();
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
    gameFinder.getClient().setBoardController(fxmlLoader.getController());
  }
}
