package com.thebigfail.dz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by code on 3/27/2015.
 * Class representing the home in game
 */
public class MyGestureListener implements GestureDetector.GestureListener {

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Gdx.app.log("occurred: ", "touchDown");
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