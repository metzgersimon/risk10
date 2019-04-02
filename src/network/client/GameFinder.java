package network.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import network.Parameter;

public class GameFinder extends Thread {

  private DatagramSocket datagramSocket;
  
  private ServerSocket serverSocket;
  
/**
 * @author skaur
 */
  public GameFinder() {
    this.start();
    }

  /**
   * @author skaur
   * this methods opens a socket and try to send the game request on the broadcast address
   * furthermore a response back back from the Broadcasting server is recieved and
   * the ip address of server is saved in the the Parameter hostIP
   */
  public void findGames() {
    try {
      // opening socket on a random port and setting the broadcast to true
      datagramSocket = new DatagramSocket();
      datagramSocket.setBroadcast(true);
      byte[] sendRequest = "GAME_REQUEST".getBytes();
      try {
        // firstly broadcast to the default broadcast address
        DatagramPacket sendPacket = new DatagramPacket(sendRequest, sendRequest.length,
            InetAddress.getByName("255.255.255.255"), Parameter.PORT);
        this.datagramSocket.send(sendPacket);
        System.out.println("A Game Request has been send to 255.255.255.255");
      } catch (SocketException e) {
        System.out.println("Package cant be send on the address 255.255.255.255");
        System.exit(1);
        e.printStackTrace();
      }

      // waiting for the response from the server side
//      byte[] buf = new byte[1248];
//      DatagramPacket responseP = new DatagramPacket(buf, buf.length);
//      datagramSocket.receive(responseP);
//      String hostIP = responseP.getAddress().toString();
//      System.out.println("A package is recieved from " + responseP.getAddress());
//      datagramSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }   
  }

  public void run() {
    try {
      ServerSocket server = new ServerSocket(Parameter.PORT);
       this.serverSocket = server;
       Socket socket = server.accept();
       System.out.println("Connected with:  " + InetAddress.getLocalHost());
       // start a new client thread here with the parameter as socket
        new Client(socket).start();
       serverSocket.close();
//       socket.close();
     } catch (Exception e) {
       e.printStackTrace();
     }
  }

  public static void main(String[] args) {
    new GameFinder().findGames();
  }

}
