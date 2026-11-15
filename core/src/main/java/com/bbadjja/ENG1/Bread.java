package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

/**
 * An item Bread that causes stops all Goose objects for 5 seconds.
 */
public class Bread extends Item {
    private static int itemNumber = 0;
    private final static Texture TEXTURE = new Texture("bread.png");

    public Bread(boolean inInventory) {
        super("Bread", TEXTURE, inInventory);

        this.itemID = generateItemID("2",itemNumber);
    }

    /**
     * Stops all goose movement for a fixed amount of time and removes
     * the item from the player's inventory as well as the gamescreen
     *
     * @see Item#useItem(Player, ArrayList)
     */
    @Override
    public void useItem(Player player, ArrayList<Goose> gooseList) {
        for (Goose goose : gooseList) {
            goose.changeSpeed(0, 5);
        }

        this.inInventory = false;
        player.inventory.remove(this);
    }
}
