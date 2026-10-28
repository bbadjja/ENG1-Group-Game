package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

int itemNumber = 0;
Rectangle usePlacement;

/*

Item number is used to count how many sausage rolls there are, makes up the 
second digit of itemID. The first is '1' which is for all SausageRoll objects.

usePlacement is for when the sausage roll is used, the location it's used on
is recorded so that the geese's target can be altered

*/

class SausageRoll extends Item {
    SausageRoll(Texture itemTexture, Sprite itemSprite, String name, 
        boolean inInventory, Rectangle initialPlacement, String itemNumber, Rectangle usePlacement) {
        super(itemTexture) = new Texture("texturePathHere.png");
        super(itemSprite) = new Sprite(itemTexture);
        super(itemID) = generateItemID("1", itemNumber);
        super(inInventory) = true;
        super(initialPlacement) = new Rectangle(); // put initial placement here
        usePlacement = new Rectangle(); // make null
    }

    private void useItem() {
        // do you need to take the item as a parameter or can it know
        this.inInventory = false;
        // something to remove sprite from inventory
        // increase player health
    }
}