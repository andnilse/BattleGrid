package com.example.andreas.battlegrid.Controller;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreas.battlegrid.Model.Game;
import com.example.andreas.battlegrid.Model.actions.Actions;
import com.example.andreas.battlegrid.Model.actions.BuildWall;
import com.example.andreas.battlegrid.Model.actions.PlayerMovment;
import com.example.andreas.battlegrid.Model.actions.weapons.Gun;
import com.example.andreas.battlegrid.Model.actions.MakeTrap;
import com.example.andreas.battlegrid.Model.actions.weapons.Pistol;
import com.example.andreas.battlegrid.Model.objects.Objects;
import com.example.andreas.battlegrid.Model.objects.Player;
import com.example.andreas.battlegrid.Model.objects.Trap;
import com.example.andreas.battlegrid.Model.objects.Wall;
import com.example.andreas.battlegrid.Model.objects.nothing;
import com.example.andreas.battlegrid.R;

import java.util.ArrayList;

public class ViewController extends AppCompatActivity {

    /*
    TODO
    fikse animationer


    fikse main manue

     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerchoise);

        game = (Game) getIntent().getSerializableExtra("Game");
        for ( int i =0; i<game.playerList.size();i++) {
            plActions.add(new ArrayList<Actions>());
            Player p = new Player(game.playerList.get(i).getName(), game.playerList.get(i).getIcon());
            p.setX(game.playerList.get(i).getX());
            p.setY(game.playerList.get(i).getY());
            players.add(p);
        }
        map = copymap(game.gameMap);
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
    ArrayList<Player> players = new ArrayList<>();
    int actionsPerTurn = 5;

    int curentPlayer = 0;
    int numberOfCurentInputs = 0;
    ArrayList<ArrayList<Actions>> plActions = new ArrayList<ArrayList<Actions>>();
    Actions curentAction = new Pistol();

    public void MapButtonClick(View v){
        if (!(moveActive || buildActive || (weaponActive && (gunactive || trapactive)))){
            return;
        }
        if (curentAction == null){ return; }

        int i = (int) v.getTag(R.string.tagIDx);
        int j = (int) v.getTag(R.string.tagIDy);

        if (curentAction.calculateAlowedTargets(i, j, map, players.get(curentPlayer))){
            Actions act = new PlayerMovment();



            if (curentAction instanceof PlayerMovment){

                map.get(players.get(curentPlayer).getX()).set(players.get(curentPlayer).getY(), new nothing());
                players.get(curentPlayer).setX(i);
                players.get(curentPlayer).setY(j);
                map.get(i).set(j, players.get(curentPlayer));

            } else if (curentAction instanceof BuildWall){
                act = new BuildWall();
                map.get(i).set(j, new Wall());
            } else if (curentAction instanceof MakeTrap){
                act = new MakeTrap();
                map.get(i).set(j, new Trap());
            } else if (curentAction instanceof Gun){
                act = new Gun();
            }
            act.setTarget(i,j);
            updateMap(map);



            plActions.get(curentPlayer).add(plActions.get(curentPlayer).size(), act);
            numberOfCurentInputs ++;
        } else {
            Toast.makeText(this, "Tile not allowed for this kind of target", Toast.LENGTH_SHORT).show();
            return;
        }

        if (numberOfCurentInputs >= actionsPerTurn){
            curentPlayer++;
            numberOfCurentInputs =0;
            TextView text = (TextView) findViewById(R.id.editText);
            text.setText("Input from player" + (curentPlayer +1));

            map = copymap(game.gameMap);
            updateMap(map);


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

            if (curentPlayer >= players.size()){

                text.setText("Battle!!!");

                ArrayList<ArrayList<ArrayList<Objects>>> maps = game.setActionList(plActions, this);
                for (int iii =0; iii<maps.size();iii++){

                    waitingmap showmaps = new waitingmap();
                    showmaps.execute(maps.get(iii)); // TODO
                }

                newRound();






            }

            move.setEnabled(true);
            build.setEnabled(true);
            shoot.setEnabled(true);
            moveActive = false;
            weaponActive = false;
            buildActive = false;
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
            curentAction = null;

            Button gun = (Button) findViewById(R.id.ibGun);
            gun.setEnabled(true);
            Button trap = (Button) findViewById(R.id.ibTrap);
            trap.setEnabled(true);
        } else {

            Button gun = (Button) findViewById(R.id.ibGun);
            gun.setEnabled(false);
            Button trap = (Button) findViewById(R.id.ibTrap);
            trap.setEnabled(false);
            gunactive = false;
            trapactive = false;

            move.setEnabled(true);
            build.setEnabled(true);

            weaponActive = false;
            curentAction = null;
        }

    }

    boolean gunactive = false;
    public void gun(View v){
        Button trap = (Button) findViewById(R.id.ibTrap);
        if (!gunactive){
            trap.setEnabled(false);
            gunactive = true;
            curentAction = new Gun();
        } else {
            trap.setEnabled(true);
            gunactive = false;
            curentAction = null;
        }

    }

    boolean trapactive = false;
    public void trap(View v){
        Button gun = (Button) findViewById(R.id.ibGun);
        if (!trapactive){
            gun.setEnabled(false);
            trapactive = true;
            curentAction = new MakeTrap();
        } else {
            gun.setEnabled(true);
            trapactive = false;
            curentAction = null;
        }
    }

    Player winner = null;
    public void updateMap (ArrayList<ArrayList<Objects>> newmap){
        for (int i =0; i<gridButtons.size();i++){
            gridButtons.get(i).setImageResource(newmap.get((int) gridButtons.get(i).getTag(R.string.tagIDx)).get((int) gridButtons.get(i).getTag(R.string.tagIDy)).getIcon());
        }
    }

    public void receavWinner(Player winner, int turn){
        this.winner = winner;
    }

    public ArrayList copymap(ArrayList<ArrayList<Objects>> org){
        ArrayList<ArrayList<Objects>> clone = new ArrayList<ArrayList<Objects>>(org.size());
        for (ArrayList a : org) {
            ArrayList<Objects> innerClone = new ArrayList<Objects>(a);
            clone.add(innerClone);
        }
        return clone;
    }

    public void newRound(){
        TextView text = (TextView) findViewById(R.id.editText);
        if (winner != null){
            waitingWinn showmaps = new waitingWinn();
            showmaps.execute("");
        } else {
            players = new ArrayList<Player>();
            plActions = new ArrayList<ArrayList<Actions>>();
            for ( int nyi =0; nyi<game.playerList.size();nyi++) {
                plActions.add(new ArrayList<Actions>());
                Player p = new Player(game.playerList.get(nyi).getName(), game.playerList.get(nyi).getIcon());
                p.setX(game.playerList.get(nyi).getX());
                p.setY(game.playerList.get(nyi).getY());
                players.add(p);
            }
            map = copymap(game.gameMap);
            curentPlayer = 0;
            updateMap(map);
            text.setText("Input from player" + (curentPlayer +1));
        }
    }

    public void finnish(){
        TextView text = (TextView) findViewById(R.id.editText);
        text.setText("The Winner Is " + winner.getName());

        finish();
    }






    private class waitingmap extends AsyncTask<ArrayList<ArrayList<Objects>>, ArrayList<ArrayList<Objects>>, ArrayList<ArrayList<Objects>>[]> {

        @Override
        protected ArrayList<ArrayList<Objects>>[] doInBackground(ArrayList<ArrayList<Objects>>... arrayLists) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return arrayLists;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<Objects>>... result) {
            updateMap(result[0]);
        }
    }

    private class waitingWinn extends AsyncTask<String, String, String> {



        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(4000);

            }
            catch (Exception e) {                }
            return "COMPLETE!";
        }

        @Override
        protected void onPostExecute(String result) {
            finnish();
            super.onPostExecute(result);
        }
    }
}
