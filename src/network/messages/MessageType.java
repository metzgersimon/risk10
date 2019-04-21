package network.messages;

public enum MessageType {
DISPLAY, //for the statistic
BROADCAST,SEND,INGAME,ALLIANCE, //enums for chat messages
JOIN, JOIN_REPONSE, PLAYER_SIZE,PLAYER_LIST_UPDATE,LEAVE, // enums for joining and leaving the game
START_GAME,//enums for the game below
INITIAL_TERRITORY, DISTRIBUTE_ARMY, //enums for the start phase of the game
GAMESTATE,ATTACK; //
}
