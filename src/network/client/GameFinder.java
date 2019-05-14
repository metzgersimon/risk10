package network.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * This class is for automatically finding a game on the network.
 * 
 * @author skaur
 *
 */
public class GameFinder extends Thread {
 
  /**
   * Client which is used to connect to a server after finding a server.
   */
  private Client client;
  
  /**
   * Datagram socket to find a server by sending/receiving packets.
   */
  private DatagramSocket datagramSocket;
  
  /**
   * Port for the communication.
   */
  private int port;
  
  /**
   * IP Address of the server.
   */
  private String ip;
  
  /**************************************************
   *                                                *
   *                Constructors                    *
   *                                                *
   *************************************************/

  /**
   * Create an instance of this class to search the available server automatically on the network.
   */
  public GameFinder() {
    this.port = main.Parameter.PORT;
    this.start();
  }
  
  /**
   * This constructor is for connecting to the server using its IP address and port.
   * 
   * @param ip of the server
   * @param port of the server
   */
  public GameFinder(String ip, int port) {
    this.port = port;
    this.ip = ip;
    this.client = new Client(ip, port);
    client.start();
  }
  
  
  public void closeConnection() {
    this.client.disconnect();
    this.interrupt();
  }

  /**************************************************
   *                                                *
   *                      Getters.                  *
   *                                                *
   *************************************************/
  
  public Client getClient() {
    return this.client;
  }

  public String getIp() {
    return this.ip;
  }


  /**************************************************
   *                                                *
   *      Method to find the network game           *
   *                                                *
   *************************************************/
  
  /**
   * This methods opens a socket and try to send the game request on the broadcast address.
   * Furthermore a response back from the Broadcasting server is received and the IP address of
   * server is saved in the the Parameter hostIP and a client is created to connect with the server.
   * 
   * @author skaur
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
        System.out.println(getClass().getName() + "Package cant be send");
        // e.printStackTrace();
      }

      // waiting for the response from the server side
      byte[] buf = new byte[1248];
      DatagramPacket responseP = new DatagramPacket(buf, buf.length);
      datagramSocket.receive(responseP);
      System.out.println("A package is recieved from " + responseP.getAddress());
      
      // get the IP address of the server and create a client for it
      InetAddress address = responseP.getAddress();
      client = new Client(address, port);
      client.start();
      System.out.println("Client Created");
      
      //close after connecting
      this.datagramSocket.close();
      
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  
}
