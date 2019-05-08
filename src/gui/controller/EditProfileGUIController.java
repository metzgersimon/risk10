package gui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller class for EditProfileGUI
 * 
 * @author liwang
 */
public class EditProfileGUIController {

  int nr;

  public static boolean edit = false;
  public static int imageId = 0;
  public static Image image = null;
  public static String profileName = null;

  private static boolean nameChanged = false;
  private static boolean imageChanged = false;

  @FXML
  private Button save;
  @FXML
  private TextField name;
  @FXML
  private ImageView profileImage;
  @FXML
  private Button delete;

  @FXML
  private Label matchesPlayed;

  @FXML
  private Label matchesWon;

  @FXML
  private Label matchesLost;

  @FXML
  private Label territoriesConquered;

  /**
   * Event handle class invoked when the profile image is clicked
   * 
   * @author liwang
   * @param event
   */
  @FXML
  void chooseImage(MouseEvent event) {
    edit = true;
    if (!profileName.equals(name.getText())) {
      nameChanged = true;
      profileName = name.getText();
    }
    imageChanged = true;

    try {
      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/gui/ProfileImagePickerGUI.fxml"));
      AnchorPane root = (AnchorPane) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Profile Image Selection");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Event handle class invoked when delete button is clicked
   * 
   * @author liwang
   * @param event
   */
  @FXML
  void delete(ActionEvent event) {
    Stage dialogStage = new Stage();
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    VBox vbox = new VBox(new Text("Are you sure to delete this profile?"));
    Button okButton = new Button("Yes");
    Button noButton = new Button("No");
    vbox.getChildren().add(okButton);
    vbox.getChildren().add(noButton);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(15));
    dialogStage.setScene(new Scene(vbox));
    dialogStage.show();

    okButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        /**
         * delete the profile in xml
         */
        ProfileManager.deleteProfile(ProfileSelectionGUIController.names[nr]);

        dialogStage.close();
        try {
          FXMLLoader fxmlLoader =
              new FXMLLoader(getClass().getResource("/gui/ProfileSelectionGUI.fxml"));
          BorderPane root = (BorderPane) fxmlLoader.load();
          Stage stage = main.Main.stage;
          stage.setTitle("Profile Selection");
          stage.setScene(new Scene(root));
          stage.show();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });

    noButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        dialogStage.close();
      }
    });

  }

  /**
   * Event handle class invoked when save button is clicked
   * 
   * @author liwang
   * @param event
   */
  @FXML
  void save(ActionEvent event) {
    nameChanged = false;
    imageChanged = false;
    String profileName = name.getText();

    boolean nameAvailable = true;
    boolean nameNotEmpty = true;

    for (int i = 0; i < ProfileSelectionGUIController.count; i++) {
      if (profileName.equals(ProfileSelectionGUIController.names[i]) && i != nr) {
        nameAvailable = false;
        showNameUnavilable();
        break;
      }
      if (profileName.equals("")) {
        nameNotEmpty = false;
        showNameEmpty();
        break;
      }
    }

    if (nameAvailable && nameNotEmpty) {
      /**
       * edit the profile in xml
       */
      ProfileManager.editProfile(ProfileSelectionGUIController.names[nr], profileName, imageId);
      try {
        FXMLLoader fxmlLoader =
            new FXMLLoader(getClass().getResource("/gui/ProfileSelectionGUI.fxml"));
        BorderPane root = (BorderPane) fxmlLoader.load();
        Stage stage = main.Main.stage;
        stage.setTitle("Profile Selection");
        stage.setScene(new Scene(root));
        stage.show();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  private void showNameEmpty() {
    Stage dialogStage = new Stage();
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    VBox vbox = new VBox(new Text("The name should not be empty"));
    Button okButton = new Button("OK");
    okButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        dialogStage.close();

      }
    });

    vbox.getChildren().add(okButton);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(15));
    dialogStage.setScene(new Scene(vbox));
    dialogStage.show();
  }

  private void showNameUnavilable() {
    Stage dialogStage = new Stage();
    dialogStage.initModality(Modality.APPLICATION_MODAL);
    VBox vbox = new VBox(new Text("This name already exists. Try a different one"));
    Button okButton = new Button("OK");
    okButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        dialogStage.close();

      }
    });

    vbox.getChildren().add(okButton);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(15));
    dialogStage.setScene(new Scene(vbox));
    dialogStage.show();

  }

  /**
   * initialize the gui to ensure consistency
   * 
   * @author liwang
   */
  public void initialize() {
    edit = false;
    nr = ProfileSelectionGUIController.editNr;

    if (!nameChanged) {
      profileName = ProfileSelectionGUIController.names[nr];
    }
    name.setText(profileName);

    if (!imageChanged) {
      image = (ProfileManager.profileList.get(profileName)).getImage();
      imageId = (ProfileManager.profileList.get(profileName)).getIdInt();
    }
    profileImage.setImage(image);

    // @prto Load stats
    matchesPlayed.setText(
        "Matches played:\t\t    " + ProfileManager.profileList.get(profileName).getMatchesPlayed());
    matchesWon.setText(
        "Matches won:\t\t    " + ProfileManager.profileList.get(profileName).getMatchesWon());
    matchesLost.setText(
        "Matches lost:\t\t    " + ProfileManager.profileList.get(profileName).getMatchesLost());
    territoriesConquered.setText("Territories conquered: "
        + ProfileManager.profileList.get(profileName).getTerritoriesConquered());

  }

}
