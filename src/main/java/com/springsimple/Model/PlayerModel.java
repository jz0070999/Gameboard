package com.springsimple.Model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springsimple.DataObjects.Player;
import com.springsimple.Repository.PlayerRepository;

@Service
public class PlayerModel {

    private final PlayerRepository playerRepository;

    public PlayerModel(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    

    public List<Player> GetAllPlayers() {
        return this.playerRepository.findAll();
    }

    public Player GetPlayerById(int id) {
        return this.playerRepository.findById(id)
        .orElse(null);

    }

}
