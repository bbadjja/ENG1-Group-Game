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

    // width and height of screen in game units I.e. independent of window size
    float worldWidth;
    float worldHeight;

    /**
     * The screen on which the game is displayed
     *
     * @param game the entrypoint of the game
     */
    public GameScreen(final Main game) {
        this.game = game;

        worldWidth = game.viewport.getMinWorldWidth();
        worldHeight = game.viewport.getMinWorldHeight();

        player = new Player();
        player.sprite.setCenter(worldWidth/2,worldHeight/2);
        Gdx.input.setInputProcessor(new PlayerInputListener(player));

        // TODO temp remove this
        Goose testGoose = new Goose();
        testGoose.sprite.setX(-2);
    }

    /**
     * Called when the screen is displayed
     */
    @Override
    public void show() {
        GameLogic.startTime = System.currentTimeMillis();
        GameLogic.togglePause();
    }

    /**
     * Runs every frame and handles rendering the screen
     *
     * <p>This method is used as the main game loop. Any event which
     * should happen every frame is called from this method.</p>
     *
     * @param delta the time in seconds since the previous frame
     */
    @Override
    public void render(float delta) {
        GameLogic.update(this,delta);
        draw();
    }

    /**
     * Clears and then redraws sprites to the screen
     */
    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();

        // Draws the background at a fixed position in the center of the screen
        game.batch.setProjectionMatrix(game.backgroundCamera.combined);
        game.batch.draw(BACKGROUND, -worldWidth/2, -worldHeight/2, worldWidth, worldHeight);

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        player.sprite.draw(game.batch);

        for (Goose goose : Goose.gooseList) {
            goose.sprite.draw(game.batch);
        }

        // TODO Draw timer onto screen - GameLogic.displayTime

        // TODO Draw healthBar onto screen

        game.batch.end();
    }

    /**
     * Called when a resize event occurs and handles resizing elements
     *
     * @param width the new width of the window, 0 if minimized
     * @param height the new height of the window, 0 if minimized
     */
    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        game.viewport.update(width, height, false);

        // Prevents inputs getting stuck when resizing the window
        player.currentInputs.clear();
    }

    /**
     * Called whenever the application is paused
     *
     * <p>Note this is different to the game being paused. An application
     * pause is caused by events such as minimizing the window.</p>
     */
    @Override
    public void pause() {
        // Invoked when your application is paused.
        GameLogic.togglePause();
    }

    /**
     * Called whenever the application is resumed (see {@link #pause()})
     */
    @Override
    public void resume() {
        GameLogic.togglePause();
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    /**
     * Called when screen is to be disposed and destroys screen assets
     */
    @Override
    public void dispose() {
        BACKGROUND.dispose();
        player.sprite.getTexture().dispose();
    }
}
