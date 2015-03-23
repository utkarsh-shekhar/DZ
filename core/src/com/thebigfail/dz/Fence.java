package com.thebigfail.dz;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by code on 3/21/2015.
 */
public class Fence {
    Rectangle bounds=new Rectangle();
    public Fence(int x,int y){
        bounds.x=x*40;
        bounds.y=y*40;
    }
}
