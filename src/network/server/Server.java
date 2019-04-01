package network.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Server extends Thread {

  private int port;
  private Socket socket;

  /**
   * @author skaur
   * @param port
   */
  public Server(int port) {
    this.port = port;

    Thread t = new Thread(new Runnable() {
      public void run() {
        DatagramSocket datagramSocket = null;
        try {
          // opening socket on a random port and setting the broadcast to true
          datagramSocket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
          datagramSocket.setBroadcast(true);
          System.out
              .println("The server " + InetAddress.getLocalHost() + " has started broadcasting");
          while (true) {
            // receive game request from the clients
            byte[] buf = new byte[1248];
            DatagramPacket getPacket = new DatagramPacket(buf, buf.length);
            datagramSocket.receive(getPacket);

            String message = new String(getPacket.getData()).trim();
            if (message.equals("GAME_REQUEST")) {
//               sending a response back
               byte[] sendResponse = "Game_RESPONSE".getBytes();
              
               DatagramPacket responsePacket = new DatagramPacket(sendResponse,
               sendResponse.length,
               getPacket.getAddress(), getPacket.getPort());
               datagramSocket.send(responsePacket);

              // creating a socket for the client, with the client address
              socket = new Socket(getPacket.getAddress(), 8888);

              System.out.println("A response has been sent back to: " + getPacket.getAddress());
              System.out.println(InetAddress.getLocalHost());
            }
          }
        } catch (SocketException | UnknownHostException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        } 
      }

    });
    t.start();
    this.start();
  }


}

