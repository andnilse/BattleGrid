package com.example.andreas.battlegrid.Model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.andreas.battlegrid.Controller.ViewController;
import com.example.andreas.battlegrid.Map;
import com.example.andreas.battlegrid.Model.actions.Actions;
import com.example.andreas.battlegrid.Model.actions.BuildWall;
import com.example.andreas.battlegrid.Model.actions.PlayerMovment;
import com.example.andreas.battlegrid.Model.actions.Weapon;
import com.example.andreas.battlegrid.Model.actions.weapons.Gun;
import com.example.andreas.battlegrid.Model.actions.weapons.Trap;
import com.example.andreas.battlegrid.Model.objects.Objects;
import com.example.andreas.battlegrid.Model.objects.Player;
import com.example.andreas.battlegrid.Model.objects.Wall;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Andreas on 22.02.2018.
 */

public class Game implements Serializable{
    private Map map;
    private boolean finished;
    private int numPlayers;
    public ArrayList<Player> playerList;
    private Player currentPlayer;
    public ArrayList<ArrayList<Objects>> gameMap;
    private ArrayList<ArrayList<Actions>> actionList = new ArrayList<ArrayList<Actions>>();
    private Player winner;

    private ViewController vc;
    private ArrayList<ArrayList<ArrayList<Objects>>> gameMaps;


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
        //vc = new ViewController();
        //add additional items on the map/grid

    }

    public void setActionList(ArrayList<ArrayList<Actions>> actionList){
        this.actionList = actionList;
        run();
    }
    public void run(){
        //turn-based multiplayer
        //each player gives 5 actions to be performed. The actions are done in a round-robin order.
        //Player1action1, player2 action1, player1 action2, player2 action2
        //This is done for every player in the list
        while (!finished){

            for (int index1 = 0;index1<5;index1++){
                //For every of the 10 actions
                for (int index2 = 0;index2<actionList.size();index2++){
                    //For every player
                    currentPlayer = playerList.get(index2);
                    Actions action = actionList.get(index2).get(index1);

                    int nextTargetX = action.getTargetX();
                    int nextTargetY = action.getTargetY();

                    if (action instanceof PlayerMovment){
                        if (((PlayerMovment) action).CalculateAllowedTargets(nextTargetX, nextTargetY, gameMap, currentPlayer)){
                            // If the target position does not contain an object (other player or wall)
                            gameMap.remove(currentPlayer);
                            gameMap.get(nextTargetX).add(nextTargetY, currentPlayer);
                            currentPlayer.setX(nextTargetX);
                            currentPlayer.setY(nextTargetY);
                            //IS this added correctly to map location? OR is it opposite?
                        }if (gameMap.get(nextTargetX).get(nextTargetY) instanceof Trap){
                            //If statement for stepping on a trap
                            Trap trap = (Trap) gameMap.get(nextTargetX).get(nextTargetY);
                            currentPlayer.setHealth(currentPlayer.getHealth()-trap.damage);
                            //Now remove the trap and move player
                            gameMap.remove(trap);
                            gameMap.remove(currentPlayer);
                            gameMap.get(nextTargetX).set(nextTargetY, currentPlayer);
                            currentPlayer.setX(nextTargetX);
                            currentPlayer.setY(nextTargetY);
                        }
                        //Else dont move and write to log??
                    }if (action instanceof Weapon){
                        if (action instanceof Gun){
                            if (((Gun) action).CalculateAllowedTargets(nextTargetX, nextTargetY, gameMap, currentPlayer)){
                                Objects object = gameMap.get(nextTargetX).get(nextTargetY);
                                //Right now the gun takes 1 damage
                                object.setHealth(object.getHealth()-1);
                                if (object.getHealth()<=0){
                                    gameMap.remove(object);
                                }
                            }
                        }

                    }if (action instanceof BuildWall){
                        if (((BuildWall) action).CalculateAllowedTargets(nextTargetX,nextTargetY, gameMap, currentPlayer)){
                            Wall wall = new Wall();
                            gameMap.get(nextTargetX).set(nextTargetY, wall);
                            wall.setX(nextTargetX);
                            wall.setY(nextTargetY);
                        }
                    }
                    updateMapView(gameMap);
                    gameMaps.add(gameMap);

                    if (currentPlayer.getHealth()<=0){
                        if (currentPlayer == playerList.get(0)){
                            winner = playerList.get(1);
                        }else{
                            winner = playerList.get(1);
                        }
                        vc.receavWinner(winner);
                        finished = true;
                    }

                }
            }
            actionList = null;
        }
    }
    private void changePlayer(){
        int index = playerList.indexOf(currentPlayer);
        if (index == 0){
            currentPlayer = playerList.get(0);
        }else{
            currentPlayer = playerList.get(1);
        }
        //Ready for multiple players. More than 2 with below code

        /*if (index == playerList.size()-1){
            currentPlayer = playerList.get(0);
        }else{
            currentPlayer = playerList.get(index+1);
        }*/
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
