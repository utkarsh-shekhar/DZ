package com.thebigfail.dz;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by code on 3/21/2015.
 */
public class Home {
    Rectangle bounds = new Rectangle();
    boolean active = false;
    public Home (float x, float y) {
        bounds.x = x*40;
        bounds.y = y*40;
        bounds.width = bounds.height = 1;
    }
    public void update(){

    }
}
