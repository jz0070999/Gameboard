package com.springsimple.Rest.Response;

import java.util.ArrayList;
import java.util.List;

import com.springsimple.DataObjects.Game;


public class GameResponseMapper {

    public static GameResponse mapFromGameModel(Game from) {
        return new GameResponse(from.getId(), from.getPlayerId(), from.getLocationColumn(), from.getLocationRow(), from.getStatus());
    }


    public static List<GameResponse> mapFromGameModel(List<Game> fromList) {
        ArrayList<GameResponse> toList = new ArrayList<GameResponse>(fromList.size());

        for(Game game : fromList) {
            toList.add(mapFromGameModel(game));
        }

        return toList;
    }


}
