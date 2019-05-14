package gui.controller;

import game.AiPlayer;
import game.Continent;
import game.GameState;
import game.Player;
import game.PlayerColor;
import game.Territory;
import game.TutorialMessages;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import main.Main;
import main.Parameter;
import network.client.Client;
import network.messages.GameMessageMessage;
import network.messages.game.DistributeArmyMessage;
import network.messages.game.LeaveGameResponseMessage;
import network.messages.game.SelectInitialTerritoryMessage;
import network.messages.game.SkipgamestateMessage;
import network.messages.SendAllianceMessage;


/**
 * 
 * Class defines the controller class for the game board.
 *
 */
public class BoardController implements Initializable {

  private SinglePlayerGUIController boardGui;
  private Territory selectedTerritory = null;
  private Territory selectedTerritoryAttacked = null;

  /**
   * Elements for the chat.
   */
  @FXML
  private Button chatRoomButton;
  @FXML
  private SplitPane splitter;
  @FXML
  private TextField playername;
  @FXML
  private TextArea chat;
  @FXML
  private Button send;
  @FXML
  private TextField messages;

  /**
   * Elements to connect Region, Label and Territory.
   */
  @FXML
  private Region alaska, northwestTerritory, alberta, westernUnitedStates, centralAmerica,
      greenland, ontario, quebec, easternUnitedStates, venezuela, peru, brazil, argentina, iceland,
      scandinavia, ukraine, greatBritain, northernEurope, westernEurope, southernEurope,
      northAfrica, egypt, congo, eastAfrica, southAfrica, madagascar, siberia, ural, china,
      afghanistan, middleEast, india, siam, yakutsk, irkutsk, mongolia, japan, kamchatka, indonesia,
      newGuinea, westernAustralia, easternAustralia;
  @FXML
  private Label alaskaHeadline, northwestTerritoryHeadline, albertaHeadline,
      westernUnitedStatesHeadline, centralAmericaHeadline, greenlandHeadline, ontarioHeadline,
      quebecHeadline, easternUnitedStatesHeadline, venezuelaHeadline, peruHeadline, brazilHeadline,
      argentinaHeadline, icelandHeadline, scandinaviaHeadline, ukraineHeadline,
      greatBritainHeadline, northernEuropeHeadline, westernEuropeHeadline, southernEuropeHeadline,
      northAfricaHeadline, egyptHeadline, congoHeadline, eastAfricaHeadline, southAfricaHeadline,
      madagascarHeadline, siberiaHeadline, uralHeadline, chinaHeadline, afghanistanHeadline,
      middleEastHeadline, indiaHeadline, siamHeadline, yakutskHeadline, irkutskHeadline,
      mongoliaHeadline, japanHeadline, kamchatkaHeadline, indonesiaHeadline, newGuineaHeadline,
      westernAustraliaHeadline, easternAustraliaHeadline;
  @FXML
  private Label alaskaNoA, northwestTerritoryNoA, albertaNoA, westernUnitedStatesNoA,
      centralAmericaNoA, greenlandNoA, ontarioNoA, quebecNoA, easternUnitedStatesNoA, venezuelaNoA,
      peruNoA, brazilNoA, argentinaNoA, icelandNoA, scandinaviaNoA, ukraineNoA, greatBritainNoA,
      northernEuropeNoA, westernEuropeNoA, southernEuropeNoA, northAfricaNoA, egyptNoA, congoNoA,
      eastAfricaNoA, southAfricaNoA, madagascarNoA, siberiaNoA, uralNoA, chinaNoA, afghanistanNoA,
      middleEastNoA, indiaNoA, siamNoA, yakutskNoA, irkutskNoA, mongoliaNoA, japanNoA, kamchatkaNoA,
      indonesiaNoA, newGuineaNoA, westernAustraliaNoA, easternAustraliaNoA;
  @FXML
  private Label armiesToDistribute;
  @FXML
  private Circle circle;

  /**
   * Elements that handle leave option.
   */
  @FXML
  private Shape circleLeave;
  @FXML
  private ImageView imageLeave;
  @FXML
  private ImageView winnerCrown;

  /**
   * Element which handles the function to skip a game state.
   * 
   */
  @FXML
  private Pane skip;
  @FXML
  private Button changeGameState;
  @FXML
  private Label gameState;
  @FXML
  private static ProgressBar progress;


  /**
   * Elements for statistic pane.
   */
  @FXML
  private TableView<Player> statistic;
  @FXML
  private TableColumn<Player, PlayerColor> c0;
  @FXML
  private TableColumn<Player, Integer> c1;
  @FXML
  private TableColumn<Player, Integer> c2;
  @FXML
  private TableColumn<Player, Integer> c3;
  @FXML
  private Button liveStats;
  @FXML
  private AnchorPane rootAnchor;
  @FXML
  private AnchorPane newSPane;
  @FXML
  private Circle openRuleBook;
  @FXML
  private Label endGame;


  /**************************************************
   *                                                *
   *                Getter and Setter               *
   *                                                *
   *************************************************/

  public Territory getSelectedTerritory() {
    return this.selectedTerritory;
  }

  public void setSelectedTerritory(Territory t) {
    this.selectedTerritory = t;
  }

  public Territory getSelectedTerritoryAttacked() {
    return this.selectedTerritoryAttacked;
  }

  public void setSelectedTerritoryAttacked(Territory t) {
    this.selectedTerritoryAttacked = t;
  }

  public Label getArmiesToDistributeLabel() {
    return armiesToDistribute;
  }
  
  
  /**************************************************
   *                                                *
   *        Methods that initialize boardGUI        *
   *                                                *
   *************************************************/
  
  /**
   * Create live statistic table columns.
   * 
   * @author prto
   */
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    c0.setCellValueFactory(new PropertyValueFactory<>("colorString"));
    c1.setCellValueFactory(new PropertyValueFactory<>("name"));
    c2.setCellValueFactory(new PropertyValueFactory<>("numberOfTerritories"));
    c3.setCellValueFactory(new PropertyValueFactory<>("numberOfCards"));

