package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;

public class Seeds extends Item {
    private static int itemNumber = 0;
    private final static Texture TEXTURE = new Texture("bread.png");

    public Seeds(boolean inInventory) {
        // TODO initialPlacement could be got from a list using itemNumber as the index
        //  if inInventory is true, item was placed into inventory when created
        //  so no initalPlacement needed, so initalPlacement can be null - conditional
        super("Seeds", TEXTURE, true, null, null);

        this.itemID = generateItemID("1",itemNumber);
        itemNumber++;
    }

    /**
     * Changes the targets of all the geese temporarily and
     * removes seeds from the player's inventory and visually
     * from the game screen.
     */

    @Override
    public void useItem(Player player, Goose goose) {
       // The changeTarget method is called on all geese
       this.hitbox = player.hitbox; // make it the current location on screens
       goose.changeTarget(this.hitbox, 5);

        this.inInventory = false;
    }

}
