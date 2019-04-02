package network.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnection {

  private InetAddress address;
  private int port;
  private Socket socket;
  
  public ClientConnection(InetAddress address, int port) {
    this.address = address;
    this.port = port;
  }
  
  public boolean connect() {
    try {
      socket = new Socket(this.address, this.port);
      System.out.println("Socket created successfully");
      return true;
    } catch(IOException e) {
      e.printStackTrace();
      return false;
    }
  }
}
