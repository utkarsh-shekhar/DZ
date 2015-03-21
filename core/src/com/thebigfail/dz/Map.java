package com.thebigfail.dz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by code on 3/21/2015.
 */
public class Map {
    static int EMPTY = 0;
    static int GROUND = 0x000000;
    static int HOUSE = 0xff00ff;
    static int ROCK = 0x9f9f9f;
    static int GRASS = 0x00ff00;
    static int FENCE = 0x00ffff;
    static int WATER = 0x0000ff;
    int tiles[][];
    Texture HouseImage;
    Dz dz;

    int drawX,drawY;

    public Map(Dz dz ){
        this.dz=dz;
        loadBinary();
    }
    public void update(){

        dz.batch.begin();
        dz.batch.draw(HouseImage,drawX*40,drawY*40);
        dz.batch.end();

    }
    private void loadBinary () {
        HouseImage=new Texture(Gdx.files.internal("house.png"));
        Pixmap pixmap = new Pixmap(Gdx.files.internal("map.png"));
        tiles = new int[pixmap.getWidth()][pixmap.getHeight()];

        //System.out.println("hello g");
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 54; x++) {
                int pix = (pixmap.getPixel(x, y) >>> 8) & 0xffffff;
                System.out.println("x: "+x+" y: "+y);
                System.out.println(pix+" "+HOUSE );
                if (match(pix, HOUSE)) {


                    drawX=x;
                    drawY=y;


                }/* else if (match(pix, DISPENSER)) {
                    Dispenser dispenser = new Dispenser(x, pixmap.getHeight() - 1 - y);
                    dispensers.add(dispenser);
                } else if (match(pix, ROCKET)) {
                    Rocket rocket = new Rocket(this, x, pixmap.getHeight() - 1 - y);
                    rockets.add(rocket);
                } else if (match(pix, MOVING_SPIKES)) {
                    movingSpikes.add(new MovingSpikes(this, x, pixmap.getHeight() - 1 - y));
                } else if (match(pix, LASER)) {
                    lasers.add(new Laser(this, x, pixmap.getHeight() - 1 - y));
                } else if (match(pix, END)) {
                    endDoor = new EndDoor(x, pixmap.getHeight() - 1 - y);
                } else {
                    tiles[x][y] = pix;
                }
                */
            }
        }
        /*
        for (int i = 0; i < movingSpikes.size; i++) {
            movingSpikes.get(i).init();
        }
        for (int i = 0; i < lasers.size; i++) {
            lasers.get(i).init();
        }
        */
    }
    boolean match (int src, int dst) {
        return src == dst;
    }
}
