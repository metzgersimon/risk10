package game;


/**
 * 
 * @author Preston
 *
 */

public class TutorialMessages {

  public static String welcome = "Welcome to Tutorial Mode";
  
  public static String intro = "This is the game board. "
      + "Interaction will be done through clicking "
      + "on the territories. Attack and defeat opponent "
      + "areas to obtain their territories and risk cards,"
      + " which can be traded in for armies.\n\n At the beginning"
      + " of each turn, you will be given a number of"
      + " armies to distribute across your territories.\n\n"
      + "You can then choose to attack,"
      + " fortify or pass the round using the red arrow.\n"
      + "Firstly, players take turns claming their territories. ";
  
  public static String distributing = "You are now in the "
      + "distributing phase. At the beginning of each turn,"
      + "the player receives a number of Armies"
      + "to distribute across their territories.\n\n"
      + "The number of armies you get for each turn is calculated"
      + "from the number of territories owned, continents claimed"
      + "and number of cards traded in with a minimum of 3 armies.";
  
  public static String distributingTip = "Tip: Isolated "
      + "continents are easier to maintain,"
      + " but are also more difficult to expand from";
  
  public static String newRound = "It is now your turn. First, place "; 
  
  public static String attacking1 = "You are now in the attacking phase. "
      + "Click one of your territories to start";
  
  public static String attacking2 = "Now pick an adjacent territory which"
      + " belongs to an opponent to attack";
  
  public static String dicing = "You now have to choose "
      + "how many armies you "
      + "want to attack with. You then roll dices "
      + "against your chosen opponent";
  
  public static String attackingTip = "Tip: Your chances "
      + "of winning an attack are "
      + "higher if you attack a territory with fewer armies "
      + "than your attacking territory";
  
  public static String attackSuccess = "Your attack was a success!"
      + " Your opponent lost armies."
      + " You can attack again or click the red arrow to pass";
  
  public static String conqueredTerritory = "You conquered "
      + "your opponents territory! "
      + "This territory is now yours and you get a risk card";
  
  public static String attackFailed = "Your attack failed! "
      + "You lost armies. "
      + "You can attack again or pass";
  
  public static String lostTerritory = "You defensive efforts"
      + " failed and you "
      + "have no armies left on this territory."
      + " It now belongs to your opponent";
  
  public static String fortify = "You can now move armies"
      + " between adjacent territories";
  
  public static String fortifyTip = "Tip: Decrease the chances "
      + "of weak spots by "
      + "fortifying your border territories against opponent attacks";
  
  public static String tradeIn = "You now have the option to trade in 3 "
      + "compatible risk cards for armies";
  
  public static String tradeInTip = "You don't have to trade in"
      + " your cards right away,"
      + " feel free to hold them as long as you want "
      + "unless you have 5 cards,"
      + " where you have to trade in a set of 3";
  
  public static String forcedTrade = "You have 5 risk cards: you have to"
      + " trade in 3 to continue";
  
  public static String alliance = "You can form alliances with fellow "
      + "players to form a powerful union";
  
  public static String allianceTip = "Form alliances to face "
      + "stronger opponents, "
      + "but be wary: your alliance partner can turn "
      + "against you at any time";
  
  public static String continents = "";
  
  public static String cards = "";
  
  //Multiplayer messages
  public static String chat = "This is the chat. To talk to all players,"
      + " write your message into the message box and hit send. "
      + "To send a message privately to another player, "
      + "enter their name into the @Name box.";
  
  //Rule Book
  public static String ruleBook = 
      "1 Overview\nRisk​ is a strategy board game of diplomacy, "
      + "conflict and conquest for two to six players.\n" 
      + "The standard version is played on a board depicting a "
      + "political map of the earth, divided into\n"  
      + "forty-two territories, which are grouped into six continents."
      + " Turn rotates among players who\n" 
      + "control armies of playing pieces with which they attempt"
      + " to capture territories from other\n"  
      + "players, with results determined by dice rolls. "
      + "Players may form and dissolve alliances during\n" 
      + "the course of the game. The goal of the game is to "
      + "occupy every territory on the board and\n"
      + "in doing so, eliminate the other players.\n\n\n"
      
      + "2 The Parts\n\n"
      + "Every Risk game has a map that has a similar shape to"
      + " the original map of the world. Every\n" 
      + "Player can place armies on unclaimed or hers/his countries."
      + " These armies can fight at each\n" 
      + "other for bordering countries. These fights are decided"
      + " by dice/die that are rolled at every\n" 
      + "round in the fight.\n\n" 
      + "● The ​Risk​ board has 6 continents — North America, "
      + "South America, Europe, Africa,\n" 
      + "   Asia, and the Australian Archipelago — "
      + "and 42 countries.\n" 
      + "● The ​Risk​ armies come in different colors,"
      + " each assigned to a player\n" 
      + "● There are 44 risk cards. 42 cards are marked "
      + "with countries as\n" 
      + "  well as an infantry, cavalry, or artillery symbol "
      + "and there are two \"Wild\" cards.\n\n\n" 
      
