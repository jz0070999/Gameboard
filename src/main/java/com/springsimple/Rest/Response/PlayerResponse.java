package com.springsimple.Rest.Response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerResponse {

    private int id;
    private String name;
    private List<GameResponse> games;

    public PlayerResponse(int id, String name, List<GameResponse> games) {
        this.id = id;
        this.name = name;
        this.games = games;
    }

    @JsonProperty("id") 
    public int getId(){
        return this.id;
    }

    @JsonProperty("name") 
    public String getName() {
        return this.name;
    }

    @JsonProperty("games") 
    public List<GameResponse> getGames() {
        return this.games;
    }


}
