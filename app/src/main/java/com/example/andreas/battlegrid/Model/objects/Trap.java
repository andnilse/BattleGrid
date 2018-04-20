package com.example.andreas.battlegrid.Model.objects;

import com.example.andreas.battlegrid.Model.actions.Weapon;
import com.example.andreas.battlegrid.Model.objects.Objects;
import com.example.andreas.battlegrid.Model.objects.Player;
import com.example.andreas.battlegrid.R;

import java.util.ArrayList;

public class Trap extends Objects {
    private Weapon trap;
    public int damage = 1;
    private int trapIcon;

    @Override
    public int getIcon(){
        return trapIcon;
    }

    public Trap(){
        trapIcon = R.mipmap.trap;
    }
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
