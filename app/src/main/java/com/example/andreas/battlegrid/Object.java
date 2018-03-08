package com.example.andreas.battlegrid;

/**
 * Created by Andreas on 22.02.2018.
 */

public class Object {
    private String name;
    private int health;

    public Object(){
        this.name = name;
        setHealth(5);//ex 5 health
        //set imageview or some sort of object graphics
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
