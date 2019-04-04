package gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import game.Player;
import game.PlayerColor;

/**
 * @author liwang Controller class for ProfileSelectionGUI
 */
public class ProfileSelectionGUIController {

  public static int count = 0;
  static String[] names = new String[5];
  static Image[] images = new Image[5];
  static int editNr;
  static String n;
  public static Player player;

  @FXML
  private Label name1;

  @FXML
  private Label name2;

  @FXML
  private Label name3;

  @FXML
  private Label name4;

  @FXML
  private Label name5;

  @FXML
  private Button back;

  @FXML
  private Button createNewProfile;

  @FXML
  private ImageView image1;

  @FXML
  private ImageView image2;

  @FXML
  private ImageView image3;

  @FXML
  private ImageView image4;

  @FXML
  private ImageView image5;

  @FXML
  private Button edit1;

  @FXML
  private Button edit2;

  @FXML
  private Button edit3;

  @FXML
  private Button edit4;

  @FXML
  private Button edit5;

  /**
   * Event handle class invoked when back Button clicked
   * 
   * @param event
   */
  @FXML
  void handleBackButton(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuGUI.fxml"));
      Parent root = fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Main Menu");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void handleCreateNewProfileButton(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateProfileGUI.fxml"));
      Parent root = fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Create Profile");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load CreateProfileGUI.fxml");
    }
  }

  @FXML
  void choose(MouseEvent event) {
    // player = new Player("");
    String toOpen = "";
    if (MainMenuGUIController.mode.equals("singlePlayer")) {
      toOpen = "SinglePlayerGUI.fxml";
    } else {
      //only to test connection
      player = new Player("Test", PlayerColor.GREEN);
      toOpen = "MultiPlayerGUI.fxml";
    }
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(toOpen));
      Parent root = fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Main Menu");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @author prto loads all saved player profiles
   */
  public void loadProfiles() {
    ProfileManager.readXml();
    int tempCounter = 0;
    count = 0;
    for (PlayerProfile x : ProfileManager.profileList.values()) {
      names[tempCounter] = x.getName();
      images[tempCounter] = x.getImage();
      tempCounter++;
      count++;
    }
    System.out.println(count);
  }

  public void initialize() {

    loadProfiles();

    if (count == 1) {
      name1.setText(names[0]);
      name1.setLayoutX(560);

      image1.setImage(images[0]);
      image1.setLayoutX(540);

      edit1.setOpacity(1);
      edit1.setLayoutX(560);
    }

    if (count == 2) {
      name1.setText(names[0]);
      name2.setText(names[1]);
      name1.setLayoutX(347);
      name2.setLayoutX(774);

      image1.setImage(images[0]);
      image2.setImage(images[1]);
      image1.setLayoutX(327);
      image2.setLayoutX(754);

      edit1.setOpacity(1);
      edit1.setLayoutX(347);
      edit2.setOpacity(1);
      edit2.setLayoutX(774);
    }
    if (count == 3) {
      name1.setText(names[0]);
      name2.setText(names[1]);
      name3.setText(names[2]);
      name1.setLayoutX(240);
      name2.setLayoutX(560);
      name3.setLayoutX(880);

      image1.setImage(images[0]);
      image2.setImage(images[1]);
      image3.setImage(images[2]);
      image1.setLayoutX(220);
      image2.setLayoutX(540);
      image3.setLayoutX(860);

      edit1.setOpacity(1);
      edit1.setLayoutX(240);
      edit2.setOpacity(1);
      edit2.setLayoutX(560);
      edit3.setOpacity(1);
      edit3.setLayoutX(880);
    }
    if (count == 4) {
      name1.setText(names[0]);
      name2.setText(names[1]);
      name3.setText(names[2]);
      name4.setText(names[3]);
      name1.setLayoutX(176);
      name2.setLayoutX(432);
      name3.setLayoutX(688);
      name4.setLayoutX(944);

      image1.setImage(images[0]);
      image2.setImage(images[1]);
      image3.setImage(images[2]);
      image4.setImage(images[3]);
      image1.setLayoutX(156);
      image2.setLayoutX(412);
      image3.setLayoutX(668);
      image4.setLayoutX(924);

      edit1.setOpacity(1);
      edit1.setLayoutX(176);
      edit2.setOpacity(1);
      edit2.setLayoutX(432);
      edit3.setOpacity(1);
      edit3.setLayoutX(688);
      edit4.setOpacity(1);
      edit4.setLayoutX(940);
    }

    if (count == 5) {
      name1.setText(names[0]);
      name2.setText(names[1]);
      name3.setText(names[2]);
      name4.setText(names[3]);
      name5.setText(names[4]);
      name1.setLayoutX(133);
      name2.setLayoutX(346);
      name3.setLayoutX(559);
      name4.setLayoutX(772);
      name5.setLayoutX(985);

      image1.setImage(images[0]);
      image2.setImage(images[1]);
      image3.setImage(images[2]);
      image4.setImage(images[3]);
      image5.setImage(images[4]);
      image1.setLayoutX(113);
      image2.setLayoutX(326);
      image3.setLayoutX(539);
      image4.setLayoutX(752);
      image5.setLayoutX(965);

      edit1.setOpacity(1);
      edit1.setLayoutX(133);
      edit2.setOpacity(1);
      edit2.setLayoutX(346);
      edit3.setOpacity(1);
      edit3.setLayoutX(559);
      edit4.setOpacity(1);
      edit4.setLayoutX(772);
      edit5.setOpacity(1);
      edit5.setLayoutX(985);

      createNewProfile.setDisable(true);
    }
  }

  @FXML
  void edit(ActionEvent event) {

    switch (((Button) event.getSource()).getId()) {
      case ("edit1"):
        editNr = 0;
        break;
      case ("edit2"):
        editNr = 1;
        break;
      case ("edit3"):
        editNr = 2;
        break;
      case ("edit4"):
        editNr = 3;
        break;
      case ("edit5"):
        editNr = 4;
        break;
    }

    n = names[editNr];

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditProfileGUI.fxml"));
      Parent root = fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Edit Profile");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
