package com.example.andreas.battlegrid.Model.objects;

import com.example.andreas.battlegrid.R;

/**
 * Created by Andreas on 06.04.2018.
 */

public class Wall extends Objects {
    private int wallIcon;

    public Wall(){
        setHealth(1);
        wallIcon = R.mipmap.wall;
    }
    public int getIcon(){
        return wallIcon;
    }
}
