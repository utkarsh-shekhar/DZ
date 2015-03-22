package com.thebigfail.dz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.Texture;
/**
 * Created by code on 3/22/2015.
 */
public class MapRenderer {
    Map map;
    Dz dz;
    OrthographicCamera cam;
    SpriteCache cache;
    SpriteBatch batch;
    Texture home;
    public MapRenderer (Map map,Dz dz) {
        this.map = map;
        this.dz=dz;
        this.cam = new OrthographicCamera(54, 32);
        this.batch=new SpriteBatch( );
        home=new Texture(Gdx.files.internal("house.png"));
        //this.cam.position.set(map.bob.pos.x, map.bob.pos.y, 0);
        //this.cache = new SpriteCache(this.map.tiles.length * this.map.tiles[0].length, false);
        //this.blocks = new int[(int)Math.ceil(this.map.tiles.length / 24.0f)][(int)Math.ceil(this.map.tiles[0].length / 16.0f)];

        //createAnimations();
        //createBlocks();
    }
    public void render(){
        batch.setProjectionMatrix(dz.camera.combined);
        batch.begin();
        renderHomes();
        batch.end();


    }
    private void renderHomes () {
        for (int i = 0; i < map.homes.size; i++) {
            Home home = map.homes.get(i);

            batch.draw(this.home, home.bounds.x,home.bounds.y);


        }
    }
}
