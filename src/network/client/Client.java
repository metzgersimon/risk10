package network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import game.Player;
import gui.HostGameGUIController;
import gui.HostGameLobbyController;
import gui.JoinGameLobbyController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import main.Main;
import network.Parameter;
import network.messages.JoinGameMessage;
import network.messages.Message;
import network.messages.SendChatMessageMessage;

public class Client extends Thread {



  private InetAddress address;
  private Socket s;
  private Player player;
  private ObjectInputStream fromServer;
  private ObjectOutputStream toServer;
  private boolean active;

  private JoinGameLobbyController controller = null;
  // private HostGameLobbyController hostUi;

  public Client(InetAddress address) {
    this.address = address;
    connect();
  }

  /**
   * Constructor
   * 
   * @param address
   * @param player
   */
  public Client(InetAddress address, Player player) {
    this.address = address;
    this.player = player;
    connect();
  }

  public boolean connect() {
    try {
      this.s = new Socket(address, Parameter.PORT);
      fromServer = new ObjectInputStream(s.getInputStream());
      toServer = new ObjectOutputStream(s.getOutputStream());
      this.active = true;
      System.out.println("Socket opened successfully");
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public void run() {
    transact();
  }

  /**
   * send message to server
   * 
   * @param m
   */
  public void sendMessage(Message m) {
    System.out.println("test send message");
    try {
      toServer.writeObject(m);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void register() {
    JoinGameMessage join = new JoinGameMessage(player);
    this.sendMessage(join);
    // To get response
  }

  public void setController(JoinGameLobbyController controller) {
    this.controller = controller;
  }

  /**
   * handle incoming messages from server
   */
  public void transact() {
    // clientUi = new JoinGameLobbyController();
    // hostUi = new HostGameLobbyController();

    // FXMLLoader loader = new FXMLLoader(getClass().getResource("JoinGameLobby.fxml"));
    // Main.j = loader.getController();
    // Main.j.setMain(this, Main.g);

    while (active) {
      try {
        Message message = (Message) fromServer.readObject();
        switch (message.getType()) {
          case BROADCAST:
            String name = ((SendChatMessageMessage) message).getUsername();
            String content = ((SendChatMessageMessage) message).getMessage();
            System.out.println("from server: " + name + " " + content);
            // clientUi.showMessage(content);
            // main.Main.h.showMessage(content);
            // Main.j.showMessage(content);
            System.out.println("client show " + content);
            // hostUi.showMessage(content);
            // main.Main.j.showMessage(content);
            // hc.showMessage(content);
            System.out.println("host show " + content);
            controller.showMessage(content);
            // Parent root = FXMLLoader.load(getClass().getResource("JoinGameLobby.fxml"));
            // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("JoinGameLobby.fxml"));
            // Parent root = (Parent) fxmlLoader.load();
            // TextArea chat = (TextArea) root.lookup("#lblData");
            // chat.appendText(content);
            break;
          case LEAVE:
            break;
          case DISPLAY:
            break;
        }
      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  /**
   * disconnect the conneciton
   */
  public void disconnect() {
    try {
      active = false;
      this.fromServer.close();
      this.toServer.close();
      s.close();
      System.out.println("Protocol ended");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}

// grobe interface
// mit methoden
// liste von sockets, an alle schicken
//
