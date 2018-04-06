package com.example.andreas.battlegrid.Model;

import com.example.andreas.battlegrid.Objects;

/**
 * Created by Andreas on 22.02.2018.
 */

public class Weapon extends Object{
    private Weapon weapon;
    private String name;
    private int health;

    public Weapon(){

    }

    public Weapon(String weaponType){
        if (weaponType.toLowerCase().equals("gun")){
            weapon = new Gun();
        }
    }

    public void doDamage(int damage, Objects objects){
        int remainingHealth = objects.getHealth() - damage;
        objects.setHealth(remainingHealth);
    }
}
