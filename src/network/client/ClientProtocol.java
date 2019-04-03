package network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import network.Parameter;

/**
 * Test class from client side
 * 
 * @author qiychen
 * 
 *
 */
public class ClientProtocol extends Thread {
  private Socket s;
  private BufferedReader fromServer;
  private PrintWriter toServer;
  private BufferedReader fromTastatur;
  private InetAddress address;
 /**
  * Constructor
  * @param address
  */
  public ClientProtocol(InetAddress address) {
    this.address = address;
    connect();
  }

  public void connect() {
    try {
      this.s = new Socket(address, Parameter.PORT);
      System.out.println("Socket opened successfully");
      fromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
      toServer = new PrintWriter(s.getOutputStream(), true);
      fromTastatur = new BufferedReader(new InputStreamReader(System.in));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void run() {
    while (true) {
      System.out.println("Server said: ");
      try {
        System.out.println(fromServer.readLine());
        System.out.println("You said: ");
        String word = fromTastatur.readLine();
        toServer.println(word);
        System.out.println(fromServer.readLine());
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
  }
}

