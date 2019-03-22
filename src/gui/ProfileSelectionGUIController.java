package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author liwang Controller class for ProfileSelectionGUI
 */
public class ProfileSelectionGUIController {

  @FXML
  private Button back;

  @FXML
  private Button createNewProfile;
  
  @FXML
  private ImageView image;


  /**
   * Event handle class invoked when back Button clicked to go back to the MainMenuGUI
   * 
   * @param event
   */
  @FXML
  void handleBackButton(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Main Menu");
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load MainMenuGUI.fxml");
    }
  }

  @FXML
  void handleCreateNewProfileButton(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateProfileGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Create Profile");
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load CreateProfileGUI.fxml");
    }
  }
  
  @FXML
  void select(MouseEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MultiPlayerGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Multi Player");
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load MultiPlayerGUI.fxml");
      e.printStackTrace();
    }
  }

}
