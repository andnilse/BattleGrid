package com.example.andreas.battlegrid.Model;

import com.example.andreas.battlegrid.Model.actions.Actions;
import com.example.andreas.battlegrid.Model.objects.Player;

import java.util.ArrayList;

/**
 * Created by Andreas on 06.04.2018.
 */

public class Action {
    private String objectClassName;
    private Player performedBy;
    private Actions action;
    private int xPos, yPos;

    public Action(Actions action,Player originPlayer, int xPos, int yPos){
        this.action = action;
        this.xPos = xPos;
        this.yPos = yPos;
        this.performedBy = originPlayer;
        this.objectClassName = action.getClass().getSimpleName(); //Class name

    }
}
