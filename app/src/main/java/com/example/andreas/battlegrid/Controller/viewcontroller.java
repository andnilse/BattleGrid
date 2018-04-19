package com.example.andreas.battlegrid.Controller;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.andreas.battlegrid.Map;
import com.example.andreas.battlegrid.Model.Game;
import com.example.andreas.battlegrid.Model.actions.Actions;
import com.example.andreas.battlegrid.Model.actions.Weapon;
import com.example.andreas.battlegrid.Model.actions.weapons.Pistol;
import com.example.andreas.battlegrid.Model.objects.Player;
import com.example.andreas.battlegrid.R;

import java.util.ArrayList;

public class viewcontroller extends AppCompatActivity {

    viewcontroller(Map map, ArrayList<Player> p, int nrActions, Game g){
        map = map;
        players = p;
        actionsPerTurn = nrActions;
        game = g;
    }

    /*
    TODO
    oppdatere kart når spilere legger til movments
    resette kart når neste spiller legger til actions
    legge til liste med våpen
    legge til for build og move

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerchoise);

        LinearLayout mapLay = (LinearLayout) findViewById(R.id.maplayout);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        for (int i =0;i<10;i++){
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setLayoutParams(LLParams);

            for (int j =0; j<10; j++){
                ImageButton ib = new ImageButton(this);
                if (map.getObj(i,j) != null){
                    ib.setBackgroundColor(map.getObj(i,j).getColor()); // change to get image later
                } else {
                    ib.setBackgroundColor(Color.LTGRAY);
                }
                ib.setClickable(false);
                ib.setEnabled(false);
                ib.setTag(0, i);
                ib.setTag(1, j);
                ib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MapButtonClick(v);
                    }
                });
                gridButtons.add(ib);
                row.addView(ib);
            }

            mapLay.addView(row);
        }

        ImageButton move = (ImageButton) findViewById(R.id.ibMovment);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(v);
            }
        });

        ImageButton build = (ImageButton) findViewById(R.id.ibBuild);
        build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                build(v);
            }
        });

        final ImageButton shoot = (ImageButton) findViewById(R.id.ibWeapon);
        shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoot(v);
            }
        });


    }

    ArrayList<ImageButton> gridButtons = new ArrayList<>();

    Game game;
    Map map;
    ArrayList<Player> players;
    int actionsPerTurn = 5;

    int curentPlayer = 0;
    int numberOfCurentInputs = 0;
    ArrayList<ArrayList<Actions>> plActions = new ArrayList<ArrayList<Actions>>();
    Actions curentAction = new Pistol();


    public void MapButtonClick(View v){
        if (!(moveActive || buildActive || weaponActive)){
            return;
        }

        int i = (int) v.getTag(0);
        int j = (int) v.getTag(1);

        if (curentAction.calculateAlowedTargets(players.get(curentPlayer),i,j)){
            curentAction.setTarget(i,j);
            plActions.get(curentPlayer).add(curentAction);
            numberOfCurentInputs ++;
        } else {
            Toast.makeText(this, "Tile not allowed for this kind of target", Toast.LENGTH_SHORT).show();
            return;
        }

        if (numberOfCurentInputs >= actionsPerTurn){
            curentPlayer++;
            EditText text = (EditText) findViewById(R.id.editText);
            text.setText("Input from player" + (curentPlayer +1));
            if (curentPlayer >= players.size()){
                game.setActionList(plActions);
            }
        }


    }

    boolean moveActive = false;
    public void move(View v){


    }

    boolean buildActive = false;
    public void build(View v){
        // endre logic og view til drop list med flere alternativer etter hvert

    }

    boolean weaponActive = false;
    Weapon weapon = new Pistol();
    public void shoot(View v){
        if (!weaponActive){
            ImageButton move = (ImageButton) findViewById(R.id.ibMovment);
            move.setEnabled(false);
            ImageButton build = (ImageButton) findViewById(R.id.ibBuild);
            build.setEnabled(false);


        }

    }

    public void receaveMap (Map newMap){

    }

    public void receavWinner(Player winner){

    }

}
