package com.thebigfail.dz;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by code on 3/21/2015.
 * Class representing the home in game
 */
public class Home extends Tile{
    boolean active = false;
    public Home (float x, float y) {
        super((int)x, (int)y, true);
    }
}
