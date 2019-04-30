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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
  private ImageView imgAI;
  @FXML
  private Button addAI;
  @FXML
  private Button reduceAI;
  @FXML
  private Label numberAI;
  @FXML
  private ImageView aiIcon;
  private String numberOfAiString;
  private int numberOfAi;

  @FXML
  private CheckBox checkTips;

  // @FXML
  // public void initialize() {
  // ObservableList<String> numberOfAis = FXCollections.observableArrayList("1", "2", "3", "4",
  // "5");
  // addAi.setItems(numberOfAis);
  // }


  @FXML
  void back(ActionEvent event) {
    // Main.g.removePlayer();

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfileSelectionGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Profile Selection");
      Main.sceneMain.setRoot(root);
      stage.setScene(Main.sceneMain);
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
    Main.g.setPlayers(new ArrayList<>());
    startGame.setDisable(true);
  }

  @FXML
  void startGame(ActionEvent event) {
    try {
      // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BoardGUI.fxml"));
      // Parent root = (Parent) fxmlLoader.load();
      Stage stage = main.Main.stage;
      // stage.setTitle("Board");
      // Main.b = fxmlLoader.getController();
      Main.b.setMain(this);
      stage.setScene(Main.boardScene);
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("-----------------------------");
    Main.g.initGame();
    System.out.println("2" + Main.g.getGameState());
  }

  /**
   * @author smetzger
   * @author pcoberge
   */
  public void handleAddAi() {
    startGame.setDisable(false);
    imgAI.setVisible(true);
    reduceAI.setDisable(false);



    // numberOfAiString = addAi.getSelectionModel().getSelectedItem().toString();
    // numberOfAi = Integer.parseInt(numberOfAiString);
    //
    // clearPane();

    if (Main.g.getPlayers().size() < 6) {
      // for (int i = 0; i < numberOfAi; i++) {
      Player p = null;
      // ImageView aiIcon = new ImageView();
      // aiIcon.setImage(
      // new Image(getClass().getResource("/ressources/gui/robot2.png").toString(), true));
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
      numberAI.setText(Main.g.getPlayers().size() - 1 + "");
      // System.out.println(p.getName());

      // iconPane.getChildren().add(aiIcon);
      // System.out.println("IMAGE ADDED");

      // }
    }
    if (Main.g.getPlayers().size() == 6) {
      addAI.setDisable(true);
    }

  }

  public void reduceAi(ActionEvent e) {
    Main.g.removePlayer();
    numberAI.setText(Main.g.getPlayers().size() - 1 + "");
    addAI.setDisable(false);

    if (Main.g.getPlayers().size() < 2) {
      startGame.setDisable(true);
      imgAI.setVisible(false);
      reduceAI.setDisable(true);
    }
  }

  public void handleTips(ActionEvent e) {
    if (this.checkTips.isSelected()) {
      Main.g.setShowTutorialMessages(true);
    } else {
      Main.g.setShowTutorialMessages(false);
    }
  }

  /**
   * Method removes all Ai icons of the vbox and the added players if a new amount of ai players is
   * selected in the combobox
   * 
   * @author smetzger
   */
  public void clearPane() {
    // System.out.println(Main.g.getPlayers().size());
    // System.out.println(Main.g.getPlayers().get(0).getName());
    if (iconPane.getChildren() != null) {
      iconPane.getChildren().clear();
      Main.g.getPlayers().subList(1, Main.g.getPlayers().size()).clear();
    }
  }
}
