package network.server;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import game.PlayerColor;
import gui.controller.BoardController;
import gui.controller.HostGameLobbyController;
import main.Main;

/**
 * This class represents the Server for the network game.
 * 
 * @author skaur
 */

public class Server extends Thread implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * the port where server connects to the clients
   */
  private int port;

  /**
   * server socket which accepts the client connections
   */
  private ServerSocket serverSocket;

  /**
   * A list clients connected to the game server
   */
  public List<ClientConnection> clients = new ArrayList<ClientConnection>();

  /**
   * The socket used for communication
   */
  private Socket socket;

  /**
   * Indicates if the server is running
   */
  private boolean isRunning;

  /**
   * This parameter represents the number of players selected by the host to play the game
   */
  private int noOfPlayer;

  /**
   * Controller of the host game lobby
   * 
   */
  private HostGameLobbyController lobbyController;

  /**
   * Represents the socket of each client with this server
   */
  private ClientConnection connection;

  /**
   * IP address of this server
   */
  private InetAddress ipAddress;

  /**
   * Board Controller of the Game
   */
  private BoardController boardController;
  DatagramSocket datagramSocket = null;

  /**
   * @author skaur
   * @param port : port of the server
   */
  public Server(int port, int noofPlayers) {
    this.port = port;
    this.noOfPlayer = noofPlayers;
    this.isRunning = true;
    Main.g.setNetworkGame(true);

    try {
      // Ip address of this server
      this.ipAddress = InetAddress.getLocalHost();
    } catch (UnknownHostException e1) {
      e1.printStackTrace();
    }
    
    try {
       serverSocket = new ServerSocket(main.Parameter.PORT);
    } catch (SocketException e) {      
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    } 
    catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    // This thread makes the server available on the network for the clients who wants to discover
    // the server without giving the IP address
    Thread t = new Thread(new Runnable() {

      public void run() {
        // socket to send packets on the broadcast address
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
            // If the message includes the String GAME_REQUEST, send a response back

            // Don't send the response if enough clients have joined the game
            if (Main.g.getPlayers().size() < noOfPlayer) {
              if (message.equals("GAME_REQUEST")) {
                // sending a response back
                byte[] sendResponse = "Game_RESPONSE".getBytes();
                DatagramPacket responsePacket = new DatagramPacket(sendResponse,
                    sendResponse.length, getPacket.getAddress(), getPacket.getPort());
                datagramSocket.send(responsePacket);
              }
            }
          }
        } catch (SocketException | UnknownHostException e) {
          System.out.println("Datagramsocket closed !");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

    });
    t.start();
    this.start();
  }

  /**
   * Indicates that that the server has started running and starts listening to the clients.
   * 
   * @author skaur
   */
  @Override
  public void run() {
    System.out.println("Server started....");
    while (this.isRunning) {
      listen();
    }
  }

  /**
   * Opens the server socket on s specified port and starts accepting the connections creates an
   * instance of client connection to the server and add the clients to the list of clients
   * connected to this server.
   * 
   * @author skaur
   */
  public void listen() {
    try {
      Socket socket = serverSocket.accept();
      // create a client connection instance for each client which connects to the server
      ClientConnection c = new ClientConnection(socket, this);
      clients.add(c);
      c.start();
    } catch (SocketException e) {
      System.out.println(" Socket closed!");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return list of client connections
   */
  public List<ClientConnection> getConnections() {
    return this.clients;
  }

  /**
   * Stops the server and send a broadcast message to all clients that server is shutting down.
   * 
   * @author skaur
   */
  public void stopServer() {
    try {
      if (this.getConnections().size() == 0) {
        this.interrupt();
        this.serverSocket.close();
        this.datagramSocket.close();
        this.isRunning = false;
        System.out.println("Server is shutting down....");
      }

    } catch (SocketException e) {
      System.out.println("Socket closed");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  
  /**************************************************
   *                                                *
   *                Getter and Setter               *
   *                                                *
   *************************************************/

  public int getPort() {
    return this.port;
  }

  public int getNoOfPlayer() {
    return this.noOfPlayer;
  }

  public void setController(HostGameLobbyController lobbyController) {
    this.lobbyController = lobbyController;
  }

  public HostGameLobbyController getHostLobbyController() {
    return this.lobbyController;
  }

  public ClientConnection getClientConnection() {
    return this.connection;
  }

  public InetAddress getIpAddress() {
    return this.ipAddress;
  }

  public void setBoardController(BoardController boardController) {
    this.boardController = boardController;
  }

  public BoardController getBoardController() {
    return this.boardController;
  }
  
}
