package com.bbadjja.ENG1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {
    private final Texture BACKGROUND = new Texture("map_temp.png");
    private final Texture INVENTORY_SLOT = new Texture("inventory_slot.png");
    private final Texture HEALTH_BAR = new Texture("health.png");

    public final Main game;
    public Player player;

    // width and height of screen in game units I.e. independent of window size
    private float screenWidth;
    private float screenHeight;

    /**
     * The screen on which the game is displayed
     *
     * @param game the entrypoint of the game
     */
    public GameScreen(final Main game) {
        this.game = game;

        player = new Player();
        player.sprite.setCenter(0,0); // This is now center
        Gdx.input.setInputProcessor(new PlayerInputListener(player));

        // TODO temp remove this
        Goose testGoose = new Goose();
        testGoose.sprite.setCenter(player.sprite.getX()-4,player.sprite.getY());
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
        game.batch.begin();

        // Alters coordinates to be fixed - I.e. (0,0) = bottom left
        game.fixedViewport.apply();
        game.batch.setProjectionMatrix(game.fixedViewport.getCamera().combined);

        game.batch.draw(BACKGROUND, 0, 0, screenWidth, screenHeight);

        // Alters coordinates to be based on camera position
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        player.sprite.draw(game.batch);
        drawHealthBar();

        for (Goose goose : Goose.gooseList) {
            goose.sprite.draw(game.batch);
        }

        drawHUD();

        game.batch.end();
    }

    /**
     * Convenience method for drawing the healthBar
     */
    private void drawHealthBar() {
        float healthWidth = 1.5f * (player.getHealth() / player.MAX_HEALTH);
        float healthHeight = 0.25f;
        float healthBarX = player.sprite.getX() + player.sprite.getWidth()/2 - healthWidth/2;
        float healthBarY = player.sprite.getY()-(healthHeight+0.25f);

        game.batch.draw(HEALTH_BAR,healthBarX,healthBarY,healthWidth,healthHeight);
    }

    /**
     * Convenience method for drawing the HUD
     */
    private void drawHUD() {
        game.fixedViewport.apply();
        game.batch.setProjectionMatrix(game.fixedViewport.getCamera().combined);

        drawInventory();
        drawTimer();
    }

    /**
     * Convenience method for drawing the inventory
     */
    private void drawInventory() {
        float screenWidth = game.fixedViewport.getCamera().viewportWidth;

        float size = 0.5f;
        float x = (screenWidth - ((size +0.05f)* 5)) /2 ;
        float y = 0.25f;

        float itemSize = size*0.8f;

        for (int slot = 0; slot < player.INVENTORY_SIZE; slot++) {
            game.batch.draw(INVENTORY_SLOT,x,y,size,size);
            // TODO one potential way to display items in inventory

            if (slot < player.inventory.size()) {
                float itemX = x + (size-itemSize)/2;
                float itemY = y + (size-itemSize)/2;
                game.batch.draw(player.inventory.get(slot).sprite.getTexture(),itemX,itemY,itemSize,itemSize);
            }

            x += size + 0.05f;
        }

        game.viewport.apply();
    }

    /**
     * Convenience method for drawing the timer
     */
    private void drawTimer() {
        // TODO Draw timer onto screen - GameLogic.displayTime
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
        game.fixedViewport.update(width,height,true);

        screenWidth = game.fixedViewport.getCamera().viewportWidth;
        screenHeight = game.fixedViewport.getCamera().viewportHeight;

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
        HEALTH_BAR.dispose();
        player.sprite.getTexture().dispose();

        for (Goose goose : Goose.gooseList) {
            goose.sprite.getTexture().dispose();
        }
    }
}
