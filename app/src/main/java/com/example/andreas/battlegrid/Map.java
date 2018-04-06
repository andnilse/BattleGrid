package com.example.andreas.battlegrid;

import com.example.andreas.battlegrid.Model.Action;
import com.example.andreas.battlegrid.Model.objects.Player;

import java.util.ArrayList;

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

    public ArrayList<ArrayList<Objects>> getMap(){
        return row;
    }

    public Objects getObj(int i, int j){
        return row.get(i).get(j);
    }

    public ArrayList<ArrayList> calculatePosibleTargets(Action a){
        return null;

    }
}
