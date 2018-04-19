package com.example.andreas.battlegrid.Model.actions.weapons;

import com.example.andreas.battlegrid.Model.actions.Weapon;
import com.example.andreas.battlegrid.Model.objects.Player;

import java.util.ArrayList;

public class Pistol extends Weapon {


    public boolean calculateAlowedTargets(Player p, int i, int j){
        return false;
    }

}
