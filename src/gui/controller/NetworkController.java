package gui.controller;

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
 * This class contains methods which connects the network with gui.
 * 
 * @author skaur
 *
 */
public class NetworkController {

  /** Server for the network game */
  public static Server server;

  /** Client which connects to the server */
  public static Client client;

  /**
   * This instance of the class gameFinder, searches for the server with the IP address and port or
   * discover it automatically
   */
  public static GameFinder gameFinder;

  public static boolean ingame = false;

  /**
   * 
   * @param noOfPlayer selected by the host player.
   * 
   *        This method is called by the host player when h/she wants to host the game. It starts
   *        the server on a specified port and with the selected number of players, then its creates
   *        a client for the host player and connects it to the server with the server's address and
   *        port.
   */
  public void hostGame(int noOfPlayer) {
    server = new Server(Parameter.PORT, noOfPlayer);
    String[] token = server.getIpAddress().toString().split("/");
    joinGame(token[1], server.getPort());
    gameFinder.getClient().isHost = true;
  }

  /**
   * Called by the player who wants to join the game on the network through discovering the game
   * automatically.
   */
  public void joinGameonDiscovery() {
    gameFinder = new GameFinder();
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    client = gameFinder.getClient();
  }

  /**
   * 
   * @param ip of the server, player wants to join
   * @param port port of the server to be joined
   * 
   *        Join the network game by giving the port and IP address of the server.
   */
  public void joinGame(String ip, int port) {
    gameFinder = new GameFinder(ip, port);
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method is called by the server when enough players have joined the game. It opens the game
   * board for every client
   */
  public void viewBoardGame() {
    FXMLLoader fxmlLoader = null;
    try {
      fxmlLoader = new FXMLLoader(getClass().getResource("/gui/BoardGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      // set the controller
      Main.b = fxmlLoader.getController();
      Main.b.startBoard();
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
    // set the board controller for every client in the client class
    gameFinder.getClient().setBoardController(fxmlLoader.getController());
  }
}