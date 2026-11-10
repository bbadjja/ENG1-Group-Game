package com.bbadjja.ENG1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends Game {
    public ExtendViewport viewport;
    public ExtendViewport fixedViewport;
    public OrthographicCamera camera;
    public SpriteBatch batch;
    BitmapFont font;

    /**
     * Called when the game is started, sets up game elements such as screen
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        // Screen is 800x800
        // viewport = 8x8 so 100 pixels = 1 game unit
        viewport = new ExtendViewport(8, 8);
        fixedViewport = new ExtendViewport(8,8,camera);

        this.setScreen(new GameScreen(this));
    }

//    public void render(){
//        super.render();
//    }

    /**
     * Called when the game is closed, disposes all game elements
     */
    @Override
    public void dispose() {
        screen.dispose();
        batch.dispose();
    }
}
