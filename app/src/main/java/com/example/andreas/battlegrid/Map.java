package com.example.andreas.battlegrid;

import java.util.ArrayList;

public class Map {

    Map(){

        Objects obj = new Objects();

        for (int i = 0; i<10;i++){
            ArrayList<Objects> colmn = new ArrayList<Objects>();

            for (int j = 0; j<10; j++){
                colmn.add(obj);
            }
        }

    }

    ArrayList<ArrayList> row;

    public Objects getObj(int i, int j){
        return row.get(i).get(j);
    }
}
