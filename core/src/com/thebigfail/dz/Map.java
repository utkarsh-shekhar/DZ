package com.thebigfail.dz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;

/**
 * Created by code on 3/21/2015.
 */
public class Map {
    static int EMPTY = 0;

    static int HOME = 0xff00ff;
    static int ROCK = 0x9f9f9f;
    static int GRASS = 0x00ff00;
    static int FENCE = 0x00ffff;
    static int WATER = 0x0000ff;
    //int tiles[][];
    //Texture HouseImage;
    Dz dz;
    Array<Home> homes=new Array<Home>();
    Array<Rock> rocks=new Array<Rock>();
    Array<Grass> grasses=new Array<Grass>();
    Array<Fence> fences=new Array<Fence>();
    Array<Water> waters=new Array<Water>();


    int drawX,drawY;

    public Map(Dz dz ){
        this.dz=dz;
        loadBinary();
    }
    public void update(){
        for (int i = 0; i < homes.size; i++) {
            homes.get(i).update();
        }
    }
    private void loadBinary () {
        //HouseImage=new Texture(Gdx.files.internal("house.png"));
        Pixmap pixmap = new Pixmap(Gdx.files.internal("map1.png"));
        //tiles = new int[pixmap.getWidth()][pixmap.getHeight()];

        //System.out.println("hello g");
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 54; x++) {
                int pix = (pixmap.getPixel(x, y) >>> 8) & 0xffffff;
                System.out.println("x: "+x+" y: "+y);
                System.out.println(pix);
                if (match(pix, HOME)) {
                    Home home = new Home(x, pixmap.getHeight() - 1 - y);
                    homes.add(home);
                }
                else if (match(pix, ROCK)) {
                    Rock rock = new Rock(x, pixmap.getHeight() - 1 - y);
                    rocks.add(rock);
                } else if (match(pix, GRASS)) {
                    Grass grass = new Grass(x, pixmap.getHeight() - 1 - y);
                    grasses.add(grass);
                } else if (match(pix, FENCE)) {
                    fences.add(new Fence(x, pixmap.getHeight() - 1 - y));
                } else if (match(pix, WATER)) {
                    waters.add(new Water(x, pixmap.getHeight() - 1 - y));
                }

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
