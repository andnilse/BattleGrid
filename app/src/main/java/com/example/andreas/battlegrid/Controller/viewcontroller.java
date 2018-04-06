package com.example.andreas.battlegrid.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.andreas.battlegrid.Map;
import com.example.andreas.battlegrid.Model.actions.weapons.Pistol;
import com.example.andreas.battlegrid.Model.objects.Player;
import com.example.andreas.battlegrid.R;

public class viewcontroller extends AppCompatActivity {

    viewcontroller(Map map){
        map = map;
    }

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
                ib.setBackgroundColor(map.getObj(i,j).getcolor()); // change to get image later
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

    Map map;

    public void MapButtonClick(View v){
        int i = (int) v.getTag(0);
        int j = (int) v.getTag(1);
        // do somtehing with info of button clicked.
    }

    public void move(View v){
        Pistol p = new Pistol();
        Player player = new Player("test");

        

        Map map = new Map();
        map.calculatePosibleTargets();
    }

    public void build(View v){
        // endre logic og view til drop list med flere alternativer etter hvert

    }

    public void shoot(View v){
        // endre logic og view til drop list med flere alternativer etter hvert

    }

}
