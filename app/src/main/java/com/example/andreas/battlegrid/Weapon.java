package com.example.andreas.battlegrid;

/**
 * Created by Andreas on 22.02.2018.
 */

public class Weapon {
    private String name;
    private int health;

    public void doDamage(int damage,Player player){
        int remainingHealth = player.getHealth() - damage;
        player.setHealth(remainingHealth);
    }
    public void doDamage(int damage, Objects objects){
        int remainingHealth = objects.getHealth() - damage;
        objects.setHealth(remainingHealth);
    }
}
