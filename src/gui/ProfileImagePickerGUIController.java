package gui;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ProfileImagePickerGUIController {

  @FXML
  private ImageView image3;

  @FXML
  private ImageView image10;

  @FXML
  private ImageView image5;

  @FXML
  private ImageView image7;

  @FXML
  private ImageView image6;

  @FXML
  private ImageView image2;

  @FXML
  private ImageView image8;

  @FXML
  private ImageView image9;

  @FXML
  private ImageView image4;

  @FXML
  private ImageView image1;

  @FXML
  void chooseImage(MouseEvent event) {
    CreateProfileGUIController.image = ((ImageView) event.getSource()).getImage();
    String id = ((ImageView) event.getSource()).getId();
    switch (id) {
      case ("image1"):
        CreateProfileGUIController.id = 1;
        break;
      case ("image2"):
        CreateProfileGUIController.id = 2;
        break;
      case ("image3"):
        CreateProfileGUIController.id = 3;
        break;
      case ("image4"):
        CreateProfileGUIController.id = 4;
        break;
      case ("image5"):
        CreateProfileGUIController.id = 5;
        break;
      case ("image6"):
        CreateProfileGUIController.id = 6;
        break;
      case ("image7"):
        CreateProfileGUIController.id = 7;
        break;
      case ("image8"):
        CreateProfileGUIController.id = 8;
        break;
      case ("image9"):
        CreateProfileGUIController.id = 9;
        break;
      case ("image10"):
        CreateProfileGUIController.id = 10;
        break;
    }

    System.out.println(id);

    // System.out.println("is " + image);

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateProfileGUI.fxml"));
      AnchorPane root = (AnchorPane) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Create Profile");
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
