package gui;

import java.util.ArrayList;
import game.AiPlayerEasy;
import game.AiPlayerHard;
import game.AiPlayerMedium;
import game.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.Main;

public class SinglePlayerGUIController {

  @FXML
  private Button startGame;

  @FXML
  private Button back;

  @FXML
  private Slider difficulty;

  @FXML
  private ComboBox addAi;

  @FXML
  private HBox iconPane;

  @FXML
  private ImageView aiIcon;
  private String numberOfAiString;
  private int numberOfAi;

  @FXML
  public void initialize() {
    ObservableList<String> numberOfAis = FXCollections.observableArrayList("1", "2", "3", "4", "5");
    addAi.setItems(numberOfAis);
  }


  @FXML
  void back(ActionEvent event) {
    Main.g.removePlayer();

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfileSelectionGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Profile Selection");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
    Main.g.setPlayers(null);
    startGame.setDisable(true);
  }

  @FXML
  void startGame(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BoardGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      // stage.setTitle("Board");
      Main.b = fxmlLoader.getController();
      Main.b.setMain(this, Main.g);
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
    Main.g.initGame();
    System.out.println(Main.g.getGameState());
  }

  /**
   * @author smetzger
   * @author pcoberge
   */
  public void handleAddAi() {
    startGame.setDisable(false);

    numberOfAiString = addAi.getSelectionModel().getSelectedItem().toString();
    numberOfAi = Integer.parseInt(numberOfAiString);

    clearPane();

    if (Main.g.getPlayers().size() < 6) {
      for (int i = 0; i < numberOfAi; i++) {
        Player p = null;
        ImageView aiIcon = new ImageView();
        aiIcon.setImage(
            new Image(getClass().getResource("/ressources/gui/robot2.png").toString(), true));
        switch ((int) difficulty.getValue()) {

          case (1):
            p = new AiPlayerEasy();
            break;
          case (2):
            p = new AiPlayerMedium();
            break;
          case (3):
            p = new AiPlayerHard();
            break;
        }
        Main.g.addPlayer(p);
        System.out.println(p.getName());

        iconPane.getChildren().add(aiIcon);
        System.out.println("IMAGE ADDED");

      }
    }
  }


  /**
   * @author smetzger
   */
  public void clearPane() {
    if (iconPane.getChildren() != null) {
      iconPane.getChildren().clear();
      int i = 1;
      while(i < Main.g.getPlayers().size()) {
        Main.g.getPlayers().remove(i);
        i++;
      }
    }
  }
}
