package network.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import network.Parameter;
import network.client.Client;


public class Server extends Thread {

  private int port;
  private ServerSocket serverSocket;
  private List<ClientConnection> clients = new ArrayList<ClientConnection>();
  private Socket socket;
  private boolean isRunning;
  private int counter = 1;
  /**
   * this parameter represents the number of players selected by the host to play the game 
   */
  private int noOfPlayer;
  /**
   * @author skaur
   * @param port
   */
  public Server(int port, int noOfPlayers) {
    this.port = port;
    this.noOfPlayer = noOfPlayers;
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
              if(counter < noOfPlayers) {
              // sending a response back
              byte[] sendResponse = "Game_RESPONSE".getBytes();
              DatagramPacket responsePacket = new DatagramPacket(sendResponse, sendResponse.length,
                  getPacket.getAddress(), getPacket.getPort());
              datagramSocket.send(responsePacket);
              // serverSocket = new ServerSocket(Parameter.PORT);
              // Socket s = serverSocket.accept();
              // ClientConnection cc = new ClientConnection(s);
              // creating a socket each client, gets an response back
              // new ClientConnection(getPacket.getAddress(),Parameter.PORT).connect();
              // clientNo++;
              //
              System.out.println("Client Connection for client no. " + counter + " created") ;
              counter++;
              } else {
                System.out.println("Maximum number of clients have joined");
              }
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


  }

  @Override
  public void run() {
    this.isRunning = true;
    System.out.println("Server started....");
    while (isRunning) {
      listen();
    }
  }

  public void listen() {
    try {
      serverSocket = new ServerSocket(Parameter.PORT);
      socket = serverSocket.accept();
      ClientConnection c = new ClientConnection(socket);
      System.out.println("dsds");
      clients.add(c);
      c.start();
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}

