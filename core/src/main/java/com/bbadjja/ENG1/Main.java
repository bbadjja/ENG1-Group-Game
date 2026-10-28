package com.bbadjja.ENG1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {
    public FitViewport viewport;
    public SpriteBatch batch;

    @Override
    public void create() {
        // TODO Change viewport size to appropriate value
        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        this.setScreen(new GameScreen(this));
    }

    public void render(){
        super.render();
    }

    @Override
    public void dispose() {
        screen.dispose();
        batch.dispose();
    }
}