    c1.setSortType(TableColumn.SortType.ASCENDING);

  }
  
  /**
   * Method creates base of the game board.
   * 
   * @param boardGui parameter to connect both GUIs
   */
  public void setMain(SinglePlayerGUIController boardGui) {
    this.boardGui = boardGui;
    connectRegionTerritory();
    if (Main.g.isShowTutorialMessages()) {
      showMessage(game.TutorialMessages.welcome);
      showMessage(game.TutorialMessages.intro);
    }
  }

  public void startBoard() {
    connectRegionTerritory();
  }
  
  
  /**************************************************
   *                                                *
   *        Methods that handle boardGUI from       *
   *               different classes                *
   *                                                *
   *************************************************/

  public void setTerritoryText(Territory t) {
    Platform.runLater(new Runnable() {
      public void run() {
        t.getBoardRegion().getNumberOfArmy().setText(t.getNumberOfArmies() + "");
      }
    });
  }

  public void setCircleArmiesToDistributeLable() {
    Platform.runLater(new Runnable() {
      public void run() {
        armiesToDistribute.setText(Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + "");
      }
    });
  }

  public synchronized void updateLabelTerritory(Territory t) {
    Platform.runLater(new Runnable() {
      public void run() {
        t.getBoardRegion().getNumberOfArmy().setText(t.getNumberOfArmies() + "");
      }
    });
  }

  public synchronized void updateColorTerritory(Territory t) {
    Thread th = new Thread() {
      public void run() {
        Platform.runLater(new Runnable() {
          public void run() {
            t.getBoardRegion().getRegion()
                .setBackground(new Background(new BackgroundFill(t.getOwner().getColor().getColor(),
                    CornerRadii.EMPTY, Insets.EMPTY)));
            t.getBoardRegion().getNumberOfArmy().setText(t.getNumberOfArmies() + "");
            circle.setFill(Main.g.getCurrentPlayer().getColor().getColor());
            armiesToDistribute.setText(Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + "");
          }
        });
      }
    };
    th.start();
  }

  public void highlightTerritory(Territory t) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        DropShadow d = (DropShadow) t.getBoardRegion().getRegion().getEffect();
        d.setInput(null);
      }
    });
  }
  
  /**
   * Method handles a glowing effect if a player owns a whole continent.
   * 
   * @author smetzger
   */
  public void handleContinentGlow(Continent c, boolean hasShadow) {
    Thread th = new Thread() {
      public void run() {
        if (hasShadow) {
          for (Territory tL : c.getTerritories()) {
            Platform.runLater(new Runnable() {
              public void run() {
                DropShadow shadow = (DropShadow) tL.getBoardRegion().getRegion().getEffect();
                shadow.setRadius(40.0);
                shadow.setColor(tL.getOwner().getColor().getColor());
              }
            });
          }
        } else {
          for (Territory t : c.getTerritories()) {
            Platform.runLater(new Runnable() {
              public void run() {
                DropShadow shadow = (DropShadow) t.getBoardRegion().getRegion().getEffect();
                shadow.setRadius(0.0);
                shadow.setColor(null);
              }
            });
          }
        }
      }
    };
    th.start();
  }

  public void disableAll() {
    Platform.runLater(new Runnable() {
      public void run() {
        rootAnchor.setMouseTransparent(true);
      }
    });
  }

  public void enableAll() {
    Platform.runLater(new Runnable() {
      public void run() {
        rootAnchor.setMouseTransparent(false);
      }
    });
  }

  /**
   * 
   * @param change the game state text
   */
  public synchronized void setState(String text) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        gameState.setText(text);
      }
    });
  }


  /**************************************************
   *                                                *
   *            Methods to prepare Board            * 
   *                                                *
   *************************************************/
  
  /**
   * Method handles clicking on the skip button to end a given phase and come to the next one.
   * 
   * @author smetzger
   * @author pcoberge
   */
  @FXML
  public void handleSkipGameState() {
    Platform.runLater(new Runnable() {
      public void run() {
        changeGameState.setEffect(new Bloom());
        changeGameState.setDisable(true);
        switch (Main.g.getGameState()) {
          case ARMY_DISTRIBUTION:
            gameState.setText("Attack!");
            changeGameState.setDisable(false);
            prepareAttack();
            Main.g.setGameState(GameState.ATTACK);
            break;
          case ATTACK:
            gameState.setText("Move your Armies!");
            prepareFortify();
            Main.g.setGameState(GameState.FORTIFY);
            changeGameState.setDisable(false);
            break;
          case FORTIFY:
            if (Main.g.isNetworkGame()) {
              SkipgamestateMessage message = new SkipgamestateMessage(GameState.FORTIFY);
              message.setColor(Main.g.getCurrentPlayer().getColor().toString());
              NetworkController.gameFinder.getClient().sendMessage(message);
            }
            Main.g.getCurrentPlayer().setFortify(true);
            neutralizeGUIfortify();
            gameState.setText("End your turn!");
            try {
              Thread.sleep(100);
            } catch (InterruptedException e1) {
              e1.printStackTrace();
            }
            Main.g.furtherFortify();
            break;
        }
      }
    });
  }

  /**
   * Method to prepare boardGui for the initial territory distribution phase.
   * 
   * @author smetzger
   * @author pcoberge
   */
  public synchronized void prepareInitTerritoryDistribution() {
    Thread th = new Thread() {
      public void run() {
        Platform.runLater(new Runnable() {
          public void run() {
            setTurns();
            armiesToDistribute.setText(Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + "");
            circle.setFill(Main.g.getCurrentPlayer().getColor().getColor());
          }
        });
        if (Main.g.getCurrentPlayer() instanceof AiPlayer) {
          for (Territory t : Main.g.getWorld().getTerritories().values()) {
            if (t.getOwner() != null) {
              Platform.runLater(new Runnable() {
                public void run() {
                  t.getBoardRegion().getRegion().setDisable(true);
                }
              });
            }
          }
        } else {
          for (Territory t : Main.g.getWorld().getTerritories().values()) {
            if (t.getOwner() == null) {
              Platform.runLater(new Runnable() {
                public void run() {
                  t.getBoardRegion().getRegion().setDisable(false);
                }
              });
            }
          }
        }

      }
    };
    th.start();
    enableAll();
  }

  /**
   * Method to prepare the BoardGUI for phase ARMY_DISTRIBUTION.
   * 
   * @author smetzger
   * @author pcoberge
   * 
   */
  public synchronized void prepareArmyDistribution() {
    Thread th = new Thread() {
      public void run() {
        Platform.runLater(new Runnable() {
          public void run() {
            circle.setFill(Main.g.getCurrentPlayer().getColor().getColor());
            armiesToDistribute.setText(Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + "");
          }
        });
        if (Main.g.getGameState() == GameState.INITIALIZING_ARMY) {
          Platform.runLater(new Runnable() {
            public void run() {
              if (!gameState.getText().equals("Place your Armies initially!")) {
                setTurns();
                if (!Main.g.isNetworkGame()) {
                  gameState.setText("Place your Armies initially!");
                }
                // Tutorial Messages
                if (Main.g.isShowTutorialMessages()) {
                  showMessage(game.TutorialMessages.distributing);
                  showMessage(game.TutorialMessages.distributingTip);
                }
              } else {
                setTurns();
              }
            }
          });
        } else if (Main.g.getGameState() == GameState.ARMY_DISTRIBUTION) {
          if (!gameState.getText().equals("Place your Armies!")) {
            Platform.runLater(new Runnable() {
              public void run() {
                setTurns();
                if (!Main.g.isNetworkGame()) {
                  gameState.setText("Place your Armies!");
                }
              }
            });
          }
        }
        if (Main.g.getCurrentPlayer() instanceof AiPlayer) {
          for (Territory t : Main.g.getWorld().getTerritories().values()) {
            Platform.runLater(new Runnable() {
              public void run() {
                t.getBoardRegion().getRegion().setDisable(true);
                DropShadow d = (DropShadow) t.getBoardRegion().getRegion().getEffect();
                d.setInput(new Lighting());
              }
            });
          }
        } else {
          Platform.runLater(new Runnable() {
            public void run() {
              changeGameState.setDisable(false);
            }
          });
          for (Territory t : Main.g.getWorld().getTerritories().values()) {
            if (t.getOwner().equals(Main.g.getCurrentPlayer())) {
              Platform.runLater(new Runnable() {

                @Override
                public void run() {
                  DropShadow d = (DropShadow) t.getBoardRegion().getRegion().getEffect();
                  d.setInput(null);
                  t.getBoardRegion().getRegion().setDisable(false);
                }
              });
            } else {
              Platform.runLater(new Runnable() {
                @Override
                public void run() {
                  t.getBoardRegion().getRegion().setDisable(true);
                  DropShadow d = (DropShadow) t.getBoardRegion().getRegion().getEffect();
                  d.setInput(new Lighting());
                }
              });
            }
          }
          // show Cardpane and force player to trade in cards
          if (Main.g.getCurrentPlayer().getCards().size() >= 5) {
            Platform.runLater(new Runnable() {
              public void run() {
                try {
                  Main.cardC.handleGrayPane(true);
                  Main.stagePanes.setScene(Main.cards);
                  Main.stagePanes.setOpacity(0.9);
                  Main.stagePanes.setX(Main.stage.getX() + Parameter.WIDTH);
                  Main.stagePanes.setY(Main.stage.getY() + Parameter.HEIGHT);
                  Main.stagePanes.show();
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            });
            if (Main.g.isShowTutorialMessages()) {
              Platform.runLater(new Runnable() {
                public void run() {
                  showMessage(TutorialMessages.forcedTrade);
                }
              });
            }
          }
        }
      }
    };
    th.start();
    enableAll();
  }

  /**
   * Method to prepare the BoardGUI for phase ATTACK.
   * 
   * @author smetzger
   * @author pcoberge
   */
  public synchronized void prepareAttack() {
    Thread th = new Thread() {
      public void run() {
        if (!gameState.getText().equals("Attack!")) {
          Platform.runLater(new Runnable() {
            public void run() {
              gameState.setText("Attack!");
              if (Main.g.isShowTutorialMessages()) {
                showMessage(game.TutorialMessages.attacking1);
              }
            }
          });
        }
        for (Territory t : Main.g.getWorld().getTerritories().values()) {
          if (t.getOwner().equals(Main.g.getCurrentPlayer()) && t.getNumberOfArmies() > 1
              && t.getHostileNeighbor().size() > 0) {
            Platform.runLater(new Runnable() {
              public void run() {
                t.getBoardRegion().getRegion().setDisable(false);
                DropShadow d = (DropShadow) t.getBoardRegion().getRegion().getEffect();
                d.setInput(null);
              }
            });
          } else {
            Platform.runLater(new Runnable() {
              public void run() {
                t.getBoardRegion().getRegion().setDisable(true);
                DropShadow d = (DropShadow) t.getBoardRegion().getRegion().getEffect();
                d.setInput(new Lighting());
              }
            });
          }
        }
      }
    };
    th.start();
  }

  /**
   * Method to prepare the BoradGUI for phase FORTIFY.
   * 
   * @author smetzger
   * @author pcoberge 
   */
  public synchronized void prepareFortify() {
    Thread th = new Thread() {
      public void run() {
        if (!gameState.getText().equals("Move your Armies!")) {
          Platform.runLater(new Runnable() {
            @Override
            public void run() {
              gameState.setText("Move your Armies!");
              if (Main.g.isShowTutorialMessages()) {
                showMessage(game.TutorialMessages.fortify);
                showMessage(game.TutorialMessages.fortifyTip);
              }
            }
          });
        }
        for (Territory t : Main.g.getWorld().getTerritories().values()) {
          if (t.getOwner().equals(Main.g.getCurrentPlayer()) && t.getNumberOfArmies() > 1
              && t.getHostileNeighbor().size() != t.getNeighbor().size()) {
            Platform.runLater(new Runnable() {
              public void run() {
                DropShadow d = (DropShadow) t.getBoardRegion().getRegion().getEffect();
                d.setInput(null);
                t.getBoardRegion().getRegion().setDisable(false);
              }
            });
          } else {
            Platform.runLater(new Runnable() {
              public void run() {
                t.getBoardRegion().getRegion().setDisable(true);
                DropShadow d = (DropShadow) t.getBoardRegion().getRegion().getEffect();
                d.setInput(new Lighting());
              }

            });
          }
        }
      }
    };
    th.start();
  }
  
  /**
   * This method changes the game state in network game, the current player name will be showed in
   * game state.
   * 
   * @author qiychen
   * 
   */
  public void setTurns() {
    if (Main.g.isNetworkGame()) {
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          gameState.setText("It's " + Main.g.getCurrentPlayer().getName() + "'s turn.");
          if (Main.g.getCurrentPlayer().getName()
              .equals(NetworkController.gameFinder.getClient().getPlayer().getName())) {
            if (Main.g.getGameState() == GameState.INITIALIZING_TERRITORY) {
              gameState.setText("Choose your Territory");
            } else if (Main.g.getGameState() == GameState.INITIALIZING_ARMY) {
              gameState.setText("Place your Armies initially!");
            } else if (Main.g.getGameState() == GameState.ARMY_DISTRIBUTION) {
              gameState.setText("Place your Armies!");
            }

          }
        }
      });
    }
  }

  
  /**************************************************
   *                                                *
   *        Methods to handle clicks on Board       * 
   *                                                *
   *************************************************/

  /**
   * The method is divided into two parts.
   * 
   * <p>1. Part: the previous selected Territory is not the same like the current selection.
   * 
   * <p>INITIALIZING_TERRITORY - the selected territory is highlighted in the color of the current
   * player
   * 
   * <p>INITIALIZING_ARMY - if an army could be placed on the chosen territory the label of this
   * territory is updated
   * 
   * <p>ARMY_DISTRIBUTION - if a territory is selected, the player has to choose how many armies he
   * want to place at this territory. Therefore the slider in the background is set on the amount of
   * armies the player is allowed to set. A pane with a slider to choose appears. The label of this
   * territory will be updated. 
   * 
   * <p>ATTACK - if the selected territory is the first one, this territory
   * is highlighted and all neighbors that do not belong to the same player will be highlighted as
   * well. All other territories are disabled. if the selected territory is the second one, a pane
   * appears in order to throw the dices.
   * 
   * <p>FORTIFY - if the selected territory is the first one, this territory is highlighted and all
   * neighbors that belong to the same player will be highlighted as well. All other territories are
   * disabled. if the selected territory is the second one, a pane appears in order to choose how
   * many armies should be transferred.
   * 
   * @author smetzger
   * @author pcoberge
   * @param MouseEvent The parameter contains the information which gui element triggers the
   *        actionlistener.
   */
  @FXML
  public synchronized void clicked(MouseEvent e) {
    if (Main.g.isNetworkGame()) {
      if (!NetworkController.gameFinder.getClient().getPlayer().equals(Main.g.getCurrentPlayer())) {
        return;
      }
    }
    Region r = (Region) e.getSource();
    Territory t = Main.g.getWorld().getTerritoriesRegion().get(r);

    Platform.runLater(new Runnable() {
      public void run() {
        DropShadow d = (DropShadow) r.getEffect();
        d.setInput(null);
      }
    });

    if (!t.equals(selectedTerritory)) {
      switch (Main.g.getGameState()) {
        case INITIALIZING_TERRITORY:
          if (Main.g.getCurrentPlayer().initialTerritoryDistribution(t)) {
            disableAll();
            Thread th = new Thread() {
              public void run() {
                updateColorTerritory(t);

                if (Main.g.isNetworkGame() && !(Main.g.getCurrentPlayer() instanceof AiPlayer)) {
                  SelectInitialTerritoryMessage message =
                      new SelectInitialTerritoryMessage(t.getId());
                  message.setColor(Main.g.getCurrentPlayer().getColor().toString());
                  NetworkController.gameFinder.getClient().sendMessage(message);
                  return;
                }
                Platform.runLater(new Runnable() {
                  public void run() {
                    DropShadow d = (DropShadow) r.getEffect();
                    d.setInput(new Lighting());
                  }
                });
                Main.g.furtherInitialTerritoryDistribution();
              }
            };
            th.start();
          }
          break;
        case INITIALIZING_ARMY:
          if (Main.g.getCurrentPlayer().armyDistribution(1, t)) {
            disableAll();
            Thread th = new Thread() {
              public void run() {
                Platform.runLater(new Runnable() {
                  public void run() {
                    armiesToDistribute
                        .setText(Main.g.getCurrentPlayer().getNumberArmiesToDistibute() + "");
                    circle.setFill(Main.g.getCurrentPlayer().getColor().getColor());
                    t.getBoardRegion().getNumberOfArmy().setText(t.getNumberOfArmies() + "");
                  }
                });
                if (Main.g.isNetworkGame() && !(Main.g.getCurrentPlayer() instanceof AiPlayer)) {
                  DistributeArmyMessage armyMessage = new DistributeArmyMessage(1, t.getId());
                  armyMessage.setColor(Main.g.getCurrentPlayer().getColor().toString());
                  NetworkController.gameFinder.getClient().sendMessage(armyMessage);
                  return;
                }
                Main.g.furtherInitialArmyDistribution();
              }
            };
            th.start();
          }
          break;
        case ARMY_DISTRIBUTION:
          if (Main.g.getCurrentPlayer().getNumberArmiesToDistibute() > 0) {
            Platform.runLater(new Runnable() {
              public void run() {
                selectedTerritory = t;
                try {
                  FXMLLoader fxmlLoader =
                      new FXMLLoader(getClass().getResource("/gui/ArmyDistributionSubScene.fxml"));
                  Parent root = (Parent) fxmlLoader.load();
                  Main.stagePanes.setScene(new Scene(root));
                  Main.stagePanes.setX(Main.stage.getX() + Parameter.WIDTH);
                  Main.stagePanes.setY(Main.stage.getY() + Parameter.HEIGHT);
                  Main.stagePanes.show();
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            });
          }
          break;
        case ATTACK:
          clickedAttack(t);
          break;
        case FORTIFY:
          clickedFortify(t);
          break;
      }
    } else if (t.equals(selectedTerritory)) {
      if (Main.g.getGameState() == GameState.ATTACK) {
        neutralizeGUIattack();
      } else if (Main.g.getGameState() == GameState.FORTIFY) {
        neutralizeGUIfortify();
      }
    }
  }

  /**
   * This method handles clicks in attack phase.
   * 
   * @author pcoberge
   * @author smetzger
   * @param t = Territory that is chosen to attack with or to be attacked
   */
  public void clickedAttack(Territory t) {
    // First click or change between own territories
    if (selectedTerritory == null || t.getOwner().equals(Main.g.getCurrentPlayer())) {
      Thread th = new Thread() {
        public void run() {
          if (selectedTerritory != null) {
            for (Territory territory : selectedTerritory.getHostileNeighbor()) {
              Platform.runLater(new Runnable() {
                public void run() {
                  DropShadow d = (DropShadow) territory.getBoardRegion().getRegion().getEffect();
                  d.setInput(new Lighting());
                  territory.getBoardRegion().getRegion().setDisable(true);
                }
              });
            }
          }
          selectedTerritory = t;
          for (Territory territory : t.getHostileNeighbor()) {
            Platform.runLater(new Runnable() {
              public void run() {
                DropShadow d = (DropShadow) territory.getBoardRegion().getRegion().getEffect();
                d.setInput(null);
                territory.getBoardRegion().getRegion().setDisable(false);
              }
            });
          }
          if (Main.g.isShowTutorialMessages() && !(Main.g.getCurrentPlayer() instanceof AiPlayer)) {
            showMessage(game.TutorialMessages.attacking2);
            showMessage(game.TutorialMessages.attackingTip);
          }
        }
      };
      th.start();
      // Second Click
    } else if (selectedTerritory != null && selectedTerritory.getNeighbor().contains(t)) {
      selectedTerritoryAttacked = t;

      Thread th = new Thread() {
        public void run() {
          if (Main.g.isShowTutorialMessages()) {
            showMessage(game.TutorialMessages.dicing);
          }
          Platform.runLater(new Runnable() {
            public void run() {
              try {
                FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass().getResource("/gui/AttackSubScene.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Main.attack = fxmlLoader.getController();
                Main.stagePanes.setScene(new Scene(root));
                Main.stagePanes.setX(Main.stage.getX() + Parameter.WIDTH);
                Main.stagePanes.setY(Main.stage.getY() + Parameter.HEIGHT);
                Main.stagePanes.show();
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
          });
        }
      };
      th.start();
    }
  }

  /**
   * This method handles clicks in fortify phase.
   * 
   * @author pcoberge
   * @author smetzger
   * @param t = Territory to move armies from or to move armies to
   */
  public void clickedFortify(Territory t) {
    Thread th = new Thread() {
      public void run() {
        if (!Main.g.getCurrentPlayer().getFortify()) {
          if (selectedTerritory == null) {
            selectedTerritory = t;
            Platform.runLater(new Runnable() {
              public void run() {
                DropShadow d =
                    (DropShadow) selectedTerritory.getBoardRegion().getRegion().getEffect();
                d.setInput(new Lighting());
              }
            });
            for (Territory territory : t.getNeighbor()) {
              if (t.getOwner().equals(territory.getOwner())) {
                Platform.runLater(new Runnable() {
                  public void run() {
                    DropShadow d = (DropShadow) territory.getBoardRegion().getRegion().getEffect();
                    d.setInput(null);
                    territory.getBoardRegion().getRegion().setDisable(false);
                  }
                });
              }
            }
            for (Territory territory : Main.g.getCurrentPlayer().getTerritories()) {
              if (!territory.equals(selectedTerritory)
                  && (!selectedTerritory.getNeighbor().contains(territory))) {
                Platform.runLater(new Runnable() {
                  public void run() {
                    territory.getBoardRegion().getRegion().setDisable(true);
                    DropShadow d = (DropShadow) territory.getBoardRegion().getRegion().getEffect();
                    d.setInput(new Lighting());
                  }
                });
              }
            }
          } else if (selectedTerritory != null) {
            selectedTerritoryAttacked = t;
            Platform.runLater(new Runnable() {
              public void run() {
                try {
                  FXMLLoader fxmlLoader =
                      new FXMLLoader(getClass().getResource("/gui/FortifySubScene.fxml"));
                  Parent root = (Parent) fxmlLoader.load();
                  Main.fortify = fxmlLoader.getController();
                  Main.stagePanes.setScene(new Scene(root));
                  Main.stagePanes.setX(Main.stage.getX() + Parameter.WIDTH);
                  Main.stagePanes.setY(Main.stage.getY() + Parameter.HEIGHT);
                  Main.stagePanes.show();
                } catch (Exception e1) {
                  e1.printStackTrace();
                }
              }
            });
          }
        }
      }
    };
    th.start();
  }
  
  
  /**************************************************
   *                                                *
   *    Methods to neutralize board depending on    *
   *                  current phase                 * 
   *                                                *
   *************************************************/
  
  /**
   * This method neutralizes board after an action in army distribution phase.
   * 
   * @author pcoberge
   * @author smetzger
   */
  public void neutralizeGUIarmyDistribution() {
    Region r = selectedTerritory.getBoardRegion().getRegion();
    Thread th = new Thread() {
      public void run() {
        DropShadow d = (DropShadow) r.getEffect();
        d.setInput(null);
        selectedTerritory = null;
      }
    };
    th.start();
  }

  /**
   * This method neutralizes board after an action in attack phase.
   * 
   * @author pcoberge
   * @author smetzger
   */
  public void neutralizeGUIattack() {
    Region r1 = selectedTerritory.getBoardRegion().getRegion();
    if (selectedTerritory.getNumberOfArmies() == 1
        || selectedTerritory.getHostileNeighbor().size() == 0) {
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          DropShadow d = (DropShadow) r1.getEffect();
          d.setInput(new Lighting());
          r1.setDisable(true);
        }
      });
    }
    if (selectedTerritoryAttacked != null) {
      Region r2 = selectedTerritoryAttacked.getBoardRegion().getRegion();
      if (selectedTerritoryAttacked.getNumberOfArmies() > 1
          && selectedTerritoryAttacked.getHostileNeighbor().size() > 0) {
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            DropShadow d = (DropShadow) r2.getEffect();
            d.setInput(null);
            r2.setDisable(false);
          }
        });
      }
    }
    for (Territory t : selectedTerritory.getNeighbor()) {
      if (!(t.getOwner().equals(selectedTerritory.getOwner()))
          || (t.getOwner().equals(selectedTerritory.getOwner())
              && t.getHostileNeighbor().size() == 0)) {
        Platform.runLater(new Runnable() {
          public void run() {
            DropShadow d = (DropShadow) t.getBoardRegion().getRegion().getEffect();
            d.setInput(new Lighting());
            t.getBoardRegion().getRegion().setDisable(true);
          }
        });
      }
    }
    selectedTerritory = null;
    selectedTerritoryAttacked = null;
  }

  /**
   * This method neutralizes board after an action in fortify phase.
   * 
   * @author pcoberge
   * @author smetzger
   */
  public void neutralizeGUIfortify() {
    if (Main.g.getCurrentPlayer().getFortify()) {
      for (Territory t : Main.g.getCurrentPlayer().getTerritories()) {
        Platform.runLater(new Runnable() {
          public void run() {
            DropShadow d = (DropShadow) t.getBoardRegion().getRegion().getEffect();
            d.setInput(new Lighting());
            t.getBoardRegion().getRegion().setDisable(true);
          }
        });
      }
    } else {
      for (Territory t : Main.g.getCurrentPlayer().getTerritories()) {
        if (t.getHostileNeighbor().size() != t.getNeighbor().size() && t.getNumberOfArmies() > 1) {
          Platform.runLater(new Runnable() {
            public void run() {
              DropShadow d = (DropShadow) t.getBoardRegion().getRegion().getEffect();
              d.setInput(null);
              t.getBoardRegion().getRegion().setDisable(false);
            }
          });
        } else {
          Platform.runLater(new Runnable() {
            public void run() {
              DropShadow d = (DropShadow) t.getBoardRegion().getRegion().getEffect();
              d.setInput(new Lighting());
              t.getBoardRegion().getRegion().setDisable(true);
            }
          });
        }
      }
    }
    selectedTerritory = null;
    selectedTerritoryAttacked = null;
  }

  
  /**************************************************
   *                                                *
   *       Methods to handle in-game statistic      * 
   *                                                *
   *************************************************/
  
  /**
   * Handles click on statistic button during a game.
   * 
   * @author prto
   */
  public void handleLiveStats() {
    Main.g.updateLiveStatistics();
    ObservableList<Player> playerList = FXCollections.observableArrayList(Main.g.getPlayers());
    statistic.setItems(playerList);
    statistic.getSortOrder().add(c1);
    statistic.refresh();
    newSPane.setVisible(true);
    if (!Main.g.isNetworkGame() && Main.g.isShowTutorialMessages()) {
      showMessage("Click on the background to close the statistics screen");
    }
  }

  /**
   * handle press on background to close live stats.
   * 
   * @author prto
   */
  public void handleCloseLiveStats() {
    newSPane.setVisible(false);
  }

  
  /**************************************************
   *                                                *
   *        Methods to show rule book and           *
   *             to handle card pane                * 
   *                                                *
   *************************************************/

  /**
   * handle press on rule book button.
   * 
   * @author prto
   */
  public void handleRuleBook() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/RuleBookPopUp.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Main.ruleBook = fxmlLoader.getController();
      Main.stagePanes.setScene(new Scene(root));
      Main.stagePanes.setX(Main.stage.getX() + Parameter.WIDTH);
      Main.stagePanes.setY(Main.stage.getY() + Parameter.HEIGHT);
      Main.stagePanes.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This method shows all cards of the local player.
   * 
   * @author smetzger
   */
  @FXML
  public synchronized void handleCardPane() {
    Platform.runLater(new Runnable() {
      public void run() {
        if (!Main.stagePanes.isShowing()) {
          Main.stagePanes.setOpacity(0.9);
          try {
            FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("/gui/CardSubScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            if (Main.g.getGameState() != GameState.ARMY_DISTRIBUTION) {
              Main.cardC.disableTradeInButton(true);
            }
            Main.stagePanes.setScene(Main.cards);
            Main.stagePanes.setX(Main.stage.getX() + Parameter.WIDTH);
            Main.stagePanes.setY(Main.stage.getY() + Parameter.HEIGHT);
            Main.stagePanes.show();

          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          Main.stagePanes.close();
          if (Main.g.isShowTutorialMessages()) {
            showMessage(TutorialMessages.tradeIn);
            showMessage(TutorialMessages.tradeInTip);
          }
        }
      }
    });
  }  
  
  
  /**************************************************
   *                                                *
   *          Methods to handle quit game           * 
   *                                                *
   *************************************************/

  /**
   * This method handles the exit Button. When the user presses the exit-Button a pop-up window
   * appears and stops the game.
   * 
   * @author pcoberge
   */
  public synchronized void pressLeave() {
    Platform.runLater(new Runnable() {
      public void run() {
        try {
          FXMLLoader fxmlLoader =
              new FXMLLoader(getClass().getResource("/gui/QuitGameSubScene.fxml"));
          Parent root = (Parent) fxmlLoader.load();
          Main.quit = fxmlLoader.getController();
          Main.stagePanes.setScene(new Scene(root));
          Main.stagePanes.setX(Main.stage.getX() + Parameter.WIDTH);
          Main.stagePanes.setY(Main.stage.getY() + Parameter.HEIGHT);
          Main.stagePanes.show();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  
  /**
   * This method shows a screen-message depending on winning or losing the game.
   */
  public void endGame() {
    if (Main.g.isNetworkGame()&& !(Main.g.onlyAiPlayersLeft())) {
      Player p=NetworkController.gameFinder.getClient().getPlayer();
      if (Main.g.getPlayers().contains(p) && Main.g.getPlayers().size()>1) {
        System.out.println("EndGame ingame"+p.getName()+ "Rank"+p.getRank());
        return;
      }
    }
    Main.g.addToAllPlayers(Main.g.getPlayers().get(0));
    Platform.runLater(new Runnable() {
      public void run() {
        try {
          FXMLLoader fxmlLoader =
              new FXMLLoader(getClass().getResource("/gui/EndGameSubScene.fxml"));
          Parent root = fxmlLoader.load();
          Stage stage = Main.stagePanes;
          stage.setScene(new Scene(root));
          stage.setX(Main.stage.getX() + 2);
          stage.setY(Main.stage.getY() + 24);
          stage.show();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  
  
  /**************************************************
   *                                                *
   *          Methods to handle multiplayer         * 
   *                                                *
   *************************************************/

  /**
   * Method handles the ability to send messages in a multiplayer situation.
   * 
   * @author qiychen
   * @param event Messages regarding chat/alliance can be sent if a specific name is given, private
   *        messages will be sent, otherwise the message will be sent to all members
   */
  @FXML
  void handleSendMessage(ActionEvent event) {
    String message = messages.getText();
    String player = playername.getText();
    String author = ProfileSelectionGUIController.selectedPlayerName;
    if (Main.g.isNetworkGame()) {
      Client client = NetworkController.gameFinder.getClient();
      // don't send empty string
      if (message.equals("")) {
        return;
      }
      if (player.equals("") || player.isEmpty() || playername == null) {
        GameMessageMessage chatmessage = new GameMessageMessage(author, message);
        client.sendMessage(chatmessage);
        this.messages.clear();
        this.playername.clear();
      } else {
        this.showMessage(author.toUpperCase() + " : " + message + " (private to " + player + ")");
        SendAllianceMessage privatemessage = new SendAllianceMessage(player, message, author);
        client.sendMessage(privatemessage);
        this.messages.clear();
        this.playername.clear();
      }
    }
  }
  
  public void showMessage(String message) {
    Platform.runLater(new Runnable() {
      public void run() {
        chat.appendText(message + "\n_____________\n\n");
      }
    });
  }

  public void showAllianceMessage(String message) {
    chat.appendText(message + " (private) ");
    chat.appendText("\n_____________\n\n");
  }
  
  /**
   * This method is called in the client class, when a client receives the leave game message from
   * the host player. After being called, this method shows an alert to every player connected to
   * the game. The alert informs the players that the game is cancelled and asks them to leave the
   * game by clicking anywhere on the board.
   * 
   * @author skaur
   */
  public void gameCancelAlert() {
    // show the alert that game is cancelled
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Game Cancelled");
    alert.setHeaderText("A player has left the game. The game is now cancelled." + "\n"
        + "Please leave the Game Board");
    alert.setContentText("Leave Game");
    Optional<ButtonType> option = alert.showAndWait();
    if (option.get() == null) {
      LeaveGameResponseMessage responseMessage = new LeaveGameResponseMessage();
      NetworkController.gameFinder.getClient().sendMessage(responseMessage);
      this.clientLeaveGame();
    } else if (option.get() == ButtonType.OK) {
      // send a leave game response to the server
      LeaveGameResponseMessage responseMessage = new LeaveGameResponseMessage();
      NetworkController.gameFinder.getClient().sendMessage(responseMessage);
      this.clientLeaveGame();
    } else {
      LeaveGameResponseMessage responseMessage = new LeaveGameResponseMessage();
      NetworkController.gameFinder.getClient().sendMessage(responseMessage);
      this.clientLeaveGame();
    }
  }
  

  /**
   * After the player choose to leave the game, this method shows the game statistics to each
   * player.
   * 
   * @author skaur
   */
  public void clientLeaveGame() {
    Platform.runLater(new Runnable() {
      public void run() {
        try {
          for (Player p : Main.g.getPlayers()) {
            Main.g.addToAllPlayers(p);
          }
          Main.g.setGameState(GameState.END_GAME);
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/StatisticGUI.fxml"));
          Parent root = (Parent) fxmlLoader.load();
          Stage stage = main.Main.stage;
          stage.setScene(new Scene(root));
          stage.show();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  
  
  /**************************************************
   *                                                *
   *          Methods to create connection          *
   *            between logic and board             * 
   *                                                *
   *************************************************/
  
  /**
   * This method creates a connection between the javafx region and label elements and the
   * equivalent territory.
   * 
   * @author pcoberge
   */
  public void connectRegionTerritory() {
    Thread th = new Thread() {
      public void run() {
        Main.g.getWorld().getTerritories().get(1)
            .setBoardRegion(new BoardRegion(alaska, alaskaHeadline, alaskaNoA));
        Main.g.getWorld().getTerritories().get(2).setBoardRegion(
            new BoardRegion(northwestTerritory, northwestTerritoryHeadline, northwestTerritoryNoA));
        Main.g.getWorld().getTerritories().get(3)
            .setBoardRegion(new BoardRegion(alberta, albertaHeadline, albertaNoA));
        Main.g.getWorld().getTerritories().get(4).setBoardRegion(new BoardRegion(
            westernUnitedStates, westernUnitedStatesHeadline, westernUnitedStatesNoA));
        Main.g.getWorld().getTerritories().get(5).setBoardRegion(
            new BoardRegion(centralAmerica, centralAmericaHeadline, centralAmericaNoA));
        Main.g.getWorld().getTerritories().get(6)
            .setBoardRegion(new BoardRegion(greenland, greenlandHeadline, greenlandNoA));
        Main.g.getWorld().getTerritories().get(7)
            .setBoardRegion(new BoardRegion(ontario, ontarioHeadline, ontarioNoA));
        Main.g.getWorld().getTerritories().get(8)
            .setBoardRegion(new BoardRegion(quebec, quebecHeadline, quebecNoA));
        Main.g.getWorld().getTerritories().get(9).setBoardRegion(new BoardRegion(
            easternUnitedStates, easternUnitedStatesHeadline, easternUnitedStatesNoA));
        Main.g.getWorld().getTerritories().get(10)
            .setBoardRegion(new BoardRegion(venezuela, venezuelaHeadline, venezuelaNoA));
        Main.g.getWorld().getTerritories().get(11)
            .setBoardRegion(new BoardRegion(peru, peruHeadline, peruNoA));
        Main.g.getWorld().getTerritories().get(12)
            .setBoardRegion(new BoardRegion(brazil, brazilHeadline, brazilNoA));
        Main.g.getWorld().getTerritories().get(13)
            .setBoardRegion(new BoardRegion(argentina, argentinaHeadline, argentinaNoA));
        Main.g.getWorld().getTerritories().get(14)
            .setBoardRegion(new BoardRegion(iceland, icelandHeadline, icelandNoA));
        Main.g.getWorld().getTerritories().get(15)
            .setBoardRegion(new BoardRegion(scandinavia, scandinaviaHeadline, scandinaviaNoA));
        Main.g.getWorld().getTerritories().get(16)
            .setBoardRegion(new BoardRegion(ukraine, ukraineHeadline, ukraineNoA));
        Main.g.getWorld().getTerritories().get(17)
            .setBoardRegion(new BoardRegion(greatBritain, greatBritainHeadline, greatBritainNoA));
        Main.g.getWorld().getTerritories().get(18).setBoardRegion(
            new BoardRegion(northernEurope, northernEuropeHeadline, northernEuropeNoA));
        Main.g.getWorld().getTerritories().get(19).setBoardRegion(
            new BoardRegion(westernEurope, westernEuropeHeadline, westernEuropeNoA));
        Main.g.getWorld().getTerritories().get(20).setBoardRegion(
            new BoardRegion(southernEurope, southernEuropeHeadline, southernEuropeNoA));
        Main.g.getWorld().getTerritories().get(21)
            .setBoardRegion(new BoardRegion(northAfrica, northAfricaHeadline, northAfricaNoA));
        Main.g.getWorld().getTerritories().get(22)
            .setBoardRegion(new BoardRegion(egypt, egyptHeadline, egyptNoA));
        Main.g.getWorld().getTerritories().get(23)
            .setBoardRegion(new BoardRegion(congo, congoHeadline, congoNoA));
        Main.g.getWorld().getTerritories().get(24)
            .setBoardRegion(new BoardRegion(eastAfrica, eastAfricaHeadline, eastAfricaNoA));
        Main.g.getWorld().getTerritories().get(25)
            .setBoardRegion(new BoardRegion(southAfrica, southAfricaHeadline, southAfricaNoA));
        Main.g.getWorld().getTerritories().get(26)
            .setBoardRegion(new BoardRegion(madagascar, madagascarHeadline, madagascarNoA));
        Main.g.getWorld().getTerritories().get(27)
            .setBoardRegion(new BoardRegion(siberia, siberiaHeadline, siberiaNoA));
        Main.g.getWorld().getTerritories().get(28)
            .setBoardRegion(new BoardRegion(ural, uralHeadline, uralNoA));
        Main.g.getWorld().getTerritories().get(29)
            .setBoardRegion(new BoardRegion(china, chinaHeadline, chinaNoA));
        Main.g.getWorld().getTerritories().get(30)
            .setBoardRegion(new BoardRegion(afghanistan, afghanistanHeadline, afghanistanNoA));
        Main.g.getWorld().getTerritories().get(31)
            .setBoardRegion(new BoardRegion(middleEast, middleEastHeadline, middleEastNoA));
        Main.g.getWorld().getTerritories().get(32)
            .setBoardRegion(new BoardRegion(india, indiaHeadline, indiaNoA));
        Main.g.getWorld().getTerritories().get(33)
            .setBoardRegion(new BoardRegion(siam, siamHeadline, siamNoA));
        Main.g.getWorld().getTerritories().get(34)
            .setBoardRegion(new BoardRegion(yakutsk, yakutskHeadline, yakutskNoA));
        Main.g.getWorld().getTerritories().get(35)
            .setBoardRegion(new BoardRegion(irkutsk, irkutskHeadline, irkutskNoA));
        Main.g.getWorld().getTerritories().get(36)
            .setBoardRegion(new BoardRegion(mongolia, mongoliaHeadline, mongoliaNoA));
        Main.g.getWorld().getTerritories().get(37)
            .setBoardRegion(new BoardRegion(japan, japanHeadline, japanNoA));
        Main.g.getWorld().getTerritories().get(38)
            .setBoardRegion(new BoardRegion(kamchatka, kamchatkaHeadline, kamchatkaNoA));
        Main.g.getWorld().getTerritories().get(39)
            .setBoardRegion(new BoardRegion(indonesia, indonesiaHeadline, indonesiaNoA));
        Main.g.getWorld().getTerritories().get(40)
            .setBoardRegion(new BoardRegion(newGuinea, newGuineaHeadline, newGuineaNoA));
        Main.g.getWorld().getTerritories().get(41).setBoardRegion(
            new BoardRegion(westernAustralia, westernAustraliaHeadline, westernAustraliaNoA));
        Main.g.getWorld().getTerritories().get(42).setBoardRegion(
            new BoardRegion(easternAustralia, easternAustraliaHeadline, easternAustraliaNoA));
        Main.g.getWorld().createTerritoriesBoardRegion();

        for (Territory t : Main.g.getWorld().getTerritories().values()) {
          Platform.runLater(new Runnable() {
            public void run() {
              DropShadow shadow = new DropShadow();
              Lighting light = new Lighting();
              shadow.setRadius(0.0);
              shadow.setInput(light);
              t.getBoardRegion().getRegion().setEffect(shadow);
            }
          });
        }
      }

    };
    th.start();
  }

}
