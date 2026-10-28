package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

abstract class Item {
    Texture itemTexture; 
    Sprite itemSprite;
    String itemID; // can easily be changed to integer if needed - ask Amber
    String name;
    boolean inInventory;
    Rectangle initialPlacement;

    // constructor
    public Item(String itemID, String name) {
        this.itemID = itemID; // first digit is item subclass, second digit is no. within that subclass
        this.name = name;
        this.inInventory = false;

        // Presumably we will extend this class for each new item
        // Make this class abstract?
        // Possible Functions:
        //  - OnUse
        //  -
    }

    protected String generateItemID(String itemNum, int objNum) { 
        // could put this method into item and take itemNum and objectNum
        // as parameters?
        String ID = itemNum + String.valueOf(objNum);
        objNum ++;
        return ID;
    }

    abstract void useItem();

    abstract void placeOnMap();

    void pickUpItem() {
        this.inInventory = true;
        // something to remove sprite from map
        return;
    }

}
