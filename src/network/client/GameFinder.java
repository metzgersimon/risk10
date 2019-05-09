package network.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class GameFinder extends Thread {
 
  private Client client;
  private DatagramSocket datagramSocket;
  private int port;
  private String ip;
  /**
   * @author skaur
   */
  public GameFinder() {
    this.port = main.Parameter.PORT;
    this.start();
  }
  
  public GameFinder(String ip, int port) {
    this.port = port;
    this.ip = ip;
    this.client = new Client(ip,port);
    client.start();
  }

  /**
   * @author skaur this methods opens a socket and try to send the game request on the broadcast
   *         address furthermore a response back back from the Broadcasting server is recieved and
   *         the ip address of server is saved in the the Parameter hostIP
   */
  public void run() {
    try {
      // opening socket on a random port and setting the broadcast to true
      datagramSocket = new DatagramSocket();
      datagramSocket.setBroadcast(true);
      byte[] sendRequest = "GAME_REQUEST".getBytes();
      try {
        // firstly broadcast to the default broadcast address
        DatagramPacket sendPacket = new DatagramPacket(sendRequest, sendRequest.length,
            InetAddress.getByName("255.255.255.255"), main.Parameter.PORT);
        this.datagramSocket.send(sendPacket);
        System.out.println("A Game Request has been send to 255.255.255.255");
      } catch (SocketException e) {
        System.out.println("Package cant be send on the address 255.255.255.255");
        System.exit(1);
        e.printStackTrace();
      }

      // waiting for the response from the server side
      byte[] buf = new byte[1248];
      DatagramPacket responseP = new DatagramPacket(buf, buf.length);
      datagramSocket.receive(responseP);
      System.out.println("A package is recieved from " + responseP.getAddress());
      // get the ip address and create a socket for it
      InetAddress address = responseP.getAddress();
      client = new Client(address, port);
      client.start();
      // new ClientProtocol(address).start();
      System.out.println("Client Created");
      this.datagramSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public void closeConnection() { 
    this.client.disconnect();
    this.interrupt(); 
  }
  
  public Client getClient() {
    return this.client;
  }

}
