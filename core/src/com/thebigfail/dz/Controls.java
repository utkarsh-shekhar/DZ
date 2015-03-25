package com.thebigfail.dz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Controls {
    Dz dz;
    SpriteBatch batch;
    TextureRegion dpad;
    TextureRegion left;
    TextureRegion right;
    TextureRegion jump;
    TextureRegion cubeControl;
    TextureRegion cubeFollow;

    public Controls (Dz dz) {
        this.dz = dz;
        batch=dz.batch;
        loadAssets();
    }

    private void loadAssets () {
        Texture texture = new Texture(Gdx.files.internal("controls.png"));
        TextureRegion[] buttons = TextureRegion.split(texture, 64, 64)[0];
        left = buttons[0];
        right = buttons[1];
        jump = buttons[2];
        cubeControl = buttons[3];
        cubeFollow = TextureRegion.split(texture, 64, 64)[1][2];
        dpad = new TextureRegion(texture, 0, 64, 128, 128);
    }

    public void render () {
            batch.draw(left, dz.camera.position.x-360, 0);
            batch.draw(right, dz.camera.position.x+360-right.getRegionWidth(), 0);
    }

    public void dispose () {
        dpad.getTexture().dispose();
    }
}
