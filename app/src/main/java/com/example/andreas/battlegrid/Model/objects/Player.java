package com.example.andreas.battlegrid.Model.objects;

import android.graphics.drawable.Drawable;

import com.example.andreas.battlegrid.R;

/**
 * Created by Andreas on 22.02.2018.
 */

public class Player extends Objects {
    private int color;
    private int playerIcon;

    public Player(String name, int playerIcon){
        setHealth(10);//ex 10 health;
        this.setName(name);
        this.playerIcon = playerIcon;
    }

    @Override
    public int getIcon() {
        return playerIcon;
    }
}
