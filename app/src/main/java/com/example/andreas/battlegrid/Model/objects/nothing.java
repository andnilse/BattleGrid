package com.example.andreas.battlegrid.Model.objects;

import com.example.andreas.battlegrid.R;

public class nothing extends Objects {
    private int nothingIcon;

    public nothing(){
        setHealth(0);
        nothingIcon = R.mipmap.nothinggray;
    }
    public int getIcon(){
        return nothingIcon;
    }
}
