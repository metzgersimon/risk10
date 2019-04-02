package network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread{
  
  private Socket socket;
  
  public Client(Socket socket) {
    this.socket = socket;
  }
  
}
