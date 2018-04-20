package com.example.andreas.battlegrid.Model.objects;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by Andreas on 22.02.2018.
 */

public class Objects implements Serializable {
    private int x,y;
    private String name;
    private int health;

    public Objects(){

    }

    public Objects(String name, int health){
        this.name = name;
        setHealth(health);//ex 5 health
        //set imageview or some sort of object graphics
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
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
    public int getColor(){return Color.LTGRAY;}
    public int getIcon(){
        return 0;
    }
}
