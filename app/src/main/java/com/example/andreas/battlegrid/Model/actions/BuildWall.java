package com.example.andreas.battlegrid.Model.actions;

import com.example.andreas.battlegrid.Model.objects.Objects;
import com.example.andreas.battlegrid.Model.objects.Player;

import java.util.ArrayList;

public class BuildWall extends Actions{
    public boolean CalculateAllowedTargets(int nextX, int nextY, ArrayList<ArrayList<Objects>> gameMap, Player player){
        int x = player.getX();
        int y = player.getY();
        if (gameMap.get(x).get(y) == null){
            if (x!=nextX && y!=nextY){
                if ((nextX-1==x || (nextX+1 ==x)) && (nextY == y-1 || nextY ==y || nextY ==y+1)){
                    if ((nextX == x) && (nextY == y+1 || nextY == y-1)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
