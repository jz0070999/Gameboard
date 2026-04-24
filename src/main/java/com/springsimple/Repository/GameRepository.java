package com.springsimple.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springsimple.DataObjects.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {

    List<Game> findAllGameByStatus(String status);
    List<Game> findAllGameByPlayer_Id(Integer playerId);

}
