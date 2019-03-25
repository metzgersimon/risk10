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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateProfileGUIController {

  private String username = null;
  public static Image image = null;
  public static int id = 0;


  @FXML
  private Button conform;

  @FXML
  private TextField name;

  @FXML
  private ImageView profileImage;

  @FXML
  void chooseImage(MouseEvent event) {
    // FileChooser fileChooser = new FileChooser();
    // File file = fileChooser.showOpenDialog(null);
    //
    // FileChooser.ExtensionFilter jpg = new FileChooser.ExtensionFilter("JPG files (*.jpg)",
    // "*.JPG");
    // fileChooser.getExtensionFilters().add(jpg);
    // fileChooser.setTitle("Select a profile image");
    // fileChooser.setInitialDirectory(
    // new File(System.getProperty("user.home"), ".risk10/resources/avatars"));
    //
    // try {
    // BufferedImage bufferedImage = ImageIO.read(file);
    // Image image = SwingFXUtils.toFXImage(bufferedImage, null);
    // profileImage.setImage(image);
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    username = name.getText();

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfileImagePickerGUI.fxml"));
      AnchorPane root = (AnchorPane) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Profile Image Selection");
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @FXML
  void conform(ActionEvent event) {
    username = name.getText();

    boolean nameAvailable = true;
    boolean nameNotEmpty = true;

    for (int i = 0; i <= ProfileSelectionGUIController.count; i++) {
      if (username.equals(ProfileSelectionGUIController.names[i])) {
        nameAvailable = false;
        showNameUnavilable();
        break;
      }
      if (username.equals("")) {
        nameNotEmpty = false;
        showNameEmpty();
      }
    }

    if (nameAvailable && nameNotEmpty) {
      image = profileImage.getImage();
      ProfileSelectionGUIController.images[ProfileSelectionGUIController.count] = image;
      ProfileSelectionGUIController.names[ProfileSelectionGUIController.count] = username;
      ProfileSelectionGUIController.count++;

       ProfileManager.addNewProfile(username, id);
       ProfileManager.readXml();
       ProfileManager.printAllProfiles();

      try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfileSelectionGUI.fxml"));
        BorderPane root = (BorderPane) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Profile Selection");
        stage.setScene(new Scene(root));
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
      } catch (Exception e) {
        System.out.println("Can't load ProfileSelectionGUI.fxml");
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
    VBox vbox = new VBox(new Text("The name is already exist, please give another name"));
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

  public void initialize() {
    if (image != null) {
      profileImage.setImage(image);
    }
    if (username != null) {
      name.setText(username);

    }

  }
}
