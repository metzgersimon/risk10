package network;

import network.client.Client;
import network.server.Server;

public class NetworkMain {

  /**
   * to test the network Classes
   */
  
  public static void main (String [] args) {
   Client c = new Client("localHost",8888);
   c.start();
  }
}
