package com.example.andreas.battlegrid.Model.actions;

import com.example.andreas.battlegrid.Objects;

/**
 * Created by Andreas on 22.02.2018.
 */

public class Weapon extends Actions {
    private Weapon weapon;
    private String name;


    public Weapon(){

    }

    public Weapon(String weaponType){
        if (weaponType.toLowerCase().equals("gun")){
            weapon = new Gun();
        }
    }

    public void doDamage(int damage, Objects object){
        int remainingHealth = object.getHealth() - damage;
        object.setHealth(remainingHealth);
    }
}
