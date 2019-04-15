package gui;

import network.Parameter;
import network.client.Client;
import network.client.GameFinder;
import network.server.Server;

public class NetworkController {

  public static Server server;
  public static Client hostClient;
  public static Client client;
  public static GameFinder gameFinder;
  public static GameFinder gameFinderHost;
  
  public void hostGame(int noOfPlayer) {
    server = new Server(Parameter.PORT,noOfPlayer);
    gameFinderHost = new GameFinder();
  }
  
  public void joinGameonDiscovery(){
    gameFinder = new GameFinder();
    client = gameFinder.getClient();
  }
  
  
}
