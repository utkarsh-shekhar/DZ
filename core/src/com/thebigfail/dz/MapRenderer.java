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
    Texture homeImg;
    Texture rockImg;
    Texture grassImg;
    Texture fenceImg;
    Texture waterImg;
    private int width, height;

    public MapRenderer (Map map,Dz dz) {
        this.map = map;
        this.dz=dz;
        this.cam = new OrthographicCamera(map.pixmap.getWidth(), map.pixmap.getHeight());
        this.batch=new SpriteBatch( );

        width = height = 150;

        homeImg=new Texture(Gdx.files.internal("house.png"));
        rockImg=new Texture(Gdx.files.internal("rock.png"));
        grassImg=new Texture(Gdx.files.internal("grass.png"));
        fenceImg=new Texture(Gdx.files.internal("fence.png"));
        waterImg=new Texture(Gdx.files.internal("water.png"));
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
        renderRocks();
        renderGrasses();
        renderFences();
        renderWaters();
        batch.end();
    }
    private void renderHomes () {
        for (int i = 0; i < map.homes.size; i++) {
            Home home = map.homes.get(i);

            batch.draw(this.homeImg, home.bounds.x,home.bounds.y, width, height);



        }
    }
    private void renderRocks(){
        for (int i = 0; i < map.rocks.size; i++) {
            Rock rock = map.rocks.get(i);

            batch.draw(this.rockImg, rock.bounds.x,rock.bounds.y, width, height);


        }
    }
    private void renderGrasses() {
        for (int i = 0; i < map.grasses.size; i++) {
            Grass grass = map.grasses.get(i);

            batch.draw(this.grassImg, grass.bounds.x,grass.bounds.y, width, height);


        }
    }
    private void renderFences(){
        for (int i = 0; i < map.fences.size; i++) {
            Fence fence = map.fences.get(i);

            batch.draw(this.fenceImg, fence.bounds.x,fence.bounds.y, width, height);


        }
    }
    private void renderWaters(){
        for (int i = 0; i < map.waters.size; i++) {
            Water water = map.waters.get(i);

            batch.draw(this.waterImg, water.bounds.x,water.bounds.y, width, height);


        }
    }

    public void dispose() {
        homeImg.dispose();
        rockImg.dispose();
        grassImg.dispose();
        fenceImg.dispose();
        waterImg.dispose();
    }
}
