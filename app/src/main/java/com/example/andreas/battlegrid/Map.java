package com.example.andreas.battlegrid;

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
        return null;

    }

    /**
     * Created by Andreas on 06.04.2018.
     */

    public static class Action {
        private String objectClassName;
        private Player performedBy;
        private com.example.andreas.battlegrid.Model.objects.Objects object;
        private int xPos, yPos;

        public Action(com.example.andreas.battlegrid.Model.objects.Objects object, Player originPlayer, int xPos, int yPos){
            this.object = object;
            this.xPos = xPos;
            this.yPos = yPos;
            this.performedBy = originPlayer;
            this.objectClassName = object.getClass().getSimpleName(); //Class name
            /*if (object instanceof Player){
                this.name = "Moving";
            }
            if (object instanceof Weapon){
                this.name = "Shooting";
            }
            if (object instanceof Wall){
                this.name = "Building";
            }*/
        }
    }
}
