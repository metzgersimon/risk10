package gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import main.Main;

public class RuleBookPopUp implements Initializable {

  @FXML
  private TextArea ruleBook;

  @FXML
  private Pane grayPane;

  /**
   * @author prto updates content
   */
  public void initialize(URL location, ResourceBundle resources) {
    ruleBook.setText(game.TutorialMessages.ruleBook);
  }

  /**
   * @author prto handle press on background to close
   */
  public synchronized void clickBack() {
    Platform.runLater(new Runnable() {
      public void run() {
        Main.stagePanes.close();
      }
    });
  }
}
