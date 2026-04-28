package com.springsimple.Rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springsimple.Common.Messages;
import com.springsimple.DataObjects.Game;
import com.springsimple.DataObjects.Player;
import com.springsimple.Model.GameModel;
import com.springsimple.Model.PlayerModel;
import com.springsimple.Rest.Request.MoveRequest;
import com.springsimple.Rest.Request.GameRequest;
import com.springsimple.Rest.Response.GameResponse;
import com.springsimple.Rest.Response.GameResponseMapper;
import com.springsimple.Rest.Response.PlayerResponse;
import com.springsimple.Rest.Response.Response;

import jakarta.servlet.http.HttpSession;

@RestController
public class GameController {

    private final GameModel gameModel;
    private final PlayerModel playerModel;
    private final Messages messages;

    public GameController(GameModel gameModel, PlayerModel playerModel, Messages messages) {
        this.gameModel = gameModel;
        this.playerModel = playerModel;
        this.messages = messages;
    }

    @GetMapping("/api/game")
    public ResponseEntity<Response<List<GameResponse>>> getAllGames() {
        List<Game> gameList = this.gameModel.GetAllGames();

        Response<List<GameResponse>> response = new Response<List<GameResponse>>(true, null,
                GameResponseMapper.mapFromGameModel(gameList));

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/api/game/{id}")
    public ResponseEntity<Response<GameResponse>> getGameById(@PathVariable int id) {

        Game game = this.gameModel.GetGameById(id);

        List<String> errors = this.messages.GetErrors();

        if (!errors.isEmpty()) {

            Response<GameResponse> response = new Response<GameResponse>(false, errors, null);

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);

        }

        Response<GameResponse> response = new Response<GameResponse>(true, null,
                GameResponseMapper.mapFromGameModel(game));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/api/game/status/{status}")
    public ResponseEntity<Response<PlayerResponse>> getGameByStatus(@PathVariable String status) {

        int playerId = 1;

        Player player = this.playerModel.GetPlayerById(playerId);
        List<Game> games = this.gameModel.GetGamesByPlayerId(1);

        PlayerResponse playerResponse = new PlayerResponse(player.getId(), player.getName(),
                GameResponseMapper.mapFromGameModel(games));

        Response<PlayerResponse> response = new Response<PlayerResponse>(true, null, playerResponse);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    // chnage the method signature to inclufe HttpSession
    // why do I add it to this @PostMapping method and not the others? Because this is the only method that needs to check if the player is logged in. 
    @PostMapping("/api/game/{gameid}/move")
    public ResponseEntity<Response<GameResponse>> makeMove(@PathVariable int gameid, @RequestBody MoveRequest move, HttpSession session) {

        Game game = this.gameModel.GetGameById(gameid);

        List<String> errors = this.messages.GetErrors();
        if (!errors.isEmpty()) {
            Response<GameResponse> response = new Response<GameResponse>(false, errors, null);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);

        }

        game = this.gameModel.MakeMove(gameid, move.getColumn(), move.getRow());

        Response<GameResponse> response = new Response<GameResponse>(true, null,
                GameResponseMapper.mapFromGameModel(game));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/api/game")
    public ResponseEntity<Response<GameResponse>> createNewGame(@RequestBody GameRequest newGame) {
        Game game = this.gameModel.CreateGame(newGame.getPlayerid());

        List<String> errors = this.messages.GetErrors();
        // !errors.isEmpty Dr. Florin explained in class.
        if (!errors.isEmpty()) {
            Response<GameResponse> response = new Response<GameResponse>(false, errors, null);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);

        }

        Response<GameResponse> response = new Response<GameResponse>(true, null,
                GameResponseMapper.mapFromGameModel(game));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/api/player/{playerId}/games/active")
    public ResponseEntity<Response<List<GameResponse>>> getActiveGamesForPlayer(@PathVariable int playerId) {
        List<Game> games = this.gameModel.GetGamesByPlayerId(playerId);

        List<Game> activeGames = new ArrayList<>();
        for (Game game : games) {
            if (game.getStatus().equals("Active")) {
                activeGames.add(game);
            }
        }

        if (activeGames.isEmpty()) {
            List<String> errors = new ArrayList<String>();
            errors.add("No active games found for player with id: " + playerId);
            Response<List<GameResponse>> response = new Response<List<GameResponse>>(false, errors, null);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }

        Response<List<GameResponse>> response = new Response<List<GameResponse>>(true, null,
                GameResponseMapper.mapFromGameModel(activeGames));

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);

    }

    // Why did Dr. Florin comment this out?
    // @PostMapping("/api/game")
    // public ResponseEntity<GameResponse> createNewGame(@RequestBody GameRequest
    // newGame) {

    // Game game = GameModel.CreateGame(GameRequestMapper.mapToGameModel(newGame));

    // return ResponseEntity.status(HttpStatus.OK)
    // .contentType(MediaType.APPLICATION_JSON)
    // .body(GameResponseMapper.mapFromGameModel(game));
    // }

    // @PostMapping("/api/game/{id}")
    // public ResponseEntity<GameResponse> updateGame(@PathVariable int id,
    // @RequestBody GameRequest newGame) {
    // Game game = GameModel.UpdateGame(id,
    // GameRequestMapper.mapToGameModel(newGame));
    // return ResponseEntity.status(HttpStatus.OK)
    // .contentType(MediaType.APPLICATION_JSON)
    // .body(GameResponseMapper.mapFromGameModel(game));
    // }

    // @DeleteMapping("/api/game/{id}")
    // public ResponseEntity<GameResponse> deleteGame(@PathVariable int id) {

    // Game game = GameModel.DeleteGame(id);
    // return ResponseEntity.status(HttpStatus.OK)
    // .contentType(MediaType.APPLICATION_JSON)
    // .body(GameResponseMapper.mapFromGameModel(game));
    // }

    // step 1, add the endpoint, this part is for blocking access control part 
    @PostMapping("/api/login/player/{playerid}")
    public ResponseEntity<Response<String>> loginPlayer(@PathVariable int playerid, HttpSession session){
        
        session.setAttribute("playerid", playerid);
        Response<String> response = new Response<String>(true, null, "player logged in");  

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

}
