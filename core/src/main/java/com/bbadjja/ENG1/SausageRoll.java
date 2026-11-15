package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

/**
 * 
 * An item SausageRoll that increases player health upon use.
 */
public class SausageRoll extends Item {
    private static int itemNumber = 0;
    private final static Texture TEXTURE = new Texture("sausage_roll.png");

    public SausageRoll(boolean inInventory) {
        super("SausageRoll", TEXTURE, inInventory);

        this.itemID = generateItemID("1",itemNumber);
        itemNumber++;
    }

    /**
     * Increments player health by 15 and removes the instance from
     * the player's inventory.
     *
     * @see Item#useItem(Player, ArrayList)
     */
    @Override
    public void useItem(Player player, ArrayList<Goose> gooseList) {
        player.changeHealth(15);
        this.inInventory = false;
    }

}
