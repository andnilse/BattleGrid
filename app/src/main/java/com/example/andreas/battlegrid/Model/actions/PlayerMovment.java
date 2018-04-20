package com.example.andreas.battlegrid.Model.actions;

import com.example.andreas.battlegrid.Model.actions.Actions;
import com.example.andreas.battlegrid.Model.objects.Objects;
import com.example.andreas.battlegrid.Model.objects.Player;
import com.example.andreas.battlegrid.Model.objects.nothing;

import java.util.ArrayList;

public class PlayerMovment extends Actions {


    @Override
    public boolean calculateAlowedTargets(int nextX, int nextY, ArrayList<ArrayList<Objects>> gameMap, Player player){
        int x = player.getX();
        int y = player.getY();
        if (gameMap.get(nextX).get(nextY) instanceof nothing){
            if (!(x==nextX && y==nextY)){
                if (((nextX-1==x || (nextX+1 ==x)) && (nextY-1 == y || nextY+1 ==y)) || (x == nextX && (nextY-1 == y || nextY+1 ==y) || (y == nextY && (nextX-1==x || (nextX+1 ==x))))){
                    return true;
                }
            }
        }
        return false;

    }
}
