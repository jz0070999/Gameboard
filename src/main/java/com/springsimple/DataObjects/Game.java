package com.springsimple.DataObjects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    private int locationColumn;
    private int locationRow;
    private String status;

    public Game(){}
    
    public Game(Player player, int locationColumn, int locationRow, String status) {
        this.player = player;
        this.locationColumn = locationColumn;
        this.locationRow = locationRow;
        this.status = status;
    }


    public int getId(){
        return this.id;
    }
    public int getPlayerId() {
        return this.player.getId();
    }

    public Player getPlayer() {
        return this.player;
    }
    public int getLocationColumn() {
        return this.locationColumn;
    }
    public int getLocationRow() {
        return this.locationRow;
    }
    public String getStatus(){
        return this.status;
    }



    public void setLocationColumn(int locationColumn){
        this.locationColumn = locationColumn;
    }
    public void setLocationRow(int locationRow){
        this.locationRow = locationRow;
    }
    public void setStatus (String status) {
        this.status = status;
    }

}
