package gui.controller;



import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import game.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.Main;


/**
 * Controls the endgame statistics screen.
 * 
 * @author prto
 *
 */

public class StatisticController implements Initializable {
  @FXML
  private Button back;

  @FXML
  private Button closeGame;

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
   * Creates a table with the end game statistics.
   * 
   * @author prto
   * 
   */
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    c1.setCellValueFactory(new PropertyValueFactory<>("rank"));
    c2.setCellValueFactory(new PropertyValueFactory<>("name"));
    c3.setCellValueFactory(new PropertyValueFactory<>("numberOfAttacks"));
    c4.setCellValueFactory(new PropertyValueFactory<>("territoriesConquered"));
    c5.setCellValueFactory(new PropertyValueFactory<>("sessionWins"));

    c1.setSortType(TableColumn.SortType.ASCENDING);
    HashSet<Player> hash = new HashSet<Player>(Main.g.getAllPlayers());
    hash.addAll(Main.g.getPlayers());
    ObservableList<Player> playerList = FXCollections.observableArrayList(hash);
    table.setItems(playerList);
    for (Player p : playerList) {
      System.out.println(p);
    }
    table.getSortOrder().add(c1);

    updateProfileStats();
  }

  /**
   * 
   * @author prto
   * 
   *         update player profile statistics
   */
  public void updateProfileStats() {

    Player thisPlayer = null;

    // Get this player
    for (Player p : Main.g.getAllPlayers()) {
      if (p.getName().equals(ProfileManager.getSelectedProfile().getName())) {
        thisPlayer = p;
      }
    }

    // Update matchedPlayed
    ProfileManager.getSelectedProfile().incrementMatchesPlayed();

    // Update territoriesConquered
    ProfileManager.getSelectedProfile()
        .incrementTerritoriesConquered(thisPlayer.getTerritoriesConquered());

    // Update matchesWon / matchesLost
    if (thisPlayer.rank == 1) {
      ProfileManager.getSelectedProfile().incrementMatchesWon();
      ProfileManager.getSelectedProfile().incrementSessionWins();
    } else {
      ProfileManager.getSelectedProfile().incrementMatchesLost();
    }

    // Update session stat
    main.Main.session = true;

    ProfileManager.saveXml();
  }

  public void backToMenu(ActionEvent e) {
    try {
      BorderPane root =
          (BorderPane) FXMLLoader.load(getClass().getResource("/gui/MainMenuGUI.fxml"));
      Main.stage.setScene(new Scene(root));
      Main.stage.show();

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/BoardGUI.fxml"));
      AnchorPane board = (AnchorPane) fxmlLoader.load();
      Main.b = fxmlLoader.getController();

    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

  /**
   * @author prto closes the game
   */
  public void closeGame() {
    main.Main.stagePanes.close();
    main.Main.stage.close();
  }



}
