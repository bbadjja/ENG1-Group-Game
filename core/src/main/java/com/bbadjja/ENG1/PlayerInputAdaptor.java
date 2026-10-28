package com.bbadjja.ENG1;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class PlayerInputAdaptor extends InputAdapter {
    Player player;

    public PlayerInputAdaptor(Player player) {
        this.player = player;
    }

    /**
     * Called when a key is pressed down. Reports the keycode to {@code player}.
     *
     * @param keycode the keycode of the key which was just pressed
     * @return {@code true} if the event was handled
     */
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.A, Keys.LEFT:
                player.currentInputs.add(Keys.LEFT);
                break;
            case Keys.D, Keys.RIGHT:
                player.currentInputs.add(Keys.RIGHT);
                break;
            case Keys.W, Keys.UP:
                player.currentInputs.add(Keys.UP);
                break;
            case Keys.S, Keys.DOWN:
                player.currentInputs.add(Keys.DOWN);
                break;
            case Keys.NUM_1, Keys.NUM_2, Keys.NUM_3, Keys.NUM_4, Keys.NUM_5:
                player.useItem(keycode-7);
                break;
            case Keys.ESCAPE:
                GameLogic.togglePause();
        }

        return true;
    }

    /**
     * Called when a key is released. Reports the keycode to other {@code player}.
     *
     * @param keycode the keycode of the key which was released.
     * @return {@code true} if the event was handled
     */
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.A, Keys.LEFT:
                player.currentInputs.remove(Keys.LEFT);
                break;
            case Keys.D, Keys.RIGHT:
                player.currentInputs.remove(Keys.RIGHT);
                break;
            case Keys.W, Keys.UP:
                player.currentInputs.remove(Keys.UP);
                break;
            case Keys.S, Keys.DOWN:
                player.currentInputs.remove(Keys.DOWN);
                break;
        }

        return true;
    }

}
