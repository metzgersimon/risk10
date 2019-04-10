package network.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import network.Parameter;


/** Game Server that host the game **/

public class Server extends Thread {

  /** the port where server connects to the clients */
  private int port;

  /** server socket which accepts the client connections */
  private ServerSocket serverSocket;

  /** A list clients connected to the game server */
  private List<ClientConnection> clients = new ArrayList<ClientConnection>();

  /** The socket used for communication */
  private Socket socket;

  private boolean isRunning;

  /** only to test; counts the number of clients connected to the server */
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
                // sending a response back
                byte[] sendResponse = "Game_RESPONSE".getBytes();
                DatagramPacket responsePacket = new DatagramPacket(sendResponse,
                    sendResponse.length, getPacket.getAddress(), getPacket.getPort());
                datagramSocket.send(responsePacket);
                // serverSocket = new ServerSocket(Parameter.PORT);
                // Socket s = serverSocket.accept();
                // ClientConnection cc = new ClientConnection(s);
                // creating a socket each client, gets an response back
                // new ClientConnection(getPacket.getAddress(),Parameter.PORT).connect();
                // clientNo++;
                //
                System.out.println("Client Connection for client no. " + counter + " created");
                counter++;
             
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

  /**
   * @author skaur indicates that that the server has started running and starts listening to the
   *         clients
   */
  @Override
  public void run() {
    this.isRunning = true;
    System.out.println("Server started....");
    while (isRunning) {
      listen();
    }
  }

  /**
   * @author skaur opens the server socket on s specified port and starts accepting the connections
   *         creates an instance of client connection to the server and add the clients to the list
   *         of clients connected to this server
   */
  public void listen() {
    try {
        serverSocket = new ServerSocket(Parameter.PORT);
        socket = serverSocket.accept();
        ClientConnection c = new ClientConnection(socket,this);
        System.out.println("dsds");
        clients.add(c);
        System.out.println(clients.size()+"size");
        c.start();
        serverSocket.close();
     //new ServerProtocol(socket).start();
  
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * @return list of client connections
   */
  public List<ClientConnection> getConnections() {
    return this.clients;
  }

  /**
   * @author skaur stops the server and send a broadcast message to all clients that server is
   *         shutting down
   */
  public void stopServer() {
    if (!this.serverSocket.isClosed()) {
      System.out.println("stopping server....");
      try {
        this.serverSocket.close();
        this.isRunning = false;
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    
    }
  }

  public int getPort() {
    return this.port;
  }

  public int getNoOfPlayer() {
    return this.noOfPlayer;
  }
}
