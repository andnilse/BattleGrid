package com.example.andreas.battlegrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.andreas.battlegrid.Model.Game;
import com.example.andreas.battlegrid.Model.objects.Player;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Player> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");
        list.add(player1);
        list.add(player2);

        Game game = new Game(list);

        //123123
    }
}
