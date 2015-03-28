package com.thebigfail.dz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by code on 3/27/2015.
 * Class representing the home in game
 */
public class MyGestureListener implements GestureDetector.GestureListener {
    Dz dz;
    public MyGestureListener(Dz dz){
        this.dz=dz;
    }
    public void isTouched(int x, int y){
        Gdx.app.log("occurred: ", "touched");
        if(x*dz.xScale <= dz.controls.left.getRegionWidth() && y*dz.yScale >= dz.resolutionY-dz.controls.left.getRegionHeight())
            dz.camera.position.x-=dz.camScrollRate;// camera.position.x;
        else if(x*dz.xScale >= dz.resolutionX- dz.controls.right.getRegionWidth() && y*dz.yScale >= dz.resolutionY- dz.controls.right.getRegionHeight())
            dz.camera.position.x+=dz.camScrollRate;// camera.position.x;

        //The following lines disallow the camera from going out of the worldd
        if(dz.camera.position.x < dz.resolutionX/2) {
            dz.camera.position.x = dz.resolutionX / 2;
            dz.batch.setColor(new Color(Color.RED));
            dz.worldStretch.play();
        }
        else if(dz.camera.position.x > dz.resolutionX*3-dz.resolutionX/2) {
            dz.camera.position.x = dz.resolutionX * 3 - dz.resolutionX / 2;
            dz.batch.setColor(new Color(Color.RED));
            dz.worldStretch.play();
        }

    }
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {   //even if you keep it touched the function is called only once
        Gdx.app.log("occurred: ", "touchDown");
        // checks if pet is touched.
        dz.pet.isPetTouched();
        if(x*dz.xScale > dz.controls.left.getRegionWidth() && y*dz.yScale < dz.resolutionY-dz.controls.left.getRegionHeight() && x*dz.xScale < dz.resolutionX- dz.controls.right.getRegionWidth() && y*dz.yScale < dz.resolutionY- dz.controls.right.getRegionHeight())
            dz.pet.playSoundClip(0);
        Gdx.app.log("x and y and pointer and button",x+" "+y+" "+pointer+" "+button);
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        Gdx.app.log("occurred: ", "tap");
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        Gdx.app.log("occurred: ", "longPress");
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        Gdx.app.log("occurred: ", "fling");
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        Gdx.app.log("occurred: ", "pan");
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        Gdx.app.log("occurred: ", "panStop");
        return false;
    }

    @Override
    public boolean zoom (float originalDistance, float currentDistance){
        Gdx.app.log("occurred: ", "zoom");
        return false;
    }

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer){
        Gdx.app.log("occurred: ", "pinch");
        return false;
    }
}