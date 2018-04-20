package com.example.andreas.battlegrid;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.andreas.battlegrid.Controller.ViewController;
import com.example.andreas.battlegrid.Model.Game;
import com.example.andreas.battlegrid.Model.objects.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {
    ArrayList<Player> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Player player1 = new Player("Alice", Color.BLUE);
        Player player2 = new Player("Bob", Color.RED);
        list.add(player1);
        list.add(player2);

        Game game = new Game(list);
        Intent intent = new Intent(this, ViewController.class);
        intent.putExtra("Game", game);
        startActivity(intent);

        //123123
    }
}
