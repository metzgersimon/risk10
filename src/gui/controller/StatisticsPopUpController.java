package gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import game.Player;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.Main;

public class StatisticsPopUpController implements Initializable {

  @FXML
  private TitledPane statisticPane;
  @FXML
  private TableView<Player> statistic;

  @FXML
  private TableColumn<Player, Color> c0;

  @FXML
  private TableColumn<Player, Integer> c1;

  @FXML
  private TableColumn<Player, Integer> c2;

  @FXML
  private TableColumn<Player, Integer> c3;

  @FXML
  private Pane grayPane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    openLiveStats();
  }

  /**
   * @author prto opens live statistics panel
   */
  public void openLiveStats() {

    c1.setCellValueFactory(new PropertyValueFactory<>("name"));
    c2.setCellValueFactory(new PropertyValueFactory<>("numberOfTerritories"));
    c3.setCellValueFactory(new PropertyValueFactory<>("numberOfCards"));
    c1.setSortType(TableColumn.SortType.ASCENDING);

    // Main.g.updateLiveStatistics();
    ObservableList<Player> playerList = FXCollections.observableArrayList(Main.g.getPlayers());
    statistic.setItems(playerList);
    statistic.getSortOrder().add(c1);

    statistic.refresh();

  }

  public synchronized void clickBack() {
    Platform.runLater(new Runnable() {
      public void run() {
        Main.stagePanes.close();
      }
    });
  }
}
