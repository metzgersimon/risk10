package game;

import java.util.HashMap;
import java.util.HashSet;
import gui.BoardRegion;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * @author pcoberge The class World defines all countries that exist in the risk-game.
 */
public class World {
  static HashMap<Integer, Territory> territories = new HashMap<>();
  static HashMap<BoardRegion, Territory> territoriesBoardRegion = new HashMap<>();
  static HashMap<Region, Territory> territoriesRegion = new HashMap<>();
  static HashMap<Label, Territory> territoriesName = new HashMap<>();
  static HashMap<Label, Territory> territoriesNoA = new HashMap<>();
  static HashMap<Continente, Continent> continent = new HashMap<>();

  /**
   * Constructor
   */
  public World() {
    this.initialiseTerritories();
    this.initialiseNeighbors();
    this.initialiseContinents();
  }

  /**
   * Method initialises all territories existing in risk world
   */
  public void initialiseTerritories() {
    territories.put(1, new Territory("Alaska", 1, CardSymbol.INFANTRY, Continente.NORTHAMERICA));
    territories.put(2,
        new Territory("North_West_Territory", 2, CardSymbol.CANNON, Continente.NORTHAMERICA));
    territories.put(3, new Territory("Alberta", 3, CardSymbol.INFANTRY, Continente.NORTHAMERICA));
    territories.put(4,
        new Territory("Western_United_States", 4, CardSymbol.INFANTRY, Continente.NORTHAMERICA));
    territories.put(5,
        new Territory("Central_America", 5, CardSymbol.CAVALRY, Continente.NORTHAMERICA));
    territories.put(6, new Territory("Greenland", 6, CardSymbol.CAVALRY, Continente.NORTHAMERICA));
    territories.put(7, new Territory("Ontario", 7, CardSymbol.CAVALRY, Continente.NORTHAMERICA));
    territories.put(8, new Territory("Quebec", 8, CardSymbol.CANNON, Continente.NORTHAMERICA));
    territories.put(9,
        new Territory("Eastern_United_States", 9, CardSymbol.CANNON, Continente.NORTHAMERICA));
    territories.put(10, new Territory("Venezuela", 10, CardSymbol.CANNON, Continente.SOUTHAMERICA));
    territories.put(11, new Territory("Peru", 11, CardSymbol.CAVALRY, Continente.SOUTHAMERICA));
    territories.put(12, new Territory("Brazil", 12, CardSymbol.CANNON, Continente.SOUTHAMERICA));
    territories.put(13,
        new Territory("Argentina", 13, CardSymbol.INFANTRY, Continente.SOUTHAMERICA));
    territories.put(14, new Territory("Iceland", 14, CardSymbol.INFANTRY, Continente.EUROPE));
    territories.put(15, new Territory("Scandinavia", 15, CardSymbol.CANNON, Continente.EUROPE));
    territories.put(16, new Territory("Ukraine", 16, CardSymbol.CANNON, Continente.EUROPE));
    territories.put(17, new Territory("Great_Britain", 17, CardSymbol.CAVALRY, Continente.EUROPE));
    territories.put(18,
        new Territory("Northern_Europe", 18, CardSymbol.CAVALRY, Continente.EUROPE));
    territories.put(19,
        new Territory("Western_Europe", 19, CardSymbol.INFANTRY, Continente.EUROPE));
    territories.put(20,
        new Territory("Southern_Europe", 20, CardSymbol.CAVALRY, Continente.EUROPE));
    territories.put(21, new Territory("North_Africa", 21, CardSymbol.INFANTRY, Continente.AFRICA));
    territories.put(22, new Territory("Egypt", 22, CardSymbol.INFANTRY, Continente.AFRICA));
    territories.put(23, new Territory("Congo", 23, CardSymbol.CAVALRY, Continente.AFRICA));
    territories.put(24, new Territory("East_Africa", 24, CardSymbol.CANNON, Continente.AFRICA));
    territories.put(25, new Territory("South_Africa", 25, CardSymbol.CANNON, Continente.AFRICA));
    territories.put(26, new Territory("Madagascar", 26, CardSymbol.INFANTRY, Continente.AFRICA));
    territories.put(27, new Territory("Siberia", 27, CardSymbol.CANNON, Continente.ASIA));
    territories.put(28, new Territory("Ural", 28, CardSymbol.CAVALRY, Continente.ASIA));
    territories.put(29, new Territory("China", 29, CardSymbol.CAVALRY, Continente.ASIA));
    territories.put(30, new Territory("Afghanistan", 30, CardSymbol.INFANTRY, Continente.ASIA));
    territories.put(31, new Territory("Middle_East", 31, CardSymbol.CANNON, Continente.ASIA));
    territories.put(32, new Territory("India", 32, CardSymbol.INFANTRY, Continente.ASIA));
    territories.put(33, new Territory("Siam", 33, CardSymbol.CANNON, Continente.ASIA));
    territories.put(34, new Territory("Yakutsk", 34, CardSymbol.CAVALRY, Continente.ASIA));
    territories.put(35, new Territory("Irkutsk", 35, CardSymbol.INFANTRY, Continente.ASIA));
    territories.put(36, new Territory("Mongolia", 36, CardSymbol.CANNON, Continente.ASIA));
    territories.put(37, new Territory("Japan", 37, CardSymbol.INFANTRY, Continente.ASIA));
    territories.put(38, new Territory("Kamchatka", 38, CardSymbol.CAVALRY, Continente.ASIA));
    territories.put(39, new Territory("Indonesia", 39, CardSymbol.CAVALRY, Continente.AUSTRALIA));
    territories.put(40, new Territory("New_Guinea", 40, CardSymbol.CAVALRY, Continente.AUSTRALIA));
    territories.put(41,
        new Territory("Western_Australia", 41, CardSymbol.CANNON, Continente.AUSTRALIA));
    territories.put(42,
        new Territory("Eastern_Australia", 42, CardSymbol.INFANTRY, Continente.AUSTRALIA));
  }

