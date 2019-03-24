package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditProfileGUIController {

  int nr;

  @FXML
  private Button save;

  @FXML
  private TextField name;

  @FXML
  private ImageView profileImage;

  @FXML
  private Button delete;

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
        // while(ProfileSelectionGUIController.count > ProfileSelectionGUIController.editNr) {
        //
        // }
        //
        ProfileSelectionGUIController.names[nr] = null;
        ProfileSelectionGUIController.images[nr] = null;
        ProfileSelectionGUIController.count--;


        dialogStage.close();
        try {
          FXMLLoader fxmlLoader =
              new FXMLLoader(getClass().getResource("ProfileSelectionGUI.fxml"));
          BorderPane root = (BorderPane) fxmlLoader.load();
          Stage stage = new Stage();
          stage.setTitle("Profile Selection");
          stage.setScene(new Scene(root));
          stage.show();
          ((Node) event.getSource()).getScene().getWindow().hide();
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

  @FXML
  void save(ActionEvent event) {
    ProfileSelectionGUIController.names[nr] = name.getText();
    Image image = profileImage.getImage();
    ProfileSelectionGUIController.images[nr] = image;

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfileSelectionGUI.fxml"));
      BorderPane root = (BorderPane) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Profile Selection");
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void initialize() {
    nr = ProfileSelectionGUIController.editNr;
    profileImage.setImage(ProfileSelectionGUIController.images[nr]);
    name.setText(ProfileSelectionGUIController.names[nr]);
  }

}
