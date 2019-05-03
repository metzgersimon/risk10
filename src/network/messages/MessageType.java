package network.messages;

public enum MessageType {
DISPLAY, //for the statistic
BROADCAST,INGAME,ALLIANCE, // for chat messages
JOIN, JOIN_REPONSE,LEAVE, // for joining and leaving the game
START_GAME,// for the game below
INITIAL_TERRITORY, DISTRIBUTE_ARMY, FURTHER_DISTRIBUTE_ARMY, //enums for the start phase of the game
GAMESTATE,ATTACK,FORTIFY; //
}
