package com.example.andreas.battlegrid.Model.actions;

import com.example.andreas.battlegrid.Model.objects.Objects;

import java.util.ArrayList;

public class BuildWall extends Actions{
    public boolean CalculateAllowedTargets(int x, int y, ArrayList<ArrayList<Objects>> gameMap){
        if (gameMap.get(x).get(y) == null){
            return true;
        }
        return false;
    }
}
