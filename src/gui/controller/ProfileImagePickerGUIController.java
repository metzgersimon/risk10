package gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller class for ProfileImagePickerGUI.
 * 
 * @author liwang
 */
public class ProfileImagePickerGUIController {

  @FXML
  private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9, image10;

  /**
   * this method saved the chosen image and go back to edit or create profile gui.
   * 
   * @author liwang
   * @param event
   */
  @FXML
  void chooseImage(MouseEvent event) {
    String id = ((ImageView) event.getSource()).getId();

    if (EditProfileGUIController.edit) {
      EditProfileGUIController.image = ((ImageView) event.getSource()).getImage();
      EditProfileGUIController.imageId = getId(id);
      try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/EditProfileGUI.fxml"));
        AnchorPane root = (AnchorPane) fxmlLoader.load();
        Stage stage = main.Main.stage;
        stage.setScene(new Scene(root));
        stage.show();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      try {
        CreateProfileGUIController.image = ((ImageView) event.getSource()).getImage();
        CreateProfileGUIController.id = getId(id);
        FXMLLoader fxmlLoader =
            new FXMLLoader(getClass().getResource("/gui/CreateProfileGUI.fxml"));
        AnchorPane root = (AnchorPane) fxmlLoader.load();
        Stage stage = main.Main.stage;
        stage.setScene(new Scene(root));
        stage.show();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * convert the id from string to int since the id in scence builder can not be a simple int.
   * 
   * @author liwang
   * @param id of the image type String
   * @return int the id of image type Integer
   */
  public static int getId(String id) {
    switch (id) {
      case ("image1"):
        return 1;
      case ("image2"):
        return 2;
      case ("image3"):
        return 3;
      case ("image4"):
        return 4;
      case ("image5"):
        return 5;
      case ("image6"):
        return 6;
      case ("image7"):
        return 7;
      case ("image8"):
        return 8;
      case ("image9"):
        return 9;
      case ("image10"):
        return 10;
      default:
        return 0;
    }
  }

}