      + "3 Beginning of the Game\n\n"
      + "Depending on how many players starting the game the amount"
      + " of armies \nto place for every player varies:\n\n"
      + "● 2 Players -> 40 armies for each\n"
      + "● 3 Players -> 35 armies for each\n"
      + "● 4 Players -> 30 armies for each\n"
      + "● 5 Players -> 25 armies for each\n"
      + "● 6 Players -> 20 armies for each\n\n"
      + "The player who will start the game and make his first move"
      + " will be determined by random. They\n" 
      + "will then choose an open territory and place armies on it.\n" 
      + "Each player will take turns selecting an open "
      + "territory until all territories are occupied.\n" 
      + "Once players have claimed all the 42 territories on the"
      + " board, players place their remaining\n" 
      + "armies onto territories they already claim in any order "
      + "they choose.\n\n"
      
      + "At the beginning of each turn, players receive more armies."
      + " The number of armies is\n" 
      + "determined by:\n\n"
      + "● The number of territories you own. For every three"
      + " countries, the player gets one\n" 
      + "   army. For example, if you had 11 countries, you would "
      + "receive 3 armies; if you had\n" 
      + "   22 countries, you would receive 7 armies.\n" 
      + "● Turning in cards. Cards can be turned in when you have"
      + " a three of a kind (e.g. all\n" 
      + "   three cards have artillery pictures) or all three types"
      + " of armies (soldier, cavalry,\n" 
      + "   artillery). For the first set of cards you turn in, you "
      + "receive 4 armies; 6 for the\n" 
      + "   second; 8 for the third; 10 for the fourth; 12 for the fifth;"
      + " 15 for the sixth; and for\n"
      + "   every additional set thereafter, 5 more armies than the "
      + "previous set turned in. If you\n" 
      + "   have 5 or more ​Risk​ cards at the beginning of a turn, "
      + "you must turn at least one set\n" 
      + "   of them in.\n" 
      + "● Owning all the territories of a continent. For each continent "
      + "that you completely\n" 
      + "   dominate (no other enemy armies are present), you "
      + "receive reinforcements. You\n" 
      + "   receive 3 armies for Africa, 7 armies for Asia, 2 armies"
      + " for Australia, 5 armies for\n" 
      + "   Europe, 5 armies for North America and 2 armies for "
      + "South America. To see all continents\n" 
      + "   , open the Continent map\n" 
      + "● Note​: if the amount of armies you would receive at the "
      + "beginning of your turn is less\n" 
      + "   than three, you will receive three armies.\n\n"
      + "You may place the armies you received at the beginning of "
      + "your turn wherever you have an\n" 
      + "army presence, in whatever proportion. If you wish, "
      + "you can place one army in each of your\n" 
      + "territories; or you can place all of your armies in one "
      + "territory. The choice is up to you.\n\n" 
      + "● If, during the beginning of your turn, you turned in a "
      + "set of cards with a territory that\n" 
      + "   you owned, you receive two extra infantrymen.\n" 
      + "   You must place those infantrymen on\n" 
      + "   the territory specified by the card.\n\n\n"
      + "4 Attacking\n\n"
      + "You may only attack other territories that are adjacent to"
      + " a territory you own or that are\n" 
      + "connected to a territory you own by a sea-lane. For example,"
      + " you cannot attack India from\n" 
      + "the Eastern United States because the territories "
      + "are not adjacent.\n" 
      + "You may attack the same territory more than once, or "
      + "you may attack different territories.\n" 
      + "You can attack the same territory from the same adjacent"
      + " position, or you can attack it from\n" 
      + "different adjacent positions.\n\n" 
      + "● Understand that attacking is optional. A player may "
      + "decide not to attack at all during\n" 
      + "   a turn, only deploying armies.\n\n"
      + "When you want to attack another territory, decide how many"
      + " armies \nyou are going to use in your attack.​\n"
      + "Because your territory must be occupied at all times,\n" 
      + "you must leave at least one army behind. The number of "
      + "armies you attack with will\n" 
      + "determine how many dice you get to roll "
      + "when you square off the\n" 
      + "opponent whose territory you are defending.\n\n"
      + "● 1 army = 1 die\n"
      + "● 2 armies = 2 dice\n"
      + "● 3 armies = 3 dice\n\n"
      + "You roll up to three red dice, depending on your "
      + "troop size. The defending player rolls the\n" 
      + "same number of blue dice as the number of troops "
      + "in their defending territory, with a\n" 
      + "maximum of two.\n\n"
      + "● Match up the highest red die with the highest blue die,"
      + " and match the second\n" 
      + "   highest red die with the second highest blue die. "
      + "If there is only one blue die, only\n" 
      + "   match up the highest red die with the blue die.\n"
      + "● Remove one of your pieces from the attacking territory"
      + " if the blue die is higher or\n" 
      + "   equal to its corresponding red die.\n" 
      + "● Remove one of your opponent’s pieces from the defending"
      + " territory if the red die is\n" 
      + "   higher to its corresponding blue die.\n\n"
      + "If you successfully wipe out all of the defending "
      + "armies in the area you are attacking, then you\n" 
      + "will occupy the territory with the amount of attacking"
      + " armies as used in the attack.\n" 
      + "If you attack with three dice (or three armies), "
      + "you will colonize the newly-acquired territory\n" 
      + "with three armies.\n\n" 
      + "If at the end of your attacking turn you've conquered "
      + "at least one territory, then you have\n" 
      + "earned a Risk​ card. You cannot earn more than one ​ Risk"
      + " ​ card for this. The goal is to collect\n" 
      + "sets of three “Risk” cards to exchange them for new armies."
      + "\n\n" 
      +   "● If you manage to wipe out an opponent by destroying"
      + " his or her last army, you gain\n"
      + "   possession of all the ​ Risk ​ cards he or she may"
      + " have had in their hands.";
}
