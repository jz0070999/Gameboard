package com.springsimple.Model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springsimple.Common.Messages;
import com.springsimple.DataObjects.Game;
import com.springsimple.DataObjects.Player;
import com.springsimple.Repository.GameRepository;

@Service
public class GameModel {

    private final GameRepository gameRepository;
    private final PlayerModel playerModel;
    private final Messages messages;

    public GameModel(GameRepository gameRepository, PlayerModel playerModel, Messages messages) {
        this.gameRepository = gameRepository;
        this.playerModel = playerModel;
        this.messages = messages;
    }

    // private void initialize() {
    //     games = new ArrayList<Game>();

    //     games.add(new Game(1, 1, 0, 0, "Active"));
    //     games.add(new Game(2, 1, 3, 3, "Complete"));
    
    //     nextId = 3;
    // }
    
    public List<Game> GetAllGames() {
        return this.gameRepository.findAll();
    }

    public List<Game> GetAllGamesByStatus(String status) {
        return this.gameRepository.findAllGameByStatus(status);
    }

    public List<Game> GetGamesByPlayerId(int playerid) {
        return this.gameRepository.findAllGameByPlayer_Id(playerid);
    }

    public Game GetGameById(int id) {

        Game game = this.gameRepository.findById(id)
                .orElse(null);

        if (game == null) {
            messages.AddError("The GameId is invalid.");            
        }
        return game;
    }

    // CreateGame method dinds the player, makes a new game starting at (0,0), sets
    // status to "Active", saves it,
    // then returns it.
    public Game CreateGame(int playerId) {
        
        Player player = this.playerModel.GetPlayerById(playerId);
        
        Game game = new Game(player, 0, 0, "Active");
        this.gameRepository.save(game); 
        return game;
    }

    public Game MakeMove(int gameid, int locationColumn, int locationRow) {
        Game game = GetGameById(gameid);

        game.setLocationColumn(locationColumn);
        game.setLocationRow(locationRow);
        this.gameRepository.save(game);
        return game;
    }

    


}
