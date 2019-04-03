package network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import network.Parameter;

public class Client extends Thread {

  private InetAddress address;
  private Socket s;

  public Client(InetAddress address) {
  this.address = address;
  connect();
  }

  public void connect() {
    try {
      this.s = new Socket(address, Parameter.PORT);
      System.out.println("Socket opened successfully");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
public void run() {
  
}

}

// grobe interface
// mit methoden
// liste von sockets, an alle schicken
//
