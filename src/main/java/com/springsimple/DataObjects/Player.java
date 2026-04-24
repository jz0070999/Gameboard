package com.springsimple.DataObjects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    // public Player(int id, String name) {
    //     this.id = id;
    //     this.name = name;
    // }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    } 



}
