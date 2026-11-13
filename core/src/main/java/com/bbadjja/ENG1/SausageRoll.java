package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

/*

Item number is used to count how many sausage rolls there are, makes up the
second digit of itemID. The first is '1' which is for all SausageRoll objects.

usePlacement is for when the sausage roll is used, the location it's used on
is recorded so that the geese's target can be altered

*/

public class SausageRoll extends Item {
    private static int itemNumber = 0;
    private final static Texture TEXTURE = new Texture("char_temp.png");

    public SausageRoll(boolean inInventory) {
        super("SausageRoll", TEXTURE, inInventory, null);

        this.itemID = generateItemID("1",itemNumber);
        itemNumber++;
    }

    @Override
    public void useItem(Player player, ArrayList<Goose> gooseList) {
        player.changeHealth(15);
        this.inInventory = false;
    }

}
