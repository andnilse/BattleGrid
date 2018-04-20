package com.example.andreas.battlegrid.Controller;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.andreas.battlegrid.Model.Game;
import com.example.andreas.battlegrid.Model.actions.Actions;
import com.example.andreas.battlegrid.Model.actions.BuildWall;
import com.example.andreas.battlegrid.Model.actions.PlayerMovment;
import com.example.andreas.battlegrid.Model.actions.Weapon;
import com.example.andreas.battlegrid.Model.actions.weapons.Gun;
import com.example.andreas.battlegrid.Model.actions.weapons.Pistol;
import com.example.andreas.battlegrid.Model.objects.Objects;
import com.example.andreas.battlegrid.Model.objects.Player;
import com.example.andreas.battlegrid.R;

import java.util.ArrayList;

public class ViewController extends AppCompatActivity {

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

        game = (Game) getIntent().getSerializableExtra("Game");
        players = game.playerList;
        map = game.gameMap;
        actionsPerTurn = 5;

        LinearLayout mapLay = (LinearLayout) findViewById(R.id.maplayout);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);


        for (int i =0;i<10;i++){
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setLayoutParams(LLParams);

            for (int j =0; j<10; j++){
                ImageButton ib = new ImageButton(this);

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(130, 130);
                ib.setLayoutParams(lp);

                if (map.get(i).get(j) != null){
                    ib.setBackgroundColor(map.get(i).get(j).getColor()); // change to get image later
                } else {
                    ib.setBackgroundColor(Color.LTGRAY);
                }
                ib.setClickable(false);
                ib.setEnabled(false);
                ib.setTag(R.string.tagIDx, i);
                ib.setTag(R.string.tagIDy, j);
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

        Button move = (Button) findViewById(R.id.ibMovment);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(v);
            }
        });

        Button build = (Button) findViewById(R.id.ibBuild);
        build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                build(v);
            }
        });

        Button shoot = (Button) findViewById(R.id.ibWeapon);
        shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoot(v);
            }
        });

        Button gun = (Button) findViewById(R.id.ibGun);
        shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gun(v);
            }
        });

        Button trap = (Button) findViewById(R.id.ibTrap);
        shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trap(v);
            }
        });

    }

    ArrayList<ImageButton> gridButtons = new ArrayList<>();

    Game game;
    ArrayList<ArrayList<Objects>> map;
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

        int i = (int) v.getTag(R.string.tagIDx);
        int j = (int) v.getTag(R.string.tagIDy);

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
        Button build = (Button) findViewById(R.id.ibBuild);
        Button shoot = (Button) findViewById(R.id.ibWeapon);
        if (!moveActive){

            build.setEnabled(false);
            shoot.setEnabled(false);

            moveActive = true;
            curentAction = new PlayerMovment();
        } else {

            build.setEnabled(true);
            shoot.setEnabled(true);

            moveActive = false;
            curentAction = null;
        }

    }

    boolean buildActive = false;
    public void build(View v){
        Button move = (Button) findViewById(R.id.ibMovment);
        Button shoot = (Button) findViewById(R.id.ibWeapon);
        if (!buildActive){

            move.setEnabled(false);
            shoot.setEnabled(false);

            buildActive = true;
            curentAction = new BuildWall();
        } else {

            move.setEnabled(true);
            shoot.setEnabled(true);

            buildActive = false;
            curentAction = null;
        }

    }

    boolean weaponActive = false;
    Weapon weapon = new Gun();
    public void shoot(View v){

        Button move = (Button) findViewById(R.id.ibMovment);
        Button build = (Button) findViewById(R.id.ibBuild);
        if (!weaponActive){

            move.setEnabled(false);
            build.setEnabled(false);

            weaponActive = true;
            curentAction = weapon;

            Button gun = (Button) findViewById(R.id.ibGun);
            gun.setEnabled(false);
            Button trap = (Button) findViewById(R.id.ibTrap);
            trap.setEnabled(true);
        } else {

            move.setEnabled(true);
            build.setEnabled(true);

            weaponActive = false;
            curentAction = null;
        }

    }

    public void gun(View v){
        weapon = new Gun();
        curentAction = weapon;
        Button gun = (Button) findViewById(R.id.ibGun);
        gun.setEnabled(false);
        Button trap = (Button) findViewById(R.id.ibTrap);
        trap.setEnabled(true);
    }

    public void trap(View v){
        weapon = new Trap
        curentAction = weapon;
        Button gun = (Button) findViewById(R.id.ibGun);
        gun.setEnabled(true);
        Button trap = (Button) findViewById(R.id.ibTrap);
        trap.setEnabled(false);
    }

    public void receaveMaps (ArrayList<ArrayList<ArrayList<Objects>>> newMap){

    }

    public void receavWinner(Player winner){

    }

}
