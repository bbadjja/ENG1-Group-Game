package com.bbadjja.ENG1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Main extends Game {
    public ExtendViewport viewport;
    public Camera backgroundCamera;
    public SpriteBatch batch;

    /**
     * Called when the game is started, sets up game elements such as screen
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        backgroundCamera = new PerspectiveCamera();
        // Screen is 800x800
        // viewport = 8x8 so 100 pixels = 1 game unit
        viewport = new ExtendViewport(8, 8);

        this.setScreen(new GameScreen(this));
    }

    public void render(){
        super.render();
    }

    /**
     * Called when the game is closed, disposes all game elements
     */
    @Override
    public void dispose() {
        screen.dispose();
        batch.dispose();
    }
}
