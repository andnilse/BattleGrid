package com.example.andreas.battlegrid.Model.actions;

import com.example.andreas.battlegrid.Model.objects.Objects;
import com.example.andreas.battlegrid.Model.objects.Player;
import com.example.andreas.battlegrid.Model.objects.Trap;
import com.example.andreas.battlegrid.Model.objects.nothing;

import java.util.ArrayList;

public class MakeTrap extends Weapon {

    @Override
    public boolean calculateAlowedTargets(int nextX, int nextY, ArrayList<ArrayList<Objects>> gameMap, Player player){
        int x = player.getX();
        int y = player.getY();
        if (gameMap.get(nextX).get(nextY) instanceof nothing || gameMap.get(nextX).get(nextY) instanceof Trap){
            if (!(x==nextX && y==nextY)){
                if (((nextX-1==x || (nextX+1 ==x)) && (nextY-1 == y || nextY+1 ==y)) || (x == nextX && (nextY-1 == y || nextY+1 ==y) || (y == nextY && (nextX-1==x || (nextX+1 ==x))))){
                    return true;
                }
            }
        }
        return false;
    }
}
