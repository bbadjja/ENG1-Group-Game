package com.bbadjja.ENG1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

abstract class Item {
    Texture texture; 
    Sprite sprite;
    String itemID; // can easily be changed to integer if needed - ask Amber
    String name;
    boolean inInventory;
    Rectangle initialPlacement;

    // constructor
    Item(String texturePath, Sprite sprite, String itemID, String name) {
        texture = new Texture(texturePath);
        this.sprite = sprite;
        this.itemID = itemID; // first digit is item subclass, second digit is no. within that subclass
        this.name = name;
        this.inInventory = false;

        // Presumably we will extend this class for each new item
        // Make this class abstract?
        // Possible Functions:
        //  - OnUse
        //  -
    }

    /**
     * Creates a unique ID for every object. The first digit is specific to the 
     * child class it comes from and the second digit is what number object
     * it is within that class (1, 2, 3...)
     * 
     * @param itemNum the unique number for the child class
     * @param objNum the number that object is within the class
     * @return the unique ID for that instance
     */
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
