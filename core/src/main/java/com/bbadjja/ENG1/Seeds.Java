package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

int itemNumber = 0;
Rectangle usePlacement;

class Seeds extends Item {
    Seeds(Texture texture, Sprite sprite, String name, 
        boolean inInventory, Rectangle initialPlacement, String itemNumber, 
        Rectangle usePlacement) {
        super(this.texture) = new Texture("seeds_temp.png");
        super(this.sprite) = new Sprite(this.texture);
        super(this.itemID) = generateItemID("2", itemNumber);
        itemNumber++;
        super(this.inInventory) = true;
        super(this.initialPlacement) = new Rectangle(); // put initial placement here
        usePlacement = new Rectangle(); // make null
    }

    /**
     * Changes the targets of all the geese temporarily and 
     * removes seeds from the player's inventory and visually
     * from the game screen.
     */

    void useItem() {
        changeTarget(Player.hitbox, 3000);
        // also changeSpeed?

        this.inInventory = false;
        this.texture = null;
        Player.inventory[0] = null;

    }
    
}