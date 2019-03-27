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

public class EditProfileGUIController {


  int nr = ProfileSelectionGUIController.editNr;

  public static int id = 0;
  public static boolean edit = false;

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
    edit = true;
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfileImagePickerGUI.fxml"));
      AnchorPane root = (AnchorPane) fxmlLoader.load();
      Stage stage = main.Main.stage;
      stage.setTitle("Profile Image Selection");
      stage.setScene(new Scene(root));
      stage.show();
      // ((Node) event.getSource()).getScene().getWindow().hide();
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
        ProfileManager.deleteProfile(name.getText());

        while (nr < ProfileSelectionGUIController.count - 1) {
          ProfileSelectionGUIController.names[nr] = ProfileSelectionGUIController.names[nr + 1];
          ProfileSelectionGUIController.images[nr] = ProfileSelectionGUIController.images[nr + 1];
          nr++;
        }
        ProfileSelectionGUIController.count--;


        dialogStage.close();
        try {
          FXMLLoader fxmlLoader =
              new FXMLLoader(getClass().getResource("ProfileSelectionGUI.fxml"));
          BorderPane root = (BorderPane) fxmlLoader.load();
          Stage stage = main.Main.stage;
          stage.setTitle("Profile Selection");
          stage.setScene(new Scene(root));
          stage.show();
          // ((Node) event.getSource()).getScene().getWindow().hide();
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
      ProfileSelectionGUIController.names[nr] = profileName;
      Image image = profileImage.getImage();
      ProfileSelectionGUIController.images[nr] = image;

      ProfileManager.editProfile(profileName, id);

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
    edit = false;
    nr = ProfileSelectionGUIController.editNr;
    profileImage.setImage(ProfileSelectionGUIController.images[nr]);
    name.setText(ProfileSelectionGUIController.names[nr]);
  }

}