  /**
   * Method initialises all neighbor-relationships between territories
   */
  public void initialiseNeighbors() {
    // Alaska
    HashSet<Territory> a1 = new HashSet<>();
    a1.add(territories.get(2));
    a1.add(territories.get(3));
    a1.add(territories.get(38));
    territories.get(1).setNeighbor(a1);

    // North-West Territory
    HashSet<Territory> a2 = new HashSet<>();
    a2.add(territories.get(1));
    a2.add(territories.get(3));
    a2.add(territories.get(6));
    a2.add(territories.get(7));
    territories.get(2).setNeighbor(a2);

    // Alberta
    HashSet<Territory> a3 = new HashSet<>();
    a3.add(territories.get(1));
    a3.add(territories.get(2));
    a3.add(territories.get(7));
    a3.add(territories.get(4));
    territories.get(3).setNeighbor(a3);

    // Western-United-States
    HashSet<Territory> a4 = new HashSet<>();
    a4.add(territories.get(3));
    a4.add(territories.get(5));
    a4.add(territories.get(7));
    a4.add(territories.get(9));
    territories.get(4).setNeighbor(a4);

    // Central-America
    HashSet<Territory> a5 = new HashSet<>();
    a5.add(territories.get(4));
    a5.add(territories.get(9));
    a5.add(territories.get(10));
    territories.get(5).setNeighbor(a5);

    // Greenland
    HashSet<Territory> a6 = new HashSet<>();
    a6.add(territories.get(2));
    a6.add(territories.get(7));
    a6.add(territories.get(8));
    a6.add(territories.get(14));
    territories.get(6).setNeighbor(a6);

    // Ontario
    HashSet<Territory> a7 = new HashSet<>();
    a7.add(territories.get(2));
    a7.add(territories.get(3));
    a7.add(territories.get(4));
    a7.add(territories.get(6));
    a7.add(territories.get(8));
    a7.add(territories.get(9));
    territories.get(7).setNeighbor(a7);

    // Quebec
    HashSet<Territory> a8 = new HashSet<>();
    a8.add(territories.get(6));
    a8.add(territories.get(7));
    a8.add(territories.get(9));
    territories.get(8).setNeighbor(a8);

    // Eastern-United-States
    HashSet<Territory> a9 = new HashSet<>();
    a9.add(territories.get(4));
    a9.add(territories.get(5));
    a9.add(territories.get(7));
    a9.add(territories.get(8));
    territories.get(9).setNeighbor(a9);

    // Venezuela
    HashSet<Territory> a10 = new HashSet<>();
    a10.add(territories.get(5));
    a10.add(territories.get(11));
    a10.add(territories.get(12));
    territories.get(10).setNeighbor(a10);

    // Peru
    HashSet<Territory> a11 = new HashSet<>();
    a11.add(territories.get(10));
    a11.add(territories.get(12));
    a11.add(territories.get(13));
    territories.get(11).setNeighbor(a11);

    // Brazil
    HashSet<Territory> a12 = new HashSet<>();
    a12.add(territories.get(10));
    a12.add(territories.get(11));
    a12.add(territories.get(13));
    a12.add(territories.get(21));
    territories.get(12).setNeighbor(a12);

    // Argentinia
    HashSet<Territory> a13 = new HashSet<>();
    a13.add(territories.get(11));
    a13.add(territories.get(12));
    territories.get(13).setNeighbor(a13);

    // Iceland
    HashSet<Territory> a14 = new HashSet<>();
    a14.add(territories.get(6));
    a14.add(territories.get(17));
    territories.get(14).setNeighbor(a14);

    // Scandinavia
    HashSet<Territory> a15 = new HashSet<>();
    a15.add(territories.get(16));
    a15.add(territories.get(18));
    territories.get(15).setNeighbor(a15);

    // Ukraine
    HashSet<Territory> a16 = new HashSet<>();
    a16.add(territories.get(15));
    a16.add(territories.get(18));
    a16.add(territories.get(20));
    a16.add(territories.get(28));
    a16.add(territories.get(30));
    a16.add(territories.get(31));
    territories.get(16).setNeighbor(a16);

    // Great-Britain
    HashSet<Territory> a17 = new HashSet<>();
    a17.add(territories.get(14));
    a17.add(territories.get(18));
    a17.add(territories.get(19));
    territories.get(17).setNeighbor(a17);

    // Northern-Europe
    HashSet<Territory> a18 = new HashSet<>();
    a18.add(territories.get(15));
    a18.add(territories.get(16));
    a18.add(territories.get(17));
    a18.add(territories.get(19));
    a18.add(territories.get(20));
    territories.get(18).setNeighbor(a18);

    // Western-Europe
    HashSet<Territory> a19 = new HashSet<>();
    a19.add(territories.get(17));
    a19.add(territories.get(18));
    a19.add(territories.get(20));
    a19.add(territories.get(21));
    territories.get(19).setNeighbor(a19);

    // Southern-Europe
    HashSet<Territory> a20 = new HashSet<>();
    a20.add(territories.get(16));
    a20.add(territories.get(18));
    a20.add(territories.get(19));
    a20.add(territories.get(21));
    a20.add(territories.get(22));
    a20.add(territories.get(31));
    territories.get(20).setNeighbor(a20);

    // North-Africa
    HashSet<Territory> a21 = new HashSet<>();
    a21.add(territories.get(12));
    a21.add(territories.get(19));
    a21.add(territories.get(20));
    a21.add(territories.get(22));
    a21.add(territories.get(24));
    a21.add(territories.get(23));
    territories.get(21).setNeighbor(a21);

    // Egypt
    HashSet<Territory> a22 = new HashSet<>();
    a22.add(territories.get(20));
    a22.add(territories.get(21));
    a22.add(territories.get(24));
    a22.add(territories.get(31));
    territories.get(22).setNeighbor(a22);

    // Congo
    HashSet<Territory> a23 = new HashSet<>();
    a23.add(territories.get(21));
    a23.add(territories.get(24));
    a23.add(territories.get(25));
    territories.get(23).setNeighbor(a23);

    // East-Africa
    HashSet<Territory> a24 = new HashSet<>();
    a24.add(territories.get(21));
    a24.add(territories.get(22));
    a24.add(territories.get(23));
    a24.add(territories.get(25));
    a24.add(territories.get(26));
    a24.add(territories.get(31));
    territories.get(24).setNeighbor(a24);

    // South-Africa
    HashSet<Territory> a25 = new HashSet<>();
    a25.add(territories.get(23));
    a25.add(territories.get(24));
    a25.add(territories.get(26));
    territories.get(25).setNeighbor(a25);

    // Madagascar
    HashSet<Territory> a26 = new HashSet<>();
    a26.add(territories.get(24));
    a26.add(territories.get(25));
    territories.get(26).setNeighbor(a26);

    // Siberia
    HashSet<Territory> a27 = new HashSet<>();
    a27.add(territories.get(28));
    a27.add(territories.get(29));
    a27.add(territories.get(34));
    a27.add(territories.get(35));
    a27.add(territories.get(36));
    territories.get(27).setNeighbor(a27);

    // Ural
    HashSet<Territory> a28 = new HashSet<>();
    a28.add(territories.get(16));
    a28.add(territories.get(27));
    a28.add(territories.get(29));
    a28.add(territories.get(30));
    territories.get(28).setNeighbor(a28);

    // China
    HashSet<Territory> a29 = new HashSet<>();
    a29.add(territories.get(27));
    a29.add(territories.get(28));
    a29.add(territories.get(30));
    a29.add(territories.get(32));
    a29.add(territories.get(33));
    a29.add(territories.get(36));
    territories.get(29).setNeighbor(a29);

    // Afghanistan
    HashSet<Territory> a30 = new HashSet<>();
    a30.add(territories.get(16));
    a30.add(territories.get(28));
    a30.add(territories.get(29));
    a30.add(territories.get(31));
    a30.add(territories.get(32));
    territories.get(30).setNeighbor(a30);

    // Middle-East
    HashSet<Territory> a31 = new HashSet<>();
    a31.add(territories.get(16));
    a31.add(territories.get(20));
    a31.add(territories.get(22));
    a31.add(territories.get(24));
    a31.add(territories.get(30));
    a31.add(territories.get(32));
    territories.get(31).setNeighbor(a31);

    // India
    HashSet<Territory> a32 = new HashSet<>();
    a32.add(territories.get(29));
    a32.add(territories.get(30));
    a32.add(territories.get(31));
    a32.add(territories.get(33));
    territories.get(32).setNeighbor(a32);

    // Siam
    HashSet<Territory> a33 = new HashSet<>();
    a33.add(territories.get(29));
    a33.add(territories.get(32));
    a33.add(territories.get(39));
    territories.get(33).setNeighbor(a33);

    // Yakutsk
    HashSet<Territory> a34 = new HashSet<>();
    a34.add(territories.get(27));
    a34.add(territories.get(35));
    a34.add(territories.get(38));
    territories.get(34).setNeighbor(a34);

    // Irkutsk
    HashSet<Territory> a35 = new HashSet<>();
    a35.add(territories.get(27));
    a35.add(territories.get(34));
    a35.add(territories.get(36));
    a35.add(territories.get(38));
    territories.get(35).setNeighbor(a35);

    // Mongolia
    HashSet<Territory> a36 = new HashSet<>();
    a36.add(territories.get(27));
    a36.add(territories.get(29));
    a36.add(territories.get(35));
    a36.add(territories.get(37));
    a36.add(territories.get(38));
    territories.get(36).setNeighbor(a36);

    // Japan
    HashSet<Territory> a37 = new HashSet<>();
    a37.add(territories.get(36));
    a37.add(territories.get(38));
    territories.get(37).setNeighbor(a37);

    // Kamschatka
    HashSet<Territory> a38 = new HashSet<>();
    a38.add(territories.get(1));
    a38.add(territories.get(34));
    a38.add(territories.get(35));
    a38.add(territories.get(36));
    a38.add(territories.get(37));
    territories.get(38).setNeighbor(a38);

    // Indonesia
    HashSet<Territory> a39 = new HashSet<>();
    a39.add(territories.get(33));
    a39.add(territories.get(40));
    a39.add(territories.get(41));
    territories.get(39).setNeighbor(a39);

    // New-Guinea
    HashSet<Territory> a40 = new HashSet<>();
    a40.add(territories.get(39));
    a40.add(territories.get(41));
    a40.add(territories.get(42));
    territories.get(40).setNeighbor(a40);

    // Western-Australia
    HashSet<Territory> a41 = new HashSet<>();
    a41.add(territories.get(39));
    a41.add(territories.get(40));
    a41.add(territories.get(42));
    territories.get(41).setNeighbor(a41);

    // Eastern-Australia
    HashSet<Territory> a42 = new HashSet<>();
    a42.add(territories.get(40));
    a42.add(territories.get(41));
    territories.get(42).setNeighbor(a42);
  }

