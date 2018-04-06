package com.example.andreas.battlegrid.Model;

import com.example.andreas.battlegrid.Map;
import com.example.andreas.battlegrid.Model.objects.Objects;
import com.example.andreas.battlegrid.Model.objects.Player;

import java.util.ArrayList;

/**
 * Created by Andreas on 22.02.2018.
 */

public class Game {
    private int numPlayers;
    private ArrayList<Player> playerList;
    private Player currentPlayer;
    private ArrayList<ArrayList<Objects>> outerList;
    //private ArrayList<Objects> innerList;

    public Game(ArrayList<Player> playerList){
        this.playerList = playerList;
        this.numPlayers = playerList.size();
        //List should always be  2 players
        currentPlayer = playerList.get(0);
    }
    private void init(){
        //setup players
        //make the map
        //add additional items on the map/grid


    }
    private void run(){
        //turn-based multiplayer
        //each player gives 5 actions to be performed. The actions are done in a round-robin order.
        //Player1action1, player2 action1, player1 action2, player2 action2
        //This is done for every player in the list
    }
    private void changePlayer(){
        int index = playerList.indexOf(currentPlayer);
        if (index == 0){
            currentPlayer = playerList.get(0);
        }else{
            currentPlayer = playerList.get(1);
        }
    }

    private void getMap(){
        Map map = new Map();
    }
}
