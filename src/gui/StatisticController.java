package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import game.Game;
import game.Player;
import game.PlayerColor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;


/**
 * Controls the endgame statistics screen
 * 
 * @author prto
 *
 */

public class StatisticController {

  @FXML
  private TableView<Player> table;

  @FXML
  private TableColumn<Player, Integer> c1;

  @FXML
  private TableColumn<Player, String> c2;

  @FXML
  private TableColumn<Player, Integer> c3;

  @FXML
  private TableColumn<Player, Integer> c4;

  @FXML
  private TableColumn<Player, Integer> c5;
  
   
  /**
   * creates a table with the end game statistics
   * 
   */
  public void intialize() {
    System.out.println("Test");
    /**
    TableColumn<Player, Integer> c1 = new TableColumn<>("Rank");
    TableColumn<Player, String> c2 = new TableColumn<>("Name");
    TableColumn<Player, Integer> c3 = new TableColumn<>("Attacks");
    TableColumn<Player, Integer> c4 = new TableColumn<>("T conquered");
    TableColumn<Player, Integer> c5 = new TableColumn<>("Session Wins");
*/
    c1.setCellValueFactory(new PropertyValueFactory<>("rank"));
    c2.setCellValueFactory(new PropertyValueFactory<>("name"));
    c3.setCellValueFactory(new PropertyValueFactory<>("numberOfAttacks"));
    c4.setCellValueFactory(new PropertyValueFactory<>("territoriesConquered"));
    c5.setCellValueFactory(new PropertyValueFactory<>("sessionWins"));
    
    ArrayList<Player> testList = new ArrayList<Player>();
    testList.add(new Player("Preston", 10, PlayerColor.BLUE));
    
    ObservableList<Player> playerList = FXCollections.observableArrayList(Main.g.getPlayers());   
    table.setItems(playerList);
    
    table.getColumns().add(c1);
    table.getColumns().add(c2);
    table.getColumns().add(c3);
    table.getColumns().add(c4);
    table.getColumns().add(c5);
   
  }

}