  /**
   * Method initialises all continents and links them with territories
   */
  public void initialiseContinents() {
    HashSet<Territory> c1 = new HashSet<>();
    for (int i = 1; i < 10; i++) {
      c1.add(territories.get(i));
    }
    continent.put(Continente.NORTHAMERICA, new Continent(Continente.NORTHAMERICA, 5, c1));

    HashSet<Territory> c2 = new HashSet<>();
    for (int i = 10; i < 14; i++) {
      c2.add(territories.get(i));
    }
    continent.put(Continente.SOUTHAMERICA, new Continent(Continente.SOUTHAMERICA, 2, c2));

    HashSet<Territory> c3 = new HashSet<>();
    for (int i = 14; i < 21; i++) {
      c3.add(territories.get(i));
    }
    continent.put(Continente.EUROPE, new Continent(Continente.EUROPE, 5, c3));

    HashSet<Territory> c4 = new HashSet<>();
    for (int i = 21; i < 27; i++) {
      c4.add(territories.get(i));
    }
    continent.put(Continente.AFRICA, new Continent(Continente.AFRICA, 3, c4));

    HashSet<Territory> c5 = new HashSet<>();
    for (int i = 27; i < 39; i++) {
      c5.add(territories.get(i));
    }
    continent.put(Continente.ASIA, new Continent(Continente.ASIA, 7, c5));

    HashSet<Territory> c6 = new HashSet<>();
    for (int i = 39; i < 43; i++) {
      c6.add(territories.get(i));
    }
    continent.put(Continente.AUSTRALIA, new Continent(Continente.AUSTRALIA, 2, c6));
  }

