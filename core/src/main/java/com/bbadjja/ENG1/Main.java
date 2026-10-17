package com.bbadjja.ENG1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {
    public FitViewport viewport;

    @Override
    public void create() {
        viewport = new FitViewport(8, 5);

        this.setScreen(new GameScreen(this));
    }

    public void render(){
        super.render();
    }

    @Override
    public void dispose() {
        screen.dispose();
    }
}
