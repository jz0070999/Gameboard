package com.springsimple.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springsimple.DataObjects.Player;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

}
