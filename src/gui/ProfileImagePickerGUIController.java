package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProfileImagePickerGUIController {

  @FXML
  private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9, image10;

  @FXML
  void chooseImage(MouseEvent event) {
    String id = ((ImageView) event.getSource()).getId();

    // System.out.println(id);
    if (EditProfileGUIController.edit) {
      EditProfileGUIController.image = ((ImageView) event.getSource()).getImage();
      EditProfileGUIController.id = getId(id);
      try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditProfileGUI.fxml"));
        AnchorPane root = (AnchorPane) fxmlLoader.load();
        Stage stage = main.Main.stage;
        stage.setTitle("Edit Profile");
        stage.setScene(new Scene(root));
        stage.show();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      try {
        CreateProfileGUIController.image = ((ImageView) event.getSource()).getImage();
        CreateProfileGUIController.id = getId(id);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateProfileGUI.fxml"));
        AnchorPane root = (AnchorPane) fxmlLoader.load();
        Stage stage = main.Main.stage;
        stage.setTitle("Create Profile");
        stage.setScene(new Scene(root));
        stage.show();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static int getId(String id) {
    int nr = 0;
    switch (id) {
      case ("image1"):
        EditProfileGUIController.id = 1;
        nr = 1;
        break;
      case ("image2"):
        EditProfileGUIController.id = 2;
        nr = 2;
        break;
      case ("image3"):
        EditProfileGUIController.id = 3;
        nr = 3;
        break;
      case ("image4"):
        EditProfileGUIController.id = 4;
        nr = 4;
        break;
      case ("image5"):
        EditProfileGUIController.id = 5;
        nr = 5;
        break;
      case ("image6"):
        EditProfileGUIController.id = 6;
        nr = 6;
        break;
      case ("image7"):
        EditProfileGUIController.id = 7;
        nr = 7;
        break;
      case ("image8"):
        EditProfileGUIController.id = 8;
        nr = 8;
        break;
      case ("image9"):
        EditProfileGUIController.id = 9;
        nr = 9;
        break;
      case ("image10"):
        EditProfileGUIController.id = 10;
        nr = 10;
        break;

    }
    return nr;
  }

  public void initialize() {
    // EditProfileGUIController.image = null;
  }

}
