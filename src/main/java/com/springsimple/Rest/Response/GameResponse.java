package com.springsimple.Rest.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameResponse {

    private int id;
    private int playerId;
    private int locationColumn;
    private int locationRow;
    private String status;

    public GameResponse(int id, int playerId, int locationColumn, int locationRow, String status) {
        this.id = id;
        this.playerId = playerId;
        this.locationColumn = locationColumn;
        this.locationRow = locationRow;
        this.status = status;
    }

    @JsonProperty("id") 
    public int getId(){
        return this.id;
    }

    @JsonProperty("playerid") 
    public int playerId() {
        return this.playerId;
    }

    @JsonProperty("column") 
    public int locationColumn() {
        return this.locationColumn;
    }

    @JsonProperty("row") 
    public int locationRow() {
        return this.locationRow;
    }

    @JsonProperty("status") 
    public String status(){
        return this.status;
    }




}
