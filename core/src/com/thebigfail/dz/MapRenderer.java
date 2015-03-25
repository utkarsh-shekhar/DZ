package com.thebigfail.dz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * Renders the map
 * Created by code on 3/22/2015.
 */
public class MapRenderer {
    Map map;
    Dz dz;
    OrthographicCamera cam;
    SpriteBatch batch;
    Texture homeImg;
    Texture rockImg;
    Texture grassImg;
    Texture fenceImg;
    Texture waterImg;
    public MapRenderer (Map map,Dz dz) {
        this.map = map;
        this.dz=dz;
        this.cam = new OrthographicCamera(54, 32);
        this.batch=new SpriteBatch( );
        homeImg=new Texture(Gdx.files.internal("house.png"));
        rockImg=new Texture(Gdx.files.internal("rock.png"));
        grassImg=new Texture(Gdx.files.internal("grass.png"));
        fenceImg=new Texture(Gdx.files.internal("fence.png"));
        waterImg=new Texture(Gdx.files.internal("water.png"));
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

            batch.draw(this.homeImg, home.bounds.x,home.bounds.y,200,200);



        }
    }
    private void renderRocks(){
        for (int i = 0; i < map.rocks.size; i++) {
            Rock rock = map.rocks.get(i);

            batch.draw(this.rockImg, rock.bounds.x,rock.bounds.y,200,200);


        }
    }
    private void renderGrasses() {
        for (int i = 0; i < map.grasses.size; i++) {
            Grass grass = map.grasses.get(i);

            batch.draw(this.grassImg, grass.bounds.x,grass.bounds.y,200,200);


        }
    }
    private void renderFences(){
        for (int i = 0; i < map.fences.size; i++) {
            Fence fence = map.fences.get(i);

            batch.draw(this.fenceImg, fence.bounds.x,fence.bounds.y,200,200);


        }
    }
    private void renderWaters(){
        for (int i = 0; i < map.waters.size; i++) {
            Water water = map.waters.get(i);

            batch.draw(this.waterImg, water.bounds.x,water.bounds.y,200,200);


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
