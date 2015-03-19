package com.thebigfail.dz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Dz extends ApplicationAdapter {
	SpriteBatch batch;
<<<<<<< HEAD
	Texture img, bg;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        bg = new Texture("bg.jpg");
	}
=======
	Texture img;
    BitmapFont font;
	OrthographicCamera camera;
    int cameraX;
    Controls controls;
    int camScrollRate=10;
    // Creating a base pet texture.
    Texture petBase;
    // The object of the pet class.
    Pet pet;
    float yScale;   // in some devices actual Gdx.graphics.height is equal to 1184 even if the device screen height is 1280 because 96px maybe used for navigation bar. use this scale to compare y co-ordinates with 1280 screen. example : isThere() in Pet


	@Override
	public void create () {
		batch = new SpriteBatch();

		img = new Texture("bg.jpg");
        camera = new OrthographicCamera(720,1280);
        camera.position.set(1080, 640, 0);
        controls = new Controls(this);
        font = new BitmapFont();
        //camera.update();


        // Creating a pet object with a name "Critzu".
        pet = new Pet("Critzu",this);
        petBase = new Texture(pet.getBaseImage());

        // Move pet to the location (1440, 0)
        pet.moveTo(100, 900);
        yScale=(float)1280/(float)Gdx.graphics.getHeight(); //scale actual height to 1280 standard
    }
>>>>>>> 2d0c2d27b017f722cae80bb46a0b44a3c907e4c2

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
<<<<<<< HEAD
        batch.draw(bg, -720, 0);
=======
        // draw the background.

		batch.draw(img, 0, 0,2160,1280);

        // drawing the base pet on the screen over the background.
        batch.draw(petBase, pet.getX(), pet.getY(), petBase.getWidth(), petBase.getHeight());
        font.setColor(new Color(1, 1, 1, 1));
        font.setScale(3f, 3f);
        font.draw(batch, "Hunger: " + pet.getHunger(), camera.position.x ,camera.position.y);
        controls.render();
>>>>>>> 2d0c2d27b017f722cae80bb46a0b44a3c907e4c2
		batch.end();
        /*if(Gdx.input.isTouched()){
            cameraX+=(Gdx.input.getX()-360);
            if(cameraX<360)
                cameraX=360;
            if(cameraX>1800)
                cameraX=1800;
            camera.position.set(cameraX,640,0);
        }*/

        if(Gdx.input.isTouched()) {

            // checks if pet is touched.
            // Does not work yet.
            pet.isPetTouched();

            // camera.position.x;


            if(Gdx.input.getX() < 64 && Gdx.input.getY() > Gdx.graphics.getHeight()-64)
                camera.position.x-=camScrollRate;// camera.position.x;
            if(Gdx.input.getX() > Gdx.graphics.getWidth()-64 && Gdx.input.getY() > Gdx.graphics.getHeight()-64)
                camera.position.x+=camScrollRate;// camera.position.x;
        }
        if(camera.position.x < 360)
            camera.position.x=360;
        if(camera.position.x > 1800)
            camera.position.x=1800;
        camera.update();
	}


}
