package com.example.andreas.battlegrid.Model.objects;

import android.graphics.drawable.Drawable;

/**
 * Created by Andreas on 22.02.2018.
 */

public class Player extends Objects {
    private int color;
    public int playerIcon;

    public Player(String name, int playerIcon){
        setHealth(10);//ex 10 health;
        this.setName(name);
        this.playerIcon = playerIcon;
    }
}
