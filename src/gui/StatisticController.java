package gui;

import java.util.ArrayList;
import game.Game;
import game.Player;
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
  private TableView table;
  
  public StatisticController() {
    this.openStats();
  }
  
  /**
   * creates a table with the end game statistics
   * 
   */
  public void openStats() {

    table = new TableView();

    TableColumn<String, Player> c1 = new TableColumn<>("Rank");
    TableColumn<String, Player> c2 = new TableColumn<>("Name");
    TableColumn<String, Player> c3 = new TableColumn<>("Attacks");
    TableColumn<String, Player> c4 = new TableColumn<>("T conquered");
    TableColumn<String, Player> c5 = new TableColumn<>("Session Wins");

    c1.setCellValueFactory(new PropertyValueFactory<>("rank"));
    c2.setCellValueFactory(new PropertyValueFactory<>("name"));
    c3.setCellValueFactory(new PropertyValueFactory<>("numberOfAttacks"));
    c4.setCellValueFactory(new PropertyValueFactory<>("territoriesConquered"));
    c5.setCellValueFactory(new PropertyValueFactory<>("sessionWins"));
    
    table.getColumns().add(c1);
    table.getColumns().add(c2);
    table.getColumns().add(c3);
    table.getColumns().add(c4);
    table.getColumns().add(c5);
    
    for (Player x : Main.g.getPlayers()) {
      table.getItems().add(x);
    }
    
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("StatisticGUI.fxml"));
      Parent root = loader.load();
      Stage stage = main.Main.stage;
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
