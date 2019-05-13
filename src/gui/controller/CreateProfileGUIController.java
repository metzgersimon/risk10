package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controller class of CreateProfile GUI
 * 
 * @author liwang
 *
 */
public class CreateProfileGUIController {

  public static String username = null;
  public static Image image = null;
  public static int id = 0;
  public static boolean toInizialied = false; // if there is a need to initialize name or image

  private Stage stage = main.Main.stage;

  @FXML
  private Button save;
  @FXML
  private TextField name;
  @FXML
  private ImageView profileImage;
  @FXML
  private Pane warningPane;
  @FXML
  private Label warning;
  @FXML
  private Button ok;

  /**
   * this method opens ProfileImagePickerGUI when the default image file is clicked
   * 
   * @author liwang
   * @param event
   */
  @FXML
  void chooseImage(MouseEvent event) {
    toInizialied = true; // gui need to be initialized if user comes back to this
    username = name.getText();
    try {
      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/gui/ProfileImagePickerGUI.fxml"));
      AnchorPane root = (AnchorPane) fxmlLoader.load();
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * this method save the created profile and go back to profileSelection GUI, to be successfully
   * saved, the name should not be empty or repeated and an image is selected
   * 
   * @author liwang
   * @param event save the created profile and go back to profileSelection GUI
   */
  @FXML
  void save(ActionEvent event) {
    toInizialied = false;
    username = name.getText();

    boolean nameAvailable = true;
    boolean nameNotEmpty = true;

    for (int i = 0; i <= ProfileSelectionGUIController.count; i++) {
      if (username.equals(ProfileSelectionGUIController.names[i])) {
        nameAvailable = false;
        warning.setText("The name already exists, please enter another name");
        warningPane.toFront();
        name.setDisable(true);
        save.setDisable(true);
        break;
      }
      if (username.equals("")) {
        nameNotEmpty = false;
        warning.setText("The name should not be empty");
        warningPane.toFront();
        name.setDisable(true);
        save.setDisable(true);
      }
    }

    if (image == null) {
      warning.setText("Please choose your profile image");
      warningPane.toFront();
      name.setDisable(true);
      save.setDisable(true);
    }

    if (nameAvailable && nameNotEmpty && image != null) {
      image = profileImage.getImage();
      ProfileSelectionGUIController.images[ProfileSelectionGUIController.count] = image;
      ProfileSelectionGUIController.names[ProfileSelectionGUIController.count] = username;
      ProfileSelectionGUIController.count++;

      // add the profile in the xml
      ProfileManager.addNewProfile(username, id);

      try {
        FXMLLoader fxmlLoader =
            new FXMLLoader(getClass().getResource("/gui/ProfileSelectionGUI.fxml"));
        BorderPane root = (BorderPane) fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Can't load ProfileSelectionGUI.fxml");
      }
    }

  }

  /**
   * this method is to bring the warning to the back after user confirm the warning
   * 
   * @author liwang
   * @param event
   */
  @FXML
  void handleOk(ActionEvent event) {
    warningPane.toBack();
    name.setDisable(false);
    save.setDisable(false);
  }

  /**
   * this method is to initialize this gui, if there is already name entered, it should be shown,
   * when the image is chosen and go back to this gui, the chosen image will also be shown
   * 
   * @author liwang
   */
  public void initialize() {
    if (toInizialied) {
      if (image != null) {
        profileImage.setImage(image);
      }
      if (username != null) {
        name.setText(username);
      }
    }

  }
}
