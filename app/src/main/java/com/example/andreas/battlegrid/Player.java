package com.example.andreas.battlegrid;

/**
 * Created by Andreas on 22.02.2018.
 */

public class Player {
    private String name;
    private int health;

    public Player(String name){
        this.name = name;
        setHealth(5);//ex 5 health
        //set imageview or some sort of player graphics
    }
    public String getName(){
        return this.name;
    }
    public int getHealth(){
        return this.health;
    }
    public void setHealth(int health){
        this.health = health;
    }
}
