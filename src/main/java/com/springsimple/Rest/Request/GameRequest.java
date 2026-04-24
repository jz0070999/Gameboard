package com.springsimple.Rest.Request;

public class GameRequest {
    private int playerid;

    public GameRequest(){
    }

    public int getPlayerid(){
        return playerid; 
    }

    public void setPlayerid(int playerid){
        this.playerid = playerid;
    }
}