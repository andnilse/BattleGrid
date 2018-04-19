package com.example.andreas.battlegrid.Model.objects;

/**
 * Created by Andreas on 22.02.2018.
 */

public class Player extends Objects {
    private String color;

    public Player(String name, String color){
        setHealth(10);//ex 10 health;
        this.setName(name);
        this.color=color;
    }
    public int getColor(){
        return this.color;
    }
}
