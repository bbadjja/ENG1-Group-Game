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
    SausageRoll(Texture texture, Sprite sprite, String name, 
        boolean inInventory, Rectangle initialPlacement, String itemNumber) {
        super(this.texture) = new Texture("sausageRoll_temp.png");
        super(this.sprite) = new Sprite(this.texture);
        super(this.itemID) = generateItemID("1", itemNumber);
        itemNumber++;
        super(this.inInventory) = true;
        super(this.initialPlacement) = new Rectangle(); // put initial placement here
    }

    void useItem() {
        // do you need to take the item as a parameter or can it know

        if(Player.health > 90) { // what is player health and its increments?
            Player.health = 100;
        }
        else {
             Player.health = Player.health + 15;
        }

        this.inInventory = false;
        this.texture = null;
        Player.inventory[1] = null;
    }
}