package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EnterIPGUIController {

  @FXML
  private Button back;

  @FXML
  private Button connect;

  @FXML
  private TextField ip;

  @FXML
  void back(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SinglePlayerGUI.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setTitle("Single Player");
      stage.setScene(new Scene(root));
      stage.show();
      ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
      System.out.println("Can't load SinglePlayerGUI.fxml");
      e.printStackTrace();
    }
  }

  @FXML
  void connect(ActionEvent event) {

  }

}
