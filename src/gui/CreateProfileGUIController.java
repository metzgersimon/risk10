package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CreateProfileGUIController {

  @FXML
  private Button conform;

  @FXML
  private TextField name;

  @FXML
  private ImageView profileImage;

  @FXML
  void chooseImage(MouseEvent event) {
    FileChooser fileChooser = new FileChooser();
    File file = fileChooser.showOpenDialog(null);

    FileChooser.ExtensionFilter jpg = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
    fileChooser.getExtensionFilters().add(jpg);
    fileChooser.setTitle("Select a profile image");
    fileChooser.setInitialDirectory(
        new File(System.getProperty("user.home"), ".risk10/resources/avatars"));

    try {
      BufferedImage bufferedImage = ImageIO.read(file);
      Image image = SwingFXUtils.toFXImage(bufferedImage, null);
      profileImage.setImage(image);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @FXML
  void conform(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfileSelectionGUI.fxml"));
      BorderPane root = (BorderPane) fxmlLoader.load();
      Stage stage = new Stage();

      Label username = new Label();
      username.setText(name.getText());


      root.getChildren().add(username);
      // root.getChildren().add(name);
      
      

      stage.setTitle("Profile Selection");
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load ProfileSelectionGUI.fxml");
    }
  }

}
