package network.messages;

public enum MessageType {
DISPLAY, //for the statistic
BROADCAST,SEND,INGAME,ALLIANCE, //enums for chat messages
JOIN, JOIN_REPONSE,LEAVE, // enums for joining and leaving the game
START_GAME,//enums for the game below
INITIAL_TERRITORY, DISTRIBUTE_ARMY, FURTHER_DISTRIBUTE_ARMY, //enums for the start phase of the game
GAMESTATE,ATTACK,FORTIFY; //
}
