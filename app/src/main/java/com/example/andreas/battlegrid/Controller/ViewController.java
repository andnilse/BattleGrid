package com.example.andreas.battlegrid.Controller;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreas.battlegrid.Model.Game;
import com.example.andreas.battlegrid.Model.actions.Actions;
import com.example.andreas.battlegrid.Model.actions.BuildWall;
import com.example.andreas.battlegrid.Model.actions.PlayerMovment;
import com.example.andreas.battlegrid.Model.actions.Weapon;
import com.example.andreas.battlegrid.Model.actions.weapons.Gun;
import com.example.andreas.battlegrid.Model.actions.MakeTrap;
import com.example.andreas.battlegrid.Model.actions.weapons.Pistol;
import com.example.andreas.battlegrid.Model.objects.Objects;
import com.example.andreas.battlegrid.Model.objects.Player;
import com.example.andreas.battlegrid.Model.objects.Trap;
import com.example.andreas.battlegrid.Model.objects.Wall;
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

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        LinearLayout mapLay = (LinearLayout) findViewById(R.id.maplayout);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i =0;i<10;i++){
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setLayoutParams(LLParams);

            for (int j =0; j<10; j++){
                ImageButton ib = new ImageButton(this);
                
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width/10, width/10);

                ib.setLayoutParams(lp);

                if (map.get(i).get(j) != null){
                    ib.setImageResource(map.get(i).get(j).getIcon()); // change to get image later
                } else {
                    ib.setColorFilter(Color.LTGRAY);
                }

                ib.setTag(R.string.tagIDx, i);
                ib.setTag(R.string.tagIDy, j);
                ib.setScaleType(ImageButton.ScaleType.CENTER_INSIDE);
                ib.setPadding(2,2,2,2);
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
        gun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gun(v);
            }
        });

        Button trap = (Button) findViewById(R.id.ibTrap);
        trap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trap(v);
            }
        });



        gun.setEnabled(false);
        trap.setEnabled(false);
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

            ArrayList<ArrayList<Objects>> newMap = map;
            if (curentAction instanceof PlayerMovment){
                newMap.get(i).set(j, players.get(curentPlayer));
                newMap.get(players.get(curentPlayer).getX()).set(players.get(curentPlayer).getY(), null);
            } else if (curentAction instanceof BuildWall){
                newMap.get(i).set(j, new Wall());
            } else if (curentAction instanceof MakeTrap){
                newMap.get(i).set(j, new Trap());
            }



            plActions.get(curentPlayer).add(curentAction);
            numberOfCurentInputs ++;
        } else {
            Toast.makeText(this, "Tile not allowed for this kind of target", Toast.LENGTH_SHORT).show();
            return;
        }

        if (numberOfCurentInputs >= actionsPerTurn){
            curentPlayer++;
            TextView text = (TextView) findViewById(R.id.editText);
            text.setText("Input from player" + (curentPlayer +1));
            updateMap(map);

            if (curentPlayer >= players.size()){
                game.setActionList(plActions, this);

                Button move = (Button) findViewById(R.id.ibMovment);
                move.setEnabled(false);
                Button build = (Button) findViewById(R.id.ibBuild);
                build.setEnabled(false);
                Button shoot = (Button) findViewById(R.id.ibWeapon);
                shoot.setEnabled(false);
                Button gun = (Button) findViewById(R.id.ibGun);
                gun.setEnabled(false);
                Button trap = (Button) findViewById(R.id.ibTrap);
                trap.setEnabled(false);

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

        Button gun = (Button) findViewById(R.id.ibGun);
        gun.setEnabled(false);
        Button trap = (Button) findViewById(R.id.ibTrap);
        trap.setEnabled(false);
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

        Button gun = (Button) findViewById(R.id.ibGun);
        gun.setEnabled(false);
        Button trap = (Button) findViewById(R.id.ibTrap);
        trap.setEnabled(false);

    }

    boolean weaponActive = false;
    Actions weapon = new Gun();
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

            Button gun = (Button) findViewById(R.id.ibGun);
            gun.setEnabled(false);
            Button trap = (Button) findViewById(R.id.ibTrap);
            trap.setEnabled(false);

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
        weapon = new MakeTrap();
        curentAction = weapon;
        Button gun = (Button) findViewById(R.id.ibGun);
        gun.setEnabled(true);
        Button trap = (Button) findViewById(R.id.ibTrap);
        trap.setEnabled(false);
    }

    public void updateMap (ArrayList<ArrayList<Objects>> newmap){
        for (int i =0; i<gridButtons.size();i++){
            if (newmap.get((int) gridButtons.get(i).getTag(R.string.tagIDx)).get((int) gridButtons.get(i).getTag(R.string.tagIDy)) == null){
                gridButtons.get(i).setBackgroundColor(Color.LTGRAY);
            } else {
                gridButtons.get(i).setImageResource(newmap.get((int) gridButtons.get(i).getTag(R.string.tagIDx)).get((int) gridButtons.get(i).getTag(R.string.tagIDy)).getIcon());
            }
        }
    }

    public void receaveMaps (ArrayList<ArrayList<ArrayList<Objects>>> newMap){

    }

    public void receavWinner(Player winner){

    }

}
