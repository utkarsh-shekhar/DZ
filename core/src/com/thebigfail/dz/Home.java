package com.thebigfail.dz;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by code on 3/21/2015.
 * Class representing the home in game
 */
public class Home {
    Rectangle bounds = new Rectangle();
    public Home (float x, float y) {
        bounds.x = x*40;
        bounds.y = y*40;
        bounds.width = bounds.height = 1;
    }
}
