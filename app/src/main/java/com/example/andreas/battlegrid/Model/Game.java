package com.example.andreas.battlegrid.Model;

import com.example.andreas.battlegrid.Map;
import com.example.andreas.battlegrid.Model.actions.Actions;
import com.example.andreas.battlegrid.Model.actions.PlayerMovment;
import com.example.andreas.battlegrid.Model.actions.Weapon;
import com.example.andreas.battlegrid.Model.actions.weapons.Gun;
import com.example.andreas.battlegrid.Model.actions.weapons.Trap;
import com.example.andreas.battlegrid.Model.objects.Objects;
import com.example.andreas.battlegrid.Model.objects.Player;

import java.util.ArrayList;

/**
 * Created by Andreas on 22.02.2018.
 */

public class Game {
    private Map map;
    private boolean finished;
    private int numPlayers;
    private ArrayList<Player> playerList;
    private Player currentPlayer;
    private ArrayList<ArrayList<Objects>> gameMap;
    private ArrayList<ArrayList<Actions>> actionList;

    public Game(ArrayList<Player> playerList){
        if (playerList.size()>=2){
            init(playerList);
        }else{
            throw new IllegalArgumentException("Playerlist contains too few players: "+playerList.size());
        }
    }
    private void init(ArrayList<Player> playerList){
        finished = false;
        //setup players
        this.playerList = playerList;
        this.numPlayers = playerList.size();
        currentPlayer = playerList.get(0);
        //make the map
        getInitMap();
        //add additional items on the map/grid
    }
    private void run(){
        //turn-based multiplayer
        //each player gives 5 actions to be performed. The actions are done in a round-robin order.
        //Player1action1, player2 action1, player1 action2, player2 action2
        //This is done for every player in the list
        while (true && !finished){

            for (int index1 = 0;index1<10;index1++){
                //For every of the 10 actions
                for (int index2 = 0;index2<actionList.size();index2++){
                    //For every player
                    currentPlayer = playerList.get(index2);
                    Actions action = actionList.get(index2).get(index1);

                    int nextTargetX = action.getTargetX();
                    int nextTargetY = action.getTargetY();

                    if (action instanceof PlayerMovment){
                        if (!(gameMap.get(nextTargetX).get(nextTargetY) instanceof Objects)){
                            // If the target position does not contain an object (other player or wall)
                            gameMap.remove(currentPlayer);
                            gameMap.get(nextTargetX).add(nextTargetY, currentPlayer);
                            //IS this added correctly to map location? OR is it opposite?

                            //If statement for stepping on a trap
                            if (gameMap.get(nextTargetX).get(nextTargetY) instanceof Trap){
                                Trap trap = (Trap) gameMap.get(nextTargetX).get(nextTargetY);
                                currentPlayer.setHealth(currentPlayer.getHealth()-trap.damage);
                            }
                        }
                        //Else dont move and write to log??

                    }if (action instanceof Weapon){
                        if (action instanceof Gun){
                            if (gameMap.get(nextTargetX).get(nextTargetY) instanceof Objects){
                                Objects object = gameMap.get(nextTargetX).get(nextTargetY);
                                //Right now the gun takes 1 damage
                                object.setHealth(object.getHealth()-1);
                                if (object.getHealth()<=0){
                                    gameMap.remove(object);
                                }
                            }
                        }

                    }
                    updateMapView(gameMap);

                }
            }
        }
    }
    private void changePlayer(){
        int index = playerList.indexOf(currentPlayer);
        /*if (index == 0){
            currentPlayer = playerList.get(0);
        }else{
            currentPlayer = playerList.get(1);
        }*/
        //Ready for multiple players. More than 2 with below code

        if (index == playerList.size()-1){
            currentPlayer = playerList.get(0);
        }else{
            currentPlayer = playerList.get(index+1);
        }
    }

    private void updateMapView(ArrayList<ArrayList<Objects>> gameMap){
        //Send the updated map to the View
        map.setMap(gameMap);
    }

    private void getInitMap(){
        map = new Map();
        gameMap = map.getMap();

    }
}
