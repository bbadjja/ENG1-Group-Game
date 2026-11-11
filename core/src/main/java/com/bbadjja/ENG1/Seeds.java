package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;

public class Seeds extends Item {
    private static int itemNumber = 0;
    private final static Texture TEXTURE = new Texture("bread.png");

    public Seeds(boolean inInventory) {
        // TODO initialPlacement could be got from a list using itemNumber as the index
        //  if inInventory is true, item was placed into inventory when created
        //  so no initalPlacement needed, so initalPlacement can be null - conditional
        super("SausageRoll",TEXTURE,inInventory,null);

        itemNumber++;
        this.itemID = generateItemID("1",itemNumber);
    }

    /**
     * Changes the targets of all the geese temporarily and
     * removes seeds from the player's inventory and visually
     * from the game screen.
     */

    public void useItem(Player player) {
        // TODO Need to call changeTarget on each individual goose
        //  E.g. below would likely work

        for (Goose goose : Goose.gooseList) {
            goose.changeTarget(player.hitbox,3000);
        }

        // also changeSpeed?

        this.inInventory = false;
    }

}
