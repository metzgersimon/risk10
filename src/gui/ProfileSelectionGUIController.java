package gui;

import game.Game;
import game.Player;
import game.PlayerColor;
import game.TestGame;
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

/**
 * Controller class for ProfileSelectionGUI
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
   * the selected Player name
   */
  public static String selectedPlayerName = null;

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

  /**
   * Event handle class invoked when back Button clicked
   * 
   * @author liwang
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

  /**
   * Event handle class invoked when create new Profile Button clicked
   * 
   * @author liwang
   * @param event
   */
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

  /**
   * Event handle class invoked when edit Button clicked
   * 
   * @author liwang
   * @param event
   */
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

  /**
   * Event handle class invoked when a profile is clicked
   * 
   * @author liwang
   * @param event
   */
  @FXML
  void choose(MouseEvent event) {
    switch (((ImageView) event.getSource()).getId()) {
      case ("image1"):
        selectedPlayerName = names[0];
        break;
      case ("image2"):
        selectedPlayerName = names[1];
        break;
      case ("image3"):
        selectedPlayerName = names[2];
        break;
      case ("image4"):
        selectedPlayerName = names[3];
        break;
      case ("image5"):
        selectedPlayerName = names[4];
        break;
    }

    Main.g = new TestGame();
    System.out.println(Main.g.getGameState().toString());
    String toOpen = "";
    if (MainMenuGUIController.mode.equals("singlePlayer")) {
      toOpen = "SinglePlayerGUI.fxml";
      Main.g.addPlayer(new Player(selectedPlayerName, PlayerColor.YELLOW, Main.g));
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
   * loads all saved player profiles
   * 
   * @author prto
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


  /**
   * load all saved profiles, calculate the position and show them
   * 
   * @author liwang
   */
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

}
