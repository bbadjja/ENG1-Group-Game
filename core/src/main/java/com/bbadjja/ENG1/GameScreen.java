package com.bbadjja.ENG1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * A screen which displays game content and handles window modifications
 */
public class GameScreen implements Screen {
    private final Texture BACKGROUND = new Texture("map_temp.png");
    private final Texture INVENTORY_SLOT = new Texture("inventory_slot.png");
    private final Texture HEALTH_BAR = new Texture("health.png");
    private final Texture TEXT_BACKGROUND = new Texture("textBackground.png");

    public final Main game;
    public Player player;

    // width and height of screen in game units I.e. independent of window size
    private float screenWidth;
    private float screenHeight;

    /**
     * Creates the {@code GameScreen} on which game content is displayed
     *
     * @param game the entrypoint of the game
     */
    public GameScreen(final Main game) {
        this.game = game;

        player = new Player();
        player.sprite.setCenter(0,0);
        Gdx.input.setInputProcessor(new PlayerInputListener(player));

        Goose goose1 = new Goose();
        goose1.goosePos.set(player.sprite.getX()-8,player.sprite.getY());

        Goose goose2 = new Goose();
        goose2.goosePos.set(player.sprite.getX(),player.sprite.getY()-8);

        Goose goose3 = new Goose();
        goose3.goosePos.set(player.sprite.getX()+4,player.sprite.getY()+8);

        for (Goose goose : Goose.gooseList) {
            goose.sprite.setPosition(goose.goosePos.x,goose.goosePos.y);
        }

        Event.createEvents();
        Item.createMapItems();
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
        GameLogic.handleCollisions(this);
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
        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiViewport.getCamera().combined);

        game.batch.draw(BACKGROUND, 0, 0, screenWidth, screenHeight);

        // Alters coordinates to be based on camera position
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        for (Event event : Event.eventList) {
            if (event.sprite != null && event.triggered) {
                event.sprite.draw(game.batch);
            }
        }

        for (Item item : Item.itemList) {;
            item.sprite.draw(game.batch);
        }

        for (Goose goose : Goose.gooseList) {
            goose.sprite.draw(game.batch);
        }

        player.sprite.draw(game.batch);

        drawHealthBar();

        drawHUD();

        game.batch.end();
    }

    /**
     * Convenience method for drawing the healthBar
     */
    private void drawHealthBar() {
        float healthWidth;
        if (player.getHealth() <= 0) {
            healthWidth = 0;
        } else {
            healthWidth = 1.3f * (player.getHealth() / player.MAX_HEALTH);
        }

        float healthHeight = 0.15f;
        float healthBarX = player.sprite.getX() + player.sprite.getWidth()/2 - healthWidth/2;
        float healthBarY = player.sprite.getY() + player.sprite.getHeight()+(healthHeight);

        game.batch.draw(HEALTH_BAR,healthBarX,healthBarY,healthWidth,healthHeight);
    }

    /**
     * Convenience method for drawing the HUD
     */
    private void drawHUD() {
        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiViewport.getCamera().combined);

        drawInventory();
        drawTimer();
        drawScoreValues();
        if (GameLogic.isPaused) {
           drawPauseMessage();
        }
    }

    /**
     * Convenience method for drawing the inventory
     */
    private void drawInventory() {
        int scale = 800/8;

        float screenWidth = game.uiViewport.getCamera().viewportWidth;
        float spacing = 0.05f * scale;

        float size = 0.5f * scale;
        float x = (screenWidth - ((5*size) + (4*spacing))) /2;
        float y = 0.25f * scale;

        float itemSize = size*0.8f;

        for (int slot = 0; slot < player.INVENTORY_SIZE; slot++) {
            game.batch.draw(INVENTORY_SLOT,x,y,size,size);

            if (slot < player.inventory.size()) {
                float itemX = x + (size-itemSize)/2;
                float itemY = y + (size-itemSize)/2;
                game.batch.draw(player.inventory.get(slot).sprite.getTexture(),itemX,itemY,itemSize,itemSize);
            }

            x += size + spacing;
        }

        game.viewport.apply();
    }

    /**
     * Convenience method for drawing the timer
     */
    private void drawTimer() {
        game.GLYPH_LAYOUT.setText(game.font,GameLogic.displayTime);
        float textWidth = game.GLYPH_LAYOUT.width;
        game.font.draw(game.batch,GameLogic.displayTime,screenWidth-textWidth,screenHeight);
    }

    /**
     * Convenience method for drawing items used and events triggered
     */
    private void drawScoreValues() {
        String message1 = "Items Used: " + GameLogic.itemsUsed;
        String message2 = "Events Triggered: " + GameLogic.eventsTriggered;
        game.GLYPH_LAYOUT.setText(game.font,message1);
        float textHeight = game.GLYPH_LAYOUT.height;
        game.font.draw(game.batch,message1,0,screenHeight);
        game.font.draw(game.batch,message2,0,screenHeight-(textHeight+5));
    }

    /**
     * Convenience method for drawing the pause message
     */
    private void drawPauseMessage() {
        int scale = 800/8;
        String message;

        if (GameLogic.gameOver) {
            message = "Game Over";
        } else {
            message = "Press Escape to Unpause.";
        }

        game.GLYPH_LAYOUT.setText(game.font,message);
        float textWidth = game.GLYPH_LAYOUT.width;
        float textHeight = game.GLYPH_LAYOUT.height;

        float x = (screenWidth-textWidth)/2;
        float y = screenHeight - (screenHeight-textHeight)/2;
        float padding = (0.2f*scale);

        game.batch.draw(TEXT_BACKGROUND,x-padding/2,y-textHeight-padding/2,textWidth+padding,textHeight+padding);
        game.font.draw(game.batch,message,x,y);
    }

    /**
     * Called when a resize event occurs and handles resizing elements
     *
     * @param width the new width of the window, 0 if minimized
     * @param height the new height of the window, 0 if minimized
     */
    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform,
        //   width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to
        //   be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        game.viewport.update(width, height, false);
        game.uiViewport.update(width,height,true);

        screenWidth = game.uiViewport.getCamera().viewportWidth;
        screenHeight = game.uiViewport.getCamera().viewportHeight;

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
        INVENTORY_SLOT.dispose();
        TEXT_BACKGROUND.dispose();
        player.sprite.getTexture().dispose();

        for (Goose goose : Goose.gooseList) {
            goose.sprite.getTexture().dispose();
        }

        for (Event event : Event.eventList) {
            event.sprite.getTexture().dispose();
        }
    }
}
