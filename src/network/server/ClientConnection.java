package network.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnection extends Thread{

  private InetAddress address;
  private int port;
  private Socket socket;
  
  public ClientConnection(Socket s) {
     this.socket = socket;
  }
  
  public void run(){
    while(true) {
      
    }
    }
}
