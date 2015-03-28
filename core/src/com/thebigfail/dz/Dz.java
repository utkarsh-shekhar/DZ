package com.thebigfail.dz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;

//This class is the initial class and contains the render function responsible for all the graphics on screen

public class Dz extends ApplicationAdapter {
	SpriteBatch batch;
    BitmapFont font;
	OrthographicCamera camera;
    Map map;
    final int camScrollRate=10;
    // Creating a base pet texture.
    Texture petBase;
    Sprite drawPet;
    // The object of the pet class.
    Pet pet;
    MapRenderer mapRenderer;
    Controls controls;
    float yScale;   // in some devices actual Gdx.graphics.height is equal to 1184 even if the device screen height is 1280 because 96px maybe used for navigation bar. use this scale to compare y co-ordinates with 1280 screen. example : isThere() in Pet
    float xScale;   //same as yScale Whenever you use Gdx.input.getX() or getY() multiply the values retrieved by their respective scales
    String petName;
    final int resolutionX=720;
    final int resolutionY=1280;
    Vector3 pos = new Vector3(resolutionX, resolutionY, 0);
    Sound worldStretch;
    MyGestureListener mgl;

	@Override
	public void create () {
		batch = new SpriteBatch();
        map = new Map(this);
        mapRenderer= new MapRenderer(map,this);
        petBase = new Texture(Gdx.files.internal("pet.png"));
        camera = new OrthographicCamera(resolutionX,resolutionY);
        camera.position.set((int)(resolutionX*1.5), resolutionY/2, 0);
        controls = new Controls(this);
        font = new BitmapFont();
        yScale=(float)1280/(float)Gdx.graphics.getHeight(); //scale actual height to 1280 standard
        xScale=(float)720/(float)Gdx.graphics.getWidth();
        // Creating a pet object with a name "Critzu".
        petName="Critzu";
        // Pet creation should always be the last thing to do in this method.
        pet = new Pet(petName,this);
        drawPet = new Sprite(petBase, 0, 0, pet.getWidth(), pet.getHeight());
        //sound when scrolling map disallowed due to reaching the end
        worldStretch=Gdx.audio.newSound(Gdx.files.internal("stretch.mp3"));
        mgl=new MyGestureListener(this);
        Gdx.input.setInputProcessor(new GestureDetector(mgl)); //all gestures will be automatically handled by event listener
    }

    @Override
	public void render () {
        //map.update();
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        //mapRenderer.render();
        drawPet.setPosition(pet.getX(), pet.getY());
        batch.begin(); // place only batch operations between begin() and end() otherwise lag may appear
        mapRenderer.render();
        controls.render();
        pet.plotStats();
        drawPet.draw(batch);
        batch.setColor(Color.WHITE);
        batch.end();
        pet.setTouched(false);      // Have to set it to false at every iteration because if someone touches the pet once
                                    // then the pet is set to touched = true and it won't be false even if later it is not being touched
        if(Gdx.input.isTouched()){
            mgl.isTouched(Gdx.input.getX(),Gdx.input.getY());
        }
        camera.update();
	}

    public void setPetTextureRegion(int x, int y) {
        drawPet.setRegion(x, y, pet.getWidth(), pet.getHeight());
    }

    // Clean up the resources
    public void dispose() {
        mapRenderer.dispose();
        pet.dispose();
        petBase.dispose();
        font.dispose();
        map.dispose();
        batch.dispose();
        controls.dispose();
    }
}