package gui;

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
  
  public void hostGame(int noOfPlayer) {
    server = new Server(Parameter.PORT,noOfPlayer);
    gameFinderHost = new GameFinder();
    gameFinderHost.getClient().isHost = true;
  }
  
  public void joinGameonDiscovery(){
    gameFinder = new GameFinder();
    client = gameFinder.getClient();
  }
  
  public void joinGame(String ip, int port) {
   gameFinder = new GameFinder(ip,port);
  }
  
}
