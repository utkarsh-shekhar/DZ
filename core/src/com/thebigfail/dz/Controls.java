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
        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 720, 1280);
    }

    public void render () {
            batch.begin();
            batch.draw(left, 0, 0);
            batch.draw(right, 720-right.getRegionWidth(), 0);
            batch.end();
    }

    public void dispose () {
        dpad.getTexture().dispose();
        batch.dispose();

    }
}
