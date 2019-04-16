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
  public static boolean ingame=false;
  public void hostGame(int noOfPlayer) {
    server = new Server(Parameter.PORT,noOfPlayer);
    gameFinderHost = new GameFinder();
    client=gameFinderHost.getClient();
    Client.isHost=true;

  }
  
  public void joinGameonDiscovery(){
    gameFinder = new GameFinder();
    client = gameFinder.getClient();
    Client.isHost=false;
  }
  
  public void joinGame(String ip, int port) {
   gameFinder = new GameFinder(ip,port);
  }
  
}
