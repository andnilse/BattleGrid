package com.example.andreas.battlegrid;

import com.example.andreas.battlegrid.Model.Action;
import com.example.andreas.battlegrid.Model.actions.PlayerMovment;
import com.example.andreas.battlegrid.Model.actions.Weapon;
import com.example.andreas.battlegrid.Model.objects.Player;

import java.util.ArrayList;

public class Map {

    public Map(){

        Objects obj = new Objects();

        for (int i = 0; i<10;i++){
            ArrayList<Objects> colmn = new ArrayList<Objects>();

            for (int j = 0; j<10; j++){
                colmn.add(obj);
            }

            row.add(colmn);
        }

    }

    ArrayList<ArrayList<Objects>> row;

    public Objects getObj(int i, int j){
        return row.get(i).get(j);
    }

    public ArrayList<ArrayList> calculatePosibleTargets(Action a){

        if (a.action instanceof Weapon){ // behover ikke ta hensyn til objekter på kartet



        } else { // må ta ensyn til obj på kartet, kan ikke bygge vegg oppå vegg, kan ikke gå på vegg

        }

        return null;
    }
}