  /**
   * Method that creates a link between objects of territory and the gui territory that is
   * referenced by a javaFx region. It should simplify interaction between the gui board and the
   * logical board setting.
   */
  public void createTerritoriesBoardRegion() {
    for (Territory t : territories.values()) {
      if (t.getBoardRegion() != null) {
        territoriesBoardRegion.put(t.getBoardRegion(), t);
        territoriesRegion.put(t.getBoardRegion().getRegion(), t);
        territoriesName.put(t.getBoardRegion().getHeadline(), t);
        territoriesNoA.put(t.getBoardRegion().getNumberOfArmy(), t);
      }
    }
  }

  /**
   * @return HashMap of territories that are referenced by the territoriesId Getter-method
   */
  public HashMap<Integer, Territory> getTerritories() {
    return territories;
  }

  /**
   * @return HashMap of territories that are reference by the territories board region Getter-method
   */
  public HashMap<BoardRegion, Territory> getTerritoriesBoardRegion() {
    return territoriesBoardRegion;
  }

  /**
   * @return HashMap of territories that are reference by the territories gui region Getter-method
   */
  public HashMap<Region, Territory> getTerritoriesRegion() {
    return territoriesRegion;
  }

  /**
   * @return HashMap of territories that are referenced by the territories gui label, that represent
   *         the territories name Getter-method
   */
  public HashMap<Label, Territory> getTerritoriesName() {
    return territoriesName;
  }

  /**
   * @return HashMap of territories that are referenced by the territories gui label, that represent
   *         the territories number of armies Getter-method
   */
  public HashMap<Label, Territory> getTerritoriesNoA() {
    return territoriesNoA;
  }

  /**
   * @return HashMap of Continents Getter-method
   */
  public HashMap<Continente, Continent> getContinent() {
    return continent;
  }
}
