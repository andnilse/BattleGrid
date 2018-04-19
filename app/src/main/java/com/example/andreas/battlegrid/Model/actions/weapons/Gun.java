package com.example.andreas.battlegrid.Model.actions.weapons;

import com.example.andreas.battlegrid.Model.actions.Weapon;
import com.example.andreas.battlegrid.Model.objects.Objects;
import com.example.andreas.battlegrid.Model.objects.Player;

import java.util.ArrayList;

/**
 * Created by Andreas on 06.04.2018.
 */

public class Gun extends Weapon {
    private Weapon gun;
    public int damage = 1;

    public Gun(){
        gun = this;
    }
    public boolean CalculateAllowedTargets(int x, int y, ArrayList<ArrayList<Objects>> gameMap, Player player){
        if (gameMap.get(x).get(y) instanceof Objects && gameMap.get(x).get(y)!=player){
            return true;
        }
        return false;
    }

}
