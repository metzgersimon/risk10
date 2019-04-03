package network.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import network.Parameter;

/**
 * Test class from server side
 * 
 * @author qiychen
 * 
 *
 */
public class ServerProtocol extends Thread {
  private Socket s;
  BufferedReader fromClient;
  PrintWriter toClient;
  BufferedReader fromTastatur;

  /**
   * Constructor
   * 
   * @param socket
   */
  public ServerProtocol(Socket s) {
    this.s = s;
    try {
      fromClient = new BufferedReader(new InputStreamReader(s.getInputStream()));
      toClient = new PrintWriter(s.getOutputStream(), true);
      fromTastatur = new BufferedReader(new InputStreamReader(System.in));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void run() {
    System.out.println("Protocol started");
    while (true) {
      try {
        toClient.println("Please write something");
        String answer = fromClient.readLine();
        toClient.println(answer);
        System.out.println("Client said: " + answer);

      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }



}
