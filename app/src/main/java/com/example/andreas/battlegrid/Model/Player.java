package com.example.andreas.battlegrid.Model;

import android.media.Image;

/**
 * Created by Andreas on 22.02.2018.
 */

public class Player extends Objects{
    private String name;
    private int health;
    private Image avatar;//Velges og settes somwhere

    public Player(String name){
        setHealth(10);//ex 10 health;
    }
}
