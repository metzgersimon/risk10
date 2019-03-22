package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CreateProfileGUIController {

  @FXML
  private Button conform;

  @FXML
  private TextField name;

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
