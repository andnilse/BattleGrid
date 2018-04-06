package com.example.andreas.battlegrid.Model;

import java.util.ArrayList;

/**
 * Created by Andreas on 06.04.2018.
 */

public class Action {
    private String name;
    private Player performedBy;
    private Objects object;
    private int xPos, yPos;


    public Action(Objects object,Player originPlayer, int xPos, int yPos){
        this.object = object;
        this.xPos = xPos;
        this.yPos = yPos;
        this.performedBy = originPlayer;
        if (object instanceof Player){
            this.name = "Moving";
        }
        if (object instanceof Weapon){
            this.name = "Shooting";
        }
        if (object instanceof Wall){
            this.name = "Building";
        }
    }
}
