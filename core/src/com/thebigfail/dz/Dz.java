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

    // Creating a base pet texture.
    Texture petBase;
    // The object of the pet class.
    Pet pet;
	@Override
	public void create () {
		batch = new SpriteBatch();

		img = new Texture("bg.jpg");
        camera = new OrthographicCamera(720,1280);
        camera.position.set(1080, 640, 0);
        //camera.update();


        // Creating a pet object with a name "Critzu".
        pet = new Pet("Critzu");
        petBase = new Texture("bot.png");

        // Move pet to the location (1440, 0)
        pet.moveTo(1440, 0);
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
        // draw the background.
		batch.draw(img, 0, 0,2160,1280);

        // drawing the base pet on the screen over the background.
        batch.draw(petBase, pet.getX(), pet.getY(), petBase.getWidth(), petBase.getHeight());

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
