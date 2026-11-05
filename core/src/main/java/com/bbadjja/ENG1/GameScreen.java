package com.bbadjja.ENG1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {
    final Main game;
    Player player;
    private final Texture BACKGROUND = new Texture("map_temp.png");

    public GameScreen(final Main game) {
        this.game = game;

        float worldWidth = game.viewport.getMinWorldWidth();
        float worldHeight = game.viewport.getMinWorldHeight();

        player = new Player();
        player.sprite.setCenter(worldWidth/2,worldHeight/2);
        Gdx.input.setInputProcessor(new PlayerInputAdaptor(player));

        // TODO temp remove this
        Goose testGoose = new Goose();
        testGoose.sprite.setX(-2);
    }

    @Override
    public void show() {
        // This is called when the screen is displayed.
        // As such anything which should start when the screen is displayed
        //   is put in here e.g. startTime

        GameLogic.startTime = System.currentTimeMillis();
        GameLogic.togglePause();
    }

    @Override
    public void render(float delta) {
        // This is libGDX's main loop. Anything in here will run every frame.

        GameLogic.update(this,delta);

        System.out.println(GameLogic.displayTime);
        draw();

    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();

        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        // Draws the background at a fixed position in the center of the screen
        game.batch.setProjectionMatrix(game.backgroundCamera.combined);
        game.batch.draw(BACKGROUND, -worldWidth/2, -worldHeight/2, worldWidth, worldHeight);

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        player.sprite.draw(game.batch);

        for (Goose goose : Goose.gooseList) {
            goose.sprite.draw(game.batch);
        }

        // TODO Draw timer onto screen
        // TODO Draw healthBar onto screen

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your screen here. The parameters represent the new window size.
        game.viewport.update(width, height, false);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
        GameLogic.togglePause();
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
        GameLogic.togglePause();
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        BACKGROUND.dispose();
        player.sprite.getTexture().dispose();
    }
}
