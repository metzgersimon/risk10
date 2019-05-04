package game;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Vector;
import org.junit.Assert;
import org.junit.Test;
import gui.controller.BoardController;
import javafx.scene.paint.Color;
import main.Main;

/**
 * 
 * @author qiychen
 *
 */
public class AttackTest {
  boolean result;

  @Test
  public void testAttack() {
    Main.g = new Game();
    Player p1 = new Player("Test1", PlayerColor.BLUE, Main.g);
    Main.g.setCurrentPlayer(p1);
    // First attack
    Territory t1 = new Territory("China", 29, CardSymbol.CAVALRY, Continente.ASIA);
    t1.setOwner(p1);
    p1.addTerritories(t1);
    t1.setNumberOfArmies(3);
    int attackBeforearmies1 = t1.getNumberOfArmies();
    Player p2 = new Player("Test2", PlayerColor.GREEN, Main.g);
    Territory t2 = new Territory("Siberia", 27, CardSymbol.CANNON, Continente.ASIA);
    t2.setOwner(p2);
    p2.addTerritories(t2);
    t2.setNumberOfArmies(2);
    int attackBeforearmies2 = t2.getNumberOfArmies();
    Vector<Integer> attacker = Dice.rollDices(t1.getNumberOfArmies());
    Vector<Integer> defender = Dice.rollDices(t2.getNumberOfArmies());
    // test whether the number of territories changed after attack
    try {
      // p1 attack territory Siberia with 2 armies
      p1.attack(attacker, defender, t1, t2, 2);
      System.out.println(attackBeforearmies1 + " after attack " + t1.getNumberOfArmies());
      System.out.println(attackBeforearmies2 + " after attack " + t2.getNumberOfArmies());
      assertTrue(attackBeforearmies1 != t1.getNumberOfArmies()
          || attackBeforearmies2 != t2.getNumberOfArmies());
    } catch (NullPointerException e) {
      System.out.println("Try to reach board gui");
      assertTrue(attackBeforearmies1 != t1.getNumberOfArmies()
          || attackBeforearmies2 != t2.getNumberOfArmies());
    }
    // Second attack
    Territory t3 = new Territory("Great_Britain", 17, CardSymbol.CAVALRY, Continente.EUROPE);
    t3.setOwner(p1);
    p1.addTerritories(t3);
    t3.setNumberOfArmies(3);
    Territory t4 = new Territory("Northern_Europe", 18, CardSymbol.CAVALRY, Continente.EUROPE);
    t4.setOwner(p2);
    p2.addTerritories(t4);
    t4.setNumberOfArmies(1);
    Vector<Integer> attacker2 = Dice.rollDices(t3.getNumberOfArmies());
    Vector<Integer> defender2 = Dice.rollDices(t4.getNumberOfArmies());
    // test whether the owner of the territory changes depending on whether the attack is successful
    // or not
    try {
      // p1 attack territory Northern_Europe with 2 armies
      result = p1.attack(attacker2, defender2, t3, t4, 2);
      if (!result) {
        assertFalse(t3.getOwner() == t4.getOwner());
      }
    } catch (NullPointerException e) {
      if (p1.getSuccessfullAttack()) {
        assertTrue(t3.getOwner() == t4.getOwner());
      } else {
        assertFalse(t3.getOwner() == t4.getOwner());
      }
    }

  }

}
