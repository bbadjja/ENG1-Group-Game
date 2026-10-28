package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

int itemNumber = 0;
Rectangle usePlacement;

class Seeds extends Item {
    Seeds(Texture itemTexture, Sprite itemSprite, String name, 
        boolean inInventory, Rectangle initialPlacement, String itemNumber, Rectangle usePlacement) {
        super(itemTexture) = new Texture("texturePathHere.png");
        super(itemSprite) = new Sprite(itemTexture);
        super(itemID) = generateItemID("2", itemNumber);
        super(inInventory) = true;
        super(initialPlacement) = new Rectangle(); // put initial placement here
        usePlacement = new Rectangle(); // make null
    }

    private void useItem() {
    // do you need to take the item as a parameter or can it know
    this.inInventory = false;
    // something to remove sprite from inventory
    this.usePlacement = new Rectangle(); // make current location or however far away
    changeTarget(usePlacement, 3000); // can change to whatever time
    }
    
}