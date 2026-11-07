package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

/*

Item number is used to count how many sausage rolls there are, makes up the
second digit of itemID. The first is '1' which is for all SausageRoll objects.

usePlacement is for when the sausage roll is used, the location it's used on
is recorded so that the geese's target can be altered

*/

class SausageRoll extends Item {
    // TODO made these static as they should be the same for all SausageRoll objects
    private static int itemNumber = 0;
    private final static Texture TEXTURE = new Texture("char_temp.png");

    // TODO initialPlacement could be got from a list using itemNumber as the index
    //  if inInventory is true, item was placed into inventory when created
    //  so no initalPlacement needed, so initalPlacement can be null - conditional
    public SausageRoll(boolean inInventory) {
        super("SausageRoll",TEXTURE,inInventory,null);

        itemNumber++;
        this.itemID = generateItemID("1",itemNumber);
    }

    public void useItem(Player player) {
        player.changeHealth(15);
        this.inInventory = false;
        player.inventory.remove(this);
    }

}
