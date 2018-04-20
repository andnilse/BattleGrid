package com.example.andreas.battlegrid.Model.actions;

import com.example.andreas.battlegrid.Model.objects.Objects;
import com.example.andreas.battlegrid.Model.objects.Player;

import java.util.ArrayList;

public class Actions {
    private int targetX,targetY;

    public void setTarget(int x, int y){
        targetY = y;
        targetX = x;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public boolean calculateAlowedTargets(int nextX, int nextY, ArrayList<ArrayList<Objects>> gameMap, Player player){
        return false;
    }
}
