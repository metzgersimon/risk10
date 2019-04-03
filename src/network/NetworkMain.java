package network;

import network.server.Server;

public class NetworkMain {

  /**
   * to test the network Classes
   */
  
  public static void main (String [] args) {
    Server server = new Server(Parameter.PORT,5);
    server.start();
  }
}
