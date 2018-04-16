package com.example.andreas.battlegrid.Model.objects;

/**
 * Created by Andreas on 22.02.2018.
 */

public class Objects{
    //private position;
    private String name;
    private int health;

    public Objects(){

    }

    public Objects(String name, int health){
        this.name = name;
        setHealth(health);//ex 5 health
        //set imageview or some sort of object graphics
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getHealth(){
        return this.health;
    }
    public void setHealth(int health){
        this.health = health;
    }
}
