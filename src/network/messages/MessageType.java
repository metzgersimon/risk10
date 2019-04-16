package network.messages;

public enum MessageType {
DISPLAY, //for the statistic
BROADCAST,SEND,LOBBY,ALLIANCE, //enums for chat messages
JOIN, JOIN_REPONSE, PLAYER_SIZE,PLAYER_LIST_UPDATE,LEAVE, // enums for joining and leaving the game
START_GAME,//enums for the game below
INITIAL_TERRITORY, //enums for the start phase of the game
GAMESTATE,ATTACK; //
}
