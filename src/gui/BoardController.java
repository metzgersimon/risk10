package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

/**
 * 
 * Logik hinter Board
 *
 */
public class BoardController {

  // Views
  @FXML private Button chatRoomButton;
  @FXML private SplitPane splitter;
  @FXML private Button kamschatka;
  
  @FXML private Region eastAust;
  @FXML private Region westAust;


  public BoardGUI_Main boardGui;

  public void setMain(BoardGUI_Main boardGui) {
    this.boardGui = boardGui;
  }

  @FXML
  public void handleClickCRB() {
    if (chatRoomButton.getText().equals("<")) {
      splitter.setDividerPosition(1, 0.8);
      chatRoomButton.setText("Chat Room >");
    } else {
      splitter.setDividerPosition(1, 1);
      chatRoomButton.setText("<");
    }
  }
  
  @FXML
  public void handleButton() {
    System.out.println("TEST");
  }
  
  @FXML
  public void pressed(MouseEvent e) {
    Region b = (Region) e.getSource();
    if (b.getEffect()==null) {
      b.setEffect(new Glow(0.3));
    } else {
      b.setEffect(null);
    }
  }
  
}
