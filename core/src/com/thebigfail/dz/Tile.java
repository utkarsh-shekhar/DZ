package com.thebigfail.dz;


import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Utkarsh on 3/26/2015.
 * Base class for all the tiles.
 * All the tiles must extend this class.
 */
public class Tile {
    private static float xScale = 150, yScale = 150; // 1px on the PixelMap = these many pixels on screen
    Rectangle bounds;

    Tile(int x, int y) {
        bounds = new Rectangle();
        bounds.x = (int)(x * xScale);
        bounds.y = (int)(y * yScale);
    }

    public static float getXScale() {
        return xScale;
    }

    public static float getYScale() {
        return yScale;
    }
}
