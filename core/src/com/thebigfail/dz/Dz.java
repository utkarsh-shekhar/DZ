package com.thebigfail.dz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//TO DO
//separate function to render pet stats
//separate class to render the map


public class Dz extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    BitmapFont font;
	OrthographicCamera camera;
    int cameraX;
    Map map;
    final int camScrollRate=10;
    // Creating a base pet texture.
    Texture petBase;
    // The object of the pet class.
    Pet pet;
    Controls controls;
    float yScale;   // in some devices actual Gdx.graphics.height is equal to 1184 even if the device screen height is 1280 because 96px maybe used for navigation bar. use this scale to compare y co-ordinates with 1280 screen. example : isThere() in Pet
    float xScale;   //same as yScale Whenever you use Gdx.input.getX() or getY() multiply the values retrieved by their respective scales
    String petName;
    final int resolutionX=720;
    final int resolutionY=1280;

	@Override
	public void create () {
		batch = new SpriteBatch();
        map = new Map(this);

		//img = new Texture("bg.jpg");

        camera = new OrthographicCamera(resolutionX,resolutionY);
        camera.position.set((int)(resolutionX*1.5), resolutionY/2, 0);
        controls = new Controls(this);
        font = new BitmapFont();
        //camera.update();


        // Creating a pet object with a name "Critzu".
        petName="Critzu";

        pet = new Pet(petName,this);


        // Move pet to the location (1440, 0)
        //pet.moveTo(100, 900);
        yScale=(float)1280/(float)Gdx.graphics.getHeight(); //scale actual height to 1280 standard
        xScale=(float)720/(float)Gdx.graphics.getWidth();
    }

	@Override
	public void render () {
        //delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());

        map.update();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
        // draw the background.

		//batch.draw(img, 0, 0,resolutionX*3,resolutionY);

        // drawing the base pet on the screen over the background.
        batch.draw(petBase, pet.getX(), pet.getY(), petBase.getWidth(), petBase.getHeight());
        // font.setColor(new Color(1, 1, 1, 1));
        // font.setScale(3f, 3f);
        // font.draw(batch, "Hunger: " + pet.getHunger(), camera.position.x ,camera.position.y);
        batch.end();
        map.update();
        controls.render();
        pet.plotStats();

        pet.setTouched(false);      // Have to set it to false at every iteration because if someone touches the pet once
                                    // then the pet is set to touched = true and it won't be false even if later it is not being touched

        if(Gdx.input.isTouched()) {

            // checks if pet is touched.
            // Does not work yet.
            pet.isPetTouched();

            // camera.position.x;

//System.out.println("touched at : "+Gdx.input.getX()*xScale+" "+Gdx.input.getY()*yScale+"\t left width: "+controls.left.getRegionWidth());
            if(Gdx.input.getX()*xScale < controls.left.getRegionWidth() && Gdx.input.getY()*yScale > resolutionY-controls.left.getRegionHeight())
                camera.position.x-=camScrollRate;// camera.position.x;
            if(Gdx.input.getX()*xScale > resolutionX- controls.right.getRegionWidth() && Gdx.input.getY()*yScale > resolutionY- controls.right.getRegionHeight())
                camera.position.x+=camScrollRate;// camera.position.x;
        }
        if(camera.position.x < resolutionX/2)
            camera.position.x=resolutionX/2;
        if(camera.position.x > resolutionX*3-resolutionX/2)
            camera.position.x=resolutionX*3-resolutionX/2;
        camera.update();
	}


}

