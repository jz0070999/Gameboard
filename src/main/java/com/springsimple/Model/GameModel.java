package com.springsimple.Model;

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
    // Game rules should be in the model, not the controller. The controller should just call a method on the model to make the move, and the model should handle all the game logic. 
    // What MakeMove needs to do:
    // 1. is the game active or complete 
    // 2. Is the move on the board? 
    // 3. Is the move only one cell away 
    // 4. If valid, update row/column 
    // 5. If new position is (3, 3) set status to complete 

    // Given a new gameif and a new column and row, please try to move the player here, will return a game object afterward
    public Game MakeMove(int gameid, int locationColumn, int locationRow) {
        // creates a method named MakeMove and it receives 3 pieces of information, the gameid so which game are we moving in,
        // the locationColumn so which column does the player want to move to, and the locationRow so whoich row does the player want to move to
        
        // finds the game from the database using the gameid, 
        Game game = GetGameById(gameid);
        if (game == null) {
            return null;
        }

        // check if the game is active 
        if(!game.getStatus().equals("Active")){
            messages.AddError("The game is not active");
            return game; 
        }

        // check board bounds
        // it is indexed by 0, it is 0, 1, 2, 3 so if you go to the game board it is like an array
        if(locationColumn < 0 || locationColumn > 3 || locationRow < 0 || locationRow > 3){
            messages.AddError("The move is off the board.");
            return game; 
        }

        // check only one cell away 
        int columnDiff = Math.abs(locationColumn - game.getLocationColumn());
        int rowDiff = Math.abs(locationRow - game.getLocationRow());

        if(columnDiff + rowDiff != 1){
            messages.AddError("You may only move 1 cell away.");
            return game;
        }

        // valid move 
        game.setLocationColumn(locationColumn); 
        game.setLocationRow(locationRow); 

        if(locationColumn == 3 && locationRow == 3){
            game.setStatus("Complete");
        }

        // gameRepository is an interface 
        // this interface extends a type that is built into the spring framework called the Jpa repositoru
        // JPA repository does 

        this.gameRepository.save(game); 
        return game; 
    }

    


}
