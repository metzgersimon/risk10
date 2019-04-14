package gui;

import game.Player;
import game.PlayerColor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Main;
import java.util.ArrayList;
import game.*;

/**
 * @author liwang Controller class for ProfileSelectionGUI
 */
public class ProfileSelectionGUIController {

  /**
   * the total number of profiles
   */
  public static int count = 0;

  /**
   * which profile will be edited
   */
  public static int editNr;

  /**
   * fields to save and order names and images of profiles
   */
  public static String[] names = new String[5];
  public static Image[] images = new Image[5];

  @FXML
  private Label name1, name2, name3, name4, name5;
  private ArrayList<Label> nameLabels = new ArrayList<>();

  @FXML
  private ImageView image1, image2, image3, image4, image5;
  private ArrayList<ImageView> imageviews = new ArrayList<>();

  @FXML
  private Button edit1, edit2, edit3, edit4, edit5;
  private ArrayList<Button> editButtons = new ArrayList<>();

  @FXML
  private Button back;

  @FXML
  private Button createNewProfile;

  public static Player player;



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
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void handleCreateNewProfileButton(ActionEvent event) {
    CreateProfileGUIController.image = null;
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateProfileGUI.fxml"));
      Parent root = fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Create Profile");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      System.out.println("Can't load CreateProfileGUI.fxml");
    }
  }


  @FXML
  void choose(MouseEvent event) {

    String playerName = "something wrong";
    A: switch (((ImageView) event.getSource()).getId()) {
      case ("image1"):
        playerName = names[0];
        break A;
      case ("image2"):
        playerName = names[1];
        break;
      case ("image3"):
        playerName = names[2];
        break;
      case ("image4"):
        playerName = names[3];
        break;
      case ("image5"):
        playerName = names[4];
        break;
    }

    // System.out.println(playerName);
    player = new Player(playerName, PlayerColor.YELLOW);
    Main.g.addPlayer(player);

    String toOpen = "";
    if (MainMenuGUIController.mode.equals("singlePlayer")) {
      toOpen = "SinglePlayerGUI.fxml";
    } else {
      toOpen = "MultiPlayerGUI.fxml";
    }
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(toOpen));
      Parent root = fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Main Menu");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @author prto loads all saved player profiles
   */
  public void loadProfiles() {
    ProfileManager.readXml();
    ProfileManager.printAllProfiles();

    count = 0;
    for (PlayerProfile x : ProfileManager.profileList.values()) {
      names[count] = x.getName();
      System.out.println("the name is " + names[count]);
      images[count] = x.getImage();
      count++;
    }
    System.out.println("there are " + count + " profiles");//
  }



  public void initialize() {

    loadProfiles();

    nameLabels.add(name1);
    nameLabels.add(name2);
    nameLabels.add(name3);
    nameLabels.add(name4);
    nameLabels.add(name5);

    imageviews.add(image1);
    imageviews.add(image2);
    imageviews.add(image3);
    imageviews.add(image4);
    imageviews.add(image5);

    editButtons.add(edit1);
    editButtons.add(edit2);
    editButtons.add(edit3);
    editButtons.add(edit4);
    editButtons.add(edit5);

    int distance = 1280 / (count + 1) - 100;

    for (int i = 0; i < count; i++) {
      nameLabels.get(i).setText(names[i]);
      nameLabels.get(i).setLayoutX(distance + (distance + 100) * i + 20);
      imageviews.get(i).setImage(images[i]);
      imageviews.get(i).setLayoutX(distance + (distance + 100) * i);
      editButtons.get(i).setOpacity(1);
      editButtons.get(i).setLayoutX(distance + (distance + 100) * i + 20);
    }

    for (int i = count; i < 5; i++) {
      imageviews.get(i).setDisable(true);
      editButtons.get(i).setDisable(true);
    }

    if (count == 5) {
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

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditProfileGUI.fxml"));
      Parent root = fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Edit Profile");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
