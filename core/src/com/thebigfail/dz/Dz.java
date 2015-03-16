package com.thebigfail.dz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Dz extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;
    int cameraX;
	@Override
	public void create () {
		batch = new SpriteBatch();

		img = new Texture("bg.jpg");
        camera = new OrthographicCamera(720,1280);
        camera.position.set(1080, 640, 0);
        //camera.update();
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, 0, 0,2160,1280);
		batch.end();
        if(Gdx.input.isTouched()){
            cameraX+=(Gdx.input.getX()-360);
            if(cameraX<360)
                cameraX=360;
            if(cameraX>1800)
                cameraX=1800;
            camera.position.set(cameraX,640,0);
        }
        camera.update();
	}
}
